package ro.dobrescuandrei.mvvm.utils

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ro.dobrescuandrei.mvvm.BaseFragment

class SimpleFragmentPagerAdapter
(
    fragmentManager : FragmentManager,
    val fragments : Array<BaseFragment<*>>
) : FragmentPagerAdapter(fragmentManager)
{
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size

    fun shouldFinishActivityOnBackPressed(currentTab: Int): Boolean
    {
        if (currentTab>=0&&currentTab<fragments.size)
            return fragments[currentTab].shouldFinishActivityOnBackPressed()
        return true
    }

    fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater)
    {
        for (fragment in fragments)
            fragment.onCreateOptionsMenu(menu, menuInflater)
    }

    fun onOptionsItemSelected(item: MenuItem, currentTab: Int)
    {
        if (currentTab>=0&&currentTab<fragments.size)
            fragments[currentTab].onOptionsItemSelected(item)
    }
}
