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
@Table(name = "is_area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsArea.findAll", query = "SELECT i FROM IsArea i")
    , @NamedQuery(name = "IsArea.findAllActivos", query = "SELECT i FROM IsArea i where i.estado = 'A' ORDER BY i.nombre")
    , @NamedQuery(name = "IsArea.findByIdArea", query = "SELECT i FROM IsArea i WHERE i.idArea = :idArea")
    , @NamedQuery(name = "IsArea.findByNombre", query = "SELECT i FROM IsArea i WHERE i.nombre = :nombre")
    , @NamedQuery(name = "IsArea.findByDetalle", query = "SELECT i FROM IsArea i WHERE i.detalle = :detalle")
    , @NamedQuery(name = "IsArea.findByEstado", query = "SELECT i FROM IsArea i WHERE i.estado = :estado")
    , @NamedQuery(name = "IsArea.findByUsuarioIngreso", query = "SELECT i FROM IsArea i WHERE i.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "IsArea.findByFechaIngreso", query = "SELECT i FROM IsArea i WHERE i.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "IsArea.findByUsuarioModificacion", query = "SELECT i FROM IsArea i WHERE i.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "IsArea.findByFechaModificacion", query = "SELECT i FROM IsArea i WHERE i.fechaModificacion = :fechaModificacion")})
public class IsArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_area")
    private Integer idArea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 1000)
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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idArea")
    private Collection<IsUsuarios> isUsuariosCollection;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public IsArea() {
    }

    public IsArea(Integer idArea) {
        this.idArea = idArea;
    }

    public IsArea(Integer idArea, String nombre, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idArea = idArea;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdArea() {
        return this.idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    @XmlTransient
    public Collection<IsUsuarios> getIsUsuariosCollection() {
        return this.isUsuariosCollection;
    }

    public void setIsUsuariosCollection(Collection<IsUsuarios> isUsuariosCollection) {
        this.isUsuariosCollection = isUsuariosCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idArea != null) ? this.idArea.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsArea)) {
            return false;
        }
        IsArea other = (IsArea) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.IsArea[ idArea=" + this.idArea + " ]";
    }
}
