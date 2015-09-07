package com.phicomm.account.data;

import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import com.phicomm.account.provider.DatabaseHelper;
import android.util.Log;

/**
 * Static methods to update contact information.
 */
public class ContactsOperate {

    private ContentResolver mContentResolver;
    private Context mContext;
    private Contact mContact;

    public ContactsOperate(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;
        mContentResolver = context.getContentResolver();
    }

    public boolean queryDataExist(String contact_id) {
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

    public void queryContact() {
        Cursor cursor = mContentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[] { Data._ID }, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String rawContactId = "";
                String id = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                Log.i("contactID", id);
                Cursor rawContactCur = mContentResolver.query(
                        RawContacts.CONTENT_URI, null, RawContacts._ID + "=?",
                        new String[] { id }, null);
                if (rawContactCur.moveToFirst()) {
                    rawContactId = rawContactCur.getString(rawContactCur
                            .getColumnIndex(RawContacts._ID));
                    Log.i("rawContactID", rawContactId);
                }
                rawContactCur.close();
                if (Integer
                        .parseInt(cursor.getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor phoneCur = mContentResolver
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID
                                            + "=?",
                                    new String[] { rawContactId }, null);
                    while (phoneCur.moveToNext()) {
                        String number = phoneCur
                                .getString(phoneCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("number", number);
                        String type = phoneCur
                                .getString(phoneCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        Log.i("type", type);
                    }
                    phoneCur.close();
                }
            }
            cursor.close();
        }
    }

    public Context getContext() {
        return mContext;
    }

    public ContentResolver getContentResolver() {
        return mContentResolver;
    }

    public void insertContact(Contact contact) {
        if (contact == null) {
            return;
        }
        mContentResolver = this.getContext().getContentResolver();
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(
                RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        if (!contact.getDisplayName().equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
            values.put(StructuredName.DISPLAY_NAME, contact.getDisplayName());
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
        if (!contact.getMobilePhone().equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
            values.put(Phone.NUMBER, contact.getMobilePhone());
            values.put(Phone.TYPE, Phone.TYPE_MOBILE);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
        if (!contact.getEmail().equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
            values.put(Email.DATA, contact.getEmail());
            values.put(Email.TYPE, Email.TYPE_WORK);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
        if (!contact.getIm().equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Im.CONTENT_ITEM_TYPE);
            values.put(Im.DATA, contact.getIm());
            values.put(Im.PROTOCOL, Im.PROTOCOL_QQ);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
        if (!contact.getOrganization().equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
            values.put(Organization.COMPANY, contact.getOrganization());
            values.put(Organization.TYPE, Organization.TYPE_WORK);
            getContentResolver().insert(
                    android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
        if (!contact.getWorkphone().equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
            values.put(Phone.NUMBER, contact.getWorkphone());
            values.put(Phone.TYPE, Phone.TYPE_WORK);
            getContentResolver().insert(
                    android.provider.ContactsContract.Data.CONTENT_URI, values);

        }
        if (!contact.getHomeAddress().equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, StructuredPostal.CONTENT_ITEM_TYPE);
            values.put(StructuredPostal.STREET, contact.getHomeAddress());
            values.put(StructuredPostal.TYPE, StructuredPostal.TYPE_WORK);
            getContentResolver().insert(
                    android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
        if (!contact.getNote().equals("")) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Note.CONTENT_ITEM_TYPE);
            values.put(Note.NOTE, contact.getNote());
            getContentResolver().insert(
                    android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
        if (!contact.getNickName().equals("")) {
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Nickname.CONTENT_ITEM_TYPE);
            values.put(Nickname.NAME, contact.getNickName());
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
        if (!contact.getWebsite().equals("")) {
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Website.CONTENT_ITEM_TYPE);
            values.put(Website.URL, contact.getWebsite());
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
        }
    }

    public void deleteContact(long rawContactId) {
        getContentResolver().delete(
                ContentUris.withAppendedId(RawContacts.CONTENT_URI,
                        rawContactId), null, null);
    }

    public void UpdateSyncToContact(){
        Cursor synccontent = mContext.getContentResolver().
                query(Provider.SyncColumns.CONTENT_URI, null, null, null, null);

        if(synccontent.getCount() <= 0) {
            Log.i("ContactsOperate", "Sync Table is empty!");
            return;
        }

        while(synccontent.moveToNext()){
            long contact_id = synccontent.getInt(synccontent.getColumnIndex(Provider.SyncColumns.CONTACT_ID));
            String operate = synccontent.getString(synccontent.getColumnIndex(Provider.SyncColumns.OPERATE_ID));
            String global_id = synccontent.getString(synccontent.getColumnIndex(Provider.SyncColumns.GLOBAL_ID));

            if(contact_id == -1){//not local record, add to local db
                mContact = getInfoFromSymbolCursor(synccontent);
                long rawContactId = AddContactFromSync(mContact);
                long contactId = getContactIdByRawContactId(rawContactId);
                createMapping(""+contactId, global_id);
            }else{
                if(operate.equals(DatabaseHelper.OPERATE_DELETE)){//delete
                    deleteContactByContactId(contact_id);
                }else if(operate.equals(DatabaseHelper.OPERATE_ADD)){//insert
                    mContact = getInfoFromSymbolCursor(synccontent);
                    long rawContactId = AddContactFromSync(mContact);
                    long contactId = getContactIdByRawContactId(rawContactId);

                    createMapping(""+contactId, global_id);
                }else{//update
                    long rawContactId = getRawContactIdByContactId(contact_id);
                    if(rawContactId != -1){
                       // updataCotact(rawContactId);
                    }
                }
            }
        }

        if(synccontent != null){
            synccontent.close();
        }
    }


    public void createMapping(String contact_id, String global_id){
        ContentValues values = new ContentValues();
        values.put(Provider.MapColumns.CONTACT_ID, contact_id);
        values.put(Provider.MapColumns.GLOBAL_ID, global_id);
        getContext().getContentResolver().insert(Provider.MapColumns.CONTENT_URI, values);
    }

   private long getContactIdFromMap(String global_id){
       long defaultGlobalId = -1;

        Cursor mappingContent = getContext().getContentResolver().
                query(Provider.MapColumns.CONTENT_URI, new String[]{Provider.MapColumns.CONTACT_ID},
                        Provider.MapColumns.GLOBAL_ID+"=?", new String[]{global_id}, null);

        if(mappingContent != null && mappingContent.getCount() != 0){
            mappingContent.moveToFirst();
            defaultGlobalId = mappingContent.getLong(0);
        }

        if(mappingContent != null){
            mappingContent.close();
        }

        return defaultGlobalId;
    }

    private void deleteContactByContactId(long ContactId) {
        getContentResolver().delete(
                RawContacts.CONTENT_URI, RawContacts.CONTACT_ID, new String[]{""+ContactId});
    }

    private void deleteMappByGlobalId(String global_id){
        //delete item in mapping table by global id
        getContentResolver().delete(Provider.MapColumns.CONTENT_URI,
                Provider.MapColumns.GLOBAL_ID+"=?", new String[]{global_id});
    }

    private int getRawContactIdByContactId(long contact_id){
        int rawContactId = -1;

        Cursor rawContactIdCursor =
                getContext().getContentResolver().query(RawContacts.CONTENT_URI,
                        new String[]{RawContacts._ID}, RawContacts.CONTACT_ID+"=?", new String[]{""+contact_id}, null);

        rawContactIdCursor.moveToFirst();
        if(rawContactIdCursor != null && rawContactIdCursor.getCount() > 0){
            rawContactId = rawContactIdCursor.getInt(0);
        }

        if(rawContactIdCursor != null){
            rawContactIdCursor.close();
        }

        return rawContactId;
    }


    private long getContactIdByRawContactId(long rawContactId){
        long contactId = -1;

        Cursor contactIdCursor =
                getContext().getContentResolver().query(RawContacts.CONTENT_URI,
                        new String[]{RawContacts.CONTACT_ID}, RawContacts._ID+"=?", new String[]{""+rawContactId}, null);

        contactIdCursor.moveToFirst();

        if(contactIdCursor != null && contactIdCursor.getCount() > 0){
            contactId = contactIdCursor.getLong(0);
        }

        if(contactIdCursor != null){
            contactIdCursor.close();
        }

        return contactId;
    }


    public Contact getInfoFromSymbolCursor(Cursor c){
        Contact contact = new Contact();

        contact.setDisplayName(c.getString(c.getColumnIndex(Provider.SyncColumns.DISPLAY_NAME)));
        contact.setEmail(c.getString(c.getColumnIndex(Provider.SyncColumns.EMAIL)));
        contact.setNickName(c.getString(c.getColumnIndex(Provider.SyncColumns.NICK_NAME)));
        contact.setWorkAddress(c.getString(c.getColumnIndex(Provider.SyncColumns.WORK_ADDRESS)));
        contact.setHomeAddress(c.getString(c.getColumnIndex(Provider.SyncColumns.HOME_ADDRESS)));
        contact.setOrganization(c.getString(c.getColumnIndex(Provider.SyncColumns.ORGANIZATION)));
        contact.setMobilePhone(c.getString(c.getColumnIndex(Provider.SyncColumns.MOBILE_PHONE)));
        contact.setTelephone(c.getString(c.getColumnIndex(Provider.SyncColumns.TELEPHONE)));
        contact.setWorkphone(c.getString(c.getColumnIndex(Provider.SyncColumns.WORKPHONE)));
        contact.setWebsite(c.getString(c.getColumnIndex(Provider.SyncColumns.WEBSITE)));
        contact.setNote(c.getString(c.getColumnIndex(Provider.SyncColumns.NOTE)));
        contact.setIm(c.getString(c.getColumnIndex(Provider.SyncColumns.IM)));
        contact.setGroupMember(c.getString(c.getColumnIndex(Provider.SyncColumns.GROUP_MEMBER)));

        return contact;
    }


    public long AddContactFromSync(Contact contact){
        ContentValues values = new ContentValues();

        if(contact == null){
            return -1;
        }

        //insert a null value
        Uri rawContentUri = getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContentId = ContentUris.parseId(rawContentUri);

        //diaplayname
        if(contact.getDisplayName() != null){
            values.clear();
            values.put(StructuredName.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
            values.put(StructuredName.DISPLAY_NAME, contact.getDisplayName());
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //mobilephone
        if(contact.getMobilePhone() != null){
            values.clear();
            values.put(Phone.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
            values.put(Phone.NUMBER, contact.getMobilePhone());
            values.put(Phone.DATA2, 2);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //email
        if(contact.getEmail() != null){
            values.clear();
            values.put(CommonDataKinds.Email.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            values.put(CommonDataKinds.Email.ADDRESS, contact.getEmail());
            values.put(CommonDataKinds.Email.DATA2, 2);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //nickname
        if(contact.getNickName() != null){
            values.clear();
            values.put(CommonDataKinds.Nickname.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, CommonDataKinds.Nickname.CONTENT_ITEM_TYPE);
            values.put(CommonDataKinds.Nickname.NAME, contact.getNickName());
            values.put(CommonDataKinds.Nickname.DATA2, 1);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //work address
        if(contact.getWorkAddress() != null){
            values.clear();
            values.put(CommonDataKinds.StructuredPostal.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
            values.put(CommonDataKinds.StructuredPostal.DATA1, contact.getWorkAddress());
            values.put(CommonDataKinds.StructuredPostal.DATA2, 2);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //home address
        if(contact.getHomeAddress() != null){
            values.clear();
            values.put(CommonDataKinds.StructuredPostal.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
            values.put(CommonDataKinds.StructuredPostal.DATA1, contact.getHomeAddress());
            values.put(CommonDataKinds.StructuredPostal.DATA2, 1);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //organization
        if(contact.getOrganization() != null){
            values.clear();
            values.put(CommonDataKinds.Organization.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, CommonDataKinds.Organization.CONTENT_ITEM_TYPE);
            values.put(CommonDataKinds.Organization.COMPANY, contact.getOrganization());
            values.put(CommonDataKinds.Organization.DATA2, 1);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //telephone
        if(contact.getTelephone() != null){
            values.clear();
            values.put(Phone.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
            values.put(Phone.NUMBER, contact.getTelephone());
            values.put(Phone.DATA2, 1);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //workphone
        if(contact.getWorkphone() != null){
            values.clear();
            values.put(Phone.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
            values.put(Phone.NUMBER, contact.getWorkphone());
            values.put(Phone.DATA2, 3);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //website
        if(contact.getWebsite() != null){
            values.clear();
            values.put(CommonDataKinds.Website.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, "12");
            values.put(CommonDataKinds.Website.URL, contact.getWebsite());
            values.put(CommonDataKinds.Website.DATA2, 7);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //note
        if(contact.getNote() != null){
            values.clear();
            values.put(CommonDataKinds.Note.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, "13");
            values.put(CommonDataKinds.Note.NOTE, contact.getNote());
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //im
        if(contact.getIm() != null){
            values.clear();
            values.put(CommonDataKinds.Im.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, CommonDataKinds.Im.CONTENT_ITEM_TYPE);
            values.put(CommonDataKinds.Im.DATA1, contact.getIm());
            values.put(CommonDataKinds.Im.DATA2, "3");
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        //groupmembwe
        if(contact.getGroupMember() != null){
            values.clear();
            values.put(CommonDataKinds.GroupMembership.RAW_CONTACT_ID, rawContentId);
            values.put(Data.MIMETYPE, "14");
            values.put(CommonDataKinds.GroupMembership.GROUP_ROW_ID, contact.getGroupMember());
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        return rawContentId;
    }

    public void deleteContactIdInMapAndContacts(String globalId) {
        Log.i("ss",
                "__________________deleteContactIdInMapAndContacts globalId :"
                        + globalId);
        long contactId = getContactIdFromMap(globalId);
        long rawContactId = getRawContactIdByContactId(contactId);
        Log.i("ss",
                "__________________deleteContactIdInMapAndContacts contactId :"
                        + contactId);
        Log.i("ss",
                "__________________deleteContactIdInMapAndContacts rawContactId :"
                        + rawContactId);
        deleteContact(rawContactId);
        deleteMappByGlobalId(globalId);

    }

    public void addContactIdInMapAndContacts(String globalId, Contact contact) {
        Log.i("ss", "__________________global_id :" + globalId);
        long rawContactId = AddContactFromSync(contact);
        long contactId = getContactIdByRawContactId(rawContactId);
        Log.i("ss", "__________________contact_id :" + ("" + contactId));
        createMapping("" + contactId, globalId);
    }

    public void updateContact(String globalId, Contact contact) {
        Log.i("ss", "__________________global_id :" + globalId);
        long contactId = getContactIdFromMap(globalId);
        long rawContactId = getRawContactIdByContactId(contactId);
        updataCotact(contact, globalId);
    }

    public void updataCotact(Contact contact, String globalId) {
        long contactId = getContactIdFromMap(globalId);
        long rawContactId = getRawContactIdByContactId(contactId);
        Log.i("ss", "__________________rawContactId :" + rawContactId);
        ContentValues values = new ContentValues();
        values.clear();
        values.put(StructuredName.DISPLAY_NAME, contact.getDisplayName());
        String where = ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                + Data.MIMETYPE + "=?";
        String[] selectionName = new String[] { String.valueOf(rawContactId),
                StructuredName.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionName);

        values.clear();
        values.put(Phone.NUMBER, contact.getMobilePhone());
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        String[] selectionPhone = new String[] { String.valueOf(rawContactId),
                Phone.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionPhone);

        values.clear();
        values.put(Phone.NUMBER, contact.getTelephone());
        values.put(Phone.TYPE, Phone.TYPE_HOME);
        String[] selectionTelephone = new String[] {
                String.valueOf(rawContactId), Phone.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionTelephone);

        values.clear();
        values.put(Phone.NUMBER, contact.getWorkphone());
        values.put(Phone.TYPE, Phone.TYPE_WORK);
        String[] selectionWorkphone = new String[] {
                String.valueOf(rawContactId), Phone.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionWorkphone);

        values.clear();
        values.put(Email.DATA, contact.getEmail());
        String[] selectionEmail = new String[] { String.valueOf(rawContactId),
                Email.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionEmail);

        values.clear();
        values.put(CommonDataKinds.Im.DATA1, contact.getIm());
        String[] selectionIm = new String[] { String.valueOf(rawContactId),
                Im.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionIm);

        values.clear();
        values.put(CommonDataKinds.StructuredPostal.DATA1,
                contact.getHomeAddress());
        values.put(StructuredPostal.TYPE, StructuredPostal.TYPE_HOME);
        String[] selectionHomeAddress = new String[] {
                String.valueOf(rawContactId),
                StructuredPostal.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionHomeAddress);

        values.clear();
        values.put(CommonDataKinds.StructuredPostal.DATA1,
                contact.getWorkAddress());
        values.put(StructuredPostal.TYPE, StructuredPostal.TYPE_WORK);
        String[] selectionWorkAddress = new String[] {
                String.valueOf(rawContactId),
                StructuredPostal.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionWorkAddress);

        values.clear();
        values.put(CommonDataKinds.Organization.COMPANY,
                contact.getOrganization());
        String[] selectionOrganization = new String[] {
                String.valueOf(rawContactId), Organization.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionOrganization);

        values.clear();
        values.put(CommonDataKinds.Note.NOTE, contact.getNote());
        String[] selectionNote = new String[] { String.valueOf(rawContactId),
                Note.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionNote);

        values.clear();
        values.put(CommonDataKinds.Nickname.NAME, contact.getNickName());
        String[] selectionNickname = new String[] {
                String.valueOf(rawContactId), Nickname.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionNickname);

        values.clear();
        values.put(CommonDataKinds.Website.URL, contact.getWebsite());
        values.put(CommonDataKinds.Website.TYPE,
                CommonDataKinds.Website.TYPE_HOMEPAGE);
        String[] selectionWebsite = new String[] {
                String.valueOf(rawContactId), Website.CONTENT_ITEM_TYPE };
        getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionWebsite);
    }
}
