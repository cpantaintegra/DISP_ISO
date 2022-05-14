package com.integrasystemsonline.Dispensario;

import com.Entity.DispConsultorio;
import com.Entity.DispEspecialidad;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispConsultorioFacade;
import com.Session.DispEspecialidadFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyConsultorioModel;
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

@Named("consultorioMB")
@ViewScoped
public class ConsultorioMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispEspecialidadFacade dispEspecialidadFacade;

    @EJB
    DispConsultorioFacade dispConsultorioFacade;

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

    private List<DispConsultorio> listDispConsultorio = new ArrayList<>();

    private DispConsultorio dispConsultorio = new DispConsultorio();

    private List<DispEspecialidad> listEspecialidad;

    private DispEspecialidad especialidadObj;

    private UIData dataTable;

    private LazyDataModel<DispConsultorio> lazyDispConsultorio;

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
            this.listEspecialidad = this.dispEspecialidadFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispConsultorio.getIdConsultorio() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (this.especialidadObj != null) {
                this.dispConsultorio.setIdEspecialidad(this.especialidadObj);
                if (guardar) {
                    this.dispConsultorio.setIdEmpresa(this.usuario.getIdEmpresa());
                    this.dispConsultorio.setIdCiudad(this.usuario.getIdCiudad());
                    this.dispConsultorio.setIdSector(this.usuario.getIdSector());
                    this.dispConsultorio.setUsuarioIngreso(this.usuario.getUsuario());
                    this.dispConsultorio.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispConsultorioFacade.createWithValidator(this.dispConsultorio);
                    this.dispConsultorioFacade.flush();
                } else {
                    this.dispConsultorio.setUsuarioModificacion(this.usuario.getUsuario());
                    this.dispConsultorio.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispConsultorioFacade.editWithValidator(this.dispConsultorio);
                    this.dispConsultorioFacade.flush();
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Seleccione una especialidad");
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
            this.dispConsultorio = new DispConsultorio();
            this.estado = "A";
            this.labelMant = "Ingresar";
            try {
                this.especialidadObj = this.listEspecialidad.get(0);
            } catch (Exception exception) {
            }
        } catch (Exception exception) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.dispConsultorioFacade.remove(this.dispConsultorio);
            this.dispConsultorioFacade.flush();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispConsultorio = (DispConsultorio) event.getObject();
            this.labelMant = "Actualizar";
            this.especialidadObj = this.dispConsultorio.getIdEspecialidad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<DispConsultorio> getAll() {
        Integer especialidad = Integer.valueOf(0);
        if (this.lazyDispConsultorio == null) {
            if (this.especialidadObj != null) {
                especialidad = this.especialidadObj.getIdEspecialidad();
            }
            this.lazyDispConsultorio = new LazyConsultorioModel(especialidad, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispConsultorio;
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispConsultorio> getListDispConsultorio() {
        return this.listDispConsultorio;
    }

    public void setListDispConsultorio(List<DispConsultorio> listDispConsultorio) {
        this.listDispConsultorio = listDispConsultorio;
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

    public DispEspecialidad getEspecialidadObj() {
        return this.especialidadObj;
    }

    public void setEspecialidadObj(DispEspecialidad especialidadObj) {
        this.especialidadObj = especialidadObj;
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

    public DispConsultorio getDispConsultorio() {
        return this.dispConsultorio;
    }

    public void setDispConsultorio(DispConsultorio dispConsultorio) {
        this.dispConsultorio = dispConsultorio;
    }

    public List<DispEspecialidad> getListEspecialidad() {
        return this.listEspecialidad;
    }

    public void setListEspecialidad(List<DispEspecialidad> listEspecialidad) {
        this.listEspecialidad = listEspecialidad;
    }
}
