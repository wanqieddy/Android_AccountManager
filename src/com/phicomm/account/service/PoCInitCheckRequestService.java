package com.phicomm.account.service;

import java.util.ArrayList;
import java.util.concurrent.Future;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;
import android.database.Cursor;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;

public class PoCInitCheckRequestService extends BaseThreadService {

    private Runnable mWorkDoneRun = new Runnable(){
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
                    Log.i("ss","_________________________________PoCInitCheckRequestService_stopSelf");
                    stopSelf();
                }else if(mSumList.size() == mIndex && futureList.size() == 1 && !futureList.get(0).isDone()){
                    mHandler.post(mWorkDoneRun);
                }
            }
        }
    };

    private class IntentRun implements Runnable{

        String mContactId ;
        public IntentRun(String contactId) {
            super();
            this.mContactId = contactId;
        }
        @Override
        public void run() {
            Contact contact = mContact.getContact(mContactId);
            mCheck.insertCheckData(contact);
            mHandler.post(mWorkDoneRun);
        }
    }

    @Override
    public void initDataList() {
        //getContentResolver().delete(Provider.CheckDataColumns.CONTENT_URI, null, null);
        boolean initCheckFlag = true;
        Cursor cursorCheck = getContentResolver().query(
                Provider.CheckDataColumns.CONTENT_URI, null, null, null, null);
        if(cursorCheck != null){
            if(cursorCheck.getCount() > 0){
                initCheckFlag = false;
            }
            cursorCheck.close();
        }
        if(initCheckFlag){
            Cursor cursorData = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts._ID + " asc");
            String contactId;
            if (cursorData != null) {
                while (cursorData.moveToNext()) {
                    contactId = cursorData
                            .getString(cursorData
                                    .getColumnIndex(ContactsContract.Contacts._ID));
                    mSumList.add(contactId);
                }
                cursorData.close();
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
}
