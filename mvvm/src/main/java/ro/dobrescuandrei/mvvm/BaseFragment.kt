package ro.dobrescuandrei.mvvm

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miguelcatalan.materialsearchview.MaterialSearchView
import org.greenrobot.eventbus.Subscribe
import ro.dobrescuandrei.mvvm.utils.*
import ro.dobrescuandrei.utils.onCreateOptionsMenuFromFragment
import ro.dobrescuandrei.utils.onOptionsItemSelected

abstract class BaseFragment<VIEW_MODEL : BaseViewModel> : Fragment()
{
    var toolbar : Toolbar? = null
    var searchView : MaterialSearchView? = null

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

        toolbar=view.findViewById(R.id.toolbar)
        searchView=view.findViewById(R.id.searchView)

        (context as AppCompatActivity).setSupportActionBar(toolbar)

        if (viewModelClass()!=BaseViewModel::class.java)
        {
            viewModel().run {
                error.value=0
                loading.value=false

                error.observe(this@BaseFragment) { error ->
                    if (error!=NO_VALUE_INT)
                        (context as BaseActivity<*>).showToast(error)
                }

                loading.observe(this@BaseFragment) { loading ->
                    if (loading) (context as BaseActivity<*>).showLoadingDialog()
                    else (context as BaseActivity<*>).hideLoadingDialog()
                }
            }
        }

        try
        {
            BackgroundEventBus.register(this)
        }
        catch (ex : Exception) {}

        return view
    }

    @Subscribe
    open fun onKeyboardOpened(event : OnKeyboardOpenedEvent)
    {
    }

    @Subscribe
    open fun onKeyboardClosed(event : OnKeyboardClosedEvent)
    {
        searchView?.closeSearch()
    }

    override fun onResume()
    {
        super.onResume()

        try
        {
            ForegroundEventBus.register(this)
        }
        catch (ex : Exception) {}
    }

    override fun onPause()
    {
        super.onPause()

        try
        {
            ForegroundEventBus.unregister(this)
        }
        catch (ex : Exception) {}
    }

    override fun onDestroy()
    {
        try
        {
            BackgroundEventBus.unregister(this)
        }
        catch (ex : Exception) {}

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater)
    {
        try
        {
            toolbar?.onCreateOptionsMenuFromFragment()
            searchView?.setMenuItem(menu.findItem(R.id.search))
        }
        catch (ex : Exception) {}

        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        toolbar?.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    open fun shouldFinishActivityOnBackPressed() : Boolean
    {
        return true
    }

    abstract class WithoutViewModel : BaseFragment<BaseViewModel>()
    {
        override fun viewModelClass() : Class<BaseViewModel> = BaseViewModel::class.java
    }
}
