package com.example.k_medica.models;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Medico extends RealmObject {

    @PrimaryKey
    private String rut;
    private String nombre;
    private String contraseña;
    private String email;
    private String especialidad;
    private String ubicacion;

    private int id;
    /*nuevo atributo para saber si es que fue enviado o no a la bd remota*/
    private boolean sendBd;

    public Medico() {
    }

    public Medico(String rut, String nombre, String contraseña, String email, String especialidad, String ubicacion, int id, boolean sendBd) {
        this.rut = rut;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.especialidad = especialidad;
        this.ubicacion = ubicacion;
        this.id = id;
        this.sendBd = sendBd;
    }

    public Medico(String rut, String nombre, String contraseña, String email, String especialidad, String ubicacion, int id) {
        this.rut = rut;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.especialidad = especialidad;
        this.ubicacion = ubicacion;
        this.id = id;
    }

    public Medico(String rut, String nombre, String contraseña, String email, String especialidad, String ubicacion, boolean sendBd) {
        this.rut = rut;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.especialidad = especialidad;
        this.ubicacion = ubicacion;
        this.sendBd=sendBd;
        /*se agrega el id de forma automática y no se pide como parametro del constructor*/
        this.id=getNextKey();
    }



    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
            Number number = realm.where(Medico.class).max("id");
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
