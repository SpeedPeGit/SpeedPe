package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class RecyclerView_About extends RecyclerView.Adapter<RecyclerView_About.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> summarys = new ArrayList<>();
    private Context mContext;

    private int row_index;
    public RecyclerView_About(Context context,ArrayList<String> summary) {
        summarys = summary;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_about_frame, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.txt_des.setText( summarys.get( position ) );
    }

    @Override
    public int getItemCount() {
        return summarys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_des;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_des = itemView.findViewById(R.id.txt_des);
        }
    }
}