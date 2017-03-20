package ua.r4mstein.converterlab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ua.r4mstein.converterlab.database.DBContract.CurrenciesEntry;
import ua.r4mstein.converterlab.database.DBContract.OrganizationEntry;

public final class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "currency.db";
    private static final int DB_VERSION = 1;

    public DBHelper(final Context _context) {
        super(_context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase _db) {
        _db.execSQL(OrganizationEntry.SQL_CREATE);
        _db.execSQL(CurrenciesEntry.SQL_CREATE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase _db, final int _oldVersion, final int _newVersion) {
        _db.execSQL(OrganizationEntry.SQL_DELETE);
        _db.execSQL(CurrenciesEntry.SQL_DELETE);

        onCreate(_db);
    }
}
