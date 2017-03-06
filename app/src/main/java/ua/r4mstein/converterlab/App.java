package ua.r4mstein.converterlab;

import android.app.Application;
import android.content.Intent;

import ua.r4mstein.converterlab.services.DataService;

import static ua.r4mstein.converterlab.util.Constants.SERVICE_INIT;

public final class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, DataService.class);
        intent.setAction(SERVICE_INIT);
        startService(intent);
    }

}
