package com.vku.lmhiep.finalexam.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.Data.ListFood;
import com.vku.lmhiep.finalexam.R;

import java.util.ArrayList;

public class AdapterMeal extends RecyclerView.Adapter<AdapterMeal.ViewHolder> {
    ArrayList<Datameal> datameals;
    String calo;
    Context mContext;
    int rowIndex;
    AdapterFood adapterFood;
    RecyclerView recyclerView1;
    ArrayList<Datameal> Meal = new ArrayList<>();
    ArrayList<ListFood> breakfast = new ArrayList<>();
    ArrayList<ListFood> lunch = new ArrayList<>();
    ArrayList<ListFood> dinner = new ArrayList<>();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Calories");
    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("list");
    String mail;

    public AdapterMeal(ArrayList<Datameal> datameals, String calo, Context mContext, RecyclerView recyclerView1, String mail) {
        this.datameals = datameals;
        this.mContext = mContext;
        this.calo = calo;
        this.recyclerView1 = recyclerView1;
        this.mail = mail;

        Query qbreakfast = reference.child(String.valueOf(calo)).child(datameals.get(0).getDanhmuc());
        Query qlunch = reference.child(String.valueOf(calo)).child(datameals.get(1).getDanhmuc());
        Query qdinner = reference.child(String.valueOf(calo)).child(datameals.get(2).getDanhmuc());

        qbreakfast.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ListFood meal = new ListFood();
                    meal.setName(snapshot1.child("name").getValue().toString());
                    meal.setCalo(snapshot1.child("calo").getValue().toString());
                    meal.setImg(snapshot1.child("img").getValue().toString());
                    meal.setReact(snapshot1.child("react").getValue().toString());
                    breakfast.add(meal);
                    Datameal name = new Datameal();
                    name.setDanhmuc(snapshot1.getKey());
                    Meal.add(name);
                    Query check = reference1.child("user").child(snapshot1.getKey());
                    check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (mail.equals(snapshot.child("user").getValue())) {
                                meal.setFav(true);
                            }
                            recyclerView1.setHasFixedSize(true);
                            recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
                            adapterFood = new AdapterFood(mContext, breakfast, calo, datameals.get(0).getDanhmuc(), Meal, mail);
                            recyclerView1.setAdapter(adapterFood);
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

        qlunch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ListFood meal = new ListFood();
                    meal.setName(snapshot1.child("name").getValue().toString());
                    meal.setCalo(snapshot1.child("calo").getValue().toString());
                    meal.setImg(snapshot1.child("img").getValue().toString());
                    meal.setReact(snapshot1.child("react").getValue().toString());
                    lunch.add(meal);
                    Datameal name = new Datameal();
                    name.setDanhmuc(snapshot1.getKey());
                    Meal.add(name);
                    Query check = reference1.child("user").child(snapshot1.getKey());
                    check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (mail.equals(snapshot.child("user").getValue())) {
                                meal.setFav(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        qdinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ListFood meal = new ListFood();
                    meal.setName(snapshot1.child("name").getValue().toString());
                    meal.setCalo(snapshot1.child("calo").getValue().toString());
                    meal.setImg(snapshot1.child("img").getValue().toString());
                    meal.setReact(snapshot1.child("react").getValue().toString());
                    dinner.add(meal);
                    Datameal name = new Datameal();
                    name.setDanhmuc(snapshot1.getKey());
                    Meal.add(name);
                    Query check = reference1.child("user").child(snapshot1.getKey());
                    check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (mail.equals(snapshot.child("user").getValue())) {
                                meal.setFav(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_menu_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt.setText(datameals.get(position).getDanhmuc());

        holder.cardView.setOnClickListener(v -> {
            rowIndex = position;

            ArrayList<ListFood> lf = new ArrayList<>();
            switch (datameals.get(position).getDanhmuc()) {
                case "Buổi sáng":
                    lf = breakfast;
                    break;
                case "Buổi trưa":
                    lf = lunch;
                    break;
                case "Buổi tối":
                    lf = dinner;
                    break;
                default:
                    lf = breakfast;
                    break;
            }
            recyclerView1.setHasFixedSize(true);
            recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
            adapterFood = new AdapterFood(mContext, lf, calo, datameals.get(position).getDanhmuc(), Meal, mail);
            recyclerView1.setAdapter(adapterFood);
            notifyDataSetChanged();
        });
        if (rowIndex == position) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFB300"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }


    }


    @Override
    public int getItemCount() {
        return datameals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.menu_card);

        }
    }
}

