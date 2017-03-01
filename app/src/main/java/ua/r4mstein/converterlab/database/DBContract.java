package ua.r4mstein.converterlab.database;

public class DBContract {

    public static class OrganizationEntry {

        public static final String TABLE_NAME = "organization_models";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_REGION = "region";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_LINK = "link";

        public static final String[] ALL_COLUMNS =
                {COLUMN_ID, COLUMN_TITLE, COLUMN_REGION, COLUMN_CITY, COLUMN_PHONE,
                        COLUMN_ADDRESS, COLUMN_LINK};

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " TEXT PRIMARY KEY," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_REGION + " TEXT," +
                        COLUMN_CITY + " TEXT," +
                        COLUMN_PHONE + " TEXT," +
                        COLUMN_ADDRESS + " TEXT," +
                        COLUMN_LINK + " TEXT" + ");";

        public static final String SQL_DELETE =
                "DROP TABLE " + TABLE_NAME;

    }

    public static class CurrenciesEntry {

        public static final String TABLE_NAME = "currencies_models";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ASK = "ask";
        public static final String COLUMN_BID = "bid";

        public static final String[] ALL_COLUMNS =
                {COLUMN_ID, COLUMN_NAME, COLUMN_ASK, COLUMN_BID};

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_ASK + " TEXT," +
                        COLUMN_BID + " TEXT" + ");";

        public static final String SQL_DELETE =
                "DROP TABLE " + TABLE_NAME;
    }
}
