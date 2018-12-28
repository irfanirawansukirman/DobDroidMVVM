package ro.dobrescuandrei.demonewlibs.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_restaurant_editor.*
import org.greenrobot.eventbus.Subscribe
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.RefreshRestaurantListCommand
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.router.ShowDialog
import ro.dobrescuandrei.demonewlibs.viewmodel.RestaurantEditorViewModel
import ro.dobrescuandrei.mvvm.editor.BaseEditorActivity
import ro.dobrescuandrei.mvvm.utils.BackgroundEventBus
import ro.dobrescuandrei.mvvm.utils.NO_VALUE_INT
import ro.dobrescuandrei.mvvm.utils.OnEditorModel
import ro.dobrescuandrei.utils.setOnTextChangedListener
import ro.dobrescuandrei.utils.setupBackIcon

class RestaurantEditorActivity : BaseEditorActivity<Restaurant, RestaurantEditorViewModel>()
{
    override fun viewModelClass() = RestaurantEditorViewModel::class.java
    override fun layout() : Int = R.layout.activity_restaurant_editor

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        toolbar.setupBackIcon()
        toolbar.setTitle(if (viewModel().addMode())
                R.string.add_restaurant
            else R.string.edit_restaurant)

        typeButton.setOnClickListener {
            ShowDialog.withList(context = this,
                title = R.string.choose_type,
                onClick = { index, value ->
                    viewModel().model { type=index+1 }
                },
                values = listOf(
                    getString(R.string.normal),
                    getString(R.string.fast_food)))
        }

        ratingSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {
                viewModel().model { rating=progress+1 }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?)
            {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?)
            {
            }
        })

        nameEt.setOnTextChangedListener { name ->
            viewModel().model { it.name=name }
        }
    }

    override fun show(restaurant : Restaurant)
    {
        viewModel().isValid=true

        if (restaurant.type== NO_VALUE_INT)
        {
            typeErrorTv.visibility=View.VISIBLE
            typeTv.visibility=View.GONE
            viewModel().isValid=false
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
            viewModel().isValid=false
            nameTil.error=getString(R.string.type_name)
        }
        else
        {
            nameTil.error=null
        }
    }

    @Subscribe
    override fun onAdded(event : OnEditorModel.AddedEvent<Restaurant>)
    {
        showToast(R.string.restaurant_added)
    }

    @Subscribe
    override fun onEdited(event : OnEditorModel.EditedEvent<Restaurant>)
    {
        showToast(R.string.restaurant_edited)
    }

    @Subscribe
    override fun onAddedOrEdited(event: OnEditorModel.AddedOrEditedEvent<Restaurant>)
    {
        BackgroundEventBus.post(RefreshRestaurantListCommand())
        finish()
    }
}