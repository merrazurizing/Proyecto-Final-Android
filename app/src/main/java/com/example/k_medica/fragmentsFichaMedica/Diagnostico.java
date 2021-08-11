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


public class Diagnostico extends Fragment {

    private String idFicha;
    private Realm mRealm;
    private Button btn;
    private TextView textView;
    private Ficha ficha;
    private boolean estado;

    public Diagnostico(String idFicha) {
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


        View v=inflater.inflate(R.layout.fragment_diagnostico, container, false);
        btn = v.findViewById(R.id.diagnostico_editar);
        textView = v.findViewById(R.id.diagnostico_editText);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diagnostico, container, false);
    }

    private void comportamientoInputs(boolean bol){
        this.estado = !bol;
        //bol = false todo se desactiva
        //bol = true todo se activa

        textView.setEnabled(bol);


        if(!bol){
            //si bol es falso, pues se desactivó el botón y habría que guardar las cosas en realm
            //en teoría esto puede quedar en blanco

            ficha.setTratamiento(textView.getText().toString());
            ficha.setSendBd(false);

            mRealm.beginTransaction();
            mRealm.insertOrUpdate(ficha);
            mRealm.commitTransaction();

        }
    }
}