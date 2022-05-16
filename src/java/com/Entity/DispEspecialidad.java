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
@Table(name = "disp_especialidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispEspecialidad.findAll", query = "SELECT d FROM DispEspecialidad d")
    , @NamedQuery(name = "DispEspecialidad.findByCodigoByIDs", query = "SELECT d FROM DispEspecialidad d WHERE d.codigo = :codigo AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispEspecialidad.findByNombreByIDs", query = "SELECT d FROM DispEspecialidad d WHERE UPPER(d.nombre) = :nombre AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispEspecialidad.findAllActivos", query = "SELECT d FROM DispEspecialidad d where d.estado = 'A' AND d.idEspecialidad != 1 AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispEspecialidad.findByIdEspecialidad", query = "SELECT d FROM DispEspecialidad d WHERE d.idEspecialidad = :idEspecialidad")
    , @NamedQuery(name = "DispEspecialidad.findByNombre", query = "SELECT d FROM DispEspecialidad d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispEspecialidad.findByCodigo", query = "SELECT d FROM DispEspecialidad d WHERE d.codigo = :codigo")
    , @NamedQuery(name = "DispEspecialidad.findByDescripcion", query = "SELECT d FROM DispEspecialidad d WHERE d.descripcion = :descripcion")
    , @NamedQuery(name = "DispEspecialidad.findByEstado", query = "SELECT d FROM DispEspecialidad d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispEspecialidad.findByUsuarioIngreso", query = "SELECT d FROM DispEspecialidad d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispEspecialidad.findByFechaIngreso", query = "SELECT d FROM DispEspecialidad d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispEspecialidad.findByUsuarioModificacion", query = "SELECT d FROM DispEspecialidad d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispEspecialidad.findByFechaModificacion", query = "SELECT d FROM DispEspecialidad d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispEspecialidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_especialidad")
    private Integer idEspecialidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;
    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispEspecialidad() {
    }

    public DispEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public DispEspecialidad(Integer idEspecialidad, String nombre, String codigo, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
        this.codigo = codigo;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idEspecialidad != null ? idEspecialidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispEspecialidad)) {
            return false;
        }
        DispEspecialidad other = (DispEspecialidad) object;
        if ((this.idEspecialidad == null && other.idEspecialidad != null) || (this.idEspecialidad != null && !this.idEspecialidad.equals(other.idEspecialidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispEspecialidad[ idEspecialidad=" + idEspecialidad + " ]";
    }
    
}
