package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class RecyclerView_trash_category extends RecyclerView.Adapter<RecyclerView_trash_category.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> categorys = new ArrayList<>();
    private Context mContext;

    private int row_index;
    public RecyclerView_trash_category(Context context,ArrayList<String> category) {
        categorys = category;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.txt_category.setText( categorys.get( position ) );

        holder.card_box.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                notifyDataSetChanged();
            }
        } );

        if(row_index==position){
            holder.card_box.setCardBackgroundColor( Color.parseColor("#003D59"));
            holder.txt_category.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.card_box.setCardBackgroundColor(Color.parseColor("#ffffff"));
            holder.txt_category.setTextColor(Color.parseColor("#003D59"));
        }
    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_category;
        MaterialCardView card_box;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_category = itemView.findViewById(R.id.txt_category);
            card_box = itemView.findViewById(R.id.card_box);
        }
    }
}
