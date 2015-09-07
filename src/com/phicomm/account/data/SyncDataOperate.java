package com.phicomm.account.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.phicomm.account.provider.Person;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.util.Contact;
import android.util.Log;
import com.phicomm.account.provider.DatabaseHelper;

public class SyncDataOperate {
    private ContentResolver mContentResolver;
    boolean isNameEquals;
    boolean isEmailEquals;
    boolean isGroupMemberEquals;
    boolean isWorkAddressEquals;
    boolean isImEquals;
    boolean isMobilePhoneEquals;
    boolean isWorkPhoneEquals;
    boolean isTelephoneEquals;
    boolean isNoteEquals;
    boolean isNickNameEquals;
    boolean isOrganizationEquals;
    boolean isPhotoImgEquals;
    boolean isWebsiteEquals;
    boolean isHomeAddressEquals;

    public SyncDataOperate(Context context) {
        // TODO Auto-generated constructor stub
        mContentResolver = context.getContentResolver();
    }

    public int insertSyncData(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(Provider.SyncColumns.CONTACT_ID, contact.getContactId());
        values.put(Provider.SyncColumns.CHANGE_TIME, contact.getChangeTime());
        values.put(Provider.SyncColumns.OPERATE_ID, contact.getOperateId());
        values.put(Provider.SyncColumns.DISPLAY_NAME, contact.getDisplayName());
        values.put(Provider.SyncColumns.EMAIL, contact.getEmail());
        values.put(Provider.SyncColumns.NICK_NAME, contact.getNickName());
        values.put(Provider.SyncColumns.NOTE, contact.getNote());
        values.put(Provider.SyncColumns.ORGANIZATION, contact.getOrganization());
        values.put(Provider.SyncColumns.HOME_ADDRESS, contact.getHomeAddress());
        values.put(Provider.SyncColumns.MOBILE_PHONE, contact.getMobilePhone());
        values.put(Provider.SyncColumns.IM, contact.getIm());
        values.put(Provider.SyncColumns.PHOTO_IMAGE, contact.getPhotoImg());
        values.put(Provider.SyncColumns.TELEPHONE, contact.getTelephone());
        values.put(Provider.SyncColumns.WEBSITE, contact.getWebsite());
        values.put(Provider.SyncColumns.GROUP_MEMBER, contact.getGroupMember());
        values.put(Provider.SyncColumns.WORKPHONE, contact.getWorkphone());
        values.put(Provider.SyncColumns.WORK_ADDRESS, contact.getWorkAddress());
        values.put(Provider.SyncColumns.GLOBAL_ID, contact.getGlobalId());
        Uri uri = mContentResolver.insert(Provider.SyncColumns.CONTENT_URI,
                values);
        String lastPath = uri.getLastPathSegment();
        return Integer.parseInt(lastPath);
    }

    public int deleteSyncData(String contactId) {

        return mContentResolver.delete(Provider.SyncColumns.CONTENT_URI,
                Provider.SyncColumns.CONTACT_ID + "=?",
                new String[] { contactId });
    }

    public boolean querySyncDataExist(String contact_id) {
        Cursor cursor = mContentResolver.query(
                Provider.SyncColumns.CONTENT_URI, null,
                Provider.SyncColumns.CONTACT_ID + "=?",
                new String[] { contact_id }, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            }
        }
        return false;
    }

    public Contact getSyncContact(String globalId) {
        Cursor cursorSync = mContentResolver.query(
                Provider.SyncColumns.CONTENT_URI, null,
                Provider.SyncColumns.GLOBAL_ID + "=?",
                new String[] { globalId }, null);
        Contact contact = new Contact();
        if (cursorSync != null) {
            if (cursorSync.moveToFirst()) {
                String contact_id = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.CONTACT_ID));
                String change_time = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.CHANGE_TIME));
                String display_name = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.DISPLAY_NAME));
                String email = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.EMAIL));
                String group_member = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.GROUP_MEMBER));
                String home_address = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.HOME_ADDRESS));
                String im = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.IM));
                String mobile_phone = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.MOBILE_PHONE));
                String nick_name = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.NICK_NAME));
                String note = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.NOTE));
                String organization = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.ORGANIZATION));
                String photo_img = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.PHOTO_IMAGE));
                String telephone = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.TELEPHONE));
                String website = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.WEBSITE));
                String work_address = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.WORK_ADDRESS));
                String work_phone = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.WORKPHONE));
                String global_id = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.GLOBAL_ID));
                String operate_id=cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.OPERATE_ID));
                contact.setContactId(contact_id);
                contact.setChangeTime(change_time);
                contact.setDisplayName(display_name);
                contact.setEmail(email);
                contact.setGroupMember(group_member);
                contact.setHomeAddress(home_address);
                contact.setIm(im);
                contact.setMobilePhone(mobile_phone);
                contact.setNickName(nick_name);
                contact.setNote(note);
                contact.setOrganization(organization);
                contact.setPhotoImg(photo_img);
                contact.setTelephone(telephone);
                contact.setWebsite(website);
                contact.setWorkAddress(work_address);
                contact.setWorkphone(work_phone);
                contact.setGlobalId(global_id);
                contact.setOperateId(operate_id);
            }
            cursorSync.close();
        }

        return contact;

    }
    public boolean judgeSyncDataExist() {
        Cursor cursor = mContentResolver.query(
                Provider.SyncColumns.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String querySyncDataExist(Contact contact) {
        Cursor cursorSync = mContentResolver.query(
                Provider.SyncColumns.CONTENT_URI, null, null, null, null);
        String flag = "";
        if (cursorSync != null) {
            while (cursorSync.moveToNext()) {
                String sync_name = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.DISPLAY_NAME));
                String sync_email = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.EMAIL));
                String sync_group_member = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.GROUP_MEMBER));
                String sync_home_address = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.HOME_ADDRESS));
                String sync_im = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.IM));
                String sync_mobile_phone = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.MOBILE_PHONE));
                String sync_nick_name = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.NICK_NAME));
                String sync_note = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.NOTE));
                String sync_organization = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.ORGANIZATION));
                String sync_photo_img = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.PHOTO_IMAGE));
                String sync_telephone = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.TELEPHONE));
                String sync_website = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.WEBSITE));
                String sync_work_address = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.WORK_ADDRESS));
                String sync_work_phone = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.WORKPHONE));
                String sync_operate_id = cursorSync.getString(cursorSync
                        .getColumnIndex(Provider.SyncColumns.OPERATE_ID));
                if (!"".equals(contact.getDisplayName())
                        && !"".equals(sync_name)) {
                    isNameEquals = contact.getDisplayName().equals(sync_name);
                } else if ("".equals(contact.getDisplayName())
                        && "".equals(sync_name)) {
                    isNameEquals = true;
                } else if ("".equals(contact.getDisplayName())
                        || "".equals(sync_name)) {
                    isNameEquals = false;
                }
                if (!"".equals(contact.getEmail()) && !"".equals(sync_email)) {
                    isEmailEquals = contact.getEmail().equals(sync_email);
                } else if ("".equals(contact.getEmail())
                        && "".equals(sync_email)) {
                    isEmailEquals = true;
                } else if ("".equals(contact.getEmail())
                        || "".equals(sync_email)) {
                    isEmailEquals = false;
                }
                if (!"".equals(contact.getGroupMember())
                        && !"".equals(sync_group_member)) {
                    isGroupMemberEquals = contact.getGroupMember().equals(
                            sync_group_member);
                } else if ("".equals(contact.getGroupMember())
                        && "".equals(sync_group_member)) {
                    isGroupMemberEquals = true;
                } else if ("".equals(contact.getGroupMember())
                        || "".equals(sync_group_member)) {
                    isGroupMemberEquals = false;
                }
                if (!"".equals(contact.getHomeAddress())
                        && !"".equals(sync_home_address)) {
                    isHomeAddressEquals = contact.getHomeAddress().equals(
                            sync_home_address);
                } else if ("".equals(contact.getHomeAddress())
                        && "".equals(sync_home_address)) {
                    isHomeAddressEquals = true;
                } else if ("".equals(contact.getHomeAddress())
                        || "".equals(sync_home_address)) {
                    isHomeAddressEquals = false;
                }
                Log.i("ss",
                        "onChange_____contact.getWorkAddress():"
                                + contact.getWorkAddress() + sync_work_address);
                if (!"".equals(contact.getWorkAddress())
                        && !"".equals(sync_work_address)) {
                    isWorkAddressEquals = contact.getWorkAddress().equals(
                            sync_work_address);
                } else if ("".equals(contact.getWorkAddress())
                        && "".equals(sync_work_address)) {
                    isWorkAddressEquals = true;
                } else if ("".equals(contact.getWorkAddress())
                        || "".equals(sync_work_address)) {
                    Log.i("ss", "isWorkAddressEquals--");
                    isWorkAddressEquals = false;
                }
                if (!"".equals(contact.getIm()) && !"".equals(sync_im)) {
                    isImEquals = contact.getIm().equals(sync_im);
                } else if ("".equals(contact.getIm()) && "".equals(sync_im)) {
                    isImEquals = true;
                } else if ("".equals(contact.getIm()) || "".equals(sync_im)) {
                    isImEquals = false;
                }
                if (!"".equals(contact.getMobilePhone())
                        && !"".equals(sync_mobile_phone)) {
                    isMobilePhoneEquals = contact.getMobilePhone().equals(
                            sync_mobile_phone);
                } else if ("".equals(contact.getMobilePhone())
                        && "".equals(sync_mobile_phone)) {
                    isMobilePhoneEquals = true;
                } else if ("".equals(contact.getMobilePhone())
                        || "".equals(sync_mobile_phone)) {
                    isMobilePhoneEquals = false;
                }
                if (!"".equals(contact.getWorkphone())
                        && !"".equals(sync_work_phone)) {
                    isWorkPhoneEquals = contact.getWorkphone().equals(
                            sync_work_phone);
                } else if ("".equals(contact.getWorkphone())
                        && "".equals(sync_work_phone)) {
                    isWorkPhoneEquals = true;
                } else if ("".equals(contact.getWorkphone())
                        || "".equals(sync_work_phone)) {
                    isWorkPhoneEquals = false;
                }
                if (!"".equals(contact.getTelephone())
                        && !"".equals(sync_telephone)) {
                    isTelephoneEquals = contact.getTelephone().equals(
                            sync_telephone);
                } else if ("".equals(contact.getTelephone())
                        && "".equals(sync_telephone)) {
                    isTelephoneEquals = true;
                } else if ("".equals(contact.getTelephone())
                        || "".equals(sync_telephone)) {
                    isTelephoneEquals = false;
                }
                if (!"".equals(contact.getNote()) && !"".equals(sync_note)) {
                    isNoteEquals = contact.getNote().equals(sync_note);
                } else if ("".equals(contact.getNote()) && "".equals(sync_note)) {
                    isNoteEquals = true;
                } else if ("".equals(contact.getNote()) || "".equals(sync_note)) {
                    isNoteEquals = false;
                }
                if (!"".equals(contact.getNickName())
                        && !"".equals(sync_nick_name)) {
                    isNickNameEquals = contact.getNickName().equals(
                            sync_nick_name);
                } else if ("".equals(contact.getNickName())
                        && "".equals(sync_nick_name)) {
                    isNickNameEquals = true;
                } else if ("".equals(contact.getNickName())
                        || "".equals(sync_nick_name)) {
                    isNickNameEquals = false;
                }
                if (!"".equals(contact.getOrganization())
                        && !"".equals(sync_organization)) {
                    isOrganizationEquals = contact.getOrganization().equals(
                            sync_organization);
                } else if ("".equals(contact.getOrganization())
                        && "".equals(sync_organization)) {
                    isOrganizationEquals = true;
                } else if ("".equals(contact.getOrganization())
                        || "".equals(sync_organization)) {
                    isOrganizationEquals = false;
                }
                if (!"".equals(contact.getPhotoImg())
                        && !"".equals(sync_photo_img)) {
                    isPhotoImgEquals = contact.getPhotoImg().equals(
                            sync_photo_img);
                } else if ("".equals(contact.getPhotoImg())
                        && "".equals(sync_photo_img)) {
                    isPhotoImgEquals = true;
                } else if ("".equals(contact.getPhotoImg())
                        || "".equals(sync_photo_img)) {
                    isPhotoImgEquals = false;
                }
                if (!"".equals(contact.getWebsite())
                        && !"".equals(sync_website)) {
                    isWebsiteEquals = contact.getWebsite().equals(sync_website);
                } else if ("".equals(contact.getWebsite())
                        && "".equals(sync_website)) {
                    isWebsiteEquals = true;
                } else if ("".equals(contact.getWebsite())
                        || "".equals(sync_website)) {
                    isWebsiteEquals = false;
                }
                if (isNameEquals && isEmailEquals && isWorkAddressEquals
                        && isGroupMemberEquals && isImEquals
                        && isMobilePhoneEquals && isWorkPhoneEquals
                        && isTelephoneEquals && isNoteEquals
                        && isNickNameEquals && isOrganizationEquals
                        && isPhotoImgEquals && isWebsiteEquals
                        && isHomeAddressEquals) {
                    if (sync_operate_id.equals(Contact.OPERATOR_ID_UPDATE)) {
                        flag = Contact.OPERATOR_ID_UPDATE;
                    } else if (sync_operate_id
                            .equals(Contact.OPERATOR_ID_INSERT)) {
                        flag = Contact.OPERATOR_ID_INSERT;
                    } else if (sync_operate_id
                            .equals(Contact.OPERATOR_ID_DELETE)) {
                        flag = Contact.OPERATOR_ID_DELETE;
                    }
                    break;
                }
            }
            cursorSync.close();
        }
        return flag;
    }
    public void insertSyncTimeToDataBase(String syncTime) {
        int id = 1;
        Person p = new Person();
        ContentValues value = new ContentValues();
        value.put(Provider.PersonColumns.SYNC_TIME, p.syncTime);
        int i = mContentResolver.update(
                Provider.PersonColumns.CONTENT_URI, value,
                Provider.PersonColumns._ID + "=?", new String[] { id + "" });

    }

}
