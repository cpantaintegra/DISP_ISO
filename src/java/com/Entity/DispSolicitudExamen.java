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
@Table(name = "disp_solicitud_examen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispSolicitudExamen.findAll", query = "SELECT d FROM DispSolicitudExamen d")
    , @NamedQuery(name = "DispSolicitudExamen.findByIdAgendamiento", query = "SELECT d FROM DispSolicitudExamen d WHERE d.idAgendamiento.idAgendamiento = :idAgendamiento")
    , @NamedQuery(name = "DispSolicitudExamen.findByIdSolicitudExamen", query = "SELECT d FROM DispSolicitudExamen d WHERE d.idSolicitudExamen = :idSolicitudExamen")
    , @NamedQuery(name = "DispSolicitudExamen.findByFecha", query = "SELECT d FROM DispSolicitudExamen d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "DispSolicitudExamen.findByEstado", query = "SELECT d FROM DispSolicitudExamen d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispSolicitudExamen.findByUsuarioIngreso", query = "SELECT d FROM DispSolicitudExamen d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispSolicitudExamen.findByFechaIngreso", query = "SELECT d FROM DispSolicitudExamen d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispSolicitudExamen.findByUsuarioModificacion", query = "SELECT d FROM DispSolicitudExamen d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispSolicitudExamen.findByFechaModificacion", query = "SELECT d FROM DispSolicitudExamen d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispSolicitudExamen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitud_examen")
    private Integer idSolicitudExamen;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
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
    @JoinColumn(name = "id_agendamiento", referencedColumnName = "id_agendamiento")
    @ManyToOne(optional = false)
    private DispAgendamiento idAgendamiento;
    @JoinColumn(name = "id_examen", referencedColumnName = "id_examen")
    @ManyToOne(optional = false)
    private DispExamen idExamen;
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;
    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispSolicitudExamen() {
    }

    public DispSolicitudExamen(Integer idSolicitudExamen) {
        this.idSolicitudExamen = idSolicitudExamen;
    }

    public DispSolicitudExamen(Integer idSolicitudExamen, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idSolicitudExamen = idSolicitudExamen;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdSolicitudExamen() {
        return idSolicitudExamen;
    }

    public void setIdSolicitudExamen(Integer idSolicitudExamen) {
        this.idSolicitudExamen = idSolicitudExamen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public DispAgendamiento getIdAgendamiento() {
        return idAgendamiento;
    }

    public void setIdAgendamiento(DispAgendamiento idAgendamiento) {
        this.idAgendamiento = idAgendamiento;
    }

    public DispExamen getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(DispExamen idExamen) {
        this.idExamen = idExamen;
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
        hash += (idSolicitudExamen != null ? idSolicitudExamen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispSolicitudExamen)) {
            return false;
        }
        DispSolicitudExamen other = (DispSolicitudExamen) object;
        if ((this.idSolicitudExamen == null && other.idSolicitudExamen != null) || (this.idSolicitudExamen != null && !this.idSolicitudExamen.equals(other.idSolicitudExamen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispSolicitudExamen[ idSolicitudExamen=" + idSolicitudExamen + " ]";
    }
    
}
