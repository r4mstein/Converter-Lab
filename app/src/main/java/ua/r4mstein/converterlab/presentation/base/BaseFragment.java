package ua.r4mstein.converterlab.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.r4mstein.converterlab.presentation.fragments.DetailFragment;


public abstract class BaseFragment<A extends BaseActivity> extends Fragment {

    @LayoutRes
    protected abstract int getLayoutResId();

    @SuppressWarnings("unchecked")
    protected A getActivityGeneric(){
        return (A) getActivity();
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container,
                             final Bundle _savedInstanceState) {
        return _inflater.inflate(getLayoutResId(), _container, false);
    }
}
