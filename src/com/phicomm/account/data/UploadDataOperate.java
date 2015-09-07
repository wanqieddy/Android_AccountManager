package com.phicomm.account.data;

import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class UploadDataOperate {
    private ContentResolver mContentResolver;

    public UploadDataOperate(Context context) {
        // TODO Auto-generated constructor stub
        mContentResolver = context.getContentResolver();
    }

    public int insertUploadData(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(Provider.UploadDataColumns.CONTACT_ID,
                contact.getContactId());
        values.put(Provider.UploadDataColumns.CHANGE_TIME,
                contact.getChangeTime());
        values.put(Provider.UploadDataColumns.OPERATE_ID,
                contact.getOperateId());
        values.put(Provider.UploadDataColumns.DISPLAY_NAME,
                contact.getDisplayName());
        values.put(Provider.UploadDataColumns.EMAIL, contact.getEmail());
        values.put(Provider.UploadDataColumns.NICK_NAME, contact.getNickName());
        values.put(Provider.UploadDataColumns.NOTE, contact.getNote());
        values.put(Provider.UploadDataColumns.ORIGANIZATION,
                contact.getOrganization());
        values.put(Provider.UploadDataColumns.HOME_ADDRESS,
                contact.getHomeAddress());
        values.put(Provider.UploadDataColumns.IM, contact.getIm());
        values.put(Provider.UploadDataColumns.MOBILE_PHONE,
                contact.getMobilePhone());
        values.put(Provider.UploadDataColumns.PHOTO_IMG, contact.getPhotoImg());
        values.put(Provider.UploadDataColumns.TELEPHONE, contact.getTelephone());
        values.put(Provider.UploadDataColumns.WEBSITE, contact.getWebsite());
        values.put(Provider.UploadDataColumns.GROUP_MEMBER,
                contact.getGroupMember());
        values.put(Provider.UploadDataColumns.WORK_PHONE,
                contact.getWorkphone());
        values.put(Provider.UploadDataColumns.WORK_ADDRESS,
                contact.getWorkAddress());
        values.put(Provider.UploadDataColumns.OLD_EMAIL, contact.getOldEmail());
        values.put(Provider.UploadDataColumns.OLD_NICK_NAME,
                contact.getOldNickName());
        values.put(Provider.UploadDataColumns.OLD_NOTE, contact.getOldNote());
        values.put(Provider.UploadDataColumns.OLD_ORIGANIZATION,
                contact.getOldOrganization());
        values.put(Provider.UploadDataColumns.OLD_HOME_ADDRESS,
                contact.getOldHomeAddress());
        values.put(Provider.UploadDataColumns.OLD_IM, contact.getOldIm());
        values.put(Provider.UploadDataColumns.OLD_NAME, contact.getOldName());
        values.put(Provider.UploadDataColumns.OLD_PHONE, contact.getOldPhone());
        values.put(Provider.UploadDataColumns.OLD_PHOTO_IMG,
                contact.getOldPhotoImg());
        values.put(Provider.UploadDataColumns.OLD_TELEPHONE,
                contact.getOldTelephone());
        values.put(Provider.UploadDataColumns.OLD_WEBSITE,
                contact.getOldWebsite());
        values.put(Provider.UploadDataColumns.OLD_GROUP_MEMBER,
                contact.getOldGroupMember());
        values.put(Provider.UploadDataColumns.OLD_WORK_PHONE,
                contact.getOldWorkphone());
        values.put(Provider.UploadDataColumns.OLD_WORK_ADDRESS,
                contact.getOldWorkAddress());
        values.put(Provider.UploadDataColumns.GLOBAL_ID,
                contact.getGlobalId());

        Uri uri = mContentResolver.insert(
                Provider.UploadDataColumns.CONTENT_URI, values);
        String lastPath = uri.getLastPathSegment();
        return Integer.parseInt(lastPath);
    }

    public boolean queryUploadDataExist(String contact_id) {
        Cursor cursorUpload = mContentResolver.query(
                Provider.UploadDataColumns.CONTENT_URI, null,
                Provider.UploadDataColumns.CONTACT_ID + "=?",
                new String[] { contact_id }, null);
        if (cursorUpload != null) {
            if (cursorUpload.getCount() == 1) {
                cursorUpload.close();
                return true;
            }
        }
        return false;
    }

    public int deleteUploadData(String contactId) {

        return mContentResolver.delete(Provider.UploadDataColumns.CONTENT_URI,
                Provider.UploadDataColumns.CONTACT_ID + "=?",
                new String[] { contactId });
    }

    public boolean judgeUploadDataExist() {

        Cursor cursorUpload = mContentResolver.query(
                Provider.UploadDataColumns.CONTENT_URI, null, null, null, null);
        if (cursorUpload != null) {
            if (cursorUpload.getCount() > 0) {
                cursorUpload.close();
                return true;
            }
            cursorUpload.close();
        }
        return false;
    }
}
