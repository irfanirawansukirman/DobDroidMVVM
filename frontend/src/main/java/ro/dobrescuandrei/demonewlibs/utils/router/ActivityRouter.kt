package ro.dobrescuandrei.demonewlibs.utils.router

import android.content.Context
import android.content.Intent
import ro.dobrescuandrei.demonewlibs.user.login.LoginActivity

object ActivityRouter
{
    fun startLoginActivity(from : Context)
    {
        val i=Intent(from, LoginActivity::class.java)
        from.startActivity(i)
    }

    fun startMainActivity(from : Context)
    {
        val i=Intent(from, LoginActivity::class.java)
        from.startActivity(i)
    }
}