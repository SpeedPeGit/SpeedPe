package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wallet.speedpe.R;

public class FAQ extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AboutUs.class.getSimpleName();
    private FloatingActionButton faq_fab_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.faq);

        faq_fab_back = findViewById(R.id.faq_fab_back);
        faq_fab_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.faq_fab_back:
                Intent i = new Intent(FAQ.this, Activity_Home.class);
                startActivity(i);
                break;
        }
    }
}