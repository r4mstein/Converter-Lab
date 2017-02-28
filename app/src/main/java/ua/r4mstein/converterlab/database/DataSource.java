package ua.r4mstein.converterlab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ua.r4mstein.converterlab.models.HomeModel;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;

    public DataSource(Context context) {
        mContext = context;
        mDBHelper = new DBHelper(mContext);
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public HomeModel createHomeModel(HomeModel homeModel) {
        return null;
    }
}
