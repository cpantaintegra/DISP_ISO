package com.integrasystemsonline.Dispensario;

import com.Entity.DispEstudiosMedicos;
import com.Entity.DispExamen;
import com.Entity.DispUnidadMedica;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispEstudiosMedicosFacade;
import com.Session.DispExamenFacade;
import com.Session.DispUnidadMedicaFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyExamenModel;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("examenMB")
@ViewScoped
public class ExamenMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispExamenFacade dispExamenFacade;

    @EJB
    DispEstudiosMedicosFacade dispEstudiosMedicosFacade;

    @EJB
    DispUnidadMedicaFacade dispUnidadMedicaFacade;

    @EJB
    IsEmpresaFacade isEmpresaFacade;

    @EJB
    IsCiudadFacade isCiudadFacade;

    @EJB
    IsSectorFacade isSectorFacade;

    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;

    @EJB
    IsParametrosFacade isParametrosFacade;

    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<DispExamen> listDispExamen = new ArrayList<>();

    private DispExamen dispExamen = new DispExamen();

    private List<DispEstudiosMedicos> listDispEstudiosMedicos = new ArrayList<>();

    private DispEstudiosMedicos estudiosMedicosObj = new DispEstudiosMedicos();

    private List<DispUnidadMedica> listDispUnidadMedica = new ArrayList<>();

    private DispUnidadMedica unidadMedicaObj = new DispUnidadMedica();

    private UIData dataTable;

    private LazyDataModel<DispExamen> lazyDispExamen;

    private String filtroConsulta;

    IsUsuarios usuario;

    private String estado;

    private String labelMant;

    private String labelMantPrecio;

    private List<Estado> listaEstado;

    private Estado estadoObj;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    private String timeZone = "";

    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
            this.estado = "A";
            this.labelMant = "Ingresar";
            this.listaEstado = new ArrayList<>();
            Estado estado = new Estado();
            estado.setValor("A");
            estado.setDetalle("Activo");
            this.estadoObj = estado;
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("I");
            estado.setDetalle("Inactivo");
            this.listaEstado.add(estado);
            this.listIsRolesPermisos = this.isRolesPermisosFacade.findByRol(this.usuario.getIdRoles());
            String eli = "", edi = "", con = "", ing = "";
            Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
            try {
                eli = propParam.getProperty("eliminar");
                edi = propParam.getProperty("editar");
                con = propParam.getProperty("consultar");
                ing = propParam.getProperty("ingresar");
            } catch (Exception exception) {
            }
            for (IsRolesPermisos isRolesPermisos : this.listIsRolesPermisos) {
                if (isRolesPermisos.getIdPermisos().getNombre().equals(edi)) {
                    this.editar = false;
                    continue;
                }
                if (isRolesPermisos.getIdPermisos().getNombre().equals(eli)) {
                    this.eliminarBl = false;
                    continue;
                }
                if (isRolesPermisos.getIdPermisos().getNombre().equals(con)) {
                    this.consultar = false;
                    continue;
                }
                if (isRolesPermisos.getIdPermisos().getNombre().equals(ing)) {
                    this.ingresar = false;
                }
            }
            this.listDispEstudiosMedicos = this.dispEstudiosMedicosFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            if (!listDispEstudiosMedicos.isEmpty()) {
                this.estudiosMedicosObj = this.listDispEstudiosMedicos.get(0);
            }

            this.listDispUnidadMedica = this.dispUnidadMedicaFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            if (!listDispUnidadMedica.isEmpty()) {
                this.estudiosMedicosObj = this.listDispEstudiosMedicos.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispExamen.getIdExamen() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (this.estudiosMedicosObj.getIdEstudiosMedicos() != null) {
                if (this.unidadMedicaObj.getIdUnidadMedica() != null) {
                    this.dispExamen.setIdEstudiosMedicos(this.estudiosMedicosObj);
                    this.dispExamen.setIdUnidadMedica(this.unidadMedicaObj);
                    if (guardar) {
                        DispExamen examenObj = this.dispExamenFacade.findByNombre(this.dispExamen.getNombre().toUpperCase(), this.estudiosMedicosObj.getIdEstudiosMedicos(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                        if (examenObj == null) {
                            this.dispExamen.setEstado("A");
                            this.dispExamen.setIdEmpresa(this.usuario.getIdEmpresa());
                            this.dispExamen.setIdCiudad(this.usuario.getIdCiudad());
                            this.dispExamen.setIdSector(this.usuario.getIdSector());
                            this.dispExamen.setUsuarioIngreso(this.usuario.getUsuario());
                            this.dispExamen.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                            this.dispExamenFacade.createWithValidator(this.dispExamen);
                            this.dispExamenFacade.flush();
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                            redireccionar();
                        } else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Examen ya registrado");
                        }
                    } else {
                        this.dispExamen.setUsuarioModificacion(this.usuario.getUsuario());
                        this.dispExamen.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                        this.dispExamenFacade.editWithValidator(this.dispExamen);
                        this.dispExamenFacade.flush();
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                        redireccionar();
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Por favor, seleccione la unidad medica");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Por favor, seleccione el estudio medico");
            }
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void redireccionar() {
        try {
            this.dispExamen = new DispExamen();
            this.estado = "A";
            this.labelMant = "Ingresar";
            if (!listDispEstudiosMedicos.isEmpty()) {
                this.estudiosMedicosObj = this.listDispEstudiosMedicos.get(0);
            }

            if (!listDispUnidadMedica.isEmpty()) {
                this.unidadMedicaObj = this.listDispUnidadMedica.get(0);
            }
        } catch (Exception exception) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.dispExamenFacade.remove(this.dispExamen);
            this.dispExamenFacade.flush();
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispExamen = (DispExamen) event.getObject();
            this.labelMant = "Actualizar";
            this.estudiosMedicosObj = this.dispExamen.getIdEstudiosMedicos();
            this.unidadMedicaObj = this.dispExamen.getIdUnidadMedica();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<DispExamen> getAll() {
        if (this.lazyDispExamen == null) {
            this.lazyDispExamen = new LazyExamenModel(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispExamen;
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispExamen> getListDispExamen() {
        return this.listDispExamen;
    }

    public void setListDispExamen(List<DispExamen> listDispExamen) {
        this.listDispExamen = listDispExamen;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFiltroConsulta() {
        return this.filtroConsulta;
    }

    public void setFiltroConsulta(String filtroConsulta) {
        this.filtroConsulta = filtroConsulta;
    }

    public DispExamen getDispExamen() {
        return this.dispExamen;
    }

    public void setDispExamen(DispExamen dispExamen) {
        this.dispExamen = dispExamen;
    }

    public IsUsuarios getUsuario() {
        return this.usuario;
    }

    public void setUsuario(IsUsuarios usuario) {
        this.usuario = usuario;
    }

    public String getLabelMant() {
        return this.labelMant;
    }

    public void setLabelMant(String labelMant) {
        this.labelMant = labelMant;
    }

    public List<Estado> getListaEstado() {
        return this.listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public Estado getEstadoObj() {
        return this.estadoObj;
    }

    public void setEstadoObj(Estado estadoObj) {
        this.estadoObj = estadoObj;
    }

    public List<IsRolesPermisos> getListIsRolesPermisos() {
        return this.listIsRolesPermisos;
    }

    public void setListIsRolesPermisos(List<IsRolesPermisos> listIsRolesPermisos) {
        this.listIsRolesPermisos = listIsRolesPermisos;
    }

    public boolean isEliminarBl() {
        return this.eliminarBl;
    }

    public void setEliminarBl(boolean eliminarBl) {
        this.eliminarBl = eliminarBl;
    }

    public boolean isConsultar() {
        return this.consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public boolean isIngresar() {
        return this.ingresar;
    }

    public void setIngresar(boolean ingresar) {
        this.ingresar = ingresar;
    }

    public boolean isEditar() {
        return this.editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public List<DispEstudiosMedicos> getListDispEstudiosMedicos() {
        return this.listDispEstudiosMedicos;
    }

    public void setListDispEstudiosMedicos(List<DispEstudiosMedicos> listDispEstudiosMedicos) {
        this.listDispEstudiosMedicos = listDispEstudiosMedicos;
    }

    public DispEstudiosMedicos getEstudiosMedicosObj() {
        return this.estudiosMedicosObj;
    }

    public void setEstudiosMedicosObj(DispEstudiosMedicos estudiosMedicosObj) {
        this.estudiosMedicosObj = estudiosMedicosObj;
    }

    public String getLabelMantPrecio() {
        return this.labelMantPrecio;
    }

    public void setLabelMantPrecio(String labelMantPrecio) {
        this.labelMantPrecio = labelMantPrecio;
    }

    public List<DispUnidadMedica> getListDispUnidadMedica() {
        return listDispUnidadMedica;
    }

    public void setListDispUnidadMedica(List<DispUnidadMedica> listDispUnidadMedica) {
        this.listDispUnidadMedica = listDispUnidadMedica;
    }

    public DispUnidadMedica getUnidadMedicaObj() {
        return unidadMedicaObj;
    }

    public void setUnidadMedicaObj(DispUnidadMedica unidadMedicaObj) {
        this.unidadMedicaObj = unidadMedicaObj;
    }
    
}
