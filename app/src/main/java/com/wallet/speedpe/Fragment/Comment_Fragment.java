package com.wallet.speedpe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.speedpe.Adapter.RecyclerView_Comment;
import com.wallet.speedpe.R;
import java.util.ArrayList;

public class Comment_Fragment extends Fragment {
    private ArrayList<String> sImageUrls = new ArrayList<>();
    private ArrayList<String> summary = new ArrayList<>();
    private LinearLayout ley_about;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_profile_post, container, false );
        ley_about = view.findViewById(R.id.ley_about);
        ley_about.setVisibility(View.GONE);
        init_data(view);
        return view;
    }

    private void init_data(View view) {
        sImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        sImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        sImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        sImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");

        summary.add("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy");
        summary.add("Industries fail to utilize drones and aerial vehicles efficietly, securely and quickly for industrial applications. As a result, we can’t increase the efficiency of human laborDrones become inefficient and primitive tools in the hands of a human operator, in terms of both in-flight and post-opera tion data handling. This limits the potential of aerial vehi- clees to maximize revenues in many industries such as energy, agriculture, security and more");
        summary.add("Industries fail to utilize drones and aerial vehicles efficietly, securely and quickly for industrial applications. As a result, we can’t increase the efficiency of human laborDrones become inefficient and primitive tools in the hands of a human operator, in terms of both in-flight and post-opera tion data handling. This limits the potential of aerial vehi- clees to maximize revenues in many industries such as energy, agriculture, security and more");
        summary.add("Industries fail to utilize drones and aerial vehicles efficietly, securely and quickly for industrial applications. As a result, we can’t increase the efficiency of human laborDrones become inefficient and primitive tools in the hands of a human operator, in terms of both in-flight and post-opera tion data handling. This limits the potential of aerial vehi- clees to maximize revenues in many industries such as energy, agriculture, security and more");
        init_comment(view);
    }

    private void init_comment(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_Comment adapter = new RecyclerView_Comment(getContext(),sImageUrls,summary);
        recyclerView.setAdapter(adapter);
    }
}
