package com.blackmirrror.sportclub.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class SportClubContract {

    public static final String DATABASE_NAME = "sport_club";
    public static final int DATABASE_VERSION = 1;

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.blackmirrror.sportclub";
    public static final String PATH_MEMBERS = "members";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    private SportClubContract() {}

    public static final class MembersEntry implements BaseColumns {
        public static final String TABLE_NAME = "members";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_SPORT = "sport";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                BASE_CONTENT_URI, PATH_MEMBERS);
        public static final String CONTENT_MULTIPLE_ITEMS = "vnd.android.cursor.dir/"
                + AUTHORITY + PATH_MEMBERS;
        public static final String CONTENT_SINGLE_ITEM = "vnd..android.cursor.item"
                + AUTHORITY + PATH_MEMBERS;
    }
}
