package com.vku.lmhiep.finalexam.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.vku.lmhiep.finalexam.Data.comment;
import com.vku.lmhiep.finalexam.Data.datapost;
import com.vku.lmhiep.finalexam.Data.post;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.User.profile;
import com.vku.lmhiep.finalexam.User.stranger;
import com.vku.lmhiep.finalexam.User.tabProfile.strangerFavFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.Viewholder> {
    List<datapost> info;
    Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("comment");
    ArrayList<comment> Info = new ArrayList<>();
    ArrayList<Datameal> Key = new ArrayList<>();

    public AdapterPost(List<datapost> info, Context context) {
        this.info = info;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new AdapterPost.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPost.Viewholder holder, int position) {
        Picasso.get().load(info.get(position).getImg()).into(holder.avatar);
        holder.content.setText(info.get(position).getContent());
        holder.name.setText(info.get(position).getName());
        String s = holder.name.getText().toString();
        String gmail = info.get(position).getMail();
        holder.name.setOnClickListener(v -> {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
            String name2 = acct.getDisplayName();
            Intent intent = new Intent(v.getContext(), stranger.class);
            intent.putExtra("name", s);
            intent.putExtra("mail", gmail);
            if (name2.equals(s)){
                Intent intent1 = new Intent(v.getContext(), profile.class);
                v.getContext().startActivity(intent1);
            }
            else{
                strangerFavFragment strangerFavFragment = new strangerFavFragment();
                Bundle bundle = new Bundle();
                bundle.putString("mail", gmail);
                v.getContext().startActivity(intent);
            }
        });
        if (info.get(position).getPic().trim().length() > 0){
            Picasso.get().load(info.get(position).getPic()).resize(600, 400).centerCrop().into(holder.pic);
        }

        String content = holder.content.getText().toString();

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
                        post list = new post(name, content, anh, mail, cmt);
                        reference.child("cmt").push().setValue(list);
                    }
                    text.setText("");

                });
                bt.setContentView(view);
                bt.show();

                Query check = reference.child("cmt").orderByChild("content").equalTo(content);

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
                            info.setContent(snapshot1.child("content").getValue().toString());
                            Info.add(info);

                            Datameal key = new Datameal();
                            key.setDanhmuc(snapshot1.getKey());
                            Key.add(key);
                        }
                        String cmt = "cmt";
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, true);
                        recyclerView.setLayoutManager(layoutManager);
                        AdapterComment adapterComment = new AdapterComment(Info, context, mail, Key, cmt);
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
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        CircleImageView avatar;
        TextView name, content;
        ImageView cmt, pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.content);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            cmt = itemView.findViewById(R.id.cmt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
