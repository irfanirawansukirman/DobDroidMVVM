package ro.dobrescuandrei.demonewlibs.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.Subscribe
import ro.dobrescuandrei.demonewlibs.OnLoggedInEvent
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.router.ActivityRouter
import ro.dobrescuandrei.demonewlibs.viewmodel.LoginViewModel
import ro.dobrescuandrei.mvvm.BaseActivity
import ro.dobrescuandrei.utils.setOnTextChangedListener

class LoginActivity : BaseActivity<LoginViewModel>()
{
    override fun viewModelClass() : Class<LoginViewModel> = LoginViewModel::class.java
    override fun layout() : Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        loginButton.setOnClickListener {
            viewModel().onLoginClicked(
                username = usernameEt.text.toString().trim(),
                password = passwordEt.text.toString().trim())
        }
    }

    @Subscribe
    fun onLoggedIn(event : OnLoggedInEvent)
    {
        ActivityRouter.startRestaurantListActivity(from = this)
        finish()
    }
}