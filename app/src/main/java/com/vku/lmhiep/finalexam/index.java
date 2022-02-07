package com.vku.lmhiep.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.User.MainActivity;
import com.vku.lmhiep.finalexam.User.profile;
import com.vku.lmhiep.finalexam.View.ChooseCalo;
import com.vku.lmhiep.finalexam.View.forum;
import com.vku.lmhiep.finalexam.adapter.AdapterMeal;

import java.util.ArrayList;


public class index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private long backPressedTime;
    Toast mToast;
    GoogleSignInClient mGoogleSignInClient;
    RecyclerView recyclerView, recyclerView1;
    AdapterMeal adapterMeal;
    Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Calories");
    ArrayList<Datameal> Meal = new ArrayList<>();
    SearchView searchView;
    TextView textView;
    ImageView avatar;
    TextView name, gmail;
    View hview;
    String ad = "ndhoang.19it2@vku.udn.vn";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Intent intent = getIntent();
        String calo = intent.getStringExtra("calories");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Calories");
        builder.setMessage("Mức calo bạn cần nạp trong ngày " + calo);
        builder.setPositiveButton("Ok", (dialog, which) -> {
        });
        builder.show();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String mail = acct.getEmail();
        String name1 = acct.getDisplayName();
        Uri img = acct.getPhotoUrl();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);



        hview = navigationView.getHeaderView(0);

        avatar = hview.findViewById(R.id.nav_avatar);
        name = hview.findViewById(R.id.nav_name);
        gmail = hview.findViewById(R.id.nav_gmail);

        Picasso.get().load(img).into(avatar);
        name.setText(name1);
        gmail.setText(mail);

        textView = findViewById(R.id.calo);
        searchView = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView1 = findViewById(R.id.recycleView1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        textView.setText(calo + " Calo");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Query check = reference.child(calo);
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Datameal meal = new Datameal();
                    meal.setDanhmuc(snapshot1.getKey());
                    Meal.add(meal);
                }
                adapterMeal = new AdapterMeal(Meal, calo, context, recyclerView1, mail);

                recyclerView.setAdapter(adapterMeal);
                adapterMeal.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Intent intent = new Intent(index.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            if (backPressedTime + 20000 > System.currentTimeMillis()){
                mToast.cancel();
                super.onBackPressed();
                return;
            }
            else{
                mToast = Toast.makeText(index.this, "Press back again to exit", Toast.LENGTH_SHORT);
                mToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String mail = acct.getEmail();
        Intent intent = new Intent(index.this, ChooseCalo.class);

        if (mail.equalsIgnoreCase(ad)){
            switch (item.getItemId()){
                case R.id.nav_home:
                    break;
                case R.id.profile:
                    Intent intent1 = new Intent(this, profile.class);
                    startActivity(intent1);
                    break;
                case R.id.forum:
                    Intent intent2 = new Intent(this, forum.class);
                    startActivity(intent2);
                    break;
                case R.id.nav_add:
                    intent.putExtra("add", "Thêm");
                    startActivity(intent);
                    break;
                case R.id.nav_delete:
                    intent.putExtra("delete", "Xóa");
                    startActivity(intent);
                    break;
                case R.id.logout:
                    signOut();
                    break;
            }
        }
        else{
            switch (item.getItemId()){
                case R.id.nav_home:
                    break;
                case R.id.profile:
                    Intent intent1 = new Intent(this, profile.class);
                    startActivity(intent1);
                    break;
                case R.id.forum:
                    Intent intent2 = new Intent(this, forum.class);
                    startActivity(intent2);
                    break;
                case R.id.nav_add:
                    Toast.makeText(this,"You must be an administrator to use this feature", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_delete:
                    Toast.makeText(this,"You must be an administrator to use this feature", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logout:
                    signOut();
                    break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}