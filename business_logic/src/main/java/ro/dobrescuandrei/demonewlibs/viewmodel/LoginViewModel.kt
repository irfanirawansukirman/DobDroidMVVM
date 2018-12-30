package ro.dobrescuandrei.demonewlibs.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import ro.dobrescuandrei.demonewlibs.OnLoggedInEvent
import ro.dobrescuandrei.demonewlibs.Preferences
import ro.dobrescuandrei.demonewlibs.api.LoginRequest
import ro.dobrescuandrei.demonewlibs_bll.R
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