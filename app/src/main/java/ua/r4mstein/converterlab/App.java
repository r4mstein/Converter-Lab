package ua.r4mstein.converterlab;

import android.app.Application;
import android.os.StrictMode;

import ua.r4mstein.converterlab.services.DataService;

public final class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        detectFails();

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
