package com.vku.lmhiep.finalexam.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vku.lmhiep.finalexam.Data.calories;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.adapter.AdapterCalo;

import java.util.ArrayList;


public class ChooseCalo extends AppCompatActivity {
    ImageView imageView;
    RecyclerView recyclerView;
    AdapterCalo adapterCalo;
    ArrayList<calories> Calo = new ArrayList<>();
    Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Calories");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_calo);

        Intent intent = getIntent();
        String add = intent.getStringExtra("add");
        String delete = intent.getStringExtra("delete");
        String rename = intent.getStringExtra("rename");
        imageView = findViewById(R.id.callback);
        imageView.setOnClickListener(v -> finish());
        recyclerView = findViewById(R.id.calorcl);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    if (Calo.size() < 3){
                        calories calo = new calories();
                        calo.setCalo(snapshot1.getKey());
                        Calo.add(calo);
                    }
                }
                if (add != null){
                    adapterCalo = new AdapterCalo(context, Calo, add);
                    recyclerView.setAdapter(adapterCalo);
                    adapterCalo.notifyDataSetChanged();
                }
                else if (delete != null){
                    adapterCalo = new AdapterCalo(context, Calo, delete);
                    recyclerView.setAdapter(adapterCalo);
                    adapterCalo.notifyDataSetChanged();
                }
                else{
                    adapterCalo = new AdapterCalo(context, Calo, rename);
                    recyclerView.setAdapter(adapterCalo);
                    adapterCalo.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}