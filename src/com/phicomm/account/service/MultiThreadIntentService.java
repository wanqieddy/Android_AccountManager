package com.phicomm.account.service;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.phicomm.account.requestmanager.Request;
import com.phicomm.account.requestmanager.RequestManager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;

public abstract class MultiThreadIntentService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    private static final long STOP_SELF_DELAY = TimeUnit.SECONDS.toMillis(30L);
    private ExecutorService mThreadPool;
    private ArrayList<Future<?>> mFutureList;
    private Handler mHandler;

    private final Runnable mStopServiceSelfRun = new Runnable(){

        @Override
        public void run() {
            // stop current service
            Log.i("ss","__________________________________run stopSelf");
            stopSelf();
        }
    };

    private final Runnable mWorkDoneRun = new Runnable(){

        @Override
        public void run() {
            if(Looper.getMainLooper().getThread() != Thread.currentThread()){
                Log.i("ss","_____________________error");
            }
            final ArrayList<Future<?>> futureList = mFutureList;
            for(int i = 0; i < futureList.size(); i++){
                if(futureList.get(i).isDone()){
                    futureList.remove(i);
                    i--;
                }
            }
            if(futureList.isEmpty()){
                mHandler.postDelayed(mStopServiceSelfRun, STOP_SELF_DELAY);
            }
        }
    };

    protected int getMaxNumberOfThreads(){
        return 1;
    }


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        int maxNumberOfThreads = getMaxNumberOfThreads();
        Log.i("ss","onCreate_____________________________maxNumberOfThreads:"+maxNumberOfThreads);
        mThreadPool = Executors.newFixedThreadPool(maxNumberOfThreads);
        mHandler = new Handler();
        mFutureList = new ArrayList<Future<?>>();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mThreadPool.shutdown();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        //super.onStart(intent, startId);
        mHandler.removeCallbacks(mStopServiceSelfRun);
        Log.i("ss","_________________________onStart()");
        ResultReceiver receiver = intent.getParcelableExtra(RequestManager.INTENT_EXTRA_RECEIVER);
        Log.i("ss","____________________________receiver:"+receiver);
        Request request = intent.getParcelableExtra(RequestManager.INTENT_EXTRA_REQUEST);
        Log.i("ss","______________________________request:"+request);
        mFutureList.add(mThreadPool.submit(new IntentRun(intent)));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        Log.i("ss","________________________________onStartCommand");
        return START_NOT_STICKY;
    }

    private class IntentRun implements Runnable{
        private final Intent mIntent;
        public IntentRun(Intent intent){
            this.mIntent = intent;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Log.i("ss","___________________________________intentRun");
            onHandleIntent(mIntent);
            mHandler.removeCallbacks(mWorkDoneRun);
            mHandler.post(mWorkDoneRun);
        }

    }

    abstract protected void onHandleIntent(Intent intent);

}
