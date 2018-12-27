package ro.dobrescuandrei.demonewlibs.user.login

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.Subscribe
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.events.OnLoggedInEvent
import ro.dobrescuandrei.demonewlibs.utils.router.ActivityRouter
import ro.dobrescuandrei.mvvm.BaseActivity
import ro.dobrescuandrei.utils.setOnTextChangedListener

class LoginActivity : BaseActivity<LoginViewModel>()
{
    override fun viewModelClass() : Class<LoginViewModel> = LoginViewModel::class.java
    override fun layout() : Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        viewModel().run {
            usernameEt.setOnTextChangedListener { username.value=it.trim() }
            passwordEt.setOnTextChangedListener { password.value=it.trim() }
            loginButton.setOnClickListener { onLoginClicked() }
        }
    }

    @Subscribe
    fun onLoggedIn(event : OnLoggedInEvent)
    {
        ActivityRouter.startMainActivity(from = this)
        finish()
    }
}