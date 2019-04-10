package com.example.nusantara;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class FileData extends AppCompatActivity {

    FirebaseFirestore db;
    StorageReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_data);

        db = FirebaseFirestore.getInstance();

        String[] provinsi = getResources().getStringArray(R.array.provinsi);
        String[] ibukota = getResources().getStringArray(R.array.ibukota);
        String[] makanan = getResources().getStringArray(R.array.makanan_khas);
        String[] tempat_wisata = getResources().getStringArray(R.array.tempat_wisata);

        for(String string : provinsi){
            HashMap<String, String> map = new HashMap<>();
            map.put("Nama",string);
            db.collection("provinsi").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()){

                    }
                }
            });
        }
    }
}