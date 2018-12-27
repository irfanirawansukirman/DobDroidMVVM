package ro.dobrescuandrei.demonewlibs.restaurant.list

import android.os.Bundle
import ro.andreidobrescu.declarativeadapterkt.SimpleDeclarativeAdapter
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.utils.dialogs.ShowDialog
import ro.dobrescuandrei.demonewlibs.utils.router.ActivityRouter
import ro.dobrescuandrei.mvvm.list.BaseListActivity
import ro.dobrescuandrei.utils.set
import ro.dobrescuandrei.utils.setMenu
import ro.dobrescuandrei.utils.setupBackIcon

class RestaurantListActivity : BaseListActivity<RestaurantListViewModel, SimpleDeclarativeAdapter<Restaurant>>()
{
    override fun provideAdapter() = SimpleDeclarativeAdapter { RestaurantCellView(it) }
    override fun provideEmptyViewDescription() = getString(R.string.no_restaurants)
    override fun viewModelClass() = RestaurantListViewModel::class.java
    override fun layout() = R.layout.activity_restaurant_list

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        toolbar.setupBackIcon()
        toolbar.setMenu(R.menu.menu_restaurants)
        toolbar[R.id.filterByRating]={
            ShowDialog.withList(context = this,
                title = R.string.choose_rating,
                onClick = { index, value ->
                    viewModel().filter.rating=value
                    viewModel().loadData()
                },
                values = listOf(1,2,3,4,5))
        }

        toolbar[R.id.filterByType]={
            ShowDialog.withList(context = this,
                title = R.string.choose_type,
                onClick = { index, value ->
                    viewModel().filter.type=index+1
                    viewModel().loadData()
                },
                values = listOf(
                    getString(R.string.normal),
                    getString(R.string.fast_food)))
        }
    }

    override fun onAddButtonClicked()
    {
        ActivityRouter.startAddRestaurantActivity(from = this)
    }
}