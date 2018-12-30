package ro.dobrescuandrei.mvvm.chooser

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import ro.dobrescuandrei.mvvm.BaseFragment
import ro.dobrescuandrei.mvvm.R
import ro.dobrescuandrei.mvvm.utils.SimpleFragmentPagerAdapter
import ro.dobrescuandrei.utils.setupWithViewPager

abstract class BaseFragmentsContainerActivity<MODEL> : BaseContainerActivity<MODEL>()
{
    lateinit var viewPager : ViewPager

    abstract fun provideFragments() : Array<BaseFragment<*>>
    abstract fun provideBottomNavigationMenu() : Int
    open fun provideInitialTab() : Int = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setSupportActionBar(Toolbar(this))

        val initialTab=provideInitialTab()

        viewPager=findViewById<ViewPager>(R.id.viewPager)
        viewPager.offscreenPageLimit=100
        viewPager.currentItem=initialTab
        viewPager.adapter= SimpleFragmentPagerAdapter(
            fragmentManager = supportFragmentManager,
            fragments = provideFragments()
        )

        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.inflateMenu(provideBottomNavigationMenu())
        bottomNavigationView.setupWithViewPager(viewPager, initialTab)
    }

    override fun layout() : Int = R.layout.activity_fragments_container

    fun adapter() : SimpleFragmentPagerAdapter = viewPager.adapter as SimpleFragmentPagerAdapter

    override fun onBackPressed()
    {
        if (adapter().shouldFinishActivityOnBackPressed(currentTab = viewPager.currentItem))
            super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        if (menu!=null) adapter().onCreateOptionsMenu(menu, menuInflater)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        if (item!=null) adapter().onOptionsItemSelected(item, currentTab = viewPager.currentItem)
        return super.onOptionsItemSelected(item)
    }
}
