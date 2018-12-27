package ro.dobrescuandrei.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ro.dobrescuandrei.mvvm.utils.BackgroundEventBus
import ro.dobrescuandrei.mvvm.utils.ForegroundEventBus

abstract class BaseFragment<VIEW_MODEL : BaseViewModel> : Fragment()
{
    abstract fun viewModelClass() : Class<VIEW_MODEL>
    abstract fun layout() : Int
    open fun loadDataFromArguments() {}

    fun viewModel() = ViewModelProviders.of(this)[viewModelClass()]

    fun <T> LiveData<T>.observe(owner : LifecycleOwner, observer : (T) -> (Unit))
    {
        observe(owner, Observer(observer))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view=inflater.inflate(layout(), container, false)

        loadDataFromArguments()

        viewModel().run {
            error.value=0
            loading.value=false

            error.observe(this@BaseFragment) { error ->
                if (error!=0) (context as BaseActivity<*>).showToast(error)
            }

            loading.observe(this@BaseFragment) { loading ->
                if (loading) (context as BaseActivity<*>).showLoadingDialog()
                else (context as BaseActivity<*>).hideLoadingDialog()
            }
        }

        try
        {
            BackgroundEventBus.register(this)
        }
        catch (ex : Exception)
        {
            if (BuildConfig.DEBUG)
                ex.printStackTrace()
        }

        return view
    }

    override fun onResume()
    {
        super.onResume()

        try
        {
            ForegroundEventBus.register(this)
        }
        catch (ex : Exception)
        {
            if (BuildConfig.DEBUG)
                ex.printStackTrace()
        }
    }

    override fun onPause()
    {
        super.onPause()

        try
        {
            ForegroundEventBus.unregister(this)
        }
        catch (ex : Exception)
        {
            if (BuildConfig.DEBUG)
                ex.printStackTrace()
        }
    }

    override fun onDestroy()
    {
        try
        {
            BackgroundEventBus.unregister(this)
        }
        catch (ex : Exception)
        {
            if (BuildConfig.DEBUG)
                ex.printStackTrace()
        }

        super.onDestroy()
    }

    abstract class WithoutViewModel : BaseFragment<BaseViewModel>()
    {
        override fun viewModelClass() : Class<BaseViewModel> = BaseViewModel::class.java
    }
}
