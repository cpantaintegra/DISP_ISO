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
@Table(name = "is_empresa_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsEmpresaRoles.findAll", query = "SELECT i FROM IsEmpresaRoles i")
    , @NamedQuery(name = "IsEmpresaRoles.findByIdEmpresaRoles", query = "SELECT i FROM IsEmpresaRoles i WHERE i.idEmpresaRoles = :idEmpresaRoles")
    , @NamedQuery(name = "IsEmpresaRoles.findByEstado", query = "SELECT i FROM IsEmpresaRoles i WHERE i.estado = :estado")
    , @NamedQuery(name = "IsEmpresaRoles.findByUsuarioIngreso", query = "SELECT i FROM IsEmpresaRoles i WHERE i.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "IsEmpresaRoles.findByFechaIngreso", query = "SELECT i FROM IsEmpresaRoles i WHERE i.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "IsEmpresaRoles.findByUsuarioModificacion", query = "SELECT i FROM IsEmpresaRoles i WHERE i.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "IsEmpresaRoles.findByFechaModificacion", query = "SELECT i FROM IsEmpresaRoles i WHERE i.fechaModificacion = :fechaModificacion")})
public class IsEmpresaRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empresa_roles")
    private Integer idEmpresaRoles;

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

    @JoinColumn(name = "id_roles", referencedColumnName = "id_roles")
    @ManyToOne(optional = false)
    private IsRoles idRoles;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public IsEmpresaRoles() {
    }

    public IsEmpresaRoles(Integer idEmpresaRoles) {
        this.idEmpresaRoles = idEmpresaRoles;
    }

    public IsEmpresaRoles(Integer idEmpresaRoles, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idEmpresaRoles = idEmpresaRoles;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdEmpresaRoles() {
        return this.idEmpresaRoles;
    }

    public void setIdEmpresaRoles(Integer idEmpresaRoles) {
        this.idEmpresaRoles = idEmpresaRoles;
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
        hash += (this.idEmpresaRoles != null) ? this.idEmpresaRoles.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsEmpresaRoles)) {
            return false;
        }
        IsEmpresaRoles other = (IsEmpresaRoles) object;
        if ((this.idEmpresaRoles == null && other.idEmpresaRoles != null) || (this.idEmpresaRoles != null && !this.idEmpresaRoles.equals(other.idEmpresaRoles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.IsEmpresaRoles[ idEmpresaRoles=" + this.idEmpresaRoles + " ]";
    }
}
