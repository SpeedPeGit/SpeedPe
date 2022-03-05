package com.wallet.speedpe.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int prevNav,currentNav;
    private FloatingActionButton fab;
    BottomNavigationView navigationView;
    DrawerLayout drawer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            prevNav = getSelectedNav();
            currentNav = item.getItemId();
            if (currentNav == prevNav)
                return false;
            switch (item.getItemId()) {
                case R.id.bottom_wallet:
                    Intent ii1 = new Intent( BaseActivity.this, Activity_Wallet.class);
                    startActivity(ii1);
                    return true;
                case R.id.bottom_plans:
                    Intent ii2 = new Intent( BaseActivity.this, Activity_Plans.class);
                    startActivity(ii2);
                    return true;
                case R.id.bottom_payments:
                    Intent ii4 = new Intent( BaseActivity.this, Activity_Payments.class);
                    startActivity(ii4);
                    return true;
                case R.id.bottom_more:
                    drawer.openDrawer(Gravity.LEFT);
                    return true;
            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                /*final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    R.id.lay_welcome.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.left_drawer_welcome));
                } else {*/
//                R.id.lay_welcome  setBackground(ContextCompat.getDrawable(this, R.drawable.left_drawer_welcome));
//                }
//                drawable.setColorFilter(getResources().getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharepost = new Intent(BaseActivity.this, Activity_Home.class);
                startActivity( sharepost );
            }
        } );

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null); // SET MENU ICONS TO ORIGINAL IMAGES
        navigationView.setNavigationItemSelectedListener(this);
    }

    public View setContentLayout(int layoutID)
    {
        FrameLayout contentLayout = (FrameLayout) findViewById(R.id.content_layout);
        LayoutInflater inflater = (LayoutInflater)getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(layoutID, contentLayout, true);
    }


    public void setSelected(int optionID)
    {
        navigationView.getMenu().findItem(optionID).setChecked(true);
        getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().putInt("selectedNav",optionID).commit();
    }

    public int getSelectedNav()
    {
        return getSharedPreferences(getPackageName(), MODE_PRIVATE).getInt("selectedNav", R.id.bottom_wallet);
    }

    private void confirm_logout() {
        androidx.appcompat.app.AlertDialog alertbox = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setMessage("Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        Config.setStringPreference(BaseActivity.this, Constant.IS_LOGED_IN, "false");
                        Intent lo = new Intent(BaseActivity.this, Activity_login.class);
                        startActivity(lo);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.lay_welcome:
                Intent i = new Intent(BaseActivity.this, Activity_Home.class);
                startActivity(i);
                break;
            case R.id.lay_wallet:
                Intent w = new Intent(BaseActivity.this, Activity_Wallet.class);
                startActivity(w);
                break;
            case R.id.lay_plan:
                Intent p = new Intent(BaseActivity.this, Activity_Plans.class);
                startActivity(p);
                break;
            case R.id.lay_referralCode:
                Intent ref = new Intent(BaseActivity.this, Activity_Referral.class);
                startActivity(ref);
                break;
            case R.id.lay_aboutus:
                Intent a = new Intent(BaseActivity.this, AboutUs.class);
                startActivity(a);
                break;
            case R.id.lay_faq:
                Intent faq = new Intent(BaseActivity.this, FAQ.class);
                startActivity(faq);
                break;
            case R.id.lay_contact_us:
                Intent contact_us = new Intent(BaseActivity.this, ContactUs.class);
                startActivity(contact_us);
                break;
            case R.id.lay_support:
                Intent ca = new Intent(Intent.ACTION_DIAL);
                String ph = "tel:" + Constant.CALL_SUPPORT_NUMBER;
                ca.setData(Uri.parse(ph));
                startActivity(ca);
                break;
            case R.id.lay_logout:
                confirm_logout();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}