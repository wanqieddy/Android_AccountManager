/**
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 * <p>
 * Licensed under the Beerware License : <br />
 * As long as you retain this notice you can do whatever you want with this stuff. If we meet some
 * day, and you think this stuff is worth it, you can buy me a beer in return
 */

package com.phicomm.account.operator;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.phicomm.account.config.WSConfig;
import com.phicomm.account.data.ContactUpload;
import com.phicomm.account.data.UploadDataOperate;
import com.phicomm.account.network.ConnectionException;
import com.phicomm.account.network.NetworkConnection;
import com.phicomm.account.network.NetworkConnection.ConnectionResult;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.requestmanager.Request;
import com.phicomm.account.service.RequestService.Operation;
import com.phicomm.account.util.Contact;
import com.phicomm.account.util.NetworkUtilities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

public final class UploadContactOperation implements Operation {

    @Override
    public Bundle execute(Context context, Request request) {
        //read upload table
        //xml
        //upload to service   -- ok

        String ssession_id = null;
        String user_key = null;
        String user_name = null;
        String xmlDoc = null;
        String sync_time = null;
        Bundle resultData = new Bundle();
        Cursor persionCursor = context.getContentResolver().query(Provider.PersonColumns.CONTENT_URI, null, null, null, null);
        if(persionCursor != null){
            if(persionCursor.moveToFirst()){
                ssession_id = persionCursor.getString(persionCursor
                        .getColumnIndex(Provider.PersonColumns.JSSESSIONID));
                user_key = persionCursor.getString(persionCursor
                        .getColumnIndex(Provider.PersonColumns.USER_KEY));
                user_name = persionCursor.getString(persionCursor
                        .getColumnIndex(Provider.PersonColumns.NAME));
                sync_time = persionCursor.getString(persionCursor.getColumnIndex(Provider.PersonColumns.SYNC_TIME));
            }
            persionCursor.close();
        }
        //mode xmlDoc.
        ContactUpload upload = new ContactUpload();
        UploadDataOperate uploadOperate = new UploadDataOperate(context);
        Log.i("ss", "_____upload :" + uploadOperate.judgeUploadDataExist());
        if (uploadOperate.judgeUploadDataExist()) {
            ArrayList<Contact> updataValues = upload
                    .getUploadContactList(context);
            xmlDoc = NetworkUtilities.modContactToXml(updataValues, user_name,
                    user_key,sync_time);
        }
        String url = WSConfig.WS_SYNC_CONTACT_URL + ssession_id;
        NetworkConnection networkConnection1 = new NetworkConnection(context, url);
        ArrayList<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>();
        BasicNameValuePair value = new BasicNameValuePair("XML",xmlDoc);
        parameterList.add(value);
        networkConnection1.setParameters(parameterList);
        try {
            ConnectionResult result = networkConnection1.execute();
            resultData.putString("result", result.body);
            Log.i("ss","________________________________result.body:"+result.body);
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        Log.i("ss","_______________________________________OK");
        return resultData;
    }
}
