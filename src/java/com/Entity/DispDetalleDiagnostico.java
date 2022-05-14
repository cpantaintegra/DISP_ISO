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
@Table(name = "disp_detalle_diagnostico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispDetalleDiagnostico.findAll", query = "SELECT d FROM DispDetalleDiagnostico d")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByIdDiagnostico", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.idDiagnostico.idDiagnostico = :idDiagnostico")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByIdCliente", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.idCliente.idCliente = :idCliente")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByIdDetalleDiagnostico", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.idDetalleDiagnostico = :idDetalleDiagnostico")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByTipo", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.tipo = :tipo")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByIdImagen", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.idImagen = :idImagen")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByEstado", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByUsuarioIngreso", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByFechaIngreso", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByUsuarioModificacion", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispDetalleDiagnostico.findByFechaModificacion", query = "SELECT d FROM DispDetalleDiagnostico d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispDetalleDiagnostico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_diagnostico")
    private Integer idDetalleDiagnostico;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "id_imagen")
    private Integer idImagen;

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

    @JoinColumn(name = "id_diagnostico", referencedColumnName = "id_diagnostico")
    @ManyToOne(optional = false)
    private DispDiagnostico idDiagnostico;

    @JoinColumn(name = "id_medico_personal", referencedColumnName = "id_medico_personal")
    @ManyToOne(optional = false)
    private DispMedicoPersonal idMedicoPersonal;

    @JoinColumn(name = "id_resultado", referencedColumnName = "id_resultado")
    @ManyToOne(optional = false)
    private DispResultado idResultado;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispDetalleDiagnostico() {
    }

    public DispDetalleDiagnostico(Integer idDetalleDiagnostico) {
        this.idDetalleDiagnostico = idDetalleDiagnostico;
    }

    public DispDetalleDiagnostico(Integer idDetalleDiagnostico, String tipo, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idDetalleDiagnostico = idDetalleDiagnostico;
        this.tipo = tipo;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdDetalleDiagnostico() {
        return this.idDetalleDiagnostico;
    }

    public void setIdDetalleDiagnostico(Integer idDetalleDiagnostico) {
        this.idDetalleDiagnostico = idDetalleDiagnostico;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdImagen() {
        return this.idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
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

    public DispDiagnostico getIdDiagnostico() {
        return this.idDiagnostico;
    }

    public void setIdDiagnostico(DispDiagnostico idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public DispMedicoPersonal getIdMedicoPersonal() {
        return this.idMedicoPersonal;
    }

    public void setIdMedicoPersonal(DispMedicoPersonal idMedicoPersonal) {
        this.idMedicoPersonal = idMedicoPersonal;
    }

    public DispResultado getIdResultado() {
        return this.idResultado;
    }

    public void setIdResultado(DispResultado idResultado) {
        this.idResultado = idResultado;
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
        hash += (this.idDetalleDiagnostico != null) ? this.idDetalleDiagnostico.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispDetalleDiagnostico)) {
            return false;
        }
        DispDetalleDiagnostico other = (DispDetalleDiagnostico) object;
        if ((this.idDetalleDiagnostico == null && other.idDetalleDiagnostico != null) || (this.idDetalleDiagnostico != null && !this.idDetalleDiagnostico.equals(other.idDetalleDiagnostico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispDetalleDiagnostico[ idDetalleDiagnostico=" + this.idDetalleDiagnostico + " ]";
    }
}
