package ro.dobrescuandrei.demonewlibs.restaurant.list

import org.greenrobot.eventbus.Subscribe
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.model.utils.OnRestaurantAddedEvent
import ro.dobrescuandrei.mvvm.BaseFragment
import ro.dobrescuandrei.mvvm.chooser.BaseFragmentsContainerActivity

class RestaurantPagedListActivity : BaseFragmentsContainerActivity<Restaurant>()
{
    override fun provideFragments(): Array<BaseFragment<*>> = arrayOf(
        RestaurantListFragment(),
        RestaurantListFragment())

    override fun provideBottomNavigationMenu(): Int = R.menu.bottom_menu_restaurants_paged

    override fun onItemChoosed(restaurant : Restaurant)
    {
        println(restaurant)
        finish()
    }

    @Subscribe
    fun onRestaurantAdded(event : OnRestaurantAddedEvent)
    {
        onItemChoosed(event.restaurant)
    }
}