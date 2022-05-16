/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "disp_examen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispExamen.findAll", query = "SELECT d FROM DispExamen d")
    , @NamedQuery(name = "DispExamen.findByNombreByIDs", query = "SELECT d FROM DispExamen d WHERE upper(d.nombre) = :nombre AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector AND d.idEstudiosMedicos.idEstudiosMedicos = :idEstudiosMedicos")
    , @NamedQuery(name = "DispExamen.findByIdEstudiosMedicos", query = "SELECT d FROM DispExamen d WHERE d.idEstudiosMedicos.idEstudiosMedicos = :idEstudiosMedicos")
    , @NamedQuery(name = "DispExamen.findByIdExamen", query = "SELECT d FROM DispExamen d WHERE d.idExamen = :idExamen")
    , @NamedQuery(name = "DispExamen.findByNombre", query = "SELECT d FROM DispExamen d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispExamen.findByDescripcion", query = "SELECT d FROM DispExamen d WHERE d.descripcion = :descripcion")
    , @NamedQuery(name = "DispExamen.findByTiempoHora", query = "SELECT d FROM DispExamen d WHERE d.tiempoHora = :tiempoHora")
    , @NamedQuery(name = "DispExamen.findByAyuno", query = "SELECT d FROM DispExamen d WHERE d.ayuno = :ayuno")
    , @NamedQuery(name = "DispExamen.findByVejigaLlena", query = "SELECT d FROM DispExamen d WHERE d.vejigaLlena = :vejigaLlena")
    , @NamedQuery(name = "DispExamen.findByPrecio", query = "SELECT d FROM DispExamen d WHERE d.precio = :precio")
    , @NamedQuery(name = "DispExamen.findByEstado", query = "SELECT d FROM DispExamen d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispExamen.findByUsuarioIngreso", query = "SELECT d FROM DispExamen d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispExamen.findByFechaIngreso", query = "SELECT d FROM DispExamen d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispExamen.findByUsuarioModificacion", query = "SELECT d FROM DispExamen d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispExamen.findByFechaModificacion", query = "SELECT d FROM DispExamen d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispExamen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_examen")
    private Integer idExamen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 30)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tiempo_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiempoHora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ayuno")
    private boolean ayuno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vejiga_llena")
    private boolean vejigaLlena;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Float precio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "usuario_ingreso")
    private String usuarioIngreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Size(max = 100)
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @JoinColumn(name = "id_estudios_medicos", referencedColumnName = "id_estudios_medicos")
    @ManyToOne(optional = false)
    private DispEstudiosMedicos idEstudiosMedicos;
    @JoinColumn(name = "id_unidad_medica", referencedColumnName = "id_unidad_medica")
    @ManyToOne(optional = false)
    private DispUnidadMedica idUnidadMedica;
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;
    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispExamen() {
    }

    public DispExamen(Integer idExamen) {
        this.idExamen = idExamen;
    }

    public DispExamen(Integer idExamen, String nombre, boolean ayuno, boolean vejigaLlena, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idExamen = idExamen;
        this.nombre = nombre;
        this.ayuno = ayuno;
        this.vejigaLlena = vejigaLlena;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(Integer idExamen) {
        this.idExamen = idExamen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getTiempoHora() {
        return tiempoHora;
    }

    public void setTiempoHora(Date tiempoHora) {
        this.tiempoHora = tiempoHora;
    }

    public boolean getAyuno() {
        return ayuno;
    }

    public void setAyuno(boolean ayuno) {
        this.ayuno = ayuno;
    }

    public boolean getVejigaLlena() {
        return vejigaLlena;
    }

    public void setVejigaLlena(boolean vejigaLlena) {
        this.vejigaLlena = vejigaLlena;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public DispEstudiosMedicos getIdEstudiosMedicos() {
        return idEstudiosMedicos;
    }

    public void setIdEstudiosMedicos(DispEstudiosMedicos idEstudiosMedicos) {
        this.idEstudiosMedicos = idEstudiosMedicos;
    }

    public DispUnidadMedica getIdUnidadMedica() {
        return idUnidadMedica;
    }

    public void setIdUnidadMedica(DispUnidadMedica idUnidadMedica) {
        this.idUnidadMedica = idUnidadMedica;
    }

    public IsCiudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(IsCiudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public IsEmpresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(IsEmpresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public IsSector getIdSector() {
        return idSector;
    }

    public void setIdSector(IsSector idSector) {
        this.idSector = idSector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExamen != null ? idExamen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispExamen)) {
            return false;
        }
        DispExamen other = (DispExamen) object;
        if ((this.idExamen == null && other.idExamen != null) || (this.idExamen != null && !this.idExamen.equals(other.idExamen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispExamen[ idExamen=" + idExamen + " ]";
    }
    
}
