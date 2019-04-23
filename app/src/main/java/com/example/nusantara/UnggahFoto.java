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

public class UnggahFoto extends AppCompatActivity {

    private static final int REQUEST_GET_SINGLE_FILE = 101;
    private static final int REQUEST_CAPTURE_IMAGE = 102;
    private static final String TAG = "InputActivity";
    ImageView uploadfoto;
    Button btnFoto, btnUnggah;
    EditText nmDaerah, dsDaerah;
    String  mName, mDesc, imgUrl;

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

        uploadfoto = findViewById(R.id.img_upload);
        btnFoto = findViewById(R.id.btn_upload);
        btnUnggah = findViewById(R.id.btn_unggah);
        nmDaerah = findViewById(R.id.txt_nama_daerah);
        dsDaerah = findViewById(R.id.txt_desk);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Tunggu sebentar");
        dialog.setCancelable(false);
        dialog.setProgress(0);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = nmDaerah.getText().toString();
                mDesc = dsDaerah.getText().toString();

                if (mName.isEmpty()){
                    toast("Nama daerah harus diisi");
                    return;
                }
                if (mDesc.isEmpty()){
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
                    uploadfoto.setImageBitmap(bitmap);
                }
            } else if (requestCode == REQUEST_GET_SINGLE_FILE) {
                uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                            uri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    uploadfoto.setImageBitmap(bitmap);

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

        final StorageReference up = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()+""+System.currentTimeMillis());

        if(selectedImageBytes != null){
            up.putBytes(selectedImageBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    up.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgUrl = uri.toString();

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
        FirebaseFirestore.getInstance().collection("ItemProvinsi").add(new ItemData(imgUrl,mName, mDesc)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();

                    backToMain();
//                    Intent i = new Intent(UnggahFoto.this, DashboardUser.class);
//                    startActivity(i);
                }
            }
        });
    }

    private void backToMain(){
        Intent i = new Intent(UnggahFoto.this, DashboardUser.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}