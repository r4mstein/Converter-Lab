package ua.r4mstein.converterlab.util.map_api;

import java.util.List;

public interface IMapApi {

    List<String> getCoordinatesWithApi(String _request);
    String getHTTPData(String _requestURL);
}
