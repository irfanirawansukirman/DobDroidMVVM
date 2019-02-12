package ro.dobrescuandrei.demonewlibs.restaurant.list

import ro.dobrescuandrei.demonewlibs.model.utils.OnRestaurantChoosedEvent
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.mvvm.chooser.BaseFragmentContainerActivity
import ro.dobrescuandrei.mvvm.utils.BackgroundEventBus

class RestaurantListActivity : BaseFragmentContainerActivity<RestaurantListFragment, Restaurant>()
{
    override fun provideFragment() =
        RestaurantListFragment()

    override fun onBackPressed()
    {
        if (fragment.shouldFinishActivityOnBackPressed())
            moveTaskToBack(false)
    }

    override fun onItemChoosed(restaurant : Restaurant)
    {
        BackgroundEventBus.post(OnRestaurantChoosedEvent(restaurant))
        finish()
    }
}