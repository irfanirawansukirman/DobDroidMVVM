package ro.dobrescuandrei.mvvm;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public abstract class JBaseFragment<VIEW_MODEL extends BaseViewModel> extends Fragment
{
    public abstract Class<VIEW_MODEL> viewModelClass();

    public VIEW_MODEL getViewModel()
    {
        return ViewModelProviders.of(this).get(viewModelClass());
    }
}
