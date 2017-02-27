package ua.r4mstein.converterlab.models;


import android.content.ContentValues;

import ua.r4mstein.converterlab.database.DBContract.HomeEntry;

public class HomeModel {

    private String id;
    private int oldId;
    private int orgType;
    private String title;
    private String regionId;
    private String cityId;
    private String phone;
    private String address;
    private String link;

    public ContentValues toValues() {
        ContentValues values = new ContentValues(9);

        values.put(HomeEntry.COLUMN_ID, id);
        values.put(HomeEntry.COLUMN_OLD_ID, oldId);
        values.put(HomeEntry.COLUMN_ORG_TYPE, orgType);
        values.put(HomeEntry.COLUMN_TITLE, title);
        values.put(HomeEntry.COLUMN_REGION_ID, regionId);
        values.put(HomeEntry.COLUMN_CITY_ID, cityId);
        values.put(HomeEntry.COLUMN_PHONE, phone);
        values.put(HomeEntry.COLUMN_ADDRESS, address);
        values.put(HomeEntry.COLUMN_LINK, link);

        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOldId() {
        return oldId;
    }

    public void setOldId(int oldId) {
        this.oldId = oldId;
    }

    public int getOrgType() {
        return orgType;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
