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

public class RecyclerView_Activties extends RecyclerView.Adapter<RecyclerView_Activties.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> img_url = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> summarys = new ArrayList<>();
    private Context mContext;

    private int row_index;
    public RecyclerView_Activties(Context context,ArrayList<String> activity_img_url,ArrayList<String> name,ArrayList<String> summary) {
        img_url = activity_img_url;
        names = name;
        summarys = summary;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_activities_frame, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(img_url.get(position))
                .into(holder.activities_post_img);

        holder.txt_activity_post_name.setText( names.get( position ) );
        holder.txt_activity_des.setText( summarys.get( position ) );
    }

    @Override
    public int getItemCount() {
        return summarys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView activities_post_img;
        TextView txt_activity_post_name,txt_activity_des;

        public ViewHolder(View itemView) {
            super(itemView);
            activities_post_img = itemView.findViewById(R.id.activities_post_img);
            txt_activity_post_name = itemView.findViewById(R.id.txt_activity_post_name);
            txt_activity_des = itemView.findViewById(R.id.txt_activity_des);
        }
    }
}