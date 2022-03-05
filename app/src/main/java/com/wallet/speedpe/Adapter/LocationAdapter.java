package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wallet.speedpe.Activity.Activity_Home;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> locations = new ArrayList<>();
    private Context mContext;

    int selectedPosition = 0;

    public LocationAdapter(Context context,ArrayList<String> location) {
        locations = location;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.location_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.radio_loc.setChecked(position == selectedPosition);
        holder.radio_loc.setTag(position);

        if (holder.radio_loc.isChecked()){
            holder.txt_location.setText(locations.get(position));
            holder.txt_location.setTextColor(mContext.getResources().getColor(R.color.black));
        }else{
            holder.txt_location.setText(locations.get(position));
            holder.txt_location.setTextColor(mContext.getResources().getColor(R.color.grey));
        }

        holder.radio_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = Integer.parseInt(view.getTag().toString());
                notifyDataSetChanged();
                String locaton = locations.get(selectedPosition);
                Intent goack = new Intent(mContext, Activity_Home.class);
                goack.putExtra("location",locaton);
                mContext.startActivity(goack);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RadioButton radio_loc;
        TextView txt_location;
        public ViewHolder(View itemView) {
            super(itemView);
            radio_loc = itemView.findViewById(R.id.radio_loc);
            txt_location = itemView.findViewById(R.id.txt_location);
        }
    }
}