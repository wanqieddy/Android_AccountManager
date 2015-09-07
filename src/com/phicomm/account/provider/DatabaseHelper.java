package com.phicomm.account.provider;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_data.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;
    public static final String OPERATE_UPDATE = "U";
    public static final String OPERATE_ADD = "A";
    public static final String OPERATE_DELETE = "D";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Provider.PersonColumns.TABLE_NAME + " ("
                + Provider.PersonColumns._ID + " INTEGER PRIMARY KEY,"
                + Provider.PersonColumns.NAME + " TEXT,"
                + Provider.PersonColumns.USER_KEY + " TEXT,"
                + Provider.PersonColumns.USER_ID + " TEXT,"
                + Provider.PersonColumns.JSSESSIONID + " TEXT,"
                + Provider.PersonColumns.TRUE_NAME + " TEXT,"
                + Provider.PersonColumns.USER_SEX + " TEXT,"
                + Provider.PersonColumns.USER_BIRTH + " TEXT,"
                + Provider.PersonColumns.USER_ADDRESS + " TEXT,"
                + Provider.PersonColumns.USER_EMAIL + " TEXT,"
                + Provider.PersonColumns.CREATETIME + " TEXT,"
                + Provider.PersonColumns.PHONE + " TEXT,"
                + Provider.PersonColumns.NICK_NAME + " TEXT,"
                + Provider.PersonColumns.USER_IMAGE + " TEXT,"   
                + Provider.PersonColumns.PASSWORD + " TEXT,"
                + Provider.PersonColumns.SYNC_TIME + " TEXT,"
                + Provider.PersonColumns.CONTACT_SWITCHER_SELECTED
                + " INTEGER,"
                + Provider.PersonColumns.AGE + " INTEGER" + ");");

        db.execSQL("CREATE TABLE " + Provider.CheckDataColumns.TABLE_NAME + " ("
                + Provider.CheckDataColumns._ID + " INTEGER PRIMARY KEY,"
                + Provider.CheckDataColumns.CONTACT_ID + " INTEGER,"
                + Provider.CheckDataColumns.VERSION + " INTEGER,"
                + Provider.CheckDataColumns.CHANGE_TIME + " TEXT,"
                + Provider.CheckDataColumns.DISPLAY_NAME + " TEXT,"
                + Provider.CheckDataColumns.GROUP_MEMBER + " TEXT,"
                + Provider.CheckDataColumns.HOME_ADDRESS + " TEXT,"
                + Provider.CheckDataColumns.IM + " TEXT,"
                + Provider.CheckDataColumns.MOBILE_PHONE + " INTEGER,"
                + Provider.CheckDataColumns.NICK_NAME + " TEXT,"
                + Provider.CheckDataColumns.NOTE + " TEXT,"
                + Provider.CheckDataColumns.OPERATE_ID + " INTEGER,"
                + Provider.CheckDataColumns.ORIGANIZATION + " TEXT,"
                + Provider.CheckDataColumns.PHOTO_IMG + " TEXT,"
                + Provider.CheckDataColumns.WEBSITE + " TEXT,"
                + Provider.CheckDataColumns.WORK_ADDRESS + " TEXT,"
                + Provider.CheckDataColumns.WORK_PHONE + " INTEGER,"
                + Provider.CheckDataColumns.TELEPHONE + " INTEGER,"
                + Provider.CheckDataColumns.EMAIL + " TEXT" + ");");

        // add upload data database.
        db.execSQL("CREATE TABLE " + Provider.UploadDataColumns.TABLE_NAME
                + " (" + Provider.UploadDataColumns._ID
                + " INTEGER PRIMARY KEY,"
                + Provider.UploadDataColumns.CONTACT_ID + " INTEGER,"
                + Provider.UploadDataColumns.CHANGE_TIME + " TEXT,"
                + Provider.UploadDataColumns.DISPLAY_NAME + " TEXT,"
                + Provider.UploadDataColumns.GROUP_MEMBER + " TEXT,"
                + Provider.UploadDataColumns.HOME_ADDRESS + " TEXT,"
                + Provider.UploadDataColumns.IM + " TEXT,"
                + Provider.UploadDataColumns.MOBILE_PHONE + " INTEGER,"
                + Provider.UploadDataColumns.NICK_NAME + " TEXT,"
                + Provider.UploadDataColumns.NOTE + " TEXT,"
                + Provider.UploadDataColumns.OPERATE_ID + " INTEGER,"
                + Provider.UploadDataColumns.ORIGANIZATION + " TEXT,"
                + Provider.UploadDataColumns.PHOTO_IMG + " TEXT,"
                + Provider.UploadDataColumns.WEBSITE + " TEXT,"
                + Provider.UploadDataColumns.WORK_ADDRESS + " TEXT,"
                + Provider.UploadDataColumns.WORK_PHONE + " INTEGER,"
                + Provider.UploadDataColumns.EMAIL + " TEXT,"
                + Provider.UploadDataColumns.TELEPHONE + " INTEGER,"
                + Provider.UploadDataColumns.OLD_NAME + " TEXT,"
                + Provider.UploadDataColumns.OLD_PHONE + " INTEGER,"
                + Provider.UploadDataColumns.OLD_GROUP_MEMBER + " TEXT,"
                + Provider.UploadDataColumns.OLD_HOME_ADDRESS + " TEXT,"
                + Provider.UploadDataColumns.OLD_IM + " TEXT,"
                + Provider.UploadDataColumns.OLD_NICK_NAME + " TEXT,"
                + Provider.UploadDataColumns.OLD_NOTE + " TEXT,"
                + Provider.UploadDataColumns.OLD_ORIGANIZATION + " TEXT,"
                + Provider.UploadDataColumns.OLD_PHOTO_IMG + " TEXT,"
                + Provider.UploadDataColumns.OLD_WEBSITE + " TEXT,"
                + Provider.UploadDataColumns.OLD_WORK_ADDRESS + " TEXT,"
                + Provider.UploadDataColumns.OLD_WORK_PHONE + " INTEGER,"
                + Provider.UploadDataColumns.OLD_TELEPHONE + " INTEGER,"
                + Provider.UploadDataColumns.OLD_EMAIL + " TEXT,"
                + Provider.UploadDataColumns.GLOBAL_ID + " TEXT" + ");");

        // add sync data database.
        db.execSQL("CREATE TABLE " + Provider.SyncColumns.TABLE_NAME + " ("
                + Provider.SyncColumns._ID + " INTEGER PRIMARY KEY,"
                + Provider.SyncColumns.CONTACT_ID + " INTEGER,"
                + Provider.SyncColumns.CHANGE_TIME + " TEXT,"
                + Provider.SyncColumns.DISPLAY_NAME + " TEXT,"
                + Provider.SyncColumns.GROUP_MEMBER + " TEXT,"
                + Provider.SyncColumns.HOME_ADDRESS + " TEXT,"
                + Provider.SyncColumns.IM + " TEXT,"
                + Provider.SyncColumns.MOBILE_PHONE + " INTEGER,"
                + Provider.SyncColumns.NICK_NAME + " TEXT,"
                + Provider.SyncColumns.NOTE + " TEXT,"
                + Provider.SyncColumns.OPERATE_ID + " INTEGER,"
                + Provider.SyncColumns.ORGANIZATION + " TEXT,"
                + Provider.SyncColumns.PHOTO_IMAGE + " TEXT,"
                + Provider.SyncColumns.WEBSITE + " TEXT,"
                + Provider.SyncColumns.WORK_ADDRESS + " TEXT,"
                + Provider.SyncColumns.WORKPHONE + " INTEGER,"
                + Provider.SyncColumns.TELEPHONE + " INTEGER,"
//                + Provider.SyncColumns.OLD_NAME + " TEXT,"
//                + Provider.SyncColumns.OLD_EAMIL + " TEXT,"
//                + Provider.SyncColumns.OLD_PHONE + " INTEGER,"
                + Provider.SyncColumns.EMAIL + " TEXT,"
                + Provider.SyncColumns.GLOBAL_ID + " TEXT"+ ");");
        // add mapping table in database.
        db.execSQL("CREATE TABLE " + Provider.MapColumns.TABLE_NAME + " ("
                + Provider.MapColumns._ID + " INTEGER PRIMARY KEY,"
                + Provider.MapColumns.CONTACT_ID + " INTEGER,"
                + Provider.MapColumns.GLOBAL_ID + " TEXT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Provider.PersonColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "
                + Provider.CheckDataColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "
                + Provider.UploadDataColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Provider.SyncColumns.TABLE_NAME);
        onCreate(db);
    }
}