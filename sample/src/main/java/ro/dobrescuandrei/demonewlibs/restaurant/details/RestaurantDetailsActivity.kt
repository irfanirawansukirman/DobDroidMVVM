package ro.dobrescuandrei.demonewlibs.restaurant.details

import android.os.Bundle
import ro.andreidobrescu.declarativeadapterkt.DeclarativeAdapter
import ro.andreidobrescu.declarativeadapterkt.view.HeaderView
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.model.utils.FirstPageHeader
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.model.utils.SecondPageHeader
import ro.dobrescuandrei.demonewlibs.restaurant.list.cells.RestaurantCellView
import ro.dobrescuandrei.demonewlibs.restaurant.details.headers.FirstPageHeaderView
import ro.dobrescuandrei.demonewlibs.restaurant.details.headers.SecondPageHeaderView
import ro.dobrescuandrei.mvvm.details.BaseDetailsActivity
import ro.dobrescuandrei.utils.setupBackIcon

class RestaurantDetailsActivity : BaseDetailsActivity<Restaurant, RestaurantDetailsViewModel, DeclarativeAdapter>()
{
    override fun viewModelClass() : Class<RestaurantDetailsViewModel> = RestaurantDetailsViewModel::class.java
    override fun layout() : Int = R.layout.activity_restaurant_details

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        toolbar?.let { toolbar ->
            toolbar.setupBackIcon()
            toolbar.setTitle(R.string.restaurant)
        }
    }

    override fun provideAdapter() : DeclarativeAdapter
    {
        val adapter=DeclarativeAdapter()

        adapter.whenInstanceOf(Restaurant::class,
                use = { RestaurantCellView(it) })
            .whenInstanceOf(
                FirstPageHeader::class,
                use = { FirstPageHeaderView(it) })
            .whenInstanceOf(
                SecondPageHeader::class,
                use = { SecondPageHeaderView(it) })

        return adapter
    }

    override fun provideStickyHeaderModelClass(position: Int): Class<*>? = when
    {
        position>=viewModel.secondPageStickyHeaderIndex -> SecondPageHeader::class.java
        position>=viewModel.firstPageStickyHeaderIndex  -> FirstPageHeader::class.java
        else -> null
    }

    override fun provideStickyHeaderView(position: Int): HeaderView<*>? = when
    {
        position>=viewModel.secondPageStickyHeaderIndex -> SecondPageHeaderView(this)
        position>=viewModel.firstPageStickyHeaderIndex  -> FirstPageHeaderView(this)
        else -> null
    }
}