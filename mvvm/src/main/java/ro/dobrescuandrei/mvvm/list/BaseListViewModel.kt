package ro.dobrescuandrei.mvvm.list

import androidx.lifecycle.MutableLiveData
import ro.dobrescuandrei.mvvm.BaseViewModel
import ro.dobrescuandrei.mvvm.utils.RESULTS_PER_PAGE
import ro.dobrescuandrei.utils.Run

abstract class BaseListViewModel<FILTER>
(
    var filter : FILTER
) : BaseViewModel()
{
    var search : String? = null
    var limit : Int = RESULTS_PER_PAGE
    var offset : Int = 0

    private var doneLoadingPages : Boolean = false

    val firstPageItems : MutableLiveData<List<Any>> by lazy { MutableLiveData<List<Any>>() }
    val nextPageItems  : MutableLiveData<List<Any>> by lazy { MutableLiveData<List<Any>>() }
    val isEmpty : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun searchMode() : Boolean = search!=null
    abstract fun getItems() : List<Any>
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

        Run.async(task = { getItems() },
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
}
