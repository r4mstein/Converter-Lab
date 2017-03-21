package ua.r4mstein.converterlab.presentation.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected Logger logger;

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract int getFragmentContainerResId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        logger = LogManager.getLogger();
    }

    protected final void addFragment(final BaseFragment _fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainerResId(), _fragment)
                .commit();

        logger.d(TAG, "addFragment: " + _fragment.getClass().getSimpleName());
    }

    protected final void addFragmentWithBackStack(final BaseFragment _fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(getFragmentContainerResId(), _fragment)
                .commit();

        logger.d(TAG, "addFragmentWithBackStack: " + _fragment.getClass().getSimpleName());
    }
}
