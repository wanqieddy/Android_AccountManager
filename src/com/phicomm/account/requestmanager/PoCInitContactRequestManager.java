package com.phicomm.account.requestmanager;

import com.phicomm.account.service.PoCInitContactRequestService;
import android.content.Context;

public class PoCInitContactRequestManager extends RequestManager {

    private static PoCInitContactRequestManager sInstance;
    public synchronized static PoCInitContactRequestManager from(Context context){
        if(sInstance == null){
            sInstance = new PoCInitContactRequestManager(context);
        }
        return sInstance;
    }
    private PoCInitContactRequestManager(Context context) {
        super(context, PoCInitContactRequestService.class);
    }

}
