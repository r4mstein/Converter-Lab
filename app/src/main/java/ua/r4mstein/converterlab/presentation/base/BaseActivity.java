package ua.r4mstein.converterlab.presentation.base;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import ua.r4mstein.converterlab.api.RetrofitManager;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

public abstract class BaseActivity extends AppCompatActivity {

    protected Logger logger;
    protected RetrofitManager retrofitManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        logger = LogManager.getLogger();

        retrofitManager = RetrofitManager.getInstance();
    }
}
