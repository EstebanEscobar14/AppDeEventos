package com.esteban.eventosapp.InicioApp;

public class MainModel {

    String estadio, fecha, partido, url;

    MainModel(){

    }
    public MainModel(String estadio, String fecha, String partido, String url) {
        this.estadio = estadio;
        this.fecha = fecha;
        this.partido = partido;
        this.url = url;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

