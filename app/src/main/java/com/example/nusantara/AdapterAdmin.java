package com.example.nusantara;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.UUID;

import com.example.nusantara.model.ItemModel;

public class AdapterAdmin extends RecyclerView.Adapter<AdapterAdmin.Myviewholder> {
    List<ItemModel> itemmodell;
    Context context;

    public AdapterAdmin(List<ItemModel> itemmodell, Context context) {
        this.itemmodell = itemmodell;
        this.context = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemadmin,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder myviewholder, final int i) {
        final ItemModel item = itemmodell.get(i);


        myviewholder.tvTerima.setText(item.image);
        myviewholder.tvTolak.setText(item.caption);

        myviewholder.btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemModel im = new ItemModel(item.image, item.caption, item.captionlain);
                UUID a = UUID.randomUUID();
                FirebaseFirestore.getInstance().document("post/"+a).set(im).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "MANTUL", Toast.LENGTH_SHORT).show();
                        itemmodell.remove(i);
                        notifyItemRemoved(i);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        myviewholder.btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemModel im = new ItemModel(item.image, item.caption, item.captionlain);
                UUID a = UUID.randomUUID();
                FirebaseFirestore.getInstance().document("post/"+a).set(im).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "MANTUL", Toast.LENGTH_SHORT).show();
                        itemmodell.remove(i);
                        notifyItemRemoved(i);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        myviewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdapterAdmin.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemmodell.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView tvTerima, tvTolak;
        Button btnTerima, btnTolak;
        CardView cardView;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvTerima = itemView.findViewById(R.id.tv_tr);
            tvTolak = itemView.findViewById(R.id.tv_tlk);
            btnTerima = itemView.findViewById(R.id.btn_terima);
            btnTolak = itemView.findViewById(R.id.btn_tolak);
}}}

