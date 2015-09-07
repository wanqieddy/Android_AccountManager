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

/**
 * Person table ContentProvider
 * @author jacp
 */
public class CheckDataProvider extends ContentProvider {

    private static HashMap<String, String> sPersonsProjectionMap;

    private static final int CHECKDATA = 1;
    private static final int CHECKDATA_ID = 2;

    private static final UriMatcher sUriMatcher;

    private DatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Provider.CheckDataColumns.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
        case CHECKDATA:
            qb.setProjectionMap(sPersonsProjectionMap);
            break;

        case CHECKDATA_ID:
            qb.setProjectionMap(sPersonsProjectionMap);
            qb.appendWhere(Provider.CheckDataColumns._ID + "="
                    + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Provider.CheckDataColumns.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data
        // changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case CHECKDATA:
            return Provider.CONTENT_TYPE;
        case CHECKDATA_ID:
            return Provider.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != CHECKDATA) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        if (values.containsKey(Provider.CheckDataColumns.CHANGE_TIME) == false) {
            values.put(Provider.CheckDataColumns.CHANGE_TIME, 0);
        }

        if (values.containsKey(Provider.CheckDataColumns.CONTACT_ID) == false) {
            values.put(Provider.CheckDataColumns.CONTACT_ID, 0);
        }
        if (values.containsKey(Provider.CheckDataColumns.DISPLAY_NAME) == false) {
            values.put(Provider.CheckDataColumns.DISPLAY_NAME, "");
        }

        if (values.containsKey(Provider.CheckDataColumns.EMAIL) == false) {
            values.put(Provider.CheckDataColumns.EMAIL, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.GROUP_MEMBER) == false) {
            values.put(Provider.CheckDataColumns.GROUP_MEMBER, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.HOME_ADDRESS) == false) {
            values.put(Provider.CheckDataColumns.HOME_ADDRESS, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.IM) == false) {
            values.put(Provider.CheckDataColumns.IM, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.MOBILE_PHONE) == false) {
            values.put(Provider.CheckDataColumns.MOBILE_PHONE, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.NICK_NAME) == false) {
            values.put(Provider.CheckDataColumns.NICK_NAME, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.NOTE) == false) {
            values.put(Provider.CheckDataColumns.NOTE, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.OPERATE_ID) == false) {
            values.put(Provider.CheckDataColumns.OPERATE_ID, 0);
        }
        if (values.containsKey(Provider.CheckDataColumns.ORIGANIZATION) == false) {
            values.put(Provider.CheckDataColumns.ORIGANIZATION, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.PHOTO_IMG) == false) {
            values.put(Provider.CheckDataColumns.PHOTO_IMG, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.TELEPHONE) == false) {
            values.put(Provider.CheckDataColumns.TELEPHONE, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.WEBSITE) == false) {
            values.put(Provider.CheckDataColumns.WEBSITE, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.WORK_ADDRESS) == false) {
            values.put(Provider.CheckDataColumns.WORK_ADDRESS, "");
        }
        if (values.containsKey(Provider.CheckDataColumns.WORK_PHONE) == false) {
            values.put(Provider.CheckDataColumns.WORK_PHONE, "");
        }

        if (values.containsKey(Provider.CheckDataColumns.VERSION) == false) {
            values.put(Provider.CheckDataColumns.VERSION, "");
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(Provider.CheckDataColumns.TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(
                    Provider.CheckDataColumns.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case CHECKDATA:
            count = db.delete(Provider.CheckDataColumns.TABLE_NAME, where,
                    whereArgs);
            break;

        case CHECKDATA_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.delete(
                    Provider.CheckDataColumns.TABLE_NAME,
                    Provider.CheckDataColumns._ID
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
    public int update(Uri uri, ContentValues values, String where,
            String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case CHECKDATA:
            count = db.update(Provider.CheckDataColumns.TABLE_NAME, values, where,
                    whereArgs);
            break;

        case CHECKDATA_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(
                    Provider.CheckDataColumns.TABLE_NAME,
                    values,
                    Provider.CheckDataColumns._ID
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
        sUriMatcher.addURI(Provider.CheckDataColumns.AUTHORITY, "checkdata", CHECKDATA);
        sUriMatcher.addURI(Provider.CheckDataColumns.AUTHORITY, "checkdata/#", CHECKDATA_ID);

        sPersonsProjectionMap = new HashMap<String, String>();
        sPersonsProjectionMap.put(Provider.CheckDataColumns._ID,
                Provider.CheckDataColumns._ID);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.CONTACT_ID,
                Provider.CheckDataColumns.CONTACT_ID);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.VERSION,
                Provider.CheckDataColumns.VERSION);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.CHANGE_TIME,
                Provider.CheckDataColumns.CHANGE_TIME);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.DISPLAY_NAME,
                Provider.CheckDataColumns.DISPLAY_NAME);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.EMAIL,
                Provider.CheckDataColumns.EMAIL);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.GROUP_MEMBER,
                Provider.CheckDataColumns.GROUP_MEMBER);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.HOME_ADDRESS,
                Provider.CheckDataColumns.HOME_ADDRESS);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.IM,
                Provider.CheckDataColumns.IM);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.MOBILE_PHONE,
                Provider.CheckDataColumns.MOBILE_PHONE);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.NICK_NAME,
                Provider.CheckDataColumns.NICK_NAME);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.NOTE,
                Provider.CheckDataColumns.NOTE);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.OPERATE_ID,
                Provider.CheckDataColumns.OPERATE_ID);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.ORIGANIZATION,
                Provider.CheckDataColumns.ORIGANIZATION);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.PHOTO_IMG,
                Provider.CheckDataColumns.PHOTO_IMG);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.TELEPHONE,
                Provider.CheckDataColumns.TELEPHONE);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.WEBSITE,
                Provider.CheckDataColumns.WEBSITE);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.WORK_ADDRESS,
                Provider.CheckDataColumns.WORK_ADDRESS);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.WORK_PHONE,
                Provider.CheckDataColumns.WORK_PHONE);
        sPersonsProjectionMap.put(Provider.CheckDataColumns.CHANGE_TIME,
                Provider.CheckDataColumns.CHANGE_TIME);
    }
}
