package com.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class HistorialPaciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @Column(name = "nombreMedicamento")
    private String nombreMedicamento;

    @Column(name = "descripcionMedicamento")
    private String descripcionMedicamento;

    @Column(name = "enfermedad")
    private String enfermedad;

    @Column(name = "presentacion")
    private String presentacion;

    @Column(name = "dosis")
    private String dosis;

    @Column(name = "duracion")
    private String cantidad;

    @Column(name = "tipo")
    private String tipo;
}
