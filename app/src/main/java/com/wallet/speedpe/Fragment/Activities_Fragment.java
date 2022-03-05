package com.wallet.speedpe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.speedpe.Adapter.RecyclerView_Activties;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class Activities_Fragment extends Fragment {
    private ArrayList<String> activity_img_url = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> summary = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_profile, container, false );
        init_data(view);
        return view;
    }

    private void init_data(View view) {
        activity_img_url.add("https://i.redd.it/glin0nwndo501.jpg");
        activity_img_url.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        activity_img_url.add("https://i.redd.it/j6myfqglup501.jpg");

        name.add("Smart Work and cold lead with niche");
        name.add("Add on: I am planning to grow my network across all countries where li has even the sl...");
        name.add("Add on: I am planning to grow my network across all countries where li has even the sl...");

        summary.add("Abhinav replied to a comment");
        summary.add("Abhinav replied to a comment");
        summary.add("Abhinav replied to a comment");

        init_pitch(view);
    }

    private void init_pitch(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_Activties adapter = new RecyclerView_Activties(getContext(),activity_img_url,name,summary);
        recyclerView.setAdapter(adapter);
    }
}
