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

import com.phicomm.account.requestmanager.Request;
import com.phicomm.account.requestmanager.RequestManager;
import com.phicomm.account.util.Contact;
import com.phicomm.account.util.MapId;
import com.phicomm.account.util.ReadDoc;


import android.os.Looper;
import android.util.Log;

public class PoCInitMapRequestService extends BaseThreadService {

    ArrayList<MapId> mMapList  = new ArrayList<MapId>();

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
                    Log.i("ss","_________________________________PoCInitMapRequestService_stopSelf");
                    stopSelf();
                }else if(mMapList.size() == mIndex && futureList.size() == 1 && !futureList.get(0).isDone()){
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
            MapId map = mMapList.get(mIndex);
            mMap.insertMap(map);
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
            mMapList = ReadDoc.readXMLToMapList(doc);
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
        if(mMapList.size() < mPoolSize){
            mPoolSize = mMapList.size();
        }
        for(int i = 0;i<mPoolSize;i++){
            mFutureList.add(mThreadPool.submit(new IntentRun(i)));
        }
        mIndex = mPoolSize;

    }
}
