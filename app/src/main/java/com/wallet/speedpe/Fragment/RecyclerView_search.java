package com.wallet.speedpe.Fragment;

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

public class RecyclerView_search extends RecyclerView.Adapter<RecyclerView_search.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> mImageUrls= new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> summarys = new ArrayList<>();
    private Context mContext;

    private int row_index;
    public RecyclerView_search(Context context,ArrayList<String> mImageUrl,ArrayList<String> name,ArrayList<String> summary) {
        mImageUrls = mImageUrl;
        names = name;
        summarys = summary;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.message_img);

        holder.txt_message_name.setText( names.get( position ) );

        holder.txt_message_des.setText( summarys.get( position ) );
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public void updateList(ArrayList<String> list){
        names = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView message_img;
        TextView txt_message_name,txt_message_des;

        public ViewHolder(View itemView) {
            super(itemView);
            message_img = itemView.findViewById(R.id.message_img);
            txt_message_name = itemView.findViewById(R.id.txt_message_name);
            txt_message_des = itemView.findViewById(R.id.txt_message_des);
        }
    }
}