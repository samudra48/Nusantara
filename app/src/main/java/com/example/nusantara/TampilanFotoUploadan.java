package com.example.nusantara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nusantara.model.Comment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class TampilanFotoUploadan extends AppCompatActivity {

    ImageView img_uplodan;
    TextView tv_captionuploadan, tv_captionlain;
    EditText mComment;
    Button mSend;
    ArrayList<Comment> commentList;
    String uidD;

    RecyclerView comm;
    AdapterComment comment;

    //objek instance
    FirebaseAuth mAuth;
    CollectionReference databaseComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_foto_uploadan);

        img_uplodan= findViewById(R.id.img_uploadadmin);
        tv_captionuploadan= findViewById(R.id.tv_captionan );
        tv_captionlain = findViewById(R.id.tv_capsionlain);
        mComment = (EditText) findViewById(R.id.comment);
        mSend = (Button) findViewById(R.id.send);

        commentList = new ArrayList<>();

        comm = findViewById(R.id.rv_comment);
        comm.setLayoutManager(new LinearLayoutManager(this));
        comment = new AdapterComment(this, commentList);
        comm.setAdapter(comment);


        mAuth = FirebaseAuth.getInstance();
        databaseComments = FirebaseFirestore.getInstance().collection("comment");


        if (getIntent() != null){
            String nameA = getIntent().getStringExtra("nama");
            String descB = getIntent().getStringExtra("deskripsi");
            String gambarC = getIntent().getStringExtra("gambar");
            uidD = getIntent().getStringExtra("uid");

            if (!nameA.isEmpty()){
                tv_captionlain.setText(nameA);
            }
            if (!descB.isEmpty()){
                tv_captionuploadan.setText(descB);
            }
            if (!gambarC.isEmpty()){
                Picasso.get().load(gambarC).into(img_uplodan);
            }

        }

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mSend = mComment.getText().toString();

                if (!TextUtils.isEmpty(mSend)) {
                    long timestamp = System.currentTimeMillis();
                    Comment track = new Comment(uidD, mAuth.getCurrentUser().getDisplayName(), mSend, timestamp * (-1));
                    databaseComments.add(track);
                    Toast.makeText(TampilanFotoUploadan.this, mSend + "posted", Toast.LENGTH_SHORT).show();

                    mComment.setText("");
                } else {

                    Toast.makeText(TampilanFotoUploadan.this, "Please enter comment first", Toast.LENGTH_SHORT).show();
                }
            }

        });

        Log.d("PTK", "onCreate: " + uidD);

        databaseComments.whereEqualTo("id",uidD).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                commentList.clear();
                if (queryDocumentSnapshots != null) {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Comment data = doc.toObject(Comment.class);
                        commentList.add(data);

                        comment.notifyDataSetChanged();

                    }
                }
            }
        });

    }
}
