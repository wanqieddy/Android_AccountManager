package com.phicomm.account.main;

import android.accounts.AccountAuthenticatorActivity;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

import com.phicomm.account.R;
import com.phicomm.account.provider.DatabaseHelper;
import com.phicomm.account.provider.Person;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.register.FindPwdWebView;
import com.phicomm.account.register.RegWebView;
import com.phicomm.account.util.FxConstants;
import com.phicomm.account.util.MCrypt;
import com.phicomm.account.util.MD5;
import com.phicomm.account.util.NetworkUtilities;
import com.phicomm.account.util.ReadDoc;
import com.phicomm.account.util.Result;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import com.phicomm.account.util.FxDisplayAuthInfo;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.view.View.OnFocusChangeListener;
import com.phicomm.account.util.ClearEditText;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {

    private String TAG = "AuthenticatorActivity";
    private String mUsername, mPassword;
    private ClearEditText mUsernameEdit;
    private ClearEditText mPasswordEdit;
    private UserLoginTask mAuthTask;
    private AccountManager mAccountManager;
    private TextView mLoginView;
    private TextView mFindPassword;
    private View mEye;
    private Result resultFromService;
    private TextView mAccountInfo;
    //modified for bug:CBBAT-1120.
    private View mUserView;
    private View mPasswordView;
    //end
    private static final int ID = 1;

    @Override
    protected void onCreate(Bundle icicle) {
        // TODO Auto-generated method stub
        super.onCreate(icicle);
        setContentView(R.layout.login_activity);
        mUsernameEdit = (ClearEditText) findViewById(R.id.username);
        mPasswordEdit = (ClearEditText) findViewById(R.id.password);
        mLoginView = (TextView) findViewById(R.id.account_login);
        mFindPassword = (TextView) findViewById(R.id.find_password);
        mEye = (View) findViewById(R.id.eye);
        mUserView = (View) findViewById(R.id.user_view);
        mPasswordView = (View) findViewById(R.id.password_view);
        mFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(AuthenticatorActivity.this,
                        FindPwdWebView.class);
                startActivity(intent);
            }
        });
        mLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                handleLogin();
            }
        });
        mEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //modified for bug:CBBAT-1122.
                if (mPasswordEdit.getInputType() == 129) {
                    mPasswordEdit
                            .setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }
        });
        mAccountInfo = (TextView) findViewById(R.id.account_info);
        mAccountInfo.setTextColor(Color.RED);
        //modified for bug:CBBAT-1120.
        mUsernameEdit.setOnFocusChangeListener(mUsernameFocusChangeListener);
        mPasswordEdit.setOnFocusChangeListener(mPasswordFocusChangeListener);
        mAccountManager = AccountManager.get(this);
    }
    //modified for bug:CBBAT-1120.
    private OnFocusChangeListener mUsernameFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                mUserView
                        .setBackgroundResource(R.drawable.icon_contact_pressed);
            } else {
                mUserView.setBackgroundResource(R.drawable.icon_contact);
            }
        }
    };

    private OnFocusChangeListener mPasswordFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                mPasswordView
                        .setBackgroundResource(R.drawable.icon_contact_lock);
            } else {
                mPasswordView
                        .setBackgroundResource(R.drawable.ic_cancle_lock_normal);
            }
        }
    };
    //end
    public void handleLogin() {
        mUsername = mUsernameEdit.getText().toString();
        mPassword = mPasswordEdit.getText().toString();
        if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
            //modified for bug:CBBAT-1123.
            if (TextUtils.isEmpty(mUsername) && !TextUtils.isEmpty(mPassword)) {
                mAccountInfo.setText(getResources().getString(
                        R.string.nameisNull));
            } else if (!TextUtils.isEmpty(mUsername)
                    && TextUtils.isEmpty(mPassword)) {
                mAccountInfo.setText(getResources().getString(
                        R.string.passwordIsNull));
            } else {
                mAccountInfo.setText(getResources().getString(
                        R.string.psdOrNameisNull));
            }

        } else {
            // Show a progress dialog, and kick off a background task to perform
            // the user login attempt.
            mAuthTask = new UserLoginTask();
            // mAuthTask.execute();
            Log.i(TAG, "Fx  mAuthTask.execute():" + mAuthTask.execute());
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // We do the actual work of authenticating the user
            // in the NetworkUtilities class.
            try {
                Log.i(TAG, "Fx UserLoginTask");
                return NetworkUtilities.authenticate(getApplicationContext() ,mUsername, mPassword);
            } catch (Exception ex) {
                Log.e(TAG,
                        "UserLoginTask.doInBackground: failed to authenticate");
                Log.i(TAG, ex.toString());
                return "error";
            }
        }

        @Override
        protected void onPostExecute(final String authToken) {
            // On a successful authentication, call back into the Activity to
            // communicate the authToken (or null for an error).
            Log.i(TAG, "onPostExecute authToken:" + authToken);
            onAuthenticationResult(authToken);
        }

        @Override
        protected void onCancelled() {
            // If the action was canceled (by the user clicking the cancel
            // button in the progress dialog), then call back into the
            // activity to let it know.
            // onAuthenticationCancel();
        }
    }

    public void onAuthenticationResult(String authToken) {
        // TODO Auto-generated method stub
        if ("error".equals(authToken)) {
            popDialog();
            return;
        }
        try {
            Log.i(TAG, "Fx authToken:" + authToken);
            StringReader sr = new StringReader(authToken);
            InputSource is = new InputSource(sr);
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(is);
            resultFromService = ReadDoc.readXML(doc);
            Log.i(TAG, "Fx resultFromService.getStatusCode():"
                    + resultFromService.getStatusCode());
            if (resultFromService.getStatusCode().equals("0")) {
                setDataToDataBases(resultFromService);
            } else {
                FxDisplayAuthInfo.displayUserInfo(mUsername, mPassword,
                        mAccountInfo, resultFromService, mUserView,
                        mPasswordView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void popDialog() {
        Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.prompt);
        builder.setMessage(R.string.prompt_info);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        startActivity(new Intent(
                                android.provider.Settings.ACTION_WIFI_SETTINGS));
                    }
                });
        builder.create().show();
    }

    private void setDataToDataBases(Result resultFromService) {
        // TODO Auto-generated method stub
        Person p = new Person();
        String isStringNull = getResources().getString(R.string.isStringNull);
        String isMale = getResources().getString(R.string.isMale);
        String isFemale = getResources().getString(R.string.isFemale);
        if (TextUtils.isEmpty(resultFromService.getUserName())
                || resultFromService.getUserName() == null) {
            p.name = isStringNull;
        } else {
            p.name = resultFromService.getUserName();
        }
        if (TextUtils.isEmpty(resultFromService.getUserId())
                || resultFromService.getUserId() == null) {
            p.userId = isStringNull;
        } else {
            p.userId = resultFromService.getUserId();
        }
        if (TextUtils.isEmpty(resultFromService.getUserEmail())
                || resultFromService.getUserEmail() == null) {
            p.userEmail = isStringNull;
        } else {
            p.userEmail = resultFromService.getUserEmail();

        }
        if (TextUtils.isEmpty(resultFromService.getPhone())
                || resultFromService.getPhone() == null
                || resultFromService.getPhone().equals("null")) {
            p.phone = isStringNull;
        } else {
            p.phone = resultFromService.getPhone();

        }
        if (TextUtils.isEmpty(resultFromService.getUserTrueName())
                || resultFromService.getUserTrueName() == null) {
            p.trueName = isStringNull;
        } else {
            p.trueName = resultFromService.getUserTrueName();

        }
        if (resultFromService.getUserSex().equals("0")) {
            p.userSex = isMale;
        } else {
            p.userSex = isFemale;

        }
        if (TextUtils.isEmpty(resultFromService.getUserBirth())
                || resultFromService.getUserBirth() == null) {
            p.userBirth = isStringNull;
        } else {
            p.userBirth = resultFromService.getUserBirth();
        }
        if (TextUtils.isEmpty(resultFromService.getNickName())
                || resultFromService.getNickName() == null) {
            p.nickName = isStringNull;
        } else {
            p.userBirth = resultFromService.getNickName();
        }

        if (TextUtils.isEmpty(resultFromService.getUserAddress())
                || resultFromService.getUserAddress() == null) {
            p.userAddress = isStringNull;
        } else {
            p.userAddress = resultFromService.getUserAddress();

        }

        if (TextUtils.isEmpty(resultFromService.getImage())
                || resultFromService.getImage() == null) {
            p.userImage = isStringNull;
        } else {
            p.userImage = resultFromService.getImage();

        }

        if (TextUtils.isEmpty(resultFromService.getUserCreateTime())
                || resultFromService.getUserCreateTime() == null) {
            p.createTime = isStringNull;
        } else {
            p.createTime = resultFromService.getUserCreateTime();

        }

        if (TextUtils.isEmpty(resultFromService.getUserAge())
                || resultFromService.getUserAge() == null) {
            p.age = isStringNull;
        } else {
            p.age = resultFromService.getUserAge();

        }
        if (TextUtils.isEmpty(resultFromService.getKeyuserKey())
                || resultFromService.getKeyuserKey() == null) {
            p.userKey = isStringNull;
        } else {
            p.userKey = resultFromService.getKeyuserKey();
        }
        if (TextUtils.isEmpty(resultFromService.getJssessionid())
                || resultFromService.getJssessionid() == null) {
            p.jssessionid = isStringNull;
        } else {
            p.jssessionid = resultFromService.getJssessionid();
        }
        if (TextUtils.isEmpty(resultFromService.getSyncTime())
                || resultFromService.getSyncTime() == null) {
            p.syncTime = "";
        } else {
            p.syncTime = resultFromService.getSyncTime();
        }
        p.contactSwitcherSelected = 0;
        p.password = mPasswordEdit.getText().toString();
        getContentResolver().delete(Provider.PersonColumns.CONTENT_URI, null,
                null);
        insert(p);
        Intent intent = new Intent();
        Bundle bl = new Bundle();
        bl.putString("userImage", p.userImage);
        bl.putString("userName", p.name);
        bl.putString("userId", p.userId);
        intent.putExtras(bl);
        intent.setClass(AuthenticatorActivity.this, UserInfoActivity.class);
        startActivity(intent);
        finish();

    }

    private void insert(Person person) {
        ContentValues values = new ContentValues();
        values.put(Provider.PersonColumns.USER_KEY, person.userKey);
        values.put(Provider.PersonColumns.AGE, person.age);
        values.put(Provider.PersonColumns.USER_ID, person.userId);
        values.put(Provider.PersonColumns.JSSESSIONID, person.jssessionid);
        values.put(Provider.PersonColumns.NAME, person.name);
        values.put(Provider.PersonColumns.TRUE_NAME, person.trueName);
        values.put(Provider.PersonColumns.USER_SEX, person.userSex);
        values.put(Provider.PersonColumns.USER_BIRTH, person.userBirth);
        values.put(Provider.PersonColumns.USER_ADDRESS, person.userAddress);
        values.put(Provider.PersonColumns.USER_EMAIL, person.userEmail);
        values.put(Provider.PersonColumns.CREATETIME, person.createTime);
        values.put(Provider.PersonColumns.PHONE, person.phone);
        values.put(Provider.PersonColumns.USER_IMAGE, person.userImage);
        values.put(Provider.PersonColumns.NICK_NAME, person.nickName);
        values.put(Provider.PersonColumns.PASSWORD, person.password);
        values.put(Provider.PersonColumns.SYNC_TIME, person.syncTime);
        values.put(Provider.PersonColumns.CONTACT_SWITCHER_SELECTED, person.contactSwitcherSelected);
        Uri uri = getContentResolver().insert(
                Provider.PersonColumns.CONTENT_URI, values);
        Log.i(TAG, "insert uri=" + uri);
        String lastPath = uri.getLastPathSegment();
        if (TextUtils.isEmpty(lastPath)) {
            Log.i(TAG, "insert failure!");
        } else {
            Log.i(TAG, "insert success! the id is " + lastPath);
        }
    }

    private void finishLogin(Result authToken) {
        final Account account = new Account(mUsername, FxConstants.ACCOUNT_TYPE);
        mAccountManager.addAccountExplicitly(account, mPassword, null);
        // mAccountManager.setPassword(account, mPassword);
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE,
                FxConstants.ACCOUNT_TYPE);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = this.getActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_register:
            Intent intent = new Intent();
            intent.setClass(AuthenticatorActivity.this, RegWebView.class);
            startActivity(intent);
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
