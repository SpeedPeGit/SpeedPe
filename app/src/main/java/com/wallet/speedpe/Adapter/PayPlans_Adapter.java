package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wallet.speedpe.Model.Pay_Plans_Items;
import com.wallet.speedpe.R;

import java.util.ArrayList;
import java.util.List;

public class PayPlans_Adapter extends RecyclerView.Adapter<PayPlans_Adapter.ViewHolder> {
    private List<Pay_Plans_Items> pay_Plans_Items = new ArrayList<>();
    private Context context;

    // RecyclerView recyclerView;
    public PayPlans_Adapter(Context context, List<Pay_Plans_Items> pay_Plans_Items) {
        this.context = context;
        this.pay_Plans_Items = pay_Plans_Items;
    }

    @NonNull
    @Override
    public PayPlans_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.pay_plans_list, parent, false);
        PayPlans_Adapter.ViewHolder viewHolder = new PayPlans_Adapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PayPlans_Adapter.ViewHolder holder, final int position) {
        if (pay_Plans_Items.get(position).getPay_name().toLowerCase().contains("welcome")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.welcom_plan)
                        .into(holder.pay_logo);
            }
        }
        else if (pay_Plans_Items.get(position).getPay_name().toLowerCase().contains("bronze")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.bronze_plan)
                        .into(holder.pay_logo);
            }
        }
        else if (pay_Plans_Items.get(position).getPay_name().toLowerCase().contains("silver")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.silver_plan)
                        .into(holder.pay_logo);
            }
        }
        else if (pay_Plans_Items.get(position).getPay_name().toLowerCase().contains("gold")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.gold_plan)
                        .into(holder.pay_logo);
            }
        }
        else {
            Glide.with(context)
                    .load(R.drawable.welcom_plan)
                    .into(holder.pay_logo);
        }

        if (pay_Plans_Items.get(position).getPay_name().equalsIgnoreCase("null")) {
            holder.pay_plan_name.setText("");
        } else {
            holder.pay_plan_name.setText(pay_Plans_Items.get(position).getPay_name());
        }
        if (pay_Plans_Items.get(position).getPay_date().equalsIgnoreCase("null")) {
            holder.pay_date.setText("");
        } else {
            holder.pay_date.setText(pay_Plans_Items.get(position).getPay_date());
        }
        if (pay_Plans_Items.get(position).getPay_year().equalsIgnoreCase("null")) {
            holder.pay_year.setText("");
        } else {
            holder.pay_year.setText(pay_Plans_Items.get(position).getPay_year());
        }
        if (pay_Plans_Items.get(position).getPay_amt().equalsIgnoreCase("null")) {
            holder.pay_amt.setText("");
        } else {
            holder.pay_amt.setText(pay_Plans_Items.get(position).getPay_amt());
        }
    }
    
    @Override
    public int getItemCount() {
        return pay_Plans_Items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pay_logo;
        private TextView pay_plan_name, pay_date, pay_year, pay_amt;

        public ViewHolder(View itemView) {
            super(itemView);
            this.pay_plan_name = itemView.findViewById(R.id.pay_plan_name);
            this.pay_date = itemView.findViewById(R.id.pay_date);
            this.pay_year = itemView.findViewById(R.id.pay_year);
            this.pay_amt = itemView.findViewById(R.id.pay_amt);
            this.pay_logo = itemView.findViewById(R.id.pay_logo);
        }
    }
}