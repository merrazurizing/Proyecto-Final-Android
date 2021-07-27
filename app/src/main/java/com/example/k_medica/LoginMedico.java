package com.example.k_medica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginMedico extends AppCompatActivity {

    private Button btnIngreso,btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_medico);

        btnIngreso = findViewById(R.id.button_ingreso);
        btnRegistro = findViewById(R.id.button_registro);

        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIngresoMedico();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRegistroMedico();
            }
        });
    }

    private void sendIngresoMedico(){
        Intent intent = new Intent(LoginMedico.this, IngresoMedico.class);
        startActivity(intent);
    }

    private void sendRegistroMedico(){
        Intent intent = new Intent(LoginMedico.this, RegistroMedico.class);
        startActivity(intent);
    }
}