package ro.dobrescuandrei.mvvm.list

import androidx.lifecycle.MutableLiveData
import ro.dobrescuandrei.mvvm.BaseViewModel
import ro.dobrescuandrei.utils.Run

abstract class BaseListViewModel<FILTER>
(
    val filter : FILTER
) : BaseViewModel()
{
    var search : String? = null

    protected var offset : Int = 0
    protected var doneLoadingPages : Boolean = false

    val firstPageItems : MutableLiveData<List<Any>> by lazy { MutableLiveData<List<Any>>() }
    val nextPageItems  : MutableLiveData<List<Any>> by lazy { MutableLiveData<List<Any>>() }
    val isEmpty : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    abstract fun getItems(atPage : Int) : List<Any>
    open fun limit() : Int = 100

    fun loadData()
    {
        doneLoadingPages=false
        offset=0

        loadMoreData()
    }

    fun loadMoreData()
    {
        if (doneLoadingPages)
            return

        val isFirstPage=offset==0
        if (isFirstPage)
            showLoading()

        Run.async(task = { getItems(atPage = offset) },
            onAny = {
                if (isFirstPage)
                    hideLoading()
            },
            onSuccess = { items ->
                doneLoadingPages=items.isEmpty()

                if (isFirstPage)
                {
                    firstPageItems.value=items
                    isEmpty.value=items.isEmpty()
                }
                else
                {
                    nextPageItems.value=items
                }

                if (!doneLoadingPages)
                    offset+=limit()
            })
    }

    fun searchMode() : Boolean = search!=null
}
