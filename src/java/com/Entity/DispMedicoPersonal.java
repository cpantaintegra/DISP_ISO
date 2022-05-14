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
@Table(name = "disp_medico_personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispMedicoPersonal.findAll", query = "SELECT d FROM DispMedicoPersonal d")
    , @NamedQuery(name = "DispMedicoPersonal.findByIdUsuario", query = "SELECT d FROM DispMedicoPersonal d WHERE d.idUsuario.idUsuarios = :idUsuario")
    , @NamedQuery(name = "DispMedicoPersonal.findByNames", query = "SELECT d FROM DispMedicoPersonal d where d.apaterno = :apaterno AND d.amaterno = :amaterno AND d.nombre = :nombre AND d.estado = 'A' AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispMedicoPersonal.findAllActivos", query = "SELECT d FROM DispMedicoPersonal d where d.estado = 'A' AND d.idMedicoPersonal != 1 AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispMedicoPersonal.findByIdMedicoPersonal", query = "SELECT d FROM DispMedicoPersonal d WHERE d.idMedicoPersonal = :idMedicoPersonal")
    , @NamedQuery(name = "DispMedicoPersonal.findByTipoDocumento", query = "SELECT d FROM DispMedicoPersonal d WHERE d.tipoDocumento = :tipoDocumento")
    , @NamedQuery(name = "DispMedicoPersonal.findByNumDocumento", query = "SELECT d FROM DispMedicoPersonal d WHERE d.numDocumento = :numDocumento")
    , @NamedQuery(name = "DispMedicoPersonal.findByApaterno", query = "SELECT d FROM DispMedicoPersonal d WHERE d.apaterno = :apaterno")
    , @NamedQuery(name = "DispMedicoPersonal.findByAmaterno", query = "SELECT d FROM DispMedicoPersonal d WHERE d.amaterno = :amaterno")
    , @NamedQuery(name = "DispMedicoPersonal.findByNombre", query = "SELECT d FROM DispMedicoPersonal d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispMedicoPersonal.findByFechaNacimiento", query = "SELECT d FROM DispMedicoPersonal d WHERE d.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "DispMedicoPersonal.findBySexo", query = "SELECT d FROM DispMedicoPersonal d WHERE d.sexo = :sexo")
    , @NamedQuery(name = "DispMedicoPersonal.findByEstadoCivil", query = "SELECT d FROM DispMedicoPersonal d WHERE d.estadoCivil = :estadoCivil")
    , @NamedQuery(name = "DispMedicoPersonal.findByTelefono", query = "SELECT d FROM DispMedicoPersonal d WHERE d.telefono = :telefono")
    , @NamedQuery(name = "DispMedicoPersonal.findByCelular", query = "SELECT d FROM DispMedicoPersonal d WHERE d.celular = :celular")
    , @NamedQuery(name = "DispMedicoPersonal.findByOcupacion", query = "SELECT d FROM DispMedicoPersonal d WHERE d.ocupacion = :ocupacion")
    , @NamedQuery(name = "DispMedicoPersonal.findByDireccion", query = "SELECT d FROM DispMedicoPersonal d WHERE d.direccion = :direccion")
    , @NamedQuery(name = "DispMedicoPersonal.findByEmail", query = "SELECT d FROM DispMedicoPersonal d WHERE d.email = :email")
    , @NamedQuery(name = "DispMedicoPersonal.findByEstado", query = "SELECT d FROM DispMedicoPersonal d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispMedicoPersonal.findByUsuarioIngreso", query = "SELECT d FROM DispMedicoPersonal d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispMedicoPersonal.findByFechaIngreso", query = "SELECT d FROM DispMedicoPersonal d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispMedicoPersonal.findByUsuarioModificacion", query = "SELECT d FROM DispMedicoPersonal d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispMedicoPersonal.findByFechaModificacion", query = "SELECT d FROM DispMedicoPersonal d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispMedicoPersonal implements Serializable {

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idMedicoPersonal")
    private Collection<DispSolicitudExamen> dispSolicitudExamenCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idMedicoPersonal")
    private Collection<DispAgendamiento> dispAgendamientoCollection;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_medico_personal")
    private Integer idMedicoPersonal;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "num_documento")
    private String numDocumento;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "apaterno")
    private String apaterno;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "amaterno")
    private String amaterno;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "sexo")
    private String sexo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estado_civil")
    private String estadoCivil;

    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;

    @Size(max = 12)
    @Column(name = "celular")
    private String celular;

    @Size(max = 30)
    @Column(name = "ocupacion")
    private String ocupacion;

    @Size(max = 70)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 50)
    @Column(name = "email")
    private String email;

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

    @JoinColumn(name = "id_consultorio", referencedColumnName = "id_consultorio")
    @ManyToOne(optional = false)
    private DispConsultorio idConsultorio;

    @JoinColumn(name = "id_especialidad", referencedColumnName = "id_especialidad")
    @ManyToOne(optional = false)
    private DispEspecialidad idEspecialidad;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuarios")
    @ManyToOne(optional = false)
    private IsUsuarios idUsuario;

    public DispMedicoPersonal() {
    }

    public DispMedicoPersonal(Integer idMedicoPersonal) {
        this.idMedicoPersonal = idMedicoPersonal;
    }

    public DispMedicoPersonal(Integer idMedicoPersonal, String tipoDocumento, String numDocumento, String apaterno, String amaterno, String nombre, String sexo, String estadoCivil, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idMedicoPersonal = idMedicoPersonal;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.nombre = nombre;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdMedicoPersonal() {
        return this.idMedicoPersonal;
    }

    public void setIdMedicoPersonal(Integer idMedicoPersonal) {
        this.idMedicoPersonal = idMedicoPersonal;
    }

    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocumento() {
        return this.numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getApaterno() {
        return this.apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return this.amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getOcupacion() {
        return this.ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public DispConsultorio getIdConsultorio() {
        return this.idConsultorio;
    }

    public void setIdConsultorio(DispConsultorio idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public DispEspecialidad getIdEspecialidad() {
        return this.idEspecialidad;
    }

    public void setIdEspecialidad(DispEspecialidad idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public IsUsuarios getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(IsUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idMedicoPersonal != null) ? this.idMedicoPersonal.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispMedicoPersonal)) {
            return false;
        }
        DispMedicoPersonal other = (DispMedicoPersonal) object;
        if ((this.idMedicoPersonal == null && other.idMedicoPersonal != null) || (this.idMedicoPersonal != null && !this.idMedicoPersonal.equals(other.idMedicoPersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispMedicoPersonal[ idMedicoPersonal=" + this.idMedicoPersonal + " ]";
    }

    @XmlTransient
    public Collection<DispAgendamiento> getDispAgendamientoCollection() {
        return this.dispAgendamientoCollection;
    }

    public void setDispAgendamientoCollection(Collection<DispAgendamiento> dispAgendamientoCollection) {
        this.dispAgendamientoCollection = dispAgendamientoCollection;
    }

    @XmlTransient
    public Collection<DispSolicitudExamen> getDispSolicitudExamenCollection() {
        return this.dispSolicitudExamenCollection;
    }

    public void setDispSolicitudExamenCollection(Collection<DispSolicitudExamen> dispSolicitudExamenCollection) {
        this.dispSolicitudExamenCollection = dispSolicitudExamenCollection;
    }
}
