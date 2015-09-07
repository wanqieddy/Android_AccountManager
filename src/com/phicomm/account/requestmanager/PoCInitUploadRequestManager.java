package com.phicomm.account.requestmanager;

import com.phicomm.account.service.PoCInitUploadRequestService;
import android.content.Context;

public class PoCInitUploadRequestManager extends RequestManager {

    private static PoCInitUploadRequestManager sInstance;
    public synchronized static PoCInitUploadRequestManager from(Context context){
        if(sInstance == null){
            sInstance = new PoCInitUploadRequestManager(context);
        }
        return sInstance;
    }
    private PoCInitUploadRequestManager(Context context) {
        super(context, PoCInitUploadRequestService.class);
    }

}
