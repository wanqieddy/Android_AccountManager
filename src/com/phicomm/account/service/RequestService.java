package com.phicomm.account.service;

import com.phicomm.account.requestmanager.Request;
import com.phicomm.account.requestmanager.RequestManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public abstract class RequestService extends MultiThreadIntentService {


    private static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = -1;
    public interface Operation{
        public Bundle execute(Context context, Request request);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        Bundle bundle = intent.getExtras();

        ResultReceiver receiver = intent.getParcelableExtra(RequestManager.INTENT_EXTRA_RECEIVER);
        Request request = intent.getParcelableExtra(RequestManager.INTENT_EXTRA_REQUEST);
        Operation operation = getOperationType(request.getRequestType());
        sendResult(receiver, operation.execute(this, request), SUCCESS_CODE);
    }

    private void sendResult(ResultReceiver receiver, Bundle data, int code){
        if(receiver != null){
            if(data == null){
                data = new Bundle();
            }
            receiver.send(code, data);
        }
    }

    public abstract Operation getOperationType(int requestType);

}
