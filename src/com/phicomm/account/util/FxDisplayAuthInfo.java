package com.phicomm.account.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.phicomm.account.R;

/*
 * display user info in login menu.
 */

public class FxDisplayAuthInfo {
    private static final String TAG = "FxDisplayAuthInfo";
    private static final boolean DEBUG = true;

    public static void displayUserInfo(String userName, String password,
            TextView accountInfo, Result resultFromService, View userView,
            View passwordView) {
        if (DEBUG)
            Log.i(TAG, "Fx userName :" + userName + "Fx password" + password
                    + "Fx isUserName(userName):" + isUserName(userName)
                    + "Fx getPassword() :" + resultFromService.getPassword());
        boolean mUserName = userName.equals(resultFromService.getUserName())
                || userName.equals(resultFromService.getUserEmail())
                || userName.equals(resultFromService.getUserId());
        if ((isUserName(userName) || isEmail(userName) || isUserId(userName))
                && isPassword(password)) {
            if (mUserName && !password.equals(resultFromService.getPassword())) {
                accountInfo.setText(R.string.passwordIsWrong);
                passwordView
                        .setBackgroundResource(R.drawable.icon_lock_disable);
            } else if (!mUserName
                    && password.equals(resultFromService.getPassword())) {
                accountInfo.setText(R.string.userNameIsWrong);
                userView.setBackgroundResource(R.drawable.icon_contact_disable);
            } else if (!mUserName
                    && !password.equals(resultFromService.getPassword())) {
                accountInfo.setText(R.string.userNamePsdIsWrong);
                userView.setBackgroundResource(R.drawable.icon_contact_disable);
                passwordView
                        .setBackgroundResource(R.drawable.icon_lock_disable);
            }
        } else {
            if (!(isUserName(userName) || isEmail(userName) || isUserId(userName))
                    && isPassword(password)) {
                accountInfo.setText(R.string.userNameFormatIsWrong);
                userView.setBackgroundResource(R.drawable.icon_contact_disable);
            } else if ((isUserName(userName) || isEmail(userName) || isUserId(userName))
                    && !isPassword(password)) {
                accountInfo.setText(R.string.passwordFormatWrong);
                passwordView
                        .setBackgroundResource(R.drawable.icon_lock_disable);
            } else {
                accountInfo.setText(R.string.allFormatIsWrong);
                userView.setBackgroundResource(R.drawable.icon_contact_disable);
                passwordView
                        .setBackgroundResource(R.drawable.icon_lock_disable);
            }
        }
    }

    public static boolean isUserName(String userName) {
        Pattern p = Pattern.compile("^\\w{1,40}$");
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isUserId(String userId) {
        Pattern p = Pattern.compile("^\\d{16}$");
        Matcher m = p.matcher(userId);
        return m.matches();
    }

    public static boolean isPassword(String password) {
        Pattern p = Pattern.compile("^\\w{1,18}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

}
