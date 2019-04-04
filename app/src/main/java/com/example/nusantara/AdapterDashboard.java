package com.example.nusantara;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.MyViewHolder> {
    List<ItemDashboard> list;
    Context context;

    AdapterDashboard(List<ItemDashboard> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_dashboard,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        ItemDashboard dashboard = list.get(i);
        viewHolder.title.setText(dashboard.title);
        viewHolder.deskripsi.setText(dashboard.description);
        Picasso.get().load(dashboard.image).into(viewHolder.photo);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView photo;
        private TextView deskripsi,title;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            photo= itemView.findViewById(R.id.gambar);
            title = itemView.findViewById(R.id.judul);
            deskripsi= itemView.findViewById(R.id.deskripsi);
        }
    }
}
