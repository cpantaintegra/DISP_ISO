package com.integrasystemsonline.Utilidades;

public class EstadoDia {

    private String hora;

    private String dia;

    private boolean disponibilildad;

    public EstadoDia(String hora, String dia, boolean disponibilildad) {
        this.hora = hora;
        this.dia = dia;
        this.disponibilildad = disponibilildad;
    }

    public EstadoDia() {
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDia() {
        return this.dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public boolean isDisponibilildad() {
        return this.disponibilildad;
    }

    public void setDisponibilildad(boolean disponibilildad) {
        this.disponibilildad = disponibilildad;
    }
}
