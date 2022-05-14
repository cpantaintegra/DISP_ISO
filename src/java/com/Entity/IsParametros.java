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
@Table(name = "is_parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsParametros.findAll", query = "SELECT i FROM IsParametros i")
    , @NamedQuery(name = "IsParametros.findAllActivos", query = "SELECT i FROM IsParametros i where i.estado = 'A' ORDER BY i.codigo")
    , @NamedQuery(name = "IsParametros.findByIdParametro", query = "SELECT i FROM IsParametros i WHERE i.idParametro = :idParametro")
    , @NamedQuery(name = "IsParametros.findByCodigo", query = "SELECT i FROM IsParametros i WHERE i.codigo = :codigo AND i.idEmpresa.idEmpresa = :empresa AND i.idCiudad.idCiudad = :ciudad AND i.idSector.idSector = :sector AND i.estado = 'A'")
    , @NamedQuery(name = "IsParametros.findByValor", query = "SELECT i FROM IsParametros i WHERE i.valor = :valor")
    , @NamedQuery(name = "IsParametros.findByDetalle", query = "SELECT i FROM IsParametros i WHERE i.detalle = :detalle")
    , @NamedQuery(name = "IsParametros.findByEstado", query = "SELECT i FROM IsParametros i WHERE i.estado = :estado")
    , @NamedQuery(name = "IsParametros.findByUsuarioIngreso", query = "SELECT i FROM IsParametros i WHERE i.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "IsParametros.findByFechaIngreso", query = "SELECT i FROM IsParametros i WHERE i.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "IsParametros.findByUsuarioModificacion", query = "SELECT i FROM IsParametros i WHERE i.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "IsParametros.findByFechaModificacion", query = "SELECT i FROM IsParametros i WHERE i.fechaModificacion = :fechaModificacion")})
public class IsParametros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro")
    private Integer idParametro;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigo")
    private String codigo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "valor")
    private String valor;

    @Size(max = 200)
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

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public IsParametros() {
    }

    public IsParametros(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public IsParametros(Integer idParametro, String codigo, String valor, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idParametro = idParametro;
        this.codigo = codigo;
        this.valor = valor;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdParametro() {
        return this.idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
        hash += (this.idParametro != null) ? this.idParametro.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsParametros)) {
            return false;
        }
        IsParametros other = (IsParametros) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.IsParametros[ idParametro=" + this.idParametro + " ]";
    }
}
