package com.example.nusantara;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    final String PREF_NIGHT_MODE = "NightMode";
    SharedPreferences spNight;


    TextView id_emaile;
    FirebaseAuth email_yangdiambil;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        spNight = getContext().getSharedPreferences(PREF_NIGHT_MODE , Context.MODE_PRIVATE);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getContext().setTheme(R.style.DarkTheme);
        }else{
            getContext().setTheme(R.style.AppTheme);

            if(spNight.getBoolean(PREF_NIGHT_MODE,false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        id_emaile= view.findViewById(R.id.id_email);

        email_yangdiambil=FirebaseAuth.getInstance();


        String emailnya =  email_yangdiambil.getCurrentUser().getEmail();

        id_emaile.setText(emailnya);

    }

}
