package com.vku.lmhiep.finalexam.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vku.lmhiep.finalexam.Data.users;
import com.vku.lmhiep.finalexam.R;

public class info extends AppCompatActivity {

    FirebaseDatabase Fb = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    TextView textView;
    EditText editText, editText1;
    Button btn;
    int calo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        textView = findViewById(R.id.et_name);
        editText = findViewById(R.id.sex);
        editText1 = findViewById(R.id.age);
        btn = findViewById(R.id.btn);

        Intent intent = getIntent();
        String bmi = intent.getStringExtra("bmi");
        int weight = Integer.parseInt(intent.getStringExtra("weight"));
        int height = Integer.parseInt(intent.getStringExtra("height"));
        String name = intent.getStringExtra("name");
        String gmail = intent.getStringExtra("gmail");

        double parse = Double.parseDouble(bmi);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BMI");
        builder.setMessage("BMI của bạn là " + bmi);
        builder.setPositiveButton("Ok", (dialog, which) -> {
        });
        builder.show();

        textView.setText(bmi);


        btn.setOnClickListener(v -> {
            reference = Fb.getReference("Users");

            String ss = editText1.getText().toString();
            String sex = editText.getText().toString();

            int tuoi = Integer.parseInt(ss);
            if (sex.equalsIgnoreCase("nam")) {
                if (parse < 18.5) {
                    calo = (int) ((int) ((9.99 * weight)) + (6.25 * height) - (4.92 * tuoi) + 5 + 500);
                } else if (parse >= 18.5 && parse < 24.9)
                    calo = (int) ((int) ((9.99 * weight)) + (6.25 * height) - (4.92 * tuoi) + 5);
                else {
                    calo = (int) ((int) ((9.99 * weight)) + (6.25 * height) - (4.92 * tuoi) + 5 - 300);
                }
            } else {
                if (parse < 18.5) {
                    calo = (int) ((int) ((9.99 * weight)) + (6.25 * height) - (4.92 * tuoi) - 161 + 500);
                } else if (parse >= 18.5 && parse < 24.9) {
                    calo = (int) ((int) ((9.99 * weight)) + (6.25 * height) - (4.92 * tuoi) - 161);
                } else {
                    calo = (int) ((int) ((9.99 * weight)) + (6.25 * height) - (4.92 * tuoi) - 161 - 300);
                }
            }

            if (calo >= 1000 && calo <= 1500) {
                calo = 1000;
            } else if (calo > 1500 && calo < 2000) {
                calo = 1500;
            } else if (calo >= 2000 && calo <= 2500) {
                calo = 2000;
            } else if (calo > 2500 && calo < 3000) {
                calo = 2500;
            }

            users user = new users(name, gmail, weight, height, calo);
            reference.push().setValue(user);
            Toast.makeText(getApplicationContext(), "Regis success", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            finish();
        });
    }
}