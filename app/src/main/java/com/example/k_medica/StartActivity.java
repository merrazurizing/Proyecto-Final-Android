package com.example.k_medica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_main);

        Realm mRealm;
        setUpRealmConfig();

        sendLoginMedico();
    }

    private void sendLoginMedico(){
        Intent intent = new Intent(StartActivity.this, LoginMedico.class);
        startActivity(intent);
    }

    private void setUpRealmConfig() {

        // Se inicializa realm
        Realm.init(this.getApplicationContext());

        // Configuración por defecto en realm
        RealmConfiguration config = new RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded().
                build();
        Realm.setDefaultConfiguration(config);

    }
}