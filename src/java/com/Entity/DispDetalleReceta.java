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
@Table(name = "disp_detalle_receta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispDetalleReceta.findAll", query = "SELECT d FROM DispDetalleReceta d")
    , @NamedQuery(name = "DispDetalleReceta.findByIdReceta", query = "SELECT d FROM DispDetalleReceta d WHERE d.idReceta.idReceta = :idReceta")
    , @NamedQuery(name = "DispDetalleReceta.findByIdDetalleReceta", query = "SELECT d FROM DispDetalleReceta d WHERE d.idDetalleReceta = :idDetalleReceta")
    , @NamedQuery(name = "DispDetalleReceta.findByDosis", query = "SELECT d FROM DispDetalleReceta d WHERE d.dosis = :dosis")
    , @NamedQuery(name = "DispDetalleReceta.findByDuracion", query = "SELECT d FROM DispDetalleReceta d WHERE d.duracion = :duracion")
    , @NamedQuery(name = "DispDetalleReceta.findByCantidad", query = "SELECT d FROM DispDetalleReceta d WHERE d.cantidad = :cantidad")
    , @NamedQuery(name = "DispDetalleReceta.findByEstado", query = "SELECT d FROM DispDetalleReceta d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispDetalleReceta.findByUsuarioIngreso", query = "SELECT d FROM DispDetalleReceta d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispDetalleReceta.findByFechaIngreso", query = "SELECT d FROM DispDetalleReceta d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispDetalleReceta.findByUsuarioModificacion", query = "SELECT d FROM DispDetalleReceta d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispDetalleReceta.findByFechaModificacion", query = "SELECT d FROM DispDetalleReceta d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispDetalleReceta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_receta")
    private Integer idDetalleReceta;

    @Size(max = 2000)
    @Column(name = "dosis")
    private String dosis;

    @Size(max = 200)
    @Column(name = "duracion")
    private String duracion;

    @Column(name = "cantidad")
    private Integer cantidad;

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

    @JoinColumn(name = "id_medicamento", referencedColumnName = "id_medicamento")
    @ManyToOne(optional = false)
    private DispMedicamento idMedicamento;

    @JoinColumn(name = "id_receta", referencedColumnName = "id_receta")
    @ManyToOne(optional = false)
    private DispReceta idReceta;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispDetalleReceta() {
    }

    public DispDetalleReceta(Integer idDetalleReceta) {
        this.idDetalleReceta = idDetalleReceta;
    }

    public DispDetalleReceta(Integer idDetalleReceta, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idDetalleReceta = idDetalleReceta;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdDetalleReceta() {
        return this.idDetalleReceta;
    }

    public void setIdDetalleReceta(Integer idDetalleReceta) {
        this.idDetalleReceta = idDetalleReceta;
    }

    public String getDosis() {
        return this.dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDuracion() {
        return this.duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
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

    public DispMedicamento getIdMedicamento() {
        return this.idMedicamento;
    }

    public void setIdMedicamento(DispMedicamento idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public DispReceta getIdReceta() {
        return this.idReceta;
    }

    public void setIdReceta(DispReceta idReceta) {
        this.idReceta = idReceta;
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
        hash += (this.idDetalleReceta != null) ? this.idDetalleReceta.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispDetalleReceta)) {
            return false;
        }
        DispDetalleReceta other = (DispDetalleReceta) object;
        if ((this.idDetalleReceta == null && other.idDetalleReceta != null) || (this.idDetalleReceta != null && !this.idDetalleReceta.equals(other.idDetalleReceta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispDetalleReceta[ idDetalleReceta=" + this.idDetalleReceta + " ]";
    }
}
