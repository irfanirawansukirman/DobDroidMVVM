package ro.dobrescuandrei.demonewlibs.api

import ro.dobrescuandrei.demonewlibs.model.Restaurant

class EditRestaurantRequest
(
    val restaurant : Restaurant
) : BaseRequest<Boolean>()
{
    override fun execute() : Boolean = true
}