package ua.r4mstein.converterlab.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ua.r4mstein.converterlab.models.cities.City;
import ua.r4mstein.converterlab.models.currencies.Currency;
import ua.r4mstein.converterlab.models.organizations.Organization;
import ua.r4mstein.converterlab.models.regions.Region;

public class RootResponse {

    @SerializedName("sourceId")
    private String sourceId;

    @SerializedName("date")
    private String date;

    @SerializedName("regions")
    private List<Region> regions;

    @SerializedName("cities")
    private List<City> cities;

    @SerializedName("currencies")
    private List<Currency> currencies;

    @SerializedName("organizations")
    private List<Organization> organizations;

    public String getSourceId() {
        return sourceId;
    }

    public String getDate() {
        return date;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public List<City> getCities() {
        return cities;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }
}