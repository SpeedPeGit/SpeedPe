package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.wallet.speedpe.Activity.Activity_preview;
import com.wallet.speedpe.R;
import com.wallet.speedpe.recyclerViewListClicked;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;


    private static recyclerViewListClicked itemListeners;

    public RecyclerViewAdapter(Context context,ArrayList<String> imageUrls) {
        mImageUrls = imageUrls;
        mContext = context;
    }

    public RecyclerViewAdapter(Context context,ArrayList<String> imageUrls,recyclerViewListClicked itemListener) {
        mImageUrls = imageUrls;
        mContext = context;
        itemListeners = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.cardview_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.card_main_img);

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.card_sub_img);

        holder.card_main_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newchat = new Intent(mContext, Activity_preview.class);
                newchat.putExtra("url",mImageUrls.get(position));
                mContext.startActivity(newchat);
            }
        });

        holder.card_sub_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newchat = new Intent(mContext, Activity_preview.class);
                newchat.putExtra("url",mImageUrls.get(position));
                mContext.startActivity(newchat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AppCompatImageView card_main_img,card_sub_img;

        public ViewHolder(View itemView) {
            super(itemView);
            card_main_img = itemView.findViewById(R.id.card_main_imgs);
            card_sub_img = itemView.findViewById(R.id.card_sub_img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListeners.recyclerViewListClicked(view, this.getPosition());
        }
    }
}