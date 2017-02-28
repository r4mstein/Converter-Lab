package ua.r4mstein.converterlab.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import ua.r4mstein.converterlab.models.regions.Region;


public class RootResponse {

    @SerializedName("sourceId")
    private String sourceId;

    @SerializedName("date")
    private String date;

    @SerializedName("regions")
    List<Region> regions;

}