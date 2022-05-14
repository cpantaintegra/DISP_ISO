package com.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AgendaDiagnostico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamiento")
    private Integer id_agendamiento;

    @Column(name = "nombPaciente")
    private String nombPaciente;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "fecha_llamada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlamada;

    @Column(name = "fecha_inicio_atencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioAtencion;

    @Column(name = "fecha_atendido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtendido;

    @Column(name = "fecha_llamar_medico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlamarMedico;

    @Column(name = "fecha_inicio_medico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioMedico;

    @Column(name = "fecha_atendido_medico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtendidoMedico;

    @Column(name = "estado")
    private String estado;

    public Integer getId_agendamiento() {
        return this.id_agendamiento;
    }

    public void setId_agendamiento(Integer id_agendamiento) {
        this.id_agendamiento = id_agendamiento;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getServicio() {
        return this.servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMotivoConsulta() {
        return this.motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getNombPaciente() {
        return this.nombPaciente;
    }

    public void setNombPaciente(String nombPaciente) {
        this.nombPaciente = nombPaciente;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaLlamada() {
        return this.fechaLlamada;
    }

    public void setFechaLlamada(Date fechaLlamada) {
        this.fechaLlamada = fechaLlamada;
    }

    public Date getFechaInicioAtencion() {
        return this.fechaInicioAtencion;
    }

    public void setFechaInicioAtencion(Date fechaInicioAtencion) {
        this.fechaInicioAtencion = fechaInicioAtencion;
    }

    public Date getFechaAtendido() {
        return this.fechaAtendido;
    }

    public void setFechaAtendido(Date fechaAtendido) {
        this.fechaAtendido = fechaAtendido;
    }

    public Date getFechaLlamarMedico() {
        return this.fechaLlamarMedico;
    }

    public void setFechaLlamarMedico(Date fechaLlamarMedico) {
        this.fechaLlamarMedico = fechaLlamarMedico;
    }

    public Date getFechaInicioMedico() {
        return this.fechaInicioMedico;
    }

    public void setFechaInicioMedico(Date fechaInicioMedico) {
        this.fechaInicioMedico = fechaInicioMedico;
    }

    public Date getFechaAtendidoMedico() {
        return this.fechaAtendidoMedico;
    }

    public void setFechaAtendidoMedico(Date fechaAtendidoMedico) {
        this.fechaAtendidoMedico = fechaAtendidoMedico;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
