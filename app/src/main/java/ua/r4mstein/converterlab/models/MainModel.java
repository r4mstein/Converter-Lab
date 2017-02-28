package ua.r4mstein.converterlab.models;


import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;


public class MainModel {

    @SerializedName("regions")
    JsonArray cityList;

    public final static class City {
        public String id;
        public String name;
    }

    @SerializedName("sourceId")
    private String sourceId;

    @SerializedName("date")
    private String date;

}