package com.vku.lmhiep.finalexam.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vku.lmhiep.finalexam.Data.Datameal;
import com.vku.lmhiep.finalexam.Data.comment;
import com.vku.lmhiep.finalexam.Data.post;
import com.vku.lmhiep.finalexam.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHoler> {
    List<comment> list;
    Context context;
    String gmail;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("comment");
    ArrayList<Datameal> Key;
    String key;

    public AdapterComment(List<comment> list, Context context, String gmail, ArrayList<Datameal> Key, String key) {
        this.list = list;
        this.context = context;
        this.gmail = gmail;
        this.Key = Key;
        this.key = key;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        return new AdapterComment.ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComment.ViewHoler holder, int position) {

        Picasso.get().load(list.get(position).getImg()).into(holder.avatar);
        holder.name.setText(list.get(position).getName());
        holder.cmt.setText(list.get(position).getCmt());

        if (gmail.equalsIgnoreCase(list.get(position).getGmail())) {
            holder.view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xóa bình luận");
                String keyid = reference.child(key).child(Key.get(position).getDanhmuc()).getKey();

                builder.setPositiveButton("Xóa", (dialog, which) -> {
                    reference.child(key).child(keyid).removeValue();
                    Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                });
                builder.show();
                return true;
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        CircleImageView avatar;
        TextView name, cmt;
        View view;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            cmt = itemView.findViewById(R.id.cmt);
            view = itemView;
        }
    }
}
