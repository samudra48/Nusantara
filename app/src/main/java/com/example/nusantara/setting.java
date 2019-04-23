package com.example.nusantara;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class setting extends AppCompatActivity {

    Button btnSave;
    Switch swNight, swFontSize;
    SharedPreferences spFont, spNight;

    final String PREF_NIGHT_MODE = "NightMode";
    final String PREF_FONT_SIZE = "BigSize";

    SharedPreferences.Editor editNight, editFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);

            spNight = getSharedPreferences(PREF_NIGHT_MODE , Context.MODE_PRIVATE);
            if(spNight.getBoolean(PREF_NIGHT_MODE,false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnSave = findViewById(R.id.btn_save_setting);
        swNight = findViewById(R.id.sw_night_mode);
        swFontSize = findViewById(R.id.sw_font_size);

        spFont = getSharedPreferences(PREF_FONT_SIZE, Context.MODE_PRIVATE);

        if (spFont.getBoolean(PREF_FONT_SIZE, false)){
            swFontSize.setChecked(true);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfig();
            }
        });

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            swNight.setChecked(true);
        }
        swNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    private void saveConfig() {
        if (swFontSize.isChecked()){
            editFont = spFont.edit();
            editFont.putBoolean(PREF_FONT_SIZE, true);
            editFont.apply();
        }

        if (swNight.isChecked()){
            editNight = spNight.edit();
            editNight.putBoolean(PREF_NIGHT_MODE, true);
            editNight.apply();
        }

        Intent i = new Intent(setting.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
