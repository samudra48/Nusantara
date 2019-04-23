package com.example.nusantara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginAdminActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;
    private Button LoginAdmin;
    private int counter = 5;

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
        setContentView(R.layout.activity_login_admin);

        Username = (EditText) findViewById(R.id.edit_user);
        Password = (EditText) findViewById(R.id.edit_pass);
        LoginAdmin = (Button) findViewById(R.id.buttonLoginAdmin);
        LoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAdminActivity.this,AdminDashboardActivity.class);
                startActivity(intent);
                validate(Username.getText().toString(),Password.getText().toString());
            }
        });

    }
    private void validate(String username,String password){
        if ((username == "Nusantara") && (password == "NusantaraApps")){
        }
        else {
            counter--;

            if(counter == 0){
                LoginAdmin.setEnabled(false);
            }
        }
    }
}
