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
public class MapProvider extends ContentProvider {

    private static HashMap<String, String> sPersonsProjectionMap;

    private static final int MAPPING = 1;
    private static final int MAPPING_ID = 2;

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
        qb.setTables(Provider.MapColumns.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
        case MAPPING:
            qb.setProjectionMap(sPersonsProjectionMap);
            break;

        case MAPPING_ID:
            qb.setProjectionMap(sPersonsProjectionMap);
            qb.appendWhere(Provider.MapColumns._ID + "="
                    + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Provider.MapColumns.DEFAULT_SORT_ORDER;
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
        case MAPPING:
            return Provider.CONTENT_TYPE;
        case MAPPING_ID:
            return Provider.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != MAPPING) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        if(values.containsKey(Provider.MapColumns.CONTACT_ID) == false){
            throw new IllegalArgumentException("Illegal value: insert object must contain CONTACT_ID");
        }

        if(values.containsKey(Provider.MapColumns.GLOBAL_ID) == false){
            throw new IllegalArgumentException("Illegal value: insert object must contain GLOBAL_ID");
        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(Provider.MapColumns.TABLE_NAME, null, values);

        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(
                    Provider.MapColumns.CONTENT_URI, rowId);
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
        case MAPPING:
            count = db
                    .delete(Provider.MapColumns.TABLE_NAME, where, whereArgs);
            break;

        case MAPPING_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.delete(
                    Provider.MapColumns.TABLE_NAME,
                    Provider.MapColumns._ID
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
        case MAPPING:
            count = db.update(Provider.MapColumns.TABLE_NAME, values, where,
                    whereArgs);
            break;

        case MAPPING_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(
                    Provider.MapColumns.TABLE_NAME,
                    values,
                    Provider.MapColumns._ID
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
        sUriMatcher.addURI(Provider.MapColumns.AUTHORITY, "mapping",
                MAPPING);
        sUriMatcher.addURI(Provider.MapColumns.AUTHORITY, "mapping/#",
                MAPPING_ID);

        sPersonsProjectionMap = new HashMap<String, String>();
        sPersonsProjectionMap.put(Provider.MapColumns._ID,
                Provider.MapColumns._ID);
        sPersonsProjectionMap.put(Provider.MapColumns.CONTACT_ID,
                Provider.MapColumns.CONTACT_ID);
        sPersonsProjectionMap.put(Provider.MapColumns.GLOBAL_ID,
                Provider.MapColumns.GLOBAL_ID);
    }
}
