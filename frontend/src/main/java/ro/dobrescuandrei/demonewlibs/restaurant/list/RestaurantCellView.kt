package ro.dobrescuandrei.demonewlibs.restaurant.list

import android.content.Context
import android.util.AttributeSet
import kotlinx.android.synthetic.main.cell_restaurant.view.*
import ro.andreidobrescu.declarativeadapterkt.CellView
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.utils.router.ActivityRouter

class RestaurantCellView : CellView<Restaurant>
{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun layout() : Int = R.layout.cell_restaurant

    override fun setData(restaurant : Restaurant)
    {
        nameTv.text=restaurant.name

        nameTv.setOnClickListener {
            ActivityRouter.startRestaurantDetailsActivity(from = it.context, restaurantId = restaurant.id)
        }
    }
}