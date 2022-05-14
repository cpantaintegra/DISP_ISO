package com.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MedicamentoReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento_receta")
    private Integer idHistorial;

    @Column(name = "id_medicamento")
    private DispMedicamento idMedicamento;

    @Column(name = "medicamento")
    private String medicamento;

    @Column(name = "dosis")
    private String dosis;

    @Column(name = "duracion")
    private String duracion;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "presentacion")
    private String presentacion;

    public Integer getIdHistorial() {
        return this.idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getMedicamento() {
        return this.medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosis() {
        return this.dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDuracion() {
        return this.duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPresentacion() {
        return this.presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public DispMedicamento getIdMedicamento() {
        return this.idMedicamento;
    }

    public void setIdMedicamento(DispMedicamento idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
}
