package com.example.k_medica.models;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ficha extends RealmObject {


    @PrimaryKey
    private int id;
    private String fecha_consulta;
    private String motivo="";
    private String plan="";
    private String tratamiento="";
    private String diagnostico="";
    private String luagar="";
    private String medico_run="";
    private String usuario_run="";
    private String proxima="";
    private boolean sendBd=false;

    public Ficha(){

    }

    public Ficha(int id, String fecha_consulta, String motivo, String plan, String tratamiento, String diagnostico, String luagar, String medico_run, String usuario_run, boolean sendBd) {
        this.id = id;
        this.fecha_consulta = fecha_consulta;
        this.motivo = motivo;
        this.plan = plan;
        this.tratamiento = tratamiento;
        this.diagnostico = diagnostico;
        this.luagar = luagar;
        this.medico_run = medico_run;
        this.usuario_run = usuario_run;
        this.sendBd = sendBd;
    }
    public Ficha(String fecha_consulta, String motivo, String plan, String tratamiento, String diagnostico, String luagar, String medico_run, String usuario_run, boolean sendBd) {
        this.id = getNextKey();
        this.fecha_consulta = fecha_consulta;
        this.motivo = motivo;
        this.plan = plan;
        this.tratamiento = tratamiento;
        this.diagnostico = diagnostico;
        this.luagar = luagar;
        this.medico_run = medico_run;
        this.usuario_run = usuario_run;
        this.sendBd = sendBd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_consulta() {
        return fecha_consulta;
    }

    public void setFecha_consulta(String fecha_consulta) {
        this.fecha_consulta = fecha_consulta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getLuagar() {
        return luagar;
    }

    public void setLuagar(String luagar) {
        this.luagar = luagar;
    }

    public String getMedico_run() {
        return medico_run;
    }

    public void setMedico_run(String medico_run) {
        this.medico_run = medico_run;
    }

    public String getUsuario_run() {
        return usuario_run;
    }

    public void setUsuario_run(String usuario_run) {
        this.usuario_run = usuario_run;
    }

    public boolean isSendBd() {
        return sendBd;
    }

    public String getProxima() {
        return proxima;
    }

    public void setProxima(String proxima) {
        this.proxima = proxima;
    }

    public void setSendBd(boolean sendBd) {
        this.sendBd = sendBd;
    }

    public int getNextKey() {
        try {
            Realm realm = Realm.getDefaultInstance();
            /* se consulta por el id max actual guardado*/
            Number number = realm.where(Ficha.class).max("id");
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
