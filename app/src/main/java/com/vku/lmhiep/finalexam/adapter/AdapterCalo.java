package com.vku.lmhiep.finalexam.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vku.lmhiep.finalexam.Data.calories;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.View.AddFood;


import java.util.List;

public class AdapterCalo extends RecyclerView.Adapter<AdapterCalo.viewholder> {
    Context context;
    List<calories> listcalo;
    String name;

    public AdapterCalo(Context context, List<calories> listcalo, String name) {
        this.context = context;
        this.listcalo = listcalo;
        this.name = name;
    }

    @NonNull

    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcalo,parent,false);
        return new AdapterCalo.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCalo.viewholder holder, int position) {
        holder.textView.setText(listcalo.get(position).getCalo() +" calo");
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddFood.class);
            intent.putExtra("calo", listcalo.get(position).getCalo());
            intent.putExtra("add", name);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listcalo.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView textView;

        public viewholder(@NonNull  View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txt);
        }
    }

}
