package com.wallet.speedpe.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wallet.speedpe.Adapter.RecyclerView_Category_display;
import com.wallet.speedpe.Adapter.RecyclerView_Category_dual_display;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class Category_fragment extends Fragment {
    private static final String TAG = "MainActivity";
    private ArrayList<String> mainimg = new ArrayList<>();
    private ArrayList<String> subimg = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> des = new ArrayList<>();
    private ArrayList<String> loc = new ArrayList<>();
    private ArrayList<String> category = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        Integer dis_id = this.getArguments().getInt("dis_id");

        initArraylist();

        if (dis_id==1){
            init_card_category(view);
        }else{
            init_dual_card_category(view);
        }

        return view;
    }

    private void initArraylist() {

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mainimg.add("https://i.redd.it/glin0nwndo501.jpg");
        mainimg.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mainimg.add("https://i.redd.it/qn7f9oqu7o501.jpg");

        subimg.add("https://i.redd.it/j6myfqglup501.jpg");
        subimg.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        subimg.add("https://i.redd.it/k98uzl68eh501.jpg");

        name.add("Coolberg");
        name.add("Dunzo");
        name.add("Shuttle");

        des.add("A Unique Idea");
        des.add("Need groceries or food delivered?get it Done!");
        des.add("Stress free Commute to work");

        loc.add("Bangalore");
        loc.add("Mangalore");
        loc.add("Chennai");

        category.add("Technology");
        category.add("Agriculture");
        category.add("Cosmetics");
    }

    private void init_dual_card_category(View view) {
        initRecyclerView_dual_category(view);
    }

    private void initRecyclerView_dual_category(View view) {
        Log.d(TAG, "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        RecyclerView_Category_dual_display adapter = new RecyclerView_Category_dual_display(getContext(),mainimg,subimg,name,des,loc,category);
        recyclerView.setAdapter(adapter);
    }

    private void init_card_category(View view) {
        initRecyclerView_category(view);
    }

    private void initRecyclerView_category(View view) {
        Log.d(TAG, "initRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_Category_display adapter = new RecyclerView_Category_display(getContext(),mainimg,subimg,name,des,loc,category);
        recyclerView.setAdapter(adapter);
    }
}
