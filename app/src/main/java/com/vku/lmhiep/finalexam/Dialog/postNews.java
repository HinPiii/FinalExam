package com.vku.lmhiep.finalexam.Dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vku.lmhiep.finalexam.Data.datapost;
import com.vku.lmhiep.finalexam.R;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class postNews {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("forum");
    static final int Image_code = (int) Math.random();
    ImageView choose, back;
    static ImageView pic;
    EditText editText;
    CircleImageView avatar;
    TextView dpname;
    MaterialButton button;
    public Activity activity;
    public AlertDialog alertDialog;
    Uri img;
    String name;
    String mail;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public postNews() {
    }

    public postNews(Activity activity, Uri img, String name, String mail) {
        this.activity = activity;
        this.img = img;
        this.name = name;
        this.mail = mail;
    }


    public void startDialog() {
        StorageReference storageRef = storage.getReferenceFromUrl("gs://finalexam-5ac8e.appspot.com");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogStyle2);
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_feeds, null);
        builder.setView(v);
        builder.setCancelable(true);

        Calendar calendar = Calendar.getInstance();
        editText = v.findViewById(R.id.post);
        button = v.findViewById(R.id.btn);
        avatar = v.findViewById(R.id.img_user);
        dpname = v.findViewById(R.id.name);
        back = v.findViewById(R.id.backItem);
        choose = v.findViewById(R.id.choosepic);
        pic = v.findViewById(R.id.pic);

        choose.setOnClickListener(v1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activity.startActivityForResult(intent, Image_code);
        });


        Picasso.get().load(img).into(avatar);
        dpname.setText(name);

        button.setOnClickListener(v12 -> {
            String post = editText.getText().toString();
            String ava = img.toString();
            StorageReference mountainsRef = storageRef.child(calendar.getTimeInMillis() + ".png");
            pic.setDrawingCacheEnabled(true);
            pic.buildDrawingCache();
            if (pic == null) {
                System.out.println(pic.getResources());
                datapost po = new datapost(ava, name, post, "", mail);
                reference.child("user").push().setValue(po);
                Toast.makeText(v12.getContext(), "Đăng thành công", Toast.LENGTH_SHORT).show();
                dismissDialog();
            }
            else {
                Bitmap bitmap = ((BitmapDrawable) pic.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(exception -> Toast.makeText(v12.getContext(), "Thất bại", Toast.LENGTH_SHORT).show()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(uri -> {
                            String pic = uri.toString();
                            if (post.length() <= 0){
                                datapost po = new datapost(ava, "", post, pic, mail);
                                reference.child("user").push().setValue(po);
                                Toast.makeText(v12.getContext(), "Đăng thành công", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                datapost po = new datapost(ava, name, post, pic, mail);
                                reference.child("user").push().setValue(po);
                                Toast.makeText(v12.getContext(), "Đăng thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            dismissDialog();
            editText.getText().clear();
            pic.setImageResource(0);
        });

        back.setOnClickListener(v13 -> {
            String post = editText.getText().toString();
            AlertDialog.Builder builderr = new AlertDialog.Builder(v13.getContext());
            if (!post.isEmpty()){
                builderr.setMessage("Bạn có muốn hủy bài viết không ?");
                builderr.setPositiveButton("Có", (dialog, which) -> {
                    editText.setText("");
                    dismissDialog();
                });
                builderr.setNegativeButton("Không", (dialog, which) -> {});
                builderr.show();
            }
            else{
                dismissDialog();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog() {
        alertDialog.dismiss();
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK && requestCode == Image_code) {
            pic.setImageURI(result.getData());
        }
    }
}

