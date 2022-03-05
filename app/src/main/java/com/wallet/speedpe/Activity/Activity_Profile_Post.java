package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.wallet.speedpe.Fragment.Comment_Fragment;
import com.wallet.speedpe.Fragment.Pitch_Fragment;
import com.wallet.speedpe.Fragment.Post_Fragment;
import com.wallet.speedpe.R;

public class Activity_Profile_Post extends AppCompatActivity implements View.OnClickListener{
    private AppCompatImageView card_single_img,card_sub_img;
    private TextView txt_name,txt_location,txt_des,txt_pitch,txt_post,txt_comment;
    private String mainimg,subimg,name,location,des;
    private RelativeLayout rel_pitch,rel_post,rel_comment;
    private LinearLayout ly_pitch,ly_post,ly_comments,ivbackIcon,ivNotify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile_post );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        getIntent_data();
        initview();

        Glide.with(this)
                .asBitmap()
                .load(mainimg)
                .into(card_single_img);

        Glide.with(this)
                .asBitmap()
                .load(subimg)
                .into(card_sub_img);

        txt_name.setText(name);
        txt_location.setText(location);
        txt_des.setText(des);

        load_pitch_fragment();
    }

    private void load_pitch_fragment() {
        rel_pitch.setVisibility( View.VISIBLE );
        rel_post.setVisibility( View.GONE );
        rel_comment.setVisibility( View.GONE );

        txt_post.setTextColor(getResources().getColor(R.color.txtblue));
        txt_comment.setTextColor(getResources().getColor(R.color.txtblue));

        Pitch_Fragment fragInfo = new Pitch_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragInfo);
        fragmentTransaction.commit();
    }

    private void initview() {
        txt_pitch = findViewById(R.id.txt_pitch);
        txt_post = findViewById(R.id.txt_post);
        txt_comment = findViewById(R.id.txt_comment);

        card_single_img = findViewById( R.id.card_single_img);
        card_sub_img  = findViewById( R.id.card_sub_img);
        txt_name = findViewById( R.id.txt_name);
        txt_location =findViewById( R.id.txt_location);
        txt_des = findViewById(R.id.txt_des);

        ly_pitch = findViewById( R.id.ly_pitch);
        ly_post = findViewById( R.id.ly_post);
        ly_comments = findViewById( R.id.ly_comments);

        ivbackIcon = findViewById( R.id.ivbackIcon);

        rel_pitch = findViewById( R.id.rel_pitch);
        rel_post = findViewById( R.id.rel_post);
        rel_comment = findViewById( R.id.rel_comment);

        ly_pitch.setOnClickListener( this );
        ly_post.setOnClickListener( this );
        ly_comments.setOnClickListener( this );
        ivbackIcon.setOnClickListener(this);

        ivNotify = findViewById( R.id.ivNotify);
        ivNotify.setOnClickListener(this);
    }

    private void getIntent_data() {
        Intent data = getIntent();
        mainimg = data.getStringExtra( "mainimg" );
        subimg = data.getStringExtra( "subimg" );
        name = data.getStringExtra( "name" );
        location = data.getStringExtra( "location" );
        des = data.getStringExtra( "des" );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_pitch:
                load_pitch_fragment();
                break;

            case R.id.ly_post:
                load_post_fragment();
                break;

            case R.id.ly_comments:
                load_comments_fragment();
                break;

            case R.id.ivbackIcon:
                finish();
                break;

            case R.id.ivNotify:
              Intent notify = new Intent(Activity_Profile_Post.this,Activity_notifications.class);
              startActivity(notify);
              break;
        }
    }

    private void load_post_fragment() {
      rel_pitch.setVisibility( View.GONE );
      rel_post.setVisibility( View.VISIBLE );
      rel_comment.setVisibility( View.GONE );

        txt_pitch.setTextColor(getResources().getColor(R.color.txtblue));
        txt_post.setTextColor(getResources().getColor(R.color.bodycolor));
        txt_comment.setTextColor(getResources().getColor(R.color.txtblue));


        Post_Fragment fragInfo = new Post_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragInfo);
        fragmentTransaction.commit();
    }

    private void load_comments_fragment() {
        rel_pitch.setVisibility( View.GONE );
        rel_post.setVisibility( View.GONE );
        rel_comment.setVisibility( View.VISIBLE );

        txt_pitch.setTextColor(getResources().getColor(R.color.txtblue));
        txt_post.setTextColor(getResources().getColor(R.color.txtblue));
        txt_comment.setTextColor(getResources().getColor(R.color.bodycolor));

        Comment_Fragment fragInfo = new Comment_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragInfo);
        fragmentTransaction.commit();
    }
}
