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

public class RecyclerView_Comment extends RecyclerView.Adapter<RecyclerView_Comment.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> sImageUrls = new ArrayList<>();
    private ArrayList<String> summarys = new ArrayList<>();

    private Context mContext;

    private int row_index;
    public RecyclerView_Comment(Context context,ArrayList<String> sImageUrl, ArrayList<String> summary) {
        sImageUrls = sImageUrl;
        summarys = summary;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_comment_frame, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(sImageUrls.get(position))
                .into(holder.post_img);

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

        holder.txt_des.setText(summarys.get( position ) );
    }

    @Override
    public int getItemCount() {
        return sImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView post_img,follow_img,follow_img1,follow_img2;
        TextView txt_des;

        public ViewHolder(View itemView) {
            super(itemView);
            post_img = itemView.findViewById(R.id.post_img);
            follow_img = itemView.findViewById(R.id.follow_img);
            follow_img1 = itemView.findViewById(R.id.follow_img1);
            follow_img2 = itemView.findViewById(R.id.follow_img2);
            txt_des = itemView.findViewById(R.id.txt_des);
        }
    }
}