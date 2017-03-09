package ua.r4mstein.converterlab.database;

import android.content.ContentValues;
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

public class DataSource {

    private static final String TAG = "DataSource";

    private final Logger mLogger;

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;

    public DataSource(Context context) {
        mContext = context;
        mDBHelper = new DBHelper(mContext);
        mDatabase = mDBHelper.getWritableDatabase();
        mLogger = LogManager.getLogger();
    }

    public void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public long insertOrganizationItem(OrganizationModel model) {
        ContentValues values = model.toValues();
        long result = mDatabase.insert(OrganizationEntry.TABLE_NAME, null, values);
        mLogger.d(TAG, "insertOrganizationItem result: " + result);

        return result;
    }

    public void insertOrUpdateOrganizations(List<OrganizationModel> list) {
        String sql = "INSERT OR REPLACE INTO " + OrganizationEntry.TABLE_NAME + " (" +
                OrganizationEntry.COLUMN_ID + ", " +
                OrganizationEntry.COLUMN_TITLE + ", " +
                OrganizationEntry.COLUMN_REGION + ", " +
                OrganizationEntry.COLUMN_CITY + ", " +
                OrganizationEntry.COLUMN_PHONE + ", " +
                OrganizationEntry.COLUMN_ADDRESS + ", " +
                OrganizationEntry.COLUMN_LINK + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        mDatabase.beginTransaction();
        mLogger.d(TAG, "insertOrUpdateOrganizations: mDatabase.beginTransaction");

        SQLiteStatement statement = mDatabase.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            statement.bindString(1, list.get(i).getId());
            statement.bindString(2, list.get(i).getTitle());
            statement.bindString(3, list.get(i).getRegion());
            statement.bindString(4, list.get(i).getCity());
            statement.bindString(5, list.get(i).getPhone());
            statement.bindString(6, list.get(i).getAddress());
            statement.bindString(7, list.get(i).getLink());

            statement.execute();
            statement.clearBindings();
        }

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        mLogger.d(TAG, "insertOrUpdateOrganizations: mDatabase.endTransaction");
    }

    public void updateOrganizationItem(OrganizationModel model) {
        ContentValues values = new ContentValues();

        values.put(OrganizationEntry.COLUMN_TITLE, model.getTitle());
        values.put(OrganizationEntry.COLUMN_REGION, model.getRegion());
        values.put(OrganizationEntry.COLUMN_CITY, model.getCity());
        values.put(OrganizationEntry.COLUMN_PHONE, model.getPhone());
        values.put(OrganizationEntry.COLUMN_ADDRESS, model.getAddress());
        values.put(OrganizationEntry.COLUMN_LINK, model.getLink());

        String selection = OrganizationEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {model.getId()};

        mDatabase.update(OrganizationEntry.TABLE_NAME, values, selection, selectionArgs);

        mLogger.d(TAG, "updateOrganizationItem, item id: " + model.getId());
    }

    public void insertOrUpdateOrganizationItem(OrganizationModel model) {
        Cursor cursor = mDatabase.query(OrganizationEntry.TABLE_NAME, OrganizationEntry.ALL_COLUMNS,
                OrganizationEntry.COLUMN_ID + "=?", new String[]{model.getId()}, null, null, null);

        if (cursor.moveToFirst()) {
            mLogger.d(TAG, "insertOrUpdateOrganizationItem: update");
            updateOrganizationItem(model);
        } else {
            mLogger.d(TAG, "insertOrUpdateOrganizationItem: insert");
            insertOrganizationItem(model);
        }
        cursor.close();
    }

    public List<OrganizationModel> getAllOrganizationItems() {
        List<OrganizationModel> modelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(OrganizationEntry.TABLE_NAME, OrganizationEntry.ALL_COLUMNS,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            OrganizationModel model = new OrganizationModel();

            model.setId(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_ID)));
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

    public long insertCurrenciesItem(CurrenciesModel model) {
        ContentValues values = model.toValues();
        long result = mDatabase.insert(CurrenciesEntry.TABLE_NAME, null, values);
        mLogger.d(TAG, "insertCurrenciesItem result: " + result);

        return result;
    }

    public void insertOrUpdateCurrencies(List<CurrenciesModel> list) {
        String sql = "INSERT OR REPLACE INTO " + CurrenciesEntry.TABLE_NAME + " (" +
                CurrenciesEntry.COLUMN_ID + ", " +
                CurrenciesEntry.COLUMN_ORGANIZATION_ID + ", " +
                CurrenciesEntry.COLUMN_NAME + ", " +
                CurrenciesEntry.COLUMN_ASK + ", " +
                CurrenciesEntry.COLUMN_ASK_COLOR + ", " +
                CurrenciesEntry.COLUMN_BID + ", " +
                CurrenciesEntry.COLUMN_BID_COLOR + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        mDatabase.beginTransaction();
        mLogger.d(TAG, "insertOrUpdateCurrencies: mDatabase.beginTransaction");

        SQLiteStatement statement = mDatabase.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            statement.bindString(1, list.get(i).getId());
            statement.bindString(2, list.get(i).getOrganization_id());
            statement.bindString(3, list.get(i).getName());
            statement.bindString(4, list.get(i).getAsk());
            statement.bindString(5, list.get(i).getAsk_color());
            statement.bindString(6, list.get(i).getBid());
            statement.bindString(7, list.get(i).getBid_color());

            statement.execute();
            statement.clearBindings();
        }

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        mLogger.d(TAG, "insertOrUpdateCurrencies: mDatabase.endTransaction");
    }

    public void updateCurrenciesItem(CurrenciesModel model) {
        ContentValues values = new ContentValues();

        values.put(CurrenciesEntry.COLUMN_ORGANIZATION_ID, model.getOrganization_id());
        values.put(CurrenciesEntry.COLUMN_NAME, model.getName());
        values.put(CurrenciesEntry.COLUMN_ASK, model.getAsk());
        values.put(CurrenciesEntry.COLUMN_ASK_COLOR, model.getAsk_color());
        values.put(CurrenciesEntry.COLUMN_BID, model.getBid());
        values.put(CurrenciesEntry.COLUMN_BID_COLOR, model.getBid_color());

        String selection = OrganizationEntry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {model.getId()};

        mDatabase.update(CurrenciesEntry.TABLE_NAME, values, selection, selectionArgs);

        mLogger.d(TAG, "updateCurrenciesItem, item id: " + model.getId());
    }

    public void insertOrUpdateCurrenciesItem(CurrenciesModel model) {
        Cursor cursor = mDatabase.query(CurrenciesEntry.TABLE_NAME, CurrenciesEntry.ALL_COLUMNS,
                CurrenciesEntry.COLUMN_ID + "=?", new String[]{model.getId()}, null, null, null);

        if (cursor.moveToFirst()) {
            mLogger.d(TAG, "insertOrUpdateCurrenciesItem: update");
            updateCurrenciesItem(model);
        } else {
            mLogger.d(TAG, "insertOrUpdateCurrenciesItem: insert");
            insertCurrenciesItem(model);
        }
        cursor.close();
    }

    public List<CurrenciesModel> getAllCurrenciesItems() {
        List<CurrenciesModel> modelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(CurrenciesEntry.TABLE_NAME, CurrenciesEntry.ALL_COLUMNS,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            CurrenciesModel model = new CurrenciesModel();

            model.setId(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ID)));
            model.setOrganization_id(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ORGANIZATION_ID)));
            model.setName(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_NAME)));
            model.setAsk(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ASK)));
            model.setAsk_color(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ASK_COLOR)));
            model.setBid(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_BID)));
            model.setBid_color(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_BID_COLOR)));

            modelList.add(model);
        }
        mLogger.d(TAG, "getAllCurrenciesItems");
        cursor.close();
        return modelList;
    }

    public List<CurrenciesModel> getCurrenciesItemsForOrganization(String organizationId) {
        List<CurrenciesModel> modelList = new ArrayList<>();

        String[] strings = new String[]{organizationId};
        Cursor cursor = mDatabase.query(CurrenciesEntry.TABLE_NAME, CurrenciesEntry.ALL_COLUMNS,
                CurrenciesEntry.COLUMN_ORGANIZATION_ID + "=?", strings, null, null, null);

        while (cursor.moveToNext()) {
            CurrenciesModel model = new CurrenciesModel();

            model.setId(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ID)));
            model.setOrganization_id(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ORGANIZATION_ID)));
            model.setName(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_NAME)));
            model.setAsk(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ASK)));
            model.setAsk_color(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_ASK_COLOR)));
            model.setBid(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_BID)));
            model.setBid_color(cursor.getString(cursor.getColumnIndex(CurrenciesEntry.COLUMN_BID_COLOR)));

            modelList.add(model);
        }
        cursor.close();

        mLogger.d(TAG, "getCurrenciesItemsForOrganization");
        return modelList;
    }

    public OrganizationModel getOrganizationItem(String key) {
        OrganizationModel model = new OrganizationModel();

        String[] strings = new String[]{key};
        Cursor cursor = mDatabase.query(OrganizationEntry.TABLE_NAME, OrganizationEntry.ALL_COLUMNS,
                OrganizationEntry.COLUMN_ID + "=?", strings, null, null, null);

        while (cursor.moveToNext()) {
            model.setId(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_ID)));
            model.setTitle(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_TITLE)));
            model.setRegion(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_REGION)));
            model.setCity(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_CITY)));
            model.setPhone(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_PHONE)));
            model.setAddress(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_ADDRESS)));
            model.setLink(cursor.getString(cursor.getColumnIndex(OrganizationEntry.COLUMN_LINK)));
        }
        cursor.close();
        mLogger.d(TAG, "getOrganizationItem");
        return model;
    }

}
