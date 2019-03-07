package com.example.nusantara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private String text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Login(View view) {
        username = (EditText) findViewById(R.id.editusername);
        password = (EditText) findViewById(R.id.editpassword);
        text1 =  username.getText().toString();
        text2 = password.getText().toString();

        if((text1.contains("Username"))&&((text2.contains("password")))) {
            Toast.makeText(this, "Login sukses", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DashboardUser.class);
            startActivity(intent);
        }else if((text1.matches("")||text2.matches(""))){
            Toast.makeText(this,"Isikan Username dan password", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Login gagal/Username password salah", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrasi(View view) {
    }
}
