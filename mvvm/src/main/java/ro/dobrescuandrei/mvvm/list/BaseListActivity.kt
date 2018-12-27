package ro.dobrescuandrei.mvvm.list

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.miguelcatalan.materialsearchview.MaterialSearchView
import ro.andreidobrescu.declarativeadapterkt.BaseDeclarativeAdapter
import ro.dobrescuandrei.mvvm.BaseActivity
import ro.dobrescuandrei.mvvm.R

abstract class BaseListActivity<VIEW_MODEL : BaseListViewModel<*>, ADAPTER : BaseDeclarativeAdapter> : BaseActivity<VIEW_MODEL>()
{
    lateinit var recyclerView : RecyclerViewMod
    lateinit var emptyView : TextView
    var addButton : FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        recyclerView=findViewById(R.id.recyclerView)
        emptyView=findViewById(R.id.emptyView)
        addButton=findViewById(R.id.addButton)

        addButton?.setOnClickListener { onAddButtonClicked() }

        searchView?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                if (!TextUtils.isEmpty(query))
                {
                    viewModel().search=query!!
                    viewModel().loadData()

                    searchView?.closeSearch()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })

        recyclerView.layoutManager=provideLayoutManager()

        val adapter=provideAdapter()
        recyclerView.adapter=adapter

        val decoration=provideItemDecoration()
        if (decoration!=null)
        {
            recyclerView.addItemDecoration(decoration)
        }

        if (shouldLoadMoreOnScroll())
        {
            recyclerView.loadMoreDataAction={
                viewModel().loadMoreData()
            }
        }

        emptyView.text = provideEmptyViewDescription()

        viewModel().run {
            firstPageItems.value=null
            nextPageItems.value=null
            isEmpty.value=false

            firstPageItems.observe(this@BaseListActivity) { items ->
                if (items!=null)
                {
                    recyclerView.adapter?.setItems(items)
                    recyclerView.scrollToPosition(0)
                }
            }

            nextPageItems.observe(this@BaseListActivity) { items ->
                if (items!=null)
                {
                    recyclerView.adapter?.addItems(items)
                }
            }

            isEmpty.observe(this@BaseListActivity) { isEmpty ->
                if (isEmpty)
                {
                    emptyView.visibility=View.VISIBLE
                    recyclerView.visibility=View.GONE
                }
                else
                {
                    emptyView.visibility=View.GONE
                    recyclerView.visibility=View.VISIBLE
                }
            }
        }

        viewModel().loadData()
    }

    abstract fun provideAdapter() : ADAPTER
    open fun provideLayoutManager() : RecyclerView.LayoutManager = LinearLayoutManager(this)
    open fun provideItemDecoration() : RecyclerView.ItemDecoration? = FABDividerItemDecoration(this)
    open fun shouldLoadMoreOnScroll() : Boolean = true
    open fun provideEmptyViewDescription() : String = getString(R.string.no_items)

    override fun onBackPressed()
    {
        try
        {
            if (viewModel().searchMode())
            {
                viewModel().search=null
                viewModel().loadData()
            }
            else
            {
                super.onBackPressed()
            }
        }
        catch (e : Exception)
        {
            super.onBackPressed()
        }
    }

    override fun onKeyboardOpened()
    {
        addButton?.visibility=View.GONE
        super.onKeyboardOpened()
    }

    override fun onKeyboardClosed()
    {
        addButton?.visibility=View.VISIBLE
        super.onKeyboardClosed()
    }

    open fun onAddButtonClicked()
    {
    }
}
