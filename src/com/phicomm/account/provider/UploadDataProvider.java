package com.phicomm.account.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class UploadDataProvider extends ContentProvider {
    private DatabaseHelper mOpenHelper;
    private static final UriMatcher sUriMatcher;
    private static final int UPLOADDATA = 1;
    private static final int UPLOADDATA_ID = 2;
    private static HashMap<String, String> sPersonsProjectionMap;

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case UPLOADDATA:
            count = db.delete(Provider.UploadDataColumns.TABLE_NAME, where,
                    whereArgs);
            break;

        case UPLOADDATA_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.delete(
                    Provider.UploadDataColumns.TABLE_NAME,
                    Provider.UploadDataColumns._ID
                            + "="
                            + noteId
                            + (!TextUtils.isEmpty(where) ? " AND (" + where
                                    + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        switch (sUriMatcher.match(uri)) {
        case UPLOADDATA:
            return Provider.CONTENT_TYPE;
        case UPLOADDATA_ID:
            return Provider.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // TODO Auto-generated method stub
        if (sUriMatcher.match(uri) != UPLOADDATA) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        if (values.containsKey(Provider.UploadDataColumns.CHANGE_TIME) == false) {
            values.put(Provider.UploadDataColumns.CHANGE_TIME, 0);
        }

        if (values.containsKey(Provider.UploadDataColumns.CONTACT_ID) == false) {
            values.put(Provider.UploadDataColumns.CONTACT_ID, 0);
        }
        if (values.containsKey(Provider.UploadDataColumns.DISPLAY_NAME) == false) {
            values.put(Provider.UploadDataColumns.DISPLAY_NAME, "");
        }

        if (values.containsKey(Provider.UploadDataColumns.EMAIL) == false) {
            values.put(Provider.UploadDataColumns.EMAIL, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.GROUP_MEMBER) == false) {
            values.put(Provider.UploadDataColumns.GROUP_MEMBER, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.HOME_ADDRESS) == false) {
            values.put(Provider.UploadDataColumns.HOME_ADDRESS, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.IM) == false) {
            values.put(Provider.UploadDataColumns.IM, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.MOBILE_PHONE) == false) {
            values.put(Provider.UploadDataColumns.MOBILE_PHONE, 0);
        }
        if (values.containsKey(Provider.UploadDataColumns.NICK_NAME) == false) {
            values.put(Provider.UploadDataColumns.NICK_NAME, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.NOTE) == false) {
            values.put(Provider.UploadDataColumns.NOTE, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OPERATE_ID) == false) {
            values.put(Provider.UploadDataColumns.OPERATE_ID, 0);
        }
        if (values.containsKey(Provider.UploadDataColumns.ORIGANIZATION) == false) {
            values.put(Provider.UploadDataColumns.ORIGANIZATION, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.PHOTO_IMG) == false) {
            values.put(Provider.UploadDataColumns.PHOTO_IMG, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.TELEPHONE) == false) {
            values.put(Provider.UploadDataColumns.TELEPHONE, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.WEBSITE) == false) {
            values.put(Provider.UploadDataColumns.WEBSITE, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.WORK_ADDRESS) == false) {
            values.put(Provider.UploadDataColumns.WORK_ADDRESS, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.WORK_PHONE) == false) {
            values.put(Provider.UploadDataColumns.WORK_PHONE, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_NAME) == false) {
            values.put(Provider.UploadDataColumns.OLD_NAME, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_PHONE) == false) {
            values.put(Provider.UploadDataColumns.OLD_PHONE, 0);
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_EMAIL) == false) {
            values.put(Provider.UploadDataColumns.OLD_EMAIL, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_GROUP_MEMBER) == false) {
            values.put(Provider.UploadDataColumns.OLD_GROUP_MEMBER, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_HOME_ADDRESS) == false) {
            values.put(Provider.UploadDataColumns.OLD_HOME_ADDRESS, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_IM) == false) {
            values.put(Provider.UploadDataColumns.OLD_IM, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_NICK_NAME) == false) {
            values.put(Provider.UploadDataColumns.OLD_NICK_NAME, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_NOTE) == false) {
            values.put(Provider.UploadDataColumns.OLD_NOTE, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_ORIGANIZATION) == false) {
            values.put(Provider.UploadDataColumns.OLD_ORIGANIZATION, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_PHOTO_IMG) == false) {
            values.put(Provider.UploadDataColumns.OLD_PHOTO_IMG, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_TELEPHONE) == false) {
            values.put(Provider.UploadDataColumns.OLD_TELEPHONE, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_WEBSITE) == false) {
            values.put(Provider.UploadDataColumns.OLD_WEBSITE, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_WORK_ADDRESS) == false) {
            values.put(Provider.UploadDataColumns.OLD_WORK_ADDRESS, "");
        }
        if (values.containsKey(Provider.UploadDataColumns.OLD_WORK_PHONE) == false) {
            values.put(Provider.UploadDataColumns.OLD_WORK_PHONE, "");
        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(Provider.UploadDataColumns.TABLE_NAME, null,
                values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(
                    Provider.UploadDataColumns.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Provider.UploadDataColumns.TABLE_NAME);
        switch (sUriMatcher.match(uri)) {
        case UPLOADDATA:
            builder.setProjectionMap(sPersonsProjectionMap);
            break;

        case UPLOADDATA_ID:
            builder.setProjectionMap(sPersonsProjectionMap);
            builder.appendWhere(Provider.UploadDataColumns._ID + "="
                    + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Provider.UploadDataColumns.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = builder.query(db, projection, selection, selectionArgs,
                null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where,
            String[] whereArgs) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case UPLOADDATA:
            count = db.update(Provider.UploadDataColumns.TABLE_NAME, values,
                    where, whereArgs);
            break;

        case UPLOADDATA_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(
                    Provider.UploadDataColumns.TABLE_NAME,
                    values,
                    Provider.UploadDataColumns._ID
                            + "="
                            + noteId
                            + (!TextUtils.isEmpty(where) ? " AND (" + where
                                    + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Provider.UploadDataColumns.AUTHORITY, "uploaddata",
                UPLOADDATA);
        sUriMatcher.addURI(Provider.UploadDataColumns.AUTHORITY,
                "uploaddata/#", UPLOADDATA_ID);

        sPersonsProjectionMap = new HashMap<String, String>();
        sPersonsProjectionMap.put(Provider.UploadDataColumns._ID,
                Provider.UploadDataColumns._ID);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.CONTACT_ID,
                Provider.UploadDataColumns.CONTACT_ID);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.CHANGE_TIME,
                Provider.UploadDataColumns.CHANGE_TIME);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.DISPLAY_NAME,
                Provider.UploadDataColumns.DISPLAY_NAME);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.EMAIL,
                Provider.UploadDataColumns.EMAIL);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.GROUP_MEMBER,
                Provider.UploadDataColumns.GROUP_MEMBER);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.HOME_ADDRESS,
                Provider.UploadDataColumns.HOME_ADDRESS);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.IM,
                Provider.UploadDataColumns.IM);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.MOBILE_PHONE,
                Provider.UploadDataColumns.MOBILE_PHONE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.NICK_NAME,
                Provider.UploadDataColumns.NICK_NAME);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.NOTE,
                Provider.UploadDataColumns.NOTE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OPERATE_ID,
                Provider.UploadDataColumns.OPERATE_ID);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.ORIGANIZATION,
                Provider.UploadDataColumns.ORIGANIZATION);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.PHOTO_IMG,
                Provider.UploadDataColumns.PHOTO_IMG);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.TELEPHONE,
                Provider.UploadDataColumns.TELEPHONE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.WEBSITE,
                Provider.UploadDataColumns.WEBSITE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.WORK_ADDRESS,
                Provider.UploadDataColumns.WORK_ADDRESS);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.WORK_PHONE,
                Provider.UploadDataColumns.WORK_PHONE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.CHANGE_TIME,
                Provider.UploadDataColumns.CHANGE_TIME);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_NAME,
                Provider.UploadDataColumns.OLD_NAME);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_PHONE,
                Provider.UploadDataColumns.OLD_PHONE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_EMAIL,
                Provider.UploadDataColumns.OLD_EMAIL);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_GROUP_MEMBER,
                Provider.UploadDataColumns.OLD_GROUP_MEMBER);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_HOME_ADDRESS,
                Provider.UploadDataColumns.OLD_HOME_ADDRESS);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_IM,
                Provider.UploadDataColumns.OLD_IM);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_NICK_NAME,
                Provider.UploadDataColumns.OLD_NICK_NAME);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_NOTE,
                Provider.UploadDataColumns.OLD_NOTE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_ORIGANIZATION,
                Provider.UploadDataColumns.OLD_ORIGANIZATION);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_PHOTO_IMG,
                Provider.UploadDataColumns.OLD_PHOTO_IMG);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_TELEPHONE,
                Provider.UploadDataColumns.OLD_TELEPHONE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_WEBSITE,
                Provider.UploadDataColumns.OLD_WEBSITE);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_WORK_ADDRESS,
                Provider.UploadDataColumns.OLD_WORK_ADDRESS);
        sPersonsProjectionMap.put(Provider.UploadDataColumns.OLD_WORK_PHONE,
                Provider.UploadDataColumns.OLD_WORK_PHONE);
    }

}
