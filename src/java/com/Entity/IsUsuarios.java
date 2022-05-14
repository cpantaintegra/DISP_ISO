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
@Table(name = "is_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsUsuarios.findAll", query = "SELECT i FROM IsUsuarios i")
    , @NamedQuery(name = "IsUsuarios.findAllActivos", query = "SELECT i FROM IsUsuarios i where i.estado = 'A' AND i.idEmpresa.idEmpresa = :empresa AND i.idCiudad.idCiudad = :ciudad AND i.idSector.idSector = :sector")
    , @NamedQuery(name = "IsUsuarios.findByUserClave", query = "SELECT i FROM IsUsuarios i WHERE i.usuario = :usuario and i.clave = :clave and i.estado = 'A'")
    , @NamedQuery(name = "IsUsuarios.findByUserCorreo", query = "SELECT i FROM IsUsuarios i WHERE i.usuario = :usuario and i.correo = :correo and i.estado = 'A'")
    , @NamedQuery(name = "IsUsuarios.findByIdUsuarios", query = "SELECT i FROM IsUsuarios i WHERE i.idUsuarios = :idUsuarios")
    , @NamedQuery(name = "IsUsuarios.findByUsuario", query = "SELECT i FROM IsUsuarios i WHERE i.usuario = :usuario AND i.idEmpresa.idEmpresa = :empresa AND i.idCiudad.idCiudad = :ciudad AND i.idSector.idSector = :sector")
    , @NamedQuery(name = "IsUsuarios.findByUser", query = "SELECT i FROM IsUsuarios i WHERE i.usuario = :usuario")
    , @NamedQuery(name = "IsUsuarios.findByClave", query = "SELECT i FROM IsUsuarios i WHERE i.clave = :clave")
    , @NamedQuery(name = "IsUsuarios.findByTipoPersona", query = "SELECT i FROM IsUsuarios i WHERE i.tipoPersona = :tipoPersona")
    , @NamedQuery(name = "IsUsuarios.findByCedula", query = "SELECT i FROM IsUsuarios i WHERE i.cedula = :cedula")
    , @NamedQuery(name = "IsUsuarios.findByNombres", query = "SELECT i FROM IsUsuarios i WHERE i.nombres = :nombres")
    , @NamedQuery(name = "IsUsuarios.findByApellidos", query = "SELECT i FROM IsUsuarios i WHERE i.apellidos = :apellidos")
    , @NamedQuery(name = "IsUsuarios.findByDireccionDom", query = "SELECT i FROM IsUsuarios i WHERE i.direccionDom = :direccionDom")
    , @NamedQuery(name = "IsUsuarios.findByTelefonoCasa", query = "SELECT i FROM IsUsuarios i WHERE i.telefonoCasa = :telefonoCasa")
    , @NamedQuery(name = "IsUsuarios.findByTelefonoCel", query = "SELECT i FROM IsUsuarios i WHERE i.telefonoCel = :telefonoCel")
    , @NamedQuery(name = "IsUsuarios.findByCorreo", query = "SELECT i FROM IsUsuarios i WHERE i.correo = :correo")
    , @NamedQuery(name = "IsUsuarios.findByLogo", query = "SELECT i FROM IsUsuarios i WHERE i.logo = :logo")
    , @NamedQuery(name = "IsUsuarios.findByLogoRuta", query = "SELECT i FROM IsUsuarios i WHERE i.logoRuta = :logoRuta")
    , @NamedQuery(name = "IsUsuarios.findByDetalle", query = "SELECT i FROM IsUsuarios i WHERE i.detalle = :detalle")
    , @NamedQuery(name = "IsUsuarios.findByIntentos", query = "SELECT i FROM IsUsuarios i WHERE i.intentos = :intentos")
    , @NamedQuery(name = "IsUsuarios.findByEstado", query = "SELECT i FROM IsUsuarios i WHERE i.estado = :estado")
    , @NamedQuery(name = "IsUsuarios.findByUsuarioIngreso", query = "SELECT i FROM IsUsuarios i WHERE i.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "IsUsuarios.findByFechaIngreso", query = "SELECT i FROM IsUsuarios i WHERE i.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "IsUsuarios.findByUsuarioModificacion", query = "SELECT i FROM IsUsuarios i WHERE i.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "IsUsuarios.findByFechaModificacion", query = "SELECT i FROM IsUsuarios i WHERE i.fechaModificacion = :fechaModificacion")})
public class IsUsuarios implements Serializable {

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idUsuario")
    private Collection<DispMedicoPersonal> dispMedicoPersonalCollection;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuarios")
    private Integer idUsuarios;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "usuario")
    private String usuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "clave")
    private String clave;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo_persona")
    private String tipoPersona;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "cedula")
    private String cedula;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombres")
    private String nombres;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "apellidos")
    private String apellidos;

    @Size(max = 2000)
    @Column(name = "direccion_dom")
    private String direccionDom;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "telefono_casa")
    private String telefonoCasa;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "telefono_cel")
    private String telefonoCel;

    @Size(max = 200)
    @Column(name = "correo")
    private String correo;

    @Size(max = 300)
    @Column(name = "logo")
    private String logo;

    @Size(max = 1000)
    @Column(name = "logo_ruta")
    private String logoRuta;

    @Size(max = 1000)
    @Column(name = "detalle")
    private String detalle;

    @Column(name = "intentos")
    private Integer intentos;

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

    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private IsArea idArea;

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_roles", referencedColumnName = "id_roles")
    @ManyToOne(optional = false)
    private IsRoles idRoles;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public IsUsuarios() {
    }

    public IsUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public IsUsuarios(Integer idUsuarios, String usuario, String clave, String tipoPersona, String cedula, String nombres, String apellidos, String telefonoCasa, String telefonoCel, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idUsuarios = idUsuarios;
        this.usuario = usuario;
        this.clave = clave;
        this.tipoPersona = tipoPersona;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefonoCasa = telefonoCasa;
        this.telefonoCel = telefonoCel;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdUsuarios() {
        return this.idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipoPersona() {
        return this.tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getCedula() {
        return this.cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccionDom() {
        return this.direccionDom;
    }

    public void setDireccionDom(String direccionDom) {
        this.direccionDom = direccionDom;
    }

    public String getTelefonoCasa() {
        return this.telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoCel() {
        return this.telefonoCel;
    }

    public void setTelefonoCel(String telefonoCel) {
        this.telefonoCel = telefonoCel;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoRuta() {
        return this.logoRuta;
    }

    public void setLogoRuta(String logoRuta) {
        this.logoRuta = logoRuta;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getIntentos() {
        return this.intentos;
    }

    public void setIntentos(Integer intentos) {
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

    public IsArea getIdArea() {
        return this.idArea;
    }

    public void setIdArea(IsArea idArea) {
        this.idArea = idArea;
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

    public IsRoles getIdRoles() {
        return this.idRoles;
    }

    public void setIdRoles(IsRoles idRoles) {
        this.idRoles = idRoles;
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
        hash += (this.idUsuarios != null) ? this.idUsuarios.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsUsuarios)) {
            return false;
        }
        IsUsuarios other = (IsUsuarios) object;
        if ((this.idUsuarios == null && other.idUsuarios != null) || (this.idUsuarios != null && !this.idUsuarios.equals(other.idUsuarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.IsUsuarios[ idUsuarios=" + this.idUsuarios + " ]";
    }

    @XmlTransient
    public Collection<DispMedicoPersonal> getDispMedicoPersonalCollection() {
        return this.dispMedicoPersonalCollection;
    }

    public void setDispMedicoPersonalCollection(Collection<DispMedicoPersonal> dispMedicoPersonalCollection) {
        this.dispMedicoPersonalCollection = dispMedicoPersonalCollection;
    }
}
