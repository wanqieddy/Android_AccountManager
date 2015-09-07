package com.phicomm.account.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;

public class CheckDataOperate {
    private ContentResolver mContentResolver;

    public CheckDataOperate(Context context) {
        // TODO Auto-generated constructor stub
        mContentResolver = context.getContentResolver();
    }

    public int insertCheckData(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(Provider.CheckDataColumns.CONTACT_ID, contact.getContactId());
        values.put(Provider.CheckDataColumns.VERSION, contact.getVersion());
        values.put(Provider.CheckDataColumns.CHANGE_TIME,
                contact.getChangeTime());
        values.put(Provider.CheckDataColumns.OPERATE_ID, contact.getOperateId());
        values.put(Provider.CheckDataColumns.DISPLAY_NAME,
                contact.getDisplayName());
        values.put(Provider.CheckDataColumns.EMAIL, contact.getEmail());
        values.put(Provider.CheckDataColumns.NICK_NAME, contact.getNickName());
        values.put(Provider.CheckDataColumns.NOTE, contact.getNote());
        values.put(Provider.CheckDataColumns.ORIGANIZATION,
                contact.getOrganization());
        values.put(Provider.CheckDataColumns.HOME_ADDRESS,
                contact.getHomeAddress());
        values.put(Provider.CheckDataColumns.MOBILE_PHONE,
                contact.getMobilePhone());
        values.put(Provider.CheckDataColumns.IM, contact.getIm());
        values.put(Provider.CheckDataColumns.PHOTO_IMG, contact.getPhotoImg());
        values.put(Provider.CheckDataColumns.TELEPHONE, contact.getTelephone());
        values.put(Provider.CheckDataColumns.WEBSITE, contact.getWebsite());
        values.put(Provider.CheckDataColumns.GROUP_MEMBER,
                contact.getGroupMember());
        values.put(Provider.CheckDataColumns.WORK_PHONE, contact.getWorkphone());
        values.put(Provider.CheckDataColumns.WORK_ADDRESS,
                contact.getWorkAddress());
        Uri uri = mContentResolver.insert(
                Provider.CheckDataColumns.CONTENT_URI, values);
        String lastPath = uri.getLastPathSegment();
        return Integer.parseInt(lastPath);
    }

    public int deleteCheckData(String contactId) {

        return mContentResolver.delete(Provider.CheckDataColumns.CONTENT_URI,
                Provider.CheckDataColumns.CONTACT_ID + "=?",
                new String[] { contactId });
    }

    public boolean queryCheckDataExist(String contact_id) {
        Cursor cursor = mContentResolver.query(
                Provider.CheckDataColumns.CONTENT_URI, null,
                Provider.CheckDataColumns.CONTACT_ID + "=?",
                new String[] { contact_id }, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            }
        }
        return false;
    }

    public Contact getCheckContact(String contactIdWhere) {
        Cursor cursorCheck = mContentResolver.query(
                Provider.CheckDataColumns.CONTENT_URI, null,
                Provider.CheckDataColumns.CONTACT_ID + "=?",
                new String[] { contactIdWhere }, null);
        //Log.i("ss", "______________cursorCheck.size:" + cursorCheck.getCount());
        Contact contact = null;
        if (cursorCheck != null) {
            if (cursorCheck.moveToFirst()) {
                contact = new Contact();
                String contact_id = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.CONTACT_ID));
                String version = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.VERSION));
                String change_time = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.CHANGE_TIME));
                String display_name = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.DISPLAY_NAME));
                String email = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.EMAIL));
                String group_member = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.GROUP_MEMBER));
                String home_address = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.HOME_ADDRESS));
                String im = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.IM));
                String mobile_phone = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.MOBILE_PHONE));
                String nick_name = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.NICK_NAME));
                String note = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.NOTE));
                String organization = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.ORIGANIZATION));
                String photo_img = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.PHOTO_IMG));
                String telephone = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.TELEPHONE));
                String website = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.WEBSITE));
                String work_address = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.WORK_ADDRESS));
                String work_phone = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.WORK_PHONE));
                contact.setContactId(contact_id);
                contact.setVersion(version);
                contact.setChangeTime(change_time);
                contact.setDisplayName(display_name);
                contact.setEmail(email);
                contact.setGroupMember(group_member);
                contact.setHomeAddress(home_address);
                contact.setIm(im);
                contact.setMobilePhone(mobile_phone);
                contact.setNickName(nick_name);
                contact.setNote(note);
                contact.setOrganization(organization);
                contact.setPhotoImg(photo_img);
                contact.setTelephone(telephone);
                contact.setWebsite(website);
                contact.setWorkAddress(work_address);
                contact.setWorkphone(work_phone);
            }
            cursorCheck.close();
        }

        return contact;

    }

    public void getOldContactInfo(Contact contact, String contactId) {
        Cursor cursorCheck = mContentResolver.query(
                Provider.CheckDataColumns.CONTENT_URI, null,
                Provider.CheckDataColumns.CONTACT_ID + "=?",
                new String[] { contactId }, null);
        if (cursorCheck != null) {
            if (cursorCheck.moveToFirst()) {
                String old_name = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.DISPLAY_NAME));
                String old_email = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.EMAIL));
                String old_groupmember = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.GROUP_MEMBER));
                String old_homeaddress = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.HOME_ADDRESS));
                String old_im = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.IM));
                String old_phone = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.MOBILE_PHONE));
                String old_nickname = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.NICK_NAME));
                String old_note = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.NOTE));
                String old_organization = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.ORIGANIZATION));
                String old_photoimg = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.PHOTO_IMG));
                String old_telephone = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.TELEPHONE));
                String old_website = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.WEBSITE));
                String old_workaddress = cursorCheck
                        .getString(cursorCheck
                                .getColumnIndex(Provider.CheckDataColumns.WORK_ADDRESS));
                String old_workphone = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.WORK_PHONE));
                contact.setOldName(old_name);
                contact.setOldEmail(old_email);
                contact.setOldGroupMember(old_groupmember);
                contact.setOldHomeAddress(old_homeaddress);
                contact.setOldIm(old_im);
                contact.setOldPhone(old_phone);
                contact.setOldNickName(old_nickname);
                contact.setOldNote(old_note);
                contact.setOldOrganization(old_organization);
                contact.setOldPhotoImg(old_photoimg);
                contact.setOldTelephone(old_telephone);
                contact.setOldWebsite(old_website);
                contact.setOldWorkAddress(old_workaddress);
                contact.setOldWorkphone(old_workphone);
            }
            cursorCheck.close();
        }
    }

}
