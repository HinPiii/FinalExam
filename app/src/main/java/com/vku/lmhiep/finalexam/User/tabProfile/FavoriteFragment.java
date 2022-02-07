package com.vku.lmhiep.finalexam.User.tabProfile;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vku.lmhiep.finalexam.Data.ListFood;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.adapter.AdapterFood;
import com.vku.lmhiep.finalexam.adapter.AdapterPost;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("list");
    RecyclerView recyclerView;
    View view;
    ArrayList<ListFood> itemList = new ArrayList<>();
    AdapterFood adapterFood;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favorite, container, false);


        recyclerView = view.findViewById(R.id.profile);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        food();
        return view;
    }

    public ArrayList<ListFood> food(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        String mail = acct.getEmail();
        adapterFood = new AdapterFood(getContext(), itemList);
        recyclerView.setAdapter(adapterFood);
        Query mailll = reference.child("user").orderByChild("user").equalTo(mail);
        mailll.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    ListFood meal = new ListFood();
                    meal.setName(snapshot1.child("name").getValue().toString());
                    meal.setCalo(snapshot1.child("calo").getValue().toString());
                    meal.setImg(snapshot1.child("img").getValue().toString());
                    itemList.add(meal);
                    Query check = reference.child("user").child(snapshot1.getKey());
                    check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (mail.equals(snapshot.child("user").getValue())) {
                                meal.setFav(true);
                                adapterFood.notifyDataSetChanged();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return itemList;
    }
}