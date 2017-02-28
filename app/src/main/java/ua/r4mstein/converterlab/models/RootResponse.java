package ua.r4mstein.converterlab.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ua.r4mstein.converterlab.models.cities.City;
import ua.r4mstein.converterlab.models.currencies.Currency;
import ua.r4mstein.converterlab.models.regions.Region;


public class RootResponse {

    @SerializedName("sourceId")
    private String sourceId;

    @SerializedName("date")
    private String date;

    @SerializedName("regions")
    List<Region> regions;

    @SerializedName("cities")
    List<City> cities;

    @SerializedName("currencies")
    List<Currency> currencies;

}