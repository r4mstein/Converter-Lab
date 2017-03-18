package ua.r4mstein.converterlab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ua.r4mstein.converterlab.database.DBContract.CurrenciesEntry;
import ua.r4mstein.converterlab.database.DBContract.OrganizationEntry;

public final class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "currency.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OrganizationEntry.SQL_CREATE);
        db.execSQL(CurrenciesEntry.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(OrganizationEntry.SQL_DELETE);
        db.execSQL(CurrenciesEntry.SQL_DELETE);

        onCreate(db);
    }
}
