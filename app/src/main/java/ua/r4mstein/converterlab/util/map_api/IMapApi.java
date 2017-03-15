package ua.r4mstein.converterlab.util.map_api;

import java.util.List;

public interface IMapApi {

    List<String> getCoordinates(String request);
    String getHTTPData(String requestURL);
}
