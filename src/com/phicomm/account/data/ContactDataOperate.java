package com.phicomm.account.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.util.Log;

public class ContactDataOperate {
    private ContentResolver mContentResolver;
    private SyncDataOperate mSync;

    public ContactDataOperate(Context context) {
        // TODO Auto-generated constructor stub
        mContentResolver = context.getContentResolver();
        mSync = new SyncDataOperate(context);
    }

    public boolean isChange(String contact_id) {
        Cursor cursorData = mContentResolver.query(
                ContactsContract.Data.CONTENT_URI, null,
                ContactsContract.RawContacts.CONTACT_ID + "=?",
                new String[] { contact_id }, null);
        Cursor cursorCheck = mContentResolver.query(
                Provider.CheckDataColumns.CONTENT_URI, null,
                Provider.CheckDataColumns.CONTACT_ID + "=?",
                new String[] { contact_id }, null);
        boolean flag = false;
        if (cursorCheck != null && cursorData != null) {
            String versionData = "-1";
            String versionCheck = "0";
            if (cursorCheck.moveToFirst() && cursorData.moveToFirst()) {
                versionCheck = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.VERSION));
                versionData = cursorData.getString(cursorData
                        .getColumnIndex(ContactsContract.RawContacts.VERSION));

                String check = cursorCheck.getString(cursorCheck
                        .getColumnIndex(Provider.CheckDataColumns.CONTACT_ID));
                String data = cursorData
                        .getString(cursorData
                                .getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));
                //Log.i("ss", "_______________________check:" + check + "_____data:" + data);
                //Log.i("ss", "___________________________versionCheck:" + versionCheck + "____versionData:" + versionData);
                if (versionCheck.equals(versionData)) {
                    flag = false;
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }
            cursorCheck.close();
            cursorData.close();
        }
        return flag;
    }

    public Contact getContact(String contactIdWhere) {
        Contact contact = null;
        Cursor cursorData;
        if (contactIdWhere != null) {
            cursorData = mContentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI, null,
                    ContactsContract.Contacts._ID + "=?",
                    new String[] { contactIdWhere }, "_id desc");
        } else {
            cursorData = mContentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI, null, null, null,
                    "_id desc");
        }
        if (cursorData != null) {
            if (cursorData.moveToFirst()) {
                contact = new Contact();
                String displayName = cursorData
                        .getString(cursorData
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursorData.getString(cursorData
                        .getColumnIndex(ContactsContract.Contacts._ID));
                contact.setDisplayName(displayName);
                contact.setContactId(contactId);

                //Log.i("ss", "_____________________________displayName:"  + displayName + "_____contactId:" + contactId);

                int phoneCount = cursorData
                        .getInt(cursorData
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (phoneCount > 0) {
                    // contact phones
                    Cursor phones = mContentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
                    // ArrayList<ContactPhone> phoneList = new
                    // ArrayList<ContactPhone>();
                    // ContactPhone contactPhone = null;
                    if (phones != null) {
                        if (phones.moveToFirst()) {
                            do {
                                String phoneNumber = phones
                                        .getString(phones
                                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                int phoneType = phones
                                        .getInt(phones
                                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

                                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                                    contact.setMobilePhone(phoneNumber);
                                } else if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE) {
                                    contact.setWorkphone(phoneNumber);
                                }else if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_OTHER) {
                                    contact.setMobilePhone(phoneNumber);
                                }else if(phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_WORK){
                                    contact.setMobilePhone(phoneNumber);
                                }
                            } while (phones.moveToNext());
                        }
                        phones.close();
                    }

                }

                Cursor cursorVersion = mContentResolver.query(
                        ContactsContract.Data.CONTENT_URI, null,
                        ContactsContract.RawContacts.CONTACT_ID + "=?",
                        new String[] { contactId }, null);
                if (cursorVersion != null) {
                    if (cursorVersion.moveToFirst()) {
                        String version = cursorVersion
                                .getString(cursorVersion
                                        .getColumnIndex(ContactsContract.RawContacts.VERSION));
                        contact.setVersion(version);
                    }
                    cursorVersion.close();
                }
                // contact email
                Cursor emails = mContentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
                if (emails != null) {
                    if (emails.moveToFirst()) {
                        do {
                            String emailType = emails
                                    .getString(emails
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                            String emailValue = emails
                                    .getString(emails
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            contact.setEmail(emailValue);
                            Log.i("emailType", emailType);
                            Log.i("emailValue", emailValue);
                        } while (emails.moveToNext());
                    }
                    emails.close();
                }

                // contact IM
                Cursor IMs = mContentResolver.query(Data.CONTENT_URI,
                        new String[] { Data._ID, Im.PROTOCOL, Im.DATA },
                        Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
                                + Im.CONTENT_ITEM_TYPE + "'",
                        new String[] { contactId }, null);
                if (IMs != null) {
                    if (IMs.moveToFirst()) {
                        do {
                            String protocol = IMs.getString(IMs
                                    .getColumnIndex(Im.PROTOCOL));
                            String im = IMs.getString(IMs
                                    .getColumnIndex(Im.DATA));
                            contact.setIm(im);
                        } while (IMs.moveToNext());
                    }
                    IMs.close();
                }

                // contact address
                Cursor address = mContentResolver
                        .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + contactId, null, null);
                if (address != null) {
                    if (address.moveToFirst()) {
                        do {
                            String street = address
                                    .getString(address
                                            .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                            String city = address
                                    .getString(address
                                            .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                            String region = address
                                    .getString(address
                                            .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                            String postCode = address
                                    .getString(address
                                            .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                            String formatAddress = address
                                    .getString(address
                                            .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                            contact.setHomeAddress(street);
                        } while (address.moveToNext());
                    }
                    address.close();
                }

                // contact organization
                Cursor organizations = mContentResolver.query(Data.CONTENT_URI,
                        new String[] { Data._ID, Organization.COMPANY,
                                Organization.TITLE }, Data.CONTACT_ID + "=?"
                                + " AND " + Data.MIMETYPE + "='"
                                + Organization.CONTENT_ITEM_TYPE + "'",
                        new String[] { contactId }, null);
                if (organizations != null) {
                    if (organizations.moveToFirst()) {
                        do {
                            String company = organizations
                                    .getString(organizations
                                            .getColumnIndex(Organization.COMPANY));
                            String title = organizations
                                    .getString(organizations
                                            .getColumnIndex(Organization.TITLE));
                            contact.setOrganization(company);
                            // Log.i("company", company);
                            // Log.i("title", title);
                        } while (organizations.moveToNext());
                    }
                    organizations.close();
                }

                // note info
                Cursor notes = mContentResolver.query(Data.CONTENT_URI,
                        new String[] { Data._ID, Note.NOTE }, Data.CONTACT_ID
                                + "=?" + " AND " + Data.MIMETYPE + "='"
                                + Note.CONTENT_ITEM_TYPE + "'",
                        new String[] { contactId }, null);
                if (notes != null) {
                    if (notes.moveToFirst()) {
                        do {
                            String noteinfo = notes.getString(notes
                                    .getColumnIndex(Note.NOTE));
                            contact.setNote(noteinfo);
                        } while (notes.moveToNext());
                    }
                    notes.close();
                }

                // nickname info
                Cursor nicknames = mContentResolver.query(Data.CONTENT_URI,
                        new String[] { Data._ID, Nickname.NAME },
                        Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
                                + Nickname.CONTENT_ITEM_TYPE + "'",
                        new String[] { contactId }, null);
                if (nicknames != null) {
                    if (nicknames.moveToFirst()) {
                        do {
                            String nickName = nicknames.getString(nicknames
                                    .getColumnIndex(Nickname.NAME));
                            contact.setNickName(nickName);
                        } while (nicknames.moveToNext());
                    }
                    nicknames.close();
                }
                // website info
                Cursor website = mContentResolver.query(Data.CONTENT_URI,
                        new String[] { Data._ID, Website.URL }, Data.CONTACT_ID
                                + "=?" + " AND " + Data.MIMETYPE + "='"
                                + Website.CONTENT_ITEM_TYPE + "'",
                        new String[] { contactId }, null);
                if (website != null) {
                    if (website.moveToFirst()) {
                        do {
                            String websiteinfo = website.getString(website
                                    .getColumnIndex(Website.URL));
                            contact.setWebsite(websiteinfo);
                        } while (website.moveToNext());
                    }
                    website.close();
                }

            }

            cursorData.close();
        }

        return contact;
    }
    public List<Contact> getAddListContact() {
        List<Contact> list = new ArrayList<Contact>();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss ");
        Date curDate;
        String addTime = null;
        Contact contact = null;
        String mCheckLastcontactId = null;
        Cursor cursorCheck = mContentResolver.query(
                Provider.CheckDataColumns.CONTENT_URI,
                new String[] { Provider.CheckDataColumns.CONTACT_ID }, null,
                null, "contact_id desc");
        if (cursorCheck.moveToFirst()) {
            mCheckLastcontactId = cursorCheck.getString(cursorCheck
                    .getColumnIndex(Provider.CheckDataColumns.CONTACT_ID));
        }
        cursorCheck.close();
        String flag = mSync.querySyncDataExist(contact);
        if (mCheckLastcontactId == null) {
            Cursor cursorData = mContentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI, null, null, null,
                    null);
            while (cursorData.moveToNext()) {
                String contactId = cursorData.getString(cursorData
                        .getColumnIndex(ContactsContract.Contacts._ID));
                contact = getContact(contactId);
                curDate = new Date(System.currentTimeMillis());
                addTime = formatter.format(curDate);
                contact.setChangeTime(addTime);
                if(!flag.equals(Contact.OPERATOR_ID_INSERT)){
                list.add(contact);
                }
            }
            cursorData.close();
        } else {
            Cursor cursorData = mContentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    new String[] { ContactsContract.Contacts._ID },
                    ContactsContract.Contacts._ID + ">?",
                    new String[] { mCheckLastcontactId }, "_id desc");
            while (cursorData.moveToNext()) {
                String contactId = cursorData.getString(cursorData
                        .getColumnIndex(ContactsContract.Contacts._ID));
                curDate = new Date(System.currentTimeMillis());
                addTime = formatter.format(curDate);
                contact = getContact(contactId);
                contact.setChangeTime(addTime);
                if(!flag.equals(Contact.OPERATOR_ID_INSERT)){
                    list.add(contact);
                 }
            }
            cursorData.close();
        }
        return list;
    }
    public boolean queryContactDataExist(String contact_id) {
        Cursor cursor = mContentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null,
                ContactsContract.Contacts._ID + "=?",
                new String[] { contact_id }, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            }
        }
        return false;
    }

}
