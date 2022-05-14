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
@Table(name = "disp_consultorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispConsultorio.findAll", query = "SELECT d FROM DispConsultorio d")
    , @NamedQuery(name = "DispConsultorio.findAllActivos", query = "SELECT d FROM DispConsultorio d where d.estado = 'A' AND d.idConsultorio != 1 AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispConsultorio.findByIdEspecialidad", query = "SELECT d FROM DispConsultorio d WHERE d.idEspecialidad.idEspecialidad = :idEspecialidad")
    , @NamedQuery(name = "DispConsultorio.findByNombreByIDs", query = "SELECT d FROM DispConsultorio d WHERE d.nombre = :nombre AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispConsultorio.findByIdConsultorio", query = "SELECT d FROM DispConsultorio d WHERE d.idConsultorio = :idConsultorio")
    , @NamedQuery(name = "DispConsultorio.findByNombre", query = "SELECT d FROM DispConsultorio d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispConsultorio.findByDetalle", query = "SELECT d FROM DispConsultorio d WHERE d.detalle = :detalle")
    , @NamedQuery(name = "DispConsultorio.findByEstado", query = "SELECT d FROM DispConsultorio d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispConsultorio.findByUsuarioIngreso", query = "SELECT d FROM DispConsultorio d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispConsultorio.findByFechaIngreso", query = "SELECT d FROM DispConsultorio d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispConsultorio.findByUsuarioModificacion", query = "SELECT d FROM DispConsultorio d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispConsultorio.findByFechaModificacion", query = "SELECT d FROM DispConsultorio d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispConsultorio implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idConsultorio")
    private Collection<DispMedicoPersonal> dispMedicoPersonalCollection;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_consultorio")
    private Integer idConsultorio;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
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

    @JoinColumn(name = "id_especialidad", referencedColumnName = "id_especialidad")
    @ManyToOne(optional = false)
    private DispEspecialidad idEspecialidad;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispConsultorio() {
    }

    public DispConsultorio(Integer idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public DispConsultorio(Integer idConsultorio, String nombre, String detalle, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idConsultorio = idConsultorio;
        this.nombre = nombre;
        this.detalle = detalle;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdConsultorio() {
        return this.idConsultorio;
    }

    public void setIdConsultorio(Integer idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public DispEspecialidad getIdEspecialidad() {
        return this.idEspecialidad;
    }

    public void setIdEspecialidad(DispEspecialidad idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
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
        hash += (this.idConsultorio != null) ? this.idConsultorio.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispConsultorio)) {
            return false;
        }
        DispConsultorio other = (DispConsultorio) object;
        if ((this.idConsultorio == null && other.idConsultorio != null) || (this.idConsultorio != null && !this.idConsultorio.equals(other.idConsultorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispConsultorio[ idConsultorio=" + this.idConsultorio + " ]";
    }

    @XmlTransient
    public Collection<DispMedicoPersonal> getDispMedicoPersonalCollection() {
        return this.dispMedicoPersonalCollection;
    }

    public void setDispMedicoPersonalCollection(Collection<DispMedicoPersonal> dispMedicoPersonalCollection) {
        this.dispMedicoPersonalCollection = dispMedicoPersonalCollection;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
