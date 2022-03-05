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
import com.wallet.speedpe.AvailableAmount;
import com.wallet.speedpe.Model.Wallet_Items;
import com.wallet.speedpe.R;
import com.wallet.speedpe.Utils.Config;
import com.wallet.speedpe.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class Wallet_Adapter extends RecyclerView.Adapter<Wallet_Adapter.ViewHolder> {
    private AvailableAmount pdListener;
    private List<Wallet_Items> wallet_Items = new ArrayList<>();
    private Context context;

    // RecyclerView recyclerView;
    public Wallet_Adapter(Context context, List<Wallet_Items> wallet_Items, AvailableAmount planDetails) {
        this.context = context;
        this.wallet_Items = wallet_Items;
        this.pdListener = planDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.wallet_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (wallet_Items.get(position).getWa_name().toLowerCase().contains("welcome")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.welcom_plan)
                        .into(holder.wa_Img);
            }
        }
        else if (wallet_Items.get(position).getWa_name().toLowerCase().contains("bronze")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.bronze_plan)
                        .into(holder.wa_Img);
            }
        }
        else if (wallet_Items.get(position).getWa_name().toLowerCase().contains("silver")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.silver_plan)
                        .into(holder.wa_Img);
            }
        }
        else if (wallet_Items.get(position).getWa_name().toLowerCase().contains("gold")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.gold_plan)
                        .into(holder.wa_Img);
            }
        }
        else {
            Glide.with(context)
                    .load(R.drawable.welcom_plan)
                    .into(holder.wa_Img);
        }

        if (wallet_Items.get(position).getWa_name().equalsIgnoreCase("null")) {
            holder.wa_plan_name.setText("");
        } else {
            holder.wa_plan_name.setText(wallet_Items.get(position).getWa_name());
        }
        if (wallet_Items.get(position).getWa_mrp().equalsIgnoreCase("null")) {
            holder.wa_todaysLimit.setText("");
        } else {
            holder.wa_todaysLimit.setText(wallet_Items.get(position).getWa_mrp());
        }
        if (wallet_Items.get(position).getWa_totalquota().equalsIgnoreCase("null")) {
            holder.wa_total.setText("");
        } else {
            holder.wa_total.setText(wallet_Items.get(position).getWa_totalquota());
        }
        if (wallet_Items.get(position).getWa_cashback().equalsIgnoreCase("null")) {
            holder.wa_available.setText("");
        } else {
            holder.wa_available.setText(wallet_Items.get(position).getWa_cashback());
        }
        holder.btn_wa_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.setStringPreference(context, Constant.SHOULD_SCAN,"YES");
                String str = wallet_Items.get(position).getWa_mrp().replace("₹","");
                callShowPlanDetails(String.valueOf(wallet_Items.get(position).getWa_id()), Integer.parseInt(str));
            }
        });
        holder.btn_pl_buy_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.setStringPreference(context, Constant.SHOULD_SCAN,"NO");
                String str = wallet_Items.get(position).getWa_mrp().replace("₹","");
                callShowPlanDetails(String.valueOf(wallet_Items.get(position).getWa_id()), Integer.parseInt(str));
            }
        });
    }

    private void callShowPlanDetails(String pid, Integer amt) {
        pdListener.AvailAmount(pid,amt);
    }

    @Override
    public int getItemCount() {
        return wallet_Items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView wa_Img;
        private TextView wa_plan_name, wa_todaysLimit, wa_total, wa_available;
        private ImageButton btn_wa_scan, btn_pl_buy_scan;

        public ViewHolder(View itemView) {
            super(itemView);
            this.wa_plan_name = itemView.findViewById(R.id.wa_plan_name);
            this.wa_todaysLimit = itemView.findViewById(R.id.wa_todaysLimit);
            this.wa_total = itemView.findViewById(R.id.wa_total);
            this.wa_available = itemView.findViewById(R.id.wa_available);
            this.btn_wa_scan = itemView.findViewById(R.id.btn_wa_scan);
            this.btn_pl_buy_scan = itemView.findViewById(R.id.btn_pl_buy_scan);
            this.wa_Img = itemView.findViewById(R.id.wa_Img);
        }
    }
}