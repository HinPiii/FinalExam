package com.vku.lmhiep.finalexam.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vku.lmhiep.finalexam.Data.ListFood;
import com.vku.lmhiep.finalexam.Data.datapost;
import com.vku.lmhiep.finalexam.Data.react;
import com.vku.lmhiep.finalexam.Data.users;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.View.forum;
import com.vku.lmhiep.finalexam.adapter.AdapterFood;
import com.vku.lmhiep.finalexam.adapter.AdapterMeal;
import com.vku.lmhiep.finalexam.adapter.AdapterPost;
import com.vku.lmhiep.finalexam.adapter.AdapterViewPager;
import com.vku.lmhiep.finalexam.index;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    ImageView back;
    CircleImageView imageView;
    TextView name, gmail, weight, height, update;
    ArrayList<users> Info = new ArrayList<>();

    private AdapterViewPager adapterViewPager;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(viewPager);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String mail = acct.getEmail();
        String name2 = acct.getDisplayName();
        Uri img = acct.getPhotoUrl();

        update = findViewById(R.id.update);

        imageView = findViewById(R.id.avatar);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        name = findViewById(R.id.name);
        gmail = findViewById(R.id.gmail);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);

        update.setOnClickListener(v -> {
            String check = update.getText().toString();

            if (check.equals("Chỉnh sửa")){
                name.setEnabled(true);
                gmail.setEnabled(true);
                weight.setEnabled(true);
                height.setEnabled(true);
                update.setText("Lưu");
            }
            else{
                update.setText("Chỉnh sửa");
                name.setEnabled(false);
                gmail.setEnabled(false);
                weight.setEnabled(false);
                height.setEnabled(false);
                Toast.makeText(this, "Đã lưu chỉnh sửa", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.get().load(img).into(imageView);
        name.setText(name2);
        gmail.setText(mail);

        Query checkMail = reference.orderByChild("gmail").equalTo(mail);
        checkMail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        users info = new users();
                        info.setHeight(Integer.parseInt(snapshot1.child("height").getValue().toString()));
                        info.setWeight(Integer.parseInt(snapshot1.child("weight").getValue().toString()));
                        Info.add(info);
                    }
                    height.setText(String.valueOf(Info.get(0).getHeight()));
                    weight.setText(String.valueOf(Info.get(0).getWeight()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}