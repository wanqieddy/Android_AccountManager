package com.phicomm.account.service;

import com.phicomm.account.operator.GetMapOperation;
import com.phicomm.account.operator.UploadContactOperation;
import com.phicomm.account.requestmanager.PoCRequestFactory;

public class PoCRequestService extends RequestService {

    @Override
    protected int getMaxNumberOfThreads() {
        return 5;
    }

    @Override
    public Operation getOperationType(int requestType) {
        switch(requestType){
        case PoCRequestFactory.REQUEST_TYPE_INIT_CHECK:
            //return new InitCheckOperation2();
        case PoCRequestFactory.REQUEST_TYPE_GET_MAP:
            return new GetMapOperation();
        case PoCRequestFactory.REQUEST_TYPE_CONTACT_UPLOAD:
            return new UploadContactOperation();
        }
        return null;
    }

}
