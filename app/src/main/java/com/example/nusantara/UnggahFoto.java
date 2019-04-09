package com.example.nusantara;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UnggahFoto extends AppCompatActivity {

    private static final int REQUEST_GET_SINGLE_FILE = 101;
    private static final int REQUEST_CAPTURE_IMAGE = 102;
    private static final String TAG = "InputActivity";
    ImageView uploadfoto;
    Button btn_unggahfoto;
    EditText namadaerah, deskripsi;
    String  mNamadaerah, mDeskripsi, imgUrl;


    Uri uri;
    String imagePath;
    Bitmap bitmap;
    byte[] selectedImageBytes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unggah_foto);

        uploadfoto = findViewById(R.id.img_upload);
        btn_unggahfoto = findViewById(R.id.btn_unggah);
        namadaerah = findViewById(R.id.txt_nama_daerah);
        deskripsi= findViewById(R.id.txt_desk);


        btn_unggahfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namadaerah.getText().toString().isEmpty()) {
                    return;

                if (deskripsi.getText().toString().isEmpty()) {
                    return;
                }
                btn_unggahfoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verify();
                    }
                });
            }
            });

        uploadfoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGalery();
                });
            }
            private void verify() {
                mNamadaerah = namadaerah.getText().toString().trim();
               mDeskripsi = deskripsi.getText().toString().trim();

                if (mNamadaerah.isEmpty()){
                    pesan("Nama daerah harus diisi");
                    return;
                }

                if (mDeskripsi.isEmpty()){
                    pesan("Deskripsi harus diisi");
                    return;
                }

                saveData();
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
                FirebaseFirestore.getInstance().collection("ItemMenu").add(new ItemMenu(mName, mHarga, mDesc, imgUrl)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();

                            backToMain();
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
            private void pesan(String msg){
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }


            private void openGalery() {
                if (isReadStoragePermissionGranted()) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                            REQUEST_GET_SINGLE_FILE);
                }
            }
            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == RESULT_OK) {
                    if (requestCode == REQUEST_CAPTURE_IMAGE) {
                        if (data != null && data.getExtras() != null) {
                            bitmap = (Bitmap) data.getExtras().get("data");
                            uploadfoto.setImageBitmap(bitmap);
                        }
                    } else if (requestCode == REQUEST_GET_SINGLE_FILE) {
                        uri = data.getData();
                        imagePath = uri != null ? uri.getPath() : null;
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

            public  boolean isReadStoragePermissionGranted() {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Log.v(TAG,"Permission is granted1");
                        return true;
                    } else {

                        Log.v(TAG,"Permission is revoked1");
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                        return false;
                    }
                }
                else { //permission is automatically granted on sdk<23 upon installation
                    Log.v(TAG,"Permission is granted1");
                    return true;
                }
            }

            public  boolean isWriteStoragePermissionGranted() {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Log.v(TAG,"Permission is granted2");
                        return true;
                    } else {

                        Log.v(TAG,"Permission is revoked2");
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                        return false;
                    }
                }
                else { //permission is automatically granted on sdk<23 upon installation
                    Log.v(TAG,"Permission is granted2");
                    return true;
                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                UnggahFoto.super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                switch (requestCode) {
                    case 2:
                        Log.d(TAG, "External storage2");
                        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                            //resume tasks needing this permission
//                    downloadPdfFile();
                        }else{
//                    progress.dismiss();
                        }
                        break;

                    case 3:
                        Log.d(TAG, "External storage1");
                        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                            //resume tasks needing this permission
//                    SharePdfFile();
                        }else{
//                    progress.dismiss();
                        }
                        break;
                }
            }
        }





