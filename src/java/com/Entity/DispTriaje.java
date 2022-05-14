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
@Table(name = "disp_triaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispTriaje.findAll", query = "SELECT d FROM DispTriaje d")
    , @NamedQuery(name = "DispTriaje.findByIdAgendamiento", query = "SELECT d FROM DispTriaje d WHERE d.idAgendamiento.idAgendamiento = :idAgendamiento")
    , @NamedQuery(name = "DispTriaje.findByIdTriaje", query = "SELECT d FROM DispTriaje d WHERE d.idTriaje = :idTriaje")
    , @NamedQuery(name = "DispTriaje.findByPresionArterial", query = "SELECT d FROM DispTriaje d WHERE d.presionArterial = :presionArterial")
    , @NamedQuery(name = "DispTriaje.findByTemperatura", query = "SELECT d FROM DispTriaje d WHERE d.temperatura = :temperatura")
    , @NamedQuery(name = "DispTriaje.findByFrecuenciaRespiratoria", query = "SELECT d FROM DispTriaje d WHERE d.frecuenciaRespiratoria = :frecuenciaRespiratoria")
    , @NamedQuery(name = "DispTriaje.findByFrecuenciaCardiaca", query = "SELECT d FROM DispTriaje d WHERE d.frecuenciaCardiaca = :frecuenciaCardiaca")
    , @NamedQuery(name = "DispTriaje.findBySaturacion", query = "SELECT d FROM DispTriaje d WHERE d.saturacion = :saturacion")
    , @NamedQuery(name = "DispTriaje.findByPeso", query = "SELECT d FROM DispTriaje d WHERE d.peso = :peso")
    , @NamedQuery(name = "DispTriaje.findByTalla", query = "SELECT d FROM DispTriaje d WHERE d.talla = :talla")
    , @NamedQuery(name = "DispTriaje.findByImc", query = "SELECT d FROM DispTriaje d WHERE d.imc = :imc")
    , @NamedQuery(name = "DispTriaje.findByEstado", query = "SELECT d FROM DispTriaje d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispTriaje.findByUsuarioIngreso", query = "SELECT d FROM DispTriaje d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispTriaje.findByFechaIngreso", query = "SELECT d FROM DispTriaje d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispTriaje.findByUsuarioModificacion", query = "SELECT d FROM DispTriaje d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispTriaje.findByFechaModificacion", query = "SELECT d FROM DispTriaje d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispTriaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_triaje")
    private Integer idTriaje;

    @Size(max = 45)
    @Column(name = "presion_arterial")
    private String presionArterial;

    @Size(max = 45)
    @Column(name = "temperatura")
    private String temperatura;

    @Size(max = 45)
    @Column(name = "frecuencia_respiratoria")
    private String frecuenciaRespiratoria;

    @Size(max = 45)
    @Column(name = "frecuencia_cardiaca")
    private String frecuenciaCardiaca;

    @Size(max = 45)
    @Column(name = "saturacion")
    private String saturacion;

    @Size(max = 45)
    @Column(name = "peso")
    private String peso;

    @Size(max = 45)
    @Column(name = "talla")
    private String talla;

    @Size(max = 45)
    @Column(name = "imc")
    private String imc;

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

    public DispTriaje() {
    }

    public DispTriaje(Integer idTriaje) {
        this.idTriaje = idTriaje;
    }

    public DispTriaje(Integer idTriaje, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idTriaje = idTriaje;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdTriaje() {
        return this.idTriaje;
    }

    public void setIdTriaje(Integer idTriaje) {
        this.idTriaje = idTriaje;
    }

    public String getPresionArterial() {
        return this.presionArterial;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public String getTemperatura() {
        return this.temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getFrecuenciaRespiratoria() {
        return this.frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(String frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public String getFrecuenciaCardiaca() {
        return this.frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(String frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public String getSaturacion() {
        return this.saturacion;
    }

    public void setSaturacion(String saturacion) {
        this.saturacion = saturacion;
    }

    public String getPeso() {
        return this.peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTalla() {
        return this.talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getImc() {
        return this.imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idTriaje != null) ? this.idTriaje.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispTriaje)) {
            return false;
        }
        DispTriaje other = (DispTriaje) object;
        if ((this.idTriaje == null && other.idTriaje != null) || (this.idTriaje != null && !this.idTriaje.equals(other.idTriaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispTriaje[ idTriaje=" + this.idTriaje + " ]";
    }
}
