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

import com.example.k_medica.R;
import com.example.k_medica.adapters.FichaListAdapter;
import com.example.k_medica.adapters.PacientesListAdapter;
import com.example.k_medica.models.Ficha;
import com.example.k_medica.models.Paciente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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

        mRealm = Realm.getDefaultInstance();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ficha ficha = new Ficha( "0000-00-00", "", ""," ", "", "", runMedico, runPaciente, false);
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

            }

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        
    }

    private void getAllfichasUsuario() {

       listaFicha = new ArrayList(mRealm.where(Ficha.class).equalTo("usuario_run",this.runPaciente).findAll());


    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("ON RESUME FICHAS QLERAS");
        listaFicha.clear();
        listaFicha = new ArrayList(mRealm.where(Ficha.class).equalTo("usuario_run",this.runPaciente).findAll());
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

            }

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}