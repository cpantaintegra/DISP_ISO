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
@Table(name = "disp_antecedentes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispAntecedentes.findAll", query = "SELECT d FROM DispAntecedentes d")
    , @NamedQuery(name = "DispAntecedentes.findAllActivos", query = "SELECT d FROM DispAntecedentes d where d.estado = 'A' AND d.idEmpresa.idEmpresa = :empresa AND d.idCiudad.idCiudad = :ciudad AND d.idSector.idSector = :sector")
    , @NamedQuery(name = "DispAntecedentes.findByIdCliente", query = "SELECT d FROM DispAntecedentes d WHERE d.idCliente.idCliente = :idCliente")
    , @NamedQuery(name = "DispAntecedentes.findByIdAntecedentes", query = "SELECT d FROM DispAntecedentes d WHERE d.idAntecedentes = :idAntecedentes")
    , @NamedQuery(name = "DispAntecedentes.findByAntePersonales", query = "SELECT d FROM DispAntecedentes d WHERE d.antePersonales = :antePersonales")
    , @NamedQuery(name = "DispAntecedentes.findByAnteFamiliares", query = "SELECT d FROM DispAntecedentes d WHERE d.anteFamiliares = :anteFamiliares")
    , @NamedQuery(name = "DispAntecedentes.findByAnteOftalmologicos", query = "SELECT d FROM DispAntecedentes d WHERE d.anteOftalmologicos = :anteOftalmologicos")
    , @NamedQuery(name = "DispAntecedentes.findByInterQuirurgicas", query = "SELECT d FROM DispAntecedentes d WHERE d.interQuirurgicas = :interQuirurgicas")
    , @NamedQuery(name = "DispAntecedentes.findByAlergias", query = "SELECT d FROM DispAntecedentes d WHERE d.alergias = :alergias")
    , @NamedQuery(name = "DispAntecedentes.findByMedicamentos", query = "SELECT d FROM DispAntecedentes d WHERE d.medicamentos = :medicamentos")
    , @NamedQuery(name = "DispAntecedentes.findByTabaco", query = "SELECT d FROM DispAntecedentes d WHERE d.tabaco = :tabaco")
    , @NamedQuery(name = "DispAntecedentes.findByAlcohol", query = "SELECT d FROM DispAntecedentes d WHERE d.alcohol = :alcohol")
    , @NamedQuery(name = "DispAntecedentes.findByAnestesicos", query = "SELECT d FROM DispAntecedentes d WHERE d.anestesicos = :anestesicos")
    , @NamedQuery(name = "DispAntecedentes.findByCoagulacion", query = "SELECT d FROM DispAntecedentes d WHERE d.coagulacion = :coagulacion")
    , @NamedQuery(name = "DispAntecedentes.findByEstado", query = "SELECT d FROM DispAntecedentes d WHERE d.estado = :estado")
    , @NamedQuery(name = "DispAntecedentes.findByUsuarioIngreso", query = "SELECT d FROM DispAntecedentes d WHERE d.usuarioIngreso = :usuarioIngreso")
    , @NamedQuery(name = "DispAntecedentes.findByFechaIngreso", query = "SELECT d FROM DispAntecedentes d WHERE d.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "DispAntecedentes.findByUsuarioModificacion", query = "SELECT d FROM DispAntecedentes d WHERE d.usuarioModificacion = :usuarioModificacion")
    , @NamedQuery(name = "DispAntecedentes.findByFechaModificacion", query = "SELECT d FROM DispAntecedentes d WHERE d.fechaModificacion = :fechaModificacion")})
public class DispAntecedentes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_antecedentes")
    private Integer idAntecedentes;

    @Size(max = 10000)
    @Column(name = "ante_personales")
    private String antePersonales;

    @Size(max = 10000)
    @Column(name = "ante_familiares")
    private String anteFamiliares;

    @Size(max = 10000)
    @Column(name = "ante_oftalmologicos")
    private String anteOftalmologicos;

    @Column(name = "inter_quirurgicas")
    private Integer interQuirurgicas;

    @Size(max = 10000)
    @Column(name = "alergias")
    private String alergias;

    @Size(max = 10000)
    @Column(name = "medicamentos")
    private String medicamentos;

    @Size(max = 200)
    @Column(name = "tabaco")
    private String tabaco;

    @Size(max = 200)
    @Column(name = "alcohol")
    private String alcohol;

    @Size(max = 500)
    @Column(name = "anestesicos")
    private String anestesicos;

    @Size(max = 100)
    @Column(name = "COAGULACION")
    private String coagulacion;

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

    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private IsCiudad idCiudad;

    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private IsEmpresa idEmpresa;

    @JoinColumn(name = "id_sector", referencedColumnName = "id_sector")
    @ManyToOne(optional = false)
    private IsSector idSector;

    public DispAntecedentes() {
    }

    public DispAntecedentes(Integer idAntecedentes) {
        this.idAntecedentes = idAntecedentes;
    }

    public DispAntecedentes(Integer idAntecedentes, String estado, String usuarioIngreso, Date fechaIngreso) {
        this.idAntecedentes = idAntecedentes;
        this.estado = estado;
        this.usuarioIngreso = usuarioIngreso;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdAntecedentes() {
        return this.idAntecedentes;
    }

    public void setIdAntecedentes(Integer idAntecedentes) {
        this.idAntecedentes = idAntecedentes;
    }

    public String getAntePersonales() {
        return this.antePersonales;
    }

    public void setAntePersonales(String antePersonales) {
        this.antePersonales = antePersonales;
    }

    public String getAnteFamiliares() {
        return this.anteFamiliares;
    }

    public void setAnteFamiliares(String anteFamiliares) {
        this.anteFamiliares = anteFamiliares;
    }

    public String getAnteOftalmologicos() {
        return this.anteOftalmologicos;
    }

    public void setAnteOftalmologicos(String anteOftalmologicos) {
        this.anteOftalmologicos = anteOftalmologicos;
    }

    public Integer getInterQuirurgicas() {
        return this.interQuirurgicas;
    }

    public void setInterQuirurgicas(Integer interQuirurgicas) {
        this.interQuirurgicas = interQuirurgicas;
    }

    public String getAlergias() {
        return this.alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getMedicamentos() {
        return this.medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getTabaco() {
        return this.tabaco;
    }

    public void setTabaco(String tabaco) {
        this.tabaco = tabaco;
    }

    public String getAlcohol() {
        return this.alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getAnestesicos() {
        return this.anestesicos;
    }

    public void setAnestesicos(String anestesicos) {
        this.anestesicos = anestesicos;
    }

    public String getCoagulacion() {
        return this.coagulacion;
    }

    public void setCoagulacion(String coagulacion) {
        this.coagulacion = coagulacion;
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
        hash += (this.idAntecedentes != null) ? this.idAntecedentes.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DispAntecedentes)) {
            return false;
        }
        DispAntecedentes other = (DispAntecedentes) object;
        if ((this.idAntecedentes == null && other.idAntecedentes != null) || (this.idAntecedentes != null && !this.idAntecedentes.equals(other.idAntecedentes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entity.DispAntecedentes[ idAntecedentes=" + this.idAntecedentes + " ]";
    }
}
