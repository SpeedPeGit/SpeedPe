package com.wallet.speedpe.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wallet.speedpe.R;

public class Activity_SharePost extends AppCompatActivity implements View.OnClickListener{
    private AppCompatImageView post_img;
    private LinearLayout ivsubmit,ivclose;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_sharepost);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        post_img = findViewById(R.id.post_img);

        ivsubmit = findViewById(R.id.ivsubmit);
        ivclose = findViewById(R.id.ivclose);

        ivsubmit.setOnClickListener(this);
        ivclose.setOnClickListener(this);

        Glide.with(this)
                .asBitmap()
                .load("https://i.redd.it/qn7f9oqu7o501.jpg")
                .into(post_img);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ivsubmit:
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_SharePost.this,R.style.BottomSheetDialogTheme);
                View bottomsheetview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_post_bottomsheet,(LinearLayout)findViewById(R.id.bottom_location));
                bottomSheetDialog.setContentView(bottomsheetview);
                bottomSheetDialog.show();
                break;

            case R.id.ivclose:
                finish();
                break;
        }
    }
}
