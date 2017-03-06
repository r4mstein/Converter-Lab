package ua.r4mstein.converterlab.presentation.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import ua.r4mstein.converterlab.api.RetrofitManager;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected Logger logger;
    protected RetrofitManager retrofitManager;

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract int getFragmentContainerResId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        retrofitManager = RetrofitManager.getInstance();
        logger = LogManager.getLogger();
    }

    protected void addFragment(BaseFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainerResId(), fragment)
                .commit();

        logger.d(TAG, "addFragment");
    }

    protected void addFragmentWithBackStack(BaseFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(getFragmentContainerResId(), fragment)
                .commit();

        logger.d(TAG, "addFragmentWithBackStack");
    }
}
