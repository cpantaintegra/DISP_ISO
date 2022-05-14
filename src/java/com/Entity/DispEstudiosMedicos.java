/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "disp_estudios_medicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispEstudiosMedicos.findAll", query = "SELECT d FROM DispEstudiosMedicos d")
    , @NamedQuery(name = "DispEstudiosMedicos.findByIdEspecialidad", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.idEspecialidad.idEspecialidad = :idEspecialidad")
    , @NamedQuery(name = "DispEstudiosMedicos.findByNombreByIDs", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.nombre = :nombre AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispEstudiosMedicos.findByIdEstudiosMedicos", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.idEstudiosMedicos = :idEstudiosMedicos")
    , @NamedQuery(name = "DispEstudiosMedicos.findByNombre", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispEstudiosMedicos.findByDetalle", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.detalle = :detalle")
    , @NamedQuery(name = "DispEstudiosMedicos.findByEstado", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispEstudiosMedicos.findByUsuarioIngreso", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispEstudiosMedicos.findByFechaIngreso", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispEstudiosMedicos.findByUsuarioModificacion", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispEstudiosMedicos.findByFechaModificacion", query = "SELECT d FROM DispEstudiosMedicos d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispEstudiosMedicos implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstudiosMedicos")
    private Collection<DispExamen> dispExamenCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estudios_medicos")
    private Integer idEstudiosMedicos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 10000)
    @Column(name = "detalle")
    private String detalle;
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
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id_especialidad")
    @ManyToOne(optional = false)
    private DispEspecialidad idEspecialidad;
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;
    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispEstudiosMedicos() {
    }

    public DispEstudiosMedicos(Integer idEstudiosMedicos) {
        this.idEstudiosMedicos = idEstudiosMedicos;
    }

    public DispEstudiosMedicos(Integer idEstudiosMedicos, String nombre, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idEstudiosMedicos = idEstudiosMedicos;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdEstudiosMedicos() {
        return idEstudiosMedicos;
    }

    public void setIdEstudiosMedicos(Integer idEstudiosMedicos) {
        this.idEstudiosMedicos = idEstudiosMedicos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    public IsCiudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(IsCiudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public DispEspecialidad getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(DispEspecialidad idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
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
        hash += (idEstudiosMedicos != null ? idEstudiosMedicos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispEstudiosMedicos)) {
            return false;
        }
        DispEstudiosMedicos other = (DispEstudiosMedicos) object;
        if ((this.idEstudiosMedicos == null && other.idEstudiosMedicos != null) || (this.idEstudiosMedicos != null && !this.idEstudiosMedicos.equals(other.idEstudiosMedicos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispEstudiosMedicos[ idEstudiosMedicos=" + idEstudiosMedicos + " ]";
    }

    @XmlTransient
    public Collection<DispExamen> getDispExamenCollection() {
        return dispExamenCollection;
    }

    public void setDispExamenCollection(Collection<DispExamen> dispExamenCollection) {
        this.dispExamenCollection = dispExamenCollection;
    }
    
}
