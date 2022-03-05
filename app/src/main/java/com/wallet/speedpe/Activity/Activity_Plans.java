package com.wallet.speedpe.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.wallet.speedpe.Adapter.Plans_Adapter;
import com.wallet.speedpe.Model.Plans_Items;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class Activity_Plans extends BaseActivity implements PlanDetails, RazorPayInterface, PaymentResultWithDataListener {
    //vars
    private static final String TAG = Activity_Plans.class.getSimpleName();
    private RecyclerView recyclerView_planList;
    private CoordinatorLayout rel_progressdialog;
    private RequestQueue queue;
    private ImageButton icon_logout;
    private TextView pl_name;
    String amt, plan_nm, rp_id, rp_order_id; // Razor pay amount
    String rzp_id, rzp_orderId, rzp_paymentId, rzp_signature;
    Integer damt, credit;

    ArrayList<Plans_Items> string_plans_items = new ArrayList<Plans_Items>();
    private Dialog razorpay_dialog;
    PaymentData paymentData1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentLayout( R.layout.activity_plans);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();
        init_plans();
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
                        Config.setStringPreference(Activity_Plans.this, Constant.IS_LOGED_IN, "false");
                        Intent i = new Intent(Activity_Plans.this, Activity_login.class);
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

    private void init_plans() {
        razorpay_dialog = new Dialog(this);
        icon_logout = findViewById(R.id.pl_logout);
        pl_name = findViewById(R.id.pl_name);
        pl_name.setText(Config.getStringPreference(Activity_Plans.this,Constant.USER_NAME));
        rel_progressdialog = findViewById(R.id.rel_progressdialog);
        rel_progressdialog.setVisibility(View.GONE);

        get_banner_data();
    }

    private void get_banner_data() {
        // Setup 1 MB disk-based cache for Volley
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        queue = Volley.newRequestQueue(this);

        String url = Constant.baseURL + "plan?MaxResultCount=100";
        StringRequest request = new StringRequest(Request.Method.GET, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rel_progressdialog.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                //this method will be running on UI thread
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Integer totalCount = jsonResponse.getInt("totalCount");
                    JSONArray jsonArray = jsonResponse.optJSONArray("items");

                    string_plans_items = new ArrayList<>();

                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String perDayLimit = String.valueOf(jsonObject.getInt("perDayLimit"));
                        String cash_back = String.valueOf(jsonObject.getInt("walletAmount") - jsonObject.getInt("amount"));
                        String wallet_credit = String.valueOf(jsonObject.getInt("walletAmount"));
                        String ref_bonus = String.valueOf(jsonObject.getInt("referralBonus"));
                        string_plans_items.add(new Plans_Items(
                                jsonObject.getString("id"),
                                jsonObject.getString("planName"),
                                "₹"+String.valueOf(jsonObject.getInt("amount")),
                                "₹"+wallet_credit,
                                "₹"+cash_back,
                                perDayLimit,
                                cash_back,
                                wallet_credit,
                                ref_bonus
                        ));
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_plans_list);
        Plans_Adapter adapter = new Plans_Adapter(this,string_plans_items, (PlanDetails) this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.bottom_plans);
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
                if (Integer.parseInt(planAmount.getText().toString()) <= quota){
                    call_qrcode_payment();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Plans.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(Activity_Plans.this).inflate(R.layout.ad_general, viewGroup, false);
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
        boolean net_avail = Constant.isNetworkConnectionAvailable(Activity_Plans.this);
        if (net_avail == true){
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
        }
    }

    private void show_transactionData() {
//        Intent i = new Intent(Activity_Plans.this, TransactionData.class);
//        startActivity(i);
    }

    public void doPayment() {
        int plan_index = 0;
        for (int i=0; i<string_plans_items.size();i++){
            if (string_plans_items.get(i).pl_id.equalsIgnoreCase(Config.getStringPreference(Activity_Plans.this, Constant.PLAN_ID))){
                plan_index = i;
                break;
            }
        }
        plan_nm = string_plans_items.get(plan_index).getPl_name().toString();
        Checkout checkout = new Checkout();
        Checkout.preload(getApplicationContext());
        checkout.setKeyID(Constant.razorpayKeyID); //RazorPay API Key
        checkout.setImage(R.drawable.icon);
        final Activity activity = this;
//        amt = "2";
        amt = string_plans_items.get(plan_index).getPl_mrp().replace("₹","");
        damt = Integer.valueOf(amt) * 100;
        credit = Integer.valueOf(string_plans_items.get(plan_index).getPl_totalquota().replace("₹",""));

        generate_order_id(checkout, activity, plan_index);
    }

    private void generate_order_id(Checkout checkout, Activity activity, int plan_index) {
        Date c = Calendar.getInstance().getTime();
        android.icu.text.SimpleDateFormat df = new android.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        formattedDate = formattedDate.replace(" ","T");

        RzpOrdIdRequest rzpOrdIdRequest = new RzpOrdIdRequest(
                Config.getStringPreference(this, Constant.USER_ID),
                string_plans_items.get(plan_index).pl_id,
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
//                    rel_progressdialog.setVisibility(View.GONE);
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
            options.put("prefill.email", Config.getStringPreference(Activity_Plans.this, Constant.EMAIL));
            options.put("prefill.contact",Config.getStringPreference(Activity_Plans.this, Constant.PHONE));
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);
        } catch (JSONException e) {
            rel_progressdialog.setVisibility(View.GONE);
            e.printStackTrace();
        }

    }

    /*private void show_card_details(int plan_index) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Plans.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Plans.this).inflate(R.layout.popup_card_information, viewGroup, false);
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

    private void showAlert(String Heading, String Message, final EditText field) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Plans.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Plans.this).inflate(R.layout.ad_general, viewGroup, false);
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
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        rel_progressdialog.setVisibility(View.GONE);
        paymentData1 = paymentData;
        planRefresh();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        rel_progressdialog.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Plans.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Plans.this).inflate(R.layout.ad_general, viewGroup, false);
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

    private void planRefresh(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        formattedDate = formattedDate.replace(" ","T");

            RechargeReq rechargeReq = new RechargeReq(
                    Config.getStringPreference(this ,Constant.USER_ID),
                    Config.getStringPreference(this, Constant.PLAN_ID),
                    formattedDate,
                    damt/100,
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Plans.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(Activity_Plans.this).inflate(R.layout.popup_transaction_data, viewGroup, false);
                TextView trans_amt = dialogView.findViewById(R.id.trans_amt);
                TextView trans_pay_id = dialogView.findViewById(R.id.trans_pay_id);
                TextView trans_plan_name = dialogView.findViewById(R.id.trans_plan_name);
                TextView trans_order_id = dialogView.findViewById(R.id.trans_order_id);
                TextView trans_email = dialogView.findViewById(R.id.trans_email);
                TextView trans_phone = dialogView.findViewById(R.id.trans_phone);
                AppCompatButton trans_btn_ok = dialogView.findViewById(R.id.trans_btn_ok);

                trans_amt.setText(String.valueOf(damt/100));
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


    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
*/
    @Override
    public void ShowPlanDetail(String planNumber) {
        //get index
        int plan_index = 0;
        for (int i=0; i<string_plans_items.size();i++){
            if (string_plans_items.get(i).pl_id.equalsIgnoreCase(planNumber)){
                plan_index = i;
                break;
            }
        }
        Config.setStringPreference(Activity_Plans.this, Constant.PLAN_ID, planNumber);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Plans.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_Plans.this).inflate(R.layout.popup_buyplan, viewGroup, false);

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
        if (string_plans_items.get(plan_index).getPl_name().toLowerCase().contains("welcome")){
            image_arraylist = (R.drawable.welcom_plan);
        }
        else if (string_plans_items.get(plan_index).getPl_name().toLowerCase().contains("bronze")){
            image_arraylist = (R.drawable.bronze_plan);
        }
        else if (string_plans_items.get(plan_index).getPl_name().toLowerCase().contains("silver")){
            image_arraylist = (R.drawable.silver_plan);
        }
        else if (string_plans_items.get(plan_index).getPl_name().toLowerCase().contains("gold")){
            image_arraylist = (R.drawable.gold_plan);
        }
        else{
            image_arraylist = (R.drawable.welcom_plan);
        }
        Glide.with(Activity_Plans.this)
                .load(image_arraylist)
                .into(wa_logout);

        buy_planName.setText(string_plans_items.get(plan_index).getPl_name());
        buy_amt.setText(string_plans_items.get(plan_index).getPl_mrp());
        buy_perdaylimit.setText("₹"+string_plans_items.get(plan_index).getPerDayLimit());
        buy_cashback.setText("₹"+string_plans_items.get(plan_index).getCash_back());
        buy_walletcredit.setText("₹"+string_plans_items.get(plan_index).getWallet_credit());
        buy_refbonus.setText("₹"+string_plans_items.get(plan_index).getRef_bonus());

        builder.setView(dialogView);
        final int x_plan_index = plan_index;
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        buy_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                show_card_details(x_plan_index);
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
}