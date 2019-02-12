package ro.dobrescuandrei.demonewlibs.restaurant.editor

import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_restaurant_editor.*
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.mvvm.editor.BaseEditorActivity
import ro.dobrescuandrei.mvvm.utils.NO_VALUE_INT

abstract class RestaurantEditorAdapter : BaseEditorActivity<Restaurant, RestaurantEditorViewModel>()
{
    override fun viewModelClass() = RestaurantEditorViewModel::class.java
    override fun layout() : Int = R.layout.activity_restaurant_editor

    override fun show(restaurant : Restaurant)
    {
        viewModel.isValid=true

        if (restaurant.type== NO_VALUE_INT)
        {
            typeErrorLabel.visibility=View.VISIBLE
            typeLabel.visibility= View.GONE
            viewModel.isValid=false
        }
        else
        {
            typeErrorLabel.visibility=View.GONE
            typeLabel.visibility=View.VISIBLE
            typeLabel.text=restaurant.getTypeAsString(resources)
        }

        ratingSeekBar.progress=restaurant.rating-1

        if (!nameEditText.isFocused)
            nameEditText.setText(restaurant.name)

        if (TextUtils.isEmpty(restaurant.name))
        {
            nameTextInput.error=getString(R.string.type_name)
            viewModel.isValid=false
        }
        else
        {
            nameTextInput.error=null
        }
    }
}
