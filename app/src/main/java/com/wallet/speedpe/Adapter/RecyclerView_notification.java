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

public class RecyclerView_notification extends RecyclerView.Adapter<RecyclerView_notification.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> imageurls = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> des = new ArrayList<>();
    private Context mContext;

    public RecyclerView_notification(Context context, ArrayList<String> imageurl, ArrayList<String> name, ArrayList<String> dess) {
        imageurls = imageurl;
        names = name;
        des = dess;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_notification ,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext)
                .asBitmap()
                .load(imageurls.get(position))
                .into(holder.message_img);
        holder.txt_message_name.setText( names.get( position ) );
        holder.txt_message_des.setText( des.get( position ) );
    }

    @Override
    public int getItemCount() {
        return imageurls.size();
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