package ro.dobrescuandrei.demonewlibs.activity

import ro.dobrescuandrei.demonewlibs.OnRestaurantChoosedEvent
import ro.dobrescuandrei.demonewlibs.fragment.RestaurantListFragment
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.mvvm.chooser.BaseFragmentContainerActivity
import ro.dobrescuandrei.mvvm.utils.BackgroundEventBus

class RestaurantListActivity : BaseFragmentContainerActivity<RestaurantListFragment, Restaurant>()
{
    override fun provideFragment() = RestaurantListFragment()

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