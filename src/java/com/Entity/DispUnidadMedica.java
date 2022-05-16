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
@Table(name = "disp_unidad_medica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispUnidadMedica.findAll", query = "SELECT d FROM DispUnidadMedica d")
    , @NamedQuery(name = "DispUnidadMedica.findByNombreByIDs", query = "SELECT d FROM DispUnidadMedica d WHERE UPPER(d.nombre) = :nombre AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispUnidadMedica.findAllActivos", query = "SELECT d FROM DispUnidadMedica d where d.estado = 'A' AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispUnidadMedica.findByIdUnidadMedica", query = "SELECT d FROM DispUnidadMedica d WHERE d.idUnidadMedica = :idUnidadMedica")
    , @NamedQuery(name = "DispUnidadMedica.findByNombre", query = "SELECT d FROM DispUnidadMedica d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispUnidadMedica.findByDescripcion", query = "SELECT d FROM DispUnidadMedica d WHERE d.descripcion = :descripcion")
    , @NamedQuery(name = "DispUnidadMedica.findByEstado", query = "SELECT d FROM DispUnidadMedica d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispUnidadMedica.findByUsuarioIngreso", query = "SELECT d FROM DispUnidadMedica d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispUnidadMedica.findByFechaIngreso", query = "SELECT d FROM DispUnidadMedica d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispUnidadMedica.findByUsuarioModificacion", query = "SELECT d FROM DispUnidadMedica d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispUnidadMedica.findByFechaModificacion", query = "SELECT d FROM DispUnidadMedica d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispUnidadMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_unidad_medica")
    private Integer idUnidadMedica;

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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idUnidadMedica")
    private Collection<DispExamen> dispExamenCollection;

    public DispUnidadMedica() {
    }

    public DispUnidadMedica(Integer idUnidadMedica) {
        this.idUnidadMedica = idUnidadMedica;
    }

    public DispUnidadMedica(Integer idUnidadMedica, String nombre, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idUnidadMedica = idUnidadMedica;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdUnidadMedica() {
        return this.idUnidadMedica;
    }

    public void setIdUnidadMedica(Integer idUnidadMedica) {
        this.idUnidadMedica = idUnidadMedica;
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
    public Collection<DispExamen> getDispExamenCollection() {
        return this.dispExamenCollection;
    }

    public void setDispExamenCollection(Collection<DispExamen> dispExamenCollection) {
        this.dispExamenCollection = dispExamenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idUnidadMedica != null) ? this.idUnidadMedica.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispUnidadMedica)) {
            return false;
        }
        DispUnidadMedica other = (DispUnidadMedica) object;
        if ((this.idUnidadMedica == null && other.idUnidadMedica != null) || (this.idUnidadMedica != null && !this.idUnidadMedica.equals(other.idUnidadMedica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispUnidadMedica[ idUnidadMedica=" + this.idUnidadMedica + " ]";
    }
}
