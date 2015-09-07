package com.phicomm.account.requestmanager;

import android.content.Context;

import com.phicomm.account.service.PoCRequestService;

public final class PoCRequestManager extends RequestManager {

    private static PoCRequestManager sInstance;
    public synchronized static PoCRequestManager from(Context context){
        if(sInstance == null){
            sInstance = new PoCRequestManager(context);
        }
        return sInstance;
    }
    private PoCRequestManager(Context context) {

        super(context, PoCRequestService.class);
    }

}
