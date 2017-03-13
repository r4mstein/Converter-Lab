package ua.r4mstein.converterlab.presentation.ui_models;

import android.content.ContentValues;

import ua.r4mstein.converterlab.database.DBContract;

public final class CurrenciesModel {

    private String id;
    private String organization_id;
    private String name;
    private String name_key;
    private String ask;
    private String ask_color;
    private String bid;
    private String bid_color;

    public ContentValues toValues() {
        ContentValues values = new ContentValues();

        values.put(DBContract.CurrenciesEntry.COLUMN_ID, id);
        values.put(DBContract.CurrenciesEntry.COLUMN_ORGANIZATION_ID, organization_id);
        values.put(DBContract.CurrenciesEntry.COLUMN_NAME, name);
        values.put(DBContract.CurrenciesEntry.COLUMN_NAME_KEY, name_key);
        values.put(DBContract.CurrenciesEntry.COLUMN_ASK, ask);
        values.put(DBContract.CurrenciesEntry.COLUMN_ASK_COLOR, ask_color);
        values.put(DBContract.CurrenciesEntry.COLUMN_BID, bid);
        values.put(DBContract.CurrenciesEntry.COLUMN_BID_COLOR, bid_color);

        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_key() {
        return name_key;
    }

    public void setName_key(String name_key) {
        this.name_key = name_key;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk_color() {
        return ask_color;
    }

    public void setAsk_color(String ask_color) {
        this.ask_color = ask_color;
    }

    public String getBid_color() {
        return bid_color;
    }

    public void setBid_color(String bid_color) {
        this.bid_color = bid_color;
    }
}
