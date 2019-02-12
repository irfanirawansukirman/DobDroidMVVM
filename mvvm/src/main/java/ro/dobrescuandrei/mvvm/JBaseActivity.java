package ro.dobrescuandrei.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public abstract class JBaseActivity<VIEW_MODEL extends BaseViewModel> extends AppCompatActivity
{
    public abstract Class<VIEW_MODEL> viewModelClass();

    public VIEW_MODEL getViewModel()
    {
        return ViewModelProviders.of(this).get(viewModelClass());
    }
}
