package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wallet.speedpe.R;

public class ContactUs extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ContactUs.class.getSimpleName();
    private ImageButton s_facebook, s_youtube, s_instagram, s_twitter, s_sharechat, s_telegram;
    private FloatingActionButton fab_whatsapp, fab_back;
    private TextView txt_web_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.contact_us);

        s_facebook = findViewById(R.id.s_facebook);
        s_facebook = findViewById(R.id.s_facebook);
        s_facebook.setOnClickListener(this);
        s_youtube = findViewById(R.id.s_youtube);
        s_youtube.setOnClickListener(this);
        s_instagram = findViewById(R.id.s_instagram);
        s_instagram.setOnClickListener(this);
        s_twitter = findViewById(R.id.s_twitter);
        s_twitter.setOnClickListener(this);
        s_sharechat = findViewById(R.id.s_sharechat);
        s_sharechat.setOnClickListener(this);
        s_telegram = findViewById(R.id.s_telegram);
        s_telegram.setOnClickListener(this);

        fab_back = findViewById(R.id.fab_back);
        fab_back.setOnClickListener(this);
        fab_whatsapp = findViewById(R.id.fab_whatsapp);
        fab_whatsapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.s_facebook:
                startActivity(getOpenFacebookIntent());
                break;
            case R.id.s_youtube:
                startActivity(getYoutubeIntent());
                break;
            case R.id.s_instagram:
                Uri uri_inst = Uri.parse("https://www.instagram.com/speedpe.official/"); // missing 'http://' will cause crashed
                Intent intent_inst = new Intent(Intent.ACTION_VIEW, uri_inst);
                startActivity(intent_inst);
                break;
            case R.id.s_twitter:
                Uri uri_twi = Uri.parse("https://twitter.com/OfficialSpeedpe?t=N0NiS-QOq6ZIYH3p5vWQAQ&s=09"); // missing 'http://' will cause crashed
                Intent intent_twi = new Intent(Intent.ACTION_VIEW, uri_twi);
                startActivity(intent_twi);
                break;
            case R.id.s_sharechat:
                Uri uri_sh = Uri.parse("https://sharechat.com/profile/2281214271?d=n"); // missing 'http://' will cause crashed
                Intent intent_sh = new Intent(Intent.ACTION_VIEW, uri_sh);
                startActivity(intent_sh);
                break;
            case R.id.s_telegram:
                Uri uri_tel = Uri.parse("https://t.me/+3yetxh1zDKY2Y2Vl"); // missing 'http://' will cause crashed
                Intent intent_tel = new Intent(Intent.ACTION_VIEW, uri_tel);
                startActivity(intent_tel);
                break;
            case R.id.fab_back:
                Intent i = new Intent(ContactUs.this, Activity_Home.class);
                startActivity(i);
                break;
            case R.id.fab_whatsapp:
                Uri uri_wh = Uri.parse("https://api.whatsapp.com/send/?phone=917200005354&text&app_absent=0"); // missing 'http://' will cause crashed
                Intent intent_wh = new Intent(Intent.ACTION_VIEW, uri_wh);
                startActivity(intent_wh);
                break;
        }
    }

    private Intent getYoutubeIntent() {
        try {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC2zfOXwGAbI_iYnEBfjG_1w"));
        }
        catch (Exception e){
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=K4TOrB7at0Y"));
        }
    }

    public Intent getOpenFacebookIntent() {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/111867254705398"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Speedpeofficial-111867254705398"));
        }
    }
}