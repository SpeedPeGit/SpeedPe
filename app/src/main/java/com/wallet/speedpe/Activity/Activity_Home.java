package com.wallet.speedpe.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.bumptech.glide.Glide;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.wallet.speedpe.Adapter.RecentPayments_Adapter;
import com.wallet.speedpe.Adapter.SliderPagerAdapter;
import com.wallet.speedpe.Model.HomePlan_Items;
import com.wallet.speedpe.Model.RecentPayments_Items;
import com.wallet.speedpe.Model.RechargeReq;
import com.wallet.speedpe.Model.RechargeRes;
import com.wallet.speedpe.Model.RzpOrdIdRequest;
import com.wallet.speedpe.Model.RzpOrdIdResponse;
import com.wallet.speedpe.PlanDetails;
import com.wallet.speedpe.R;
import com.wallet.speedpe.RazorPayInterface;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;
import com.wallet.speedpe.rest.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class Activity_Home extends BaseActivity implements PlanDetails, RazorPayInterface, PaymentResultWithDataListener {
    private static final String TAG = Activity_Home.class.getSimpleName();
    private RecyclerView recycler_recent_payments;
    private CoordinatorLayout rel_progressdialog;
    private RequestQueue queue;
    private TextView currentDay;
    private LinearLayout toolbar_LeftBtn;
    private ImageButton icon_logout;
    private Button home_view_all;
    private AppCompatButton home_buy_plan;
    String[] list_array;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private RecentPayments_Adapter recentPayments_adapter;

    ArrayList<HomePlan_Items> string_plan_home_ArrayList = new ArrayList<HomePlan_Items>();
    //    ArrayList<Plan_Items> string_plan_ArrayList = new ArrayList<Plan_Items>();
    ArrayList<RecentPayments_Items> string_recent_payments_ArrayList = new ArrayList<RecentPayments_Items>();

    String amt, plan_nm, rp_id, rp_order_id; // Razor pay amount
    String rzp_id, rzp_orderId, rzp_paymentId, rzp_signature;
    Integer damt, credit;

    private RelativeLayout home_screen;
    //    , plan_screen, scan_screen, transaction_screen, nearby_screen;
    private Dialog razorpay_dialog;

    //    private ArrayList<Integer> slider_image_list;
    private ArrayList<String> banner_planid_list;
    private ArrayList<String> banner_planname_list;
    private ArrayList<String> banner_perday_limit;
    private ArrayList<String> banner_cash_back;
    private ArrayList<String> banner_wallet_credit;
    private ArrayList<String> banner_ref_bonus;
    private ArrayList<String> banner_planamount_list;
    private ArrayList<String> banner_plancredit_list;
    private ArrayList<String> banner_plancashback_list;

    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    private SliderPagerAdapter sliderPagerAdapter;
    private TextView[] dots;
    private int page_position = 0;
    PaymentData paymentData1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // method for initialisation
        init();
//        permissionTeleCheck();
        Checkout.preload(getApplicationContext());
        icon_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_logout();
            }
        });

        home_buy_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Home.this, Activity_Plans.class);
                startActivity(i);
            }
        });
        home_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Home.this, Activity_Payments.class);
                startActivity(i);
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
                        Config.setStringPreference(Activity_Home.this, Constant.IS_LOGED_IN, "false");
                        Intent i = new Intent(Activity_Home.this, Activity_login.class);
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

    private void init() {
        razorpay_dialog = new Dialog(this);
        icon_logout = findViewById(R.id.icon_logout);
        currentDay = findViewById(R.id.currentDay);
        home_buy_plan = findViewById(R.id.home_buy_plan);
        home_view_all = findViewById(R.id.home_view_all);
        rel_progressdialog = findViewById(R.id.rel_progressdialog);
        rel_progressdialog.setVisibility(View.GONE);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        vp_slider = (ViewPager) findViewById(R.id.vp_slider);

        //set current date
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy");
        String strDate = formatter.format(date);
        currentDay.setText(strDate);

        rel_progressdialog.setVisibility(View.VISIBLE);
        get_banner_data();
    }

    private void get_banner_data() {
        // Setup 1 MB disk-based cache for Volley
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        queue = Volley.newRequestQueue(this);

        String url = Constant.baseURL + "plan?MaxResultCount=100";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rel_progressdialog.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                //this method will be running on UI thread
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.optJSONArray("items");

                    banner_planid_list = new ArrayList<>();
                    banner_planname_list = new ArrayList<>();
                    banner_perday_limit = new ArrayList<>();
                    banner_cash_back = new ArrayList<>();
                    banner_wallet_credit = new ArrayList<>();
                    banner_ref_bonus = new ArrayList<>();
                    banner_planamount_list = new ArrayList<>();
                    banner_plancredit_list = new ArrayList<>();
                    banner_plancashback_list = new ArrayList<>();


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String perDayLimit = String.valueOf(jsonObject.getInt("perDayLimit"));
                        String cash_back = String.valueOf(jsonObject.getInt("walletAmount") - jsonObject.getInt("amount"));
                        String wallet_credit = String.valueOf(jsonObject.getInt("walletAmount"));
                        String ref_bonus = String.valueOf(jsonObject.getInt("referralBonus"));
                        banner_planid_list.add(jsonObject.getString("id").toString());
                        banner_planname_list.add(jsonObject.getString("planName").toString());
                        banner_planamount_list.add("₹" + String.valueOf(jsonObject.getInt("amount")));
                        banner_perday_limit.add(perDayLimit);
                        banner_plancashback_list.add("₹" + cash_back);
                        banner_plancredit_list.add("₹" + wallet_credit);
                        banner_cash_back.add(cash_back);
                        banner_wallet_credit.add(wallet_credit);
                        banner_ref_bonus.add(ref_bonus);
                    }
                    updateUI();
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

    }

    private void updateUI() {
        getRecentPayments();
        addBottomDots(0);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == banner_planname_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 3000);

        sliderPagerAdapter = new SliderPagerAdapter(Activity_Home.this, banner_planid_list, banner_planname_list, banner_planamount_list, banner_plancredit_list, banner_plancashback_list, (PlanDetails) this);
        vp_slider.setAdapter(sliderPagerAdapter);
        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getRecentPayments() {
        boolean net_avail = Constant.isNetworkConnectionAvailable(Activity_Home.this);
        if (net_avail == true) {
            rel_progressdialog.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            // Setup 1 MB disk-based cache for Volley
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

            // Use HttpURLConnection as the HTTP client
            Network network = new BasicNetwork(new HurlStack());
            queue = Volley.newRequestQueue(this);
            String url = Constant.baseURL + "withdrawal/getwithdrawbymember/" + Config.getStringPreference(Activity_Home.this, Constant.USER_ID) + "?SkipCount=0&MaxResultCount=5";
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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String dat = jsonObject.getString("creationTime");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
                            LocalDate date1 = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                try {
                                    date1 = LocalDate.parse(dat, formatter);
                                    string_recent_payments_ArrayList.add(new RecentPayments_Items(
                                            jsonObject.getString("id"),
                                            "",
                                            jsonObject.getString("name"),
                                            String.valueOf(date1),
                                            "₹" + String.valueOf(jsonObject.getInt("amount")),
                                            jsonObject.getString("withdrawalResponce")));
                                } catch (Exception e) {
                                    string_recent_payments_ArrayList.add(new RecentPayments_Items(
                                            jsonObject.getString("id"),
                                            "",
                                            jsonObject.getString("name"),
                                            dat,
                                            "₹" + String.valueOf(jsonObject.getInt("amount")),
                                            jsonObject.getString("withdrawalResponce")));
                                }
                            }
                        }

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_recent_payments);
                        RecentPayments_Adapter adapter = new RecentPayments_Adapter(Activity_Home.this, string_recent_payments_ArrayList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_Home.this));

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
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.ad_no_network, viewGroup, false);
            AppCompatButton buttonOk = dialogView.findViewById(R.id.ad_noNetwork_btnOk);
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();
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

    private void addBottomDots(int currentPage) {
        dots = new TextView[banner_planname_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#BABABA"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#003248"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.bottom_home);
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
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Home.this, Manifest.permission.CAMERA)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.ad_general, viewGroup, false);
                            ImageView image = dialogView.findViewById(R.id.ad_image);
                            image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert", null, getPackageName())));
                            LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                            lay_yesno.setVisibility(View.VISIBLE);
                            LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                            lay_ok.setVisibility(View.GONE);
                            TextView heading = dialogView.findViewById(R.id.ad_heading);
                            heading.setText("Need Location Permission");
                            TextView message = dialogView.findViewById(R.id.ad_message);
                            message.setText("This app needs Location permission to continue");
                            Button btnYes = dialogView.findViewById(R.id.ad_btnYes);
                            Button btnNo = dialogView.findViewById(R.id.ad_btnNo);
                            btnYes.setText("Grant");
                            btnNo.setText("Cancel");
                            builder.setView(dialogView);
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                            btnYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.cancel();
                                    ActivityCompat.requestPermissions(Activity_Home.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                                }
                            });
                            btnNo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.cancel();
                                    finish();
                                }
                            });
                        } else {
//                            showAlertBoxClose("Sorry, We need Telephone Permission to continue. Please enable this permission for this app.");
                        }
                    } else {
                        checklocation();
                    }
                }
                break;
        }
    }

    private void checklocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled == true && network_enabled == true) {
            int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            // notify user
            if (permissionLocation == -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.ad_general, viewGroup, false);
                ImageView image = dialogView.findViewById(R.id.ad_image);
                image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert", null, getPackageName())));
                LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                lay_yesno.setVisibility(View.VISIBLE);
                LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                lay_ok.setVisibility(View.GONE);
                TextView heading = dialogView.findViewById(R.id.ad_heading);
                heading.setText("Need Location Permission");
                TextView message = dialogView.findViewById(R.id.ad_message);
                message.setText("This app needs Location permission to continue");
                Button btnYes = dialogView.findViewById(R.id.ad_btnYes);
                Button btnNo = dialogView.findViewById(R.id.ad_btnNo);
                btnYes.setText("Grant");
                btnNo.setText("Cancel");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                        finish();
                    }
                });
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        boolean hasAllPermissions = true;
        for (String permission : permissions) {
            //you can return false instead of assigning, but by assigning you can log all permission values
            if (!hasPermission(context, permission)) {
                hasAllPermissions = false;
            }
        }
        return hasAllPermissions;
    }

    public static boolean hasPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        Log.v("TAG", "permission: " + permission + " = \t\t" +
                (res == PackageManager.PERMISSION_GRANTED ? "GRANTED" : "DENIED"));
        return res == PackageManager.PERMISSION_GRANTED;
    }

    public static String[] getCameraPermission() {
        String[] permissions = new String[]{
                Manifest.permission.CAMERA};
        return permissions;
    }

    public void onclick(List<HomePlan_Items> homePlan_Items, int position) {
        String planname = homePlan_Items.get(position).getPlanName();
        //QR Code scan
        IntentIntegrator intentIntegrator = new IntentIntegrator(Activity_Home.this);
        intentIntegrator.setPrompt("Scan a QR Code");
        intentIntegrator.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.MIXED_SCAN);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();

        //this method will be running on UI thread
//        try {
//            JSONObject jsonobject = new JSONObject(response);
//            if (jsonobject.getString("Status").equalsIgnoreCase("true")) {
//                if ((jsonobject.isNull("BunkLogo")) || (jsonobject.getString("BunkLogo").equalsIgnoreCase("null"))) {
//                    File imageFile = new File(String.valueOf(jsonobject.isNull("BunkLogo")));
//                    Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
//                    bunkLogo.setImageBitmap(bmp);1234

//                }
//                if((jsonobject.isNull("BunkName")) || (jsonobject.getString("BunkName").equalsIgnoreCase("null"))) {
//                    bunkName.setText(jsonobject.getString("BunkName"));
//                }
//                else {
//                    bunkName.setText("BunkName");
//                }
//                if((jsonobject.isNull("UPICode")) || (jsonobject.getString("UPICode").equalsIgnoreCase("null"))) {
//                    upiCode.setText(jsonobject.getString("UPICode"));
//                }
//                else {
//                    upiCode.setText("UPICode");
//                }
//                if((jsonobject.isNull("TodaysQuota")) || (jsonobject.getString("TodaysQuota").equalsIgnoreCase("null"))) {
//                    todaysQuota.setText(jsonobject.getString("TodaysQuota"));
//                }
//                else {
//                    todaysQuota.setText("BunkName");
//                }
//            }
//            else {
//                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Home.this);
//                ViewGroup viewGroup = findViewById(android.R.id.content);
//                View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.ad_general, viewGroup, false);
//                LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
//                ley_color.setBackgroundColor(getResources().getColor(R.color.dark_red));
//                ImageView image = dialogView.findViewById(R.id.ad_image);
//                image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert",null, getPackageName())));
//                LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
//                lay_yesno.setVisibility(View.GONE);
//                LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
//                lay_ok.setVisibility(View.VISIBLE);
//                TextView heading = dialogView.findViewById(R.id.ad_heading);
//                heading.setText("Something went wrong");
//                TextView message = dialogView.findViewById(R.id.ad_message);
//                message.setText("Please try after some time");
//                Button btnok = dialogView.findViewById(R.id.ad_btnOk);
//                builder.setView(dialogView);
//                final AlertDialog alertDialog = builder.create();
//                alertDialog.setCancelable(false);
//                alertDialog.show();
//                btnok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog.dismiss();
//                    }
//                });
//            }
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void show_razorpay_dialog() {
        razorpay_dialog.setContentView(R.layout.popup_pay_plan);
        ImageView bunkLogo = razorpay_dialog.findViewById(R.id.img_pop_bunk_logo);
        TextView bunkName = razorpay_dialog.findViewById(R.id.txt_pop_bunk_name);
        TextView upiCode = razorpay_dialog.findViewById(R.id.txt_pop_upi_code);
        final EditText planAmount = razorpay_dialog.findViewById(R.id.edtTxt_plan_amount);
        final TextView availQuota = razorpay_dialog.findViewById(R.id.txt_pop_avail_quota);
        Button proceedToPay = razorpay_dialog.findViewById(R.id.btn_pop_proceed_to_pay);

        proceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int todaysPrice = 100;
                int avail_quota = Integer.parseInt(availQuota.getText().toString());
                int quota = avail_quota * todaysPrice;
                if (Integer.parseInt(planAmount.getText().toString()) <= quota) {
                    call_qrcode_payment();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.ad_general, viewGroup, false);
                    LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
                    ley_color.setBackgroundColor(getResources().getColor(R.color.dark_red));
                    LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                    lay_yesno.setVisibility(View.GONE);
                    LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                    lay_ok.setVisibility(View.VISIBLE);
                    TextView heading = dialogView.findViewById(R.id.ad_heading);
                    heading.setText("Amount not acceptable");
                    TextView message = dialogView.findViewById(R.id.ad_message);
                    message.setText("Please enter lesser amount");
                    Button btnok = dialogView.findViewById(R.id.ad_btnOk);
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });

        razorpay_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        razorpay_dialog.show();
    }

    private void call_qrcode_payment() {
        boolean net_avail = Constant.isNetworkConnectionAvailable(Activity_Home.this);
        /*if (net_avail == true){
            rel_progressdialog.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            // Setup 1 MB disk-based cache for Volley
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

            // Use HttpURLConnection as the HTTP client
            Network network = new BasicNetwork(new HurlStack());
            queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, "https://erp.globalpageant.ml/Apis/LoginByEmailId", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    show_transactionData();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error",error.toString());
                    queue.stop();
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
//                    params.put("EmailId",email_text.getText().toString());
//                    params.put("Password",password_text.getText().toString());
                    return params;
                }
            };
            // Instantiate the RequestQueue with the cache and network, start the request
            // and add it to the queue
            queue = new RequestQueue(cache, network);
            queue.start();
            queue.add(request);
        }*/
        show_transactionData();
    }

    private void show_transactionData() {
//        Intent i = new Intent(Activity_Home.this, TransactionData.class);
//        startActivity(i);
    }

    private void permissionTeleCheck() {
        if (hasPermissions(Activity_Home.this, getCameraPermission())) {
            checklocation();
        } else {
            ActivityCompat.requestPermissions(this, getCameraPermission(), PERMISSION_REQUEST_CODE);
        }
    }

    public void doPayment() {
        int plan_index = banner_planid_list.indexOf(Config.getStringPreference(Activity_Home.this, Constant.PLAN_ID));
        plan_nm = banner_planname_list.get(plan_index).toString();
        Checkout checkout = new Checkout();
        checkout.setKeyID(Constant.razorpayKeyID); //RazorPay API Key
        checkout.setImage(R.drawable.icon);
        final Activity activity = this;
//        amt = "2";
        amt = banner_planamount_list.get(plan_index).replace("₹", "");
        damt = Integer.valueOf(amt) * 100;
        credit = Integer.valueOf(banner_plancredit_list.get(plan_index).replace("₹", ""));
        generate_order_id(checkout, activity, plan_index);
    }

    private void generate_order_id(Checkout checkout, Activity activity, int plan_index) {
        Date c = Calendar.getInstance().getTime();
        android.icu.text.SimpleDateFormat df = new android.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        formattedDate = formattedDate.replace(" ", "T");

        RzpOrdIdRequest rzpOrdIdRequest = new RzpOrdIdRequest(
                Config.getStringPreference(this, Constant.USER_ID),
                banner_planid_list.get(plan_index),
                formattedDate,
                Integer.parseInt(amt.replace("₹", "")),
                "",
                ""
        );
        Call<RzpOrdIdResponse> call1 = RetrofitClient.getInstance().getMyApi().rzpOrderId(rzpOrdIdRequest);
        call1.enqueue(new Callback<RzpOrdIdResponse>() {
            @Override
            public void onResponse(Call<RzpOrdIdResponse> call, retrofit2.Response<RzpOrdIdResponse> response) {
                try {
                    RzpOrdIdResponse rzpOrdIdResponse = response.body();
                    String rechargeDate = rzpOrdIdResponse.getRechargeDate();
                    rp_order_id = rzpOrdIdResponse.getRazorOrderId();
                    rp_id = rzpOrdIdResponse.getId();
                    String transactionResult = rzpOrdIdResponse.getTransactionResult();
                    call_checkout(checkout, activity);
                } catch (Exception e) {
                    rel_progressdialog.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RzpOrdIdResponse> call, Throwable t) {
                rel_progressdialog.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }

    private void call_checkout(Checkout checkout, Activity activity) {
        try {
            JSONObject options = new JSONObject();
            options.put("name", "SpeedPe");
            options.put("description", plan_nm);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("order_id", rp_order_id);
            options.put("amount", String.valueOf(damt));//pass amount in currency subunits
            options.put("prefill.email", Config.getStringPreference(Activity_Home.this, Constant.EMAIL));
            options.put("prefill.contact", Config.getStringPreference(Activity_Home.this, Constant.PHONE));
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

  /*  private void show_card_details(int plan_index) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Home.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.popup_card_information, viewGroup, false);
        EditText card_name, card_number,card_cvv, card_exp_month,card_exp_year;
        AppCompatButton card_btn_submit;

        card_name = dialogView.findViewById(R.id.card_name);
        card_number = dialogView.findViewById(R.id.card_number);
        card_cvv = dialogView.findViewById(R.id.card_cvv);
        card_exp_month = dialogView.findViewById(R.id.card_exp_month);
        card_exp_year = dialogView.findViewById(R.id.card_exp_year);
        card_btn_submit = dialogView.findViewById(R.id.card_btn_submit);

        builder.setView(dialogView);
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
        card_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (card_name.getText().toString().isEmpty()) {
                    showAlert("Cardholder Name can not be blank", "Please enter a valid Name", card_name);
                }
                else if (card_number.getText().toString().isEmpty()) {
                    showAlert("Card Number field can not be blank", "Please enter a valid Card Number", card_number);
                }
                else if (card_cvv.getText().toString().isEmpty()) {
                    showAlert("CVV field can not be blank", "Please enter a valid CVV", card_cvv);
                }
                else if (card_exp_month.getText().toString().isEmpty()) {
                    showAlert("Expiry Month field can not be blank", "Please enter a Month", card_exp_month);
                }
                else if (card_exp_year.getText().toString().isEmpty()) {
                    showAlert("Expiry Year field can not be blank", "Please enter a valid Expiry Year", card_exp_year);
                }
                else {
                    // Call Razorpay
                    rel_progressdialog.setVisibility(View.VISIBLE);
                    doPayment();
                    alertDialog.dismiss();
                }
            }
        });
    }*/

    private void planRefresh() {
        Date c = Calendar.getInstance().getTime();
        android.icu.text.SimpleDateFormat df = new android.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        formattedDate = formattedDate.replace(" ", "T");

        RechargeReq rechargeReq = new RechargeReq(
                Config.getStringPreference(this, Constant.USER_ID),
                Config.getStringPreference(this, Constant.PLAN_ID),
                formattedDate,
                damt / 100,
                paymentData1.getPaymentId(),
                0,
                credit,
                credit,
                true,
                rzp_orderId,
                rzp_paymentId,
                rzp_signature
        );

        Call<RechargeRes> call1 = RetrofitClient.getInstance().getMyApi().recharge(rechargeReq);
        call1.enqueue(new Callback<RechargeRes>() {
            @Override
            public void onResponse(Call<RechargeRes> call, retrofit2.Response<RechargeRes> response) {
                rel_progressdialog.setVisibility(View.GONE);
                RechargeRes rechargeRes = response.body();
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Home.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.popup_transaction_data, viewGroup, false);
                TextView trans_amt = dialogView.findViewById(R.id.trans_amt);
                TextView trans_pay_id = dialogView.findViewById(R.id.trans_pay_id);
                TextView trans_plan_name = dialogView.findViewById(R.id.trans_plan_name);
                TextView trans_order_id = dialogView.findViewById(R.id.trans_order_id);
                TextView trans_email = dialogView.findViewById(R.id.trans_email);
                TextView trans_phone = dialogView.findViewById(R.id.trans_phone);
                AppCompatButton trans_btn_ok = dialogView.findViewById(R.id.trans_btn_ok);

                trans_amt.setText(String.valueOf(damt / 100));
                trans_pay_id.setText(paymentData1.getPaymentId());
                trans_plan_name.setText(plan_nm);
                trans_order_id.setText(rp_order_id);
                trans_email.setText(paymentData1.getUserEmail());
                trans_phone.setText(paymentData1.getUserContact());

                builder.setView(dialogView);
                final android.app.AlertDialog tran_alertDialog = builder.create();
                tran_alertDialog.setCancelable(false);
                tran_alertDialog.show();
                trans_btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tran_alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(Call<RechargeRes> call, Throwable t) {
                rel_progressdialog.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }

    private void showAlert(String Heading, String Message, final EditText field) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.ad_general, viewGroup, false);
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
        final AlertDialog alertDialog = builder.create();
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

    @Override
    public void ShowPlanDetail(String planNumber) {
        //get index
        int plan_index = banner_planid_list.indexOf(planNumber);
        Config.setStringPreference(Activity_Home.this, Constant.PLAN_ID, planNumber);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Home.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.popup_buyplan, viewGroup, false);

        ImageButton wa_logout = dialogView.findViewById(R.id.wa_logout);
        TextView buy_planName = dialogView.findViewById(R.id.buy_planName);
        TextView buy_amt = dialogView.findViewById(R.id.buy_amt);
        TextView buy_perdaylimit = dialogView.findViewById(R.id.buy_perdaylimit);
        TextView buy_cashback = dialogView.findViewById(R.id.buy_cashback);
        TextView buy_walletcredit = dialogView.findViewById(R.id.buy_walletcredit);
        TextView buy_refbonus = dialogView.findViewById(R.id.buy_refbonus);
        AppCompatButton buy_buy = dialogView.findViewById(R.id.buy_buy);
        AppCompatButton buy_cancel = dialogView.findViewById(R.id.buy_cancel);

        Integer image_arraylist = 0;
        if (banner_planname_list.get(plan_index).toLowerCase().contains("welcome")) {
            image_arraylist = (R.drawable.welcom_plan);
        } else if (banner_planname_list.get(plan_index).toLowerCase().contains("bronze")) {
            image_arraylist = (R.drawable.bronze_plan);
        } else if (banner_planname_list.get(plan_index).contains("silver")) {
            image_arraylist = (R.drawable.silver_plan);
        } else if (banner_planname_list.get(plan_index).contains("gold")) {
            image_arraylist = (R.drawable.gold_plan);
        } else {
            image_arraylist = (R.drawable.welcom_plan);
        }
        Glide.with(Activity_Home.this)
                .load(image_arraylist)
                .into(wa_logout);

        buy_planName.setText(banner_planname_list.get(plan_index));
        buy_amt.setText(banner_planamount_list.get(plan_index));
        buy_perdaylimit.setText("₹" + banner_perday_limit.get(plan_index));
        buy_cashback.setText("₹" + banner_cash_back.get(plan_index));
        buy_walletcredit.setText("₹" + banner_wallet_credit.get(plan_index));
        buy_refbonus.setText("₹" + banner_ref_bonus.get(plan_index));

        builder.setView(dialogView);
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        buy_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                show_card_details(plan_index);
                rel_progressdialog.setVisibility(View.VISIBLE);
                doPayment();
                alertDialog.dismiss();
            }
        });
        buy_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        rel_progressdialog.setVisibility(View.GONE);
        paymentData1 = paymentData;
        planRefresh();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        rel_progressdialog.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Home.this).inflate(R.layout.ad_general, viewGroup, false);
        LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
        ley_color.setBackgroundColor(getResources().getColor(R.color.dark_red));
        LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
        lay_yesno.setVisibility(View.GONE);
        LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
        lay_ok.setVisibility(View.VISIBLE);
        TextView heading = dialogView.findViewById(R.id.ad_heading);
        heading.setText("Payment Failure");
        TextView message = dialogView.findViewById(R.id.ad_message);
        message.setText("Try paying once again");
        Button btnok = dialogView.findViewById(R.id.ad_btnOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                rel_progressdialog.setVisibility(View.GONE);
            }
        });
    }
}
