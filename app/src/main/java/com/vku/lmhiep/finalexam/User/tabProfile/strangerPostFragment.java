package com.vku.lmhiep.finalexam.User.tabProfile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vku.lmhiep.finalexam.Data.datapost;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.User.stranger;
import com.vku.lmhiep.finalexam.adapter.AdapterPost;

import java.util.ArrayList;

public class strangerPostFragment extends Fragment {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("forum");
    ArrayList<datapost> postss = new ArrayList<>();
    RecyclerView recyclerView;
    View view;
    stranger mstranger;
    AdapterPost adapterPost;

    public strangerPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stranger_post, container, false);
        recyclerView = view.findViewById(R.id.profile);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        post();
        return view;
    }

    public ArrayList<datapost> post(){
        mstranger = (stranger) getActivity();
        String name2 = mstranger.getName3();
        Query forum = reference.child("user").orderByChild("name").equalTo(name2);
        forum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postss.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    datapost data = new datapost();
                    data.setImg(snapshot1.child("img").getValue().toString());
                    data.setContent(snapshot1.child("content").getValue().toString());
                    data.setName(snapshot1.child("name").getValue().toString());
                    data.setPic(snapshot1.child("pic").getValue().toString());
                    postss.add(data);
                }
                adapterPost = new AdapterPost(postss, getContext());
                recyclerView.setAdapter(adapterPost);
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return postss;
    }
}