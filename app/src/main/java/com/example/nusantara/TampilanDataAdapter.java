package com.example.nusantara;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TampilanDataAdapter extends RecyclerView.Adapter<TampilanDataAdapter.MyViewHolder> {

    List<ItemData> mListData;
    Context context;

    public TampilanDataAdapter(List<ItemData> mListData, Context context) {
        this.mListData = mListData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dashboard, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final ItemData itemData = mListData.get(i);
        myViewHolder.tvJudul.setText(itemData.nama);
        myViewHolder.tvDesc.setText(itemData.deskripsi);
        Picasso.get().load(itemData.img).into(myViewHolder.imageView);

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TampilanFotoUploadan.class);
                i.putExtra("nama", itemData.nama);
                i.putExtra("deskripsi", itemData.deskripsi);
                i.putExtra("gambar", itemData.img);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView tvJudul, tvDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.dashboarditem);
            imageView = itemView.findViewById(R.id.gambar);
            tvJudul = itemView.findViewById(R.id.judul);
            tvDesc = itemView.findViewById(R.id.deskripsi);
        }
    }
}
