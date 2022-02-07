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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.adapter.AdapterAddFood;
import com.vku.lmhiep.finalexam.adapter.AdapterMeal;

import java.util.ArrayList;

public class AddFood extends AppCompatActivity {
    ImageView imageView;
    AdapterAddFood adapterAddFood;
    RecyclerView recyclerView;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Calories");
    ArrayList<Datameal> Meal = new ArrayList<>();
    Context context;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        textView = findViewById(R.id.mealname);
        recyclerView = findViewById(R.id.meal);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        imageView = findViewById(R.id.callback);
        imageView.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String calo = intent.getStringExtra("calo");
        String add = intent.getStringExtra("add");

        Query check = reference.child(calo);
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Meal.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Datameal meal = new Datameal();
                    meal.setDanhmuc(snapshot1.getKey());
                    Meal.add(meal);
                }
                adapterAddFood = new AdapterAddFood(Meal, context, calo, add);

                recyclerView.setAdapter(adapterAddFood);
                adapterAddFood.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}