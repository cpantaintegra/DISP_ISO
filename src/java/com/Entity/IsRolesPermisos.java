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

@Entity
@Table(name = "is_roles_permisos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsRolesPermisos.findAll", query = "SELECT i FROM IsRolesPermisos i")
    , @NamedQuery(name = "IsRolesPermisos.findAllByRol", query = "SELECT i FROM IsRolesPermisos i WHERE i.idRoles = :objRoles order by i.idPermisos.nombre")
    , @NamedQuery(name = "IsRolesPermisos.findByRol", query = "SELECT i FROM IsRolesPermisos i WHERE i.estado = 'A' and i.idRoles.estado='A' and i.idPermisos.estado='A' and i.idRoles = :objRol")
    , @NamedQuery(name = "IsRolesPermisos.findByIdRolesPermisos", query = "SELECT i FROM IsRolesPermisos i WHERE i.idRolesPermisos = :idRolesPermisos")
    , @NamedQuery(name = "IsRolesPermisos.findByEstado", query = "SELECT i FROM IsRolesPermisos i WHERE i.estado = :estado")
    , @NamedQuery(name = "IsRolesPermisos.findByUsuarioIngreso", query = "SELECT i FROM IsRolesPermisos i WHERE i.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "IsRolesPermisos.findByFechaIngreso", query = "SELECT i FROM IsRolesPermisos i WHERE i.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "IsRolesPermisos.findByUsuarioModificacion", query = "SELECT i FROM IsRolesPermisos i WHERE i.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "IsRolesPermisos.findByFechaModificacion", query = "SELECT i FROM IsRolesPermisos i WHERE i.fechaModificacion = :fechaModificacion")})
public class IsRolesPermisos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_roles_permisos")
    private Integer idRolesPermisos;

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

    @JoinColumn(name = "id_permisos", referencedColumnName = "id_permisos")
    @ManyToOne(optional = false)
    private IsPermisos idPermisos;

    @JoinColumn(name = "id_roles", referencedColumnName = "id_roles")
    @ManyToOne(optional = false)
    private IsRoles idRoles;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public IsRolesPermisos() {
    }

    public IsRolesPermisos(Integer idRolesPermisos) {
        this.idRolesPermisos = idRolesPermisos;
    }

    public IsRolesPermisos(Integer idRolesPermisos, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idRolesPermisos = idRolesPermisos;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdRolesPermisos() {
        return this.idRolesPermisos;
    }

    public void setIdRolesPermisos(Integer idRolesPermisos) {
        this.idRolesPermisos = idRolesPermisos;
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

    public IsPermisos getIdPermisos() {
        return this.idPermisos;
    }

    public void setIdPermisos(IsPermisos idPermisos) {
        this.idPermisos = idPermisos;
    }

    public IsRoles getIdRoles() {
        return this.idRoles;
    }

    public void setIdRoles(IsRoles idRoles) {
        this.idRoles = idRoles;
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
        hash += (this.idRolesPermisos != null) ? this.idRolesPermisos.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsRolesPermisos)) {
            return false;
        }
        IsRolesPermisos other = (IsRolesPermisos) object;
        if ((this.idRolesPermisos == null && other.idRolesPermisos != null) || (this.idRolesPermisos != null && !this.idRolesPermisos.equals(other.idRolesPermisos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.IsRolesPermisos[ idRolesPermisos=" + this.idRolesPermisos + " ]";
    }
}
