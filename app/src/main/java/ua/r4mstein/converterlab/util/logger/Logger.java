package ua.r4mstein.converterlab.util.logger;


public interface Logger {

    int d(String _tag, String _msg);

    int d(String _tag, String _msg, Throwable _tr);
}
