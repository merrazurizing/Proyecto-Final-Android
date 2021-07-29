package com.example.k_medica;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class IngresoMedico extends AppCompatActivity {

    private EditText run,contrasena;
    private Button botonIngreso;
    private Realm mReal;
    public static final String URL_BASE ="";
    public static final String ACESS_ID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_medico);

        run = findViewById(R.id.ingresoMedico_run);
        contrasena = findViewById(R.id.ingresoMedico_contrasena);
        botonIngreso = findViewById(R.id.ingresoMedico_btnIngreso);

        botonIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String runMedico = run.getText().toString();
                String contrasenaMedico = contrasena.getText().toString();
                if(isValidForm()){
                    validarUsuario(runMedico,contrasenaMedico);
                }
                else{
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private boolean isValidForm(){
        boolean r=false;
        if(TextUtils.isEmpty(run.getText())){
            run.setError("El RUN es obligatorio");
        }else if(TextUtils.isEmpty(contrasena.getText())){
            contrasena.setError("La contraseña es obligatoria");
        }else{
            r=true;
        }
        return r;
    }

    private void validarUsuario(final String run,final String contrasena) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("rutUsuario", run);
        params.put("idAcceso",ACESS_ID);


        // Toast.makeText(getApplicationContext(), params.toString() , Toast.LENGTH_SHORT).show();
        String URL = URL_BASE+"GetUsuario";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonReque = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String status = response.getString("status");
                            JSONObject mensaje = response.getJSONObject("mensaje");

                            if (status.equals("success")) {

                                if(run.equals(mensaje.getString("run_medico")) && contrasena.equals(mensaje.getString("contrasena"))){

                                    sendInicioMedico(mensaje.getString("run_medico"),mensaje.getString("nombre_medico"));
                                }else{
                                    Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonReque);

    }

    private void sendInicioMedico(String run, String nombre){
        Intent intent = new Intent(IngresoMedico.this, InicioMedico.class);

        Bundle b =new Bundle();
        b.putString("nombre",nombre);
        b.putString("run",run);
        intent.putExtras(b);

        startActivity(intent);
    }
}