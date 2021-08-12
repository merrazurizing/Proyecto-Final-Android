package com.example.k_medica.fragmentsFichaMedica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.k_medica.R;
import com.example.k_medica.models.FichaAnamnesisRemota;
import com.example.k_medica.models.Paciente;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AnamnesisRemota extends Fragment {

    private static String idFichaMedica;
    private static EditText morbidos,quirurgicos,hospitalizaciones,alergias,alimentacion;
    private static RadioButton alcohol,drogas,tabaco;
    private static FichaAnamnesisRemota remota;
    private static String idFicha;
    private Realm mRealm;
    private static Button btn;
    private static boolean estado=false;


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

        remota = mRealm.where(FichaAnamnesisRemota.class).equalTo("fichamedica_id",Integer.valueOf(idFicha)).findFirst();
        System.out.println("Remota fichaId"+idFicha);
        System.out.println("Remota obj:" +remota);
        if(remota == null){
            System.out.println("Remota es null y se creó una ficha");

            remota = new FichaAnamnesisRemota( "", "", "", "", "", "", Integer.valueOf(idFicha), false, false, false, false);
            mRealm.beginTransaction();
            mRealm.insertOrUpdate(remota);
            mRealm.commitTransaction();
        }
        btn = v.findViewById(R.id.remota_btn);

        morbidos = v.findViewById(R.id.remota_morbidos);
        quirurgicos = v.findViewById(R.id.remota_quirurgicos);
        hospitalizaciones = v.findViewById(R.id.remota_hospitalizaciones);
        alergias=v.findViewById(R.id.remota_alergias);
        alimentacion= v.findViewById(R.id.remota_alimentacion);

        alcohol=v.findViewById(R.id.radioButton);
        drogas=v.findViewById(R.id.radioButton2);
        tabaco=v.findViewById(R.id.radioButton3);

        comportamientoInputs(false);

        if(remota!=null){
            morbidos.setText(remota.getAntecedentes_morbidos());
            quirurgicos.setText(remota.getAntecedentes_quirurgicos());
            hospitalizaciones.setText(remota.getHospitalizaciones());
            alergias.setText(remota.getAlergias());
            alimentacion.setText(remota.getAlimentacion());

            alcohol.setChecked(remota.isAlcohol());
            drogas.setChecked(remota.isDrogas());
            tabaco.setChecked(remota.isTabaco());
        }


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

        //bol = false todo se desactiva
        //bol = true todo se activa
        System.out.println(bol);

        morbidos.setEnabled(bol);
        quirurgicos.setEnabled(bol);
        hospitalizaciones.setEnabled(bol);
        alergias.setEnabled(bol);
        alimentacion.setEnabled(bol);
        drogas.setEnabled(bol);
        alcohol.setEnabled(bol);
        tabaco.setEnabled(bol);

        if(this.estado){
            //si es falso, pues se desactivó el botón y habría que guardar las cosas en realm
            //en teoría esto puede quedar en blanco
            mRealm.beginTransaction();
            remota.setAntecedentes_morbidos(String.valueOf(morbidos.getText()));
            remota.setAntecedentes_quirurgicos(String.valueOf(quirurgicos.getText()));
            remota.setHospitalizaciones(String.valueOf(hospitalizaciones.getText()));
            remota.setAlergias(String.valueOf(alergias.getText()));
            remota.setAlimentacion(String.valueOf(alimentacion.getText()));

            remota.setDrogas(drogas.isChecked());
            remota.setAlcohol(alcohol.isChecked());
            remota.setTabaco(tabaco.isChecked());

            remota.setSendBd(false);

            mRealm.insertOrUpdate(remota);
            mRealm.commitTransaction();

        }

    }


}