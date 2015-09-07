package com.phicomm.account.main;

import com.phicomm.account.R;
import com.phicomm.account.provider.Person;
import com.phicomm.account.provider.Provider;
import com.phicomm.account.register.RegWebView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LoginActivity extends Activity {
    private String TAG = "LoginActivity";
    private TextView mExistAccount;
    private TextView mNoAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNoAccount = (TextView) findViewById(R.id.no_account);
        mExistAccount = (TextView) findViewById(R.id.exist_account);
        mExistAccount.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(LoginActivity.this,
                        AuthenticatorActivity.class));
            }
        });
        mNoAccount.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegWebView.class);
                startActivity(intent);

            }
        });
    }

}