package com.example.nusantara;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Registrasi extends AppCompatActivity {
    private static final String TAG_ACTIVITY = Registrasi.class.getSimpleName();

    EditText username;
        EditText email;
        EditText phone;
        RadioGroup Gender;
        EditText Birthday;
        EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

            username = findViewById(R.id.usernameText);
            email = findViewById(R.id.emailText);
            phone = findViewById(R.id.phoneText);
            Gender = findViewById(R.id.Gendergrup);
            Birthday = findViewById(R.id.dateText);
            Password = findViewById(R.id.PasswordText);


    }

    public void SignUp(View view) {
    }}