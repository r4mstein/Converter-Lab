package ua.r4mstein.converterlab.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

public final class NetworkHelper {

    private static final String TAG = "NetworkHelper";

    public NetworkHelper() {

    }

    public static boolean isOnline(final Context _context) {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        final Logger logger = LogManager.getLogger();

        if (networkInfo != null) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                case ConnectivityManager.TYPE_MOBILE:
                    logger.d(TAG, "isOnline: true");
                    return true;
                default:
                    logger.d(TAG, "isOnline: false");
                    return false;
            }
        } else {
            logger.d(TAG, "isOnline: false");
            return false;
        }
    }
}
