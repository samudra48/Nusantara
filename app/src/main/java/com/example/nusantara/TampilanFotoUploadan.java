package com.example.nusantara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class TampilanFotoUploadan extends AppCompatActivity {

    ImageView img_uplodan;
    TextView tv_captionuploadan, tv_captionlain;
    EditText mComment;
    Button mSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_foto_uploadan);

        img_uplodan= findViewById(R.id.img_upload);
        tv_captionuploadan= findViewById(R.id.tv_captionan );
        tv_captionlain = findViewById(R.id.tv_capsionlain);
        mComment = (EditText) findViewById(R.id.comment);
        mSend = (Button) findViewById(R.id.send);



        if (getIntent() != null){
            String nameA = getIntent().getStringExtra("nama");
            String descB = getIntent().getStringExtra("deskripsi");
            String gambarC = getIntent().getStringExtra("gambar");

            if (!nameA.isEmpty()){
                tv_captionlain.setText(nameA);
            }
            if (!descB.isEmpty()){
                tv_captionuploadan.setText(descB);
            }
            if (!gambarC.isEmpty()){
                Picasso.get().load(gambarC).into(img_uplodan);
            }
            mSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mSend = mComment.getText().toString();
                    Toast.makeText(TampilanFotoUploadan.this, mSend, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
