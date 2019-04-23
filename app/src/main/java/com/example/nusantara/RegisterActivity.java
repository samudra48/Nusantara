package com.example.nusantara;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPass;
    Button btnRegist, btnLogin;
    String mMail, mName, mPass;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;


    final String PREF_NIGHT_MODE = "NightMode";
    SharedPreferences spNight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        spNight = getSharedPreferences(PREF_NIGHT_MODE , Context.MODE_PRIVATE);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);

            if(spNight.getBoolean(PREF_NIGHT_MODE,false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            startActivity(new Intent(RegisterActivity.this, DashboardUser.class));
            finish();
        }

        etName = findViewById(R.id.editName);
        etEmail = findViewById(R.id.edit_user);
        etPass = findViewById(R.id.edit_pass);
        btnLogin = findViewById(R.id.buttonLoginAdmin);
        btnRegist = findViewById(R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyInput();
            }
        });

    }

    private void verifyInput() {
        mMail = etEmail.getText().toString().trim();
        mName = etName.getText().toString().trim();
        mPass = etPass.getText().toString().trim();

        if (mName.isEmpty()){
            toast("Enter your name");
            return;
        }
        if (mMail.isEmpty()){
            toast("Enter your email");
            return;
        }
        if (mPass.isEmpty()){
            toast("Enter your password");
            return;
        }
        if (mPass.length() < 6){
            toast("Password minimal 6 character");
            return;
        }

        registerNewUser(mMail, mPass);

    }

    private void registerNewUser(String mMail, String mPass) {
        mAuth.createUserWithEmailAndPassword(mMail, mPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            toast("Successfully register user with email" + user.getEmail());
                            startActivity(new Intent(RegisterActivity.this, DashboardUser.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, task.getResult().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void loginAdmin(View view) {
        Intent intent = new Intent(RegisterActivity.this,LoginAdminActivity.class);
        startActivity(intent);
    }
}
