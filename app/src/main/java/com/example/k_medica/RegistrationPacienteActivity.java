package com.example.k_medica;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.k_medica.models.Paciente;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegistrationPacienteActivity extends AppCompatActivity {

    private EditText editRut, editNombre, editContraseña, editEmail, editDireccion, editOcupacion, fecha_nacimiento, prevision_salud;
    private Button btnGuardar;
    Realm mRealm;

    public static final String URL_BASE ="http://abascur.cl/android/android_5/api/";
    public static final String ACESS_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_paciente);

        editNombre = findViewById(R.id.edit1);
        editRut = findViewById(R.id.edit2);
        editEmail = findViewById(R.id.edit3);
        fecha_nacimiento = findViewById(R.id.edit4);
        prevision_salud = findViewById(R.id.edit6);
        editDireccion = findViewById(R.id.edit8);
        editOcupacion = findViewById(R.id.edit7);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFormularioValido()){
                    guardarEnRealm(editNombre.getText().toString(), editRut.getText().toString(), editEmail.getText().toString(), fecha_nacimiento.getText().toString(), prevision_salud.getText().toString(), editDireccion.getText().toString(), editOcupacion.getText().toString());
                }
                    //saveRegistro(editRut.getText().toString(), editNombre.getText().toString(), editContraseña.getText().toString());
            }
        });
    }


    public boolean isFormularioValido() {

        boolean r = false;
        if (TextUtils.isEmpty(editRut.getText().toString().trim())) {
            editRut.setError("Por favor Ingresa un RUT ");
        } else if (TextUtils.isEmpty(editNombre.getText().toString().trim())) {
            editNombre.setError("Por favor Ingresa un Nombre");
        } else if (TextUtils.isEmpty(editEmail.getText().toString().trim())) {
            editEmail.setError("Por favor Ingresa un Email ");
        } else if (TextUtils.isEmpty(fecha_nacimiento.getText().toString().trim())) {
            fecha_nacimiento.setError("Por favor Ingresa una fecha_nacimiento ");
        } else if (TextUtils.isEmpty(editContraseña.getText().toString().trim())) {
            prevision_salud.setError("Por favor Ingresa una prevision_salud ");
        }
        else if (TextUtils.isEmpty(editContraseña.getText().toString().trim())) {
            editDireccion.setError("Por favor Ingresa una editDireccion ");
        }
        else if (TextUtils.isEmpty(editContraseña.getText().toString().trim())) {
            editOcupacion.setError("Por favor Ingresa una editOcupacion ");
        }
        else {
            r = true;
        }

        return r;
    }

    private void guardarEnRealm(String nombre, String rut, String email, String fecha_nacimiento, String prevision_salud, String direccion, String ocupacion){
        mRealm = Realm.getDefaultInstance();

        Paciente paciente = new Paciente(nombre, rut, email, fecha_nacimiento, prevision_salud, direccion, ocupacion, false);
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(paciente);
        mRealm.commitTransaction();
        SyncbdRemote();
    }

    private void SyncbdRemote(){
        RealmResults<Paciente> ListadoNoSync = mRealm.where(Paciente.class).equalTo("sendBD",false).findAll();
        if(ListadoNoSync.size()>0){
            for(int i=0;i<ListadoNoSync.size();i++){
                String nombre = ListadoNoSync.get(i).getNombre();
                String rut = ListadoNoSync.get(i).getRut();
                String email = ListadoNoSync.get(i).getCorreo();
                String fecha_nacimiento = ListadoNoSync.get(i).getFecha_nacimiento();
                String prevision_salud = ListadoNoSync.get(i).getPrevision_salud();
                String direccion = ListadoNoSync.get(i).getDireccion();
                String ocupacion = ListadoNoSync.get(i).getDireccion();

                InsertOrUpdate(nombre, rut, email, fecha_nacimiento, prevision_salud, direccion, ocupacion);
            }
        }
    }

    @SuppressLint("NewApi")
    private void InsertOrUpdate(final String nombre, final String rut, final String email, final String fecha_nacimiento, final String prevision_salud, final String direccion, final String ocupacion){
        DateTimeFormatter dtf = null;

        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();


        Map<String, String> params = new HashMap<String, String>();

        params.put("run_paciente", String.valueOf(rut));
        params.put("nombre", String.valueOf(nombre));
        params.put("correo", String.valueOf(email));
        params.put("fecha_nacimiento", fecha_nacimiento);
        params.put("direccion", direccion);
        params.put("ocupacion", ocupacion);
        params.put("prevision_salud", prevision_salud);

        Toast.makeText(getApplicationContext(), params.toString() , Toast.LENGTH_SHORT).show();
        String URL = URL_BASE+"InsertOrUpdatePaciente";
        //Toast.makeText(getApplicationContext(), URL , Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        System.out.println("URL "+URL);

        JsonObjectRequest jsonReque = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("JSONPost", response.toString());
                        try {
                            String status = response.getString("status");
                            String mensaje = response.getString("mensaje");
                            if (status.equals("success")) {
                                UpdateEnviado(rut);
                            }
                        } catch (JSONException e) {
                            System.out.println("Catch");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d("JSONPost", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonReque);

    }


    private void  UpdateEnviado(String run){
        mRealm.beginTransaction();
        Paciente usuario = mRealm.where(Paciente.class).equalTo("rut",run).findFirst();
        assert usuario!=null;
        usuario.setSendBd(true);
        mRealm.commitTransaction();
        sendInicioMedico();
    }

    public void sendInicioMedico(){
        Intent intent = new Intent(RegistrationPacienteActivity.this, InicioMedico.class);
        startActivity(intent);
    }
}