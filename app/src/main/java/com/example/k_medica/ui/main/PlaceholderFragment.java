package com.example.k_medica.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.k_medica.R;
import com.example.k_medica.databinding.FragmentFichaMedicaBinding;
import com.example.k_medica.fragmentsFichaMedica.AnamnesisRemota;
import com.example.k_medica.fragmentsFichaMedica.fichaDatosUsuario;
import com.example.k_medica.models.Ficha;
import com.example.k_medica.models.FichaAnamnesisRemota;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentFichaMedicaBinding binding;

    public static Fragment newInstance(int index, int idFicha) {

        //en esta parte se debería mandar una ficah en esepcífico de la cual recuperamos lo que sea necesario
        //de lo que sea necesario es lo que se mandaría a cada fragment, enviar el objeto completo igual es unaopacion

        //estas son objetos que representan lo que debería ser rescatado
        Ficha fichaUsuario = new Ficha();
        FichaAnamnesisRemota remota = new FichaAnamnesisRemota();

        Fragment fragment    = null;
        switch (index){
            case 1:
                //fichaDatosUsuario
                fragment = new fichaDatosUsuario(fichaUsuario);
                break;
            case 2:
               fragment = new AnamnesisRemota(remota);
                break;
            case 3:
                //fichaRemota
                break;
            case 4:
                //fichaFisico
                break;
            case 5:
                //fichaDiagnostico
                break;
            case 6:
                //fichaTratamiento
                break;
            default:
                return null;
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentFichaMedicaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}