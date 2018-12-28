package ro.dobrescuandrei.demonewlibs.api

import ro.dobrescuandrei.demonewlibs.model.ID
import ro.dobrescuandrei.demonewlibs.model.Restaurant

class AddRestaurantRequest
(
    val restaurant : Restaurant
) : BaseRequest<ID>()
{
    override fun execute() : ID = 100
}