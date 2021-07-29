package com.example.k_medica;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.k_medica.models.Medico;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegistroMedico extends AppCompatActivity {

    private Spinner spinnerEspecialidad, spinnerUbicacion;
    private EditText runMedico,nombreMedico,contrasenaMedico,mailMedico;
    private Button btnRegistrar;
    Realm mRealm;

    public static final String URL_BASE ="http://abascur.cl/android/android_5/api/";
    public static final String ACESS_ID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico2);

        spinnerEspecialidad = (Spinner) findViewById(R.id.registroMedico_especialidad);
        String [] especialidadOpciones = {"Medicina general", "Pediaría","Broncopulmonar Adulto","Hematología","Nutriología"};
        ArrayAdapter<String> adapterEspecialidad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,especialidadOpciones);
        spinnerEspecialidad.setAdapter(adapterEspecialidad);

        spinnerUbicacion = (Spinner) findViewById(R.id.registroMedico_ubicacion);
        String [] ubicacionOpciones = {"Hospital Clínico Regional de Concepción" , "Clínica Sanatorio Alemán","Clínica Biobío"};
        ArrayAdapter<String> adapterUbicacion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ubicacionOpciones);
        spinnerUbicacion.setAdapter(adapterUbicacion);

        nombreMedico = findViewById(R.id.registroMedico_nombre);
        runMedico = findViewById(R.id.registroMedico_run);
        contrasenaMedico = findViewById(R.id.registroMedico_contrasena);
        mailMedico = findViewById(R.id.registroMedico_mail);
        btnRegistrar = findViewById(R.id.registroMedico_btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre=nombreMedico.getText().toString();
                String contrasena=contrasenaMedico.getText().toString();
                String run=runMedico.getText().toString();
                String mail = mailMedico.getText().toString();
                String especialidad = spinnerEspecialidad.getSelectedItem().toString();
                String ubicacion = spinnerUbicacion.getSelectedItem().toString();

                if(Utilidades.verificaConexion(getApplication())){
                    if(isValidForm()){
                        if(estaRegistrado(run)){
                            Toast.makeText(getApplicationContext(),"Usuario ya esta registrado",Toast.LENGTH_LONG).show();
                        }else {
                            //Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
                            //saveShared(nombre,password,run);
                            guardarEnRealm(nombre,run,contrasena,mail,especialidad,ubicacion);
                            //sendSecondActivity(nombre,run,password);
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Esta acción necesita conexión a internet, comprueba tu conexión.", Toast.LENGTH_SHORT).show();

                }


            }
        });




    }

    private boolean isValidForm(){
        boolean r=false;
        if(TextUtils.isEmpty(nombreMedico.getText())){
            nombreMedico.setError("El nombre es obligatorio");
        }else if(TextUtils.isEmpty(contrasenaMedico.getText())){
            contrasenaMedico.setError("La contraseña es obligatoria");
        }else if(TextUtils.isEmpty(runMedico.getText())){
            runMedico.setError("El RUN es obligatorio");
        }else if(Utilidades.validarRut(runMedico.getText().toString())){
            runMedico.setError("El RUN es inválido");
        }
        else if(TextUtils.isEmpty(mailMedico.getText())){
            mailMedico.setError("El e-mail es obligatorio");
        }else if(TextUtils.isEmpty(spinnerEspecialidad.getSelectedItem().toString())){
            //spinnerEspecialidad.setError("La contraseña es obligatorio");
            TextView errorText = (TextView)spinnerEspecialidad.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("La especialidad es obligatoria");//changes the selected item text to this
        }else if(TextUtils.isEmpty(spinnerUbicacion.getSelectedItem().toString())){
            //spinnerUbicacion.setError("La contraseña es obligatorio");
            TextView errorText = (TextView)spinnerUbicacion.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("La Ubicación es obligatoria");//changes the selected item text to this
        }
        else{
            r=true;
        }

        return r;
    }

    private boolean estaRegistrado(String run){
        System.out.println(run);
        mRealm = Realm.getDefaultInstance();
        Medico medico = new Medico();
        medico = mRealm.where(Medico.class).equalTo("rut",run).findFirst();
        System.out.println( medico != null ? "hay medico" : "no hay");
        List<Medico> listaMedico=new ArrayList(mRealm.where(Medico.class).findAll());

        System.out.println(listaMedico);

        return medico != null ? true : false;




    }

    private void guardarEnRealm(String nombre,String run, String contrasena, String email,String especialidad, String ubicacion){

        mRealm = Realm.getDefaultInstance();

            System.out.println(nombre+" "+run+" "+contrasena+" "+email+" "+especialidad+" "+ubicacion);

            Medico usuario = new Medico( run,nombre, contrasena, email, especialidad, ubicacion, false);

            mRealm.beginTransaction();
            mRealm.insertOrUpdate(usuario);
            mRealm.commitTransaction();

            SyncbdRemote();
    }

    private void SyncbdRemote(){
        RealmResults<Medico> ListadoNoSync=mRealm.where(Medico.class).equalTo("sendBd",false).findAll();
        if(ListadoNoSync.size()>0){
            for(int i=0;i<ListadoNoSync.size();i++){
                String nombre = ListadoNoSync.get(i).getNombre();
                String run = ListadoNoSync.get(i).getRut();
                String contrasena = ListadoNoSync.get(i).getContraseña();
                String mail = ListadoNoSync.get(i).getEmail();
                String especialidad = ListadoNoSync.get(i).getEspecialidad();
                String ubicacion = ListadoNoSync.get(i).getUbicacion();

                InsertOrUpdate(nombre,run,contrasena,mail,especialidad,ubicacion);
            }
        }
    }



    private void InsertOrUpdate(final String nombre, final String run, final String contrasena, final String mail, final String especialidad, final String ubicacion){

        Map<String, String> params = new HashMap<String, String>();

        params.put("run_medico",run);
        params.put("nombre", nombre);
        params.put("contrasenaUsuario", contrasena);
        params.put("especialidad", especialidad);
        params.put("ubicacion", ubicacion);
        params.put("contrasena",contrasena);
        params.put("correo",mail);

        Toast.makeText(getApplicationContext(), params.toString() , Toast.LENGTH_SHORT).show();

        String URL = URL_BASE+"InsertOrUpdateMedico";
        //Toast.makeText(getApplicationContext(), URL , Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonReque = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("JSONPost", response.toString());
                        try {
                            String status = response.getString("status");
                            String mensaje = response.getString("mensaje");
                            if (status.equals("success")) {
                                UpdateEnviado(run);
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
                System.err.println(error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonReque);
    }

    private void  UpdateEnviado(String run){
        mRealm.beginTransaction();
        Medico usuario = mRealm.where(Medico.class).equalTo("rut",run).findFirst();
        assert usuario!=null;
        usuario.setSendBd(true);
        mRealm.commitTransaction();
        sendInicioMedico();
    }

    public void sendInicioMedico(){
        Intent intent = new Intent(RegistroMedico.this, InicioMedico.class);

        Bundle b =new Bundle();
        b.putString("nombre",nombreMedico.getText().toString());
        b.putString("run",runMedico.getText().toString());
        intent.putExtras(b);

        startActivity(intent);
    }


}