package ro.dobrescuandrei.demonewlibs.user.login

import android.text.TextUtils
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.model.utils.OnLoggedInEvent
import ro.dobrescuandrei.demonewlibs.model.utils.Preferences
import ro.dobrescuandrei.demonewlibs.api.LoginRequest
import ro.dobrescuandrei.mvvm.BaseViewModel
import ro.dobrescuandrei.mvvm.utils.ForegroundEventBus
import ro.dobrescuandrei.utils.Run

class LoginViewModel : BaseViewModel()
{
    fun onLoginClicked(username : String, password : String)
    {
        when
        {
            TextUtils.isEmpty(username) -> showError(R.string.please_type_username)
            TextUtils.isEmpty(password) -> showError(R.string.please_type_password)
            else ->
            {
                showLoading()

                Run.async(task = { LoginRequest(username, password).execute() },
                    onAny = { hideLoading() },
                    onError = { showError(R.string.invalid_username_or_password) },
                    onSuccess = { user ->
                        Preferences.userId=user.id?:0
                        Preferences.username=user.name

                        ForegroundEventBus.post(OnLoggedInEvent())
                    })
            }
        }
    }
}