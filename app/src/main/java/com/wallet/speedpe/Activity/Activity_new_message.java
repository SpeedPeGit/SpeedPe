package com.wallet.speedpe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wallet.speedpe.Adapter.RecyclerView_New_Message;
import com.wallet.speedpe.R;
import com.wallet.speedpe.recyclerViewListClicked;
import java.util.ArrayList;

public class Activity_new_message extends AppCompatActivity implements View.OnClickListener {
    //vars
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> des = new ArrayList<>();

    private LinearLayout ivbackIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmsg);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();
        ivbackIcon = findViewById(R.id.ivbackIcon);
        ivbackIcon.setOnClickListener(this);
        init_card_prev();
    }

    private void init_card_prev() {
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");

        name.add("Akash");
        name.add("Prajith");
        name.add("Zayn");
        name.add("Sajesh");
        name.add("Abhishek");
        name.add("Sanjay");

        des.add("Need groceries or food delivered?get it Done!");
        des.add("Need groceries or food delivered?get it Done!");
        des.add("Need groceries or food delivered?get it Done!");
        des.add("Need groceries or food delivered?get it Done!");
        des.add("Need groceries or food delivered?get it Done!");
        des.add("Need groceries or food delivered?get it Done!");

        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_New_Message adapter = new RecyclerView_New_Message(this, mImageUrls, name, des, new recyclerViewListClicked() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                String url = mImageUrls.get(position);
                String nam = name.get(position);
                String decrip = des.get(position);
                Intent newchat = new Intent(Activity_new_message.this,Actvity_newchat.class);
                newchat.putExtra("url",url);
                newchat.putExtra("nam",nam);
                newchat.putExtra("des",decrip);
                newchat.putExtra("id","2");
                startActivity(newchat);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ivbackIcon:
                finish();
                break;
        }
    }
}
