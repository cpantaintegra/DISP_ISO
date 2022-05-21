/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author USER
 */
@Entity
@Table(name = "disp_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispCliente.findAll", query = "SELECT d FROM DispCliente d")
    , @NamedQuery(name = "DispCliente.findByNumDocumentoByIDs", query = "SELECT d FROM DispCliente d where d.numDocumento = :numDocumento AND d.estado = 'A' AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispCliente.findByNames", query = "SELECT d FROM DispCliente d where d.apaterno = :apaterno AND d.amaterno = :amaterno AND d.nombre LIKE :nombre AND d.estado = 'A' AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispCliente.findAllActivos", query = "SELECT d FROM DispCliente d where d.estado = 'A' And d.idCliente !=1 AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispCliente.findByIdCliente", query = "SELECT d FROM DispCliente d WHERE d.idCliente = :idCliente")
    , @NamedQuery(name = "DispCliente.findByTipoDocumento", query = "SELECT d FROM DispCliente d WHERE d.tipoDocumento = :tipoDocumento")
    , @NamedQuery(name = "DispCliente.findByNumDocumento", query = "SELECT d FROM DispCliente d WHERE d.numDocumento = :numDocumento")
    , @NamedQuery(name = "DispCliente.findByApaterno", query = "SELECT d FROM DispCliente d WHERE d.apaterno = :apaterno")
    , @NamedQuery(name = "DispCliente.findByAmaterno", query = "SELECT d FROM DispCliente d WHERE d.amaterno = :amaterno")
    , @NamedQuery(name = "DispCliente.findByNombre", query = "SELECT d FROM DispCliente d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DispCliente.findByFechaNacimiento", query = "SELECT d FROM DispCliente d WHERE d.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "DispCliente.findBySexo", query = "SELECT d FROM DispCliente d WHERE d.sexo = :sexo")
    , @NamedQuery(name = "DispCliente.findByEstadoCivil", query = "SELECT d FROM DispCliente d WHERE d.estadoCivil = :estadoCivil")
    , @NamedQuery(name = "DispCliente.findByTelefono", query = "SELECT d FROM DispCliente d WHERE d.telefono = :telefono")
    , @NamedQuery(name = "DispCliente.findByCelular", query = "SELECT d FROM DispCliente d WHERE d.celular = :celular")
    , @NamedQuery(name = "DispCliente.findByOcupacion", query = "SELECT d FROM DispCliente d WHERE d.ocupacion = :ocupacion")
    , @NamedQuery(name = "DispCliente.findByPersonaResponsable", query = "SELECT d FROM DispCliente d WHERE d.personaResponsable = :personaResponsable")
    , @NamedQuery(name = "DispCliente.findByDireccion", query = "SELECT d FROM DispCliente d WHERE d.direccion = :direccion")
    , @NamedQuery(name = "DispCliente.findByEmail", query = "SELECT d FROM DispCliente d WHERE d.email = :email")
    , @NamedQuery(name = "DispCliente.findByEstado", query = "SELECT d FROM DispCliente d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispCliente.findByUsuarioIngreso", query = "SELECT d FROM DispCliente d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispCliente.findByFechaIngreso", query = "SELECT d FROM DispCliente d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispCliente.findByUsuarioModificacion", query = "SELECT d FROM DispCliente d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispCliente.findByFechaModificacion", query = "SELECT d FROM DispCliente d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispCliente implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private Collection<DispAgendamiento> dispAgendamientoCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
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
    @Temporal(TemporalType.TIMESTAMP)
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
    @Size(max = 30)
    @Column(name = "persona_responsable")
    private String personaResponsable;
    @Size(max = 70)
    @Column(name = "direccion")
    private String direccion;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
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
    @JoinColumn(name = "id_origen", referencedColumnName = "id_origen")
    @ManyToOne(optional = false)
    private DispOrigen idOrigen;
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;
    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispCliente() {
    }

    public DispCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public DispCliente(Integer idCliente, String tipoDocumento, String numDocumento, String apaterno, String amaterno, String nombre, String sexo, String estadoCivil, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idCliente = idCliente;
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

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getPersonaResponsable() {
        return personaResponsable;
    }

    public void setPersonaResponsable(String personaResponsable) {
        this.personaResponsable = personaResponsable;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public DispOrigen getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(DispOrigen idOrigen) {
        this.idOrigen = idOrigen;
    }

    public IsCiudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(IsCiudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public IsEmpresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(IsEmpresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public IsSector getIdSector() {
        return idSector;
    }

    public void setIdSector(IsSector idSector) {
        this.idSector = idSector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispCliente)) {
            return false;
        }
        DispCliente other = (DispCliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispCliente[ idCliente=" + idCliente + " ]";
    }

    @XmlTransient
    public Collection<DispAgendamiento> getDispAgendamientoCollection() {
        return dispAgendamientoCollection;
    }

    public void setDispAgendamientoCollection(Collection<DispAgendamiento> dispAgendamientoCollection) {
        this.dispAgendamientoCollection = dispAgendamientoCollection;
    }
    
}
