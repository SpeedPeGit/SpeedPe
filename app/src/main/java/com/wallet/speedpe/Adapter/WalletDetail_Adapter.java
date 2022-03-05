package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wallet.speedpe.Model.WalletDetail_Items;
import com.wallet.speedpe.R;

import java.util.ArrayList;
import java.util.List;

public class WalletDetail_Adapter extends RecyclerView.Adapter<WalletDetail_Adapter.ViewHolder> {
    private List<WalletDetail_Items> walletDetail_Items = new ArrayList<>();
    private Context context;

    // RecyclerView recyclerView;
    public WalletDetail_Adapter(Context context, List<WalletDetail_Items> walletDetail_Items) {
        this.context = context;
        this.walletDetail_Items = walletDetail_Items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.wallet_datalist, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (position == 0){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.wa1)
                        .into(holder.wa_inner_img);
            }
        }
        else if (position == 1){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.wa2)
                        .into(holder.wa_inner_img);
            }
        }
        else if (position == 2){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.wa3)
                        .into(holder.wa_inner_img);
            }
        }
        else if (position == 3){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.wa4)
                        .into(holder.wa_inner_img);
            }
        }
        else if (position == 4){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.wa5)
                        .into(holder.wa_inner_img);
            }
        }
        else {
            Glide.with(context)
                    .load(R.drawable.icon)
                    .into(holder.wa_inner_img);
        }

        if (walletDetail_Items.get(position).getWa_lt().equalsIgnoreCase("null")) {
            holder.wa_leftTop.setText("");
        } else {
            holder.wa_leftTop.setText(walletDetail_Items.get(position).getWa_lt());
        }
        if (walletDetail_Items.get(position).getWa_lb().equalsIgnoreCase("null")) {
            holder.wa_leftBottom.setText("");
        } else {
            holder.wa_leftBottom.setText(walletDetail_Items.get(position).getWa_lb());
        }
        if (walletDetail_Items.get(position).getWa_rt().equalsIgnoreCase("null")) {
            holder.wa_rightTop.setText("");
        } else {
            holder.wa_rightTop.setText(walletDetail_Items.get(position).getWa_rt());
        }
        if (walletDetail_Items.get(position).getWa_rb().equalsIgnoreCase("null")) {
            holder.wa_rightBottom.setText("");
        } else {
            holder.wa_rightBottom.setText(walletDetail_Items.get(position).getWa_rb());
        }
    }

    @Override
    public int getItemCount() {
        return walletDetail_Items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView wa_inner_img;
        private TextView wa_leftTop, wa_leftBottom, wa_rightTop, wa_rightBottom;
        private ImageButton btn_wa_scan;

        public ViewHolder(View itemView) {
            super(itemView);
            this.wa_leftTop = itemView.findViewById(R.id.wa_leftTop);
            this.wa_leftBottom = itemView.findViewById(R.id.wa_leftBottom);
            this.wa_rightTop = itemView.findViewById(R.id.wa_rightTop);
            this.wa_rightBottom = itemView.findViewById(R.id.wa_rightBottom);
            this.wa_inner_img = itemView.findViewById(R.id.wa_inner_img);
        }
    }
}