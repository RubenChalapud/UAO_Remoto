package com.example.uaoremoto;

public class Asistencia {
    private String idestudiante;
    private String fechaclase;
    private String nombreestudiante;
    private String apellidoestudiante;
    private String programaestudiante;
    private String modoclase;
    private String asistenciaclase;

    public Asistencia(String idestudiante, String fechaclase, String nombreestudiante, String apellidoestudiante, String programaestudiante, String modoclase, String asistenciaclase) {
        this.idestudiante = idestudiante;
        this.fechaclase = fechaclase;
        this.nombreestudiante = nombreestudiante;
        this.apellidoestudiante = apellidoestudiante;
        this.programaestudiante = programaestudiante;
        this.modoclase = modoclase;
        this.asistenciaclase = asistenciaclase;
    }

    public Asistencia(){

    }

    public String getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(String idestudiante) {
        this.idestudiante = idestudiante;
    }

    public String getFechaclase() {
        return fechaclase;
    }

    public void setFechaclase(String fechaclase) {
        this.fechaclase = fechaclase;
    }

    public String getNombreestudiante() {
        return nombreestudiante;
    }

    public void setNombreestudiante(String nombreestudiante) {
        this.nombreestudiante = nombreestudiante;
    }

    public String getApellidoestudiante() {
        return apellidoestudiante;
    }

    public void setApellidoestudiante(String apellidoestudiante) {
        this.apellidoestudiante = apellidoestudiante;
    }

    public String getProgramaestudiante() {
        return programaestudiante;
    }

    public void setProgramaestudiante(String programaestudiante) {
        this.programaestudiante = programaestudiante;
    }

    public String getModoclase() {
        return modoclase;
    }

    public void setModoclase(String modoclase) {
        this.modoclase = modoclase;
    }

    public String getAsistenciaclase() {
        return asistenciaclase;
    }

    public void setAsistenciaclase(String asistenciaclase) {
        this.asistenciaclase = asistenciaclase;
    }
}
