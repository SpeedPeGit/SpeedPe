package com.wallet.speedpe.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wallet.speedpe.Model.ReferralQRScanWithdrawReq;
import com.wallet.speedpe.Model.ReferralQRScanWithdrawRes;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;
import com.wallet.speedpe.rest.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class Activity_Referral extends BaseActivity implements View.OnClickListener {
    private static final String TAG = Activity_Referral.class.getSimpleName();
    private FloatingActionButton fab_ref_back;
    private TextView txt_ref_code, ref_avail_bonus_amt, ref_setelled_bonus_amt, ref_total_bonus_amt;
    private AppCompatButton btn_share;
    private CoordinatorLayout rel_progressdialog;
    private RequestQueue queue;
    private ImageButton btn_ref_scan, btn_ref_without_scan;
    private ArrayList<String> show_transaction_details_array;
    private String local_wallet_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_referral);
        init();

        rel_progressdialog.setVisibility(View.VISIBLE);
        getReferralData();
//        ReferralRequest referralRequest = new ReferralRequest(
//                Config.getStringPreference(Activity_Referral.this, Constant.USER_ID)
//        );
//
//        Call<ReferralResponse> call1 = RetrofitClient.getInstance().getMyApi().myrefferalstatus(referralRequest);
//        call1.enqueue(new Callback<ReferralResponse>() {
//            @Override
//            public void onResponse(Call<ReferralResponse> call, retrofit2.Response<ReferralResponse> response) {
//                rel_progressdialog.setVisibility(View.GONE);
//                ReferralResponse rechargeRes = response.body();
//                if (1==1){
//                    int i = 3;
//                }else{
//                    int i = 3;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReferralResponse> call, Throwable t) {
//                rel_progressdialog.setVisibility(View.GONE);
//                call.cancel();
//            }
//        });

    }

    private void getReferralData() {
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        queue = Volley.newRequestQueue(this);

        String url = Constant.baseURL + "referral-withdrawal/myrefferalstatus/" + Config.getStringPreference(this , Constant.USER_ID);
        StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rel_progressdialog.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                //this method will be running on UI thread
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Integer availableBonus = jsonResponse.getInt("availableBonus");
                    Integer settledBonus = jsonResponse.getInt("settledBonus");
                    Integer totalBonus = jsonResponse.getInt("totalBonus");
                    String refferalCode = String.valueOf(jsonResponse.get("refferalCode"));
                    Config.setStringPreference(Activity_Referral.this,Constant.REFERRAL_CODE, refferalCode);

                    txt_ref_code.setText(refferalCode);
                    ref_avail_bonus_amt.setText(String.valueOf(availableBonus));
                    ref_setelled_bonus_amt.setText(String.valueOf(settledBonus));
                    ref_total_bonus_amt.setText(String.valueOf(totalBonus));

                    if (refferalCode.length() > 0){
                        btn_share.setVisibility(View.VISIBLE);
                    }
                    else {
                        btn_share.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                rel_progressdialog.setVisibility(View.GONE);
                queue.stop();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        // Instantiate the RequestQueue with the cache and network, start the request
        // and add it to the queue
        queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(request);
        rel_progressdialog.setVisibility(View.GONE);
    }

    private void init() {
        fab_ref_back = findViewById(R.id.fab_ref_back);
        fab_ref_back.setOnClickListener(this);
        txt_ref_code = findViewById(R.id.txt_ref_code);
        ref_avail_bonus_amt = findViewById(R.id.ref_avail_bonus_amt);
        ref_setelled_bonus_amt = findViewById(R.id.ref_setelled_bonus_amt);
        ref_total_bonus_amt = findViewById(R.id.ref_total_bonus_amt);
        btn_share = findViewById(R.id.btn_share);
        btn_share.setVisibility(View.GONE);
        btn_share.setOnClickListener(this);
        rel_progressdialog = findViewById(R.id.rel_progressdialog);
        btn_ref_scan = findViewById(R.id.btn_ref_scan);
        btn_ref_scan.setOnClickListener(this);
        btn_ref_without_scan = findViewById(R.id.btn_ref_without_scan);
        btn_ref_without_scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fab_ref_back:
                Intent i = new Intent(Activity_Referral.this, Activity_Home.class);
                startActivity(i);
                break;
            case R.id.btn_share:
                String shareBody = "My referral code for SpeedPe is: "+ Config.getStringPreference(Activity_Referral.this, Constant.REFERRAL_CODE) + "\n https://play.google.com/store/apps/details?id=com.wallet.speedpe";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "send"));
                break;
            case R.id.btn_ref_scan:
                Config.setStringPreference(Activity_Referral.this, Constant.SHOULD_SCAN,"YES");
                IntentIntegrator intentIntegrator = new IntentIntegrator(Activity_Referral.this);
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.MIXED_SCAN);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setTorchEnabled(true);
                intentIntegrator.initiateScan();
                break;
            case R.id.btn_ref_without_scan:
                Config.setStringPreference(Activity_Referral.this, Constant.SHOULD_SCAN,"NO");
                // display in popup
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Referral.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(Activity_Referral.this).inflate(R.layout.confirm_scan_data, viewGroup, false);
                EditText upicode = dialogView.findViewById(R.id.upi_code);
                upicode.setText("");
                EditText upiname = dialogView.findViewById(R.id.upi_name);
                upiname.setText("");
                TextView mp_name = dialogView.findViewById(R.id.mp_name);
                mp_name.setText(Config.getStringPreference(Activity_Referral.this, Constant.USER_NAME));
                EditText upiamt = dialogView.findViewById(R.id.upi_amt);
                upiamt.setText("");
                AppCompatButton btnSendPayment = dialogView.findViewById(R.id.upi_send_payment);
                builder.setView(dialogView);
                final android.app.AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(true);
                alertDialog.setOnCancelListener(
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                Activity_Referral.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                            }
                        }
                );

                alertDialog.show();
                btnSendPayment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (upicode.getText().toString().isEmpty()) {
                            showAlert("UPI code can not be blank", "Please enter your UPI code", upicode);
                        } else if (upiname.getText().toString().isEmpty()) {
                            showAlert("Name field can not be blank", "Please enter your Name", upiname);
                        } else if (upiamt.getText().toString().isEmpty()) {
                            showAlert("Amount field can not be blank", "Please enter Amount", upiamt);
                        } else {
                            // Check if requested amount > todays limit
                            //Dismiss Keyboard
                            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            if (Integer.parseInt(upiamt.getText().toString()) > Double.parseDouble(ref_avail_bonus_amt.getText().toString())) {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Referral.this);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(Activity_Referral.this).inflate(R.layout.ad_general, viewGroup, false);
                                ImageView image = dialogView.findViewById(R.id.ad_image);
                                image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert", null, getPackageName())));
                                LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                                lay_yesno.setVisibility(View.GONE);
                                LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                                lay_ok.setVisibility(View.VISIBLE);
                                TextView heading = dialogView.findViewById(R.id.ad_heading);
                                heading.setText("Your request denied");
                                TextView message = dialogView.findViewById(R.id.ad_message);
                                message.setText("Requested amount greater than Todays limit");
                                Button btnok = dialogView.findViewById(R.id.ad_btnOk);
                                btnok.setText(R.string.ok);
                                builder.setView(dialogView);
                                final android.app.AlertDialog alertDialog = builder.create();
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alertDialog.dismiss();
                                    }
                                });
                            } else {
                                rel_progressdialog.setVisibility(View.VISIBLE);
                                call_qrcode_payment(upiamt.getText().toString(), upiname.getText().toString(), upicode.getText().toString());
                                alertDialog.dismiss();
                            }
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                String user_name = "";
                String upi_code = "";
                String[] separated = intentResult.getContents().toString().split("&");
                Boolean discard = false;
                if (separated.length > 1) {
                    String strUpiCode = "";
                    for (int i = 0; i < separated.length; i++) {
                        if (separated[i].contains("pa=")) {
                            strUpiCode = separated[i];
                            break;
                        }
                    }
                    String[] upi_code_array = strUpiCode.split("=");
                    upi_code = upi_code_array[1].replace("%40","@");
                    String strUpiName = "";
                    for (int i = 0; i < separated.length; i++) {
                        if (separated[i].contains("pn=")) {
                            strUpiName = separated[i];
                            break;
                        }
                    }
                    String[] upi_name_array = strUpiName.split("=");
                    user_name = upi_name_array[1];
                    user_name = user_name.replace("%20", " ");
                } else {
                    try {
                        separated = intentResult.getContents().toString().split(" ");
                        String st = "";
                        for (int i = 0; i < separated.length; i++) {
                            //Get first name
                            st = separated[i];
                            if (i == 0) {
                                for (int j = st.length() - 1; j >= 0; j--) {
                                    if (Character.isDigit(st.charAt(j))) {
                                        user_name = st.substring(st.length() - (st.length() - j - 1));
                                        break;
                                    }
                                }
                            } else if (i == separated.length - 1) { //last name
                                for (int j = 0; j < st.length(); j++) {
                                    if (Character.isDigit(st.charAt(j))) {
                                        user_name = user_name + " " + st.substring(0, j);
                                        break;
                                    }
                                }
                            } else { // All middle names
                                user_name = user_name + " " + st;
                            }
                        }

                        //get UPICode
                        String[] scode = separated[0].split("@");
                        String str_code = "";
                        for (int j = 0; j < scode[1].length(); j++) {
                            if (Character.isDigit(scode[1].charAt(j))) {
                                str_code = "@" + scode[1].substring(0, j);
                                break;
                            }
                        }

                        if (str_code.contains(".")) {
                            String[] temp = str_code.split(".");
                            str_code = temp[0];
                        }

                        String tempStr = scode[0].substring(111);
                        for (int j = 0; j < tempStr.length(); j++) {
                            if (!Character.isDigit(tempStr.charAt(j))) {
                                str_code = tempStr.substring(tempStr.length() - (tempStr.length() - j)) + str_code;
                                break;
                            }
                        }
                        upi_code = str_code.replace("%40","@");
                    } catch (Exception e) {
                        discard = true;
                        e.printStackTrace();
                    }
                }
                if (discard == false) {
                    // display in popup
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Referral.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(Activity_Referral.this).inflate(R.layout.confirm_scan_data, viewGroup, false);
                    EditText upicode = dialogView.findViewById(R.id.upi_code);
                    upicode.setText(upi_code);
                    EditText upiname = dialogView.findViewById(R.id.upi_name);
                    upiname.setText(user_name);
                    TextView mp_name = dialogView.findViewById(R.id.mp_name);
                    mp_name.setText(Config.getStringPreference(Activity_Referral.this, Constant.USER_NAME));
                    EditText upiamt = dialogView.findViewById(R.id.upi_amt);
                    upiamt.setText("");
                    AppCompatButton btnSendPayment = dialogView.findViewById(R.id.upi_send_payment);
                    builder.setView(dialogView);
                    final android.app.AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(true);
                    alertDialog.show();
                    btnSendPayment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (upicode.getText().toString().isEmpty()) {
                                showAlert("UPI code can not be blank", "Please enter your UPI code", upicode);
                            } else if (upiname.getText().toString().isEmpty()) {
                                showAlert("Name field can not be blank", "Please enter your Name", upiname);
                            } else if (upiamt.getText().toString().isEmpty()) {
                                showAlert("Amount field can not be blank", "Please enter Amount", upiamt);
                            } else {
                                // Check if requested amount > todays limit
                                //Dismiss Keyboard
                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                if (Integer.parseInt(upiamt.getText().toString()) > Double.parseDouble(ref_avail_bonus_amt.getText().toString())) {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Referral.this);
                                    ViewGroup viewGroup = findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(Activity_Referral.this).inflate(R.layout.ad_general, viewGroup, false);
                                    ImageView image = dialogView.findViewById(R.id.ad_image);
                                    image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert", null, getPackageName())));
                                    LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                                    lay_yesno.setVisibility(View.GONE);
                                    LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                                    lay_ok.setVisibility(View.VISIBLE);
                                    TextView heading = dialogView.findViewById(R.id.ad_heading);
                                    heading.setText("Your request denied");
                                    TextView message = dialogView.findViewById(R.id.ad_message);
                                    message.setText("Requested amount greater than Available Bonus");
                                    Button btnok = dialogView.findViewById(R.id.ad_btnOk);
                                    btnok.setText(R.string.ok);
                                    builder.setView(dialogView);
                                    final android.app.AlertDialog alertDialog = builder.create();
                                    alertDialog.setCancelable(false);
                                    alertDialog.show();
                                    btnok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            alertDialog.dismiss();
                                        }
                                    });
                                } else {
                                    rel_progressdialog.setVisibility(View.VISIBLE);
                                    call_qrcode_payment(upiamt.getText().toString(), upiname.getText().toString(), upicode.getText().toString());
                                    alertDialog.dismiss();
                                }
                            }
                        }
                    });
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showAlert(String Heading, String Message, final EditText field) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Referral.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Referral.this).inflate(R.layout.ad_general, viewGroup, false);
        LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
        ley_color.setBackgroundColor(getResources().getColor(R.color.dark_red));
        LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
        lay_yesno.setVisibility(View.GONE);
        LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
        lay_ok.setVisibility(View.VISIBLE);
        TextView heading = dialogView.findViewById(R.id.ad_heading);
        heading.setText(Heading);
        TextView message = dialogView.findViewById(R.id.ad_message);
        message.setText(Message);
        Button btnok = dialogView.findViewById(R.id.ad_btnOk);
        builder.setView(dialogView);
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                field.setFocusable(true);
            }
        });
    }

    private void call_qrcode_payment(String amt, String name, String code) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        formattedDate = formattedDate.replace(" ","T");

        ReferralQRScanWithdrawReq referralQRScanWithdrawReq = new ReferralQRScanWithdrawReq(
                Config.getStringPreference(this, Constant.USER_ID),
                formattedDate,
                Integer.parseInt(amt.replace("₹", "")),
                code,
                "",
                true,
                name,
                "shopping",
                "",
                ""
        );

        show_transaction_details_array = new ArrayList<>();
        Call<ReferralQRScanWithdrawRes> call1 = RetrofitClient.getInstance().getMyApi().withdrawreq(referralQRScanWithdrawReq);
        call1.enqueue(new Callback<ReferralQRScanWithdrawRes>() {
            @Override
            public void onResponse(Call<ReferralQRScanWithdrawRes> call, retrofit2.Response<ReferralQRScanWithdrawRes> response) {
                try {
                    rel_progressdialog.setVisibility(View.GONE);
                    ReferralQRScanWithdrawRes qrScanWithdrawRes = response.body();
                    String transid = "";
                    if (qrScanWithdrawRes.getWithdrawalTransactionId() != null) {
                        transid = qrScanWithdrawRes.getWithdrawalTransactionId().toString();
                    }
                    show_transaction_details_array.add(0,name); // trans_txt_name
                    show_transaction_details_array.add(1,transid); //trans_txt_transactionID
                    show_transaction_details_array.add(2,name); // trans_txt_transactionTo
                    show_transaction_details_array.add(3,code); // trans_txt_transactionToUPICode
                    show_transaction_details_array.add(4,Config.getStringPreference(Activity_Referral.this, Constant.USER_NAME)); // trans_txt_transactionFrom
                    show_transaction_details_array.add(5,Config.getStringPreference(Activity_Referral.this, Constant.EMAIL)); // trans_txt_transactionFromUPICode
                    show_transaction_details_array.add(6,Config.getStringPreference(Activity_Referral.this, Constant.REFERRAL_CODE)); // trans_txt_GoogletransactionID
                    String status = qrScanWithdrawRes.getShortMessage().toString();
                    if (status.equalsIgnoreCase("failed")){
                        rel_progressdialog.setVisibility(View.GONE);
                        showAlert("Transaction Failed", "Please try Amount", null);
                    }
                    else if (status.equalsIgnoreCase("success")){
                        rel_progressdialog.setVisibility(View.GONE);
                        show_transactionData();
                    }
                } catch (Exception e) {
                    rel_progressdialog.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ReferralQRScanWithdrawRes> call, Throwable t) {
                rel_progressdialog.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }

    private void show_transactionData() {
        try {
            android.app.AlertDialog.Builder qr_payment = new android.app.AlertDialog.Builder(Activity_Referral.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_Referral.this).inflate(R.layout.show_transaction_details, viewGroup, false);
            TextView trans_txt_name = dialogView.findViewById(R.id.trans_txt_name);
            trans_txt_name.setText(show_transaction_details_array.get(0));
            TextView trans_txt_transactionID = dialogView.findViewById(R.id.trans_txt_transactionID);
            trans_txt_transactionID.setText(show_transaction_details_array.get(1));
            TextView trans_txt_transactionTo = dialogView.findViewById(R.id.trans_txt_transactionTo);
            trans_txt_transactionTo.setText(show_transaction_details_array.get(2));
            TextView trans_txt_transactionToUPICode = dialogView.findViewById(R.id.trans_txt_transactionToUPICode);
            trans_txt_transactionToUPICode.setText(show_transaction_details_array.get(3));
            TextView trans_txt_transactionFrom = dialogView.findViewById(R.id.trans_txt_transactionFrom);
            trans_txt_transactionFrom.setText(show_transaction_details_array.get(4));
            TextView trans_txt_transactionFromUPICode = dialogView.findViewById(R.id.trans_txt_transactionFromUPICode);
            trans_txt_transactionFromUPICode.setText(show_transaction_details_array.get(5));
            TextView trans_txt_GoogletransactionID = dialogView.findViewById(R.id.trans_txt_GoogletransactionID);

            LinearLayout lay_ref_code = dialogView.findViewById(R.id.lay_ref_code);
            trans_txt_GoogletransactionID.setText(show_transaction_details_array.get(6));
            if (show_transaction_details_array.get(6).length() > 0) {
                lay_ref_code.setVisibility(View.VISIBLE);
            }
            else {
                lay_ref_code.setVisibility(View.GONE);
            }
            AppCompatButton btn_ok = dialogView.findViewById(R.id.trans_btnOK);
            qr_payment.setView(dialogView);
            final android.app.AlertDialog alertDialog = qr_payment.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Dismiss Keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    alertDialog.dismiss();

                    //refresh Referral
                    getReferralData();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}