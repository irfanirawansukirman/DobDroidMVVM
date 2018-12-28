package ro.dobrescuandrei.demonewlibs.viewmodel

import ro.dobrescuandrei.demonewlibs.api.GetRestaurantsRequest
import ro.dobrescuandrei.demonewlibs.model.FirstPageHeader
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.model.RestaurantFilter
import ro.dobrescuandrei.demonewlibs.model.SecondPageHeader
import ro.dobrescuandrei.mvvm.details.BaseDetailsViewModel

class RestaurantDetailsViewModel : BaseDetailsViewModel<Restaurant>()
{
    var firstPageStickyHeaderIndex  : Int = Int.MAX_VALUE
    var secondPageStickyHeaderIndex : Int = Int.MAX_VALUE

    override fun getItems() : List<Any>
    {
        val restaurantId=model.id
        val items=mutableListOf<Any>()
        val restaurants=GetRestaurantsRequest(null, RestaurantFilter(), 10, 0).execute()

        firstPageStickyHeaderIndex=items.size
        items.add(FirstPageHeader())
        items.addAll(restaurants)

        secondPageStickyHeaderIndex=items.size
        items.add(SecondPageHeader())
        items.addAll(restaurants)

        return items
    }
}