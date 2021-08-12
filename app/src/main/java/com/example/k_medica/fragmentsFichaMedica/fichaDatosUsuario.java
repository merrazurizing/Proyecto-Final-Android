package com.example.k_medica.fragmentsFichaMedica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.k_medica.R;
import com.example.k_medica.models.Ficha;
import com.example.k_medica.models.Paciente;

import org.jetbrains.annotations.NotNull;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class fichaDatosUsuario extends Fragment {


    private Ficha fichaPaciente;
    private String idFicha;
    private Paciente datosPaciente;
    private TextView nombrePaciente, rutPaciente, fechaNacimiento, motivoConsulta, plan,lugar,fechaConsulta;
    private Realm mRealm;
    private Button btn;

    public fichaDatosUsuario(String idFicha) {
        System.out.println("Ficha USUARIO");
        this.idFicha = idFicha;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpRealmConfig();
        mRealm = Realm.getDefaultInstance();

        fichaPaciente = mRealm.where(Ficha.class).equalTo("id",Integer.valueOf(idFicha)).findFirst();
        datosPaciente = mRealm.where(Paciente.class).equalTo("rut",fichaPaciente.getUsuario_run()).findFirst();

        if(savedInstanceState!=null){
            nombrePaciente.setText(datosPaciente.getNombre());
            System.out.println("SAVE INSTANCE STATE");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("FICHA USUARIO ON CREATE");
        View v=inflater.inflate(R.layout.fragment_ficha_datos_usuario, container, false);

        setUpRealmConfig();
        mRealm = Realm.getDefaultInstance();

        fichaPaciente = mRealm.where(Ficha.class).equalTo("id",Integer.valueOf(idFicha)).findFirst();
        datosPaciente = mRealm.where(Paciente.class).equalTo("rut",fichaPaciente.getUsuario_run()).findFirst();

        System.out.println(fichaPaciente);
        System.out.println(datosPaciente);

        nombrePaciente = v.findViewById(R.id.fichaNombreUsuario);
        rutPaciente = v.findViewById(R.id.fichaRut);
        fechaNacimiento = v.findViewById(R.id.fichaFechaNacimiento);
        motivoConsulta = v.findViewById(R.id.fichaMotivoContulta);
        plan=v.findViewById(R.id.fichaPlan);
        lugar = v.findViewById(R.id.fichaLugar);
        fechaConsulta = v.findViewById(R.id.fichaFechaConsulta);

        btn = v.findViewById(R.id.ficha_editarDatosUsuario);

        nombrePaciente.setText(datosPaciente.getNombre());
        rutPaciente.setText(datosPaciente.getRut());
        fechaNacimiento.setText(datosPaciente.getFecha_nacimiento());
        motivoConsulta.setText(fichaPaciente.getMotivo());
        plan.setText(fichaPaciente.getPlan());
        lugar.setText(fichaPaciente.getLuagar());
        fechaConsulta.setText((CharSequence) fichaPaciente.getFecha_consulta());




        return v;
        //return inflater.inflate(R.layout.fragment_ficha_datos_usuario, container, false);

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

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("existe",true);
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("on resume");
    }
}