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
@Table(name = "disp_origen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispOrigen.findAll", query = "SELECT d FROM DispOrigen d")
    , @NamedQuery(name = "DispOrigen.findAllActivos", query = "SELECT d FROM DispOrigen d where d.estado = 'A' AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispOrigen.findByIdOrigen", query = "SELECT d FROM DispOrigen d WHERE d.idOrigen = :idOrigen")
    , @NamedQuery(name = "DispOrigen.findByNombre", query = "SELECT d FROM DispOrigen d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispOrigen.findByDetalle", query = "SELECT d FROM DispOrigen d WHERE d.detalle = :detalle")
    , @NamedQuery(name = "DispOrigen.findByEstado", query = "SELECT d FROM DispOrigen d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispOrigen.findByUsuarioIngreso", query = "SELECT d FROM DispOrigen d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispOrigen.findByFechaIngreso", query = "SELECT d FROM DispOrigen d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispOrigen.findByUsuarioModificacion", query = "SELECT d FROM DispOrigen d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispOrigen.findByFechaModificacion", query = "SELECT d FROM DispOrigen d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispOrigen implements Serializable {

    @Size(max = 1000)
    @Column(name = "nombreJuridico")
    private String nombreJuridico;

    @Size(max = 13)
    @Column(name = "ruc")
    private String ruc;

    @Size(max = 1000)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 20)
    @Column(name = "telefono1")
    private String telefono1;

    @Size(max = 20)
    @Column(name = "telefono2")
    private String telefono2;

    @Size(max = 20)
    @Column(name = "telefono3")
    private String telefono3;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idOrigen")
    private Collection<DispCliente> dispClienteCollection;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_origen")
    private Integer idOrigen;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idOrigen")
    private Collection<DispPrecio> dispPrecioCollection;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispOrigen() {
    }

    public DispOrigen(Integer idOrigen) {
        this.idOrigen = idOrigen;
    }

    public DispOrigen(Integer idOrigen, String nombre, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idOrigen = idOrigen;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdOrigen() {
        return this.idOrigen;
    }

    public void setIdOrigen(Integer idOrigen) {
        this.idOrigen = idOrigen;
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
    public Collection<DispPrecio> getDispPrecioCollection() {
        return this.dispPrecioCollection;
    }

    public void setDispPrecioCollection(Collection<DispPrecio> dispPrecioCollection) {
        this.dispPrecioCollection = dispPrecioCollection;
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
        hash += (this.idOrigen != null) ? this.idOrigen.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispOrigen)) {
            return false;
        }
        DispOrigen other = (DispOrigen) object;
        if ((this.idOrigen == null && other.idOrigen != null) || (this.idOrigen != null && !this.idOrigen.equals(other.idOrigen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispOrigen[ idOrigen=" + this.idOrigen + " ]";
    }

    @XmlTransient
    public Collection<DispCliente> getDispClienteCollection() {
        return this.dispClienteCollection;
    }

    public void setDispClienteCollection(Collection<DispCliente> dispClienteCollection) {
        this.dispClienteCollection = dispClienteCollection;
    }

    public String getNombreJuridico() {
        return this.nombreJuridico;
    }

    public void setNombreJuridico(String nombreJuridico) {
        this.nombreJuridico = nombreJuridico;
    }

    public String getRuc() {
        return this.ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return this.telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return this.telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTelefono3() {
        return this.telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }
}
