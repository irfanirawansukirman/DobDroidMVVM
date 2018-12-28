package ro.dobrescuandrei.demonewlibs.model

import android.content.res.Resources
import ro.dobrescuandrei.demonewlibs_bll.R
import ro.dobrescuandrei.mvvm.utils.Identifiable
import ro.dobrescuandrei.mvvm.utils.NO_VALUE_INT

open class Restaurant
(
    id : ID = null,
    var name : String = "",
    var rating : Int = 3,
    var type : Int = NO_VALUE_INT
) : Identifiable<ID>(id)
{
    companion object
    {
        const val TYPE_NORMAL = 1
        const val TYPE_FAST_FOOD = 2
    }

    fun getTypeAsString(resources : Resources) = resources.getString(when(type)
    {
        TYPE_NORMAL -> R.string.normal
        TYPE_FAST_FOOD -> R.string.fast_food
        else -> R.string.empty
    })
}