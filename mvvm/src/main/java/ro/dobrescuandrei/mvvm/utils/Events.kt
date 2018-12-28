package ro.dobrescuandrei.mvvm.utils

class OnKeyboardOpenedEvent
class OnKeyboardClosedEvent

abstract class OnEditorModel<MODEL> (val model : MODEL)
{
    class AddedEvent        <MODEL>(model : MODEL) : OnEditorModel<MODEL>(model)
    class EditedEvent       <MODEL>(model : MODEL) : OnEditorModel<MODEL>(model)
    class AddedOrEditedEvent<MODEL>(model : MODEL) : OnEditorModel<MODEL>(model)
}
