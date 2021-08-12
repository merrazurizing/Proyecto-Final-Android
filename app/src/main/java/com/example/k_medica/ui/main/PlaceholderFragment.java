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
import com.example.k_medica.fragmentsFichaMedica.AnamnesisProxima;
import com.example.k_medica.fragmentsFichaMedica.AnamnesisRemota;
import com.example.k_medica.fragmentsFichaMedica.Diagnostico;
import com.example.k_medica.fragmentsFichaMedica.Tratamiento;
import com.example.k_medica.fragmentsFichaMedica.fichaDatosUsuario;
import com.example.k_medica.models.Ficha;
import com.example.k_medica.models.FichaAnamnesisRemota;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentFichaMedicaBinding binding;
    private Realm mRealm;


    public static Fragment newInstance(int index, int idFicha) {

        //lo que al final pasa acá es que se manda la id de la ficha y se rescata en el dato que sea


        System.out.println("FRAGMENT NEWINSTANCE");

        Fragment fragment  = null;

        switch (index){
            case 1:
                //fichaDatosUsuario
                System.out.println("FICHA OPCIÓN");
                fragment = new fichaDatosUsuario(String.valueOf(idFicha));
                break;

            case 2:
               fragment = new AnamnesisRemota(String.valueOf(idFicha));
                break;
            case 3:
                fragment = new AnamnesisProxima(String.valueOf(idFicha));
               break;
            case 4:
                fragment = new Diagnostico(String.valueOf(idFicha));

                break;
            case 5:
                fragment = new Tratamiento(String.valueOf(idFicha));
                break;
            case 6:

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