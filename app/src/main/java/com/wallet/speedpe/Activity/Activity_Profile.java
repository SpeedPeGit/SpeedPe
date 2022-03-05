package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.wallet.speedpe.Fragment.About_Fragment;
import com.wallet.speedpe.Fragment.Activities_Fragment;
import com.wallet.speedpe.Fragment.Profile_Post_Fragment;
import com.wallet.speedpe.R;

public class Activity_Profile extends BaseActivity implements View.OnClickListener {
    private AppCompatImageView card_single_img,card_sub_img;
    private LinearLayout ly_about,ly_activities,lay_edit,ivNotify;
    private RelativeLayout rel_about,rel_activities,rel_post;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentLayout( R.layout.activity_profile );
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

        load_about_fragment();
    }

    private void initview() {
        card_single_img  = findViewById( R.id.card_single_img );
        card_sub_img = findViewById( R.id.card_sub_img );

        ly_about = findViewById( R.id.ly_about );
        ly_activities = findViewById( R.id.ly_activities );
    /*    ly_post = findViewById( R.id.ly_post );*/
        rel_about = findViewById( R.id.rel_about );
        rel_activities = findViewById( R.id.rel_activities );
        rel_post = findViewById( R.id.rel_post );

        ly_about.setOnClickListener( this );
  /*      ly_post.setOnClickListener( this );*/
        ly_activities.setOnClickListener( this );

        lay_edit = findViewById(R.id.lay_edit);
        lay_edit.setOnClickListener(this);

        ivNotify = findViewById( R.id.ivNotify );
        ivNotify.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.bottom_more);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_about:
                load_about_fragment();
                break;

            case R.id.ly_activities:
                load_activities_fragment();
                break;

    /*        case R.id.ly_post:
                load_post_fragment();
                break;*/

            case R.id.lay_edit:
                Intent edit = new Intent(Activity_Profile.this,Activity_edit_profile.class);
                startActivity(edit);
                break;

            case R.id.ivNotify:
                Intent notify = new Intent(Activity_Profile.this,Activity_notifications.class);
                startActivity(notify);
                break;
        }
    }

    private void load_about_fragment() {
        rel_about.setVisibility( View.VISIBLE );
        rel_activities.setVisibility( View.GONE );

        About_Fragment fragInfo = new About_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragInfo);
        fragmentTransaction.commit();
    }

    private void load_activities_fragment() {
        rel_about.setVisibility( View.GONE );
        rel_activities.setVisibility( View.VISIBLE );

        Activities_Fragment fragInfo = new Activities_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragInfo);
        fragmentTransaction.commit();
    }

    private void load_post_fragment() {
        rel_about.setVisibility( View.GONE );
        rel_activities.setVisibility( View.GONE );
        rel_post.setVisibility( View.VISIBLE );

        Profile_Post_Fragment fragInfo = new Profile_Post_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragInfo);
        fragmentTransaction.commit();
    }
}
