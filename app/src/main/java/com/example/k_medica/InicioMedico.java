package com.example.k_medica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.k_medica.adapters.PacientesListAdapter;
import com.example.k_medica.models.Paciente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class InicioMedico extends AppCompatActivity {

    private Spinner spinnerUbicacion;
    private FloatingActionButton floatingActionButton;
    private TextView nombreMedico;
    private RecyclerView recyclerView;
    private PacientesListAdapter adapter;
    Realm mRealm;

    private String rutMedico;

    private ArrayList<Paciente> listaPaciente;

    public static final String URL_BASE = "";
    public static final String ACESS_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mRealm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_medico);

        nombreMedico = findViewById(R.id.InicioMedico_nombreMedico);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        spinnerUbicacion = (Spinner) findViewById(R.id.InicioMedico_spinnerubicacion);
        recyclerView = findViewById(R.id.recyclerView);


        listaPaciente = new ArrayList<Paciente>();
        getPacientesMedico();

        Bundle bundle = this.getIntent().getExtras();
        rutMedico = bundle.getString("run");
        nombreMedico.setText(bundle.getString("nombre"));



        //se agregan los elementos al spinner//combobox
        String [] ubicacionOpciones = {"Hospital Clínico Regional de Concepción" , "Clínica Sanatorio Alemán","Clínica Biobío"};
        ArrayAdapter<String> adapterUbicacion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ubicacionOpciones);
        spinnerUbicacion.setAdapter(adapterUbicacion);

        //se define el comportamiento del botón flotante
        //basicamente para que se puedan agregar usuarios
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAgregarNota(null);
            }
        });




        adapter = new PacientesListAdapter(listaPaciente, new PacientesListAdapter.OnItemClickListener() {

            /*
            @Override
            public void OnItemClick(Nota_Usuario notaUsuario, int position) {
                DialogAgregarNota(notaUsuario);
            }
*           */
            @Override
            public void OnDeleteClick(Paciente paciente, int position) {
                //Toast.makeText(getApplicationContext(),"itemDelete:"+alumno.getNombre(),Toast.LENGTH_LONG).show();

                AlertDialog alertDialog = new AlertDialog.Builder(InicioMedico.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("¿Esta seguro de eliminar a " + paciente.getNombre() + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePaciente(paciente);
                        adapter.removeItem(position);
                        dialogInterface.dismiss();

                    }
                });
                alertDialog.show();


            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    //acá se podrían enviar a una actividad o fragment nuevo, lo dejo a tu criterio
    private void DialogAgregarNota(Nota_Usuario notaUsuario) {

    }

    public void deletePaciente(Paciente paciente) {
        if(Utilidades.verificaConexion(getApplication())) {
            DeleteBD(String.valueOf(paciente.getId()),paciente.getRut());
            mRealm.beginTransaction();
            mRealm.where(Paciente.class).equalTo("id", paciente.getId()).findFirst().deleteFromRealm();
            mRealm.commitTransaction();
            getPacientesMedico();
            Toast.makeText(getApplicationContext(), "Eliminado de forma correcta.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Esta acción necesita conexión a internet, comprueba tu conexión.", Toast.LENGTH_SHORT).show();

        }
    }

    private void DeleteBD(final String id, final String run) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("idNota", String.valueOf(id));
        params.put("rutUsuario", String.valueOf(run));
        params.put("idAcceso", String.valueOf(ACESS_ID));



        deleteNota //cambiar eso por lo que corresponda
        String URL = URL_BASE+"DeleteNota";
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
                                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                                /*alumnosAppV2: Se actualiza en realm el estado*/
                                // UpdateEnviado(rut);
                            }
                        } catch (JSONException e) {
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

    private void getPacientesMedico(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        getAllnotas //cambiar eso por que corresponda
        String URL = URL_BASE + "GetAllNotas/18808222";
        JsonObjectRequest jsonReque = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("JSONPost", response.toString());

                        try {
                            String status = response.getString("status");

                            JSONArray mensaje = response.getJSONArray("mensaje");
                            if (status.equals("success")) {
                                add_paciente_list_to_realm(mensaje);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error no se puedo realizar la consulta", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d("JSONPost", "Error: " + error.getMessage());
            }
        });

        queue.add(jsonReque);

    }

    public void add_paciente_list_to_realm(JSONArray mensaje){
        listaPaciente.clear();
        mRealm.beginTransaction();
        mRealm.delete(Paciente.class);
        mRealm.commitTransaction();
        try {
            if (mensaje.length() > 0) {
                for (int i = 0; i < mensaje.length(); i++) {

                    JSONObject jsonObject = mensaje.getJSONObject(i);
                    String run = jsonObject.getString("rutUsuario");
                    if(run.equals(run)){
                        String id = jsonObject.getString("idNota");
                        String nombre = jsonObject.getString();
                        String rut = jsonObject.getString();
                        String fecha_nacimiento = jsonObject.getString();
                        String motivo_consulta = jsonObject.getString();
                        String prevision_salud = jsonObject.getString();
                        String ocupacion = jsonObject.getString();
                        Integer direccion = Integer.valueOf(jsonObject.getString());
                        listaPaciente.add(new Paciente(nombre,rut,fecha_nacimiento,motivo_consulta, prevision_salud, ocupacion, direccion,id);
                    }
                }
            }
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(listaPaciente);
            mRealm.commitTransaction();
            //mSwipeRefreshLayout.setRefreshing(false);
            adapter.updateList(listaPaciente);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}