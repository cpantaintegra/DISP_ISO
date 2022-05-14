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
@Table(name = "disp_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispServicio.findAll", query = "SELECT d FROM DispServicio d")
    , @NamedQuery(name = "DispServicio.findByNombreByIDs", query = "SELECT d FROM DispServicio d WHERE d.nombre = :nombre AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector AND d.idEspecialidad.idEspecialidad = :especialidad")
    , @NamedQuery(name = "DispServicio.findAllActivosByIDs", query = "SELECT d FROM DispServicio d where d.estado = 'A' AND d.idServicio != 1 AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispServicio.findByIdEspecialidad", query = "SELECT d FROM DispServicio d WHERE d.idEspecialidad.idEspecialidad = :idEspecialidad")
    , @NamedQuery(name = "DispServicio.findAllActivos", query = "SELECT d FROM DispServicio d where d.estado = 'A' AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispServicio.findByIdServicio", query = "SELECT d FROM DispServicio d WHERE d.idServicio = :idServicio")
    , @NamedQuery(name = "DispServicio.findByNombre", query = "SELECT d FROM DispServicio d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispServicio.findByCondicion", query = "SELECT d FROM DispServicio d WHERE d.condicion = :condicion")
    , @NamedQuery(name = "DispServicio.findByEstado", query = "SELECT d FROM DispServicio d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispServicio.findByUsuarioIngreso", query = "SELECT d FROM DispServicio d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispServicio.findByFechaIngreso", query = "SELECT d FROM DispServicio d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispServicio.findByUsuarioModificacion", query = "SELECT d FROM DispServicio d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispServicio.findByFechaModificacion", query = "SELECT d FROM DispServicio d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispServicio implements Serializable {

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idServicio")
    private Collection<DispAgendamiento> dispAgendamientoCollection;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_servicio")
    private Integer idServicio;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "condicion")
    private Boolean condicion;

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

    @JoinColumn(name = "id_especialidad", referencedColumnName = "id_especialidad")
    @ManyToOne(optional = false)
    private DispEspecialidad idEspecialidad;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idServicio")
    private Collection<DispPrecio> dispPrecioCollection;

    public DispServicio() {
    }

    public DispServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public DispServicio(Integer idServicio, String nombre, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdServicio() {
        return this.idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getCondicion() {
        return this.condicion;
    }

    public void setCondicion(Boolean condicion) {
        this.condicion = condicion;
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

    public DispEspecialidad getIdEspecialidad() {
        return this.idEspecialidad;
    }

    public void setIdEspecialidad(DispEspecialidad idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
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
    public Collection<DispPrecio> getDispPrecioCollection() {
        return this.dispPrecioCollection;
    }

    public void setDispPrecioCollection(Collection<DispPrecio> dispPrecioCollection) {
        this.dispPrecioCollection = dispPrecioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idServicio != null) ? this.idServicio.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispServicio)) {
            return false;
        }
        DispServicio other = (DispServicio) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispServicio[ idServicio=" + this.idServicio + " ]";
    }

    @XmlTransient
    public Collection<DispAgendamiento> getDispAgendamientoCollection() {
        return this.dispAgendamientoCollection;
    }

    public void setDispAgendamientoCollection(Collection<DispAgendamiento> dispAgendamientoCollection) {
        this.dispAgendamientoCollection = dispAgendamientoCollection;
    }
}
