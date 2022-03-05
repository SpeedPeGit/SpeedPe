package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class RecyclerView_Feed_Post extends RecyclerView.Adapter<RecyclerView_Feed_Post.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> sImageUrls = new ArrayList<>();
    private ArrayList<String> summarys = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> dess = new ArrayList<>();

    private Context mContext;

    private int row_index;
    public RecyclerView_Feed_Post(Context context, ArrayList<String> mImageUrl, ArrayList<String> sImageUrl, ArrayList<String> summary
            , ArrayList<String> name, ArrayList<String> des) {
        mImageUrls = mImageUrl;
        sImageUrls = sImageUrl;
        summarys = summary;
        names = name;
        dess = des;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_feed_post_frame, parent, false);
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
                .into(holder.feed_post_img);

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
        holder.txt_feed_post_name.setText( names.get( position ) );
        holder.txt_reply_des.setText( dess.get( position ) );
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView post_img,card_single_img,feed_post_img,follow_img,follow_img1,follow_img2;
        TextView txt_des,txt_feed_post_name,txt_reply_des;

        public ViewHolder(View itemView) {
            super(itemView);
            post_img = itemView.findViewById(R.id.post_img);
            card_single_img = itemView.findViewById(R.id.card_single_img);
            txt_des = itemView.findViewById(R.id.txt_des);
            feed_post_img = itemView.findViewById(R.id.feed_post_img);
            txt_feed_post_name = itemView.findViewById(R.id.txt_feed_post_name);
            txt_reply_des = itemView.findViewById(R.id.txt_reply_des);
            follow_img = itemView.findViewById(R.id.follow_img);
            follow_img1 = itemView.findViewById(R.id.follow_img1);
            follow_img2 = itemView.findViewById(R.id.follow_img2);
        }
    }
}