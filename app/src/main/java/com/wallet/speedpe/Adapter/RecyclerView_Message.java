package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.wallet.speedpe.R;
import com.wallet.speedpe.recyclerViewListClicked;

import java.util.ArrayList;

public class RecyclerView_Message extends RecyclerView.Adapter<RecyclerView_Message.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> imageurls = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> des = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> color = new ArrayList<>();

    private Context mContext;

    private static recyclerViewListClicked itemListeners;

    public RecyclerView_Message(Context context, ArrayList<String> imageurl, ArrayList<String> name, ArrayList<String> dess, ArrayList<String> times,ArrayList<String> colors, recyclerViewListClicked itemListener) {
        imageurls = imageurl;
        names = name;
        des = dess;
        time = times;
        color = colors;
        mContext = context;
        itemListeners = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_message ,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        for (int i=0;i<color.size();i++){
            String colorvalue = color.get(position);

            Glide.with(mContext)
                    .asBitmap()
                    .load(imageurls.get(position))
                    .into(holder.message_img);
            holder.txt_message_name.setText( names.get( position ) );
            holder.txt_message_des.setText( des.get( position ) );

            if (colorvalue=="white")
            {
                holder.ly_cnt.setVisibility(View.GONE);
                holder.txt_message_name.setTextColor(ContextCompat.getColor(mContext,R.color.basecolor_light));
                holder.txt_message_des.setTextColor(ContextCompat.getColor(mContext,R.color.basecolor_light));
                holder.txt_msg_time.setTextColor(ContextCompat.getColor(mContext,R.color.basecolor_light));
                holder.crd_bk.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.colorWhite));
            }else{
                holder.ly_cnt.setVisibility(View.VISIBLE);
                holder.txt_message_name.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite));
                holder.txt_message_des.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite));
                holder.txt_msg_time.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite));
                holder.crd_bk.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.basecolor_light));
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageurls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AppCompatImageView message_img;
        TextView txt_message_name,txt_message_des,txt_msg_time;
        CardView crd_bk;
        LinearLayout ly_cnt;

        public ViewHolder(View itemView) {
            super(itemView);
            message_img = itemView.findViewById(R.id.message_img);
            txt_message_name = itemView.findViewById(R.id.txt_message_name);
            txt_message_des = itemView.findViewById(R.id.txt_message_des);
            txt_msg_time = itemView.findViewById(R.id.txt_msg_time);
            ly_cnt = itemView.findViewById(R.id.ly_cnt);
            crd_bk = itemView.findViewById(R.id.crd_bk);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListeners.recyclerViewListClicked(view, this.getPosition());
        }
    }
}