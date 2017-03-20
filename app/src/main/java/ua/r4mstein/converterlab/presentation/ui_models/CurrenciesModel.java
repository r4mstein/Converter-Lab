package ua.r4mstein.converterlab.presentation.ui_models;

import android.content.ContentValues;

import ua.r4mstein.converterlab.database.DBContract;

public final class CurrenciesModel {

    private String id;
    private String organizationId;
    private String name;
    private String nameKey;
    private String ask;
    private String askColor;
    private String bid;
    private String bidColor;

    public ContentValues toValues() {
        ContentValues values = new ContentValues();

        values.put(DBContract.CurrenciesEntry.COLUMN_ID, id);
        values.put(DBContract.CurrenciesEntry.COLUMN_ORGANIZATION_ID, organizationId);
        values.put(DBContract.CurrenciesEntry.COLUMN_NAME, name);
        values.put(DBContract.CurrenciesEntry.COLUMN_NAME_KEY, nameKey);
        values.put(DBContract.CurrenciesEntry.COLUMN_ASK, ask);
        values.put(DBContract.CurrenciesEntry.COLUMN_ASK_COLOR, askColor);
        values.put(DBContract.CurrenciesEntry.COLUMN_BID, bid);
        values.put(DBContract.CurrenciesEntry.COLUMN_BID_COLOR, bidColor);

        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
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

    public String getAskColor() {
        return askColor;
    }

    public void setAskColor(String askColor) {
        this.askColor = askColor;
    }

    public String getBidColor() {
        return bidColor;
    }

    public void setBidColor(String bidColor) {
        this.bidColor = bidColor;
    }
}
