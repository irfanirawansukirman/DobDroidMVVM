package ro.dobrescuandrei.mvvm

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.franmontiel.localechanger.LocaleChanger
import com.miguelcatalan.materialsearchview.MaterialSearchView
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar
import ro.dobrescuandrei.mvvm.utils.BackgroundEventBus
import ro.dobrescuandrei.mvvm.utils.ForegroundEventBus
import ro.dobrescuandrei.mvvm.utils.OnKeyboardClosedEvent
import ro.dobrescuandrei.mvvm.utils.OnKeyboardOpenedEvent
import ro.dobrescuandrei.utils.Keyboard
import ro.dobrescuandrei.utils.onCreateOptionsMenu
import ro.dobrescuandrei.utils.onOptionsItemSelected

abstract class BaseActivity<VIEW_MODEL : BaseViewModel> : AppCompatActivity()
{
    lateinit var toolbar : Toolbar
    var searchView : MaterialSearchView? = null
    private var unregistrar : Unregistrar? = null
    private var loadingDialog : AlertDialog? = null

    abstract fun viewModelClass() : Class<VIEW_MODEL>
    abstract fun layout() : Int
    open fun loadDataFromIntent() {}

    fun viewModel() = ViewModelProviders.of(this)[viewModelClass()]

    fun <T> LiveData<T>.observe(owner : LifecycleOwner, observer : (T) -> (Unit))
    {
        observe(owner, Observer(observer))
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        loadDataFromIntent()

        setContentView(layout())

        toolbar=findViewById(R.id.toolbar)
        searchView=findViewById(R.id.searchView)

        setSupportActionBar(toolbar)

        viewModel().run {
            error.value=0
            loading.value=false

            error.observe(this@BaseActivity) { error ->
                if (error!=0) showToast(error)
            }

            loading.observe(this@BaseActivity) { loading ->
                if (loading) showLoadingDialog()
                else hideLoadingDialog()
            }
        }

        try
        {
            BackgroundEventBus.register(this)
        }
        catch (ex : Exception) {}

        unregistrar=KeyboardVisibilityEvent.registerEventListener(this) { isOpen ->
            if (isOpen)
            {
                onKeyboardOpened()
            }
            else
            {
                onKeyboardClosedImmediate()

                Handler().postDelayed({
                    onKeyboardClosed()
                }, 100)
            }
        }
    }

    open fun onKeyboardOpened()
    {
        ForegroundEventBus.post(OnKeyboardOpenedEvent())
    }

    open fun onKeyboardClosedImmediate()
    {
    }

    open fun onKeyboardClosed()
    {
        searchView?.closeSearch()

        ForegroundEventBus.post(OnKeyboardClosedEvent())
    }

    fun showLoadingDialog()
    {
        if (loadingDialog==null)
        {
            val builder=AlertDialog.Builder(this@BaseActivity, R.style.TransparentDialogTheme)
            builder.setCancelable(false)

            val inflater=LayoutInflater.from(this@BaseActivity)
            val view=inflater.inflate(R.layout.dialog_progress, null)
            builder.setView(view)

            loadingDialog=builder.create()
            loadingDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        loadingDialog?.show()
    }

    fun hideLoadingDialog()
    {
        loadingDialog?.dismiss()
    }

    fun showToast(error : Int)
    {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
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

        unregistrar?.unregister()
        unregistrar=null

        hideLoadingDialog()
        loadingDialog=null

        super.onDestroy()
    }

    override fun finish()
    {
        Keyboard.close(this)

        super.finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        try
        {
            toolbar.onCreateOptionsMenu(menuInflater, menu)
            searchView?.setMenuItem(menu?.findItem(R.id.search))
        }
        catch (ex : Exception) {}

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        toolbar.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    override fun attachBaseContext(newBase: Context?)
    {
        super.attachBaseContext(LocaleChanger.configureBaseContext(newBase))
    }

    abstract class WithoutViewModel : BaseActivity<BaseViewModel>()
    {
        override fun viewModelClass() : Class<BaseViewModel> = BaseViewModel::class.java
    }
}
