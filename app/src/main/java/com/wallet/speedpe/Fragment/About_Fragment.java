package com.wallet.speedpe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wallet.speedpe.Adapter.RecyclerView_About;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class About_Fragment extends Fragment {
    private ArrayList<String> summary = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_profile, container, false );
        init_data(view);
        return view;
    }

    private void init_data(View view) {
        summary.add("All of this started in 2007 when I decided to work as a freelancer while going to college. Within those years and beyond, I have had to join with writers, editors, animator, designers, programmers and a multitude of other professionals to get work done with the highest quality. If you get to know me, you will find out that I am all about quality. The reason I am so adamant about quality, is because I believe that it is the only way to do anything. I start this website designing business up over the past few years as a place to show clients my sense of style. I am an advocate for originality, style and quality. We are very...");
        init_pitch(view);
    }

    private void init_pitch(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_About adapter = new RecyclerView_About(getContext(),summary);
        recyclerView.setAdapter(adapter);
    }
}
