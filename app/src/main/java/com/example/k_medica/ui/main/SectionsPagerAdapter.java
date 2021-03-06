package com.example.k_medica.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.k_medica.R;

import org.jetbrains.annotations.NotNull;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.fichaDatosUsuario, R.string.fichaProxima,R.string.fichaRemota,R.string.fichaDiagnostico,R.string.fichaTratamiento/*,R.string.fichaFisico*/};
    private final Context mContext;
    private int idFicha;

    public SectionsPagerAdapter(Context context, FragmentManager fm,int idFicha) {
        super(fm);
        System.out.println("SECTION PAGE ADAPTER");
        mContext = context;
        this.idFicha = idFicha;
    }
    @Override
    public Fragment getItem(int position) {

        return PlaceholderFragment.newInstance(position + 1,this.idFicha);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }



}