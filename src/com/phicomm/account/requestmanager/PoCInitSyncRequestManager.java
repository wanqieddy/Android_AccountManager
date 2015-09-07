package com.phicomm.account.requestmanager;

import com.phicomm.account.service.PoCInitSyncRequestService;
import android.content.Context;

public class PoCInitSyncRequestManager extends RequestManager {

    private static PoCInitSyncRequestManager sInstance;
    public synchronized static PoCInitSyncRequestManager from(Context context){
        if(sInstance == null){
            sInstance = new PoCInitSyncRequestManager(context);
        }
        return sInstance;
    }
    private PoCInitSyncRequestManager(Context context) {
        super(context, PoCInitSyncRequestService.class);
    }

}
