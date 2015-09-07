package com.phicomm.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.phicomm.account.data.CheckDataOperate;
import com.phicomm.account.data.ContactDataOperate;
import com.phicomm.account.data.MapData;
import com.phicomm.account.data.SyncDataOperate;
import com.phicomm.account.data.UploadDataOperate;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.requestmanager.Request;
import com.phicomm.account.requestmanager.RequestManager;
import com.phicomm.account.service.RequestService.Operation;
import com.phicomm.account.util.Contact;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.ContactsContract;
import android.util.Log;

public class PoCInitUploadRequestService extends BaseThreadService {
    private boolean mMapIsNull;
    private Runnable mWorkUpdateDeleteDoneRun = new Runnable(){
        @Override
        public void run() {
            if(Looper.getMainLooper().getThread() != Thread.currentThread()){
            }
            synchronized (mFutureList) {
                ArrayList<Future<?>> futureList = mFutureList;
                for(int i=0 ;i<futureList.size(); i++){
                    if(futureList.get(i).isDone()){
                        futureList.remove(i);
                        i--;
                        if(mIndex < mSumList.size()){
                            mFutureList.add(mThreadPool.submit(new IntentUpdataDeleteRun(mSumList.get(mIndex))));
                            mIndex ++ ;
                        }
                    }
                }
                if(futureList.isEmpty()){
                    stopSelf();
                }else if(mSumList.size() == mIndex && futureList.size() == 1 && !futureList.get(0).isDone()){
                    mHandler.post(mWorkUpdateDeleteDoneRun);
                }
            }
        }
    };

    private class IntentUpdataDeleteRun implements Runnable{

        String mContactId ;
        public IntentUpdataDeleteRun(String contactId) {
            super();
            this.mContactId = contactId;
        }
        @Override
        public void run() {
            Log.i("ss","__________________________________mContactId:"+mContactId);
            Contact contact = mContact.getContact(mContactId);
            if(mMapIsNull){
                contact.setOperateId(Contact.OPERATOR_ID_INSERT);
                contact.setGlobalId("");
                mUpload.insertUploadData(contact);
            }else{
                Contact checkContact = mCheck.getCheckContact(mContactId);
                Log.i("ss", "__________________________________mContactId:"+ mContactId);
                Log.i("ss", "__________________________________checkContact :"+ checkContact + ":" + contact);
                String flag = mSync.querySyncDataExist(contact);
                Log.i("ss", "__________________________________flag :" + flag);
                if(contact == null && checkContact != null){
                    // delete
                    Log.i("ss", "__________________________________delete");
                    if (!flag.equals(Contact.OPERATOR_ID_DELETE)) {
                        checkContact.setOperateId(Contact.OPERATOR_ID_DELETE);
                        checkContact.setGlobalId(mMap.getMapGlobalId(mContactId));
                        mUpload.insertUploadData(checkContact);
                    }
                }else if(checkContact == null && contact != null){
                    //add
                    Log.i("ss", "__________________________________add");
                    if (!flag.equals(Contact.OPERATOR_ID_INSERT)) {
                        contact.setOperateId(Contact.OPERATOR_ID_INSERT);
                        contact.setGlobalId("");
                        mUpload.insertUploadData(contact);
                    }
                }else  if(!contact.getVersion().equals(checkContact.getVersion())){
                    //update
                    //new and old
                    Log.i("ss", "__________________________________update");
                    if (!flag.equals(Contact.OPERATOR_ID_UPDATE)) {
                        // map
                        contact.setGlobalId(mMap.getMapGlobalId(mContactId));
                        saveOldData(contact, checkContact);
                        mUpload.insertUploadData(contact);
                    }
                }
            }
            mHandler.post(mWorkUpdateDeleteDoneRun);
        }
    }

    private void saveOldData(Contact contact, Contact checkContact) {
        contact.setOperateId(Contact.OPERATOR_ID_UPDATE);
        contact.setOldName(checkContact.getDisplayName());
        contact.setOldEmail(checkContact.getEmail());
        contact.setOldHomeAddress(checkContact.getHomeAddress());
        contact.setOldIm(checkContact.getIm());
        contact.setOldPhone(checkContact.getMobilePhone());
        contact.setOldNickName(checkContact.getNickName());
        contact.setOldWorkAddress(checkContact.getWorkAddress());
        contact.setOldOrganization(checkContact.getOrganization());
        contact.setOldTelephone(checkContact.getTelephone());
        contact.setOldWorkphone(checkContact.getWorkphone());
        contact.setOldWebsite(checkContact.getWebsite());
        contact.setOldPhotoImg(checkContact.getPhotoImg());
        contact.setOldNote(checkContact.getNote());
        contact.setOldGroupMember(checkContact.getGroupMember());
    }

    @Override
    public void initDataList() {
        getContentResolver().delete(Provider.UploadDataColumns.CONTENT_URI, null, null);//must be
        //map is null get all
        Cursor cursorMapData = getContentResolver().query(
                Provider.MapColumns.CONTENT_URI, null, null, null, null);
        mMapIsNull = false;
        if (cursorMapData != null) {
            if(cursorMapData.getCount() == 0){
                mMapIsNull = true;
            }
            cursorMapData.close();
        }
        Log.i("ss","______________________________________mMapIsNull:"+mMapIsNull);
        if(mMapIsNull){
            //get contact DB, need all contacts to service 
            Cursor cursorContactData = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts._ID + " asc");
            String contactId = "-1";
            if (cursorContactData != null) {
                while (cursorContactData.moveToNext()) {
                    contactId = cursorContactData.getString(cursorContactData.getColumnIndex(ContactsContract.Contacts._ID));
                    mSumList.add(contactId);
                }
                cursorContactData.close();
            }
        }else{
            // get check table
            Cursor cursorCheckData = getContentResolver().query(
                    Provider.CheckDataColumns.CONTENT_URI, null, null, null, Provider.CheckDataColumns.CONTACT_ID + " asc");
            String contactId = "-1";
            if (cursorCheckData != null) {
                while (cursorCheckData.moveToNext()) {
                    contactId = cursorCheckData.getString(cursorCheckData.getColumnIndex(Provider.CheckDataColumns.CONTACT_ID));
                    mSumList.add(contactId);
                }
                cursorCheckData.close();
            }

            //get contact DB , new contact
            Cursor cursorContactData = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + " > ?", new String[]{contactId}, ContactsContract.Contacts._ID + " asc");

            if (cursorContactData != null) {
                while (cursorContactData.moveToNext()) {
                    contactId = cursorContactData.getString(cursorContactData.getColumnIndex(ContactsContract.Contacts._ID));
                    mSumList.add(contactId);
                }
                cursorContactData.close();
            }
        }
        if(mSumList.size() < mPoolSize){
            mPoolSize = mSumList.size();
        }
        for(int i = 0;i<mPoolSize;i++){
            mFutureList.add(mThreadPool.submit(new IntentUpdataDeleteRun(mSumList.get(i))));
        }
        mIndex = mPoolSize;

    }
}
