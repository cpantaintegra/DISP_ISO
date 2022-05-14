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
@Table(name = "disp_imagen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispImagen.findAll", query = "SELECT d FROM DispImagen d")
    , @NamedQuery(name = "DispImagen.findByIdImagen", query = "SELECT d FROM DispImagen d WHERE d.idImagen = :idImagen")
    , @NamedQuery(name = "DispImagen.findByObservacion", query = "SELECT d FROM DispImagen d WHERE d.observacion = :observacion")
    , @NamedQuery(name = "DispImagen.findByNoombre", query = "SELECT d FROM DispImagen d WHERE d.noombre = :noombre")
    , @NamedQuery(name = "DispImagen.findByUrl", query = "SELECT d FROM DispImagen d WHERE d.url = :url")
    , @NamedQuery(name = "DispImagen.findByEstado", query = "SELECT d FROM DispImagen d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispImagen.findByUsuarioIngreso", query = "SELECT d FROM DispImagen d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispImagen.findByFechaIngreso", query = "SELECT d FROM DispImagen d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispImagen.findByUsuarioModificacion", query = "SELECT d FROM DispImagen d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispImagen.findByFechaModificacion", query = "SELECT d FROM DispImagen d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispImagen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_imagen")
    private Integer idImagen;

    @Size(max = 30)
    @Column(name = "observacion")
    private String observacion;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "noombre")
    private String noombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "url")
    private String url;

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

    public DispImagen() {
    }

    public DispImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public DispImagen(Integer idImagen, String noombre, String url, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idImagen = idImagen;
        this.noombre = noombre;
        this.url = url;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdImagen() {
        return this.idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNoombre() {
        return this.noombre;
    }

    public void setNoombre(String noombre) {
        this.noombre = noombre;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        hash += (this.idImagen != null) ? this.idImagen.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispImagen)) {
            return false;
        }
        DispImagen other = (DispImagen) object;
        if ((this.idImagen == null && other.idImagen != null) || (this.idImagen != null && !this.idImagen.equals(other.idImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispImagen[ idImagen=" + this.idImagen + " ]";
    }
}
