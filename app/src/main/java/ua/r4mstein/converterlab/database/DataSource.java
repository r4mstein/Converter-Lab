package ua.r4mstein.converterlab.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.database.DBContract.CurrenciesEntry;
import ua.r4mstein.converterlab.database.DBContract.OrganizationEntry;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

public final class DataSource {

    private static DataSource INSTANCE;

    private static final String TAG = "DataSource";
    private final Logger mLogger;

    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;

    public boolean inUse;

    private DataSource(final Context _context) {
        mDBHelper = new DBHelper(_context);
        mLogger = LogManager.getLogger();
    }

    public static DataSource getDataSource(final Context _context) {
        if (INSTANCE == null) INSTANCE = new DataSource(_context);
        return INSTANCE;
    }

    public final void open() {
        mDatabase = mDBHelper.getWritableDatabase();
        mLogger.d(TAG, "Open DB");
    }

    public final boolean isOpen() {
        boolean isOpen = mDatabase.isOpen();
        mLogger.d(TAG, "DB isOpen = " + isOpen);
        return isOpen;
    }

    public final void close() {
        mDBHelper.close();
        mLogger.d(TAG, "Close DB");
    }

    public final void insertOrUpdateOrganizations(final List<OrganizationModel> _list) {
        String sql = "INSERT OR REPLACE INTO " + OrganizationEntry.TABLE_NAME + " (" +
                OrganizationEntry.COLUMN_ID + ", " +
                OrganizationEntry.COLUMN_DATE + ", " +
                OrganizationEntry.COLUMN_TITLE + ", " +
                OrganizationEntry.COLUMN_REGION + ", " +
                OrganizationEntry.COLUMN_CITY + ", " +
                OrganizationEntry.COLUMN_PHONE + ", " +
                OrganizationEntry.COLUMN_ADDRESS + ", " +
                OrganizationEntry.COLUMN_LINK + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        mDatabase.beginTransaction();
        mLogger.d(TAG, "insertOrUpdateOrganizations: Begin Transaction");

        SQLiteStatement statement = mDatabase.compileStatement(sql);
        for (int i = 0; i < _list.size(); i++) {
            statement.bindString(1, _list.get(i).getId());
            statement.bindString(2, _list.get(i).getDate());
            statement.bindString(3, _list.get(i).getTitle());
            statement.bindString(4, _list.get(i).getRegion());
            statement.bindString(5, _list.get(i).getCity());
            statement.bindString(6, _list.get(i).getPhone());
            statement.bindString(7, _list.get(i).getAddress());
            statement.bindString(8, _list.get(i).getLink());

            statement.execute();
            statement.clearBindings();
        }

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        mLogger.d(TAG, "insertOrUpdateOrganizations: End Transaction");
    }

    public final List<OrganizationModel> getAllOrganizationItems() {
        List<OrganizationModel> modelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(OrganizationEntry.TABLE_NAME, OrganizationEntry.ALL_COLUMNS,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            OrganizationModel model = new OrganizationModel();

            model.setId(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_ID)));
            model.setDate(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_DATE)));
            model.setTitle(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_TITLE)));
            model.setRegion(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_REGION)));
            model.setCity(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_CITY)));
            model.setPhone(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_PHONE)));
            model.setAddress(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_ADDRESS)));
            model.setLink(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_LINK)));

            modelList.add(model);
        }
        mLogger.d(TAG, "getAllOrganizationItems");
        cursor.close();
        return modelList;
    }

    public final OrganizationModel getOrganizationItem(final String _key) {
        OrganizationModel model = new OrganizationModel();

        String[] strings = new String[]{_key};
        Cursor cursor = mDatabase.query(OrganizationEntry.TABLE_NAME, OrganizationEntry.ALL_COLUMNS,
                OrganizationEntry.COLUMN_ID + "=?", strings, null, null, null);

        while (cursor.moveToNext()) {
            model.setId(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_ID)));
            model.setDate(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_DATE)));
            model.setTitle(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_TITLE)));
            model.setRegion(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_REGION)));
            model.setCity(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_CITY)));
            model.setPhone(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_PHONE)));
            model.setAddress(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_ADDRESS)));
            model.setLink(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_LINK)));
        }
        cursor.close();
        mLogger.d(TAG, "getOrganizationItem: Item Title: " + model.getTitle());
        return model;
    }

    public final void insertOrUpdateCurrencies(final List<CurrenciesModel> _list) {
        String sql = "INSERT OR REPLACE INTO " + CurrenciesEntry.TABLE_NAME + " (" +
                CurrenciesEntry.COLUMN_ID + ", " +
                CurrenciesEntry.COLUMN_ORGANIZATION_ID + ", " +
                CurrenciesEntry.COLUMN_NAME + ", " +
                CurrenciesEntry.COLUMN_NAME_KEY + ", " +
                CurrenciesEntry.COLUMN_ASK + ", " +
                CurrenciesEntry.COLUMN_ASK_COLOR + ", " +
                CurrenciesEntry.COLUMN_BID + ", " +
                CurrenciesEntry.COLUMN_BID_COLOR + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        mDatabase.beginTransaction();
        mLogger.d(TAG, "insertOrUpdateCurrencies: Begin Transaction");

        SQLiteStatement statement = mDatabase.compileStatement(sql);
        for (int i = 0; i < _list.size(); i++) {
            statement.bindString(1, _list.get(i).getId());
            statement.bindString(2, _list.get(i).getOrganizationId());
            statement.bindString(3, _list.get(i).getName());
            statement.bindString(4, _list.get(i).getNameKey());
            statement.bindString(5, _list.get(i).getAsk());
            statement.bindString(6, _list.get(i).getAskColor());
            statement.bindString(7, _list.get(i).getBid());
            statement.bindString(8, _list.get(i).getBidColor());

            statement.execute();
            statement.clearBindings();
        }

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        mLogger.d(TAG, "insertOrUpdateCurrencies: End Transaction");
    }

    public final List<CurrenciesModel> getAllCurrenciesItems() {
        List<CurrenciesModel> modelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(CurrenciesEntry.TABLE_NAME, CurrenciesEntry.ALL_COLUMNS,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            CurrenciesModel model = new CurrenciesModel();

            model.setId(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ID)));
            model.setOrganizationId(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ORGANIZATION_ID)));
            model.setName(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_NAME)));
            model.setNameKey(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_NAME_KEY)));
            model.setAsk(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ASK)));
            model.setAskColor(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ASK_COLOR)));
            model.setBid(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_BID)));
            model.setBidColor(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_BID_COLOR)));

            modelList.add(model);
        }
        mLogger.d(TAG, "getAllCurrenciesItems");
        cursor.close();
        return modelList;
    }

    public final List<CurrenciesModel> getCurrenciesItemsForOrganization(final String _organizationId) {
        List<CurrenciesModel> modelList = new ArrayList<>();

        String[] strings = new String[]{_organizationId};
        Cursor cursor = mDatabase.query(CurrenciesEntry.TABLE_NAME, CurrenciesEntry.ALL_COLUMNS,
                CurrenciesEntry.COLUMN_ORGANIZATION_ID + "=?", strings, null, null, null);

        while (cursor.moveToNext()) {
            CurrenciesModel model = new CurrenciesModel();

            model.setId(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ID)));
            model.setOrganizationId(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ORGANIZATION_ID)));
            model.setName(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_NAME)));
            model.setNameKey(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_NAME_KEY)));
            model.setAsk(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ASK)));
            model.setAskColor(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ASK_COLOR)));
            model.setBid(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_BID)));
            model.setBidColor(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_BID_COLOR)));

            modelList.add(model);
        }
        cursor.close();

        mLogger.d(TAG, "getCurrenciesItemsForOrganization: Count of Currencies = " + modelList.size());
        return modelList;
    }
}
