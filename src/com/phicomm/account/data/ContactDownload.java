package com.phicomm.account.data;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.phicomm.account.provider.Person;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;
import com.phicomm.account.util.NetworkUtilities;
import com.phicomm.account.util.ReadDoc;
import com.phicomm.account.R;

public class ContactDownload {

    private ArrayList<Contact> insertContactList(
            ArrayList<Contact> contactList, Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Contact contact = new Contact();
        ContentValues values;
        String syncTime = null;
        for (int i = 0; i < contactList.size(); i++) {
            contact = contactList.get(i);
            values = new ContentValues();
            syncTime = contact.getSyncTime();
            values.put(Provider.SyncColumns.CONTACT_ID, contact.getContactId());
            values.put(Provider.SyncColumns.CHANGE_TIME,
                    contact.getChangeTime());
            values.put(Provider.SyncColumns.OPERATE_ID, contact.getOperateId());
            values.put(Provider.SyncColumns.DISPLAY_NAME,
                    contact.getDisplayName());
            values.put(Provider.SyncColumns.NICK_NAME, contact.getNickName());
            values.put(Provider.SyncColumns.WORK_ADDRESS,
                    contact.getWorkAddress());
            values.put(Provider.SyncColumns.HOME_ADDRESS,
                    contact.getHomeAddress());
            values.put(Provider.SyncColumns.ORGANIZATION,
                    contact.getOrganization());
            values.put(Provider.SyncColumns.MOBILE_PHONE,
                    contact.getMobilePhone());
            values.put(Provider.SyncColumns.TELEPHONE, contact.getTelephone());
            values.put(Provider.SyncColumns.WORKPHONE, contact.getWorkphone());
            values.put(Provider.SyncColumns.EMAIL, contact.getEmail());
            values.put(Provider.SyncColumns.WEBSITE, contact.getWebsite());
            values.put(Provider.SyncColumns.PHOTO_IMAGE, contact.getPhotoImg());
            values.put(Provider.SyncColumns.NOTE, contact.getNote());
            values.put(Provider.SyncColumns.IM, contact.getIm());
            values.put(Provider.SyncColumns.GROUP_MEMBER,
                    contact.getGroupMember());
            values.put(Provider.SyncColumns.GLOBAL_ID, contact.getGlobalId());
            // values.put(Provider.SyncColumns.OLD_NAME, contact.getOldName());
            // values.put(Provider.SyncColumns.OLD_PHONE,
            // contact.getOldPhone());
            // values.put(Provider.SyncColumns.OLD_EAMIL,
            // contact.getOldEmail());
            // values.put(Provider.SyncColumns.OLD_GROUPMEMBER,
            // contact.getOldGroupMember());
            // values.put(Provider.SyncColumns.OLD_HOMEADDRESS,
            // contact.getOldHomeAddress());
            contentResolver.insert(Provider.SyncColumns.CONTENT_URI, values);
        }
        insertSyncTimeToDataBase(context, syncTime);
        return contactList;
    }

    public ArrayList<Contact> downloadContactListFromService(String arg, Context context){

        ArrayList<Contact> contactList  = new ArrayList<Contact>();
        try {
            Log.i("ss","____________________msg:"+arg);
            StringReader sr = new StringReader(arg);

            InputSource is = new InputSource(sr);
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(is);
            contactList = ReadDoc.readXMLToContact(doc);
            insertContactList(contactList, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public void insertSyncTimeToDataBase(Context context, String syncTime) {
        int id = 1;
        Person p = new Person();
        ContentValues value = new ContentValues();
        value.put(Provider.PersonColumns.SYNC_TIME, p.syncTime);
        int i = context.getContentResolver().update(
                Provider.PersonColumns.CONTENT_URI, value,
                Provider.PersonColumns._ID + "=?", new String[] { id + "" });

    }

}
