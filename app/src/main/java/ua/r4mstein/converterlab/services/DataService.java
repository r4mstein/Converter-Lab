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
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public DataService() {
        super();
    }

    public static void setServiceAlarm(Context context) {
        Intent intent = new Intent(context, DataService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(), HALF_HOUR, pendingIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mRetrofitManager.init();

        parseData(new DataCallback() {
            @Override
            public void onSuccess(String message) {
                sendMessage();

            }

            @Override
            public void onError(String message) {

            }
        });

        mLogger.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    public void parseData(final DataCallback callback) {

        mRetrofitManager.getResponse(new RetrofitManager.RCallback() {
            @Override
            public void onSuccess(RootResponse response) {

                IConverter converter = new Converter();

                converter.convert(response);
                List<OrganizationModel> organizationModels = converter.getOrganizationModels();
                List<CurrenciesModel> currenciesModels = converter.getCurrencies();

                mDataSource.insertOrUpdateOrganizations(organizationModels);
                mDataSource.insertOrUpdateCurrencies(currenciesModels);

                callback.onSuccess("Success");
                mLogger.d(TAG, "parseData: onSuccess");
            }

            @Override
            public void onError(String message) {
                callback.onError("Error");
                mLogger.d(TAG, "parseData: onError");
            }
        });
    }

    private void sendMessage() {
        Intent intent = new Intent("DataService");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
   }

    public interface DataCallback {

        void onSuccess(String message);

        void onError(String message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
