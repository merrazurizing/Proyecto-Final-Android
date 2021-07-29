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

    private EditText editRut, editNombre, editContraseña, editEmail, editEspecialidad, editUbicacion;
    private Button btnGuardar;
    Realm mRealm;

    public static final String URL_BASE ="";
    public static final String ACESS_ID="";

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

    private void guardarEnRealm(String nombre, String rut, String fecha_nacimiento, String motivo_consulta, String prevision_salud, String ocupacion, String direccion){
        mRealm = Realm.getDefaultInstance();

        Paciente paciente = new Paciente(nombre, rut, fecha_nacimiento, motivo_consulta, prevision_salud, ocupacion, direccion,false);
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
                String fecha_nacimiento = ListadoNoSync.get(i).getFecha_nacimiento();
                String motivo_consulta = ListadoNoSync.get(i).getMotivo_consulta();
                String prevision_salud = ListadoNoSync.get(i).getPrevision_salud();
                String ocupacion = ListadoNoSync.get(i).getOcupacion();
                String direccion = ListadoNoSync.get(i).getDireccion();

                InsertOrUpdate(nombre, rut, fecha_nacimiento, motivo_consulta, prevision_salud, ocupacion, direccion);
            }
        }
    }

    @SuppressLint("NewApi")
    private void InsertOrUpdate(final String nombre, final String rut, final String fecha_nacimiento, final String motivo_consulta, final String prevision_salud, final String ocupacion, final String direccion){
        DateTimeFormatter dtf = null;

        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();


        Map<String, String> params = new HashMap<String, String>();

        params.put("rutUsuario", String.valueOf(rut));
        params.put("nombreUsuario", String.valueOf(nombre));
        params.put("fechaNacimiento", String.valueOf(fecha_nacimiento));
        params.put("motivoConsulta", motivo_consulta);
        params.put("ubicacionUsuario", prevision_salud);
        params.put("ocupacion", ocupacion);
        params.put("direccion", direccion);
        params.put("idAcceso",ACESS_ID);

        Toast.makeText(getApplicationContext(), params.toString() , Toast.LENGTH_SHORT).show();

        String URL = URL_BASE+"InsertOrUpdateUsuario";
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
        Paciente usuario = mRealm.where(Paciente.class).equalTo("run",run).findFirst();
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