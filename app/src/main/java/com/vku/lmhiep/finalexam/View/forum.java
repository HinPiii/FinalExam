package com.vku.lmhiep.finalexam.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vku.lmhiep.finalexam.Data.datapost;
import com.vku.lmhiep.finalexam.Data.post;
import com.vku.lmhiep.finalexam.Dialog.postNews;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.adapter.AdapterPost;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class forum extends AppCompatActivity {
    CircleImageView avatar;
    ImageView imageView;
    RecyclerView recyclerView;
    TextView content;
    ArrayList<datapost> Info = new ArrayList<>();
    AdapterPost adapterPost;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("forum");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Uri img = acct.getPhotoUrl();
        String name = acct.getDisplayName();
        String mail = acct.getEmail();


        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        content = findViewById(R.id.content);
        avatar = findViewById(R.id.avatar);
        imageView = findViewById(R.id.callback);

        imageView.setOnClickListener(v -> {
            finish();
        });

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final postNews bt = new postNews(forum.this, img, name,mail);
                bt.startDialog();
            }
        });

        Picasso.get().load(img).into(avatar);

        String ava = String.valueOf(img);


        Query info = reference.child("user");
        info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Info.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    datapost info = new datapost();
                    info.setImg(snapshot1.child("img").getValue().toString());
                    info.setContent(snapshot1.child("content").getValue().toString());
                    info.setName(snapshot1.child("name").getValue().toString());
                    info.setPic(snapshot1.child("pic").getValue().toString());
                    info.setMail(snapshot1.child("mail").getValue().toString());
                    Info.add(info);
                }
                adapterPost = new AdapterPost(Info, forum.this);
                recyclerView.setAdapter(adapterPost);
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        postNews.onActivityResult(requestCode, resultCode, data);
    }
}