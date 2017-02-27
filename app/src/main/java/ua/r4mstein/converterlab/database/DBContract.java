package ua.r4mstein.converterlab.database;

public class DBContract {

    public static class HomeEntry {

        public static final String TABLE_NAME = "home_models";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_OLD_ID = "oldId";
        public static final String COLUMN_ORG_TYPE = "orgType";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_REGION_ID = "regionId";
        public static final String COLUMN_CITY_ID = "cityId";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_LINK = "link";

        public static final String[] ALL_COLUMNS =
                {COLUMN_ID, COLUMN_OLD_ID, COLUMN_ORG_TYPE, COLUMN_TITLE, COLUMN_REGION_ID,
                        COLUMN_CITY_ID, COLUMN_PHONE, COLUMN_ADDRESS, COLUMN_LINK};

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " TEXT PRIMARY KEY," +
                        COLUMN_OLD_ID + " TEXT," +
                        COLUMN_ORG_TYPE + " TEXT," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_REGION_ID + " TEXT," +
                        COLUMN_CITY_ID + " TEXT," +
                        COLUMN_PHONE + " TEXT," +
                        COLUMN_ADDRESS + " TEXT," +
                        COLUMN_LINK + " TEXT" + ");";

        public static final String SQL_DELETE =
                "DROP TABLE " + TABLE_NAME;

    }
}
