package com.phicomm.account.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phicomm.account.R;
import com.phicomm.account.data.SyncDataOperate;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.requestmanager.PoCInitCheckRequestManager;
import com.phicomm.account.requestmanager.PoCInitContactRequestManager;
import com.phicomm.account.requestmanager.PoCInitMapRequestManager;
import com.phicomm.account.requestmanager.PoCInitSyncRequestManager;
import com.phicomm.account.requestmanager.PoCInitUploadRequestManager;
import com.phicomm.account.requestmanager.PoCRequestFactory;
import com.phicomm.account.requestmanager.PoCRequestManager;
import com.phicomm.account.requestmanager.Request;
import com.phicomm.account.requestmanager.RequestManager;
import com.phicomm.account.requestmanager.RequestManager.RequestListener;
import com.phicomm.account.util.Contact;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class UserInfoActivity extends Activity implements RequestListener{
    private TextView mUserName;
    private ImageView mUserImg;
    private Intent intent;
    private LinearLayout mLayout;
    private ListView mListView;
    private BaseAdapter mAppInfoadapter;
    private Boolean isOpen;
    private AppInfoAdapter mAdapter =null;
    List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private ImageView mAnimIcon;
    private TextView mSyncTime;
    private Map<String,Object> item;
    ViewHolder holder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        mUserName = (TextView) findViewById(R.id.user_name);
        mUserName.setText(getIntent().getExtras().getString("userName"));
        mUserImg = (ImageView) findViewById(R.id.image);
        mListView = (ListView) findViewById(android.R.id.list);
        if (getIntent().getExtras().getString("userImage") != null) {
            mUserImg.setImageBitmap(stringtoBitmap(getIntent().getExtras()
                    .getString("userImage")));
        } else {
            mUserImg.setBackgroundResource(R.drawable.ic_menu_contact);
        }
        mLayout = (LinearLayout) findViewById(R.id.layout);
        mLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(UserInfoActivity.this,
                        InfoMoreActivity.class));
                finish();
            }
        });
        onRequestInit();
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        initData();
    }

    private void initData() {
        // TODO Auto-generated method stub
        item = new HashMap<String, Object>();

        List<PackageInfo> packages = getPackageManager()
                .getInstalledPackages(0);

        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals("com.android.contacts")) {
                String appName = packageInfo.applicationInfo.loadLabel(
                        getPackageManager()).toString();
                Drawable appIcon = packageInfo.applicationInfo
                        .loadIcon(getPackageManager());
                item.put("name", appName);
                item.put("icon", appIcon);
                item.put("syncTime", getSyncTime());
                item.put("switcher", getSwitcherSelected());
                data.clear();
                data.add(item);
            }

        }
        mAdapter = new AppInfoAdapter();
        mListView.setAdapter(mAdapter);
    }

    private String getSyncTime() {
        String syncTime = null;
        int contactSwitcherSelected = 0;
        Cursor cursorPerson = getContentResolver().query(
                Provider.PersonColumns.CONTENT_URI, null, null, null, null);
        if (cursorPerson != null) {
            if (cursorPerson.moveToFirst()) {
                syncTime = cursorPerson.getString(cursorPerson
                        .getColumnIndex(Provider.PersonColumns.SYNC_TIME));
            }
            cursorPerson.close();
        }
        return syncTime;
    }

    private boolean getSwitcherSelected() {
        int contactSwitcherSelected = 0;
        Cursor cursorPerson = getContentResolver().query(
                Provider.PersonColumns.CONTENT_URI, null, null, null, null);
        if (cursorPerson != null) {
            if (cursorPerson.moveToFirst()) {
                contactSwitcherSelected = cursorPerson
                        .getInt(cursorPerson
                                .getColumnIndex(Provider.PersonColumns.CONTACT_SWITCHER_SELECTED));
            }
            cursorPerson.close();
        }
        Log.i("ss", "------contactSwitcherSelected :" + contactSwitcherSelected);
        return (contactSwitcherSelected == 0) ? false : true;
    }

    private void setSwitcherSelected(int id, int contactSwitcherSelected) {
        ContentValues value = new ContentValues();
        value.put(Provider.PersonColumns.CONTACT_SWITCHER_SELECTED,
                contactSwitcherSelected);
        int i = getContentResolver().update(Provider.PersonColumns.CONTENT_URI,
                value, Provider.PersonColumns._ID + "=?",
                new String[] { id + "" });
    }

    private class AppInfoAdapter extends BaseAdapter {

        AppInfoAdapter() {
            super();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                LayoutInflater mInflater = getLayoutInflater();
                convertView = mInflater.inflate(R.layout.data_adapter_row,
                        parent, false);
                holder = new ViewHolder();
                holder.mAppName = (TextView) convertView
                        .findViewById(R.id.label);
                holder.mSyncTime = (TextView) convertView
                        .findViewById(R.id.sync_time);
                holder.mAnimIcon = (ImageView) convertView
                        .findViewById(R.id.anim_icon);
                holder.mAppIcon = (ImageView) convertView
                        .findViewById(R.id.icon);
                holder.mSyncSwitcher = (Switch) convertView
                        .findViewById(R.id.sync_switcher);
            }
            Log.i("ss", "_____________getView11111111111111111111111111");
            holder.mAppName.setText(data.get(position).get("name").toString());
            holder.mAppIcon.setImageDrawable((Drawable) data.get(position).get(
                    "icon"));
            holder.mSyncTime.setText(getResources().getString(
                    R.string.old_sync_time)
                    + data.get(position).get("syncTime").toString());
            holder.mAnimIcon.setBackgroundResource(R.drawable.ic_sync);
            holder.mAnimIcon.setVisibility(View.GONE);
            Log.i("ss", "______________________data.get(position) :"
                    + (Boolean) data.get(position).get("switcher"));
            holder.mSyncSwitcher.setChecked((Boolean) data.get(position).get(
                    "switcher"));
            holder.mSyncSwitcher
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton arg0,
                                boolean isChecked) {
                            // TODO Auto-generated method stub
                            Log.i("ss", "----isChecked :" + isChecked);
                            setSwitcherSelected(1, isChecked ? 1 : 0);
                            if (isChecked) {
                                holder.mAnimIcon.setVisibility(View.VISIBLE);
                                Animation operatingAnim = AnimationUtils
                                        .loadAnimation(UserInfoActivity.this,
                                                R.anim.anim);
                                LinearInterpolator lin = new LinearInterpolator();
                                operatingAnim.setInterpolator(lin);
                                holder.mAnimIcon.startAnimation(operatingAnim);
                                onRequestStart();
                            } else {
                                holder.mAnimIcon.clearAnimation();
                                holder.mAnimIcon.setVisibility(View.GONE);
                            }
                        }
                    });
            return convertView;
        }
    }

    class ViewHolder {
        TextView mAppName;
        TextView mSyncTime;
        ImageView mAppIcon;
        ImageView mAnimIcon;
        Switch mSyncSwitcher;
    }

    private void removeAnimation() {
        holder.mAnimIcon.clearAnimation();
        holder.mAnimIcon.setVisibility(View.GONE);
        holder.mSyncTime.setText(getResources().getString(
                R.string.old_sync_time)
                + getSyncTime());
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
    public Bitmap stringtoBitmap(String string) {

        Bitmap bitmap = null;

        try {

            byte[] bitmapArray;

            bitmapArray = Base64.decode(string, Base64.DEFAULT);

            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,

            bitmapArray.length);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return bitmap;

    }

}
