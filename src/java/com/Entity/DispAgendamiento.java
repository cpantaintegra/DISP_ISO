package com.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "disp_agendamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispAgendamiento.findAll", query = "SELECT d FROM DispAgendamiento d")
    , @NamedQuery(name = "DispAgendamiento.findAllAtendidoMedicoOperador", query = "SELECT d FROM DispAgendamiento d where d.estado = 'AM' AND (d.fecha BETWEEN :fechaIni AND :fechaFin) AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fecha DESC")
    , @NamedQuery(name = "DispAgendamiento.findAllByEmpresa", query = "SELECT d FROM DispAgendamiento d where d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fecha ASC")
    , @NamedQuery(name = "DispAgendamiento.findByIdMedicoPersonal", query = "SELECT d FROM DispAgendamiento d WHERE d.idMedicoPersonal.idMedicoPersonal = :idMedicoPersonal")
    , @NamedQuery(name = "DispAgendamiento.findByIdCliente", query = "SELECT d FROM DispAgendamiento d WHERE d.idCliente.idCliente = :idCliente")
    , @NamedQuery(name = "DispAgendamiento.findByIdEspecialidad", query = "SELECT d FROM DispAgendamiento d WHERE d.idEspecialidad.idEspecialidad = :idEspecialidad")
    , @NamedQuery(name = "DispAgendamiento.findByClienteEspecialidad", query = "SELECT d FROM DispAgendamiento d WHERE d.idEspecialidad.idEspecialidad = :idEspecialidad AND d.idCliente.idCliente = :idCliente")
    , @NamedQuery(name = "DispAgendamiento.findByEspecialidadMedico", query = "SELECT d FROM DispAgendamiento d WHERE d.estado IN ('C','D','L') AND  d.idEspecialidad.idEspecialidad = :idEspecialidad AND d.idMedicoPersonal.idMedicoPersonal = :idMedicoPersonal AND d.fecha = :fecha")
    , @NamedQuery(name = "DispAgendamiento.findAllLlamados", query = "SELECT d FROM DispAgendamiento d where d.estado = 'L' AND (d.fecha BETWEEN :fechaIni AND :fechaFin) AND d.fechaAtendido = null AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fechaLlamada ASC")
    , @NamedQuery(name = "DispAgendamiento.CountTurno", query = "SELECT MAX(d.turno) FROM DispAgendamiento d WHERE d.estado = 'C' AND d.idEspecialidad.idEspecialidad = :especialidad AND (d.fecha >= :fechaIni AND d.fecha < :fechaFin) AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispAgendamiento.findByFechaByIDs", query = "SELECT d FROM DispAgendamiento d WHERE d.fecha = :fecha AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispAgendamiento.findAllFinalizado", query = "SELECT d FROM DispAgendamiento d where d.estado IN ('FT','FM','A') AND (d.fecha BETWEEN :fechaIni AND :fechaFin) AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fecha ASC")
    , @NamedQuery(name = "DispAgendamiento.findAllCalendario", query = "SELECT d FROM DispAgendamiento d where d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector AND (d.estado = 'C' OR (d.estado != 'CA' AND d.pagado IS NULL)) order by d.fecha ASC")
    , @NamedQuery(name = "DispAgendamiento.findAllAtendidoTriaje", query = "SELECT d FROM DispAgendamiento d where d.estado IN ('AT','DM','LM','RT') AND d.fechaAtendido != null AND (d.fecha BETWEEN :fechaIni AND :fechaFin) AND d.idMedicoPersonal.idMedicoPersonal = :medico AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fecha ASC")
    , @NamedQuery(name = "DispAgendamiento.findAllCreated", query = "SELECT d FROM DispAgendamiento d where d.estado IN ('C','L','D','RT') AND (d.fecha BETWEEN :fechaIni AND :fechaFin) AND d.fechaAtendido = null AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fecha ASC")
    , @NamedQuery(name = "DispAgendamiento.findAllMedico", query = "SELECT d FROM DispAgendamiento d where d.estado IN ('C','L','LM','DM','D','RT') AND (d.fecha BETWEEN :fechaIni AND :fechaFin) AND d.idMedicoPersonal.idMedicoPersonal = :medico AND d.fechaAtendido = null AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fecha ASC")
    , @NamedQuery(name = "DispAgendamiento.findAllAtendidoMedico", query = "SELECT d FROM DispAgendamiento d where d.estado IN ('AM') AND (d.fecha BETWEEN :fechaIni AND :fechaFin) AND d.idMedicoPersonal.idMedicoPersonal = :medico AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fecha DESC")
    , @NamedQuery(name = "DispAgendamiento.findAllTriaje", query = "SELECT d FROM DispAgendamiento d where d.estado IN ('C','L','D','RT') AND (d.fecha BETWEEN :fechaIni AND :fechaFin) AND d.fechaAtendido = null AND d.idMedicoPersonal.idMedicoPersonal != 1 AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector order by d.fecha ASC")
    , @NamedQuery(name = "DispAgendamiento.findByIdAgendamiento", query = "SELECT d FROM DispAgendamiento d WHERE d.idAgendamiento = :idAgendamiento")
    , @NamedQuery(name = "DispAgendamiento.findByFecha", query = "SELECT d FROM DispAgendamiento d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "DispAgendamiento.findByFechaLlamada", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaLlamada = :fechaLlamada")
    , @NamedQuery(name = "DispAgendamiento.findByFechaInicioAtencion", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaInicioAtencion = :fechaInicioAtencion")
    , @NamedQuery(name = "DispAgendamiento.findByFechaAtendido", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaAtendido = :fechaAtendido")
    , @NamedQuery(name = "DispAgendamiento.findByFechaFinalizado", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaFinalizado = :fechaFinalizado")
    , @NamedQuery(name = "DispAgendamiento.findByFechaRecuperacion", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaRecuperacion = :fechaRecuperacion")
    , @NamedQuery(name = "DispAgendamiento.findByFechaInicioMedico", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaInicioMedico = :fechaInicioMedico")
    , @NamedQuery(name = "DispAgendamiento.findByFechaLlamarMedico", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaLlamarMedico = :fechaLlamarMedico")
    , @NamedQuery(name = "DispAgendamiento.findByFechaAtendidoMedico", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaAtendidoMedico = :fechaAtendidoMedico")
    , @NamedQuery(name = "DispAgendamiento.findByCosto", query = "SELECT d FROM DispAgendamiento d WHERE d.costo = :costo")
    , @NamedQuery(name = "DispAgendamiento.findByTurno", query = "SELECT d FROM DispAgendamiento d WHERE d.turno = :turno")
    , @NamedQuery(name = "DispAgendamiento.findByIntentos", query = "SELECT d FROM DispAgendamiento d WHERE d.intentos = :intentos")
    , @NamedQuery(name = "DispAgendamiento.findByEstado", query = "SELECT d FROM DispAgendamiento d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispAgendamiento.findByUsuarioIngreso", query = "SELECT d FROM DispAgendamiento d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispAgendamiento.findByFechaIngreso", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispAgendamiento.findByUsuarioModificacion", query = "SELECT d FROM DispAgendamiento d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispAgendamiento.findByFechaModificacion", query = "SELECT d FROM DispAgendamiento d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispAgendamiento implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "en_tiempo_pasado")
    private String enTiempoPasado;

    @Size(max = 2)
    @Column(name = "pagado")
    private String pagado;

    @Column(name = "accion")
    private Boolean accion;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "idAgendamiento")
    private DispResultado dispResultado;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idAgendamiento")
    private Collection<DispReceta> dispRecetaCollection;

    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    @ManyToOne(optional = false)
    private DispServicio idServicio;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idAgendamiento")
    private Collection<DispResultado> dispResultadoCollection;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_agendamiento")
    private Integer idAgendamiento;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "fecha_llamada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlamada;

    @Column(name = "fecha_inicio_atencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioAtencion;

    @Column(name = "fecha_atendido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtendido;

    @Column(name = "fecha_finalizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizado;

    @Column(name = "fecha_recuperacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecuperacion;

    @Column(name = "fecha_inicio_medico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioMedico;

    @Column(name = "fecha_llamar_medico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlamarMedico;

    @Column(name = "fecha_atendido_medico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtendidoMedico;

    @Column(name = "costo")
    private BigDecimal costo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "turno")
    private int turno;

    @Basic(optional = false)
    @NotNull
    @Column(name = "intentos")
    private int intentos;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
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

    @JoinColumn(name = "id_medico_personal", referencedColumnName = "id_medico_personal")
    @ManyToOne(optional = false)
    private DispMedicoPersonal idMedicoPersonal;

    public DispAgendamiento() {
    }

    public DispAgendamiento(Integer idAgendamiento) {
        this.idAgendamiento = idAgendamiento;
    }

    public DispAgendamiento(Integer idAgendamiento, int turno, int intentos, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idAgendamiento = idAgendamiento;
        this.turno = turno;
        this.intentos = intentos;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdAgendamiento() {
        return this.idAgendamiento;
    }

    public void setIdAgendamiento(Integer idAgendamiento) {
        this.idAgendamiento = idAgendamiento;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaLlamada() {
        return this.fechaLlamada;
    }

    public void setFechaLlamada(Date fechaLlamada) {
        this.fechaLlamada = fechaLlamada;
    }

    public Date getFechaInicioAtencion() {
        return this.fechaInicioAtencion;
    }

    public void setFechaInicioAtencion(Date fechaInicioAtencion) {
        this.fechaInicioAtencion = fechaInicioAtencion;
    }

    public Date getFechaAtendido() {
        return this.fechaAtendido;
    }

    public void setFechaAtendido(Date fechaAtendido) {
        this.fechaAtendido = fechaAtendido;
    }

    public Date getFechaFinalizado() {
        return this.fechaFinalizado;
    }

    public void setFechaFinalizado(Date fechaFinalizado) {
        this.fechaFinalizado = fechaFinalizado;
    }

    public Date getFechaRecuperacion() {
        return this.fechaRecuperacion;
    }

    public void setFechaRecuperacion(Date fechaRecuperacion) {
        this.fechaRecuperacion = fechaRecuperacion;
    }

    public Date getFechaInicioMedico() {
        return this.fechaInicioMedico;
    }

    public void setFechaInicioMedico(Date fechaInicioMedico) {
        this.fechaInicioMedico = fechaInicioMedico;
    }

    public Date getFechaLlamarMedico() {
        return this.fechaLlamarMedico;
    }

    public void setFechaLlamarMedico(Date fechaLlamarMedico) {
        this.fechaLlamarMedico = fechaLlamarMedico;
    }

    public Date getFechaAtendidoMedico() {
        return this.fechaAtendidoMedico;
    }

    public void setFechaAtendidoMedico(Date fechaAtendidoMedico) {
        this.fechaAtendidoMedico = fechaAtendidoMedico;
    }

    public BigDecimal getCosto() {
        return this.costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public int getTurno() {
        return this.turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int getIntentos() {
        return this.intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
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

    public DispMedicoPersonal getIdMedicoPersonal() {
        return this.idMedicoPersonal;
    }

    public void setIdMedicoPersonal(DispMedicoPersonal idMedicoPersonal) {
        this.idMedicoPersonal = idMedicoPersonal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idAgendamiento != null) ? this.idAgendamiento.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispAgendamiento)) {
            return false;
        }
        DispAgendamiento other = (DispAgendamiento) object;
        if ((this.idAgendamiento == null && other.idAgendamiento != null) || (this.idAgendamiento != null && !this.idAgendamiento.equals(other.idAgendamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispAgendamiento[ idAgendamiento=" + this.idAgendamiento + " ]";
    }

    @XmlTransient
    public Collection<DispResultado> getDispResultadoCollection() {
        return this.dispResultadoCollection;
    }

    public void setDispResultadoCollection(Collection<DispResultado> dispResultadoCollection) {
        this.dispResultadoCollection = dispResultadoCollection;
    }

    public DispServicio getIdServicio() {
        return this.idServicio;
    }

    public void setIdServicio(DispServicio idServicio) {
        this.idServicio = idServicio;
    }

    @XmlTransient
    public Collection<DispReceta> getDispRecetaCollection() {
        return this.dispRecetaCollection;
    }

    public void setDispRecetaCollection(Collection<DispReceta> dispRecetaCollection) {
        this.dispRecetaCollection = dispRecetaCollection;
    }

    public DispResultado getDispResultado() {
        return this.dispResultado;
    }

    public void setDispResultado(DispResultado dispResultado) {
        this.dispResultado = dispResultado;
    }

    public Boolean getAccion() {
        return this.accion;
    }

    public void setAccion(Boolean accion) {
        this.accion = accion;
    }

    public String getPagado() {
        return this.pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }

    public String getEnTiempoPasado() {
        return enTiempoPasado;
    }

    public void setEnTiempoPasado(String enTiempoPasado) {
        this.enTiempoPasado = enTiempoPasado;
    }
}
