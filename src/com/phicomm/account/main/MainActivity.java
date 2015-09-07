package com.phicomm.account.main;

import java.util.Date;
import com.phicomm.account.R;
import com.phicomm.account.data.ContactUpload;
import com.phicomm.account.data.ContactsOperate;
import com.phicomm.account.data.SyncDataOperate;
import com.phicomm.account.provider.Person;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.requestmanager.PoCInitCheckRequestManager;
import com.phicomm.account.requestmanager.PoCInitContactRequestManager;
import com.phicomm.account.requestmanager.PoCInitMapRequestManager;
import com.phicomm.account.requestmanager.PoCInitSyncRequestManager;
import com.phicomm.account.requestmanager.PoCRequestFactory;
import com.phicomm.account.requestmanager.PoCRequestManager;
import com.phicomm.account.requestmanager.PoCInitUploadRequestManager;
import com.phicomm.account.requestmanager.Request;
import com.phicomm.account.requestmanager.RequestManager;
import com.phicomm.account.requestmanager.RequestManager.RequestListener;
import com.phicomm.account.util.Contact;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements RequestListener{
    private String TAG = "MainActivity";
    private LinearLayout mLayout;
    private ImageView mIcon;
    private TextView mAccount;
    private TextView mAddAccount;
    private String name = null;
    private View mSpace;
    private TextView startSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mLayout = (LinearLayout) findViewById(R.id.my_account);
        mIcon = (ImageView) mLayout.findViewById(R.id.my_img);
        mAccount = (TextView) mLayout.findViewById(R.id.my_tv);
        mSpace = (View) findViewById(R.id.space);
        query(1);
        mAddAccount = (TextView) findViewById(R.id.add);
        mLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                Bundle bl = new Bundle();
                bl.putString("userName", name);
                intent.putExtras(bl);
                intent.setClass(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mAddAccount.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(name)) {
                    startActivity(new Intent(MainActivity.this,
                            CloudActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.warnning,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        startSync = (TextView)findViewById(R.id.start);
        startSync.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onRequestStart();
            }
        });
        onRequestInit();
    }

    @Override
    public void onRequestFinished(Request requestArg, Bundle bundle) {
        Log.i("ss","onRequestFinished_________________________________requestArg.getRequestType():"+requestArg.getRequestType());
        if(requestArg != null){
            int type = requestArg.getRequestType();
            if(type == PoCRequestFactory.REQUEST_TYPE_INIT_UPLOAD ){
                Request request = PoCRequestFactory.getContactUploadRequest();
                PoCRequestManager mRequestManager = PoCRequestManager.from(this);;
                mRequestManager.execute(request, this);
            }else if(type == PoCRequestFactory.REQUEST_TYPE_CONTACT_UPLOAD){

                Cursor cursorUploadData = getContentResolver().query(
                        Provider.UploadDataColumns.CONTENT_URI, null, Provider.UploadDataColumns.OPERATE_ID + " = ?", new String[]{Contact.OPERATOR_ID_INSERT}, null);
                boolean needGetMapId = true;
                if (cursorUploadData != null) {
                    Log.i("ss","______________________________cursorUploadData.getCount():"+cursorUploadData.getCount());
                    if(cursorUploadData.getCount() == 0){
                        needGetMapId = false;
                    }
                    cursorUploadData.close();
                }
                Log.i("ss","______________________________needGetMapId:"+needGetMapId);
                if(needGetMapId){
                    Request requestInitMap = PoCRequestFactory.getGetMapRequest();
                    PoCRequestManager mRequestManagerMap = PoCRequestManager.from(this);;
                    mRequestManagerMap.execute(requestInitMap, this);
                }

                Request request = PoCRequestFactory.getInitSyncRequest();
                Log.i("ss",
                        "________________________________com.phicomm.account.return.body values :"
                                + bundle.getString("result"));
                request.put(RequestManager.INTENT_EXTRA_RETURN_BODY,
                        bundle.getString("result"));
                PoCInitSyncRequestManager mRequestManager = PoCInitSyncRequestManager.from(this);
                mRequestManager.execute(request, this);
            }else if(type == PoCRequestFactory.REQUEST_TYPE_INIT_SYNC){
                // syncSize 0
                SyncDataOperate mSync = new SyncDataOperate(this);
                if (!mSync.judgeSyncDataExist()) {
                    onRequestInit();
                } else {
                    Request request = PoCRequestFactory.getInitContactRequest();
                    PoCInitContactRequestManager mRequestManager = PoCInitContactRequestManager
                            .from(this);
                    mRequestManager.execute(request, this);
                }
            }else if(type == PoCRequestFactory.REQUEST_TYPE_GET_MAP){
                //to map
                String result = bundle.getString("result");
                Log.i("ss","____________________________________result:"+result);
                Request request = PoCRequestFactory.getInitMapRequest();
                request.put(RequestManager.INTENT_EXTRA_RETURN_BODY, bundle.getString("result"));
                PoCInitMapRequestManager mRequestManager = PoCInitMapRequestManager.from(this);
                mRequestManager.execute(request, this);
            }else if(type == PoCRequestFactory.REQUEST_TYPE_INIT_CONTACT){
                // init check table.
                onRequestInit();
                // clear sync
                getContentResolver().delete(Provider.SyncColumns.CONTENT_URI,
                        null, null);
            }
        }
    }

    private void onRequestStart() {
        Request request = PoCRequestFactory.getInitUploadRequest();
        PoCInitUploadRequestManager mRequestManager = PoCInitUploadRequestManager.from(this);
        mRequestManager.execute(request, this);
    }

    private void onRequestInit() {
        Request request = PoCRequestFactory.getInitCheckRequest();
        PoCInitCheckRequestManager mRequestManager = PoCInitCheckRequestManager.from(this);
        mRequestManager.execute(request, this);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // We do the actual work of authenticating the user
            // in the NetworkUtilities class.
            try {
                ContactUpload upload = new ContactUpload();
                upload.uploadContactListToService(MainActivity.this);
            } catch (Exception ex) {
                Log.e(TAG,
                        "UserLoginTask.doInBackground: failed to authenticate");
                Log.i(TAG, ex.toString());
                return "error";
            }
            return "";
        }

        @Override
        protected void onPostExecute(final String authToken) {
            Log.i(TAG, "onPostExecute authToken:" + authToken);
            ContactsOperate co = new ContactsOperate(getApplicationContext());
            co.UpdateSyncToContact();
        }

        @Override
        protected void onCancelled() {
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void query(int id) {
        Cursor c = getContentResolver().query(
                Provider.PersonColumns.CONTENT_URI,
                new String[] { Provider.PersonColumns.NAME,
                        Provider.PersonColumns.PASSWORD,
                        Provider.PersonColumns.USER_EMAIL,
                        Provider.PersonColumns.USER_ID },
                Provider.PersonColumns._ID + "=?", new String[] { id + "" },
                null);
        if (c != null && c.moveToFirst()) {
            Person p = new Person();
            p.name = c.getString(c
                    .getColumnIndexOrThrow(Provider.PersonColumns.NAME));
            p.password = c.getString(c
                    .getColumnIndexOrThrow(Provider.PersonColumns.PASSWORD));
            p.userEmail = c.getString(c
                    .getColumnIndexOrThrow(Provider.PersonColumns.USER_EMAIL));
            p.userId = c.getString(c
                    .getColumnIndexOrThrow(Provider.PersonColumns.USER_ID));
            if ((TextUtils.isEmpty(p.name) || TextUtils.isEmpty(p.userEmail) || TextUtils
                    .isEmpty(p.userId)) && TextUtils.isEmpty(p.password)) {
                mLayout.setVisibility(View.GONE);
                mSpace.setVisibility(View.GONE);

            } else if (!TextUtils.isEmpty(p.name)
                    && !TextUtils.isEmpty(p.password)) {
                name = p.name;
            } else if (!TextUtils.isEmpty(p.userEmail)
                    && !TextUtils.isEmpty(p.password)) {
                name = p.userEmail;
            } else if (!TextUtils.isEmpty(p.userId)
                    && !TextUtils.isEmpty(p.password)) {
                name = p.userId;
            }
            mLayout.setVisibility(View.VISIBLE);
            mSpace.setVisibility(View.VISIBLE);
            mIcon.setBackgroundResource(R.drawable.ic_exchange_selected);
            mAccount.setText(name);
            mLayout.setBackgroundResource(R.drawable.exist_account);

        }
        c.close();
    }
}
