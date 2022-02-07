package com.vku.lmhiep.finalexam.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vku.lmhiep.finalexam.Data.food;
import com.vku.lmhiep.finalexam.R;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class additem extends AppCompatActivity {
    TextView textView, textView1;
    EditText editText, editText1;
    ImageView imageView, imageView1;
    Button btn;
    static final int Image_code = (int) Math.random();
    static final int permission_code = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Calories");
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        StorageReference storageRef = storage.getReferenceFromUrl("gs://finalexam-5ac8e.appspot.com");

        textView1 = findViewById(R.id.title);
        textView = findViewById(R.id.mealname);
        editText = findViewById(R.id.name);
        editText1 = findViewById(R.id.calo);
        imageView = findViewById(R.id.pic);
        btn = findViewById(R.id.btn);
        imageView1 = findViewById(R.id.callback);

        imageView1.setOnClickListener(v -> finish());

        imageView.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, permission_code);
                }
                else{
                    pickImageFromGalley();
                }
            }
            else{
                pickImageFromGalley();
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("meal");
        String calo = intent.getStringExtra("calo");
        String add = intent.getStringExtra("add");

        textView1.setText(add+ " item");
        textView.setText(name);

        btn.setOnClickListener(v -> {
            String foodname = editText.getText().toString();
            String foodcalo = editText1.getText().toString();
            int react = 0;
            Calendar calendar = Calendar.getInstance();
            StorageReference mountainsRef = storageRef.child(calendar.getTimeInMillis()+".png");

            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(exception -> Toast.makeText(additem.this, "Thất bại", Toast.LENGTH_SHORT).show()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(uri -> {
                        String photoLink = uri.toString();
                        food nfood = new food(foodname, foodcalo, photoLink, react);
                        reference.child(calo).child(name).push().setValue(nfood);
                    });
                }
            });
            Toast.makeText(additem.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            editText.setText("");
            editText1.setText("");
            imageView.setImageResource(R.drawable.ic_baseline_image_24);
        });

    }

    private void pickImageFromGalley() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Image_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case permission_code:{
                if (grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
                    pickImageFromGalley();
                }
                else{
                    Toast.makeText(additem.this, "Permission denied ", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Image_code) {
            imageView.setImageURI(data.getData());
        }
    }
}