package com.phicomm.account.service;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.phicomm.account.data.CheckDataOperate;
import com.phicomm.account.data.ContactDataOperate;
import com.phicomm.account.data.MapData;
import com.phicomm.account.data.SyncDataOperate;
import com.phicomm.account.data.UploadDataOperate;
import com.phicomm.account.requestmanager.RequestManager;
import com.phicomm.account.service.RequestService.Operation;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

public abstract class BaseThreadService extends Service {
    public Handler mHandler;
    public ArrayList<Future<?>> mFutureList;
    public ArrayList<String> mSumList;
    public ExecutorService mThreadPool;
    public int mIndex = 0;
    public int mPoolSize = 10;
    public CheckDataOperate mCheck ;
    public ContactDataOperate mContact;
    public UploadDataOperate mUpload;
    public SyncDataOperate mSync;
    public Intent mIntent;
    public MapData mMap;

    @Override
    public void onCreate() {
        super.onCreate();
        mFutureList = new ArrayList<Future<?>>();
        mHandler = new Handler();
        mThreadPool = Executors.newFixedThreadPool(mPoolSize);
        mSumList = new ArrayList<String>();
        mCheck = new CheckDataOperate(this);
        mContact = new ContactDataOperate(this);
        mUpload = new UploadDataOperate(this);
        mSync = new SyncDataOperate(this);
        mMap = new MapData(this);
    }
    @Override
    public void onStart(Intent intent, int startId) {
        mIntent = intent;
        initDataList();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mThreadPool.shutdown();

        ResultReceiver receiver = mIntent.getParcelableExtra(RequestManager.INTENT_EXTRA_RECEIVER);
        Bundle resultData = new Bundle();
        receiver.send(RequestManager.SUCCESS_CODE, resultData);
        Log.i("ss","________________________onDestroy");
    }

    public abstract void initDataList();

}
