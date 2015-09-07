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
 * Sync table ContentProvider
 * 
 * @author jacp
 */
public class SyncProvider extends ContentProvider {

    private static HashMap<String, String> sPersonsProjectionMap;

    private static final int SYNC = 1;
    private static final int SYNC_ID = 2;

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
        qb.setTables(Provider.SyncColumns.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
        case SYNC:
            qb.setProjectionMap(sPersonsProjectionMap);
            break;

        case SYNC_ID:
            qb.setProjectionMap(sPersonsProjectionMap);
            qb.appendWhere(Provider.SyncColumns._ID + "="
                    + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Provider.SyncColumns.DEFAULT_SORT_ORDER;
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
        case SYNC:
            return Provider.CONTENT_TYPE;
        case SYNC_ID:
            return Provider.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != SYNC) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        if (values.containsKey(Provider.SyncColumns.CHANGE_TIME) == false) {
            values.put(Provider.SyncColumns.CHANGE_TIME, 0);
        }

        if (values.containsKey(Provider.SyncColumns.CONTACT_ID) == false) {
            values.put(Provider.SyncColumns.CONTACT_ID, 0);
        }
        if (values.containsKey(Provider.SyncColumns.DISPLAY_NAME) == false) {
            values.put(Provider.SyncColumns.DISPLAY_NAME, "");
        }

        if (values.containsKey(Provider.SyncColumns.EMAIL) == false) {
            values.put(Provider.SyncColumns.EMAIL, "");
        }
        if (values.containsKey(Provider.SyncColumns.GROUP_MEMBER) == false) {
            values.put(Provider.SyncColumns.GROUP_MEMBER, "");
        }
        if (values.containsKey(Provider.SyncColumns.HOME_ADDRESS) == false) {
            values.put(Provider.SyncColumns.HOME_ADDRESS, "");
        }
        if (values.containsKey(Provider.SyncColumns.IM) == false) {
            values.put(Provider.SyncColumns.IM, "");
        }
        if (values.containsKey(Provider.SyncColumns.MOBILE_PHONE) == false) {
            values.put(Provider.SyncColumns.MOBILE_PHONE, 0);
        }
        if (values.containsKey(Provider.SyncColumns.NICK_NAME) == false) {
            values.put(Provider.SyncColumns.NICK_NAME, "");
        }
        if (values.containsKey(Provider.SyncColumns.NOTE) == false) {
            values.put(Provider.SyncColumns.NOTE, "");
        }
        if (values.containsKey(Provider.SyncColumns.OPERATE_ID) == false) {
            values.put(Provider.SyncColumns.OPERATE_ID, 0);
        }
        if (values.containsKey(Provider.SyncColumns.ORGANIZATION) == false) {
            values.put(Provider.SyncColumns.ORGANIZATION, "");
        }
        if (values.containsKey(Provider.SyncColumns.PHOTO_IMAGE) == false) {
            values.put(Provider.SyncColumns.PHOTO_IMAGE, "");
        }
        if (values.containsKey(Provider.SyncColumns.TELEPHONE) == false) {
            values.put(Provider.SyncColumns.TELEPHONE, "");
        }
        if (values.containsKey(Provider.SyncColumns.WEBSITE) == false) {
            values.put(Provider.SyncColumns.WEBSITE, "");
        }
        if (values.containsKey(Provider.SyncColumns.WORK_ADDRESS) == false) {
            values.put(Provider.SyncColumns.WORK_ADDRESS, "");
        }
        if (values.containsKey(Provider.SyncColumns.WORKPHONE) == false) {
            values.put(Provider.SyncColumns.WORKPHONE, "");
        }
//        if (values.containsKey(Provider.SyncColumns.OLD_NAME) == false) {
//            values.put(Provider.SyncColumns.OLD_NAME, "");
//        }
//        if (values.containsKey(Provider.SyncColumns.OLD_PHONE) == false) {
//            values.put(Provider.SyncColumns.OLD_PHONE, 0);
//        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(Provider.SyncColumns.TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(
                    Provider.SyncColumns.CONTENT_URI, rowId);
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
        case SYNC:
            count = db
                    .delete(Provider.SyncColumns.TABLE_NAME, where, whereArgs);
            break;

        case SYNC_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.delete(
                    Provider.SyncColumns.TABLE_NAME,
                    Provider.SyncColumns._ID
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
        case SYNC:
            count = db.update(Provider.SyncColumns.TABLE_NAME, values, where,
                    whereArgs);
            break;

        case SYNC_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(
                    Provider.SyncColumns.TABLE_NAME,
                    values,
                    Provider.SyncColumns._ID
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
        sUriMatcher.addURI(Provider.SyncColumns.AUTHORITY, "synchronicity",
                SYNC);
        sUriMatcher.addURI(Provider.SyncColumns.AUTHORITY, "synchronicity/#",
                SYNC_ID);

        sPersonsProjectionMap = new HashMap<String, String>();
        sPersonsProjectionMap.put(Provider.SyncColumns._ID,
                Provider.SyncColumns._ID);
        sPersonsProjectionMap.put(Provider.SyncColumns.CONTACT_ID,
                Provider.SyncColumns.CONTACT_ID);
        sPersonsProjectionMap.put(Provider.SyncColumns.CHANGE_TIME,
                Provider.SyncColumns.CHANGE_TIME);
        sPersonsProjectionMap.put(Provider.SyncColumns.OPERATE_ID,
                Provider.SyncColumns.OPERATE_ID);
        sPersonsProjectionMap.put(Provider.SyncColumns.DISPLAY_NAME,
                Provider.SyncColumns.DISPLAY_NAME);
        sPersonsProjectionMap.put(Provider.SyncColumns.NICK_NAME,
                Provider.SyncColumns.NICK_NAME);
        sPersonsProjectionMap.put(Provider.SyncColumns.WORK_ADDRESS,
                Provider.SyncColumns.WORK_ADDRESS);
        sPersonsProjectionMap.put(Provider.SyncColumns.HOME_ADDRESS,
                Provider.SyncColumns.HOME_ADDRESS);
        sPersonsProjectionMap.put(Provider.SyncColumns.ORGANIZATION,
                Provider.SyncColumns.ORGANIZATION);
        sPersonsProjectionMap.put(Provider.SyncColumns.MOBILE_PHONE,
                Provider.SyncColumns.MOBILE_PHONE);
        sPersonsProjectionMap.put(Provider.SyncColumns.TELEPHONE,
                Provider.SyncColumns.TELEPHONE);
        sPersonsProjectionMap.put(Provider.SyncColumns.WORKPHONE,
                Provider.SyncColumns.WORKPHONE);
        sPersonsProjectionMap.put(Provider.SyncColumns.EMAIL,
                Provider.SyncColumns.EMAIL);
        sPersonsProjectionMap.put(Provider.SyncColumns.WEBSITE,
                Provider.SyncColumns.WEBSITE);
        sPersonsProjectionMap.put(Provider.SyncColumns.PHOTO_IMAGE,
                Provider.SyncColumns.PHOTO_IMAGE);
        sPersonsProjectionMap.put(Provider.SyncColumns.NOTE,
                Provider.SyncColumns.NOTE);
        sPersonsProjectionMap.put(Provider.SyncColumns.IM,
                Provider.SyncColumns.IM);
        sPersonsProjectionMap.put(Provider.SyncColumns.GROUP_MEMBER,
                Provider.SyncColumns.GROUP_MEMBER);
        sPersonsProjectionMap.put(Provider.SyncColumns.GLOBAL_ID,
                Provider.SyncColumns.GLOBAL_ID);
//        sPersonsProjectionMap.put(Provider.SyncColumns.OLD_NAME,
//                Provider.SyncColumns.OLD_NAME);
//        sPersonsProjectionMap.put(Provider.SyncColumns.OLD_PHONE,
//                Provider.SyncColumns.OLD_PHONE);
    }
}
