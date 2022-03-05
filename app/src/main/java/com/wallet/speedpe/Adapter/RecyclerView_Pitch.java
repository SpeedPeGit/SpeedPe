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

public class RecyclerView_Pitch extends RecyclerView.Adapter<RecyclerView_Pitch.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> summarys = new ArrayList<>();
    private Context mContext;

    private int row_index;
    public RecyclerView_Pitch(Context context, ArrayList<String> name, ArrayList<String> summary) {
        names = name;
        summarys = summary;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_pitch_frame, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.txt_name.setText( names.get( position ) );
        holder.txt_des.setText( summarys.get( position ) );
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_name,txt_des;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_des = itemView.findViewById(R.id.txt_des);
        }
    }
}