package com.example.k_medica.fragmentsFichaMedica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.k_medica.R;
import com.example.k_medica.models.Ficha;
import com.example.k_medica.models.Paciente;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class fichaDatosUsuario extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Ficha fichaPaciente;
    private static String idFicha;
    private static Paciente datosPaciente;
    private TextView nombrePaciente, rutPaciente, fechaNacimiento, motivoConsulta, plan,lugar,fechaConsulta;
    private Realm mRealm;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fichaDatosUsuario(String idFicha) {
        System.out.println("Ficha USUARIO");
        this.idFicha = idFicha;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        nombrePaciente = v.findViewById(R.id.fichaNombreUsuario);
        rutPaciente = v.findViewById(R.id.fichaRut);
        fechaNacimiento = v.findViewById(R.id.fichaFechaNacimiento);
        motivoConsulta = v.findViewById(R.id.fichaMotivoContulta);
        plan=v.findViewById(R.id.fichaPlan);
        lugar = v.findViewById(R.id.fichaLugar);
        fechaConsulta = v.findViewById(R.id.fichaFechaConsulta);


        if(fichaPaciente!=null && datosPaciente!=null){
            nombrePaciente.setText(datosPaciente.getNombre());
            rutPaciente.setText(datosPaciente.getRut());
            fechaNacimiento.setText(datosPaciente.getFecha_nacimiento());
            motivoConsulta.setText(fichaPaciente.getMotivo());
            plan.setText(fichaPaciente.getPlan());
            lugar.setText(fichaPaciente.getLuagar());
            fechaConsulta.setText((CharSequence) fichaPaciente.getFecha_consulta());
        }



        return inflater.inflate(R.layout.fragment_ficha_datos_usuario, container, false);

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