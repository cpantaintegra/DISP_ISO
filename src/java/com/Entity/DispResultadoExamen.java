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
@Table(name = "disp_resultado_examen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispResultadoExamen.findAll", query = "SELECT d FROM DispResultadoExamen d")
    , @NamedQuery(name = "DispResultadoExamen.findByIdResultadoExamen", query = "SELECT d FROM DispResultadoExamen d WHERE d.idResultadoExamen = :idResultadoExamen")
    , @NamedQuery(name = "DispResultadoExamen.findByConclusion", query = "SELECT d FROM DispResultadoExamen d WHERE d.conclusion = :conclusion")
    , @NamedQuery(name = "DispResultadoExamen.findByEstado", query = "SELECT d FROM DispResultadoExamen d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispResultadoExamen.findByUsuarioIngreso", query = "SELECT d FROM DispResultadoExamen d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispResultadoExamen.findByFechaIngreso", query = "SELECT d FROM DispResultadoExamen d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispResultadoExamen.findByUsuarioModificacion", query = "SELECT d FROM DispResultadoExamen d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispResultadoExamen.findByFechaModificacion", query = "SELECT d FROM DispResultadoExamen d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispResultadoExamen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resultado_examen")
    private Integer idResultadoExamen;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "conclusion")
    private String conclusion;

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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idResultadoExamen")
    private Collection<DispResultadoDetalle> dispResultadoDetalleCollection;

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

    public DispResultadoExamen() {
    }

    public DispResultadoExamen(Integer idResultadoExamen) {
        this.idResultadoExamen = idResultadoExamen;
    }

    public DispResultadoExamen(Integer idResultadoExamen, String conclusion, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idResultadoExamen = idResultadoExamen;
        this.conclusion = conclusion;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdResultadoExamen() {
        return this.idResultadoExamen;
    }

    public void setIdResultadoExamen(Integer idResultadoExamen) {
        this.idResultadoExamen = idResultadoExamen;
    }

    public String getConclusion() {
        return this.conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
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
    public Collection<DispResultadoDetalle> getDispResultadoDetalleCollection() {
        return this.dispResultadoDetalleCollection;
    }

    public void setDispResultadoDetalleCollection(Collection<DispResultadoDetalle> dispResultadoDetalleCollection) {
        this.dispResultadoDetalleCollection = dispResultadoDetalleCollection;
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
        hash += (this.idResultadoExamen != null) ? this.idResultadoExamen.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispResultadoExamen)) {
            return false;
        }
        DispResultadoExamen other = (DispResultadoExamen) object;
        if ((this.idResultadoExamen == null && other.idResultadoExamen != null) || (this.idResultadoExamen != null && !this.idResultadoExamen.equals(other.idResultadoExamen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispResultadoExamen[ idResultadoExamen=" + this.idResultadoExamen + " ]";
    }
}
