package com.vku.lmhiep.finalexam.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.Data.ListFood;
import com.vku.lmhiep.finalexam.Data.comment;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.View.detailFood;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.viewHolder> {
    Context context;
    List<ListFood> listFood;
    ArrayList<Datameal> listname;
    String calo;
    String name;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("list");
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("comment");
    String mail;
    ArrayList<comment> Info = new ArrayList<>();
    ArrayList<Datameal> Key = new ArrayList<>();

    public AdapterFood(Context context, List<ListFood> listFood, String calo, String name, ArrayList<Datameal> listname, String mail) {
        this.context = context;
        this.listFood = listFood;
        this.calo = calo;
        this.name = name;
        this.listname = listname;
        this.mail = mail;

    }

    public AdapterFood(Context context, List<ListFood> listFood) {
        this.context = context;
        this.listFood = listFood;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new AdapterFood.viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Picasso.get().load(listFood.get(position).getImg()).resize(200, 120).centerCrop().into(holder.img);
        holder.txt_name.setText(listFood.get(position).getName());
        holder.txt_calo.setText(listFood.get(position).getCalo());

        String cal = holder.txt_calo.getText().toString();
        String img = listFood.get(position).getImg();
        String food = holder.txt_name.getText().toString();

        if(listFood.get(position).isFav()){
            holder.react.setColorFilter(R.color.red);
            holder.react.setBackgroundColor(Color.WHITE);
        }
        else{
            holder.react.setBackgroundColor(Color.parseColor("#EEEEEE"));
            holder.react.clearColorFilter();
        }


        holder.react.setOnClickListener(v -> {
            if(listFood.get(position).isFav()){
                holder.react.setBackgroundColor(Color.parseColor("#EEEEEE"));
                listFood.get(position).setFav(false);
                holder.react.clearColorFilter();
                reference.child("user").child(listname.get(position).getDanhmuc()).removeValue();

            }
            else{
                ListFood Food = new ListFood(food, cal, "0", img, mail);
                reference.child("user").child(listname.get(position).getDanhmuc()).setValue(Food);
                holder.react.setColorFilter(R.color.red);
                holder.react.setBackgroundColor(Color.WHITE);
                listFood.get(position).setFav(true);
            }
        });

        holder.cmt.setOnClickListener(new View.OnClickListener() {
            private ImageView send, close;
            private TextInputEditText text;
            private CircleImageView user;
            private RecyclerView recyclerView;
            private TextView count;

            @Override
            public void onClick(View v) {

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(v.getContext());
                Uri img = acct.getPhotoUrl();
                String name = acct.getDisplayName();
                String mail = acct.getEmail();

                final com.google.android.material.bottomsheet.BottomSheetDialog bt =
                        new com.google.android.material.bottomsheet.BottomSheetDialog(v.getContext(), R.style.DialogStyle);
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.comment_dialog, null);
                send = view.findViewById(R.id.send);
                text = view.findViewById(R.id.ed_desc);
                user = view.findViewById(R.id.img_user);
                close = view.findViewById(R.id.close);
                recyclerView = view.findViewById(R.id.recycleView);
                count = view.findViewById(R.id.count);

                close.setOnClickListener(v1 -> {
                    bt.dismiss();
                });

                Picasso.get().load(img).into(user);

                send.setOnClickListener(view1 -> {
                    String cmt = text.getText().toString().trim();
                    String anh = String.valueOf(img);
                    if (cmt.length() == 0) {

                    } else {
                        comment list = new comment(name, food, cmt, anh, mail);
                        reference2.child("user").push().setValue(list);
                    }
                    text.setText("");
                });
                bt.setContentView(view);
                bt.show();

                Query check = reference2.child("user").orderByChild("food").equalTo(food);
                check.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Key.clear();
                        Info.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            comment info = new comment();
                            info.setCmt(snapshot1.child("cmt").getValue().toString());
                            info.setImg(snapshot1.child("img").getValue().toString());
                            info.setName(snapshot1.child("name").getValue().toString());
                            info.setGmail(snapshot1.child("gmail").getValue().toString());
                            info.setFood(snapshot1.child("food").getValue().toString());
                            Info.add(info);

                            Datameal key = new Datameal();
                            key.setDanhmuc(snapshot1.getKey());
                            Key.add(key);
                        }
                        String user = "user";
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, true);
                        recyclerView.setLayoutManager(layoutManager);
                        AdapterComment adapterComment = new AdapterComment(Info, context, mail, Key, user);
                        recyclerView.setAdapter(adapterComment);
                        adapterComment.notifyDataSetChanged();
                        count.setText(Key.size() + " bình luận");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent1 = new Intent(v.getContext(), detailFood.class);
            intent1.putExtra("name", listFood.get(position).getName());
            v.getContext().startActivity(intent1);
        });

    }

    public List<ListFood> getMeal() {
        return listFood;
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_calo;
        CardView cardView;
        ImageView react, img, cmt;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.item_img);
            react = itemView.findViewById(R.id.react);
            txt_name = itemView.findViewById(R.id.item_name);
            txt_calo = itemView.findViewById(R.id.item_calo);
            cardView = itemView.findViewById(R.id.food_item);
            cmt = itemView.findViewById(R.id.cmt);
        }
    }
}
