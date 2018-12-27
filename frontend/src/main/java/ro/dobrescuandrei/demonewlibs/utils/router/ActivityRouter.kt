package ro.dobrescuandrei.demonewlibs.utils.router

import android.content.Context
import android.content.Intent
import ro.dobrescuandrei.demonewlibs.model.ID
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.restaurant.list.RestaurantListActivity
import ro.dobrescuandrei.demonewlibs.user.login.LoginActivity

object ActivityRouter
{
    fun startLoginActivity(from : Context)
    {
        val i=Intent(from, LoginActivity::class.java)
        from.startActivity(i)
    }

    fun startRestaurantListActivity(from : Context)
    {
        val i=Intent(from, RestaurantListActivity::class.java)
        from.startActivity(i)
    }

    fun startAddRestaurantActivity(from : Context)
    {
    }

    fun startEditRestaurantActivity(from : Context, restaurant : Restaurant)
    {
    }

    fun startRestaurantDetailsActivity(from : Context, restaurantId : ID)
    {
    }
}