package com.example.k_medica.models;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FichaAnamnesisRemota extends RealmObject {

    @PrimaryKey
    private int id;
    private String antecedentes_morbidos ="";
    private String antecedentes_quirurgicos="";
    private String hospitalizaciones="";
    private String antecedentes_familiares="";
    private String alergias="";
    private String alimentacion="";
    private int fichamedica_id;
    private boolean alcohol=false;
    private boolean drogas=false;
    private boolean tabaco=false;
    private boolean sendBd=false;

    public FichaAnamnesisRemota(int id, String antecedentes_morbidos, String antecedentes_quirurgicos, String hospitalizaciones, String antecedentes_familiares, String alergias, String alimentacion, int fichamedica_id, boolean alcohol, boolean drogas, boolean tabaco, boolean sendBd) {
        this.id = id;
        this.antecedentes_morbidos = antecedentes_morbidos;
        this.antecedentes_quirurgicos = antecedentes_quirurgicos;
        this.hospitalizaciones = hospitalizaciones;
        this.antecedentes_familiares = antecedentes_familiares;
        this.alergias = alergias;
        this.alimentacion = alimentacion;
        this.fichamedica_id = fichamedica_id;
        this.alcohol = alcohol;
        this.drogas = drogas;
        this.tabaco = tabaco;
        this.sendBd = sendBd;
    }

    public FichaAnamnesisRemota(String antecedentes_morbidos, String antecedentes_quirurgicos, String hospitalizaciones, String antecedentes_familiares, String alergias, String alimentacion, int fichamedica_id, boolean alcohol, boolean drogas, boolean tabaco, boolean sendBd) {
        this.id = getNextKey();
        this.antecedentes_morbidos = antecedentes_morbidos;
        this.antecedentes_quirurgicos = antecedentes_quirurgicos;
        this.hospitalizaciones = hospitalizaciones;
        this.antecedentes_familiares = antecedentes_familiares;
        this.alergias = alergias;
        this.alimentacion = alimentacion;
        this.fichamedica_id = fichamedica_id;
        this.alcohol = alcohol;
        this.drogas = drogas;
        this.tabaco = tabaco;
        this.sendBd = sendBd;
    }




    public FichaAnamnesisRemota(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAntecedentes_morbidos() {
        return antecedentes_morbidos;
    }

    public void setAntecedentes_morbidos(String antecedentes_morbidos) {
        this.antecedentes_morbidos = antecedentes_morbidos;
    }

    public String getAntecedentes_quirurgicos() {
        return antecedentes_quirurgicos;
    }

    public void setAntecedentes_quirurgicos(String antecedentes_quirurgicos) {
        this.antecedentes_quirurgicos = antecedentes_quirurgicos;
    }

    public String getHospitalizaciones() {
        return hospitalizaciones;
    }

    public void setHospitalizaciones(String hospitalizaciones) {
        this.hospitalizaciones = hospitalizaciones;
    }

    public String getAntecedentes_familiares() {
        return antecedentes_familiares;
    }

    public void setAntecedentes_familiares(String antecedentes_familiares) {
        this.antecedentes_familiares = antecedentes_familiares;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }

    public int getFichamedica_id() {
        return fichamedica_id;
    }

    public void setFichamedica_id(int fichamedica_id) {
        this.fichamedica_id = fichamedica_id;
    }

    public boolean isAlcohol() {
        return alcohol;
    }

    public void setAlcohol(boolean alcohol) {
        this.alcohol = alcohol;
    }

    public boolean isDrogas() {
        return drogas;
    }

    public void setDrogas(boolean drogas) {
        this.drogas = drogas;
    }

    public boolean isTabaco() {
        return tabaco;
    }

    public void setTabaco(boolean tabaco) {
        this.tabaco = tabaco;
    }

    public boolean isSendBd() {
        return sendBd;
    }

    public void setSendBd(boolean sendBd) {
        this.sendBd = sendBd;
    }


    public int getNextKey() {
        try {
            Realm realm = Realm.getDefaultInstance();
            /* se consulta por el id max actual guardado*/
            Number number = realm.where(FichaAnamnesisRemota.class).max("id");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }



}
