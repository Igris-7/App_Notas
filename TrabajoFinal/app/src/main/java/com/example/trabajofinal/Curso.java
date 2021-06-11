package com.example.trabajofinal;

public class Curso {

private int curso_id;
private String nombre;
private String docente;
private String ciclo;
private String creditos;
private String dni_est;

    public Curso() {
    }

    public Curso(int curso_id, String nombre, String docente, String ciclo, String creditos, String dni_est) {
        this.curso_id = curso_id;
        this.nombre = nombre;
        this.docente = docente;
        this.ciclo = ciclo;
        this.creditos = creditos;
        this.dni_est= dni_est;
    }

    public String getDni_est() {
        return dni_est;
    }

    public void setDni_est(String dni_est) {
        this.dni_est = dni_est;
    }

    public int getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(int curso_id) {
        this.curso_id = curso_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }
}
