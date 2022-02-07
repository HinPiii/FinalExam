package com.vku.lmhiep.finalexam.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vku.lmhiep.finalexam.R;
import com.vku.lmhiep.finalexam.Data.users;

public class Register extends AppCompatActivity {
    TextView textView;
    EditText name, gmail, password, repass;
    Button register;
    GoogleSignInClient mGoogleSignInClient;
    double bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textView = findViewById(R.id.btn_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        name = findViewById(R.id.et_name);
        gmail = findViewById(R.id.et_gmail);
        password = findViewById(R.id.et_password);
        repass = findViewById(R.id.et_repassword);
        register = findViewById(R.id.btn_register);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            name.setText(personName);
            gmail.setText(personEmail);
        }


        register.setOnClickListener(v -> {
            String name1 =  name.getText().toString().trim();
            String gmail1 = gmail.getText().toString();
            String weight = password.getText().toString();
            String height = repass.getText().toString();
            if (weight.isEmpty()){
                password.setError("not allow empty");
                password.requestFocus();
            }
            if (height.isEmpty()){
                repass.setError("not allow empty");
                repass.requestFocus();
            }
            bmi = (Double.parseDouble(weight)*10000) / Math.pow(Double.parseDouble(height),2);
            String s = String.valueOf(bmi);
            String s1 = s.substring(0,4);
            Intent intent = new Intent(Register.this, info.class);
            intent.putExtra("gmail", gmail1);
            intent.putExtra("name",name1);
            intent.putExtra("weight",weight);
            intent.putExtra("height",height);
            intent.putExtra("bmi", s1);
            startActivity(intent);
            finish();
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_login:
                        signOut();
                        break;
                }
            }
        });
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}