package com.phicomm.account.util;

import java.util.ArrayList;
import java.util.List;

public class Contact {

    public static final String OPERATOR_ID_INSERT = "A";
    public static final String OPERATOR_ID_DELETE = "D";
    public static final String OPERATOR_ID_UPDATE = "U";

    private String displayName;
    private String email;
    private String contactId;
    private String changeTime;
    private String operateId;
    private String nickName;
    private String workAddress;
    private String homeAddress;
    private String organization;
    private String mobilePhone;
    private String telephone;
    private String workphone;
    private String website;
    private String photoImg;
    private String note;
    private String im;
    private String groupMember;
    private String oldName;
    private String oldPhone;
    private String oldEmail;
    private String oldNickName;
    private String oldWorkAddress;
    private String oldHomeAddress;
    private String oldOrganization;
    private String oldTelephone;
    private String oldWorkphone;
    private String oldWebsite;
    private String oldPhotoImg;
    private String oldNote;
    private String oldIm;
    private String oldGroupMember;
    private String resultCode;
    private String version;
    private String globalId;
    private String syncTime;

    public String getDisplayName() {
        if(displayName == null){
            return "";
        }
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getEmail() {
        if(email == null){
            return "";
        }
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContactId() {
        if(contactId == null){
            return "";
        }
        return contactId;
    }
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
    public String getChangeTime() {
        if(changeTime == null){
            return "";
        }
        return changeTime;
    }
    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
    public String getOperateId() {
        if(operateId == null){
            return "";
        }
        return operateId;
    }
    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }
    public String getNickName() {
        if(nickName == null){
            return "";
        }
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getWorkAddress() {
        if(workAddress == null){
            return "";
        }
        return workAddress;
    }
    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }
    public String getHomeAddress() {
        if(homeAddress == null){
            return "";
        }
        return homeAddress;
    }
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
    public String getOrganization() {
        if(organization == null){
            return "";
        }
        return organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getMobilePhone() {
        if(mobilePhone == null){
            return "";
        }
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getTelephone() {
        if(telephone == null){
            return "";
        }
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getWorkphone() {
        if(workphone == null){
            return "";
        }
        return workphone;
    }
    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }
    public String getWebsite() {
        if(website == null){
            return "";
        }
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getPhotoImg() {
        if(photoImg == null){
            return "";
        }
        return photoImg;
    }
    public void setPhotoImg(String photoImg) {
        this.photoImg = photoImg;
    }
    public String getNote() {
        if(note == null){
            return "";
        }
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getIm() {
        if(im == null){
            return "";
        }
        return im;
    }
    public void setIm(String im) {
        this.im = im;
    }
    public String getGroupMember() {
        if(groupMember == null){
            return "";
        }
        return groupMember;
    }
    public void setGroupMember(String groupMember) {
        this.groupMember = groupMember;
    }
    public String getOldName() {
        if(oldName == null){
            return "";
        }
        return oldName;
    }
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
    public String getOldPhone() {
        if(oldPhone == null){
            return "";
        }
        return oldPhone;
    }
    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }
    public String getOldEmail() {
        if(oldEmail == null){
            return "";
        }
        return oldEmail;
    }
    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }
    public String getOldNickName() {
        if(oldNickName == null){
            return "";
        }
        return oldNickName;
    }
    public void setOldNickName(String oldNickName) {
        this.oldNickName = oldNickName;
    }
    public String getOldWorkAddress() {
        if(oldWorkAddress == null){
            return "";
        }
        return oldWorkAddress;
    }
    public void setOldWorkAddress(String oldWorkAddress) {
        this.oldWorkAddress = oldWorkAddress;
    }
    public String getOldHomeAddress() {
        if(oldHomeAddress == null){
            return "";
        }
        return oldHomeAddress;
    }
    public void setOldHomeAddress(String oldHomeAddress) {
        this.oldHomeAddress = oldHomeAddress;
    }
    public String getOldOrganization() {
        if(oldOrganization == null){
            return "";
        }
        return oldOrganization;
    }
    public void setOldOrganization(String oldOrganization) {
        this.oldOrganization = oldOrganization;
    }
    public String getOldTelephone() {
        if(oldTelephone == null){
            return "";
        }
        return oldTelephone;
    }
    public void setOldTelephone(String oldTelephone) {
        this.oldTelephone = oldTelephone;
    }
    public String getOldWorkphone() {
        if(oldWorkphone == null){
            return "";
        }
        return oldWorkphone;
    }
    public void setOldWorkphone(String oldWorkphone) {
        this.oldWorkphone = oldWorkphone;
    }
    public String getOldWebsite() {
        if(oldWebsite == null){
            return "";
        }
        return oldWebsite;
    }
    public void setOldWebsite(String oldWebsite) {
        this.oldWebsite = oldWebsite;
    }
    public String getOldPhotoImg() {
        if(oldPhotoImg == null){
            return "";
        }
        return oldPhotoImg;
    }
    public void setOldPhotoImg(String oldPhotoImg) {
        this.oldPhotoImg = oldPhotoImg;
    }
    public String getOldNote() {
        if(oldNote == null){
            return "";
        }
        return oldNote;
    }
    public void setOldNote(String oldNote) {
        this.oldNote = oldNote;
    }
    public String getOldIm() {
        if(oldIm == null){
            return "";
        }
        return oldIm;
    }
    public void setOldIm(String oldIm) {
        this.oldIm = oldIm;
    }
    public String getOldGroupMember() {
        if(oldGroupMember == null){
            return "";
        }
        return oldGroupMember;
    }
    public void setOldGroupMember(String oldGroupMember) {
        this.oldGroupMember = oldGroupMember;
    }
    public String getResultCode() {
        if(resultCode == null){
            return "";
        }
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getVersion() {
        if(version == null){
            return "";
        }
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalid) {
        globalId = globalid;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }
    public ArrayList<ContactPhone> getPhoneList() {
        return phoneList;
    }
    public void setPhoneList(ArrayList<ContactPhone> phoneList) {
        this.phoneList = phoneList;
    }
    private ArrayList<ContactPhone> phoneList;


}
