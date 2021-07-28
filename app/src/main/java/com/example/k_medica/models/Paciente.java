package com.example.k_medica.models;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Paciente extends RealmObject {

    @PrimaryKey
    private String nombre;
    private String rut;
    private String fecha_nacimiento;
    private String motivo_consulta;
    private String prevision_salud;
    private String ocupacion;
    private String direccion;
    private int id;
    /*nuevo atributo para saber si es que fue enviado o no a la bd remota*/
    private boolean sendBd;

    public Paciente() {
    }

    public Paciente(String nombre, String rut, String fecha_nacimiento, String motivo_consulta, String prevision_salud, String ocupacion, String direccion, int id, boolean sendBd) {
        this.rut = rut;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.motivo_consulta = motivo_consulta;
        this.prevision_salud = prevision_salud;
        this.ocupacion = ocupacion;
        this.direccion = direccion;
        this.id = id;
        this.sendBd = sendBd;
    }

    public Paciente(String nombre, String rut, String fecha_nacimiento, String motivo_consulta, String prevision_salud, String ocupacion, String direccion, int id) {
        this.rut = rut;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.motivo_consulta = motivo_consulta;
        this.prevision_salud = prevision_salud;
        this.ocupacion = ocupacion;
        this.direccion = direccion;
        this.id = id;
    }

    public Paciente(String nombre, String rut, String fecha_nacimiento, String motivo_consulta, String prevision_salud, String ocupacion, String direccion, boolean sendBd) {
        this.rut = rut;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.motivo_consulta = motivo_consulta;
        this.prevision_salud = prevision_salud;
        this.ocupacion = ocupacion;
        this.direccion = direccion;
        this.sendBd = sendBd;
        this.id=getNextKey();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getMotivo_consulta() {
        return motivo_consulta;
    }

    public void setMotivo_consulta(String motivo_consulta) {
        this.motivo_consulta = motivo_consulta;
    }

    public String getPrevision_salud() {
        return prevision_salud;
    }

    public void setPrevision_salud(String prevision_salud) {
        this.prevision_salud = prevision_salud;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isSendBd() {
        return sendBd;
    }

    public void setSendBd(boolean sendBd) {
        this.sendBd = sendBd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /*metodo para generar el id correlativo de forma automática*/
    public int getNextKey() {
        try {
            Realm realm = Realm.getDefaultInstance();
            /* se consulta por el id max actual guardado*/
            Number number = realm.where(Paciente.class).max("id");
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