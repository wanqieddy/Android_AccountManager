package com.phicomm.account.main;

import com.phicomm.account.util.FxConstants;

import com.phicomm.account.R;
import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class Authenticator extends AbstractAccountAuthenticator {
    private String TAG = "Authenticator";
    private Context mContext;
    private MyHandler handler;

    public Authenticator(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        handler = new MyHandler();
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
            String accountType, String authTokenType,
            String[] requiredFeatures, Bundle options)
            throws NetworkErrorException {
        // TODO Auto-generated method stub
        Log.d("tag", accountType + " - " + authTokenType);

        Account[] accounts = AccountManager.get(mContext).getAccountsByType(
                FxConstants.ACCOUNT_TYPE);

        if (accounts.length > 0) {
            for (Account account : accounts) {
                String name = account.name;
                Log.e(TAG, "name = " + name);
            }
            handler.sendEmptyMessage(10001);
            return null;
        }
        Bundle ret = new Bundle();
        Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
                response);
        ret.putParcelable(AccountManager.KEY_INTENT, intent);
        return ret;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response,
            Account account, Bundle options) throws NetworkErrorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response,
            String accountType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
            Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        Log.e(TAG, "------------------------" + authTokenType);
        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response,
            Account account, String[] features) throws NetworkErrorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response,
            Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        // TODO Auto-generated method stub
        return null;
    }

    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            int what = msg.what;
            if (what == 10001) {
                Toast.makeText(mContext, R.string.warnning, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

}