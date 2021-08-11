package com.example.k_medica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.k_medica.models.Ficha;
import com.example.k_medica.models.FichaAnamnesisRemota;
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

    public static final String URL_BASE = "http://abascur.cl/android/android_5/API/";
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


        listaPaciente = new ArrayList<>();
        getPacientesMedico();

        Bundle bundle = this.getIntent().getExtras();
        rutMedico = bundle.getString("run");
        nombreMedico.setText(bundle.getString("nombre"));

        //se agregan los elementos al spinner//combobox
        String [] ubicacionOpciones = {"Hospital Clínico Regional de Concepción" , "Clínica Sanatorio Alemán","Clínica Biobío"};
        ArrayAdapter<String> adapterUbicacion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ubicacionOpciones);
        spinnerUbicacion.setAdapter(adapterUbicacion);

        spinnerUbicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                System.out.println("Position: "+ position);
                System.out.println("selectedItemView: "+selectedItemView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //se define el comportamiento del botón flotante
        //basicamente para que se puedan agregar usuarios
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioMedico.this, RegistrationPacienteActivity.class);
                Bundle b = new Bundle();
                b.putString("run",rutMedico);
                intent.putExtras(b);
                startActivity(intent);
                //DialogAgregarNota(null);
            }
        });




        adapter = new PacientesListAdapter(listaPaciente, new PacientesListAdapter.OnItemClickListener() {



            public void OnItemClick(/*Nota_Usuario notaUsuario,*/ int position) {
                //DialogAgregarNota(notaUsuario);
            }

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

    //Metodo para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow_menu,menu);
        return true;
    }

    //metodo para asignar las funciones correspondientes a las opciones

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item_1){
            Toast.makeText(this,"Agregar Cerrar Sesión", Toast.LENGTH_SHORT);
        }else if(id == R.id.item_2){
            Toast.makeText(this,"Opcion 2", Toast.LENGTH_SHORT);
        }

        return super.onOptionsItemSelected(item);
    }

    //acá se podrían enviar a una actividad o fragment nuevo, lo dejo a tu criterio
    //private void DialogAgregarNota(Nota_Usuario notaUsuario) {    }

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
        params.put("run_paciente", String.valueOf(run));
        String URL = URL_BASE+"DeletePaciente";
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

        String URL = URL_BASE + "GetAllPaciente";
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
                    //String run = jsonObject.getString("rutUsuario");
                    //if(run.equals(run)){
                        String rut = jsonObject.getString("rut");
                        String nombre = jsonObject.getString("nombre");
                        String correo = jsonObject.getString("correo");
                        String fecha_nacimiento = jsonObject.getString("fecha_nacimiento");
                        String direccion = jsonObject.getString("direccion");
                        String ocupacion = jsonObject.getString("ocupacion");
                        String previcion_salud = jsonObject.getString("previcion_salud");

                        Paciente usuario = new Paciente(nombre, rut, correo, fecha_nacimiento, previcion_salud, direccion, ocupacion,true);



                        listaPaciente.add(usuario);
                    //}
                }

                geFichasUsuarios();
                getAnamnesisRemota();

            }
            System.out.println(listaPaciente.toString());
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

    private void geFichasUsuarios(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String URL = URL_BASE + "GetAllFicha";

        JsonObjectRequest jsonReque = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("JSONPost", response.toString());

                        try {
                            String status = response.getString("status");

                            JSONArray mensaje = response.getJSONArray("mensaje");
                            if (status.equals("success")) {
                                addFichasPacienteRealm(mensaje);
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

    public void addFichasPacienteRealm(JSONArray mensaje) {

        ArrayList<Ficha> listaFichas = new ArrayList<>();

        mRealm.beginTransaction();
        mRealm.delete(Ficha.class);
        mRealm.commitTransaction();

        try {
            if (mensaje.length() > 0) {
                for (int i = 0; i < mensaje.length(); i++) {
                    JSONObject jsonObject = mensaje.getJSONObject(i);

                    String fecha_consulta = jsonObject.getString("fecha_consulta");
                    String motivo = jsonObject.getString("motivo");
                    String plan = jsonObject.getString("plan");
                    String tratamiento = jsonObject.getString("tratamiento");
                    String diagnostico = jsonObject.getString("diagnostico");
                    String lugar = jsonObject.getString("lugar");
                    String medico_run = jsonObject.getString("medico_run");
                    String usuario_run = jsonObject.getString("usuario_run");

                    Ficha ficha = new Ficha(fecha_consulta, motivo, plan, tratamiento, diagnostico,  lugar, medico_run, usuario_run,true);
                    listaFichas.add(ficha);
                }
            }

            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(listaFichas);
            mRealm.commitTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAnamnesisRemota(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String URL = URL_BASE + "GetAllAnamnesisRemota";

        JsonObjectRequest jsonReque = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("JSONPost", response.toString());

                        try {
                            String status = response.getString("status");

                            JSONArray mensaje = response.getJSONArray("mensaje");
                            if (status.equals("success")) {
                                addAnamnesisRemota(mensaje);
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

    public void addAnamnesisRemota(JSONArray mensaje) {

        ArrayList<FichaAnamnesisRemota> listaRemota = new ArrayList<>();

        mRealm.beginTransaction();
        mRealm.delete(FichaAnamnesisRemota.class);
        mRealm.commitTransaction();

        try {
            if (mensaje.length() > 0) {
                for (int i = 0; i < mensaje.length(); i++) {
                    JSONObject jsonObject = mensaje.getJSONObject(i);

                    String antecedentes_morbidos = jsonObject.getString("antecedentes_morbidos");
                    String antecedentes_quirurgicos = jsonObject.getString("antecedentes_quirurgicos");
                    String hospitalizaciones = jsonObject.getString("hospitalizaciones");
                    String antecedentes_familiares = jsonObject.getString("antecedentes_familiares");
                    String alergias = jsonObject.getString("alergias");
                    String alimentacion = jsonObject.getString("alimentacion");
                    Boolean tabaco = jsonObject.getBoolean("tabaco");
                    Boolean alcohol = jsonObject.getBoolean("alcohol");
                    Boolean drogas= jsonObject.getBoolean("drogas");
                    int fichamedica_id =jsonObject.getInt("fichamedica_id");

                    FichaAnamnesisRemota remota= new FichaAnamnesisRemota( antecedentes_morbidos,  antecedentes_quirurgicos,  hospitalizaciones,  antecedentes_familiares,  alergias,  alimentacion,  fichamedica_id,  alcohol,  drogas,  tabaco,  true) ;
                    listaRemota.add(remota);

                }
            }

            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(listaRemota);
            mRealm.commitTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}