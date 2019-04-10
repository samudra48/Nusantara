package com.example.nusantara;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProvinsiAdapter extends RecyclerView.Adapter<ProvinsiAdapter.ProvinsiHolder> {
    private List<Object> provinsi;
    private Context context;

    public ProvinsiAdapter(Context context, List<Object> provinsi) {
        this.provinsi = provinsi;
        this.context = context;
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

        provinsiHolder.bgProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SplashActivity.class);
                i.putExtra("provinsi",provinsi.toString());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinsi.size();
    }

    public class ProvinsiHolder extends RecyclerView.ViewHolder {
        private TextView tvProv;
        private ConstraintLayout bgProv;
        public ProvinsiHolder(@NonNull View itemView) {
            super(itemView);

            tvProv = itemView.findViewById(R.id.tv_provinsi);
            bgProv = itemView.findViewById(R.id.bg_provinsi);
        }
    }
}
