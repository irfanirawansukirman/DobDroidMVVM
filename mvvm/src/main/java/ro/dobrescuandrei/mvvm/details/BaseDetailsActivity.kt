package ro.dobrescuandrei.mvvm.details

import ro.andreidobrescu.declarativeadapterkt.BaseDeclarativeAdapter
import ro.dobrescuandrei.mvvm.list.BaseListActivity
import ro.dobrescuandrei.mvvm.utils.ARG_MODEL
import ro.dobrescuandrei.mvvm.utils.Identifiable

abstract class BaseDetailsActivity<MODEL : Identifiable<*>, VIEW_MODEL : BaseDetailsViewModel<MODEL>, ADAPTER : BaseDeclarativeAdapter> : BaseListActivity<VIEW_MODEL, ADAPTER>()
{
    override fun loadDataFromIntent()
    {
        intent?.extras?.getSerializable(ARG_MODEL)?.let { model ->
            viewModel().model=model as MODEL
        }
    }

    override fun shouldLoadMoreOnScroll() : Boolean = false
    override fun hasStickyHeaders() : Boolean = true
}