package com.example.nusantara;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.io.FileNotFoundException;
import java.io.IOException;

public class UnggahFoto extends AppCompatActivity {
    private final int REQUEST_GET_SINGLE_FILE = 101;
    private final int REQUEST_CAPTURE_IMAGE = 102;


    Button buttonPilih,buttonUnggah;
    EditText namadaerah,deskripsi;
    ImageView imgupload;
    Boolean a,b;
    Bitmap bitmap;
    Uri uri;
    ProgressDialog dialog;
    String imagePath,mName,mDesk,imgUri;
    byte[] selectedImageBytes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unggah_foto);

        bindView();
        checkPermissionAndroid();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait...");
        dialog.setCancelable(false);
        dialog.setProgress(0);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        namadaerah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                a = s.toString().length() > 1;
                if (a && b){
                    buttonUnggah.setVisibility(View.VISIBLE);
                }
                else {
                    buttonUnggah.setVisibility(View.GONE);
                }
            }
        });

        deskripsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                b = s.toString().length()>1;
                if (a && b){
                    buttonUnggah.setVisibility(View.VISIBLE);
                }
                else {
                    buttonUnggah.setVisibility(View.GONE);
                }
            }
        });

        buttonPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

        buttonUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalery();
            }
        });

    }

    private void openGalery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),REQUEST_GET_SINGLE_FILE);
    }

    private void verify() {
        mName = namadaerah.getText().toString().trim();
        mDesk = deskripsi.getText().toString().trim();

        if (mName.isEmpty()){
            message("Must be Filled in...");
            return;
        }
        if (mDesk.isEmpty()){
            message("Must be Filled in...");
            return;
        }
        saveData();
    }

    private void saveData() {
        dialog.show();
        final StorageReference up = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()+""+System.currentTimeMillis());
        up.putBytes(selectedImageBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                up.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgUri = uri.toString();
                        saveDb();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                dialog.setProgress((int) progress);
            }
        });
    }

    private void saveDb() {
       FirebaseFirestore.getInstance().collection("ItemMenu").add(new ItemMenu(mName,mDesk,imgUri)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
           @Override
           public void onComplete(@NonNull Task<DocumentReference> task) {
               if (task.isSuccessful()){
                   dialog.dismiss();
                   backToMain();
               }
           }
       });
    }

    private void backToMain() {
        Intent i = new Intent(UnggahFoto.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void message(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    private void checkPermissionAndroid() {
        if (ContextCompat.checkSelfPermission(UnggahFoto.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            Log.d("MAINACTIVITY","checkPermissionAndroid: Read: You have already granted this permission");
        }
        else {
            backToMain();
        }
        if (ContextCompat.checkSelfPermission(UnggahFoto.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            Log.d("MAINACTIVITY","checkPermissionAndroid: Write: You have already granted this permission");
        }
        else {
            backToMain();
        }
    }

    private void bindView() {
        a = b = false;
        buttonPilih = findViewById(R.id.button_pilihfoto);
        imgupload = findViewById(R.id.img_upload);
        namadaerah = findViewById(R.id.txt_nama_daerah);
        deskripsi = findViewById(R.id.txt_desk);
        buttonUnggah = findViewById(R.id.btn_unggah);
        buttonUnggah.setVisibility(View.GONE);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (resultCode==RESULT_OK){
            if (requestCode==REQUEST_CAPTURE_IMAGE){
                if (data!=null && data.getExtras()!=null){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imgupload.setImageBitmap(bitmap);
                }
            }
            else if (requestCode==REQUEST_GET_SINGLE_FILE){
                uri = data.getData();
                imagePath = uri!= null?uri.getPath():null;
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
                    imgupload.setImageBitmap(bitmap);
                    selectedImageBytes = outputStream.toByteArray();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}