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


public class fichaDatosUsuario extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Ficha fichaPaciente;
    private static Paciente datosPaciente;
    private TextView nombrePaciente, rutPaciente, fechaNacimiento, motivoConsulta, plan,lugar,fechaConsulta;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fichaDatosUsuario(Ficha fichaPaciente) {
        // Required empty public constructor
        this.fichaPaciente = fichaPaciente;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_ficha_datos_usuario, container, false);

        nombrePaciente = v.findViewById(R.id.fichaNombreUsuario);
        rutPaciente = v.findViewById(R.id.fichaRut);
        fechaNacimiento = v.findViewById(R.id.fichaFechaNacimiento);
        motivoConsulta = v.findViewById(R.id.fichaMotivoContulta);
        plan=v.findViewById(R.id.fichaPlan);
        lugar = v.findViewById(R.id.fichaLugar);
        fechaConsulta = v.findViewById(R.id.fichaFechaConsulta);

        nombrePaciente.setText(datosPaciente.getNombre());
        rutPaciente.setText(datosPaciente.getRut());
        fechaNacimiento.setText(datosPaciente.getFecha_nacimiento());
        motivoConsulta.setText(fichaPaciente.getMotivo());
        plan.setText(fichaPaciente.getPlan());
        lugar.setText(fichaPaciente.getLuagar());
        fechaConsulta.setText((CharSequence) fichaPaciente.getFecha_consulta());

        return inflater.inflate(R.layout.fragment_ficha_datos_usuario, container, false);



    }
}