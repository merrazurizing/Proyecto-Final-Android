package com.example.k_medica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.k_medica.adapters.UsuariosListAsapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.realm.Realm;

public class InicioMedico extends AppCompatActivity {

    private Spinner spinnerUbicacion;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private UsuariosListAsapter adapter;
    Realm mRealm;

    private ArrayList<Nota_Usuario> lista_notas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mRealm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_medico);


        spinnerUbicacion = (Spinner) findViewById(R.id.InicioMedico_spinnerubicacion);
        String [] ubicacionOpciones = {"Hospital Clínico Regional de Concepción" , "Clínica Sanatorio Alemán","Clínica Biobío"};
        ArrayAdapter<String> adapterUbicacion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ubicacionOpciones);
        spinnerUbicacion.setAdapter(adapterUbicacion);


        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAgregarNota(null);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);


        adapter = new UsuariosListAsapter(lista_notas, new UsuariosListAsapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Nota_Usuario notaUsuario, int position) {
                DialogAgregarNota(notaUsuario);
            }

            @Override
            public void OnDeleteClick(Nota_Usuario nota, int position) {
                //Toast.makeText(getApplicationContext(),"itemDelete:"+alumno.getNombre(),Toast.LENGTH_LONG).show();

                AlertDialog alertDialog = new AlertDialog.Builder(Lista_Notas_Usuario.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("¿Esta seguro de eliminar a " + nota.getNombre() + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteNota(nota);
                        adapter.removeItem(position);
                        dialogInterface.dismiss();

                    }
                });
                alertDialog.show();


            }
            recyclerView.
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);



        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void DialogAgregarNota(Nota_Usuario notaUsuario) {

    }
}