package com.example.nusantara;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProvinsiAdapter extends RecyclerView.Adapter<ProvinsiAdapter.ProvinsiHolder> {
    private List<Object> provinsi;

    public ProvinsiAdapter(List<Object> provinsi) {
        this.provinsi = provinsi;
    }

    @NonNull
    @Override
    public ProvinsiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProvinsiHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_provinsi, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinsiHolder provinsiHolder, int i) {
        Object o = provinsi.get(i);
        provinsiHolder.tvProv.setText(o.toString());
    }

    @Override
    public int getItemCount() {
        return provinsi.size();
    }

    public class ProvinsiHolder extends RecyclerView.ViewHolder {
        private TextView tvProv;
        public ProvinsiHolder(@NonNull View itemView) {
            super(itemView);

            tvProv = itemView.findViewById(R.id.tv_provinsi);
        }
    }
}
