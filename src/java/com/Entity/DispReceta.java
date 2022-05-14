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
@Table(name = "disp_receta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispReceta.findAll", query = "SELECT d FROM DispReceta d")
    , @NamedQuery(name = "DispReceta.findAllActivos", query = "SELECT d FROM DispReceta d where d.estado = 'A' AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispReceta.findByIdAgendamiento", query = "SELECT d FROM DispReceta d WHERE d.idAgendamiento.idAgendamiento = :idAgendamiento")
    , @NamedQuery(name = "DispReceta.findByIdReceta", query = "SELECT d FROM DispReceta d WHERE d.idReceta = :idReceta")
    , @NamedQuery(name = "DispReceta.findByObservaciones", query = "SELECT d FROM DispReceta d WHERE d.observaciones = :observaciones")
    , @NamedQuery(name = "DispReceta.findByEstado", query = "SELECT d FROM DispReceta d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispReceta.findByUsuarioIngreso", query = "SELECT d FROM DispReceta d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispReceta.findByFechaIngreso", query = "SELECT d FROM DispReceta d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispReceta.findByUsuarioModificacion", query = "SELECT d FROM DispReceta d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispReceta.findByFechaModificacion", query = "SELECT d FROM DispReceta d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispReceta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_receta")
    private Integer idReceta;

    @Size(max = 10000)
    @Column(name = "observaciones")
    private String observaciones;

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

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idReceta")
    private Collection<DispDetalleReceta> dispDetalleRecetaCollection;

    public DispReceta() {
    }

    public DispReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public DispReceta(Integer idReceta, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idReceta = idReceta;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdReceta() {
        return this.idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
    public Collection<DispDetalleReceta> getDispDetalleRecetaCollection() {
        return this.dispDetalleRecetaCollection;
    }

    public void setDispDetalleRecetaCollection(Collection<DispDetalleReceta> dispDetalleRecetaCollection) {
        this.dispDetalleRecetaCollection = dispDetalleRecetaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idReceta != null) ? this.idReceta.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispReceta)) {
            return false;
        }
        DispReceta other = (DispReceta) object;
        if ((this.idReceta == null && other.idReceta != null) || (this.idReceta != null && !this.idReceta.equals(other.idReceta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispReceta[ idReceta=" + this.idReceta + " ]";
    }
}
