package com.wallet.speedpe.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_login extends AppCompatActivity implements View.OnClickListener {
    private String user_roll = "";
    private CoordinatorLayout rel_progressdialog;
    private EditText mobile_text, password_text;
    private AppCompatButton btn_login;
    private Button btn_signup;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    private void init() {
        rel_progressdialog = findViewById(R.id.rel_progressdialog);
        rel_progressdialog.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mobile_text = findViewById(R.id.mobile_text);
        mobile_text.setFocusable(true);
        password_text = findViewById(R.id.password_text);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);
        if (!Config.getStringPreference(Activity_login.this, Constant.DIRECT_LOGIN).isEmpty()) {
            if (Config.getStringPreference(Activity_login.this, Constant.DIRECT_LOGIN) == "true") {
                rel_progressdialog.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                mobile_text.setText(Config.getStringPreference(Activity_login.this, Constant.PHONE));
                password_text.setText(Config.getStringPreference(Activity_login.this, Constant.PASSWORD));
                Config.setStringPreference(Activity_login.this, Constant.DIRECT_LOGIN, "false");
                login_Pressed();
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_login.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Activity_login.this).inflate(R.layout.ad_general, viewGroup, false);
        ImageView image = dialogView.findViewById(R.id.ad_image);
        image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/ad_alert", null, getPackageName())));
        LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
        lay_yesno.setVisibility(View.VISIBLE);
        LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
        lay_ok.setVisibility(View.GONE);
        TextView heading = dialogView.findViewById(R.id.ad_heading);
        heading.setText(R.string.quit_application);
        TextView message = dialogView.findViewById(R.id.ad_message);
        message.setText("");
        Button btnYes = dialogView.findViewById(R.id.ad_btnYes);
        Button btnNo = dialogView.findViewById(R.id.ad_btnNo);
        btnYes.setText(R.string.yes);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                alertDialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login_Pressed();
                break;
            case R.id.btn_signup:
                signup_Pressed();
                break;
            default:
                break;
        }
    }

    private void signup_Pressed() {
        Intent i = new Intent(Activity_login.this, Activity_signup.class);
        startActivity(i);
    }

    private void login_Pressed() {
       /* // James Lobo
        Config.setStringPreference(Activity_login.this,Constant.IS_LOGED_IN,"true");
        Intent i = new Intent(Activity_login.this, Activity_Home.class);
        startActivity(i);
        if (rel_progressdialog.getVisibility() == View.VISIBLE) {
            rel_progressdialog.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }*/
        String mobile = mobile_text.getText().toString();
        String password = password_text.getText().toString();
        if (mobile.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_login.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_login.this).inflate(R.layout.ad_general, viewGroup, false);
            LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
            ley_color.setBackgroundColor(getResources().getColor(R.color.dark_red));
            LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
            lay_yesno.setVisibility(View.GONE);
            LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
            lay_ok.setVisibility(View.VISIBLE);
            TextView heading = dialogView.findViewById(R.id.ad_heading);
            heading.setText("Mobile number can not be blank");
            TextView message = dialogView.findViewById(R.id.ad_message);
            message.setText("Please enter your Mobile Number");
            Button btnok = dialogView.findViewById(R.id.ad_btnOk);
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mobile_text.setFocusable(true);
                    alertDialog.dismiss();
                }
            });
        }
        else if (password.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_login.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(Activity_login.this).inflate(R.layout.ad_general, viewGroup, false);
            LinearLayout ley_color = dialogView.findViewById(R.id.ley_color);
            ley_color.setBackgroundColor(getResources().getColor(R.color.dark_red));
            LinearLayout lay_yesno = dialogView.findViewById(R.id.ad_lay_yesno);
            lay_yesno.setVisibility(View.GONE);
            LinearLayout lay_ok = dialogView.findViewById(R.id.ad_lay_ok);
            lay_ok.setVisibility(View.VISIBLE);
            TextView heading = dialogView.findViewById(R.id.ad_heading);
            heading.setText("Password can not be blank");
            TextView message = dialogView.findViewById(R.id.ad_message);
            message.setText("Please enter your password");
            Button btnok = dialogView.findViewById(R.id.ad_btnOk);
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    password_text.setFocusable(true);
                    alertDialog.dismiss();
                }
            });
        }
        else {
            rel_progressdialog.setVisibility(View.VISIBLE);
            Login_Customer();
        }
    }

    private void Login_Customer() {
        // Setup 1 MB disk-based cache for Volley
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        queue = Volley.newRequestQueue(this);
        String url = Constant.baseURL + "member/signin" + "?MobileNo=" + mobile_text.getText().toString() + "&Password=" + password_text.getText().toString();
        StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
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
                        Config.setStringPreference(Activity_login.this,Constant.USER_ID,mem_id);
                        Config.setStringPreference(Activity_login.this,Constant.USER_NAME,nam);
                        Config.setStringPreference(Activity_login.this,Constant.PHONE,ph);
                        Config.setStringPreference(Activity_login.this, Constant.IS_LOGED_IN, "true");
                        Intent signin = new Intent(Activity_login.this,Activity_Home.class);
                        startActivity(signin);
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_login.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(Activity_login.this).inflate(R.layout.ad_general, viewGroup, false);
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
            /*@Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MobileNo", mobile_text.getText().toString());
                params.put("Password",password_text.getText().toString());
                return params;
            }*/

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
}