package com.wallet.speedpe.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant extends Activity {
//    public static String baseURL = "http://203.193.140.5:8086/api/app/";
    public static String baseURL = "https://emaarerp.tk/api/app/";
    public static String razorpayKeyID = "rzp_live_9gz5iT8LYZxjEY";
    public static String razorpaySecretKey = "iAewpsj6eVIYKR9Frf8Ni8Ww";
    public static String downloadURL = "http://rastest1.ml/";
    public Activity activity;
    public Constant(Activity _activity){
        this.activity = _activity;
    }

    public static final int READ_TIMEOUT=15000;
    public static final int CONNECTION_TIMEOUT=10000;

    public static final String CALL_SUPPORT_NUMBER="18003098897";

    //Customer Profile values
    public static final String PLAN_ID = "PLAN_ID";
    public static final String REFERRAL_CODE = "REFERRAL_CODE";
    public static final String PLAN_TRANSACTION_ID = "PLAN_TRANSACTION_ID";

    public static final String SHOULD_SCAN = "SHOULD_SCAN";
    public static final String PROFILE_EMAIL = "PROFILE_EMAIL";
    public static final String PROFILE_MOBILE = "PROFILE_MOBILE";
    public static final String PROFILE_LANDLINE = "PROFILE_LANDLINE";
    public static final String PROFILE_ADDRESS = "PROFILE_ADDRESS";
    public static final String PROFILE_CITY = "PROFILE_CITY";

    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    public static final String PASSWORD = "PASSWORD";
    public static final String DIRECT_LOGIN = "DIRECT_LOGIN";
    public static final String LOGIN_ID = "LOGIN_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String MEMBER_ID = "MEMBER_ID";
    public static final String USER_ID = "USER_ID";
    public static final String CITY = "CITY";
    public static final String USER_ROLES = "USER_ROLES";
    public static final String UPI = "UPI";
    public static final String UPID_1 = "UPID_1";
    public static final String UPID_2 = "UPID_2";
    public static final String UPID_3 = "UPID_3";
    public static final String UPID_4 = "UPID_4";
    public static final String UPID_5 = "UPID_5";
    public static final String IS_LOGED_IN = "IS_LOGED_IN";
    public static final String FROM_SCREEN = "FROM_SCREEN";
    public static final String UPI_SELECTED = "UPI_SELECTED";
    public static final String UPI_CODE = "UPI_CODE";
    public static final String LAST_RECEIPT_ID = "LAST_RECEIPT_ID";
    public static final String PROFILE_LATITUDE = "PROFILE_LATITUDE";
    public static final String PROFILE_LONGITUDE = "PROFILE_LONGITUDE";
    public static final String GO_BACK_MAIN_SCXREEN = "GO_BACK_MAIN_SCXREEN";
    public static final String CUSTOMER_REQUEST_STATUS = "CUSTOMER_REQUEST_STATUS";
//    public static final String YEAR_OF_EXPERIENCE = "YEAR_OF_EXPERIENCE";
//    public static final String IS_FIRST_LAUNCH = "IS_FIRST_LAUNCH";
//    public static final String CUSTOMER_REQUEST_STATUS = "CUSTOMER_REQUEST_STATUS";
//    public static final String SELECTED_RFI = "SELECTED_RFI";

    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    public static String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }
        return capMatcher.appendTail(capBuffer).toString();
    }

//    public static String getMonthNames(int mon) {
//        String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//        return months[mon];
//    }

//    public static String getTime(int hr, int min) {
//        String tm;
//        if (hr > 12) {
//            hr -= 12;
//            tm = hr + ":" + min + " " + "PM";
//        }
//        else {
//            tm = hr + ":" + min + " " + "AM";
//        }
//        return tm;
//    }
}
