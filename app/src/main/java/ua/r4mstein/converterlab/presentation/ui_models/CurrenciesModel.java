package ua.r4mstein.converterlab.presentation.ui_models;

import android.content.ContentValues;

import ua.r4mstein.converterlab.database.DBContract;

public final class CurrenciesModel {

    private String id;
    private String name;
    private String ask;
    private String bid;

    public ContentValues toValues() {
        ContentValues values = new ContentValues();

        values.put(DBContract.CurrenciesEntry.COLUMN_ID, id);
        values.put(DBContract.CurrenciesEntry.COLUMN_NAME, name);
        values.put(DBContract.CurrenciesEntry.COLUMN_ASK, ask);
        values.put(DBContract.CurrenciesEntry.COLUMN_BID, bid);

        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
