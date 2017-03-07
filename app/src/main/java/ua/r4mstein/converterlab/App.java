package ua.r4mstein.converterlab;

import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;

import ua.r4mstein.converterlab.services.DataService;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

import static ua.r4mstein.converterlab.util.Constants.SERVICE_INIT;

public final class App extends Application {

    private static final String TAG = "App";

    private final Logger mLogger;

    public App() {
        super();
        mLogger = LogManager.getLogger();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLogger.d(TAG, "onCreate");

        detectFails();

        mLogger.d(TAG, "SERVICE_INIT");
        Intent intent = new Intent(this, DataService.class);
        intent.setAction(SERVICE_INIT);
        startService(intent);
    }

    /**
     * See logs with tag: StrictMode
     * https://developer.android.com/reference/android/os/StrictMode.html
     */
    private void detectFails() {
        if (BuildConfig.DEBUG) {
            mLogger.d(TAG, "detectFails");
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

}
