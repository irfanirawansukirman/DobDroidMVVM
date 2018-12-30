package ro.dobrescuandrei.mvvm.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import ro.andreidobrescu.declarativeadapterkt.view.CellView
import ro.dobrescuandrei.mvvm.chooser.BaseContainerActivity

abstract class ChooserCellView<MODEL> : CellView<MODEL>
{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, layout: Int) : super(context, layout)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    fun View.setOnCellClickListener(withModel : MODEL, onClickListener : (View) -> (Unit))
    {
        setOnClickListener { view ->
            val activity=context as? BaseContainerActivity<MODEL>
            if (activity?.chooseMode==true)
            {
                activity.onItemChoosed(withModel)
            }
            else
            {
                onClickListener(view)
            }
        }
    }
}