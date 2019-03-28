package com.example.nusantara;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    final int REQUEST_GALLERY_IMAGE = 101;
    Button btnUpload;
    ImageView img;
    FirebaseAuth mAuth;
    List<ItemDashboard> list;
    private AdapterDashboard mAdapter;
    private RecyclerView mRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        mRecycle = findViewById(R.id.recycle);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            Intent i = new Intent(DashboardUser.this, MainActivity.class);
            finish();
            startActivity(i);
        }

        list = new ArrayList<>();
        mAdapter= new AdapterDashboard(list ,this);
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.setAdapter(mAdapter);

        if (user == null){
            startActivity(new Intent(DashboardUser.this, MainActivity.class));
            finish();
        }

        img = findViewById(R.id.imageView);
        btnUpload = findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_GALLERY_IMAGE);
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
        }

        gambarrekomendasi.recycle();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == RESULT_OK && data != null){
            Uri photoUri = data.getData();
            img.setImageURI(photoUri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

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

        }
        return false;
    }
}
