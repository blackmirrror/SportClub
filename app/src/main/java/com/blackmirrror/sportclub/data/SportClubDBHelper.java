package com.blackmirrror.sportclub.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SportClubDBHelper extends SQLiteOpenHelper {
    public SportClubDBHelper(@Nullable Context context) {
        super(context, SportClubContract.DATABASE_NAME, null, SportClubContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + SportClubContract.MembersEntry.TABLE_NAME + "("
                + SportClubContract.MembersEntry._ID + " INTEGER PRIMARY KEY,"
                + SportClubContract.MembersEntry.COLUMN_FIRST_NAME + " TEXT,"
                + SportClubContract.MembersEntry.COLUMN_LAST_NAME + " TEXT,"
                + SportClubContract.MembersEntry.COLUMN_GENDER + " INTEGER NOT NULL,"
                + SportClubContract.MembersEntry.COLUMN_SPORT + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_MEMBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SportClubContract.DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
