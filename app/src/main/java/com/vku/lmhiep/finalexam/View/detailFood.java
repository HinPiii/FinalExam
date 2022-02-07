package com.vku.lmhiep.finalexam.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.vku.lmhiep.finalexam.R;

public class detailFood extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        textView = findViewById(R.id.txt);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        textView.setText(name);
    }
}