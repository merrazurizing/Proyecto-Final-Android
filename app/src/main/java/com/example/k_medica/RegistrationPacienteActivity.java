package com.example.k_medica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationPacienteActivity extends AppCompatActivity {

    private EditText editRut, editNombre, editContraseña, editEmail, editEspecialidad, editUbicacion;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_paciente);

        editRut = findViewById(R.id.edit1);
        editNombre = findViewById(R.id.edit2);
        editEmail = findViewById(R.id.edit3);
        editEspecialidad = findViewById(R.id.edit4);
        editUbicacion = findViewById(R.id.edit5);
        editContraseña = findViewById(R.id.edit6);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFormularioValido()){}
                    //saveRegistro(editRut.getText().toString(), editNombre.getText().toString(), editContraseña.getText().toString());
            }
        });
    }


    public boolean isFormularioValido() {

        boolean r = false;
        if (TextUtils.isEmpty(editRut.getText().toString().trim())) {
            editRut.setError("Por favor Ingresa un RUT ");
        } else if (TextUtils.isEmpty(editNombre.getText().toString().trim())) {
            editRut.setError("Por favor Ingresa un Nombre");
        } else if (TextUtils.isEmpty(editEmail.getText().toString().trim())) {
            editNombre.setError("Por favor Ingresa un Email ");
        } else if (TextUtils.isEmpty(editEspecialidad.getText().toString().trim())) {
            editContraseña.setError("Por favor Ingresa una Especialidad ");
        } else if (TextUtils.isEmpty(editUbicacion.getText().toString().trim())) {
            editContraseña.setError("Por favor Ingresa una Ubicacion ");
        } else if (TextUtils.isEmpty(editContraseña.getText().toString().trim())) {
            editContraseña.setError("Por favor Ingresa una Contraseña ");
        }
        else{
            r = true;
        }

        return r;
    }
}