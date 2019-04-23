package com.example.nusantara;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.TextView;

public class DeskripsiActivity extends DashboardUser {
    final String PREF_NIGHT_MODE = "NightMode";
    SharedPreferences spNight;


    TextView tvProvinsi, tvIbukos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        spNight = getSharedPreferences(PREF_NIGHT_MODE , Context.MODE_PRIVATE);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);

            if(spNight.getBoolean(PREF_NIGHT_MODE,false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }

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
