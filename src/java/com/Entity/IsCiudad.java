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
@Table(name = "is_ciudad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IsCiudad.findAll", query = "SELECT i FROM IsCiudad i")
    , @NamedQuery(name = "IsCiudad.findAllActivos", query = "SELECT i FROM IsCiudad i where i.estado = 'A' ORDER BY i.nombre")
    , @NamedQuery(name = "IsCiudad.findByIdCiudad", query = "SELECT i FROM IsCiudad i WHERE i.idCiudad = :idCiudad")
    , @NamedQuery(name = "IsCiudad.findByNombre", query = "SELECT i FROM IsCiudad i WHERE i.nombre = :nombre")
    , @NamedQuery(name = "IsCiudad.findByDetalle", query = "SELECT i FROM IsCiudad i WHERE i.detalle = :detalle")
    , @NamedQuery(name = "IsCiudad.findByEstado", query = "SELECT i FROM IsCiudad i WHERE i.estado = :estado")
    , @NamedQuery(name = "IsCiudad.findByUsuarioIngreso", query = "SELECT i FROM IsCiudad i WHERE i.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "IsCiudad.findByFechaIngreso", query = "SELECT i FROM IsCiudad i WHERE i.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "IsCiudad.findByUsuarioModificacion", query = "SELECT i FROM IsCiudad i WHERE i.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "IsCiudad.findByFechaModificacion", query = "SELECT i FROM IsCiudad i WHERE i.fechaModificacion = :fechaModificacion")})
public class IsCiudad implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCiudad")
    private Collection<DispEstudiosMedicos> dispEstudiosMedicosCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispMedicamento> dispMedicamentoCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispDetalleReceta> dispDetalleRecetaCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispAntecedentes> dispAntecedentesCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispUnidadMedica> dispUnidadMedicaCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispResultadoDetalle> dispResultadoDetalleCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispExamen> dispExamenCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispReceta> dispRecetaCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispResultadoExamen> dispResultadoExamenCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispSolicitudExamen> dispSolicitudExamenCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispResultado> dispResultadoCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispDiagnostico> dispDiagnosticoCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispAgendamiento> dispAgendamientoCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispFactura> dispFacturaCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispCliente> dispClienteCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispServicio> dispServicioCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispPrecio> dispPrecioCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispOrigen> dispOrigenCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<IsUsuarios> isUsuariosCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispConsultorio> dispConsultorioCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<IsArea> isAreaCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<IsEmpresa> isEmpresaCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<IsRoles> isRolesCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispEspecialidad> dispEspecialidadCollection;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "idCiudad")
    private Collection<DispMedicoPersonal> dispMedicoPersonalCollection;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ciudad")
    private Integer idCiudad;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 1000)
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

    public IsCiudad() {
    }

    public IsCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public IsCiudad(Integer idCiudad, String nombre, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdCiudad() {
        return this.idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idCiudad != null) ? this.idCiudad.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IsCiudad)) {
            return false;
        }
        IsCiudad other = (IsCiudad) object;
        if ((this.idCiudad == null && other.idCiudad != null) || (this.idCiudad != null && !this.idCiudad.equals(other.idCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.IsCiudad[ idCiudad=" + this.idCiudad + " ]";
    }

    @XmlTransient
    public Collection<IsUsuarios> getIsUsuariosCollection() {
        return this.isUsuariosCollection;
    }

    public void setIsUsuariosCollection(Collection<IsUsuarios> isUsuariosCollection) {
        this.isUsuariosCollection = isUsuariosCollection;
    }

    @XmlTransient
    public Collection<DispConsultorio> getDispConsultorioCollection() {
        return this.dispConsultorioCollection;
    }

    public void setDispConsultorioCollection(Collection<DispConsultorio> dispConsultorioCollection) {
        this.dispConsultorioCollection = dispConsultorioCollection;
    }

    @XmlTransient
    public Collection<IsArea> getIsAreaCollection() {
        return this.isAreaCollection;
    }

    public void setIsAreaCollection(Collection<IsArea> isAreaCollection) {
        this.isAreaCollection = isAreaCollection;
    }

    @XmlTransient
    public Collection<IsEmpresa> getIsEmpresaCollection() {
        return this.isEmpresaCollection;
    }

    public void setIsEmpresaCollection(Collection<IsEmpresa> isEmpresaCollection) {
        this.isEmpresaCollection = isEmpresaCollection;
    }

    @XmlTransient
    public Collection<IsRoles> getIsRolesCollection() {
        return this.isRolesCollection;
    }

    public void setIsRolesCollection(Collection<IsRoles> isRolesCollection) {
        this.isRolesCollection = isRolesCollection;
    }

    @XmlTransient
    public Collection<DispEspecialidad> getDispEspecialidadCollection() {
        return this.dispEspecialidadCollection;
    }

    public void setDispEspecialidadCollection(Collection<DispEspecialidad> dispEspecialidadCollection) {
        this.dispEspecialidadCollection = dispEspecialidadCollection;
    }

    @XmlTransient
    public Collection<DispMedicoPersonal> getDispMedicoPersonalCollection() {
        return this.dispMedicoPersonalCollection;
    }

    public void setDispMedicoPersonalCollection(Collection<DispMedicoPersonal> dispMedicoPersonalCollection) {
        this.dispMedicoPersonalCollection = dispMedicoPersonalCollection;
    }

    @XmlTransient
    public Collection<DispPrecio> getDispPrecioCollection() {
        return this.dispPrecioCollection;
    }

    public void setDispPrecioCollection(Collection<DispPrecio> dispPrecioCollection) {
        this.dispPrecioCollection = dispPrecioCollection;
    }

    @XmlTransient
    public Collection<DispOrigen> getDispOrigenCollection() {
        return this.dispOrigenCollection;
    }

    public void setDispOrigenCollection(Collection<DispOrigen> dispOrigenCollection) {
        this.dispOrigenCollection = dispOrigenCollection;
    }

    @XmlTransient
    public Collection<DispServicio> getDispServicioCollection() {
        return this.dispServicioCollection;
    }

    public void setDispServicioCollection(Collection<DispServicio> dispServicioCollection) {
        this.dispServicioCollection = dispServicioCollection;
    }

    @XmlTransient
    public Collection<DispCliente> getDispClienteCollection() {
        return this.dispClienteCollection;
    }

    public void setDispClienteCollection(Collection<DispCliente> dispClienteCollection) {
        this.dispClienteCollection = dispClienteCollection;
    }

    @XmlTransient
    public Collection<DispFactura> getDispFacturaCollection() {
        return this.dispFacturaCollection;
    }

    public void setDispFacturaCollection(Collection<DispFactura> dispFacturaCollection) {
        this.dispFacturaCollection = dispFacturaCollection;
    }

    @XmlTransient
    public Collection<DispAgendamiento> getDispAgendamientoCollection() {
        return this.dispAgendamientoCollection;
    }

    public void setDispAgendamientoCollection(Collection<DispAgendamiento> dispAgendamientoCollection) {
        this.dispAgendamientoCollection = dispAgendamientoCollection;
    }

    @XmlTransient
    public Collection<DispDiagnostico> getDispDiagnosticoCollection() {
        return this.dispDiagnosticoCollection;
    }

    public void setDispDiagnosticoCollection(Collection<DispDiagnostico> dispDiagnosticoCollection) {
        this.dispDiagnosticoCollection = dispDiagnosticoCollection;
    }

    @XmlTransient
    public Collection<DispUnidadMedica> getDispUnidadMedicaCollection() {
        return this.dispUnidadMedicaCollection;
    }

    public void setDispUnidadMedicaCollection(Collection<DispUnidadMedica> dispUnidadMedicaCollection) {
        this.dispUnidadMedicaCollection = dispUnidadMedicaCollection;
    }

    @XmlTransient
    public Collection<DispResultadoDetalle> getDispResultadoDetalleCollection() {
        return this.dispResultadoDetalleCollection;
    }

    public void setDispResultadoDetalleCollection(Collection<DispResultadoDetalle> dispResultadoDetalleCollection) {
        this.dispResultadoDetalleCollection = dispResultadoDetalleCollection;
    }

    @XmlTransient
    public Collection<DispExamen> getDispExamenCollection() {
        return this.dispExamenCollection;
    }

    public void setDispExamenCollection(Collection<DispExamen> dispExamenCollection) {
        this.dispExamenCollection = dispExamenCollection;
    }

    @XmlTransient
    public Collection<DispReceta> getDispRecetaCollection() {
        return this.dispRecetaCollection;
    }

    public void setDispRecetaCollection(Collection<DispReceta> dispRecetaCollection) {
        this.dispRecetaCollection = dispRecetaCollection;
    }

    @XmlTransient
    public Collection<DispResultadoExamen> getDispResultadoExamenCollection() {
        return this.dispResultadoExamenCollection;
    }

    public void setDispResultadoExamenCollection(Collection<DispResultadoExamen> dispResultadoExamenCollection) {
        this.dispResultadoExamenCollection = dispResultadoExamenCollection;
    }

    @XmlTransient
    public Collection<DispSolicitudExamen> getDispSolicitudExamenCollection() {
        return this.dispSolicitudExamenCollection;
    }

    public void setDispSolicitudExamenCollection(Collection<DispSolicitudExamen> dispSolicitudExamenCollection) {
        this.dispSolicitudExamenCollection = dispSolicitudExamenCollection;
    }

    @XmlTransient
    public Collection<DispResultado> getDispResultadoCollection() {
        return this.dispResultadoCollection;
    }

    public void setDispResultadoCollection(Collection<DispResultado> dispResultadoCollection) {
        this.dispResultadoCollection = dispResultadoCollection;
    }

    @XmlTransient
    public Collection<DispAntecedentes> getDispAntecedentesCollection() {
        return this.dispAntecedentesCollection;
    }

    public void setDispAntecedentesCollection(Collection<DispAntecedentes> dispAntecedentesCollection) {
        this.dispAntecedentesCollection = dispAntecedentesCollection;
    }

    @XmlTransient
    public Collection<DispMedicamento> getDispMedicamentoCollection() {
        return this.dispMedicamentoCollection;
    }

    public void setDispMedicamentoCollection(Collection<DispMedicamento> dispMedicamentoCollection) {
        this.dispMedicamentoCollection = dispMedicamentoCollection;
    }

    @XmlTransient
    public Collection<DispDetalleReceta> getDispDetalleRecetaCollection() {
        return this.dispDetalleRecetaCollection;
    }

    public void setDispDetalleRecetaCollection(Collection<DispDetalleReceta> dispDetalleRecetaCollection) {
        this.dispDetalleRecetaCollection = dispDetalleRecetaCollection;
    }

    @XmlTransient
    public Collection<DispEstudiosMedicos> getDispEstudiosMedicosCollection() {
        return dispEstudiosMedicosCollection;
    }

    public void setDispEstudiosMedicosCollection(Collection<DispEstudiosMedicos> dispEstudiosMedicosCollection) {
        this.dispEstudiosMedicosCollection = dispEstudiosMedicosCollection;
    }
}
