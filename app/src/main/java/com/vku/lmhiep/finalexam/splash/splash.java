package com.vku.lmhiep.finalexam.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vku.lmhiep.finalexam.Data.users;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.User.MainActivity;
import com.vku.lmhiep.finalexam.index;

import java.util.ArrayList;

public class splash extends AppCompatActivity {
    ProgressBar progressBar;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    ArrayList<users> Info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progress_bar);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);


        if (acct == null){
            new async().execute();
        }
        else{
            String mail = acct.getEmail();
            Query checkMail = reference.orderByChild("gmail").equalTo(mail);
            checkMail.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            users info = new users();
                            info.setGmail(snapshot1.child("gmail").getValue().toString());
                            info.setCalo(snapshot1.child("calories").getValue().toString());
                            Info.add(info);
                        }
                        Intent intent = new Intent(splash.this, index.class);
                        intent.putExtra("gmail",Info.get(0).getGmail());
                        intent.putExtra("calories", Info.get(0).getCalo());
                        startActivity(intent);
                        finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    class async extends AsyncTask<Void, Integer, Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                for(int i = 0; i<=100;i++){
                    Thread.sleep(10);
                    publishProgress(i);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Intent intent = new Intent(splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}