package com.phicomm.account.requestmanager;

import com.phicomm.account.service.PoCInitMapRequestService;
import android.content.Context;

public class PoCInitMapRequestManager extends RequestManager {

    private static PoCInitMapRequestManager sInstance;
    public synchronized static PoCInitMapRequestManager from(Context context){
        if(sInstance == null){
            sInstance = new PoCInitMapRequestManager(context);
        }
        return sInstance;
    }
    private PoCInitMapRequestManager(Context context) {
        super(context, PoCInitMapRequestService.class);
    }

}
