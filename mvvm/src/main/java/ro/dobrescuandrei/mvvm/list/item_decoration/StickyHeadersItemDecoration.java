package ro.dobrescuandrei.mvvm.list.item_decoration;

import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.functions.Function1;
import ro.andreidobrescu.declarativeadapterkt.BaseDeclarativeAdapter;
import ro.andreidobrescu.declarativeadapterkt.view.CellView;
import ro.andreidobrescu.declarativeadapterkt.view.HeaderView;
import ro.dobrescuandrei.mvvm.list.RecyclerViewMod;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by andreidobrescu on 3/13/18.
 */

public class StickyHeadersItemDecoration extends RecyclerViewMod.ItemDecoration
{
    private Function1<Integer, HeaderView> headerViewInstantiator;
    private Function1<Integer, Class> headerModelClassProvider;

    public StickyHeadersItemDecoration(Function1<Integer, HeaderView> headerViewInstantiator, Function1<Integer, Class> headerModelClassProvider) {
        this.headerViewInstantiator=headerViewInstantiator;
        this.headerModelClassProvider=headerModelClassProvider;
    }

    public void onDestroy()
    {
        headerViewInstantiator=null;
        headerModelClassProvider =null;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        View topChild = parent.getChildAt(0);
        if (topChild==null) {
            return;
        }

        int topChildPosition = parent.getChildAdapterPosition(topChild);
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return;
        }

        HeaderView currentHeader = headerViewInstantiator.invoke(topChildPosition);
        Class headerClass = headerModelClassProvider.invoke(topChildPosition);

        if (currentHeader==null)
            return;

        Object headerData=null;
        List items=((BaseDeclarativeAdapter)parent.getAdapter()).getItems();
        for (int i=topChildPosition; i>=0; i--)
        {
            Object item=items.get(i);
            if (item.getClass().equals(headerClass))
            {
                headerData=item;
                break;
            }
        }

        if (headerData!=null)
        {
            currentHeader.setData(headerData);
        }

        fixLayoutSize(parent, currentHeader);
        int contactPoint = currentHeader.getBottom();
        View childInContact = getChildInContact(parent, contactPoint);
        if (childInContact==null) {
            return;
        }

        if (((CellView)childInContact).isSticky()) {
            moveHeader(c, currentHeader, childInContact);
            return;
        }

        drawHeader(c, currentHeader);
    }

    private void drawHeader(Canvas c, View header) {
        c.save();
        c.translate(0, 0);
        header.draw(c);
        c.restore();
    }

    private void moveHeader(Canvas c, View currentHeader, View nextHeader) {
        c.save();
        c.translate(0, nextHeader.getTop() - currentHeader.getHeight());
        currentHeader.draw(c);
        c.restore();
    }

    private View getChildInContact(RecyclerView parent, int contactPoint) {
        View childInContact = null;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child.getBottom() > contactPoint) {
                if (child.getTop() <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child;
                    break;
                }
            }
        }
        return childInContact;
    }

    /**
     * Properly measures and layouts the top sticky header.
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private void fixLayoutSize(ViewGroup parent, View view) {

        // Specs for parent (RecyclerView)
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        // Specs for children (headers)
        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), MATCH_PARENT);
        int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), WRAP_CONTENT);

        view.measure(childWidthSpec, childHeightSpec);

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}
