package ua.r4mstein.converterlab.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ua.r4mstein.converterlab.util.logger.LogManager;

public final class NetworkHelper {

    private static final String TAG = "NetworkHelper";

    public static boolean isOnline(final Context context) {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                case ConnectivityManager.TYPE_MOBILE:
                    LogManager.getLogger().d(TAG, "isOnline: true");
                    return true;
                default:
                    LogManager.getLogger().d(TAG, "isOnline: false");
                    return false;
            }
        } else {
            LogManager.getLogger().d(TAG, "isOnline: false");
            return false;
        }
    }
}
