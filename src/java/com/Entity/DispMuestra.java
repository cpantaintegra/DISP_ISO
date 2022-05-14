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

@Entity
@Table(name = "disp_muestra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispMuestra.findAll", query = "SELECT d FROM DispMuestra d")
    , @NamedQuery(name = "DispMuestra.findByIdMuestra", query = "SELECT d FROM DispMuestra d WHERE d.idMuestra = :idMuestra")
    , @NamedQuery(name = "DispMuestra.findByNombre", query = "SELECT d FROM DispMuestra d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispMuestra.findByDescripcion", query = "SELECT d FROM DispMuestra d WHERE d.descripcion = :descripcion")
    , @NamedQuery(name = "DispMuestra.findByEstado", query = "SELECT d FROM DispMuestra d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispMuestra.findByUsuarioIngreso", query = "SELECT d FROM DispMuestra d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispMuestra.findByFechaIngreso", query = "SELECT d FROM DispMuestra d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispMuestra.findByUsuarioModificacion", query = "SELECT d FROM DispMuestra d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispMuestra.findByFechaModificacion", query = "SELECT d FROM DispMuestra d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispMuestra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_muestra")
    private Integer idMuestra;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 20)
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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idMuestra")
    private Collection<DispTomaMuestra> dispTomaMuestraCollection;

    public DispMuestra() {
    }

    public DispMuestra(Integer idMuestra) {
        this.idMuestra = idMuestra;
    }

    public DispMuestra(Integer idMuestra, String nombre, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idMuestra = idMuestra;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdMuestra() {
        return this.idMuestra;
    }

    public void setIdMuestra(Integer idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioIngreso() {
        return this.usuarioIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public IsCiudad getIdCiudad() {
        return this.idCiudad;
    }

    public void setIdCiudad(IsCiudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public IsEmpresa getIdEmpresa() {
        return this.idEmpresa;
    }

    public void setIdEmpresa(IsEmpresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public IsSector getIdSector() {
        return this.idSector;
    }

    public void setIdSector(IsSector idSector) {
        this.idSector = idSector;
    }

    @XmlTransient
    public Collection<DispTomaMuestra> getDispTomaMuestraCollection() {
        return this.dispTomaMuestraCollection;
    }

    public void setDispTomaMuestraCollection(Collection<DispTomaMuestra> dispTomaMuestraCollection) {
        this.dispTomaMuestraCollection = dispTomaMuestraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idMuestra != null) ? this.idMuestra.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispMuestra)) {
            return false;
        }
        DispMuestra other = (DispMuestra) object;
        if ((this.idMuestra == null && other.idMuestra != null) || (this.idMuestra != null && !this.idMuestra.equals(other.idMuestra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispMuestra[ idMuestra=" + this.idMuestra + " ]";
    }
}
