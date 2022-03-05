package com.wallet.speedpe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.wallet.speedpe.Activity.Activity_Profile_Post;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class RecyclerView_Category_display extends RecyclerView.Adapter<RecyclerView_Category_display.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mainimgs = new ArrayList<>();
    private ArrayList<String> subimgs = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> dess = new ArrayList<>();
    private ArrayList<String> locs = new ArrayList<>();
    private ArrayList<String> categorys = new ArrayList<>();

    private Context mContext;

    public RecyclerView_Category_display(Context context, ArrayList<String> mainimg, ArrayList<String> subimg, ArrayList<String> name, ArrayList<String> des, ArrayList<String> loc, ArrayList<String> category) {
        mainimgs = mainimg;
        subimgs = subimg;
        names = name;
        dess = des;
        locs = loc;
        categorys = category;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_category_single_display, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mainimgs.get(position))
                .into(holder.card_single_img);

        Glide.with(mContext)
                .asBitmap()
                .load(subimgs.get(position))
                .into(holder.card_sub_img);

        holder.txt_name.setText( names.get( position ) );
        holder.txt_des.setText( dess.get( position ) );
        holder.txt_loc.setText( locs.get( position ) );
        holder.txt_category.setText(categorys.get(position));

        holder.crd_cat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goprofile = new Intent(mContext, Activity_Profile_Post.class);
                goprofile.putExtra("mainimg",mainimgs.get( position ));
                goprofile.putExtra( "subimg",subimgs.get( position ));
                goprofile.putExtra( "name",names.get( position ));
                goprofile.putExtra( "location",locs.get( position ));
                goprofile.putExtra( "des",dess.get( position ));
                mContext.startActivity(goprofile);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView card_single_img,card_sub_img;
        TextView txt_name,txt_des,txt_loc,txt_category;
        CardView crd_cat;

        public ViewHolder(View itemView) {
            super(itemView);
            card_single_img = itemView.findViewById(R.id.card_single_img);
            card_sub_img = itemView.findViewById(R.id.card_sub_img);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_des = itemView.findViewById(R.id.txt_des);
            txt_loc = itemView.findViewById(R.id.txt_loc);
            txt_category = itemView.findViewById(R.id.txt_category);
            crd_cat = itemView.findViewById(R.id.crd_cat);
        }
    }
}