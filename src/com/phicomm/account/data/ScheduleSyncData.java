package com.phicomm.account.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


public class ScheduleSyncData {

    private Timer timer;

    private Context mContext;

    public ScheduleSyncData(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    private void startSyncTableStauts(TimerTask timerTask, int hour,
            int minute, int second, long delay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date time = calendar.getTime();
        Log.i("ss", "___________time :" + time);
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, time, delay * 1000);
    }

    public void startUploadTableStatus(int hour, int minute, int second,
            long delay) {
        Log.i("ss", "___________upload data");
        //TimerTask uploadTask = new TableStatusUploadTimerTask();
        //startSyncTableStauts(uploadTask, hour, minute, second, delay);
    }

    public void startDownTableStatus(int hour, int minute, int second,
            long delay) {
        Log.i("ss", "___________down data");
        TimerTask downTask = new TableStatusDownTimerTask();
        startSyncTableStauts(downTask, hour, minute, second, delay);
    }

    public void stopSyncTableStatus() {
        timer.cancel();
    }

    private class TableStatusDownTimerTask extends TimerTask {
        @Override
        public void run() {
            ContactDownload down = new ContactDownload();
            //ArrayList<Contact> downData = down.downloadContactListFromService("");
        }
    }

    private class TableStatusUploadTimerTask extends TimerTask {
        @Override
        public void run() {
            String jssessionid = null;
            Cursor cursor = mContext.getContentResolver().query(Provider.PersonColumns.CONTENT_URI, null, null, null, null);
            if(cursor != null && cursor.moveToFirst()){
                jssessionid = cursor.getString(cursor.getColumnIndex(Provider.PersonColumns.JSSESSIONID));
                cursor.close();
            }
            ContactUpload upload = new ContactUpload();
            UploadDataOperate uploadOperate = new UploadDataOperate(mContext);
            Log.i("ss", "_____upload :" + uploadOperate.judgeUploadDataExist());
            if (uploadOperate.judgeUploadDataExist()) {
                 int i = upload.uploadContactListToService(mContext);
            }
        }
    }

}