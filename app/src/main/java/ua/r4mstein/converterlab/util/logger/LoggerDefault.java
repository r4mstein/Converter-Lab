package ua.r4mstein.converterlab.util.logger;


import android.util.Log;

public final class LoggerDefault implements Logger {

    @Override
    public int d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    @Override
    public int d(String tag, String msg, Throwable tr) {
        return Log.d(tag, msg, tr);
    }
}
