package com.vku.lmhiep.finalexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.R;

import java.util.ArrayList;

public class AdapterAddFood2 extends RecyclerView.Adapter<AdapterAddFood2.ViewHolder>{
    ArrayList<Datameal> list;
    Context context;

    public AdapterAddFood2(ArrayList<Datameal> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_additem,parent,false);
        return new AdapterAddFood2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddFood2.ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getDanhmuc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        EditText editText, editText1;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.mealname);
            editText = itemView.findViewById(R.id.name);
            editText = itemView.findViewById(R.id.calo);
            imageView = itemView.findViewById(R.id.pic);
        }
    }
}
