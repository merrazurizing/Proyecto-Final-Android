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
import com.example.k_medica.models.FichaAnamnesisRemota;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AnamnesisProxima extends Fragment {

    private String idFicha;
    private Realm mRealm;
    private Button btn;
    private TextView textView;
    private Ficha ficha;
    private boolean estado=false;

    public AnamnesisProxima(String idFicha) {
        this.idFicha = idFicha;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_plan, container, false);

        btn = v.findViewById(R.id.plan_editar);
        textView = v.findViewById(R.id.plan_editText);

        comportamientoInputs(false);


        setUpRealmConfig();
        mRealm = Realm.getDefaultInstance();
        //ficha = mRealm.where(Ficha.class).equalTo("fichamedica_id",idFicha).findFirst();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comportamientoInputs(!estado);
                estado = !estado;
            }
        });

        return v;
    }

    private void setUpRealmConfig() {

        // Se inicializa realm
        Realm.init(getActivity().getApplicationContext());

        // Configuración por defecto en realm
        RealmConfiguration config = new RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded().
                build();
        Realm.setDefaultConfiguration(config);

    }

    private void comportamientoInputs(boolean bol){
        this.estado = !bol;
        //bol = false todo se desactiva
        //bol = true todo se activa

        textView.setEnabled(bol);

    }
}