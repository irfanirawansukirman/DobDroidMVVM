package ro.dobrescuandrei.mvvm.chooser

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import ro.dobrescuandrei.mvvm.BaseFragment
import ro.dobrescuandrei.mvvm.R

abstract class BaseFragmentContainerActivity<FRAGMENT : BaseFragment<*>, MODEL> : BaseContainerActivity<MODEL>()
{
    lateinit var fragment : FRAGMENT

    abstract fun provideFragment() : FRAGMENT

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setSupportActionBar(Toolbar(this))

        fragment=provideFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun layout() : Int = R.layout.activity_fragment_container

    override fun onBackPressed()
    {
        if (fragment.shouldFinishActivityOnBackPressed())
            super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        if (menu!=null) fragment.onCreateOptionsMenu(menu, menuInflater)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        if (item!=null) fragment.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }
}
