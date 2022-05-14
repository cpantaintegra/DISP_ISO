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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "disp_resultado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispResultado.findAll", query = "SELECT d FROM DispResultado d")
    , @NamedQuery(name = "DispResultado.findByIdAgendamiento", query = "SELECT d FROM DispResultado d WHERE d.idAgendamiento.idAgendamiento = :idAgendamiento")
    , @NamedQuery(name = "DispResultado.findByIdResultado", query = "SELECT d FROM DispResultado d WHERE d.idResultado = :idResultado")
    , @NamedQuery(name = "DispResultado.findByMotivoConsulta", query = "SELECT d FROM DispResultado d WHERE d.motivoConsulta = :motivoConsulta")
    , @NamedQuery(name = "DispResultado.findByTiempoEnfermedad", query = "SELECT d FROM DispResultado d WHERE d.tiempoEnfermedad = :tiempoEnfermedad")
    , @NamedQuery(name = "DispResultado.findByAntecedentes", query = "SELECT d FROM DispResultado d WHERE d.antecedentes = :antecedentes")
    , @NamedQuery(name = "DispResultado.findByExamenFisico", query = "SELECT d FROM DispResultado d WHERE d.examenFisico = :examenFisico")
    , @NamedQuery(name = "DispResultado.findByTratamiento", query = "SELECT d FROM DispResultado d WHERE d.tratamiento = :tratamiento")
    , @NamedQuery(name = "DispResultado.findByProximaCita", query = "SELECT d FROM DispResultado d WHERE d.proximaCita = :proximaCita")
    , @NamedQuery(name = "DispResultado.findByPlan", query = "SELECT d FROM DispResultado d WHERE d.plan = :plan")
    , @NamedQuery(name = "DispResultado.findByFecha", query = "SELECT d FROM DispResultado d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "DispResultado.findByHora", query = "SELECT d FROM DispResultado d WHERE d.hora = :hora")
    , @NamedQuery(name = "DispResultado.findByEstado", query = "SELECT d FROM DispResultado d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispResultado.findByUsuarioIngreso", query = "SELECT d FROM DispResultado d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispResultado.findByFechaIngreso", query = "SELECT d FROM DispResultado d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispResultado.findByUsuarioModificacion", query = "SELECT d FROM DispResultado d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispResultado.findByFechaModificacion", query = "SELECT d FROM DispResultado d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispResultado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resultado")
    private Integer idResultado;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    @Size(max = 30)
    @Column(name = "tiempo_enfermedad")
    private String tiempoEnfermedad;

    @Size(max = 10000)
    @Column(name = "antecedentes")
    private String antecedentes;

    @Size(max = 10000)
    @Column(name = "examen_fisico")
    private String examenFisico;

    @Size(max = 10000)
    @Column(name = "tratamiento")
    private String tratamiento;

    @Column(name = "proxima_cita")
    @Temporal(TemporalType.TIMESTAMP)
    private Date proximaCita;

    @Size(max = 10000)
    @Column(name = "plan")
    private String plan;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;

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
    @OneToOne(optional = false)
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

    public DispResultado() {
    }

    public DispResultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public DispResultado(Integer idResultado, String motivoConsulta, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idResultado = idResultado;
        this.motivoConsulta = motivoConsulta;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdResultado() {
        return this.idResultado;
    }

    public void setIdResultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public String getMotivoConsulta() {
        return this.motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getTiempoEnfermedad() {
        return this.tiempoEnfermedad;
    }

    public void setTiempoEnfermedad(String tiempoEnfermedad) {
        this.tiempoEnfermedad = tiempoEnfermedad;
    }

    public String getAntecedentes() {
        return this.antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getExamenFisico() {
        return this.examenFisico;
    }

    public void setExamenFisico(String examenFisico) {
        this.examenFisico = examenFisico;
    }

    public String getTratamiento() {
        return this.tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Date getProximaCita() {
        return this.proximaCita;
    }

    public void setProximaCita(Date proximaCita) {
        this.proximaCita = proximaCita;
    }

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return this.hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
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
        hash += (this.idResultado != null) ? this.idResultado.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispResultado)) {
            return false;
        }
        DispResultado other = (DispResultado) object;
        if ((this.idResultado == null && other.idResultado != null) || (this.idResultado != null && !this.idResultado.equals(other.idResultado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispResultado[ idResultado=" + this.idResultado + " ]";
    }
}
