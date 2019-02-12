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
            typeErrorTv.visibility=View.VISIBLE
            typeTv.visibility= View.GONE
            viewModel.isValid=false
        }
        else
        {
            typeErrorTv.visibility=View.GONE
            typeTv.visibility=View.VISIBLE
            typeTv.text=restaurant.getTypeAsString(resources)
        }

        ratingSb.progress=restaurant.rating-1

        if (!nameEt.isFocused)
            nameEt.setText(restaurant.name)

        if (TextUtils.isEmpty(restaurant.name))
        {
            nameTil.error=getString(R.string.type_name)
            viewModel.isValid=false
        }
        else
        {
            nameTil.error=null
        }
    }
}
