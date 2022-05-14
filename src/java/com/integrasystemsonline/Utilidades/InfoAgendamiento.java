package com.integrasystemsonline.Utilidades;

public class InfoAgendamiento {

    private String fechaDesde;

    private String fechaHasta;

    private float Precio;

    private String medico;

    private String cedulaMedico;

    private String servicio;

    private String cliente;

    private String estado;

    public InfoAgendamiento(String fechaDesde, String fechaHasta, float Precio, String medico, String cedulaMedico, String servicio, String cliente, String estado) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.Precio = Precio;
        this.medico = medico;
        this.cedulaMedico = cedulaMedico;
        this.servicio = servicio;
        this.cliente = cliente;
        this.estado = estado;
    }

    public InfoAgendamiento() {
    }

    public String getCedulaMedico() {
        return this.cedulaMedico;
    }

    public void setCedulaMedico(String cedulaMedico) {
        this.cedulaMedico = cedulaMedico;
    }

    public String getFechaDesde() {
        return this.fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return this.fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public float getPrecio() {
        return this.Precio;
    }

    public void setPrecio(float Precio) {
        this.Precio = Precio;
    }

    public String getMedico() {
        return this.medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getServicio() {
        return this.servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCliente() {
        return this.cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
