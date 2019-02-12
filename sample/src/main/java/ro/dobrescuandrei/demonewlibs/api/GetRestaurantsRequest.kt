package ro.dobrescuandrei.demonewlibs.api

import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.model.RestaurantFilter

class GetRestaurantsRequest
(
    val search : String?,
    val filter : RestaurantFilter,
    val limit : Int,
    val offset : Int
) : BaseRequest<List<Restaurant>>()
{
    override fun execute() : List<Restaurant>
    {
        if (offset==400||search!=null)
            return listOf()

        val restaurants=mutableListOf<Restaurant>()
        for (i in offset+1..offset+limit)
            restaurants.add(Restaurant(id = i, name = "R$i", rating = 5, type = Restaurant.TYPE_NORMAL))
        return restaurants
    }
}
