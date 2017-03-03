package ua.r4mstein.converterlab.presentation.ui_models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import ua.r4mstein.converterlab.database.DBContract.OrganizationEntry;

public final class OrganizationModel implements Parcelable {

    private String id;
    private String title;
    private String region;
    private String city;
    private String phone;
    private String address;
    private String link;
    private String[] currencyId;



    public ContentValues toValues() {
        ContentValues values = new ContentValues();

        values.put(OrganizationEntry.COLUMN_ID, id);
        values.put(OrganizationEntry.COLUMN_TITLE, title);
        values.put(OrganizationEntry.COLUMN_REGION, region);
        values.put(OrganizationEntry.COLUMN_CITY, city);
        values.put(OrganizationEntry.COLUMN_PHONE, phone);
        values.put(OrganizationEntry.COLUMN_ADDRESS, address);
        values.put(OrganizationEntry.COLUMN_LINK, link);

        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String[] currencyId) {
        this.currencyId = currencyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.region);
        dest.writeString(this.city);
        dest.writeString(this.phone);
        dest.writeString(this.address);
        dest.writeString(this.link);
        dest.writeStringArray(this.currencyId);
    }

    public OrganizationModel() {
    }

    protected OrganizationModel(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.region = in.readString();
        this.city = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
        this.link = in.readString();
        this.currencyId = in.createStringArray();
    }

    public static final Parcelable.Creator<OrganizationModel> CREATOR = new Parcelable.Creator<OrganizationModel>() {
        @Override
        public OrganizationModel createFromParcel(Parcel source) {
            return new OrganizationModel(source);
        }

        @Override
        public OrganizationModel[] newArray(int size) {
            return new OrganizationModel[size];
        }
    };
}
