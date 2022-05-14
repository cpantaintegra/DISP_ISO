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
@Table(name = "is_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsMenu.findAll", query = "SELECT i FROM IsMenu i")
    , @NamedQuery(name = "IsMenu.findByIdMenu", query = "SELECT i FROM IsMenu i WHERE i.idMenu = :idMenu")
    , @NamedQuery(name = "IsMenu.findByIdMenuPadre", query = "SELECT i FROM IsMenu i WHERE i.idMenuPadre = :idMenuPadre")
    , @NamedQuery(name = "IsMenu.findByNombre", query = "SELECT i FROM IsMenu i WHERE i.nombre = :nombre")
    , @NamedQuery(name = "IsMenu.findByDetalle", query = "SELECT i FROM IsMenu i WHERE i.detalle = :detalle")
    , @NamedQuery(name = "IsMenu.findByIcono", query = "SELECT i FROM IsMenu i WHERE i.icono = :icono")
    , @NamedQuery(name = "IsMenu.findByUrl", query = "SELECT i FROM IsMenu i WHERE i.url = :url")
    , @NamedQuery(name = "IsMenu.findByOrden", query = "SELECT i FROM IsMenu i WHERE i.orden = :orden")
    , @NamedQuery(name = "IsMenu.findByEstado", query = "SELECT i FROM IsMenu i WHERE i.estado = :estado")
    , @NamedQuery(name = "IsMenu.findByUsuarioIngreso", query = "SELECT i FROM IsMenu i WHERE i.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "IsMenu.findByFechaIngreso", query = "SELECT i FROM IsMenu i WHERE i.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "IsMenu.findByUsuarioModificacion", query = "SELECT i FROM IsMenu i WHERE i.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "IsMenu.findByFechaModificacion", query = "SELECT i FROM IsMenu i WHERE i.fechaModificacion = :fechaModificacion")})
public class IsMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_menu")
    private Integer idMenu;

    @Column(name = "id_menu_padre")
    private Integer idMenuPadre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 1000)
    @Column(name = "detalle")
    private String detalle;

    @Size(max = 200)
    @Column(name = "icono")
    private String icono;

    @Size(max = 2000)
    @Column(name = "url")
    private String url;

    @Column(name = "orden")
    private Integer orden;

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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idMenu")
    private Collection<IsRolesMenu> isRolesMenuCollection;

    public IsMenu() {
    }

    public IsMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public IsMenu(Integer idMenu, String nombre, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idMenu = idMenu;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdMenu() {
        return this.idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Integer getIdMenuPadre() {
        return this.idMenuPadre;
    }

    public void setIdMenuPadre(Integer idMenuPadre) {
        this.idMenuPadre = idMenuPadre;
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

    public String getIcono() {
        return this.icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrden() {
        return this.orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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
    public Collection<IsRolesMenu> getIsRolesMenuCollection() {
        return this.isRolesMenuCollection;
    }

    public void setIsRolesMenuCollection(Collection<IsRolesMenu> isRolesMenuCollection) {
        this.isRolesMenuCollection = isRolesMenuCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idMenu != null) ? this.idMenu.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsMenu)) {
            return false;
        }
        IsMenu other = (IsMenu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.IsMenu[ idMenu=" + this.idMenu + " ]";
    }
}
