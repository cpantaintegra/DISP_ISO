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
@Table(name = "disp_diagnostico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispDiagnostico.findAll", query = "SELECT d FROM DispDiagnostico d")
    , @NamedQuery(name = "DispDiagnostico.findByIdDiagnostico", query = "SELECT d FROM DispDiagnostico d WHERE d.idDiagnostico = :idDiagnostico")
    , @NamedQuery(name = "DispDiagnostico.findByCodigo", query = "SELECT d FROM DispDiagnostico d WHERE d.codigo = :codigo")
    , @NamedQuery(name = "DispDiagnostico.findByEnfermedad", query = "SELECT d FROM DispDiagnostico d WHERE d.enfermedad = :enfermedad")
    , @NamedQuery(name = "DispDiagnostico.findByEstado", query = "SELECT d FROM DispDiagnostico d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispDiagnostico.findByUsuarioIngreso", query = "SELECT d FROM DispDiagnostico d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispDiagnostico.findByFechaIngreso", query = "SELECT d FROM DispDiagnostico d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispDiagnostico.findByUsuarioModificacion", query = "SELECT d FROM DispDiagnostico d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispDiagnostico.findByFechaModificacion", query = "SELECT d FROM DispDiagnostico d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispDiagnostico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_diagnostico")
    private Integer idDiagnostico;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "enfermedad")
    private String enfermedad;

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

    public DispDiagnostico() {
    }

    public DispDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public DispDiagnostico(Integer idDiagnostico, String codigo, String enfermedad, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idDiagnostico = idDiagnostico;
        this.codigo = codigo;
        this.enfermedad = enfermedad;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdDiagnostico() {
        return this.idDiagnostico;
    }

    public void setIdDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEnfermedad() {
        return this.enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
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
        hash += (this.idDiagnostico != null) ? this.idDiagnostico.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispDiagnostico)) {
            return false;
        }
        DispDiagnostico other = (DispDiagnostico) object;
        if ((this.idDiagnostico == null && other.idDiagnostico != null) || (this.idDiagnostico != null && !this.idDiagnostico.equals(other.idDiagnostico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispDiagnostico[ idDiagnostico=" + this.idDiagnostico + " ]";
    }
}
