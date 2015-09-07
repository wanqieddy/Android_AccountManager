package com.phicomm.account.main;

import com.phicomm.account.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CloudActivity extends Activity {

    private TextView mCloudService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_activity);
        getActionBar().hide();
        mCloudService = (TextView) findViewById(R.id.cloud_service);
        mCloudService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(CloudActivity.this,
                        LoginActivity.class));
            }
        });
    }
}