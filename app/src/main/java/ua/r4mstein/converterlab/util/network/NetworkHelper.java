package ua.r4mstein.converterlab.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkHelper {

    public static boolean isOnline(final Context context) {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                case ConnectivityManager.TYPE_MOBILE:
                    return true;
                default:
                    return false;
            }
        } else return false;
    }
}
