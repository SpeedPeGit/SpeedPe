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
import com.wallet.speedpe.Model.Plans_Items;
import com.wallet.speedpe.PlanDetails;
import com.wallet.speedpe.R;

import java.util.ArrayList;
import java.util.List;

public class Plans_Adapter extends RecyclerView.Adapter<Plans_Adapter.ViewHolder> {
    private PlanDetails pdListener;
    private List<Plans_Items> plans_Items = new ArrayList<>();
    private Context context;

    // RecyclerView recyclerView;
    public Plans_Adapter(Context context, List<Plans_Items> plans_Items, PlanDetails planDetails) {
        this.context = context;
        this.plans_Items = plans_Items;
        this.pdListener = planDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.plan_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (plans_Items.get(position).getPl_name().toLowerCase().contains("welcome")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.welcom_plan)
                        .into(holder.pl_Img);
            }
        }
        else if (plans_Items.get(position).getPl_name().toLowerCase().contains("bronze")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.bronze_plan)
                        .into(holder.pl_Img);
            }
        }
        else if (plans_Items.get(position).getPl_name().toLowerCase().contains("silver")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.silver_plan)
                        .into(holder.pl_Img);
            }
        }
        else if (plans_Items.get(position).getPl_name().toLowerCase().contains("gold")){
            if (context != null) {
                Glide.with(context)
                        .load(R.drawable.gold_plan)
                        .into(holder.pl_Img);
            }
        }
        else {
            Glide.with(context)
                    .load(R.drawable.welcom_plan)
                    .into(holder.pl_Img);
        }

        if (plans_Items.get(position).getPl_name().equalsIgnoreCase("null")) {
            holder.pl_plan_name.setText("");
        } else {
            holder.pl_plan_name.setText(plans_Items.get(position).getPl_name());
        }
        if (plans_Items.get(position).getPl_mrp().equalsIgnoreCase("null")) {
            holder.pl_mrp.setText("");
        } else {
            holder.pl_mrp.setText(plans_Items.get(position).getPl_mrp());
        }
        if (plans_Items.get(position).getPl_totalquota().equalsIgnoreCase("null")) {
            holder.pl_credit.setText("");
        } else {
            holder.pl_credit.setText(plans_Items.get(position).getPl_totalquota());
        }
        if (plans_Items.get(position).getPl_cashback().equalsIgnoreCase("null")) {
            holder.pl_cash_back.setText("");
        } else {
            holder.pl_cash_back.setText(plans_Items.get(position).getPl_cashback());
        }
        holder.btn_pl_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callShowPlanDetails(plans_Items.get(position).getPl_id());
            }
        });
    }

    private void callShowPlanDetails(String pid) {
        pdListener.ShowPlanDetail(pid);
    }

    @Override
    public int getItemCount() {
        return plans_Items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pl_Img;
        private TextView pl_plan_name, pl_mrp, pl_credit, pl_cash_back;
        private ImageButton btn_pl_buy;

        public ViewHolder(View itemView) {
            super(itemView);
            this.pl_plan_name = itemView.findViewById(R.id.pl_plan_name);
            this.pl_mrp = itemView.findViewById(R.id.pl_mrp);
            this.pl_credit = itemView.findViewById(R.id.pl_credit);
            this.pl_cash_back = itemView.findViewById(R.id.pl_cash_back);
            this.btn_pl_buy = itemView.findViewById(R.id.btn_pl_buy);
            this.pl_Img = itemView.findViewById(R.id.pl_Img);
        }
    }
}
