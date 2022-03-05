package com.wallet.speedpe.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
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
import com.wallet.speedpe.Adapter.PayPlans_Adapter;
import com.wallet.speedpe.Adapter.RecentPayments_Adapter;
import com.wallet.speedpe.Model.Pay_Plans_Items;
import com.wallet.speedpe.Model.RecentPayments_Items;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Activity_Payments extends BaseActivity implements View.OnClickListener{
    //vars
    private static final String TAG = Activity_Payments.class.getSimpleName();
    private CoordinatorLayout rel_progressdialog;
    private RequestQueue queue;
    private ImageButton icon_logout;
    private RecyclerView recyclerViewPayPayments, recyclerViewPayPlans;
    private AppCompatButton btnPayments, btnPlans;
    private TextView pay_name;

    ArrayList<RecentPayments_Items> string_pay_payments = new ArrayList<RecentPayments_Items>();
    ArrayList<Pay_Plans_Items> string_pay_plans = new ArrayList<Pay_Plans_Items>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentLayout( R.layout.activtiy_payments);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();
        init_payments();
        icon_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_logout();
            }
        });
        btnPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlans.setBackgroundColor(Color.TRANSPARENT);
                btnPlans.setTextColor(getColor(R.color.black));
                btnPayments.setTextColor(getColor(R.color.white));
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btnPayments.setBackgroundDrawable(ContextCompat.getDrawable(Activity_Payments.this, R.drawable.button_background) );
                } else {
                    btnPayments.setBackground(ContextCompat.getDrawable(Activity_Payments.this, R.drawable.button_background));
                }
                recyclerViewPayPayments.setVisibility(View.VISIBLE);
                recyclerViewPayPlans.setVisibility(View.GONE);
            }
        });

        btnPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPayments.setBackgroundColor(Color.TRANSPARENT);
                btnPayments.setTextColor(getColor(R.color.black));
                btnPlans.setTextColor(getColor(R.color.white));
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    btnPlans.setBackgroundDrawable(ContextCompat.getDrawable(Activity_Payments.this, R.drawable.button_background) );
                } else {
                    btnPlans.setBackground(ContextCompat.getDrawable(Activity_Payments.this, R.drawable.button_background));
                }
                recyclerViewPayPayments.setVisibility(View.GONE);
                recyclerViewPayPlans.setVisibility(View.VISIBLE);
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
                        Config.setStringPreference(Activity_Payments.this, Constant.IS_LOGED_IN, "false");
                        Intent i = new Intent(Activity_Payments.this, Activity_login.class);
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

    private void init_payments() {
        icon_logout = findViewById(R.id.pay_logout);
        pay_name = findViewById(R.id.pay_name);
        pay_name.setText(Config.getStringPreference(Activity_Payments.this,Constant.USER_NAME));
        btnPayments = findViewById(R.id.btnPayments);
        btnPlans = findViewById(R.id.btnPlans);
        rel_progressdialog = findViewById(R.id.rel_progressdialog);
        rel_progressdialog.setVisibility(View.GONE);
        recyclerViewPayPlans = (RecyclerView) findViewById(R.id.recyclerViewPayPlans);
        recyclerViewPayPayments = (RecyclerView) findViewById(R.id.recyclerViewPayPayments);
        displayPayments();
    }

    private void displayPayments() {
        boolean net_avail = Constant.isNetworkConnectionAvailable(Activity_Payments.this);
        if (net_avail == true) {
            rel_progressdialog.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            // Setup 1 MB disk-based cache for Volley
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

            // Use HttpURLConnection as the HTTP client
            Network network = new BasicNetwork(new HurlStack());
            queue = Volley.newRequestQueue(this);
            String url = Constant.baseURL + "withdrawal/getwithdrawbymember/" + Config.getStringPreference(Activity_Payments.this, Constant.USER_ID);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    //this method will be running on UI thread
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.optJSONArray("items");
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String dat = jsonObject.getString("withdrawalDate");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
                            LocalDate date1 = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                try {
                                    date1 = LocalDate.parse(dat, formatter);
                                    string_pay_payments.add(new RecentPayments_Items(
                                            jsonObject.getString("id"),
                                            "",
                                            jsonObject.getString("name"),
                                            String.valueOf(date1),
                                            "₹"+String.valueOf(jsonObject.getInt("amount")),
                                            jsonObject.getString("withdrawalResponce")));
                                }
                                catch (Exception e){
                                    string_pay_payments.add(new RecentPayments_Items(
                                            jsonObject.getString("id"),
                                            "",
                                            jsonObject.getString("name"),
                                            dat,
                                            "₹"+String.valueOf(jsonObject.getInt("amount")),
                                            jsonObject.getString("withdrawalResponce")));
                                }
                            }
                        }
                        displayPlans();
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
        }
        else {
            //Show no Network dialog
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Payments.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_Payments.this).inflate(R.layout.ad_no_network, viewGroup, false);
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
        RecentPayments_Adapter adapter = new RecentPayments_Adapter(this, string_pay_payments);
        recyclerViewPayPayments.setAdapter(adapter);
        recyclerViewPayPayments.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displayPlans() {
        boolean net_avail = Constant.isNetworkConnectionAvailable(Activity_Payments.this);
        if (net_avail == true) {
            rel_progressdialog.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            // Setup 1 MB disk-based cache for Volley
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

            // Use HttpURLConnection as the HTTP client
            Network network = new BasicNetwork(new HurlStack());
            queue = Volley.newRequestQueue(this);
            String url = Constant.baseURL + "recharge/getrechargebymember/" + Config.getStringPreference(Activity_Payments.this, Constant.USER_ID);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(String response) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    //this method will be running on UI thread
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.optJSONArray("items");
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String dat = jsonObject.getString("rechargeDate");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);
                            DateTimeFormatter yformatter = DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH);
                            LocalDate date1 = null;
                            LocalDate ydate = null;
                            String[] split = dat.split("T");
                            String firstSubString = split[0];
                            split = firstSubString.split("-");
                            String d1,d2;
                            d1=split[0];
                            d2=split[1] + ", " + split[2];
                            string_pay_plans.add(new Pay_Plans_Items(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("planName"),
                                    d1,
                                    d2,
                                    "₹"+String.valueOf(jsonObject.getInt("amount"))));
                        }
                        PayPlans_Adapter payPlans_adapter = new PayPlans_Adapter(Activity_Payments.this, string_pay_plans);
                        recyclerViewPayPlans.setAdapter(payPlans_adapter);
                        recyclerViewPayPlans.setLayoutManager(new LinearLayoutManager(Activity_Payments.this));
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
        }
        else {
            //Show no Network dialog
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Payments.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_Payments.this).inflate(R.layout.ad_no_network, viewGroup, false);
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

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.bottom_payments);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
        }
    }
}
