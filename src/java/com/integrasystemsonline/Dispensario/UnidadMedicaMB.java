package com.integrasystemsonline.Dispensario;

import com.Entity.DispUnidadMedica;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispUnidadMedicaFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyUnidadMedicaModel;
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

@Named("unidadMedicaMB")
@ViewScoped
public class UnidadMedicaMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

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

    private List<DispUnidadMedica> listDispUnidadMedica = new ArrayList<>();

    private DispUnidadMedica dispUnidadMedica = new DispUnidadMedica();

    private UIData dataTable;

    private LazyDataModel<DispUnidadMedica> lazyDispUnidadMedica;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispUnidadMedica.getIdUnidadMedica() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            DispUnidadMedica unidadMedicaObj = this.dispUnidadMedicaFacade.findByNombre(this.dispUnidadMedica.getNombre(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            if (unidadMedicaObj == null) {
                if (guardar) {
                    this.dispUnidadMedica.setIdEmpresa(this.usuario.getIdEmpresa());
                    this.dispUnidadMedica.setIdCiudad(this.usuario.getIdCiudad());
                    this.dispUnidadMedica.setIdSector(this.usuario.getIdSector());
                    this.dispUnidadMedica.setUsuarioIngreso(this.usuario.getUsuario());
                    this.dispUnidadMedica.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispUnidadMedicaFacade.createWithValidator(this.dispUnidadMedica);
                    this.dispUnidadMedicaFacade.flush();
                } else {
                    this.dispUnidadMedica.setUsuarioModificacion(this.usuario.getUsuario());
                    this.dispUnidadMedica.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispUnidadMedicaFacade.editWithValidator(this.dispUnidadMedica);
                    this.dispUnidadMedicaFacade.flush();
                }
                this.userTransaction.commit();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                redireccionar();
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Especialidad ya registrada");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccionar() {
        try {
            this.dispUnidadMedica = new DispUnidadMedica();
            this.estado = "A";
            this.labelMant = "Ingresar";
        } catch (Exception exception) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.dispUnidadMedicaFacade.remove(this.dispUnidadMedica);
            this.dispUnidadMedicaFacade.flush();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispUnidadMedica = (DispUnidadMedica) event.getObject();
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<DispUnidadMedica> getAll() {
        if (this.lazyDispUnidadMedica == null) {
            this.lazyDispUnidadMedica = new LazyUnidadMedicaModel(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispUnidadMedica;
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispUnidadMedica> getListDispUnidadMedica() {
        return this.listDispUnidadMedica;
    }

    public void setListDispUnidadMedica(List<DispUnidadMedica> listDispUnidadMedica) {
        this.listDispUnidadMedica = listDispUnidadMedica;
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

    public DispUnidadMedica getDispUnidadMedica() {
        return this.dispUnidadMedica;
    }

    public void setDispUnidadMedica(DispUnidadMedica dispUnidadMedica) {
        this.dispUnidadMedica = dispUnidadMedica;
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
}
