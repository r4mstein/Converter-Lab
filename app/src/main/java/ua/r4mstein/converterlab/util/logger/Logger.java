package ua.r4mstein.converterlab.util.logger;


public interface Logger {

    int d(String tag, String msg);

    int d(String tag, String msg, Throwable tr);
}
