package ua.r4mstein.converterlab.api.models.organizations;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public final class Organization {

    @SerializedName("id")
    public String id;
    @SerializedName("oldId")
    public int oldId;
    @SerializedName("title")
    public String title;
    @SerializedName("regionId")
    public String regionId;
    @SerializedName("cityId")
    public String cityId;
    @SerializedName("phone")
    public String phone;
    @SerializedName("address")
    public String address;
    @SerializedName("link")
    public String link;

    public Map<String, Currency> currencies;

    public static final class Currency {
        @SerializedName("ask")
        public String ask;
        @SerializedName("bid")
        public String bid;
    }
}
