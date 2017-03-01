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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitManager = RetrofitManager.getInstance();
        logger = LogManager.getLogger();
    }
}
