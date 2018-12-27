package ro.dobrescuandrei.mvvm.list

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.andreidobrescu.declarativeadapterkt.BaseDeclarativeAdapter

class RecyclerViewMod : RecyclerView
{
    constructor(context: Context)                                      : super(context)                  { setBackgroundColor(Color.TRANSPARENT) }
    constructor(context: Context, attrs: AttributeSet?)                : super(context, attrs)           { setBackgroundColor(Color.TRANSPARENT) }
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) { setBackgroundColor(Color.TRANSPARENT) }

    var scrollListener : OnScrollListener? = null

    var loadMoreDataAction : (() -> (Unit))? = null
    set(value)
    {
        field = value

        if (value!=null)
        {
            scrollListener=object : OnScrollListener()
            {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
                {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager=recyclerView.layoutManager

                    val visibleItemCount = layoutManager?.childCount?:0
                    val totalItemCount = layoutManager?.itemCount?:0
                    var pastVisibleItems = 0
                    var lastVisibleItem = 0
                    var isHorizontal = false
                    var isReversed = false

                    if (layoutManager is LinearLayoutManager)
                    {
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                        lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                        isHorizontal = layoutManager.orientation==LinearLayoutManager.HORIZONTAL
                        isReversed = layoutManager.reverseLayout
                    }
                    else if (layoutManager is GridLayoutManager)
                    {
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                        lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                        isHorizontal = layoutManager.orientation==GridLayoutManager.HORIZONTAL
                        isReversed = layoutManager.reverseLayout
                    }

                    if ((if (isHorizontal) dx else dy)>0)
                    {
                        if (adapter?.itemCount!=0)
                        {
                            val moreCondition=if (isReversed)
                                lastVisibleItem-pastVisibleItems-visibleItemCount<=10
                            else visibleItemCount+pastVisibleItems+10>=totalItemCount

                            if (moreCondition)
                            {
                                //sunt la sfarsitul listei, loadez mai multe elemente
                                loadMoreDataAction?.invoke()
                            }
                        }
                    }
                }
            }

            if (scrollListener!=null)
                addOnScrollListener(scrollListener!!)
        }
        else
        {
            if (scrollListener!=null)
            {
                removeOnScrollListener(scrollListener!!)
                scrollListener=null
            }
        }
    }

    override fun onDetachedFromWindow()
    {
        if (scrollListener!=null)
            removeOnScrollListener(scrollListener!!)

        loadMoreDataAction=null

        super.onDetachedFromWindow()
    }

    override fun getAdapter() : BaseDeclarativeAdapter?
    {
        return super.getAdapter() as? BaseDeclarativeAdapter
    }
}