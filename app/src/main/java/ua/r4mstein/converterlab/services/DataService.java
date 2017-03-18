package ua.r4mstein.converterlab.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.api.RetrofitManager;
import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.database.DataSource;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.converter.Converter;
import ua.r4mstein.converterlab.util.converter.IConverter;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;
import ua.r4mstein.converterlab.util.network.NetworkHelper;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_FRAGMENT_COLOR_GREEN;
import static ua.r4mstein.converterlab.util.Constants.DETAIL_FRAGMENT_COLOR_RED;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_ALARM_MANAGER;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_HALF_HOUR;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_INIT;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_ERROR;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_KEY;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_SUCCESS;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_ONE_MINUTE;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_SAVED_DATE_KEY;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_SHARED_PREFERENCES_KEY;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_START;

public final class DataService extends Service {

    private static final String TAG = "DataService";

    private Logger mLogger;
    private RetrofitManager mRetrofitManager;
    private DataSource mDataSource;
    private AlarmManager mAlarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mLogger.d(TAG, "onCreate");
        mDataSource = new DataSource(this);
        mRetrofitManager = RetrofitManager.getInstance();
        mRetrofitManager.init();
        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public DataService() {
        super();
        mLogger = LogManager.getLogger();
    }

    public void setServiceAlarm(Context context, long interval) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(SERVICE_ALARM_MANAGER);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + interval, interval, pendingIntent);

        mLogger.d(TAG, "setServiceAlarm: with interval: " + interval);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            mLogger.d(TAG, "onStartCommand: intent_action: " + intent.getAction());
            switch (intent.getAction()) {
                case SERVICE_START:
                    sendNotification(this, "Start data download...");
                    if (NetworkHelper.isOnline(this)) {
                        loadDataFromServer();
                    } else {
                        sendNotification(this, "Internet not available");
                        resetDataServiceAlarm(DataService.this, mAlarmManager);
                        setServiceAlarm(DataService.this, SERVICE_ONE_MINUTE);
                        mLogger.d(TAG, "onStartCommand: SERVICE_START: Internet not available");
                    }
                    stopSelf();
                    break;
                case SERVICE_ALARM_MANAGER:
                    sendNotification(this, "Start data download...");
                    if (NetworkHelper.isOnline(this)) {
                        loadDataFromServer();
                    } else {
                        sendNotification(this, "Internet not available");
                        resetDataServiceAlarm(DataService.this, mAlarmManager);
                        setServiceAlarm(DataService.this, SERVICE_ONE_MINUTE);
                        mLogger.d(TAG, "onStartCommand: SERVICE_ALARM_MANAGER: Internet not available");
                    }
                    stopSelf();
                    break;
                case SERVICE_INIT:
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    public void loadDataFromServer() {

        mRetrofitManager.getResponse(new RetrofitManager.RCallback() {
            @Override
            public void onSuccess(RootResponse response) {

                SharedPreferences preferences = DataService.this.getSharedPreferences(
                        SERVICE_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date newDate = null;
                try {
                    newDate = dateFormat.parse(response.getDate());
                    long dateFromPreferences = preferences.getLong(SERVICE_SAVED_DATE_KEY, 0);
                    mLogger.d(TAG, "loadDataFromServer: newDate: " + newDate);
                    mLogger.d(TAG, "loadDataFromServer: newDate: " + newDate.getTime());
                    mLogger.d(TAG, "loadDataFromServer: dateFromPreferences: " + dateFromPreferences);

                    if (dateFromPreferences != 0 && newDate.getTime() == dateFromPreferences) {
                        sendMessage(SERVICE_MESSAGE_SUCCESS);
                        sendNotification(DataService.this, "The data was not updated on the server.");
                        mLogger.d(TAG, "loadDataFromServer: The date was not changed");

                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    mLogger.d(TAG, "loadDataFromServer: ParseException: " + e.getMessage());
                }

                if (newDate != null) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong(SERVICE_SAVED_DATE_KEY, newDate.getTime());
                    editor.apply();
                }

                IConverter converter = new Converter();
                converter.convert(response);
                List<OrganizationModel> organizationModels = converter.getOrganizationModels();
                List<CurrenciesModel> currenciesModels = converter.getCurrencies();

                mDataSource.open();
                mLogger.d(TAG, "loadDataFromServer: DataSource open");

                resetDataServiceAlarm(DataService.this, mAlarmManager);

                List<CurrenciesModel> currenciesModelsFromDB = mDataSource.getAllCurrenciesItems();
                for (CurrenciesModel model : currenciesModels) {
                    for (CurrenciesModel modelDB : currenciesModelsFromDB) {
                        if (model.getId().equals(modelDB.getId())) {

                            if (Double.parseDouble(model.getAsk()) >= Double.parseDouble(modelDB.getAsk())) {
                                model.setAsk_color(DETAIL_FRAGMENT_COLOR_GREEN);
                            } else {
                                model.setAsk_color(DETAIL_FRAGMENT_COLOR_RED);
                            }

                            if (Double.parseDouble(model.getBid()) >= Double.parseDouble(modelDB.getBid())) {
                                model.setBid_color(DETAIL_FRAGMENT_COLOR_GREEN);
                            } else {
                                model.setBid_color(DETAIL_FRAGMENT_COLOR_RED);
                            }
                        }
                    }
                }

                mDataSource.insertOrUpdateOrganizations(organizationModels);
                mDataSource.insertOrUpdateCurrencies(currenciesModels);

                mDataSource.close();
                mLogger.d(TAG, "loadDataFromServer: DataSource close");

                sendMessage(SERVICE_MESSAGE_SUCCESS);

                setServiceAlarm(DataService.this, SERVICE_HALF_HOUR);
                sendNotification(DataService.this, "Data downloaded.");
                mLogger.d(TAG, "loadDataFromServer: onSuccess");
            }

            @Override
            public void onError(String message) {
                sendMessage(SERVICE_MESSAGE_ERROR);
                setServiceAlarm(DataService.this, SERVICE_ONE_MINUTE);
                sendNotification(DataService.this, "Error data download.");
                mLogger.d(TAG, "loadDataFromServer: onError");
            }
        });
    }

    private boolean resetDataServiceAlarm(Context context, AlarmManager alarmManager) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(SERVICE_ALARM_MANAGER);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,
                PendingIntent.FLAG_NO_CREATE);

        boolean result = pendingIntent != null;
        if (result) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
        mLogger.d(TAG, "resetDataServiceAlarm: AlarmManager exists: " + result);

        return result;
    }

    private void sendMessage(String message) {
        Intent intent = new Intent("DataService");
        intent.putExtra(SERVICE_MESSAGE_KEY, message);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        mLogger.d(TAG, "sendMessage: " + message);
    }

    private void sendNotification(Context context, String message) {
        Resources resources = context.getResources();

        Notification notification = new NotificationCompat.Builder(context)
                .setTicker(message)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(resources.getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(100, notification);

        mLogger.d(TAG, "sendNotification: with message: " + message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLogger.d(TAG, "onDestroy");
    }
}
