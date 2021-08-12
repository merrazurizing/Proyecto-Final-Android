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
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.k_medica.R;
import com.example.k_medica.adapters.FichaListAdapter;
import com.example.k_medica.adapters.PacientesListAdapter;
import com.example.k_medica.models.Ficha;
import com.example.k_medica.models.Paciente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class fichasPaciente extends AppCompatActivity {

    private Spinner spinnerUbicacion;
    private FloatingActionButton floatingActionButton;
    private TextView nombreMedico;
    private RecyclerView recyclerView;
    private FichaListAdapter adapter;
    Realm mRealm;

    private String runMedico;
    private String runPaciente;
    private String nombrePaciente;

    private ArrayList<Ficha> listaFicha;

    public static final String URL_BASE = "http://abascur.cl/android/android_5/API/";
    public static final String ACESS_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichas_paciente);

        mRealm = Realm.getDefaultInstance();


        recyclerView = findViewById(R.id.fichas_RV);
        listaFicha = new ArrayList<>();
        floatingActionButton = findViewById(R.id.ficha_floatingActionButton);

        Bundle bundle = this.getIntent().getExtras();
        runMedico = bundle.getString("runMedico");
        runPaciente = bundle.getString("runPaciente");
        nombrePaciente= bundle.getString("nombrePaciente");

        getAllfichasUsuario();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ficha ficha = new Ficha( "2021-08-11", "", ""," ", "", "", runMedico, runPaciente, false);
                mRealm.beginTransaction();
                mRealm.insertOrUpdate(ficha);
                mRealm.commitTransaction();


                Intent intent = new Intent(fichasPaciente.this, FichaMedica.class);
                Bundle b = new Bundle();
                b.putInt("idFicha",ficha.getId());
                intent.putExtras(b);
                startActivity(intent);
                //DialogAgregarNota(null);
            }
        });

        adapter = new FichaListAdapter(listaFicha, new FichaListAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(Ficha ficha, int position) {
                System.out.println(ficha);
                Log.i("HOLA",ficha.toString());

                Intent intent = new Intent(fichasPaciente.this, FichaMedica.class);
                Bundle b = new Bundle();
                b.putInt("idFicha",ficha.getId());
                intent.putExtras(b);
                startActivity(intent);

            }

            @Override
            public void OnDeleteClick(Ficha ficha, int position) {

                AlertDialog alertDialog = new AlertDialog.Builder(fichasPaciente.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("¿Esta seguro de eliminar a " + ficha.getId() + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteFicha(ficha);
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

    private void getAllfichasUsuario() {

       listaFicha = new ArrayList(mRealm.where(Ficha.class).equalTo("usuario_run",this.runPaciente).findAll());

    }

    public void deleteFicha(Ficha ficha) {
        if(Utilidades.verificaConexion(getApplication())) {
            DeleteBD(String.valueOf(ficha.getId()));
            mRealm.beginTransaction();
            mRealm.where(Ficha.class).equalTo("id", ficha.getId()).findFirst().deleteFromRealm();
            mRealm.commitTransaction();
            getAllfichasUsuario();
            Toast.makeText(getApplicationContext(), "Eliminado de forma correcta.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Esta acción necesita conexión a internet, comprueba tu conexión.", Toast.LENGTH_SHORT).show();

        }
    }

    private void DeleteBD(final String id) {
        Map<String, String> params = new HashMap<String, String>();
        String URL = URL_BASE+"DeleteFichaMedica";
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

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("ON RESUME FICHAS QLERAS");
        listaFicha.clear();
        listaFicha = new ArrayList(mRealm.where(Ficha.class).equalTo("usuario_run",this.runPaciente).findAll());
        System.out.println(listaFicha);
        System.out.println(listaFicha.size());
        adapter = new FichaListAdapter(listaFicha, new FichaListAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(Ficha ficha, int position) {
                System.out.println(ficha);
                Log.i("HOLA",ficha.toString());

                Intent intent = new Intent(fichasPaciente.this, FichaMedica.class);
                Bundle b = new Bundle();
                b.putInt("idFicha",ficha.getId());
                intent.putExtras(b);
                startActivity(intent);

            }

            @Override
            public void OnDeleteClick(Ficha ficha, int position) {

                AlertDialog alertDialog = new AlertDialog.Builder(fichasPaciente.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("¿Esta seguro de eliminar a " + ficha.getId() + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteFicha(ficha);
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
}