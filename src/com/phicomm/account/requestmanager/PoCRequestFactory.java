package com.phicomm.account.requestmanager;

public class PoCRequestFactory {

    public static final int REQUEST_TYPE_INIT_CHECK = 0;
    public static final int REQUEST_TYPE_CONTACT_UPLOAD = 1;
    public static final int REQUEST_TYPE_INIT_UPLOAD = 2;
    public static final int REQUEST_TYPE_INIT_SYNC = 3;
    public static final int REQUEST_TYPE_INIT_CONTACT = 4;

    public static final int REQUEST_TYPE_INIT_MAP = 10;
    public static final int REQUEST_TYPE_GET_MAP = 11;

    public static Request getInitCheckRequest(){
        Request request = new Request(REQUEST_TYPE_INIT_CHECK);
        return request;
    }
    public static Request getContactUploadRequest(){
        Request request = new Request(REQUEST_TYPE_CONTACT_UPLOAD);
        return request;
    }
    public static Request getInitUploadRequest(){
        Request request = new Request(REQUEST_TYPE_INIT_UPLOAD);
        return request;
    }
    public static Request getInitSyncRequest(){
        Request request = new Request(REQUEST_TYPE_INIT_SYNC);
        return request;
    }
    public static Request getInitMapRequest(){
        Request request = new Request(REQUEST_TYPE_INIT_MAP);
        return request;
    }
    public static Request getGetMapRequest(){
        Request request = new Request(REQUEST_TYPE_GET_MAP);
        return request;
    }
    public static Request getInitContactRequest(){
        Request request = new Request(REQUEST_TYPE_INIT_CONTACT);
        return request;
    }
}
