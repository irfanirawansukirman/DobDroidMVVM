package ro.dobrescuandrei.mvvm.list.item_decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ro.dobrescuandrei.mvvm.R

class FABDividerItemDecoration
(
    val marginBottom : Int,
    val dividerHeight : Int,
    val dividerColor : Int
) : RecyclerView.ItemDecoration()
{
    constructor(context : Context?) : this
    (
        context = context,
        dividerColor = R.color.divider_color
    )

    constructor(context : Context?, dividerColor: Int) : this
        (
        marginBottom = context?.resources?.getDimensionPixelSize(R.dimen.fab_list_bottom_margin)?:0,
        dividerHeight = context?.resources?.getDimensionPixelSize(R.dimen.one_dp)?:0,
        dividerColor = ContextCompat.getColor(context!!, dividerColor)
    )

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        if (parent.getChildLayoutPosition(view)==(parent.adapter?.itemCount?:0)-1)
            outRect.bottom=marginBottom
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        val left=parent.paddingLeft.toFloat()
        val right=(parent.width.toFloat())-(parent.paddingRight.toFloat()?:0.0f)

        val paint= Paint()
        paint.strokeWidth=1.0f
        paint.color=dividerColor

        val childCount=parent.childCount
        for (i in 0 until childCount)
        {
            val child=parent.getChildAt(i)

            val params=child?.layoutParams as RecyclerView.LayoutParams

            val top=(child.bottom+params.bottomMargin).toFloat()
            val bottom=top+dividerHeight

            c.drawRect(left, top, right, bottom, paint)
        }
    }
}
