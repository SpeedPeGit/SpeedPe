package com.wallet.speedpe.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.wallet.speedpe.PlanDetails;
import com.wallet.speedpe.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<Integer> image_arraylist= new ArrayList<>();
    ArrayList<String> plan_id_list;
    ArrayList<String> plan_name_list;
    ArrayList<String> plan_amt_list;
    ArrayList<String> plan_credit_list;
    ArrayList<String> plan_cashback_list;
    private PlanDetails pdListener;

    public SliderPagerAdapter(Activity activity, ArrayList<String> plan_id_list, ArrayList<String> plan_name_list, ArrayList<String> plan_amt_list, ArrayList<String> plan_credit_list, ArrayList<String> plan_cashback_list, PlanDetails planDetails) {
        this.activity = activity;
        this.plan_id_list = plan_id_list;
        this.plan_name_list = plan_name_list;
        this.plan_amt_list = plan_amt_list;
        this.plan_credit_list = plan_credit_list;
        this.plan_cashback_list = plan_cashback_list;
        this.pdListener = planDetails;

        for (int i=0; i<plan_name_list.size(); i++) {
            if (plan_name_list.get(i).toString().toLowerCase().contains("welcome")){
                image_arraylist.add(R.drawable.welcom_plan);
            }
            else if (plan_name_list.get(i).toString().toLowerCase().contains("bronze")){
                image_arraylist.add(R.drawable.bronze_plan);
            }
            else if (plan_name_list.get(i).toString().toLowerCase().contains("silver")){
                image_arraylist.add(R.drawable.silver_plan);
            }
            else if (plan_name_list.get(i).toString().toLowerCase().contains("gold")){
                image_arraylist.add(R.drawable.gold_plan);
            }
            else{
                image_arraylist.add(R.drawable.welcom_plan);
            }
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate( R.layout.plan_slider, container, false);

        final ImageButton btn_pl_buy;
        TextView banner_plan_name, banner_plan_price, banner_plan_credit, banner_plan_cashback;
        banner_plan_name = view.findViewById(R.id.banner_plan_name);
        banner_plan_price = view.findViewById(R.id.banner_plan_price);
        banner_plan_credit = view.findViewById(R.id.banner_plan_credit);
        banner_plan_cashback = view.findViewById(R.id.banner_plan_cashback);
        btn_pl_buy = view.findViewById(R.id.banner_buy_plan);
        btn_pl_buy.setTag(position);

        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);
        Picasso.get()
                .load(image_arraylist.get(position))
                .placeholder(R.mipmap.ic_launcher) // optional
                .error(R.mipmap.ic_launcher)         // optional
                .into(im_slider);
        banner_plan_name.setText(plan_name_list.get(position).toString());
        banner_plan_price.setText(plan_amt_list.get(position).toString());
        banner_plan_credit.setText(plan_credit_list.get(position).toString());
        banner_plan_cashback.setText(plan_cashback_list.get(position).toString());

        btn_pl_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callShowPlanDetails(plan_id_list.get(position).toString());
            }
        });
        container.addView(view);
        return view;
    }

    private void callShowPlanDetails(String pid) {
        pdListener.ShowPlanDetail(pid);
    }

    @Override
    public int getCount() {
        return plan_amt_list.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
