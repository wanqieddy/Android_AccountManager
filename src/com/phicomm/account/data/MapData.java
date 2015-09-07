package com.phicomm.account.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.MapId;

import android.content.Context;

public class MapData {

    private ContentResolver mContentResolver;

    public MapData(Context context) {
        // TODO Auto-generated constructor stub
        mContentResolver = context.getContentResolver();
    }
    public boolean getMapGlobalIdExist(String contact_id) {
        boolean exist = false;
        Cursor cursor = mContentResolver.query(Provider.MapColumns.CONTENT_URI,
                null, Provider.MapColumns.GLOBAL_ID + "=?",
                new String[] { contact_id }, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                exist = true;
            }
            cursor.close();
        }
        return exist;
    }
    public boolean getMapContactIdExist(String contact_id) {
        boolean exist = false;
        Cursor cursor = mContentResolver.query(Provider.MapColumns.CONTENT_URI,
                null, Provider.MapColumns.CONTACT_ID + "=?",
                new String[] { contact_id }, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                exist = true;
            }
            cursor.close();
        }
        return exist;
    }
    public String getMapGlobalId(String contact_id) {
        String globalId = null;
        Cursor cursor = mContentResolver.query(Provider.MapColumns.CONTENT_URI,
                null, Provider.MapColumns.GLOBAL_ID + "=?",
                new String[] { contact_id }, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                globalId = cursor.getString(cursor
                        .getColumnIndexOrThrow(Provider.MapColumns.GLOBAL_ID));
            }
            cursor.close();
        }
        return globalId;
    }

    public String getMapContactId(String contact_id) {
        String globalId = null;
        Cursor cursor = mContentResolver.query(Provider.MapColumns.CONTENT_URI,
                null, Provider.MapColumns.CONTACT_ID + "=?",
                new String[] { contact_id }, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                globalId = cursor.getString(cursor
                        .getColumnIndexOrThrow(Provider.MapColumns.CONTACT_ID));
            }
            cursor.close();
        }
        return globalId;
    }
    public void insertMap(MapId map){
        ContentValues values = new ContentValues();
        values.put(Provider.MapColumns.CONTACT_ID, map.getMobileContactId());
        values.put(Provider.MapColumns.GLOBAL_ID, map.getServiceContactId());
        mContentResolver.insert(Provider.MapColumns.CONTENT_URI, values);
    }
}