package com.integrasystemsonline.Utilidades;

import java.util.Date;
import java.util.Objects;

public class Evento {

    private Long id;

    private String titulo;

    private Date dateInicio;

    private Date dateFin;

    private boolean diaEntero;

    public Evento() {
        this.titulo = "";
        this.diaEntero = false;
    }

    public Evento(Long id, String titulo, Date dateInicio, Date dateFin, boolean diaEntero) {
        this.id = id;
        this.titulo = titulo;
        this.dateInicio = dateInicio;
        this.dateFin = dateFin;
        this.diaEntero = diaEntero;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDateInicio() {
        return this.dateInicio;
    }

    public void setDateInicio(Date dateInicio) {
        this.dateInicio = dateInicio;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isDiaEntero() {
        return this.diaEntero;
    }

    public void setDiaEntero(boolean diaEntero) {
        this.diaEntero = diaEntero;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Evento other = (Evento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
