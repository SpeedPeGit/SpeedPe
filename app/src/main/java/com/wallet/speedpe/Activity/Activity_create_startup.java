package com.wallet.speedpe.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.wallet.speedpe.R;

public class Activity_create_startup extends AppCompatActivity implements View.OnClickListener{
    private TextView txt_title,txt_submit;
    private AppCompatImageView card_single_img,card_sub_img;
    private LinearLayout ivclose;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        card_single_img = findViewById(R.id.card_single_img);
        card_sub_img = findViewById(R.id.card_sub_img);

        ivclose= findViewById(R.id.ivclose);
        ivclose.setOnClickListener(this);

        Glide.with(this)
                .asBitmap()
                .load("https://i.redd.it/glin0nwndo501.jpg")
                .into(card_single_img);

        Glide.with(this)
                .asBitmap()
                .load("https://i.redd.it/tpsnoz5bzo501.jpg")
                .into(card_sub_img);

        txt_title = findViewById(R.id.title);
        txt_submit = findViewById(R.id.txt_submit);
        txt_title.setText("Edit startup");
        txt_submit.setText("Save");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivclose:
                finish();
                break;
        }
    }
}