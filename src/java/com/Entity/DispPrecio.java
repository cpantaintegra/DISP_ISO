package com.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "disp_precio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispPrecio.findAll", query = "SELECT d FROM DispPrecio d")
    , @NamedQuery(name = "DispPrecio.findByIdOrigen", query = "SELECT d FROM DispPrecio d WHERE d.idOrigen.idOrigen = :idOrigen")
    , @NamedQuery(name = "DispPrecio.findByServicioOrigen", query = "SELECT d FROM DispPrecio d WHERE d.idServicio.idServicio = :idServicio AND d.idOrigen.idOrigen = :idOrigen")
    , @NamedQuery(name = "DispPrecio.findByIdServicio", query = "SELECT d FROM DispPrecio d WHERE d.idServicio.idServicio = :idServicio")
    , @NamedQuery(name = "DispPrecio.findByIdPrecio", query = "SELECT d FROM DispPrecio d WHERE d.idPrecio = :idPrecio")
    , @NamedQuery(name = "DispPrecio.findByValor", query = "SELECT d FROM DispPrecio d WHERE d.valor = :valor")
    , @NamedQuery(name = "DispPrecio.findByDetalle", query = "SELECT d FROM DispPrecio d WHERE d.detalle = :detalle")
    , @NamedQuery(name = "DispPrecio.findByEstado", query = "SELECT d FROM DispPrecio d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispPrecio.findByUsuarioIngreso", query = "SELECT d FROM DispPrecio d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispPrecio.findByFechaIngreso", query = "SELECT d FROM DispPrecio d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispPrecio.findByUsuarioModificacion", query = "SELECT d FROM DispPrecio d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispPrecio.findByFechaModificacion", query = "SELECT d FROM DispPrecio d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispPrecio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_precio")
    private Integer idPrecio;

    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;

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

    @JoinColumn(name = "id_origen", referencedColumnName = "id_origen")
    @ManyToOne(optional = false)
    private DispOrigen idOrigen;

    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    @ManyToOne(optional = false)
    private DispServicio idServicio;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispPrecio() {
    }

    public DispPrecio(Integer idPrecio) {
        this.idPrecio = idPrecio;
    }

    public DispPrecio(Integer idPrecio, BigDecimal valor, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idPrecio = idPrecio;
        this.valor = valor;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdPrecio() {
        return this.idPrecio;
    }

    public void setIdPrecio(Integer idPrecio) {
        this.idPrecio = idPrecio;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
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

    public DispOrigen getIdOrigen() {
        return this.idOrigen;
    }

    public void setIdOrigen(DispOrigen idOrigen) {
        this.idOrigen = idOrigen;
    }

    public DispServicio getIdServicio() {
        return this.idServicio;
    }

    public void setIdServicio(DispServicio idServicio) {
        this.idServicio = idServicio;
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
        hash += (this.idPrecio != null) ? this.idPrecio.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispPrecio)) {
            return false;
        }
        DispPrecio other = (DispPrecio) object;
        if ((this.idPrecio == null && other.idPrecio != null) || (this.idPrecio != null && !this.idPrecio.equals(other.idPrecio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispPrecio[ idPrecio=" + this.idPrecio + " ]";
    }
}
