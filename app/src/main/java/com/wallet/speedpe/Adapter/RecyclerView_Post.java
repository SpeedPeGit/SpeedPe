package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class RecyclerView_Post extends RecyclerView.Adapter<RecyclerView_Post.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> sImageUrls = new ArrayList<>();
    private ArrayList<String> summarys = new ArrayList<>();

    private Context mContext;

    private int row_index;
    public RecyclerView_Post(Context context, ArrayList<String> mImageUrl, ArrayList<String> sImageUrl, ArrayList<String> summary) {
        mImageUrls = mImageUrl;
        sImageUrls = sImageUrl;
        summarys = summary;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_post_frame, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.post_img);

        Glide.with(mContext)
                .asBitmap()
                .load(sImageUrls.get(position))
                .into(holder.card_single_img);

        Glide.with(mContext)
                .asBitmap()
                .load(sImageUrls.get(position))
                .into(holder.follow_img);

        Glide.with(mContext)
                .asBitmap()
                .load(sImageUrls.get(position))
                .into(holder.follow_img1);

        Glide.with(mContext)
                .asBitmap()
                .load(sImageUrls.get(position))
                .into(holder.follow_img2);

        holder.txt_des.setText( summarys.get( position ) );
        holder.lay_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = summarys.get( position );
                String shareSub = "Makeinindia";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        holder.ley_grow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView post_img,card_single_img,follow_img,follow_img1,follow_img2;
        TextView txt_des;
        MaterialCardView lay_share,ley_grow;

        public ViewHolder(View itemView) {
            super(itemView);
            post_img = itemView.findViewById(R.id.post_img);
            card_single_img = itemView.findViewById(R.id.card_single_img);
            txt_des = itemView.findViewById(R.id.txt_des);
            follow_img = itemView.findViewById(R.id.follow_img);
            follow_img1 = itemView.findViewById(R.id.follow_img1);
            follow_img2 = itemView.findViewById(R.id.follow_img2);
            lay_share = itemView.findViewById(R.id.mt_share);
            ley_grow = itemView.findViewById(R.id.mt_grow);
        }
    }
}