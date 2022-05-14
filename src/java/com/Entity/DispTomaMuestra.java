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
@Table(name = "disp_toma_muestra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispTomaMuestra.findAll", query = "SELECT d FROM DispTomaMuestra d")
    , @NamedQuery(name = "DispTomaMuestra.findByIdTomaMuestra", query = "SELECT d FROM DispTomaMuestra d WHERE d.idTomaMuestra = :idTomaMuestra")
    , @NamedQuery(name = "DispTomaMuestra.findByCantidad", query = "SELECT d FROM DispTomaMuestra d WHERE d.cantidad = :cantidad")
    , @NamedQuery(name = "DispTomaMuestra.findByFecha", query = "SELECT d FROM DispTomaMuestra d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "DispTomaMuestra.findByEstado", query = "SELECT d FROM DispTomaMuestra d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispTomaMuestra.findByUsuarioIngreso", query = "SELECT d FROM DispTomaMuestra d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispTomaMuestra.findByFechaIngreso", query = "SELECT d FROM DispTomaMuestra d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispTomaMuestra.findByUsuarioModificacion", query = "SELECT d FROM DispTomaMuestra d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispTomaMuestra.findByFechaModificacion", query = "SELECT d FROM DispTomaMuestra d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispTomaMuestra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_toma_muestra")
    private Integer idTomaMuestra;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

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

    @JoinColumn(name = "id_muestra", referencedColumnName = "id_muestra")
    @ManyToOne(optional = false)
    private DispMuestra idMuestra;

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

    public DispTomaMuestra() {
    }

    public DispTomaMuestra(Integer idTomaMuestra) {
        this.idTomaMuestra = idTomaMuestra;
    }

    public DispTomaMuestra(Integer idTomaMuestra, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idTomaMuestra = idTomaMuestra;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdTomaMuestra() {
        return this.idTomaMuestra;
    }

    public void setIdTomaMuestra(Integer idTomaMuestra) {
        this.idTomaMuestra = idTomaMuestra;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public DispMuestra getIdMuestra() {
        return this.idMuestra;
    }

    public void setIdMuestra(DispMuestra idMuestra) {
        this.idMuestra = idMuestra;
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
        hash += (this.idTomaMuestra != null) ? this.idTomaMuestra.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispTomaMuestra)) {
            return false;
        }
        DispTomaMuestra other = (DispTomaMuestra) object;
        if ((this.idTomaMuestra == null && other.idTomaMuestra != null) || (this.idTomaMuestra != null && !this.idTomaMuestra.equals(other.idTomaMuestra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispTomaMuestra[ idTomaMuestra=" + this.idTomaMuestra + " ]";
    }
}
