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
import com.wallet.speedpe.Model.RecentPayments_Items;
import com.wallet.speedpe.R;

import java.util.ArrayList;
import java.util.List;

public class RecentPayments_Adapter extends RecyclerView.Adapter<RecentPayments_Adapter.ViewHolder> {

    private List<RecentPayments_Items> recentPayments_items=new ArrayList<>();
    private Context context;

    // RecyclerView recyclerView;
    public RecentPayments_Adapter(Context context, List<RecentPayments_Items> recentPayments_items) {
        this.context = context;
        this.recentPayments_items = recentPayments_items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recent_payment_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (recentPayments_items.get(position).getPayment_img()!=null) {
            if(recentPayments_items.get(position).getPayment_img().equalsIgnoreCase("")){
                Glide.with(context)
                        .load(R.drawable.icon)
                        .into(holder.rp_logo);
            }
            else {
                if (context != null) {
                    Glide.with(context)
                            .load(context.getResources()
                                    .getIdentifier(recentPayments_items.get(position).getPayment_img(), "drawable", context.getPackageName()))
                            .into(holder.rp_logo);
                }
            }
        }
        else {
            Glide.with(context)
                .load(R.drawable.icon)
                .into(holder.rp_logo);
        }
        if (recentPayments_items.get(position).getBunk_name().equalsIgnoreCase("null")){ holder.rp_pump_name.setText("");   }
        else{   holder.rp_pump_name.setText(recentPayments_items.get(position).getBunk_name()); }
        if (recentPayments_items.get(position).getPayment_date().equalsIgnoreCase("null")){ holder.rp_date.setText("");   }
        else{   holder.rp_date.setText(recentPayments_items.get(position).getPayment_date()); }
        if (recentPayments_items.get(position).getPayment_amt().equalsIgnoreCase("null")){ holder.rp_amt.setText("");   }
        else{   holder.rp_amt.setText(recentPayments_items.get(position).getPayment_amt()); }
        if (recentPayments_items.get(position).getPayment_type().equalsIgnoreCase("null")){ holder.rp_type.setText("");   }
        else{   holder.rp_type.setText(recentPayments_items.get(position).getPayment_type()); }
    }

    @Override
    public int getItemCount() {
        return recentPayments_items.size();
//        return bunkname.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView rp_logo;
        private TextView rp_pump_name, rp_date, rp_amt, rp_type;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rp_logo = itemView.findViewById(R.id.rp_logo);
            this.rp_pump_name = itemView.findViewById(R.id.rp_pump_name);
            this.rp_date = itemView.findViewById(R.id.rp_date);
            this.rp_amt = itemView.findViewById(R.id.rp_amt);
            this.rp_type = itemView.findViewById(R.id.rp_type);
        }
    }
}