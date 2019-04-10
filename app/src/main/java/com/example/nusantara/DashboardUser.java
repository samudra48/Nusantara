package com.example.nusantara;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DashboardUser extends AppCompatActivity {

    FloatingActionButton btnFab;
    FirebaseAuth mAuth;
    List<ItemDashboard> list;
    List<ItemDashboard> sList;

    private AdapterDashboard mAdapter;
    private RecyclerView mRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        mRecycle = findViewById(R.id.recycle);
        btnFab = findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            Intent i = new Intent(DashboardUser.this, MainActivity.class);
            finish();
            startActivity(i);
        }

        sList = new ArrayList<>();
        list = new ArrayList<>();
        mAdapter= new AdapterDashboard(list ,this);
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.setAdapter(mAdapter);

        if (user == null){
            startActivity(new Intent(DashboardUser.this, MainActivity.class));
            finish();
        }

        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardUser.this, UnggahFoto.class));
            }
        });

        initializeData();
    }

    private void initializeData() {
        String[] rekomendasi = getResources()
                .getStringArray(R.array.rekomendasi_title);
        String[] rekomendasiinfo = getResources()
                .getStringArray(R.array.info_rekomendasi);
        TypedArray gambarrekomendasi = getResources()
                .obtainTypedArray(R.array.gambar_rekomendasi);

        list.clear();

        for (int i = 0; i < rekomendasi.length; i++) {
            list.add(new ItemDashboard(rekomendasi[i], rekomendasiinfo[i],
                    gambarrekomendasi.getResourceId(i, 0)));
            sList.add(new ItemDashboard(rekomendasi[i], rekomendasiinfo[i],
                    gambarrekomendasi.getResourceId(i, 0)));
        }

        gambarrekomendasi.recycle();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.option_search).getActionView();
        searchView.setQueryHint("Pencarian");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) {
                    list.clear();
                    String search = s.toLowerCase();
                    for (ItemDashboard item : sList) {
                        if (item.title.toLowerCase().contains(search)){
                            list.add(item);
                        }
                    }
                }else{
                    list.addAll(sList);
                }
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });

        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_logout:
                mAuth.signOut();
                startActivity(new Intent(DashboardUser.this, MainActivity.class));
                finish();
                break;

            case R.id.option_search:

                break;


        }
        return false;
    }
}
