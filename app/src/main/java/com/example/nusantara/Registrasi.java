package com.example.nusantara;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import modal.User;
import sql.DatabaseHelper;

public class Registrasi extends AppCompatActivity {
    private static final String TAG_ACTIVITY = Registrasi.class.getSimpleName();

    TextView textView12;
    TextView textView13;
    TextView textView_name;
    TextView textView_email;
    TextView textView_psw;
    TextView kp;


    TextView textView_login;
    EditText editText_name;
    EditText editText_email;
    EditText editText_kp;
    EditText editText_psw;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        getSupportActionBar().hide();

        initView();
        initListener();



    }

    private void initListener() {
        register.setOnClickListener((View.OnClickListener) this);
    }


    private void initView() {
        textView12 = (TextView) findViewById(R.id.textView12);
        textView_name = (TextView) findViewById(R.id.textView_name);
        textView_email = (TextView) findViewById(R.id.textView_email);
        textView_psw = (TextView) findViewById(R.id.textView_psw);
        kp = (TextView) findViewById(R.id.kp);
        textView13 = (TextView) findViewById(R.id.textView13);
        textView_login = (TextView) findViewById(R.id.textView_login);
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_kp = (EditText) findViewById(R.id.editText_kp);
        editText_psw = (EditText) findViewById(R.id.editText_psw);
        register = (Button) findViewById(R.id.register);


    }



    }



