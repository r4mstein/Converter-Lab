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
import ua.r4mstein.converterlab.util.logger.Logger;
import ua.r4mstein.converterlab.util.network.NetworkHelper;

public final class MapApi implements IMapApi {

    private static final String TAG = "MapApi";

    private MapApiCallback mMapApiCallback;
    private Logger mLogger;

    public MapApi() {
        mLogger = LogManager.getLogger();
    }

    public final void setMapApiCallback(final MapApiCallback _mapApiCallback) {
        mMapApiCallback = _mapApiCallback;
    }

    @Override
    public List<String> getCoordinatesWithApi(final String _request) {
        mLogger.d(TAG, "getCoordinatesWithApi: request: " + _request);
        List<String> result = new ArrayList<>();

        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s", _request);
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
    public String getHTTPData(final String _requestURL) {
        mLogger.d(TAG, "getHTTPData: URL: " + _requestURL);
        URL url;
        String response = "";

        try {
            url = new URL(_requestURL);
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

    public final void getCoordinates(final String _request, final Context _context) {

        final Handler handler = new Handler(Looper.getMainLooper());

        final List<Double> result = new ArrayList<>();
        Geocoder geocoder = new Geocoder(_context);

        if (NetworkHelper.isOnline(_context)) {
            try {
                List<Address> address = geocoder.getFromLocationName(_request, 5);

                if (address != null && !address.isEmpty()) {
                    result.add(address.get(0).getLatitude());
                    result.add(address.get(0).getLongitude());

                    if (mMapApiCallback != null) mMapApiCallback.onSuccess(result);
                    mLogger.d(TAG, "getCoordinates: Success/Geocoder: coordinates: "
                            + address.get(0).getLatitude() + " - " + address.get(0).getLongitude());
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = _request.replace(" ", "+");
                            final List<String> coordinates = getCoordinatesWithApi(url);

                            if (coordinates != null && !coordinates.isEmpty()) {
                                result.add(Double.parseDouble(coordinates.get(0)));
                                result.add(Double.parseDouble(coordinates.get(1)));

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mLogger.d(TAG, "getCoordinates: Success/API: coordinates: "
                                                + coordinates.get(0) + " - " + coordinates.get(1));
                                        if (mMapApiCallback != null)
                                            mMapApiCallback.onSuccess(result);
                                    }
                                });

                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mLogger.d(TAG,
                                                "getCoordinates: Error: Not found coordinates");
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
            Toast.makeText(_context, "Internet not available", Toast.LENGTH_SHORT).show();
        }
    }

    public final String getRequest(final OrganizationModel _model) {
        return _model.getRegion().trim() + " " + _model.getCity().trim() + " " +
                _model.getAddress().trim();
    }

    public interface MapApiCallback {

        void onSuccess(List<Double> coordinates);

        void onError(String message);
    }
}
