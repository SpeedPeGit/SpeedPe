package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wallet.speedpe.R;
import com.wallet.speedpe.recyclerViewListClicked;
import java.util.ArrayList;

public class RecyclerView_ChatMessage extends RecyclerView.Adapter<RecyclerView_ChatMessage.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private ArrayList<String> chats = new ArrayList<>();
    private ArrayList<String> values = new ArrayList<>();
    private Context mContext;
    private static recyclerViewListClicked itemListeners;

    public RecyclerView_ChatMessage(Context context, ArrayList<String> chat, ArrayList<String> value, ArrayList<String> dess, ArrayList<String> times,ArrayList<String> colors, recyclerViewListClicked itemListener) {
        chats = chat;
        values = value;
        mContext = context;
        itemListeners = itemListener;
    }

    public RecyclerView_ChatMessage(Context context, ArrayList<String> chat, ArrayList<String> value){
        chats = chat;
        values = value;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.fragment_chat ,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.txt_out.setText(chats.get(position));
        holder.txt_in.setText(values.get(position));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txt_out,txt_in,txt_out_time,txt_in_time;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_in = itemView.findViewById(R.id.txt_in);
            txt_out = itemView.findViewById(R.id.txt_out);

            txt_out_time = itemView.findViewById(R.id.txt_out_time);
            txt_in_time = itemView.findViewById(R.id.txt_in_time);
        }

        @Override
        public void onClick(View view) {
            itemListeners.recyclerViewListClicked(view, this.getPosition());
        }
    }
}