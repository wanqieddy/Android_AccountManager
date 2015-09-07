package com.phicomm.account.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.phicomm.account.R;
import com.phicomm.account.main.AuthenticatorActivity.UserLoginTask;
import com.phicomm.account.provider.DatabaseHelper;
import com.phicomm.account.provider.Person;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.register.FindPwdWebView;
import com.phicomm.account.register.RegWebView;
import com.phicomm.account.util.NetworkUtilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.phicomm.account.util.FxDisplayAuthInfo;

public class InfoMoreActivity extends Activity {
    private static final String TAG = "InfoMoreActivity";
    private ListView mListView;
    private TextView mExit;
    private InfoAdapter mAdapter;
    private HashMap<String, String> map = new HashMap<String, String>();
    private ViewHolder holder = new ViewHolder();
    private ArrayList<String> userLable = new ArrayList<String>();
    private ArrayList<String> userName = new ArrayList<String>();
    private TextView mUserName;
    private ImageView img;
    private ImageView error;
    private EditText mAuthPassword;
    private TextView info;
    private TextView mFindPassword;
    private String name;
    private String password;
    private ProgressDialog mProgressDialog;
    private int count;
    private static final int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_more);
        initalData();
        mExit = (TextView) findViewById(R.id.exit);
        mExit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createDialog();
            }
        });
    }

    private void initalData() {
        // TODO Auto-generated method stub
        mListView = (ListView) findViewById(R.id.listView);
        quaryDataBases(ID);
        mAdapter = new InfoAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    private void quaryDataBases(int id) {
        String[] project = new String[] { Provider.PersonColumns.NAME,
                Provider.PersonColumns.USER_ID,
                Provider.PersonColumns.USER_EMAIL,
                Provider.PersonColumns.PHONE, Provider.PersonColumns.TRUE_NAME,
                Provider.PersonColumns.USER_SEX,
                Provider.PersonColumns.USER_BIRTH,
                Provider.PersonColumns.NICK_NAME,
                Provider.PersonColumns.USER_ADDRESS,
                Provider.PersonColumns.PASSWORD };
        map.clear();
        userLable.clear();
        userName.clear();
        Cursor c = getContentResolver().query(
                Provider.PersonColumns.CONTENT_URI, project,
                Provider.PersonColumns._ID + "=?",
                new String[] { String.valueOf(id) }, null);
        if (c != null && c.moveToFirst()) {
            name = c.getString(c.getColumnIndex(Provider.PersonColumns.NAME));
            password = c.getString(c
                    .getColumnIndex(Provider.PersonColumns.PASSWORD));
            map.put(getResources().getString(R.string.username),name);
            map.put(getResources().getString(R.string.id), c.getString(c
                    .getColumnIndex(Provider.PersonColumns.USER_ID)));
            map.put(getResources().getString(R.string.email), c.getString(c
                    .getColumnIndex(Provider.PersonColumns.USER_EMAIL)));
            map.put(getResources().getString(R.string.phone),
                    c.getString(c.getColumnIndex(Provider.PersonColumns.PHONE)));
            map.put(getResources().getString(R.string.trueName), c.getString(c
                    .getColumnIndex(Provider.PersonColumns.TRUE_NAME)));
            map.put(getResources().getString(R.string.sex), c.getString(c
                    .getColumnIndex(Provider.PersonColumns.USER_SEX)));
            map.put(getResources().getString(R.string.birth), c.getString(c
                    .getColumnIndex(Provider.PersonColumns.USER_BIRTH)));
            map.put(getResources().getString(R.string.nickname), c.getString(c
                    .getColumnIndex(Provider.PersonColumns.NICK_NAME)));
            map.put(getResources().getString(R.string.city), c.getString(c
                    .getColumnIndex(Provider.PersonColumns.USER_ADDRESS)));
        }
        c.close();
        java.util.Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            userLable.add(entry.getKey().toString());
            userName.add(map.get(entry.getKey().toString()));
        }
    }

    private void createDialog() {
        // TODO Auto-generated method stub
        View view = getLayoutInflater().inflate(R.layout.authentication, null);
        img = (ImageView) view.findViewById(R.id.lock);
        error = (ImageView) view.findViewById(R.id.error);
        mAuthPassword = (EditText) view.findViewById(R.id.auth_password);
        // mAuthPassword.setOnFocusChangeListener(mFocusChangeListener);
        info = (TextView) view.findViewById(R.id.info);
        mFindPassword = (TextView) view.findViewById(R.id.find_password);
        mFindPassword.setOnClickListener(mFindClickListener);
        mUserName = (TextView) view.findViewById(R.id.user_name);
        mUserName.setText(getResources().getString(R.string.auth) + name);
        Builder builder = new AlertDialog.Builder(InfoMoreActivity.this);
        builder.setTitle(R.string.Authentication);
        builder.setView(view);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setDialog(dialog, false);
                        if (TextUtils.isEmpty(mAuthPassword.getText()
                                .toString())) {
                            setDisplayInfo(R.string.passwordIsNull, 0);
                        } else if (!mAuthPassword.getText().toString()
                                .equals(password)) {
                            setDisplayInfo(R.string.passwordIsWrong,
                                    R.drawable.clear);
                        } else if (mAuthPassword.getText().toString()
                                .equals(password)) {
                            dialog.dismiss();
                            mProgressDialog = ProgressDialog.show(
                                    InfoMoreActivity.this, null, getResources()
                                            .getString(R.string.exit_info),
                                    true, false);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    count = getContentResolver().delete(
                                            Provider.PersonColumns.CONTENT_URI,
                                            null, null);
                                    handler.sendEmptyMessage(count);
                                }
                            }).start();

                        } else if (FxDisplayAuthInfo.isPassword(mAuthPassword
                                .getText().toString())) {
                            setDisplayInfo(R.string.passwordFormatWrong,
                                    R.drawable.clear);
                        }
                    }

                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        setDialog(dialog, true);
                        dialog.dismiss();
                    }

                });

        builder.create().show();
    }

    private void setDialog(DialogInterface dialog, boolean isShowing) {
        try {
            Field field = dialog.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, isShowing);
            dialog.dismiss();
        } catch (Exception e) {
            Log.i(TAG, "set dialog failure");
        }
    }

    private void setDisplayInfo(int stringId, int drawableId) {
        info.setTextColor(Color.RED);
        info.setText(stringId);
        img.setBackgroundResource(R.drawable.lock_wrong);
        Log.i("lbj",
                "lalalal :"
                        + (!TextUtils.isEmpty(mAuthPassword.getText()
                                .toString())));
        if (mAuthPassword.hasFocus() && (drawableId != 0)) {
            error.setBackgroundResource(drawableId);
        } else {
            error.setBackgroundResource(0);
        }
        error.setOnClickListener(mErrorClickListener);
    }

    private OnClickListener mFindClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(InfoMoreActivity.this, FindPwdWebView.class);
            startActivity(intent);
        }
    };

    private OnClickListener mErrorClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mAuthPassword.setText("");
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mProgressDialog.isShowing() && (count > 0)) {
                mProgressDialog.dismiss();
            }
            Intent intent = new Intent();
            intent.setClass(InfoMoreActivity.this, MainActivity.class);
            startActivity(intent);
            InfoMoreActivity.this.finish();
        }
    };

    static class ViewHolder {
        TextView mUserLabel; // for label of switcher
        TextView mUserInfo; // for draging switcher
    }

    class InfoAdapter extends BaseAdapter {
        public InfoAdapter(Context context) {
            super();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = getLayoutInflater();
                convertView = mInflater.inflate(R.layout.info, parent, false);
                holder = new ViewHolder();
                holder.mUserLabel = (TextView) convertView
                        .findViewById(R.id.user_label);
                holder.mUserInfo = (TextView) convertView
                        .findViewById(R.id.user_info);
            }
            holder.mUserLabel.setText(userLable.get(position));
            holder.mUserInfo.setText(userName.get(position));
            return convertView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return map.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return map.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
    }

}
