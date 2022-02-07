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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.View.additem;
import com.vku.lmhiep.finalexam.View.deleteitem;

import java.util.ArrayList;

public class AdapterAddFood extends RecyclerView.Adapter<AdapterAddFood.view>{
    ArrayList<Datameal> list;
    Context context;
    String calo;
    String name;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Calories");

    public AdapterAddFood(ArrayList<Datameal> list, Context context, String calo, String name) {
        this.calo = calo;
        this.list = list;
        this.context = context;
        this.name = name;
    }

    @NonNull
    @Override
    public view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_menu_row,parent,false);
        return new AdapterAddFood.view(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddFood.view holder, int position) {
        holder.txt.setText(list.get(position).getDanhmuc());
        holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            Query check = reference.child(String.valueOf(calo));
            check.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Datameal meal = new Datameal();
                        meal.setDanhmuc(snapshot1.getKey());
                        list.add(meal);
                    }
                    holder.itemView.setOnClickListener(v -> {
                        if (name.equalsIgnoreCase("Xóa")){
                            Intent intent = new Intent(v.getContext(), deleteitem.class);
                            intent.putExtra("meal", list.get(position).getDanhmuc());
                            intent.putExtra("calo", calo);
                            intent.putExtra("delete", name);
                            v.getContext().startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(v.getContext(), additem.class);
                            if (list.get(position).getDanhmuc().equalsIgnoreCase(list.get(0).getDanhmuc())){
                                intent.putExtra("meal", list.get(position).getDanhmuc());
                                intent.putExtra("calo", calo);
                                intent.putExtra("add", name);
                                v.getContext().startActivity(intent);
                            }
                            if (list.get(position).getDanhmuc().equalsIgnoreCase(list.get(1).getDanhmuc())){
                                intent.putExtra("meal", list.get(position).getDanhmuc());
                                intent.putExtra("calo", calo);
                                intent.putExtra("add", name);
                                v.getContext().startActivity(intent);
                            }
                            if (list.get(position).getDanhmuc().equalsIgnoreCase(list.get(2).getDanhmuc())){
                                intent.putExtra("meal", list.get(position).getDanhmuc());
                                intent.putExtra("calo", calo);
                                intent.putExtra("add", name);
                                v.getContext().startActivity(intent);
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class view extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView txt;

        public view(@NonNull View itemView) {
            super(itemView);

            txt = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.menu_card);
        }
    }
}
