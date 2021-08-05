package com.example.k_medica.fragmentsFichaMedica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.k_medica.R;
import com.example.k_medica.models.FichaAnamnesisRemota;
import com.example.k_medica.models.Paciente;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AnamnesisRemota extends Fragment {

    private String idFichaMedica;
    private TextView morbidos,quirurgicos,hospitalizaciones,alergias,alimentacion;
    private RadioButton alcohol,drogas,tabaco;
    private FichaAnamnesisRemota remota;
    private String idFicha;
    private Realm mRealm;



    public AnamnesisRemota(String idFicha) {
        this.idFicha = idFicha;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_anamnesis_remota, container, false);

        setUpRealmConfig();
        mRealm = Realm.getDefaultInstance();

        remota = mRealm.where(FichaAnamnesisRemota.class).equalTo("fichamedica_id",idFicha).findFirst();

        morbidos = v.findViewById(R.id.remota_morbidos);
        quirurgicos = v.findViewById(R.id.remota_quirurgicos);
        hospitalizaciones = v.findViewById(R.id.remota_hospitalizaciones);
        alergias=v.findViewById(R.id.remota_alergias);
        alimentacion= v.findViewById(R.id.remota_alimentacion);

        alcohol=v.findViewById(R.id.radioButton);
        drogas=v.findViewById(R.id.radioButton2);
        tabaco=v.findViewById(R.id.radioButton3);


        morbidos.setText(remota.getAntecedentes_morbidos());
        quirurgicos.setText(remota.getAntecedentes_quirurgicos());
        hospitalizaciones.setText(remota.getHospitalizaciones());
        alergias.setText(remota.getAlergias());
        alimentacion.setText(remota.getAlimentacion());

        alcohol.setChecked(remota.isAlcohol());
        drogas.setChecked(remota.isDrogas());
        tabaco.setChecked(remota.isTabaco());



        return inflater.inflate(R.layout.fragment_anamnesis_remota, container, false);
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
}