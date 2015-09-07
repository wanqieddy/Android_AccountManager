package com.phicomm.account.util;

public class MapId {
    /*<node>
    <mobile_contact_id></mobile_contact_id>
    <service_contact_id></service_contact_id>
    <node>*/

    private String mobileContactId;
    private String serviceContactId;
    public String getMobileContactId() {
        return mobileContactId;
    }
    public void setMobileContactId(String mobileContactId) {
        this.mobileContactId = mobileContactId;
    }
    public String getServiceContactId() {
        return serviceContactId;
    }
    public void setServiceContactId(String serviceContactId) {
        this.serviceContactId = serviceContactId;
    }
}
