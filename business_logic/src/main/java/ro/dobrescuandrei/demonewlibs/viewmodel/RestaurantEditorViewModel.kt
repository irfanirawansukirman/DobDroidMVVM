package ro.dobrescuandrei.demonewlibs.viewmodel

import ro.dobrescuandrei.demonewlibs.api.AddRestaurantRequest
import ro.dobrescuandrei.demonewlibs.api.EditRestaurantRequest
import ro.dobrescuandrei.demonewlibs.model.ID
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.mvvm.editor.BaseEditorViewModel

class RestaurantEditorViewModel : BaseEditorViewModel<Restaurant, ID>(Restaurant())
{
    override fun add(restaurant : Restaurant) : ID
    {
        return AddRestaurantRequest (restaurant).execute()
    }

    override fun edit(restaurant : Restaurant)
    {
        EditRestaurantRequest(restaurant).execute()
    }
}