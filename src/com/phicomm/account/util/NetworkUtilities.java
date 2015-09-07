package com.phicomm.account.util;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.phicomm.account.network.NetworkConnection;
import com.phicomm.account.network.NetworkConnection.ConnectionResult;
import com.phicomm.account.provider.Provider;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class NetworkUtilities {

    public static String authenticate(Context context, String username, String password) throws Exception {
        NetworkConnection networkConnection = new NetworkConnection(context,"http://172.17.100.197/cloudBackup/login!userLogin.action");
        ArrayList<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>();
        User user = new User(username, MD5.getMD5Passwored(password));
        BasicNameValuePair value = new BasicNameValuePair("XML",modUserName(user));
        parameterList.add(value);
        networkConnection.setParameters(parameterList);
        ConnectionResult result = networkConnection.execute();
        return result.body;
    }
    public static String syncContactsDown(String arg) throws Exception {
        String serviceUrl = "http://172.17.100.197/cloudBackup/UserFileRemotePort?wsdl";
        String methodName = "syncContactsDown";
        SoapObject request = new SoapObject("http://remote.cloud.fx.com/", methodName);
        request.addProperty("arg0", arg);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = request;
        HttpTransportSE ht = new HttpTransportSE(serviceUrl, 15 * 1000);
        ht.call(null, envelope);
        if (envelope.getResponse() != null) {
            return "" + envelope.getResponse();
        } else {
            return "error";
        }
    }
    public static String syncContactsUp(Context context, ArrayList<Contact> contactList) throws Exception {

        String jssessionid = null;
        String user_name = null;
        String user_key = null;
        String sync_time=null;
        Cursor cursor = context.getContentResolver().query(Provider.PersonColumns.CONTENT_URI, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            jssessionid = cursor.getString(cursor.getColumnIndex(Provider.PersonColumns.JSSESSIONID));
            user_name = cursor.getString(cursor.getColumnIndex(Provider.PersonColumns.NAME));
            user_key = cursor.getString(cursor.getColumnIndex(Provider.PersonColumns.USER_KEY));
            sync_time = cursor.getString(cursor.getColumnIndex(Provider.PersonColumns.SYNC_TIME));
            cursor.close();
        }
        Log.i("ss","_______________________jssessionid:"+jssessionid);
        NetworkConnection networkConnection = new NetworkConnection(context,

        "http://172.17.100.197/cloudBackup/contact!syncContact.action;jsessionid="+jssessionid);
        ArrayList<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>();
        Log.i("ss","___________________modContactToXml(contactList):"+modContactToXml(contactList, user_name, user_key,sync_time));
        BasicNameValuePair value = new BasicNameValuePair("XML", modContactToXml(contactList, user_name, user_key,sync_time));
        parameterList.add(value);
        networkConnection.setParameters(parameterList);
        ConnectionResult result = networkConnection.execute();
        Log.i("ss","____________________hello_________result.body:"+result.body);
        return result.body;
    }

    public static String modContactToXml(ArrayList<Contact> contactList, String userNameArg, String userKeyArg,String syncTimes){
        Document document = DocumentHelper.createDocument();
        Element booksElement = document.addElement("nodes");
        Element user_name = booksElement.addElement("user_name");
        user_name.setText(userNameArg);
        Element user_key = booksElement.addElement("user_key");
        user_key.setText(userKeyArg);
        Element sync_time = booksElement.addElement("sync_time");
        Log.i("ss","________syncTimes :"+syncTimes);
        if(syncTimes==null){
            sync_time.setText("");
        }
        else {
            sync_time.setText(syncTimes);
        }
        Contact contact = null;
        Log.i("ss","________!!!!!!!!!!!!222222222222___________");
        for(int i=0;i<contactList.size();i++){
            contact = contactList.get(i);
            Element bookElement = booksElement.addElement("node");
            Element contact_id = bookElement.addElement("mobile_contact_id");
            contact_id.setText(contact.getContactId());
            Element change_time = bookElement.addElement("change_time");
            change_time.setText(contact.getChangeTime());
            Element operate_id = bookElement.addElement("operate_id");
            operate_id.setText(contact.getOperateId());
            Element display_name = bookElement.addElement("display_name");
            display_name.setText(contact.getDisplayName());
            Element nick_name = bookElement.addElement("nick_name");
            nick_name.setText(contact.getNickName());
            Element work_address = bookElement.addElement("work_address");
            work_address.setText(contact.getWorkAddress());
            Element home_address = bookElement.addElement("home_address");
            home_address.setText(contact.getHomeAddress());
            Element organization = bookElement.addElement("organization");
            organization.setText(contact.getOrganization());
            Element mobile_phone = bookElement.addElement("mobile_phone");
            mobile_phone.setText(contact.getMobilePhone());
            Element telephone = bookElement.addElement("telephone");
            telephone.setText(contact.getTelephone());
            Element workphone = bookElement.addElement("workphone");
            workphone.setText(contact.getWorkphone());
            Element email = bookElement.addElement("email");
            email.setText(contact.getEmail());
            Element website = bookElement.addElement("website");
            website.setText(contact.getWebsite());
            Element photo_img = bookElement.addElement("photo_img");
            photo_img.setText(contact.getPhotoImg());
            Element note = bookElement.addElement("note");
            note.setText(contact.getNote());
            Element im = bookElement.addElement("im");
            im.setText(contact.getIm());
            Element group_member = bookElement.addElement("group_member");
            group_member.setText(contact.getGroupMember());
            Element old_phone = bookElement.addElement("old_phone");
            old_phone.setText(contact.getOldPhone());
            Element old_name = bookElement.addElement("old_name");
            old_name.setText(contact.getOldName());
            Element old_email = bookElement.addElement("old_email");
            old_email.setText(contact.getOldEmail());
            Element old_groupmember = bookElement.addElement("old_groupmember");
            old_groupmember.setText(contact.getOldGroupMember());
            Element old_homeaddress = bookElement.addElement("old_homeaddress");
            old_homeaddress.setText(contact.getOldHomeAddress());
            Element old_im = bookElement.addElement("old_im");
            old_im.setText(contact.getOldIm());
            Element old_note = bookElement.addElement("old_note");
            old_note.setText(contact.getOldNote());
            Element old_nickname = bookElement.addElement("old_nickname");
            old_nickname.setText(contact.getOldNickName());
            Element old_organization = bookElement
                    .addElement("old_organization");
            old_organization.setText(contact.getOldOrganization());
            Element old_photoimg = bookElement.addElement("old_photoimg");
            old_photoimg.setText(contact.getOldPhotoImg());
            Element old_telephone = bookElement.addElement("old_telephone");
            old_telephone.setText(contact.getOldTelephone());
            Element old_website = bookElement.addElement("old_website");
            old_website.setText(contact.getOldWebsite());
            Element old_workaddress = bookElement.addElement("old_workaddress");
            old_workaddress.setText(contact.getOldWorkAddress());
            Element old_workphone = bookElement.addElement("old_workphone");
            old_workphone.setText(contact.getOldWorkphone());
        }
        return document.asXML();
    }

    private static String modUserName(User user) {
        Document document = DocumentHelper.createDocument();
        Element booksElement = document.addElement("nodes");
        Element bookElement = booksElement.addElement("node");
        Element USER_NAME = bookElement.addElement("userName");
        USER_NAME.setText(user.getUserName());
        Element PASS_WORD = bookElement.addElement("userPassword");
        PASS_WORD.setText(user.getPassWord());
        /*
         * Element KEY_WORD = bookElement.addElement("userKey");
         * KEY_WORD.setText("EPW6pgh8GiQtrG+SIvZh1A==");
         */
        String str = document.asXML();
        return str;
    }
}
