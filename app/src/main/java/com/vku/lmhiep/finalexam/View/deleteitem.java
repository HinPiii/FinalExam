package com.vku.lmhiep.finalexam.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.Data.ListFood;
import com.vku.lmhiep.finalexam.Data.calories;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.adapter.AdapterAddFood;
import com.vku.lmhiep.finalexam.adapter.Adapterdelete;

import java.util.ArrayList;

public class deleteitem extends AppCompatActivity {
    ImageView imageView;
    RecyclerView recyclerView;
    ArrayList<ListFood> listname = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Calories");
    ArrayList<Datameal> Meal = new ArrayList<>();
    Adapterdelete Deleteitem;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteitem);

        imageView = findViewById(R.id.callback);
        imageView.setOnClickListener(v -> finish());
        textView = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        String name = intent.getStringExtra("meal");
        String calo = intent.getStringExtra("calo");
        String delete = intent.getStringExtra("delete");

        textView.setText(delete+" item");

        Query check = reference.child(calo).child(name);
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Meal.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                        Datameal meal = new Datameal();
                        meal.setDanhmuc(snapshot1.getKey());
                        Meal.add(meal);
                        ListFood name = new ListFood();
                        name.setImg(snapshot1.child("img").getValue().toString());
                        name.setName(snapshot1.child("name").getValue().toString());
                        listname.add(name);
                }
                Deleteitem = new Adapterdelete(deleteitem.this, Meal, listname,  calo, name);
                recyclerView.setAdapter(Deleteitem);
                Deleteitem.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}