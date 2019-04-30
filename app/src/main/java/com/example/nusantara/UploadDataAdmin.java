package com.example.nusantara;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class UploadDataAdmin extends AppCompatActivity {

    private static final int REQUEST_GET_SINGLE_FILE = 101;
    private static final int REQUEST_CAPTURE_IMAGE = 102;
    private static final String TAG = "InputActivity";
    ImageView uploadfotoadmin;
    Button btnFotoadmin, btnUnggahadmin;
    EditText nmDaerahadmin, dsDaerahadmin, etCategory;
    String  mNameadmin, mDescadmin, imgUrladmin;

    ProgressDialog dialog;
    Uri uri;
    Bitmap bitmap;
    byte[] selectedImageBytes;


    final String PREF_NIGHT_MODE = "NightMode";
    SharedPreferences spNight;
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
        setContentView(R.layout.activity_unggah_foto);

        uploadfotoadmin = findViewById(R.id.img_uploadadmin);
        btnFotoadmin = findViewById(R.id.btn_uploadadin);
        btnUnggahadmin = findViewById(R.id.btn_unggahadmin);
        nmDaerahadmin = findViewById(R.id.txt_nama_daerahadmin);
        dsDaerahadmin = findViewById(R.id.txt_deskadmin);
        etCategory = findViewById(R.id.editText_kategori);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Tunggu sebentar");
        dialog.setCancelable(false);
        dialog.setProgress(0);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        btnFotoadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnUnggahadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNameadmin = nmDaerahadmin.getText().toString();
                mDescadmin = dsDaerahadmin.getText().toString();

                if (mNameadmin.isEmpty()){
                    toast("Nama daerah harus diisi");
                    return;
                }
                if (mDescadmin.isEmpty()){
                    toast("Deskripsi harus diisi");
                    return;
                }

                saveData();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAPTURE_IMAGE) {
                if (data != null && data.getExtras() != null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    uploadfotoadmin.setImageBitmap(bitmap);
                }
            } else if (requestCode == REQUEST_GET_SINGLE_FILE) {
                uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                            uri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    uploadfotoadmin.setImageBitmap(bitmap);

                    selectedImageBytes = outputStream.toByteArray();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openGallery() {
        if (isReadStoragePermissionGranted()) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                    REQUEST_GET_SINGLE_FILE);
        }
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else {
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3:
                Log.d(TAG, "External storage");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);

                }
                break;
        }
    }

    private void saveData() {
        dialog.show();

        String cat = etCategory.getText().toString();

        final StorageReference up = FirebaseStorage.getInstance().getReference().child(cat+""+System.currentTimeMillis());

        if(selectedImageBytes != null){
            up.putBytes(selectedImageBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    up.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgUrladmin = uri.toString();

                            saveDb();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    dialog.setProgress((int) progress);
                }
            });
        }else{
            saveDb();
        }

    }

    private void saveDb() {
        String id = UUID.randomUUID().toString();
        DocumentReference a = FirebaseFirestore.getInstance().document("rekomen/"+id);
        a.set(new ItemData(imgUrladmin,mNameadmin, mDescadmin,id)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) backToMain();
            }
        });
    }

    private void backToMain(){
        Intent i = new Intent(UploadDataAdmin.this, AdminDashboardActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}

