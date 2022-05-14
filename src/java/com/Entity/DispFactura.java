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
@Table(name = "disp_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispFactura.findAll", query = "SELECT d FROM DispFactura d")
    , @NamedQuery(name = "DispFactura.findByIdFactura", query = "SELECT d FROM DispFactura d WHERE d.idFactura = :idFactura")
    , @NamedQuery(name = "DispFactura.findByFecha", query = "SELECT d FROM DispFactura d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "DispFactura.findByDescuento", query = "SELECT d FROM DispFactura d WHERE d.descuento = :descuento")
    , @NamedQuery(name = "DispFactura.findByIva", query = "SELECT d FROM DispFactura d WHERE d.iva = :iva")
    , @NamedQuery(name = "DispFactura.findByTotal", query = "SELECT d FROM DispFactura d WHERE d.total = :total")
    , @NamedQuery(name = "DispFactura.findByEstado", query = "SELECT d FROM DispFactura d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispFactura.findByUsuarioIngreso", query = "SELECT d FROM DispFactura d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispFactura.findByFechaIngreso", query = "SELECT d FROM DispFactura d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispFactura.findByUsuarioModificacion", query = "SELECT d FROM DispFactura d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispFactura.findByFechaModificacion", query = "SELECT d FROM DispFactura d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispFactura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_factura")
    private Integer idFactura;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "descuento")
    private BigDecimal descuento;

    @Column(name = "iva")
    private BigDecimal iva;

    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private BigDecimal total;

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

    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
    private DispCliente idCliente;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispFactura() {
    }

    public DispFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public DispFactura(Integer idFactura, Date fecha, BigDecimal total, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdFactura() {
        return this.idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getDescuento() {
        return this.descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getIva() {
        return this.iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public DispCliente getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(DispCliente idCliente) {
        this.idCliente = idCliente;
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
        hash += (this.idFactura != null) ? this.idFactura.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispFactura)) {
            return false;
        }
        DispFactura other = (DispFactura) object;
        if ((this.idFactura == null && other.idFactura != null) || (this.idFactura != null && !this.idFactura.equals(other.idFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispFactura[ idFactura=" + this.idFactura + " ]";
    }
}
