package com.wallet.speedpe.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.RequestQueue;
import com.wallet.speedpe.Model.DataModel;
import com.wallet.speedpe.Model.SignupResponse;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;
import com.wallet.speedpe.rest.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_signup extends AppCompatActivity implements View.OnClickListener {
    private CoordinatorLayout rel_progressdialog;
    private ImageView img_logo;
    private EditText emailId_text, phone_number_text, password_text, con_password_text, name_text, referral_text;
    private AppCompatButton btn_Signup;
    private Button btn_login;
    //Top back toolbar
    private ImageView btnShowPassword, btnAgreement;
    private LinearLayout lay_showPassword, lay_agreement;
    private String cityname, cityID;
    private ArrayList<String> cityNameList;
    private ArrayList<String> cityIDList;
    private RequestQueue queue;
    private Boolean hasAgreedCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        Intent gettype = getIntent();
    }

    private void init() {
        rel_progressdialog = findViewById(R.id.rel_progressdialog);
        img_logo = findViewById(R.id.img_logo);
        rel_progressdialog.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        img_logo.setVisibility(View.GONE);
        name_text = findViewById(R.id.name_text);
        name_text.setFocusable(true);
        referral_text = findViewById(R.id.referral_text);
        referral_text.setFocusable(true);
        phone_number_text = findViewById(R.id.phone_number_text);
        phone_number_text.setFocusable(true);
        emailId_text = findViewById(R.id.emailId_text);
        emailId_text.setFocusable(true);
        password_text = findViewById(R.id.password_text);
        password_text.setFocusable(true);
        con_password_text = findViewById(R.id.con_password_text);
        con_password_text.setFocusable(true);
        btn_Signup = findViewById(R.id.btn_cust_Signup);
        btn_login = findViewById(R.id.btn_cust_login);
        btn_Signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btnShowPassword = findViewById(R.id.btnShowPassword);
        btnAgreement = findViewById(R.id.btnAgreement);
        lay_showPassword = findViewById(R.id.lay_showPassword);
        lay_showPassword.setOnClickListener(this);
        lay_agreement = findViewById(R.id.lay_agreement);
        lay_agreement.setOnClickListener(this);
        cityNameList = new ArrayList<String>();
        cityIDList = new ArrayList<String>();
//        Fetch_City();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cust_Signup:
                btn_cust_Signup_Pressed();
                break;
            case R.id.btn_cust_login:
                btn_cust_login_pressed();
                break;
            case R.id.lay_showPassword:
                btn_showPasswordClicked();
                break;
            case R.id.lay_agreement:
                btn_agreementClicked();
                break;
            default:
                break;
        }
    }

    private void btn_showPasswordClicked() {
        if (btnShowPassword.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.blank_checkbox).getConstantState()) {
            btnShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.checked_checkbox));
            password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            con_password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            btnShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.blank_checkbox));
            password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
            con_password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void btn_agreementClicked() {
        if (btnAgreement.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.blank_checkbox).getConstantState()) {
            btnAgreement.setImageDrawable(getResources().getDrawable(R.drawable.checked_checkbox));
            hasAgreedCondition = true;
        } else {
            btnAgreement.setImageDrawable(getResources().getDrawable(R.drawable.blank_checkbox));
            hasAgreedCondition = false;
        }
    }

    private void btn_cust_login_pressed() {
        Intent i = new Intent(Activity_signup.this, Activity_login.class);
        startActivity(i);
    }

    private void btn_cust_Signup_Pressed() {
        /*//            James Lobo
        rel_progressdialog.setVisibility(View.VISIBLE);
        Config.setStringPreference(Activity_signup.this, Constant.DIRECT_LOGIN, "true");
        Config.setStringPreference(Activity_signup.this, Constant.PHONE, phone_number_text.getText().toString());
        Config.setStringPreference(Activity_signup.this, Constant.USER_NAME, name_text.getText().toString());
        Config.setStringPreference(Activity_signup.this, Constant.PASSWORD, password_text.getText().toString());
        Config.setStringPreference(Activity_signup.this, Constant.EMAIL, emailId_text.getText().toString());
        Intent signin = new Intent(Activity_signup.this, Activity_login.class);
        startActivity(signin);*/

        if (name_text.getText().toString().isEmpty()) {
            showAlert("Name can not be blank", "Please enter a valid Name", name_text);
        } else if (phone_number_text.getText().toString().isEmpty()) {
            showAlert("Phone number can not be blank", "Please enter your phone number", phone_number_text);
        } else if ((isValidMobile(phone_number_text.getText().toString())) && (phone_number_text.getText().toString().length() != 10)) {
            showAlert("Invalid phone number", "Please enter a valid phone number", phone_number_text);
        } else if (emailId_text.getText().toString().isEmpty()) {
            showAlert("Email can not be blank", "Please enter your email", emailId_text);
        } else if (!emailId_text.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            showAlert("Invalid email", "Please enter a valid email", emailId_text);
        } else if (password_text.getText().toString().isEmpty()) {
            showAlert("Password can not be blank", "Please enter a valid password", password_text);
        } else if (con_password_text.getText().toString().isEmpty()) {
            showAlert("Confirm Password can not be blank", "Please confirm password", con_password_text);
        } else if (!password_text.getText().toString().equalsIgnoreCase(con_password_text.getText().toString())) {
            showAlert("Password mismatch", "Passwords doesn't match", con_password_text);
        } else if (btnAgreement.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.blank_checkbox).getConstantState()) {
            showAlert("Please check the agreement to proceed", "Terms of Use and Privacy Policy must be checked", password_text);
        } else {
            rel_progressdialog.setVisibility(View.VISIBLE);
            Signup_Customer();
        }
    }

    /*private void Signup_Customer() {
            // Setup 1 MB disk-based cache for Volley
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

            // Use HttpURLConnection as the HTTP client
            Network network = new BasicNetwork(new HurlStack());
            queue = Volley.newRequestQueue(this);

            *//*String s1 =  "?name=" + name_text.getText().toString() + "&mobileNo=" + phone_number_text.getText().toString();
            String s2 = s1 + "&email=" + emailId_text.getText().toString() + "&password=" + password_text.getText().toString();
            String s3 = s2 + "&isDeleted=false";*//*
//            String url = Constant.baseURL + "member/signup";
            StringRequest request = new StringRequest(Request.Method.POST, Constant.baseURL + "member/signup",  new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    //this method will be running on UI thread
                    try {
                        JSONObject jsonResponse = null;
                        jsonResponse = new JSONObject(response);
                        String status = jsonResponse.getString("status");
                        String shortMessage = jsonResponse.getString("shortMessage");
                        String message = jsonResponse.getString("message");
                        if (status=="true"){
                            String mem_id = jsonResponse.getString("memberId");
                            String nam = jsonResponse.getString("name");
                            String ph = jsonResponse.getString("mobileNo");
                            Config.setStringPreference(Activity_signup.this,Constant.USER_ID,mem_id);
                            Config.setStringPreference(Activity_signup.this,Constant.USER_NAME,nam);
                            Config.setStringPreference(Activity_signup.this,Constant.PHONE,ph);
                            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_signup.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(Activity_signup.this).inflate(R.layout.ad_general, viewGroup, false);
                            LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
                            ley_color.setBackgroundColor(getResources().getColor(R.color.dark_green));
                            ImageView image = dialogView.findViewById(R.id.ad_image);
                            image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert",null, getPackageName())));
                            LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                            lay_yesno.setVisibility(View.GONE);
                            LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                            lay_ok.setVisibility(View.VISIBLE);
                            TextView heading = dialogView.findViewById(R.id.ad_heading);
                            heading.setText(shortMessage);
                            TextView messages = dialogView.findViewById(R.id.ad_message);
                            messages.setText(message);
                            Button btnok = dialogView.findViewById(R.id.ad_btnOk);
                            builder.setView(dialogView);
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                    Config.setStringPreference(Activity_signup.this, Constant.DIRECT_LOGIN, "true");
                                    Config.setStringPreference(Activity_signup.this, Constant.EMAIL, emailId_text.getText().toString());
                                    Config.setStringPreference(Activity_signup.this, Constant.PASSWORD, password_text.getText().toString());
                                    Intent signin = new Intent(Activity_signup.this,Activity_login.class);
                                    startActivity(signin);
                                }
                            });
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_signup.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(Activity_signup.this).inflate(R.layout.ad_general, viewGroup, false);
                            LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
                            ley_color.setBackgroundColor(getResources().getColor(R.color.dark_red));
                            ImageView image = dialogView.findViewById(R.id.ad_image);
                            image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert",null, getPackageName())));
                            LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                            lay_yesno.setVisibility(View.GONE);
                            LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                            lay_ok.setVisibility(View.VISIBLE);
                            TextView heading = dialogView.findViewById(R.id.ad_heading);
                            heading.setText(shortMessage);
                            TextView messages = dialogView.findViewById(R.id.ad_message);
                            messages.setText(message);
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

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name_text.getText().toString());
                    params.put("mobileNo", phone_number_text.getText().toString());
                    params.put("email", emailId_text.getText().toString());
                    params.put("password", password_text.getText().toString());
                    params.put("isDeleted", "false");
                    return params;
                }
            };
            // Instantiate the RequestQueue with the cache and network, start the request
            // and add it to the queue
            queue = new RequestQueue(cache, network);
            queue.start();
            queue.add(request);
    }*/


    private void Signup_Customer(){
        String ref;
        try {
            ref = referral_text.getText().toString();
        }
        catch(Exception e) {
            e.printStackTrace();
            ref = "";
        }
        DataModel user = new DataModel(
                name_text.getText().toString(),
                phone_number_text.getText().toString(),
                emailId_text.getText().toString(),
                password_text.getText().toString(),
                false,
                ref
        );
        Call<SignupResponse> call1 = RetrofitClient.getInstance().getMyApi().createUser(user);
        call1.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                rel_progressdialog.setVisibility(View.GONE);

                SignupResponse user1 = response.body();
                boolean status = user1.getStatus();
                String shortMessage = user1.getShortMessage();
                String message = user1.getMessage();
                if (status){
                    String mem_id = user1.getMemberId();
                    String nam = user1.getName();
                    String ph = user1.getMobileNo();
                    Config.setStringPreference(Activity_signup.this,Constant.USER_ID,mem_id);
                    Config.setStringPreference(Activity_signup.this,Constant.USER_NAME,nam);
                    Config.setStringPreference(Activity_signup.this,Constant.PHONE,ph);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_signup.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    Config.setStringPreference(Activity_signup.this, Constant.DIRECT_LOGIN, "true");
                    Config.setStringPreference(Activity_signup.this, Constant.EMAIL, emailId_text.getText().toString());
                    Config.setStringPreference(Activity_signup.this, Constant.PASSWORD, password_text.getText().toString());
                    Config.setStringPreference(Activity_signup.this, Constant.REFERRAL_CODE, referral_text.getText().toString());
                    Intent signin = new Intent(Activity_signup.this,Activity_login.class);
                    startActivity(signin);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_signup.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(Activity_signup.this).inflate(R.layout.ad_general, viewGroup, false);
                    LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
                    ley_color.setBackgroundColor(getResources().getColor(R.color.dark_red));
                    ImageView image = dialogView.findViewById(R.id.ad_image);
                    image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert",null, getPackageName())));
                    LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                    lay_yesno.setVisibility(View.GONE);
                    LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                    lay_ok.setVisibility(View.VISIBLE);
                    TextView heading = dialogView.findViewById(R.id.ad_heading);
                    heading.setText(shortMessage);
                    TextView messages = dialogView.findViewById(R.id.ad_message);
                    messages.setText(message);
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

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                rel_progressdialog.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }

    private void showAlert(String Heading, String Message, final EditText field) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_signup.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_signup.this).inflate(R.layout.ad_general, viewGroup, false);
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

    private boolean isValidMobile(CharSequence phone) {
        if (!TextUtils.isEmpty(phone)) {
            return Patterns.PHONE.matcher(phone).matches();
        }
        return false;
    }

/*
    private void Fetch_City() {
        boolean net_avail = Constant.isNetworkConnectionAvailable(Activity_signup.this);
        if (net_avail == true){
            rel_progressdialog.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            img_logo.setVisibility(View.VISIBLE);
            // Setup 1 MB disk-based cache for Volley
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

            // Use HttpURLConnection as the HTTP client
            Network network = new BasicNetwork(new HurlStack());
            queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.GET, Constant.baseURL+"city/getall-city", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    rel_progressdialog.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    //this method will be running on UI thread
                    try {
                        JSONObject jsonobject = new JSONObject(response);
                        JSONArray jArray = jsonobject.getJSONArray("items");
                        cityNameList.clear();
                        cityIDList.clear();
                        if (jArray.length() > 0) {
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject jsonobj = jArray.getJSONObject(i);
                                String name = jsonobj.getString("name");
                                cityNameList.add(name);
                                String id = jsonobj.getString("id");
                                cityIDList.add(id);
                            }
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_signup.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(Activity_signup.this).inflate(R.layout.ad_general, viewGroup, false);
                            LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
                            ley_color.setBackgroundColor(getResources().getColor(R.color.dark_green));
                            ImageView image = dialogView.findViewById(R.id.ad_image);
                            image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert",null, getPackageName())));
                            LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
                            lay_yesno.setVisibility(View.GONE);
                            LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
                            lay_ok.setVisibility(View.VISIBLE);
                            TextView heading = dialogView.findViewById(R.id.ad_heading);
                            heading.setText("City list not updated");
                            TextView messages = dialogView.findViewById(R.id.ad_message);
                            messages.setText("Please try again later");
                            Button btnok = dialogView.findViewById(R.id.ad_btnOk);
                            builder.setView(dialogView);
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                    btn_cust_login_pressed();
                                }
                            });
                        }
                        SpinnerAdapter adapter = new com.example.speedpe.adapter.SpinnerAdapter(Activity_signup.this, android.R.layout.simple_spinner_dropdown_item, cityNameList);
                        spinner_city.setAdapter(adapter);
                    }
                    catch (JSONException e) {
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
            }) ;
            // Instantiate the RequestQueue with the cache and network, start the request and add it to the queue
            queue = new RequestQueue(cache, network);
            queue.start();
            queue.add(request);
        }
        else {
            //Show no Network dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_signup.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_signup.this).inflate(R.layout.ad_no_network, viewGroup, false);
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
*/

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(Activity_signup.this, Activity_login.class);
            startActivity(i);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}