package com.integrasystemsonline.Dispensario;

import com.Entity.DispEspecialidad;
import com.Entity.DispEstudiosMedicos;
import com.Entity.DispExamen;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispEspecialidadFacade;
import com.Session.DispEstudiosMedicosFacade;
import com.Session.DispExamenFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyEstudiosMedicosModel;
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

@Named("estudiosMedicosMB")
@ViewScoped
public class EstudiosMedicosMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispEstudiosMedicosFacade dispEstudiosMedicosFacade;

    @EJB
    DispEspecialidadFacade dispEspecialidadFacade;

    @EJB
    DispExamenFacade dispExamenFacade;
    
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

    private List<DispEstudiosMedicos> listDispEstudiosMedicos = new ArrayList<>();

    private DispEstudiosMedicos dispEstudiosMedicos = new DispEstudiosMedicos();

    private UIData dataTable;

    private LazyDataModel<DispEstudiosMedicos> lazyDispEstudiosMedicos;

    private String filtroConsulta;

    IsUsuarios usuario;

    private String estado;

    private String labelMant;

    private List<Estado> listaEstado;

    private Estado estadoObj;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    private String timeZone = "";

    private List<DispEspecialidad> listDispEspecialidad = new ArrayList<>();
  
    private DispEspecialidad especialidadObj = new DispEspecialidad();
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
            
            this.listDispEspecialidad = this.dispEspecialidadFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            
            if(!listDispEspecialidad.isEmpty()){
                especialidadObj = listDispEspecialidad.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onChangeNombre() {
        if (this.dispEstudiosMedicos.getNombre().isEmpty()) {
            this.dispEstudiosMedicos.setDetalle("");
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispEstudiosMedicos.getIdEstudiosMedicos()!= null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            this.dispEstudiosMedicos.setIdEspecialidad(this.especialidadObj);
            if (guardar) {
                DispEstudiosMedicos estudiosMedicosObj = this.dispEstudiosMedicosFacade.findByNombre(this.dispEstudiosMedicos.getNombre().toUpperCase(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                if (estudiosMedicosObj == null) {
                    this.dispEstudiosMedicos.setIdEmpresa(this.usuario.getIdEmpresa());
                    this.dispEstudiosMedicos.setIdCiudad(this.usuario.getIdCiudad());
                    this.dispEstudiosMedicos.setIdSector(this.usuario.getIdSector());
                    this.dispEstudiosMedicos.setUsuarioIngreso(this.usuario.getUsuario());
                    this.dispEstudiosMedicos.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispEstudiosMedicosFacade.createWithValidator(this.dispEstudiosMedicos);
                    this.dispEstudiosMedicosFacade.flush();
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Estudio Medico ya registrado");
                }
            } else {
                this.dispEstudiosMedicos.setUsuarioModificacion(this.usuario.getUsuario());
                this.dispEstudiosMedicos.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispEstudiosMedicosFacade.editWithValidator(this.dispEstudiosMedicos);
                this.dispEstudiosMedicosFacade.flush();
            }
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
            redireccionar();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccionar() {
        try {
            this.dispEstudiosMedicos = new DispEstudiosMedicos();
            this.estado = "A";
            if(!listDispEspecialidad.isEmpty()){
                especialidadObj = listDispEspecialidad.get(0);
            }
            this.labelMant = "Ingresar";
        } catch (Exception exception) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            List<DispExamen> lstExamenes = this.dispExamenFacade.findByIdEstudiosMedicos(this.dispEstudiosMedicos.getIdEstudiosMedicos());
            if (lstExamenes.isEmpty()) {
                this.dispEstudiosMedicosFacade.remove(this.dispEstudiosMedicos);
                this.dispEstudiosMedicosFacade.flush();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Existen examenes asociados a esta especializacion");
            }
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispEstudiosMedicos = (DispEstudiosMedicos) event.getObject();
            especialidadObj = dispEstudiosMedicos.getIdEspecialidad();
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<DispEstudiosMedicos> getAll() {
        if (this.lazyDispEstudiosMedicos == null) {
            this.lazyDispEstudiosMedicos = new LazyEstudiosMedicosModel(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispEstudiosMedicos;
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispEstudiosMedicos> getListDispEstudiosMedicos() {
        return this.listDispEstudiosMedicos;
    }

    public void setListDispEstudiosMedicos(List<DispEstudiosMedicos> listDispEstudiosMedicos) {
        this.listDispEstudiosMedicos = listDispEstudiosMedicos;
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

    public DispEstudiosMedicos getDispEstudiosMedicos() {
        return this.dispEstudiosMedicos;
    }

    public void setDispEstudiosMedicos(DispEstudiosMedicos dispEstudiosMedicos) {
        this.dispEstudiosMedicos = dispEstudiosMedicos;
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

    public List<DispEspecialidad> getListDispEspecialidad() {
        return listDispEspecialidad;
    }

    public void setListDispEspecialidad(List<DispEspecialidad> listDispEspecialidad) {
        this.listDispEspecialidad = listDispEspecialidad;
    }

    public DispEspecialidad getEspecialidadObj() {
        return especialidadObj;
    }

    public void setEspecialidadObj(DispEspecialidad especialidadObj) {
        this.especialidadObj = especialidadObj;
    }
    
}
