package ua.r4mstein.converterlab.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment<A extends BaseActivity> extends Fragment {

    @LayoutRes
    protected abstract int getLayoutResId();

    @SuppressWarnings("unchecked")
    protected A getActivityGeneric(){
        return (A) getActivity();
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }
}
