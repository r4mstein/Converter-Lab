package ua.r4mstein.converterlab.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import ua.r4mstein.converterlab.api.RetrofitManager;
import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.database.DataSource;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.converter.Converter;
import ua.r4mstein.converterlab.util.converter.IConverter;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

import static ua.r4mstein.converterlab.util.Constants.SERVICE_ALARM_MANAGER;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_HALF_HOUR;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_INIT;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_ERROR;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_KEY;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_SUCCESS;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_ONE_MINUTE;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_START;

public class DataService extends Service {

    private static final String TAG = "DataService";
    private static final long HALF_HOUR = 60 * 1000;
//    private static final long HALF_HOUR = 1800 * 1000;

    private Logger mLogger;
    private RetrofitManager mRetrofitManager;
    private DataSource mDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        mLogger = LogManager.getLogger();
        mDataSource = new DataSource(this);
        mRetrofitManager = RetrofitManager.getInstance();
        mRetrofitManager.init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public DataService() {
        super();
    }

    public void setServiceAlarm(Context context, long interval) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(SERVICE_ALARM_MANAGER);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + interval, interval, pendingIntent);

        LogManager.getLogger().d(TAG, "setServiceAlarm");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            switch (intent.getAction()) {
                case SERVICE_START:
                    loadDataFromServer();
                    break;
                case SERVICE_ALARM_MANAGER:
                    loadDataFromServer();
                    break;
                case SERVICE_INIT:
                    break;
            }
        }

        mLogger.d(TAG, "onStartCommand" + intent.getAction());

        return super.onStartCommand(intent, flags, startId);
    }

    public void loadDataFromServer() {

        mRetrofitManager.getResponse(new RetrofitManager.RCallback() {
            @Override
            public void onSuccess(RootResponse response) {

                IConverter converter = new Converter();

                converter.convert(response);
                List<OrganizationModel> organizationModels = converter.getOrganizationModels();
                List<CurrenciesModel> currenciesModels = converter.getCurrencies();

                mDataSource.open();
                mLogger.d(TAG, "DataSource open");

                mDataSource.insertOrUpdateOrganizations(organizationModels);
                mDataSource.insertOrUpdateCurrencies(currenciesModels);

                mDataSource.close();
                mLogger.d(TAG, "DataSource close");

                sendMessage(SERVICE_MESSAGE_SUCCESS);
                setServiceAlarm(DataService.this, SERVICE_ONE_MINUTE);

                mLogger.d(TAG, "loadDataFromServer: onSuccess");
            }

            @Override
            public void onError(String message) {
                sendMessage(SERVICE_MESSAGE_ERROR);
                setServiceAlarm(DataService.this, SERVICE_ONE_MINUTE);
                mLogger.d(TAG, "loadDataFromServer: onError");
            }
        });
    }

    private void sendMessage(String message) {
        Intent intent = new Intent("DataService");
        intent.putExtra(SERVICE_MESSAGE_KEY, message);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLogger.d(TAG, "onDestroy");
    }
}
