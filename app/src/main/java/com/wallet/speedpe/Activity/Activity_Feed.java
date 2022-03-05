package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wallet.speedpe.Adapter.RecyclerViewAdapter;
import com.wallet.speedpe.Adapter.RecyclerView_Category;
import com.wallet.speedpe.Adapter.RecyclerView_Feed_Post;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class Activity_Feed extends BaseActivity implements OnClickListener {
    private static final String TAG = "MainActivity";
    //vars
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mImageUrl = new ArrayList<>();
    private ArrayList<String> category = new ArrayList<>();
    private ArrayList<String> summary = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> des = new ArrayList<>();
    private ArrayList<String> sImageUrls = new ArrayList<>();

    private LinearLayout ivNotify,ivbackIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentLayout( R.layout.activity_feed );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        ivbackIcon = findViewById(R.id.ivbackIcon);
        ivNotify = findViewById(R.id.ivNotify);

        ivbackIcon.setOnClickListener(this);
        ivNotify.setOnClickListener(this);

        init_card_prev();
        init_card_category();
        init_data();
    }

    private void init_data() {
        mImageUrl.add("https://i.redd.it/glin0nwndo501.jpg");
        mImageUrl.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mImageUrl.add("https://i.redd.it/qn7f9oqu7o501.jpg");

        sImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        sImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        sImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");


        summary.add("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy");
        summary.add("Industries fail to utilize drones and aerial vehicles efficietly, securely and quickly for industrial applications. As a result, we can’t increase the efficiency of human laborDrones become inefficient and primitive tools in the hands of a human operator, in terms of both in-flight and post-opera tion data handling. This limits the potential of aerial vehi- clees to maximize revenues in many industries such as energy, agriculture, security and more");
        summary.add("Industries fail to utilize drones and aerial vehicles efficietly, securely and quickly for industrial applications. As a result, we can’t increase the efficiency of human laborDrones become inefficient and primitive tools in the hands of a human operator, in terms of both in-flight and post-opera tion data handling. This limits the potential of aerial vehi- clees to maximize revenues in many industries such as energy, agriculture, security and more");

        name.add("Mithoon");
        name.add("Mithoon");
        name.add("Mithoon");

        des.add("Hope your concepts of gaming attracts... investors like me.");
        des.add("Hope your concepts of gaming attracts... investors like me.");
        des.add("Hope your concepts of gaming attracts... investors like me.");

        init_post();
    }

    private void init_post() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_feed );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_Feed_Post adapter = new RecyclerView_Feed_Post(this,mImageUrl,sImageUrls,summary,name,des);
        recyclerView.setAdapter(adapter);
    }

    private void init_card_category() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        category.add("Technology");
        category.add("Agriculture");
        category.add("Cosmetics");
        initRecyclerView_category();
    }

    private void initRecyclerView_category() {
        Log.d(TAG, "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_Category adapter = new RecyclerView_Category(this,category);
        recyclerView.setAdapter(adapter);
    }

    private void init_card_prev() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_start_prev );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mImageUrls);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.bottom_plans);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ivbackIcon:
                finish();
                break;

            case R.id.ivNotify:
                Intent notify = new Intent(Activity_Feed.this,Activity_notifications.class);
                startActivity(notify);
                break;
        }
    }
}
