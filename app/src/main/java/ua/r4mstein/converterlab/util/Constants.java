package ua.r4mstein.converterlab.util;

public interface Constants {

   String SERVICE_ALARM_MANAGER = "service_alarm_manager";
   String SERVICE_START = "service_start";
   String SERVICE_INIT = "service_init";
   String SERVICE_MESSAGE_KEY = "service_message_key";
   String SERVICE_MESSAGE_SUCCESS = "service_message_success";
   String SERVICE_MESSAGE_ERROR = "service_message_error";
   String SERVICE_TIME_KEY = "service_time_key";
   long SERVICE_HALF_HOUR = 1800 * 1000;
   long SERVICE_ONE_MINUTE = 60 * 1000;

   String DETAIL_FRAGMENT_BUNDLE_KEY = "detail_fragment_bundle_key";
   String DETAIL_FRAGMENT_COLOR_GREEN = "color_green";
   String DETAIL_FRAGMENT_COLOR_RED = "color_red";

   int DETAIL_ADAPTER_ORGANIZATION = 0;
   int DETAIL_ADAPTER_CURRENCY_HEADER = 1;
   int DETAIL_ADAPTER_CURRENCY = 2;

   String DETAIL_DIALOG_BUNDLE_KEY = "detail_dialog_bundle_key";
}
