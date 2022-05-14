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
@Table(name = "disp_resultado_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispResultadoDetalle.findAll", query = "SELECT d FROM DispResultadoDetalle d")
    , @NamedQuery(name = "DispResultadoDetalle.findByIdResultadoDetalle", query = "SELECT d FROM DispResultadoDetalle d WHERE d.idResultadoDetalle = :idResultadoDetalle")
    , @NamedQuery(name = "DispResultadoDetalle.findByResultado", query = "SELECT d FROM DispResultadoDetalle d WHERE d.resultado = :resultado")
    , @NamedQuery(name = "DispResultadoDetalle.findByObservacion", query = "SELECT d FROM DispResultadoDetalle d WHERE d.observacion = :observacion")
    , @NamedQuery(name = "DispResultadoDetalle.findByEstado", query = "SELECT d FROM DispResultadoDetalle d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispResultadoDetalle.findByUsuarioIngreso", query = "SELECT d FROM DispResultadoDetalle d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispResultadoDetalle.findByFechaIngreso", query = "SELECT d FROM DispResultadoDetalle d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispResultadoDetalle.findByUsuarioModificacion", query = "SELECT d FROM DispResultadoDetalle d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispResultadoDetalle.findByFechaModificacion", query = "SELECT d FROM DispResultadoDetalle d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispResultadoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resultado_detalle")
    private Integer idResultadoDetalle;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "resultado")
    private String resultado;

    @Column(name = "observacion")
    private Integer observacion;

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

    @JoinColumn(name = "id_resultado_examen", referencedColumnName = "id_resultado_examen")
    @ManyToOne(optional = false)
    private DispResultadoExamen idResultadoExamen;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispResultadoDetalle() {
    }

    public DispResultadoDetalle(Integer idResultadoDetalle) {
        this.idResultadoDetalle = idResultadoDetalle;
    }

    public DispResultadoDetalle(Integer idResultadoDetalle, String resultado, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idResultadoDetalle = idResultadoDetalle;
        this.resultado = resultado;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdResultadoDetalle() {
        return this.idResultadoDetalle;
    }

    public void setIdResultadoDetalle(Integer idResultadoDetalle) {
        this.idResultadoDetalle = idResultadoDetalle;
    }

    public String getResultado() {
        return this.resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Integer getObservacion() {
        return this.observacion;
    }

    public void setObservacion(Integer observacion) {
        this.observacion = observacion;
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

    public DispResultadoExamen getIdResultadoExamen() {
        return this.idResultadoExamen;
    }

    public void setIdResultadoExamen(DispResultadoExamen idResultadoExamen) {
        this.idResultadoExamen = idResultadoExamen;
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
        hash += (this.idResultadoDetalle != null) ? this.idResultadoDetalle.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispResultadoDetalle)) {
            return false;
        }
        DispResultadoDetalle other = (DispResultadoDetalle) object;
        if ((this.idResultadoDetalle == null && other.idResultadoDetalle != null) || (this.idResultadoDetalle != null && !this.idResultadoDetalle.equals(other.idResultadoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispResultadoDetalle[ idResultadoDetalle=" + this.idResultadoDetalle + " ]";
    }
}
