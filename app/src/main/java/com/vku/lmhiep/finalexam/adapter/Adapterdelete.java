package com.vku.lmhiep.finalexam.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.Data.ListFood;
import com.vku.lmhiep.finalexam.R;

import java.util.ArrayList;

public class Adapterdelete extends RecyclerView.Adapter<Adapterdelete.ViewHolder> {
    Activity activity;
    ArrayList<Datameal> listname;
    String calo, name;
    ArrayList<ListFood> listFoods;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Calories");
    FirebaseStorage mStorage = FirebaseStorage.getInstance();

    public Adapterdelete(Activity activity, ArrayList<Datameal> listname, ArrayList<ListFood> listFoods, String calo, String name) {
        this.activity = activity;
        this.listname = listname;
        this.listFoods = listFoods;
        this.calo = calo;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
        return new Adapterdelete.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterdelete.ViewHolder holder, int position) {
        holder.textView.setText(listFoods.get(position).getName());
        holder.view.setOnLongClickListener(v -> {
            String keyID = reference.child(calo).child(name).child(listname.get(position).getDanhmuc()).getKey();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Xóa món ăn");
            builder.setMessage("Bạn có chắc không ?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                mStorage.getReferenceFromUrl(listFoods.get(position).getImg()).delete();
                reference.child(calo).child(name).child(keyID).removeValue();
                Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Không", (dialog, which) -> {});
            builder.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view);
            view = itemView;
        }
    }
}
