package com.example.k_medica.fragmentsFichaMedica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button btn;
    private boolean estado;


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

        btn = v.findViewById(R.id.remota_btn);
        comportamientoInputs(false);

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               comportamientoInputs(estado);
            }
        });


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

    private void comportamientoInputs(boolean bol){
        this.estado = !bol;
        //bol = false todo se desactiva
        //bol = true todo se activa

        morbidos.setEnabled(bol);
        quirurgicos.setEnabled(bol);
        hospitalizaciones.setEnabled(bol);
        alergias.setEnabled(bol);
        alimentacion.setEnabled(bol);
        drogas.setEnabled(bol);
        alcohol.setEnabled(bol);
        tabaco.setEnabled(bol);

        if(!bol){
            //si bol es falso, pues se desactivó el botón y habría que guardar las cosas en realm
            //en teoría esto puede quedar en blanco

            remota.setAntecedentes_morbidos(morbidos.getText().toString());
            remota.setAntecedentes_quirurgicos(quirurgicos.getText().toString());
            remota.setHospitalizaciones(hospitalizaciones.getText().toString());
            remota.setAlergias(alergias.getText().toString());
            remota.setAlimentacion(alimentacion.getText().toString());

            remota.setDrogas(drogas.isChecked());
            remota.setAlcohol(alcohol.isChecked());
            remota.setTabaco(tabaco.isChecked());

            remota.setSendBd(false);

            mRealm.beginTransaction();
            mRealm.insertOrUpdate(remota);
            mRealm.commitTransaction();

        }
    }


}