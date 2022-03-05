package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.wallet.speedpe.Fragment.Activities_profile_Fragment;
import com.wallet.speedpe.Fragment.Profile_Post_Fragment;
import com.wallet.speedpe.R;

public class Activity_profileList extends AppCompatActivity implements View.OnClickListener {
    private AppCompatImageView card_single_img,card_sub_img;
    private LinearLayout ly_page,ly_activities,lay_edit,ivNotify;
    private RelativeLayout rel_page,rel_activities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilelist);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();
        initview();

        Glide.with(this)
                .asBitmap()
                .load("https://i.redd.it/glin0nwndo501.jpg")
                .into(card_single_img);


        Glide.with(this)
                .asBitmap()
                .load("https://i.redd.it/glin0nwndo501.jpg")
                .into(card_sub_img);

        load_post_fragment();
    }

    private void initview() {
        card_single_img  = findViewById( R.id.card_single_img );
        card_sub_img = findViewById( R.id.card_sub_img );
        ly_page = findViewById( R.id.ly_page );

        ivNotify= findViewById( R.id.ivNotify );

        ly_activities = findViewById( R.id.ly_activities );
        rel_page = findViewById( R.id.rel_page );
        rel_activities = findViewById( R.id.rel_activities );
        lay_edit = findViewById(R.id.lay_edit);
        ly_page.setOnClickListener( this );
        ly_activities.setOnClickListener( this );
        lay_edit.setOnClickListener(this);
        ivNotify.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_page:
                load_post_fragment();
                break;

            case R.id.ly_activities:
                load_activities_fragment();
                break;

            case R.id.lay_edit:
                Intent edit = new Intent(Activity_profileList.this,Activity_edit_profile.class);
                startActivity(edit);
                break;

            case R.id.ivNotify:
                Intent notify = new Intent(Activity_profileList.this,Activity_notifications.class);
                startActivity(notify);
                break;

        }
    }

    private void load_post_fragment() {
        rel_page.setVisibility( View.VISIBLE );
        rel_activities.setVisibility( View.GONE );

        Profile_Post_Fragment fragInfo = new Profile_Post_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragInfo);
        fragmentTransaction.commit();
    }

    private void load_activities_fragment() {
        rel_page.setVisibility( View.GONE );
        rel_activities.setVisibility( View.VISIBLE );

        Activities_profile_Fragment fragInfo = new Activities_profile_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragInfo);
        fragmentTransaction.commit();
    }
}
