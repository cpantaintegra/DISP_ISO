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
@Table(name = "disp_detalle_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispDetalleFactura.findAll", query = "SELECT d FROM DispDetalleFactura d")
    , @NamedQuery(name = "DispDetalleFactura.findByIdDetalleFactura", query = "SELECT d FROM DispDetalleFactura d WHERE d.idDetalleFactura = :idDetalleFactura")
    , @NamedQuery(name = "DispDetalleFactura.findByCantidad", query = "SELECT d FROM DispDetalleFactura d WHERE d.cantidad = :cantidad")
    , @NamedQuery(name = "DispDetalleFactura.findByEstado", query = "SELECT d FROM DispDetalleFactura d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispDetalleFactura.findByUsuarioIngreso", query = "SELECT d FROM DispDetalleFactura d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispDetalleFactura.findByFechaIngreso", query = "SELECT d FROM DispDetalleFactura d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispDetalleFactura.findByUsuarioModificacion", query = "SELECT d FROM DispDetalleFactura d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispDetalleFactura.findByFechaModificacion", query = "SELECT d FROM DispDetalleFactura d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispDetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_factura")
    private Integer idDetalleFactura;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;

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

    @JoinColumn(name = "id_agendamiento", referencedColumnName = "id_agendamiento")
    @ManyToOne(optional = false)
    private DispAgendamiento idAgendamiento;

    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne(optional = false)
    private DispFactura idFactura;

    @JoinColumn(name = "id_solicitud_examen", referencedColumnName = "id_solicitud_examen")
    @ManyToOne(optional = false)
    private DispSolicitudExamen idSolicitudExamen;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispDetalleFactura() {
    }

    public DispDetalleFactura(Integer idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }

    public DispDetalleFactura(Integer idDetalleFactura, int cantidad, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idDetalleFactura = idDetalleFactura;
        this.cantidad = cantidad;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdDetalleFactura() {
        return this.idDetalleFactura;
    }

    public void setIdDetalleFactura(Integer idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public DispAgendamiento getIdAgendamiento() {
        return this.idAgendamiento;
    }

    public void setIdAgendamiento(DispAgendamiento idAgendamiento) {
        this.idAgendamiento = idAgendamiento;
    }

    public DispFactura getIdFactura() {
        return this.idFactura;
    }

    public void setIdFactura(DispFactura idFactura) {
        this.idFactura = idFactura;
    }

    public DispSolicitudExamen getIdSolicitudExamen() {
        return this.idSolicitudExamen;
    }

    public void setIdSolicitudExamen(DispSolicitudExamen idSolicitudExamen) {
        this.idSolicitudExamen = idSolicitudExamen;
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
        hash += (this.idDetalleFactura != null) ? this.idDetalleFactura.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispDetalleFactura)) {
            return false;
        }
        DispDetalleFactura other = (DispDetalleFactura) object;
        if ((this.idDetalleFactura == null && other.idDetalleFactura != null) || (this.idDetalleFactura != null && !this.idDetalleFactura.equals(other.idDetalleFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispDetalleFactura[ idDetalleFactura=" + this.idDetalleFactura + " ]";
    }
}
