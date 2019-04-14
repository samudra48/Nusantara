package com.example.nusantara;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button LogInButton, RegisterButton;
    FirebaseAuth mAuth;
    String mMail, mPass;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            startActivity(new Intent(MainActivity.this, DashboardUser.class));
            finish();
        }

        LogInButton = (Button) findViewById(R.id.buttonLoginAdmin);
        RegisterButton = (Button) findViewById(R.id.buttonRegister);
        mEmail = (EditText) findViewById(R.id.edit_user);
        mPassword = (EditText) findViewById(R.id.edit_pass);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling EditText is empty or no method.
                userSign();

            }
        });

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Opening new user registration activity using intent on button click.
                finish();

            }
        });
    }

    private void userSign() {
        mMail = mEmail.getText().toString().trim();
        mPass = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mMail)) {
            Toast.makeText(MainActivity.this, "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } if (TextUtils.isEmpty(mPass)) {
            Toast.makeText(MainActivity.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Loging in please wait...");
        dialog.setIndeterminate(true);
        dialog.show();

        mAuth.signInWithEmailAndPassword(mMail, mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();

                    Intent intent = new Intent(MainActivity.this, DashboardUser.class);
                    startActivity(intent);
                } else {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

