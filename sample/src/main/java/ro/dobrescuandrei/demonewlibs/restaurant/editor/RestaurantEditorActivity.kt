package ro.dobrescuandrei.demonewlibs.restaurant.editor

import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_restaurant_editor.*
import org.greenrobot.eventbus.Subscribe
import ro.dobrescuandrei.demonewlibs.R
import ro.dobrescuandrei.demonewlibs.model.Restaurant
import ro.dobrescuandrei.demonewlibs.model.utils.OnRestaurantAddedEvent
import ro.dobrescuandrei.demonewlibs.model.utils.OnRestaurantChoosedEvent
import ro.dobrescuandrei.demonewlibs.model.utils.RefreshRestaurantListCommand
import ro.dobrescuandrei.demonewlibs.router.ActivityRouter
import ro.dobrescuandrei.demonewlibs.router.ShowDialog
import ro.dobrescuandrei.mvvm.utils.BackgroundEventBus
import ro.dobrescuandrei.mvvm.utils.OnEditorModel
import ro.dobrescuandrei.utils.setOnTextChangedListener
import ro.dobrescuandrei.utils.setupBackIcon

class RestaurantEditorActivity : RestaurantEditorAdapter()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        toolbar.setupBackIcon()
        toolbar.setTitle(if (viewModel.addMode())
                R.string.add_restaurant
            else R.string.edit_restaurant)

        typeButton.setOnClickListener {
            ShowDialog.withList(context = this,
                title = R.string.choose_type,
                onClick = { index, value ->
                    viewModel..{ type=index+1 }
                },
                values = listOf(
                    getString(R.string.normal),
                    getString(R.string.fast_food)))
        }

        ratingSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {
                viewModel..{ rating=progress+1 }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?)
            {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?)
            {
            }
        })

        nameEditText.setOnTextChangedListener { name ->
            viewModel..{ it.name=name }
        }

        setupChooserDemo()
    }

    @Subscribe
    override fun onAdded(event : OnEditorModel.AddedEvent<Restaurant>)
    {
        BackgroundEventBus.post(OnRestaurantAddedEvent(restaurant = event.model))
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

    //chooser demo zone
    fun setupChooserDemo()
    {
        simpleChooseDemoButton.setOnClickListener {
            ActivityRouter.startChooseRestaurantActivity(from = it.context)
        }

        pagedChooseDemoButton.setOnClickListener {
            ActivityRouter.startChoosePagedRestaurantActivity(from = it.context)
        }
    }

    @Subscribe
    fun onRestaurantChoosed(event : OnRestaurantChoosedEvent)
    {
        println(event.restaurant.toString())
    }
}