package ua.r4mstein.converterlab.util.map_api;

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

import ua.r4mstein.converterlab.util.logger.LogManager;

public final class MapApi implements IMapApi {

    private static final String TAG = "MapApi";

    @Override
    public List<String> getCoordinates(String request) {
        LogManager.getLogger().d(TAG, "getCoordinates");
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
}
