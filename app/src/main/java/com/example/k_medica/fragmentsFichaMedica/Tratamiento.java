package com.example.k_medica.fragmentsFichaMedica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.k_medica.R;
import com.example.k_medica.models.Ficha;

import io.realm.Realm;


public class Tratamiento extends Fragment {

   private boolean estado=false;
   private String idFicha;
   private Ficha ficha;
   private TextView textView;
   private Button btn;
    private Realm mRealm;


    public Tratamiento(String idFicha) {
        this.idFicha = idFicha;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_tratamiento, container, false);
        btn = v.findViewById(R.id.tratamiento_editar);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(estado);
                comportamientoInputs(!estado);
                estado = !estado;

            }
        });

        return v;
    }

    private void comportamientoInputs(boolean bol){
        this.estado = !bol;
        //bol = false todo se desactiva
        //bol = true todo se activa

        textView.setEnabled(bol);


        }
    }
