package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class RecyclerView_activities_adapter extends RecyclerView.Adapter<RecyclerView_activities_adapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> categorys = new ArrayList<>();
    private Context mContext;

    private int row_index;
    public RecyclerView_activities_adapter(Context context,ArrayList<String> category) {
        categorys = category;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.cardview_category, parent, false);
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
            holder.card_box.setCardBackgroundColor( Color.parseColor("#003248"));
            holder.txt_category.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.card_box.setCardBackgroundColor(Color.parseColor("#F1F1F1"));
            holder.txt_category.setTextColor(Color.parseColor("#4D4D4D"));
        }


    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_category;
        CardView card_box;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_category = itemView.findViewById(R.id.txt_category);
            card_box = itemView.findViewById(R.id.card_box);
        }
    }
}