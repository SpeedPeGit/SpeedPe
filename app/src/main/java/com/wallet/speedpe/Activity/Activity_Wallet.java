package com.wallet.speedpe.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wallet.speedpe.Adapter.WalletDetail_Adapter;
import com.wallet.speedpe.Adapter.Wallet_Adapter;
import com.wallet.speedpe.AvailableAmount;
import com.wallet.speedpe.Model.QRScanWithdrawReq;
import com.wallet.speedpe.Model.QRScanWithdrawRes;
import com.wallet.speedpe.Model.WalletDetail_Items;
import com.wallet.speedpe.Model.Wallet_Items;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;
import com.wallet.speedpe.rest.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

public class Activity_Wallet extends BaseActivity implements AvailableAmount/*, NavigationView.OnNavigationItemSelectedListener*/ {
    //vars
    private static final String TAG = Activity_Wallet.class.getSimpleName();
    private CoordinatorLayout rel_progressdialog;
    private RequestQueue queue;
    private ImageButton icon_logout;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private Integer local_todays_limit = 0;
    private String local_wallet_id = "";
    private TextView wa_name;
    private ArrayList<String> show_transaction_details_array;

    ArrayList<Wallet_Items> string_wallet_items;
    ArrayList<WalletDetail_Items> string_walletDetail_items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activtiy_wallet);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();
        init_wallet();
        icon_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_logout();
            }
        });
    }

    private void confirm_logout() {
        androidx.appcompat.app.AlertDialog alertbox = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setMessage("Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        Config.setStringPreference(Activity_Wallet.this, Constant.IS_LOGED_IN, "false");
                        Intent i = new Intent(Activity_Wallet.this, Activity_login.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    private void init_wallet() {
        icon_logout = findViewById(R.id.wa_logout);
        wa_name = findViewById(R.id.wa_name);
        wa_name.setText(Config.getStringPreference(Activity_Wallet.this, Constant.USER_NAME));
        rel_progressdialog = findViewById(R.id.rel_progressdialog);
        rel_progressdialog.setVisibility(View.GONE);
        permissionTeleCheck();
        getWalletPlans();

    }

    private void getWalletPlans() {
        string_wallet_items = new ArrayList<Wallet_Items>();
        boolean net_avail = Constant.isNetworkConnectionAvailable(Activity_Wallet.this);
        if (net_avail == true) {
            rel_progressdialog.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            // Setup 1 MB disk-based cache for Volley
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

            // Use HttpURLConnection as the HTTP client
            Network network = new BasicNetwork(new HurlStack());
            queue = Volley.newRequestQueue(this);
            String url = Constant.baseURL + "recharge/getactiverechargebymember/" + Config.getStringPreference(Activity_Wallet.this, Constant.USER_ID);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    //this method will be running on UI thread
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        for(int i=0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            string_wallet_items.add(new Wallet_Items(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("planName"),
                                    "₹" + String.valueOf(jsonObject.getInt("todaysLimit")),
                                    "₹" + String.valueOf(jsonObject.getInt("totalLimit")),
                                    "₹" + String.valueOf(jsonObject.getInt("pendingLimit"))));
                        }
                        CreateUI();
                        rel_progressdialog.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
            });
            // Instantiate the RequestQueue with the cache and network, start the request
            // and add it to the queue
            queue = new RequestQueue(cache, network);
            queue.start();
            queue.add(request);
        } else {
            //Show no Network dialog
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Wallet.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_Wallet.this).inflate(R.layout.ad_no_network, viewGroup, false);
            AppCompatButton buttonOk = dialogView.findViewById(R.id.ad_noNetwork_btnOk);
            builder.setView(dialogView);
            final android.app.AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    alertDialog.dismiss();
                }
            });
        }
    }

    private void CreateUI() {
        /*string_wallet_items.add(new Wallet_Items(
                "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "Gold",
                "₹" + "300",
                "₹" + "200",
                "₹" + "100"));
*/
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_wallet_list);
        Wallet_Adapter adapter = new Wallet_Adapter(this, string_wallet_items, (AvailableAmount) this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_Wallet.this));

        CreateDataList();
    }

    private void CreateDataList() {
        string_walletDetail_items = new ArrayList<WalletDetail_Items>();
        boolean net_avail = Constant.isNetworkConnectionAvailable(Activity_Wallet.this);
        if (net_avail == true) {
            rel_progressdialog.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            // Setup 1 MB disk-based cache for Volley
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

            // Use HttpURLConnection as the HTTP client
            Network network = new BasicNetwork(new HurlStack());
            queue = Volley.newRequestQueue(this);
            String url = Constant.baseURL + "recharge/getwalletstatus/" + Config.getStringPreference(Activity_Wallet.this, Constant.USER_ID);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    //this method will be running on UI thread
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String todaysTotalused = String.valueOf(jsonResponse.getInt("todaysTotalused"));
                        String todaysTotalPending = String.valueOf(jsonResponse.getInt("todaysTotalPending"));
                        String todaysTotalLimit = String.valueOf(jsonResponse.getInt("todaysTotalLimit"));
                        string_walletDetail_items.add(new WalletDetail_Items(
                                "Todays Used Limit",
                                "",
                                "₹" + todaysTotalused,
                                ""));
                        string_walletDetail_items.add(new WalletDetail_Items(
                                "Todays Pending Limit",
                                "",
                                "₹" + todaysTotalPending,
                                ""));
                        string_walletDetail_items.add(new WalletDetail_Items(
                                "Total Limit",
                                "",
                                "₹" + todaysTotalLimit,
                                ""));
                        RecyclerView recyclerViewDataList = (RecyclerView) findViewById(R.id.recycler_wallet_datalist);
                        WalletDetail_Adapter walletDetail_adapter = new WalletDetail_Adapter(Activity_Wallet.this, string_walletDetail_items);
                        recyclerViewDataList.setAdapter(walletDetail_adapter);
                        recyclerViewDataList.setLayoutManager(new LinearLayoutManager(Activity_Wallet.this));
                        rel_progressdialog.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
            });
            // Instantiate the RequestQueue with the cache and network, start the request
            // and add it to the queue
            queue = new RequestQueue(cache, network);
            queue.start();
            queue.add(request);
        } else {
            //Show no Network dialog
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Wallet.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_Wallet.this).inflate(R.layout.ad_no_network, viewGroup, false);
            AppCompatButton buttonOk = dialogView.findViewById(R.id.ad_noNetwork_btnOk);
            builder.setView(dialogView);
            final android.app.AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    alertDialog.dismiss();
                }
            });
        }
    }

    private void permissionTeleCheck() {
        ActivityCompat.requestPermissions(this, getCameraPermission(), PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : getCameraPermission()) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;
                        }
                    }

                    if (permissionsDenied.length() != 0) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Wallet.this, Manifest.permission.CAMERA)) {
                            //Show Information about why you need the permission
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Wallet.this);
                            builder.setTitle("Need Camera Permission");
                            builder.setMessage("This app needs camera permission to continue.");
                            builder.setPositiveButton("Grant", (dialog, which) -> {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                            });
                            builder.setNegativeButton("Cancel", (dialog, which) -> {
                                dialog.cancel();
                                finish();
                            });
                            builder.show();
                        }
                    }
                }
                break;
        }
    }

    public static String[] getCameraPermission() {
        String[] permissions = new String[]{
                Manifest.permission.CAMERA};
        return permissions;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.bottom_wallet);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitByBackKey() {
        androidx.appcompat.app.AlertDialog alertbox = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
                        homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
                        homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeScreenIntent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    private void call_qrcode_payment(String amt, String name, String code) {
        /*show_transaction_details_array = new ArrayList<>();
        show_transaction_details_array.add("Rakesh R Amin"); // trans_txt_name
        show_transaction_details_array.add("123456"); //trans_txt_transactionID
        show_transaction_details_array.add("Rakesh R Amin"); // trans_txt_transactionTo
        show_transaction_details_array.add("123456@ybl.com"); // trans_txt_transactionToUPICode
        show_transaction_details_array.add("N. R Menezes"); // trans_txt_transactionFrom
        show_transaction_details_array.add("12345@hsn.com"); // trans_txt_transactionFromUPICode
        show_transaction_details_array.add("CICAg0c67L_AuA"); // trans_txt_GoogletransactionID
*/

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        formattedDate = formattedDate.replace(" ","T");

        QRScanWithdrawReq qrScanWithdrawReq = new QRScanWithdrawReq(
                Config.getStringPreference(this, Constant.USER_ID),
                local_wallet_id,
                formattedDate,
                Integer.parseInt(amt.replace("₹", "")),
                code,
                "",
                true,
                name,
                "shopping",
                "",
                "",
                "",
                ""
        );

        show_transaction_details_array = new ArrayList<>();
        Call<QRScanWithdrawRes> call1 = RetrofitClient.getInstance().getMyApi().withdrawreq(qrScanWithdrawReq);
        call1.enqueue(new Callback<QRScanWithdrawRes>() {
            @Override
            public void onResponse(Call<QRScanWithdrawRes> call, retrofit2.Response<QRScanWithdrawRes> response) {
                try {
                    rel_progressdialog.setVisibility(View.GONE);
                    QRScanWithdrawRes qrScanWithdrawRes = response.body();
                    String transid = "";
                    if (qrScanWithdrawRes.getWithdrawalTransactionId() != null) {
                        transid = qrScanWithdrawRes.getWithdrawalTransactionId().toString();
                    }
                    show_transaction_details_array.add(0,name); // trans_txt_name
                    show_transaction_details_array.add(1,transid); //trans_txt_transactionID
                    show_transaction_details_array.add(2,name); // trans_txt_transactionTo
                    show_transaction_details_array.add(3,code); // trans_txt_transactionToUPICode
                    show_transaction_details_array.add(4,Config.getStringPreference(Activity_Wallet.this, Constant.USER_NAME)); // trans_txt_transactionFrom
                    show_transaction_details_array.add(5,Config.getStringPreference(Activity_Wallet.this, Constant.EMAIL)); // trans_txt_transactionFromUPICode
                    show_transaction_details_array.add(6,""); // trans_txt_GoogletransactionID
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
            public void onFailure(Call<QRScanWithdrawRes> call, Throwable t) {
                rel_progressdialog.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }

    private void show_transactionData() {
        try {
            android.app.AlertDialog.Builder qr_payment = new android.app.AlertDialog.Builder(Activity_Wallet.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_Wallet.this).inflate(R.layout.show_transaction_details, viewGroup, false);
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
            trans_txt_GoogletransactionID.setText(show_transaction_details_array.get(6));
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

                    //refresh wallet
                    getWalletPlans();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
*/
    @Override
    public void AvailAmount(String wallet_id, Integer amt) {
        local_todays_limit = amt;
        local_wallet_id = wallet_id;
        if (Config.getStringPreference(this, Constant.SHOULD_SCAN).equalsIgnoreCase("yes")) {
            IntentIntegrator intentIntegrator = new IntentIntegrator(Activity_Wallet.this);
            intentIntegrator.setPrompt("Scan a barcode or QR Code");
            intentIntegrator.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.MIXED_SCAN);
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setTorchEnabled(true);
            intentIntegrator.initiateScan();
        } else {
            // display in popup
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Wallet.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_Wallet.this).inflate(R.layout.confirm_scan_data, viewGroup, false);
            EditText upicode = dialogView.findViewById(R.id.upi_code);
            upicode.setText("");
            EditText upiname = dialogView.findViewById(R.id.upi_name);
            upiname.setText("");
            TextView mp_name = dialogView.findViewById(R.id.mp_name);
            mp_name.setText(Config.getStringPreference(Activity_Wallet.this, Constant.USER_NAME));
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
                            Activity_Wallet.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
                        if (Integer.parseInt(upiamt.getText().toString()) > local_todays_limit) {
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Wallet.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(Activity_Wallet.this).inflate(R.layout.ad_general, viewGroup, false);
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
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Wallet.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(Activity_Wallet.this).inflate(R.layout.confirm_scan_data, viewGroup, false);
                    EditText upicode = dialogView.findViewById(R.id.upi_code);
                    upicode.setText(upi_code);
                    EditText upiname = dialogView.findViewById(R.id.upi_name);
                    upiname.setText(user_name);
                    TextView mp_name = dialogView.findViewById(R.id.mp_name);
                    mp_name.setText(Config.getStringPreference(Activity_Wallet.this, Constant.USER_NAME));
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
                                if (Integer.parseInt(upiamt.getText().toString()) > local_todays_limit) {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Wallet.this);
                                    ViewGroup viewGroup = findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(Activity_Wallet.this).inflate(R.layout.ad_general, viewGroup, false);
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
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showAlert(String Heading, String Message, final EditText field) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Wallet.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Wallet.this).inflate(R.layout.ad_general, viewGroup, false);
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
}