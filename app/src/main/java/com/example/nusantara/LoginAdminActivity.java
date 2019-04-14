package com.example.nusantara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginAdminActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;
    private Button LoginAdmin;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            Intent intent = new Intent(LoginAdminActivity.this,AdminDashboardActivity.class);
            startActivity(intent);
        }
        else {
            counter--;

            if(counter == 0){
                LoginAdmin.setEnabled(false);
            }
        }
    }
}
