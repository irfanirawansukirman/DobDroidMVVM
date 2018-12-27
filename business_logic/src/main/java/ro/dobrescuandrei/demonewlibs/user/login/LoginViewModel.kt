package ro.dobrescuandrei.demonewlibs.user.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import ro.dobrescuandrei.demonewlibs.api.LoginRequest
import ro.dobrescuandrei.demonewlibs.events.OnLoggedInEvent
import ro.dobrescuandrei.demonewlibs.utils.preferences.Preferences
import ro.dobrescuandrei.demonewlibs_bll.R
import ro.dobrescuandrei.mvvm.BaseViewModel
import ro.dobrescuandrei.mvvm.utils.ForegroundEventBus
import ro.dobrescuandrei.utils.Run

class LoginViewModel : BaseViewModel()
{
    val username : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val password : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun onLoginClicked()
    {
        when
        {
            TextUtils.isEmpty(username.value) -> showError(R.string.please_type_username)
            TextUtils.isEmpty(password.value) -> showError(R.string.please_type_password)
            else ->
            {
                showLoading()

                Run.async(task = { LoginRequest(
                        username = username.value?:"",
                        password = password.value?:"").execute() },
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