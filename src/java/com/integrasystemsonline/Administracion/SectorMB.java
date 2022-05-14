/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsSector;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.IsSectorFacade;
import com.Session.IsRolesPermisosFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazySectorModel;
import com.integrasystemsonline.Utilidades.Utilidades;
import javax.inject.Named;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named(value = "sector")
@ViewScoped
public class SectorMB implements Serializable {

    @Resource
    UserTransaction userTransaction;
    @EJB
    IsSectorFacade isSectorFacade;
    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;

    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // La cadena de formato de fecha se pasa como un argumento al objeto de formato de fecha  
    private List<IsSector> listIsSector = new ArrayList();
    private IsSector isSector = new IsSector();
    private UIData dataTable;
    private LazyDataModel<IsSector> lazyIsSector;
    private String filtroConsulta;
    IsUsuarios usuario;
    private String estado, labelMant;
    private List<Estado> listaEstado;
    private Estado estadoObj;
    private List<IsRolesPermisos> listIsRolesPermisos;
    private boolean editar = true, eliminarBl = true, consultar = true, ingresar = true;

    @PostConstruct
    public void ini() {
        try {
            estado = "A";
            labelMant = "Ingresar";
            listaEstado = new ArrayList<>();
            Estado estado = new Estado();
            estado.setValor("A");
            estado.setDetalle("Activo");
            estadoObj = estado;
            listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("I");
            estado.setDetalle("Inactivo");
            listaEstado.add(estado);
            usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listIsRolesPermisos = isRolesPermisosFacade.findByRol(usuario.getIdRoles());

            String eli = "", edi = "", con = "", ing = "";
            Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
            try {
                eli = propParam.getProperty("eliminar");
                edi = propParam.getProperty("editar");
                con = propParam.getProperty("consultar");
                ing = propParam.getProperty("ingresar");
            } catch (Exception e) {
            }

            for (IsRolesPermisos isRolesPermisos : listIsRolesPermisos) {
                if (isRolesPermisos.getIdPermisos().getNombre().equals(edi)) {
                    editar = false;
                } else if (isRolesPermisos.getIdPermisos().getNombre().equals(eli)) {
                    eliminarBl = false;
                } else if (isRolesPermisos.getIdPermisos().getNombre().equals(con)) {
                    consultar = false;
                } else if (isRolesPermisos.getIdPermisos().getNombre().equals(ing)) {
                    ingresar = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (isSector.getIdSector() != null) {
            guardar = false;
        }
        try {
            userTransaction.begin();
            if (guardar) {
                isSector.setUsuarioIngreso(usuario.getUsuario());
                isSector.setFechaIngreso(objSDF.parse(objSDF.format(new Date())));
                isSectorFacade.createWithValidator(isSector);
                isSectorFacade.flush();
            } else {
                isSector.setUsuarioModificacion(usuario.getUsuario());
                isSector.setFechaModificacion(objSDF.parse(objSDF.format(new Date())));
                isSectorFacade.editWithValidator(isSector);
                isSectorFacade.flush();
            }
            userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
            redireccionar();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccionar() {
        try {
            isSector = new IsSector();
            estado = "A";
            labelMant = "Ingresar";
        } catch (Exception e) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            userTransaction.begin();
            isSectorFacade.remove(isSector);
            isSectorFacade.flush();
            userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            isSector = (IsSector) event.getObject();
            labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<IsSector> getAll() {
        if (lazyIsSector == null) {
            lazyIsSector = new LazySectorModel(estado);
        }
        return lazyIsSector;
    }

    public UIData getDataTable() {
        return dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<IsSector> getListIsSector() {
        return listIsSector;
    }

    public void setListIsSector(List<IsSector> listIsSector) {
        this.listIsSector = listIsSector;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFiltroConsulta() {
        return filtroConsulta;
    }

    public void setFiltroConsulta(String filtroConsulta) {
        this.filtroConsulta = filtroConsulta;
    }

    public IsSector getIsSector() {
        return isSector;
    }

    public void setIsSector(IsSector isSector) {
        this.isSector = isSector;
    }

    public IsUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(IsUsuarios usuario) {
        this.usuario = usuario;
    }

    public String getLabelMant() {
        return labelMant;
    }

    public void setLabelMant(String labelMant) {
        this.labelMant = labelMant;
    }

    public List<Estado> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public Estado getEstadoObj() {
        return estadoObj;
    }

    public void setEstadoObj(Estado estadoObj) {
        this.estadoObj = estadoObj;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean isEliminarBl() {
        return eliminarBl;
    }

    public void setEliminarBl(boolean eliminarBl) {
        this.eliminarBl = eliminarBl;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public boolean isIngresar() {
        return ingresar;
    }

    public void setIngresar(boolean ingresar) {
        this.ingresar = ingresar;
    }

    public List<IsRolesPermisos> getListIsRolesPermisos() {
        return listIsRolesPermisos;
    }

    public void setListIsRolesPermisos(List<IsRolesPermisos> listIsRolesPermisos) {
        this.listIsRolesPermisos = listIsRolesPermisos;
    }

}
