package ua.r4mstein.converterlab.util.map_api;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.network.NetworkHelper;

public final class MapApi implements IMapApi {

    private static final String TAG = "MapApi";

    private MapApiCallback mMapApiCallback;

    public void setMapApiCallback(MapApiCallback mapApiCallback) {
        mMapApiCallback = mapApiCallback;
    }

    @Override
    public List<String> getCoordinatesWithApi(String request) {
        LogManager.getLogger().d(TAG, "getCoordinatesWithApi");
        List<String> result = new ArrayList<>();

        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s", request);
        LogManager.getLogger().d(TAG, url);
        String response = getHTTPData(url);

        try {
            JSONObject jsonObject = new JSONObject(response);

            String lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lat").toString();

            String lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lng").toString();

            result.add(lat);
            result.add(lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String getHTTPData(String requestURL) {
        LogManager.getLogger().d(TAG, "getHTTPData");
        URL url;
        String response = "";

        try {
            url = new URL(requestURL);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                int responseCode = connection.getResponseCode();
                LogManager.getLogger().d(TAG, "responseCode: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    while ((line = reader.readLine()) != null) {
                        response += line;
                    }
                    reader.close();
                } else response = "";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void getCoordinates(final String request, Context context) {

        final Handler handler = new Handler(Looper.getMainLooper());

        final List<Double> result = new ArrayList<>();
        Geocoder geocoder = new Geocoder(context);

        if (NetworkHelper.isOnline(context)) {
            try {
                List<Address> address = geocoder.getFromLocationName(request, 5);

                if (address != null && !address.isEmpty()) {
                    result.add(address.get(0).getLatitude());
                    result.add(address.get(0).getLongitude());

                    if (mMapApiCallback != null) mMapApiCallback.onSuccess(result);
                    LogManager.getLogger().d(TAG, "onSuccess -- Geocoder");
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = request.replace(" ", "+");
                            List<String> coordinates = getCoordinatesWithApi(url);

                            if (coordinates != null && !coordinates.isEmpty()) {
                                result.add(Double.parseDouble(coordinates.get(0)));
                                result.add(Double.parseDouble(coordinates.get(1)));

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        LogManager.getLogger().d(TAG, "onSuccess -- API");
                                        if (mMapApiCallback != null)
                                            mMapApiCallback.onSuccess(result);
                                    }
                                });

                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mMapApiCallback != null)
                                            mMapApiCallback.onError("Not found coordinates");
                                    }
                                });
                            }
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Internet not available", Toast.LENGTH_SHORT).show();
        }
    }

    public String getRequest(OrganizationModel model) {
        return model.getRegion().trim() + " " + model.getCity().trim() + " " +
                model.getAddress().trim();
    }

    public interface MapApiCallback {

        void onSuccess(List<Double> coordinates);

        void onError(String message);
    }
}
