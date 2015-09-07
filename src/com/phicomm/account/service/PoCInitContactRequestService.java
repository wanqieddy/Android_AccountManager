package com.phicomm.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.phicomm.account.data.CheckDataOperate;
import com.phicomm.account.data.ContactDataOperate;
import com.phicomm.account.data.ContactsOperate;
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

public class PoCInitContactRequestService extends BaseThreadService {

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
                            mFutureList.add(mThreadPool.submit(new IntentRun(mSumList.get(mIndex))));
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

    private class IntentRun implements Runnable{

        String mGlobalId  ;
        ContactsOperate  mContactsOperate;
        public IntentRun(String globalId) {
            super();
            this.mGlobalId  = globalId;
            mContactsOperate=new ContactsOperate(getApplicationContext());
        }
        @Override
        public void run() {
            Log.i("ss","__________________________________mGlobalId:"+mGlobalId);
            Contact contact = mSync.getSyncContact(mGlobalId);
            Log.i("ss","_____________________________contact:"+contact.getOperateId());
            if(contact.getOperateId().equals(Contact.OPERATOR_ID_INSERT)){
                mContactsOperate.addContactIdInMapAndContacts(
                        contact.getGlobalId(), contact);
            }else{
                //query map
                boolean globalIdExist = mMap.getMapGlobalIdExist(mGlobalId);
                if(globalIdExist){
                    //
                    if(contact.getOperateId().equals(Contact.OPERATOR_ID_DELETE)){
                        //rm contact and map
                        mContactsOperate
                                .deleteContactIdInMapAndContacts(contact
                                        .getGlobalId());
                    }else if(contact.getOperateId().equals(Contact.OPERATOR_ID_UPDATE)){
                        //rm contact than add contact
                        mContactsOperate.updateContact(
                                contact.getGlobalId(), contact);
                    }
                }else{
                    if(contact.getOperateId().equals(Contact.OPERATOR_ID_DELETE)){
                        //nothing to do
                    }else if(contact.getOperateId().equals(Contact.OPERATOR_ID_UPDATE)){
                        //add contact than add map
                        Log.i("ss","_____________________________OPERATOR_ID_UPDATE");
                        Cursor syncCursor = getContentResolver().query(
                                Provider.SyncColumns.CONTENT_URI, null,
                                Provider.SyncColumns.GLOBAL_ID + "=?",
                                new String[] { mGlobalId }, null);
                        Contact syncContact = null;
                        if (syncCursor.moveToFirst()) {
                            syncContact = mContactsOperate
                                    .getInfoFromSymbolCursor(syncCursor);
                        }
                        Log.i("ss","_____________________________syncContact:"+syncContact.getDisplayName());
                        if (syncContact != null) {
                            mContactsOperate.insertContact(syncContact);
                            // get contactId
                            Cursor contactCursor = getContentResolver()
                                    .query(ContactsContract.Contacts.CONTENT_URI,
                                            new String[] { ContactsContract.Contacts._ID },
                                            null, null, "_id desc");
                            Contact dataContact = null;
                            if (contactCursor != null
                                    && contactCursor.moveToFirst()) {
                                dataContact = mContact.getContact(contactCursor
                                        .getString(0));
                                Log.i("ss",
                                        "_____________________________contactId:"
                                                + dataContact.getContactId());
                                Log.i("ss",
                                        "_____________________________globalId:"
                                                + mGlobalId);
                                // add map
                                mContactsOperate.createMapping(
                                        dataContact.getContactId(), mGlobalId);
                            }
                            contactCursor.close();
                        }
                        syncCursor.close();
                    }
                }
            }
            mHandler.post(mWorkUpdateDeleteDoneRun);
        }
    }


    @Override
    public void initDataList() {
        // get check table
        Cursor cursorCheckData = getContentResolver().query(
                Provider.SyncColumns.CONTENT_URI, null, null, null, Provider.SyncColumns.GLOBAL_ID + " asc");
        String global_Id = "-1";
        if (cursorCheckData != null) {
            while (cursorCheckData.moveToNext()) {
                global_Id = cursorCheckData.getString(cursorCheckData.getColumnIndex(Provider.SyncColumns.GLOBAL_ID));
                mSumList.add(global_Id);
            }
            cursorCheckData.close();
        }
        if(mSumList.size() < mPoolSize){
            mPoolSize = mSumList.size();
        }
        for(int i = 0;i<mPoolSize;i++){
            mFutureList.add(mThreadPool.submit(new IntentRun(mSumList.get(i))));
        }
        mIndex = mPoolSize;

    }
}
