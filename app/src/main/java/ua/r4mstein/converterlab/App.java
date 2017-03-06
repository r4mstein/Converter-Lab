package ua.r4mstein.converterlab;

import android.app.Application;
import android.os.StrictMode;

import ua.r4mstein.converterlab.services.DataService;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

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

        mLogger.d(TAG, "setServiceAlarm");
        DataService.setServiceAlarm(this);
    }

    /**
     * See logs with tag: StrictMode
     */
    private void detectFails() {
        if (BuildConfig.DEBUG) {
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
