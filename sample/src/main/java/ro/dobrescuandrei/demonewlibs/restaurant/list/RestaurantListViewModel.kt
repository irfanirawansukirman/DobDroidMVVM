package ro.dobrescuandrei.demonewlibs.restaurant.list

import ro.dobrescuandrei.demonewlibs.api.GetRestaurantsRequest
import ro.dobrescuandrei.demonewlibs.model.RestaurantFilter
import ro.dobrescuandrei.mvvm.list.BaseListViewModel

class RestaurantListViewModel : BaseListViewModel<RestaurantFilter>(RestaurantFilter())
{
    override fun getItems() : List<Any> =
        GetRestaurantsRequest(search, filter, limit(), offset).execute()
}