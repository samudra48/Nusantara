package com.example.nusantara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DeskripsiActivity extends DashboardUser {

    TextView tvProvinsi, tvIbukos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deskripsiprovinsi);

        tvProvinsi = findViewById(R.id.provinsi);
        tvIbukos = findViewById(R.id.ibukota);

        if (getIntent() != null){
            String namaProv = getIntent().getStringExtra("nama");
            String ibuProv = getIntent().getStringExtra("ibukota");

            if (namaProv != null) {
                tvProvinsi.setText(namaProv);
            }
            if (ibuProv != null){
                tvIbukos.setText(ibuProv);
            }
        }
    }
}
