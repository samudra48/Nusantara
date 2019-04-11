package com.example.nusantara;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class TampilanData extends AppCompatActivity {


    FirebaseFirestore db;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_data);

        img = findViewById(R.id.img_img);

        db = FirebaseFirestore.getInstance();
        db.collection("ItemProvinsi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if (task.isSuccessful() && task.getResult() != null){
                    for (QueryDocumentSnapshot doc: task.getResult()){

                        ItemData tampilan = doc.toObject(ItemData.class);

                        Picasso.get().load(tampilan.img).into(img);

                    }
                }

            }
        });





    }
}
