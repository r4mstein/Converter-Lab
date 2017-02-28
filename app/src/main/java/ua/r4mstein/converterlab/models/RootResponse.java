package ua.r4mstein.converterlab.models;

import com.google.gson.annotations.SerializedName;


public class RootResponse {

    @SerializedName("sourceId")
    private String sourceId;

    @SerializedName("date")
    private String date;

    @SerializedName("regions")
    Object regionList;

}