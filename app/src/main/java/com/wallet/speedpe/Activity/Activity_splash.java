package com.wallet.speedpe.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;

public class Activity_splash extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    private int REQUEST_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Config.setStringPreference(Activity_splash.this,"option_selected","");
                Intent i;
                if (Config.getStringPreference(Activity_splash.this, Constant.IS_LOGED_IN).isEmpty()){
                    Config.setStringPreference(Activity_splash.this, Constant.IS_LOGED_IN, "false");
                }
                if (Config.getStringPreference(Activity_splash.this, Constant.IS_LOGED_IN).equalsIgnoreCase("true")){
                    i = new Intent(Activity_splash.this,Activity_Home.class);
                    startActivity(i);
                    finish();
                }
                else {
                    i = new Intent(Activity_splash.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
