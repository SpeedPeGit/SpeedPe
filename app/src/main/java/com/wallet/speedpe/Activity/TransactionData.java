package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.RequestQueue;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;


public class TransactionData extends AppCompatActivity implements View.OnClickListener {
    private CoordinatorLayout rel_progressdialog;
//    private TextView trans_txt_nameLogo, trans_txt_name, trans_txt_phone, trans_txt_amount, trans_txt_transactionID, trans_txt_transactionTo, trans_txt_transactionFrom, trans_txt_transactionToUPICode, trans_txt_transactionFromUPICode, trans_txt_GoogletransactionID;
//    private ImageButton trans_btn_back;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.show_transaction_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    private void init() {
        rel_progressdialog = findViewById(R.id.rel_progressdialog);
        rel_progressdialog.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        trans_txt_nameLogo = findViewById(R.id.trans_txt_nameLogo);
//        trans_txt_name = findViewById(R.id.trans_txt_name);
//        trans_txt_phone = findViewById(R.id.trans_txt_phone);
//        trans_txt_amount = findViewById(R.id.trans_txt_amount);
//        trans_txt_transactionID = findViewById(R.id.trans_txt_transactionID);
//        trans_txt_transactionTo = findViewById(R.id.trans_txt_transactionTo);
//        trans_txt_transactionFrom = findViewById(R.id.trans_txt_transactionFrom);
//        trans_txt_transactionToUPICode = findViewById(R.id.trans_txt_transactionToUPICode);
//        trans_txt_transactionFromUPICode = findViewById(R.id.trans_txt_transactionFromUPICode);
//        trans_txt_GoogletransactionID = findViewById(R.id.trans_txt_GoogletransactionID);
//        trans_btn_back = findViewById(R.id.trans_btn_back);
//        trans_btn_back.setOnClickListener(this);
        show_transactionData();
    }

    private void show_transactionData() {
        String str_name = Config.getStringPreference(TransactionData.this, Constant.USER_NAME);
        String[] str_nm_arr = str_name.split("_");
        String str_icon = "";
        for (int i=0; i<str_nm_arr.length; i++) {
            if (i==0) {
                str_icon = str_nm_arr[i].substring(0, 1);
            }
            else {
                str_icon = str_icon + " " + str_nm_arr[i].substring(0, 1);
            }
        }
//        trans_txt_name.setText("To " + str_name);
//        trans_txt_nameLogo.setText(str_icon);
//        trans_txt_phone.setText("+91 " + Config.getStringPreference(TransactionData.this, Constant.PHONE));
//        trans_txt_amount.setText("₹ " + Config.getStringPreference(TransactionData.this, Constant.PLAN_AMOUNT));
//        trans_txt_transactionID.setText(Config.getStringPreference(TransactionData.this, Constant.PLAN_TRANSACTION_ID));
//        trans_txt_transactionTo.setText("To " + str_name);
//        trans_txt_transactionFrom.setText("From SpeedPe");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(TransactionData.this, Activity_Home.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.trans_btn_back:
//                onBackPressed();
//                break;
            default:
                break;
        }
    }
}