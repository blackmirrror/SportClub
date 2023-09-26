package com.blackmirrror.sportclub.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

public class SportClubContentProvider extends ContentProvider {

    public static final int MEMBERS = 100;
    public static final int MEMBER_ID = 101;

    SportClubDBHelper sportClubDBHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(SportClubContract.AUTHORITY, SportClubContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(SportClubContract.AUTHORITY, SportClubContract.PATH_MEMBERS + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        sportClubDBHelper = new SportClubDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase db = sportClubDBHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                cursor = db.query(SportClubContract.MembersEntry.TABLE_NAME,
                        strings, s, strings1, null, null, s1);
                break;
            case MEMBER_ID:
                s = SportClubContract.MembersEntry._ID + "=?";
                strings1 = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(SportClubContract.MembersEntry.TABLE_NAME,
                        strings, s, strings1, null, null, s1);
                break;
            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_SHORT).show();
                cursor = null;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                return SportClubContract.MembersEntry.CONTENT_MULTIPLE_ITEMS;
            case MEMBER_ID:
                return SportClubContract.MembersEntry.CONTENT_SINGLE_ITEM;
            default:
                Toast.makeText(getContext(), "Incorrect URI, not getType", Toast.LENGTH_SHORT).show();
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        String firstName = contentValues.getAsString(SportClubContract.MembersEntry.COLUMN_FIRST_NAME);
        String lastName = contentValues.getAsString(SportClubContract.MembersEntry.COLUMN_LAST_NAME);
        String gender = contentValues.getAsString(SportClubContract.MembersEntry.COLUMN_GENDER);
        String sport = contentValues.getAsString(SportClubContract.MembersEntry.COLUMN_SPORT);
        if (firstName == null || lastName == null || gender == null || sport == null) {
            Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return null;
        }

        SQLiteDatabase db = sportClubDBHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        if (match == MEMBERS) {
            long id = db.insert(SportClubContract.MembersEntry.TABLE_NAME, null, contentValues);
            if (id == -1) {
                return null;
            }
            return ContentUris.withAppendedId(uri, id);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = sportClubDBHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                return db.delete(SportClubContract.MembersEntry.TABLE_NAME, s, strings);
            case MEMBER_ID:
                s = SportClubContract.MembersEntry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.delete(SportClubContract.MembersEntry.TABLE_NAME, s, strings);
            default:
                Toast.makeText(getContext(), "Incorrect URI, not deleted", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        String firstName = contentValues.getAsString(SportClubContract.MembersEntry.COLUMN_FIRST_NAME);
        String lastName = contentValues.getAsString(SportClubContract.MembersEntry.COLUMN_LAST_NAME);
        String gender = contentValues.getAsString(SportClubContract.MembersEntry.COLUMN_GENDER);
        String sport = contentValues.getAsString(SportClubContract.MembersEntry.COLUMN_SPORT);
        if (firstName == null || lastName == null || gender == null || sport == null) {
            Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return 0;
        }

        SQLiteDatabase db = sportClubDBHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                return db.update(SportClubContract.MembersEntry.TABLE_NAME, contentValues, s, strings);
            case MEMBER_ID:
                s = SportClubContract.MembersEntry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.update(SportClubContract.MembersEntry.TABLE_NAME, contentValues, s, strings);
            default:
                Toast.makeText(getContext(), "Incorrect URI, not updated", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }
}
