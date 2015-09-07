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
public class PersonProvider extends ContentProvider {

    private static HashMap<String, String> sPersonsProjectionMap;

    private static final int PERSONS = 1;
    private static final int PERSONS_ID = 2;

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
        qb.setTables(Provider.PersonColumns.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
        case PERSONS:
            qb.setProjectionMap(sPersonsProjectionMap);
            break;

        case PERSONS_ID:
            qb.setProjectionMap(sPersonsProjectionMap);
            qb.appendWhere(Provider.PersonColumns._ID + "="
                    + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Provider.PersonColumns.DEFAULT_SORT_ORDER;
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
        case PERSONS:
            return Provider.CONTENT_TYPE;
        case PERSONS_ID:
            return Provider.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != PERSONS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        // Make sure that the fields are all set
        if (values.containsKey(Provider.PersonColumns.NAME) == false) {
            values.put(Provider.PersonColumns.NAME, "");
        }

        if (values.containsKey(Provider.PersonColumns.TRUE_NAME) == false) {
            values.put(Provider.PersonColumns.TRUE_NAME, "");
        }
        if (values.containsKey(Provider.PersonColumns.AGE) == false) {
            values.put(Provider.PersonColumns.AGE, "");
        }

        if (values.containsKey(Provider.PersonColumns.USER_KEY) == false) {
            values.put(Provider.PersonColumns.USER_KEY, "");
        }
        if (values.containsKey(Provider.PersonColumns.CREATETIME) == false) {
            values.put(Provider.PersonColumns.CREATETIME, "");
        }
        if (values.containsKey(Provider.PersonColumns.NICK_NAME) == false) {
            values.put(Provider.PersonColumns.NICK_NAME, "");
        }
        if (values.containsKey(Provider.PersonColumns.PHONE) == false) {
            values.put(Provider.PersonColumns.PHONE, "");
        }
        if (values.containsKey(Provider.PersonColumns.USER_ADDRESS) == false) {
            values.put(Provider.PersonColumns.USER_ADDRESS, "");
        }
        if (values.containsKey(Provider.PersonColumns.USER_BIRTH) == false) {
            values.put(Provider.PersonColumns.USER_BIRTH, "");
        }
        if (values.containsKey(Provider.PersonColumns.USER_EMAIL) == false) {
            values.put(Provider.PersonColumns.USER_EMAIL, "");
        }
        if (values.containsKey(Provider.PersonColumns.USER_SEX) == false) {
            values.put(Provider.PersonColumns.USER_SEX, "");
        }
        if (values.containsKey(Provider.PersonColumns.USER_ID) == false) {
            values.put(Provider.PersonColumns.USER_ID, "");
        }
        if (values.containsKey(Provider.PersonColumns.USER_IMAGE) == false) {
            values.put(Provider.PersonColumns.USER_IMAGE, "");
        }
        if (values.containsKey(Provider.PersonColumns.JSSESSIONID) == false) {
            values.put(Provider.PersonColumns.JSSESSIONID, "");
        }
        if (values.containsKey(Provider.PersonColumns.SYNC_TIME) == false) {
            values.put(Provider.PersonColumns.SYNC_TIME, "");
        }
        if (values.containsKey(Provider.PersonColumns.CONTACT_SWITCHER_SELECTED) == false) {
            values.put(Provider.PersonColumns.CONTACT_SWITCHER_SELECTED, 0);
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(Provider.PersonColumns.TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(
                    Provider.PersonColumns.CONTENT_URI, rowId);
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
        case PERSONS:
            count = db.delete(Provider.PersonColumns.TABLE_NAME, where,
                    whereArgs);
            break;

        case PERSONS_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.delete(
                    Provider.PersonColumns.TABLE_NAME,
                    Provider.PersonColumns._ID
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
        case PERSONS:
            count = db.update(Provider.PersonColumns.TABLE_NAME, values, where,
                    whereArgs);
            break;

        case PERSONS_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(
                    Provider.PersonColumns.TABLE_NAME,
                    values,
                    Provider.PersonColumns._ID
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
        sUriMatcher.addURI(Provider.PersonColumns.AUTHORITY, "persons", PERSONS);
        sUriMatcher.addURI(Provider.PersonColumns.AUTHORITY, "persons/#", PERSONS_ID);

        sPersonsProjectionMap = new HashMap<String, String>();
        sPersonsProjectionMap.put(Provider.PersonColumns._ID,
                Provider.PersonColumns._ID);
        sPersonsProjectionMap.put(Provider.PersonColumns.NAME,
                Provider.PersonColumns.NAME);
        sPersonsProjectionMap.put(Provider.PersonColumns.JSSESSIONID,
                Provider.PersonColumns.JSSESSIONID);
        sPersonsProjectionMap.put(Provider.PersonColumns.USER_ID,
                Provider.PersonColumns.USER_ID);
        sPersonsProjectionMap.put(Provider.PersonColumns.USER_EMAIL,
                Provider.PersonColumns.USER_EMAIL);
        sPersonsProjectionMap.put(Provider.PersonColumns.PHONE,
                Provider.PersonColumns.PHONE);
        sPersonsProjectionMap.put(Provider.PersonColumns.TRUE_NAME,
                Provider.PersonColumns.TRUE_NAME);
        sPersonsProjectionMap.put(Provider.PersonColumns.USER_SEX,
                Provider.PersonColumns.USER_SEX);
        sPersonsProjectionMap.put(Provider.PersonColumns.USER_BIRTH,
                Provider.PersonColumns.USER_BIRTH);
        sPersonsProjectionMap.put(Provider.PersonColumns.NICK_NAME,
                Provider.PersonColumns.NICK_NAME);
        sPersonsProjectionMap.put(Provider.PersonColumns.USER_ADDRESS,
                Provider.PersonColumns.USER_ADDRESS);
        sPersonsProjectionMap.put(Provider.PersonColumns.USER_KEY,
                Provider.PersonColumns.USER_KEY);
        sPersonsProjectionMap.put(Provider.PersonColumns.AGE,
                Provider.PersonColumns.AGE);
        sPersonsProjectionMap.put(Provider.PersonColumns.CREATETIME,
                Provider.PersonColumns.CREATETIME);
        sPersonsProjectionMap.put(Provider.PersonColumns.PASSWORD,
                Provider.PersonColumns.PASSWORD);
        sPersonsProjectionMap.put(Provider.PersonColumns.USER_IMAGE,
                Provider.PersonColumns.USER_IMAGE);
        sPersonsProjectionMap.put(Provider.PersonColumns.SYNC_TIME,
                Provider.PersonColumns.SYNC_TIME);
        sPersonsProjectionMap.put(
                Provider.PersonColumns.CONTACT_SWITCHER_SELECTED,
                Provider.PersonColumns.CONTACT_SWITCHER_SELECTED);
    }
}
