package com.vku.lmhiep.finalexam.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
import com.vku.lmhiep.finalexam.Data.users;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.User.tabProfile.strangerFavFragment;
import com.vku.lmhiep.finalexam.User.tabProfile.strangerPostFragment;
import com.vku.lmhiep.finalexam.adapter.AdapterFood;
import com.vku.lmhiep.finalexam.adapter.AdapterPost;
import com.vku.lmhiep.finalexam.adapter.AdapterStranger;
import com.vku.lmhiep.finalexam.adapter.AdapterViewPager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class stranger extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    ArrayList<users> info = new ArrayList<>();
    TextView name, gmail;
    CircleImageView ava;
    ImageView back;
    private AdapterStranger adapterStranger;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String email = "";
    String name3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stranger);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        name = findViewById(R.id.name);
        gmail = findViewById(R.id.gmail);
        ava = findViewById(R.id.avatar);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapterStranger = new AdapterStranger(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapterStranger);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        String name2 = intent.getStringExtra("name");
        String mail = intent.getStringExtra("mail");

        email = mail;

        name3 = name2;

        strangerFavFragment strangerFavFragment = new strangerFavFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.test, strangerFavFragment);
        fragmentTransaction.commit();

        strangerPostFragment strangerPostFragment  = new strangerPostFragment();
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.test, strangerPostFragment);
        fragmentTransaction1.commit();

        Query check = reference.orderByChild("name").equalTo(name2);
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                info.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    gmail.setText(snapshot1.child("gmail").getValue().toString());
                    name.setText(snapshot1.child("name").getValue().toString());
                    Picasso.get().load(snapshot1.child("img").getValue().toString()).into(ava);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getEmail() {
        return email;
    }

    public String getName3() {
        return name3;
    }
}