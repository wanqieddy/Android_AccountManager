package com.phicomm.account.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;
import com.phicomm.account.util.NetworkUtilities;

public class ContactUpload {
    public ArrayList<Contact> getUploadContactList(Context context){
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        Cursor cursorUp = context.getContentResolver().query(Provider.UploadDataColumns.CONTENT_URI, null, null, null, null);
        if(cursorUp != null){
            Contact contact = null;
            while(cursorUp.moveToNext()){
                contact = new Contact();
                String change_time = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.CHANGE_TIME));
                String contact_id = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.CONTACT_ID));
                String display_name = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.DISPLAY_NAME));
                String email = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.EMAIL));
                String group_member = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.GROUP_MEMBER));
                String home_address = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.HOME_ADDRESS));
                String im = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.IM));
                String mobile_phone = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.MOBILE_PHONE));
                String nick_name = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.NICK_NAME));
                String note = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.NOTE));
                String old_name = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.OLD_NAME));
                String old_phone = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.OLD_PHONE));
                String operate_id = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.OPERATE_ID));
                String organization = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.ORIGANIZATION));
                String photo_img = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.PHOTO_IMG));
                String telephone = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.TELEPHONE));
                String website = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.WEBSITE));
                String work_address = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.WORK_ADDRESS));
                String work_phone = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.WORK_PHONE));
                String old_email = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.OLD_EMAIL));
                String old_groupmember = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.OLD_GROUP_MEMBER));
                String old_homeaddress = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.OLD_HOME_ADDRESS));
                String old_im = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.OLD_IM));
                String old_nickname = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.OLD_NICK_NAME));
                String old_note = cursorUp.getString(cursorUp
                        .getColumnIndex(Provider.UploadDataColumns.OLD_NOTE));
                String old_organization = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.OLD_ORIGANIZATION));
                String old_photoimg = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.OLD_PHOTO_IMG));
                String old_telephone = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.OLD_TELEPHONE));
                String old_website = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.OLD_WEBSITE));
                String old_workaddress = cursorUp
                        .getString(cursorUp
                                .getColumnIndex(Provider.UploadDataColumns.OLD_WORK_ADDRESS));
                String old_workphone = cursorUp.getString(cursorUp.getColumnIndex(Provider.UploadDataColumns.OLD_WORK_PHONE));
                contact.setContactId(contact_id);
                contact.setChangeTime(change_time);
                contact.setDisplayName(display_name);
                contact.setEmail(email);
                contact.setGroupMember(group_member);
                contact.setHomeAddress(home_address);
                contact.setIm(im);
                contact.setMobilePhone(mobile_phone);
                contact.setNickName(nick_name);
                contact.setNote(note);
                contact.setOperateId(operate_id);
                contact.setOrganization(organization);
                contact.setPhotoImg(photo_img);
                contact.setTelephone(telephone);
                contact.setWebsite(website);
                contact.setWorkAddress(work_address);
                contact.setWorkphone(work_phone);
                contact.setOldEmail(old_email);
                contact.setOldGroupMember(old_groupmember);
                contact.setOldHomeAddress(old_homeaddress);
                contact.setOldIm(old_im);
                contact.setOldNickName(old_nickname);
                contact.setOldNote(old_nickname);
                contact.setOldName(old_name);
                contact.setOldPhone(old_phone);
                contact.setOldOrganization(old_organization);
                contact.setOldPhotoImg(old_photoimg);
                contact.setOldTelephone(old_telephone);
                contact.setOldWebsite(old_website);
                contact.setOldWorkAddress(old_workaddress);
                contact.setOldWorkphone(old_workphone);
                contactList.add(contact);
            }
            cursorUp.close();
        }
        return contactList;
    }

    public int uploadContactListToService(Context context){
        //ArrayList<Contact> contactList = getUploadContactList(context);

        ArrayList<Contact> contactList = getUploadContactList(context);
        try {
            Log.i("ss", "_____uploadContactListToService");
            String msg = NetworkUtilities.syncContactsUp(context, contactList);
            Log.i("ss","____________________msg:"+msg);
            ContactDownload load = new ContactDownload();
            ArrayList<Contact> list = load.downloadContactListFromService(msg, context);
            Log.i("ss","_________________list.size:"+list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
