package com.phicomm.account.requestmanager;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import com.phicomm.account.service.RequestService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;

public abstract class RequestManager {
    public static final String INTENT_EXTRA_RECEIVER = "com.phicomm.account.receiver";
    public static final String INTENT_EXTRA_REQUEST = "com.phicomm.account.request";
    public static final String INTENT_EXTRA_XML_BODY = "com.phicomm.account.xml.body";
    public static final String INTENT_EXTRA_RETURN_BODY = "com.phicomm.account.return.body";
    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = -1;
    public static interface RequestListener extends EventListener{
        public void onRequestFinished(Request request, Bundle bundle);
    }

    private final Context mContext;
    private final Class<? extends Service> mRequestService;
    private final HashMap<Request, RequestReceiver> mRequestReceiverMap;

    public RequestManager(Context context, Class<? extends Service> requestService) {
        this.mContext = context;
        this.mRequestService = requestService;
        mRequestReceiverMap = new HashMap<Request, RequestReceiver>();
    }

    public final void addRequestListener(RequestListener listener, Request request){
        if(listener == null){
            return;
        }
        RequestReceiver requestReceiver = mRequestReceiverMap.get(request);
        Log.i("ss","addRequestListener______________________requestReceiver:"+requestReceiver);
        if(requestReceiver == null){
            return;
        }
        requestReceiver.addListenerHolder(new ListenerHolder(listener));
    }

    public final void removeRequestListener(RequestListener listener, Request request){
        if(listener == null){
            return ;
        }
        ListenerHolder holder = new ListenerHolder(listener);
        Log.i("ss","removeRequestListener________________________request:"+request);
        if(request != null){
            RequestReceiver requestReceiver = mRequestReceiverMap.get(request);
            if(requestReceiver != null){
                requestReceiver.removeListenerHolder(holder);
            }
        } else {
            for(RequestReceiver requestReceiver : mRequestReceiverMap.values()){
                requestReceiver.removeListenerHolder(holder);
            }
        }
    }

    public final void execute(Request request, RequestListener listener){

        if(mRequestReceiverMap.containsKey(request)){
            addRequestListener(listener, request);
           // return;
        }
        RequestReceiver requestReceiver = new RequestReceiver(request);
        mRequestReceiverMap.put(request, requestReceiver);
        addRequestListener(listener, request);

        Intent intent = new Intent(mContext, mRequestService);
        intent.putExtra(INTENT_EXTRA_RECEIVER, requestReceiver);
        intent.putExtra(INTENT_EXTRA_REQUEST, request);
        mContext.startService(intent);
    }

    private final class RequestReceiver extends ResultReceiver{
        private final Request mRequest;
        private final Set<ListenerHolder> mListenerHolderSet;

        public RequestReceiver(Request request) {
            super(new Handler(Looper.getMainLooper()));
            mRequest = request;
            mListenerHolderSet = Collections.synchronizedSet(new HashSet<ListenerHolder>());
        }
        void addListenerHolder(ListenerHolder listenerHolder){
            mListenerHolderSet.add(listenerHolder);
        }
        void removeListenerHolder(ListenerHolder listenerHolder){
            mListenerHolderSet.remove(listenerHolder);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mRequestReceiverMap.remove(mRequest);
            synchronized(mListenerHolderSet){
                for(ListenerHolder listenerHolder : mListenerHolderSet){
                    listenerHolder.onRequestFinished(mRequest, resultCode, resultData);
                }
            }
        }
    }

    private final class ListenerHolder{
        private final WeakReference<RequestListener> mListenerRef;
        private final int mHashCode;
        ListenerHolder(RequestListener listener){
            mListenerRef = new WeakReference<RequestListener>(listener);
            mHashCode = 31 + listener.hashCode();
        }
        void onRequestFinished(Request request, int resultCode, Bundle resultData){
            mRequestReceiverMap.remove(request);
            RequestListener listener = mListenerRef.get();
            if(listener != null){
                listener.onRequestFinished(request, resultData);
            }
        }
    }
}
