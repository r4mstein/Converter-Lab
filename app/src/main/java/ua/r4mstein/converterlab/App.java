package ua.r4mstein.converterlab;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by r4mst on 28.02.2017.
 */

public final class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        detectFails();
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
