package ro.dobrescuandrei.mvvm.editor

import androidx.lifecycle.MutableLiveData
import ro.dobrescuandrei.mvvm.BaseViewModel
import ro.dobrescuandrei.mvvm.R
import ro.dobrescuandrei.mvvm.utils.ForegroundEventBus
import ro.dobrescuandrei.mvvm.utils.Identifiable
import ro.dobrescuandrei.mvvm.utils.OnEditorModel
import ro.dobrescuandrei.utils.Run

abstract class BaseEditorViewModel<MODEL : Identifiable<ID>, ID> : BaseViewModel
{
    @PublishedApi
    internal val model : MutableLiveData<MODEL> by lazy { MutableLiveData<MODEL>() }

    @PublishedApi
    internal var shouldNotifyModelLiveDataOnPropertyChange : Boolean = true

    var isValid : Boolean = false

    open fun  addMode() = model.value?.id==null
    open fun editMode() = model.value?.id!=null

    abstract fun add (model : MODEL) : ID
    abstract fun edit(model : MODEL)

    open fun provideErrorMessage(ex : Exception? = null) = R.string.you_have_errors_please_correct

    constructor(model : MODEL)
    {
        this.model.value=model
    }

    inline operator fun rangeTo(consumer : MODEL.(MODEL) -> (Unit))
    {
        model.value?.let { model ->
            consumer(model, model)

            if (shouldNotifyModelLiveDataOnPropertyChange)
            {
                this.model.value=model
            }
        }
    }

    fun onSaveButtonClicked()
    {
        if (isValid)
        {
            loading.value=true

            model.value?.let { model ->
                val addMode=addMode()
                Run.async(task = {
                    if (addMode)
                        model.id=add(model)
                    else edit(model)
                },
                onAny = {
                    loading.value=false
                },
                onError = { exception ->
                    error.value=provideErrorMessage(exception)
                },
                onSuccess = {
                    ForegroundEventBus.post(if (addMode)
                         OnEditorModel.AddedEvent(model)
                    else OnEditorModel.EditedEvent(model))

                    ForegroundEventBus.post(OnEditorModel.AddedOrEditedEvent(model))
                })
            }
        }
        else
        {
            error.value=provideErrorMessage()
        }
    }
}
