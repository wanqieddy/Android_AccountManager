package com.phicomm.account.requestmanager;

import com.phicomm.account.service.PoCInitCheckRequestService;

import android.content.Context;

public class PoCInitCheckRequestManager extends RequestManager {

    private static PoCInitCheckRequestManager sInstance;
    public synchronized static PoCInitCheckRequestManager from(Context context){
        if(sInstance == null){
            sInstance = new PoCInitCheckRequestManager(context);
        }
        return sInstance;
    }
    private PoCInitCheckRequestManager(Context context) {
        super(context, PoCInitCheckRequestService.class);
    }

}
