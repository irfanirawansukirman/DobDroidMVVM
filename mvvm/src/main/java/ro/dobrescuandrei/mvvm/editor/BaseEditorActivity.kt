package ro.dobrescuandrei.mvvm.editor

import android.os.Bundle
import android.widget.Button
import ro.dobrescuandrei.mvvm.BaseActivity
import ro.dobrescuandrei.mvvm.R
import ro.dobrescuandrei.mvvm.utils.ARG_MODEL
import ro.dobrescuandrei.mvvm.utils.Identifiable
import ro.dobrescuandrei.mvvm.utils.OnEditorModel

abstract class BaseEditorActivity<MODEL : Identifiable<*>, VIEW_MODEL : BaseEditorViewModel<MODEL, *>> : BaseActivity<VIEW_MODEL>()
{
    lateinit var saveButton : Button

    override fun loadDataFromIntent()
    {
        (intent?.extras?.getSerializable(ARG_MODEL) as? MODEL)?.let { modelToEdit ->
            viewModel.model.value=modelToEdit
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        saveButton=findViewById(R.id.saveButton)
        saveButton.setOnClickListener { viewModel.onSaveButtonClicked() }

        viewModel.model.observe(this) { model ->
            if (model!=null)
            {
                viewModel.shouldNotifyModelLiveDataOnPropertyChange=false
                show(model)
                viewModel.shouldNotifyModelLiveDataOnPropertyChange=true
            }
        }
    }

    abstract fun show(model : MODEL)

    abstract fun onAdded(event : OnEditorModel.AddedEvent<MODEL>)
    abstract fun onEdited(event : OnEditorModel.EditedEvent<MODEL>)
    abstract fun onAddedOrEdited(event: OnEditorModel.AddedOrEditedEvent<MODEL>)
}
