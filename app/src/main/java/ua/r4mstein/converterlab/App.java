package ua.r4mstein.converterlab;

import android.app.Application;

import ua.r4mstein.converterlab.services.DataService;

public final class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DataService.setServiceAlarm(this);
    }

}
