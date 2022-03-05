package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wallet.speedpe.R;

public class AboutUs extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AboutUs.class.getSimpleName();
    private FloatingActionButton fab_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.aboutus);

        fab_back = findViewById(R.id.fab_back);
        fab_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fab_back:
                Intent i = new Intent(AboutUs.this, Activity_Home.class);
                startActivity(i);
                break;
        }
    }
}