package com.wallet.speedpe.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wallet.speedpe.Adapter.CartListAdapter;
import com.wallet.speedpe.Adapter.RecyclerView_notification;
import com.wallet.speedpe.Model.Item;
import com.wallet.speedpe.MyApplication;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.RecyclerItemTouchHelper;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class Activity_notifications extends AppCompatActivity implements View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private CardView crd_bck_startup,crd_bck_feed;
    private TextView txt_startup,txt_feed;
    //vars
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> des = new ArrayList<>();
    private static final String TAG = Activity_notifications.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Item> cartList;
    private CartListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout ivbackIcon;
    // url to fetch menu json
    private static final String URL = "https://api.androidhive.info/json/menu.json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notify );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = findViewById(R.id.recycler_category);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        cartList = new ArrayList<>();
        mAdapter = new CartListAdapter(this, cartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        prepareCart();
        initview();
/*
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");

        name.add("Akash");
        name.add("Prajith");
        name.add( "Zayn" );
        name.add("Sajesh");
        name.add("Abhishek");
        name.add( "Sanjay" );

        des.add( "Need groceries or food delivered?get it Done!" );
        des.add( "Need groceries or food delivered?get it Done!" );
        des.add( "Need groceries or food delivered?get it Done!" );
        des.add( "Need groceries or food delivered?get it Done!" );
        des.add( "Need groceries or food delivered?get it Done!" );
        des.add( "Need groceries or food delivered?get it Done!" );

        initrecyclerview_startup();*/
    }

    private void prepareCart() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading notification, please wait.");
        dialog.show();
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                          //  Toast.makeText(getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            return;
                        }

                        List<Item> items = new Gson().fromJson(response.toString(), new TypeToken<List<Item>>() {
                        }.getType());

                        // adding items to cart list
                        dialog.dismiss();
                        cartList.clear();
                        cartList.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
            //    Log.d(TAG, "Error: " + error.getMessage());
           /*     Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();*/
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = cartList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor( Color.YELLOW);
            snackbar.show();
        }
    }


    private void initview() {
        crd_bck_startup = findViewById( R.id.crd_bck_startup );
        crd_bck_feed = findViewById( R.id.crd_bck_feed );
        txt_startup = findViewById( R.id.txt_startup);
        txt_feed = findViewById( R.id.txt_feed );

        crd_bck_startup.setCardBackgroundColor( ContextCompat.getColor(this, R.color.basecolor_light) );
        txt_startup.setTextColor( ContextCompat.getColor(this, R.color.colorWhite) );
        crd_bck_feed.setCardBackgroundColor( ContextCompat.getColor(this, R.color.colorWhite) );
        txt_feed.setTextColor( ContextCompat.getColor(this, R.color.basecolor_light) );

        crd_bck_startup.setOnClickListener( this );
        crd_bck_feed.setOnClickListener( this );

        ivbackIcon = findViewById(R.id.ivbackIcon);
        ivbackIcon.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.crd_bck_startup:
                crd_bck_startup.setCardBackgroundColor( ContextCompat.getColor(this, R.color.basecolor_light) );
                txt_startup.setTextColor( ContextCompat.getColor(this, R.color.colorWhite) );
                crd_bck_feed.setCardBackgroundColor( ContextCompat.getColor(this, R.color.colorWhite) );
                txt_feed.setTextColor( ContextCompat.getColor(this, R.color.basecolor_light) );
               /* initrecyclerview_startup();*/
                prepareCart();
                break;

            case R.id.crd_bck_feed:
                crd_bck_startup.setCardBackgroundColor( ContextCompat.getColor(this, R.color.colorWhite) );
                txt_startup.setTextColor( ContextCompat.getColor(this, R.color.basecolor_light) );
                crd_bck_feed.setCardBackgroundColor( ContextCompat.getColor(this, R.color.basecolor_light) );
                txt_feed.setTextColor( ContextCompat.getColor(this, R.color.colorWhite) );
                /*initrecyclerview_feed();*/
                prepareCart();
                break;

            case R.id.ivbackIcon:
                finish();
                break;

        }
    }

    private void initrecyclerview_startup() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_notification adapter = new RecyclerView_notification(this,mImageUrls,name,des);
        recyclerView.setAdapter(adapter);
    }

    private void initrecyclerview_feed() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_notification adapter = new RecyclerView_notification(this,mImageUrls,name,des);
        recyclerView.setAdapter(adapter);
    }
}
