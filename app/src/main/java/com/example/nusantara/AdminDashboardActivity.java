package com.example.nusantara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nusantara.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {
    final String PREF_NIGHT_MODE = "NightMode";
    SharedPreferences spNight;

    RecyclerView dasboard_admin;
    AdapterAdmin adapter_admin;
    List<ItemModel> mList;

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
        setContentView(R.layout.activity_admin_dashboard);

        dasboard_admin = findViewById(R.id.rv_admin);
        mList = new ArrayList<>();

        dasboard_admin.setLayoutManager(new LinearLayoutManager(this));
        adapter_admin = new AdapterAdmin(mList, this);
        dasboard_admin.setAdapter(adapter_admin);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_logoutadmin:
                startActivity(new Intent(getBaseContext(),LoginAdminActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
