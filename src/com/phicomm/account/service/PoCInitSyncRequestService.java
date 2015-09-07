package com.phicomm.account.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.phicomm.account.provider.Provider;
import com.phicomm.account.requestmanager.Request;
import com.phicomm.account.requestmanager.RequestManager;
import com.phicomm.account.util.Contact;
import com.phicomm.account.util.ReadDoc;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.ContactsContract;
import android.util.Log;

public class PoCInitSyncRequestService extends BaseThreadService {

    ArrayList<Contact> mContactList  = new ArrayList<Contact>();

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
                            mFutureList.add(mThreadPool.submit(new IntentRun(mIndex)));
                            mIndex ++ ;
                        }
                    }
                }
                if(futureList.isEmpty()){
                    Log.i("ss","_________________________________PoCInitCheckRequestService_stopSelf");
                    stopSelf();
                }else if(mContactList.size() == mIndex && futureList.size() == 1 && !futureList.get(0).isDone()){
                    mHandler.post(mWorkDoneRun);
                }
            }
        }
    };

    private class IntentRun implements Runnable{

        int mIndex;
        public IntentRun(int index) {
            super();
            this.mIndex = index;
        }
        @Override
        public void run() {
            Contact contact = mContactList.get(mIndex);
            mSync.insertSyncData(contact);
            mHandler.post(mWorkDoneRun);
        }
    }

    @Override
    public void initDataList() {
        Request request = mIntent.getParcelableExtra(RequestManager.INTENT_EXTRA_REQUEST);
        String xmlBody = request.getString(RequestManager.INTENT_EXTRA_RETURN_BODY);
        Log.i("ss", "________________________________xmlBody :" + xmlBody);
        if (xmlBody == null) {
            return;
        }
        StringReader sr = new StringReader(xmlBody);

        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(is);
            mContactList = ReadDoc.readXMLToContact(doc);
            Contact contact=mContactList.get(0);
            Log.i("ss","---------syncTime"+contact.getSyncTime());
            mSync.insertSyncTimeToDataBase(contact.getSyncTime());
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(mContactList.size() < mPoolSize){
            mPoolSize = mContactList.size();
        }
        for(int i = 0;i<mPoolSize;i++){
            mFutureList.add(mThreadPool.submit(new IntentRun(i)));
        }
        mIndex = mPoolSize;
    }
}
