/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsPermisos;
import com.Entity.IsRoles;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.IsPermisosFacade;
import com.Session.IsRolesFacade;
import com.Session.IsRolesPermisosFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyRolesModel;
import com.integrasystemsonline.Utilidades.Utilidades;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author user
 */
@Named(value = "roles")
@ViewScoped
public class RolesMB implements Serializable {
    @Resource
    UserTransaction userTransaction;
    @EJB
    IsPermisosFacade isPermisosFacade;
    @EJB
    IsRolesFacade isRolesFacade;
    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;
    private IsRoles isRoles;
    private String estado;
    private String labelMant;
    private LazyDataModel<IsRoles> lazyIsRoles;
    IsUsuarios usuario;
    IsRolesPermisos isRolesPermisos;
    private String filtroConsulta;
    private List<Estado> listaEstado;    
    private Estado estadoObj;
    private List<IsPermisos> listPermisos;
    private List<IsPermisos> listPermisosDrop;
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // La cadena de formato de fecha se pasa como un argumento al objeto de formato de fecha  
    private List<IsRolesPermisos> listIsRolesPermisos;
    private boolean editar=true, eliminarBl=true, consultar=true, ingresar=true;
    /**
     * Creates a new instance of MenuMB
     */
    public RolesMB() {
    }
    
    @PostConstruct
    public void ini() 
    {
        try
        {
            this.estado="A";
            labelMant="Ingresar";
            listaEstado = new ArrayList<>();
            Estado estado = new Estado();
            estado.setValor("A");
            estado.setDetalle("Activo");
            estadoObj=estado;
            listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("I");
            estado.setDetalle("Inactivo");
            listaEstado.add(estado);
            usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listIsRolesPermisos = isRolesPermisosFacade.findByRol(usuario.getIdRoles());
            String eli="",edi="",con="",ing="";
            Properties propParam=Utilidades.obtenerProperties("MessageResources.properties");
            try {
                eli=propParam.getProperty("eliminar");
                edi=propParam.getProperty("editar");
                con=propParam.getProperty("consultar");
                ing=propParam.getProperty("ingresar");
            } catch (Exception e) {
            }
            
            for (IsRolesPermisos isRolesPermisos : listIsRolesPermisos) {
                if(isRolesPermisos.getIdPermisos().getNombre().equals(edi))
                    editar=false;
                else if(isRolesPermisos.getIdPermisos().getNombre().equals(eli))
                    eliminarBl=false;
                else if(isRolesPermisos.getIdPermisos().getNombre().equals(con))
                    consultar=false;
                else if(isRolesPermisos.getIdPermisos().getNombre().equals(ing))
                    ingresar=false;
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void onRowDblClckSelect(SelectEvent event) {
        try
        {
            isRoles = (IsRoles) event.getObject();
            labelMant="Actualizar";
            //listaRoles=isRolesFacade.findAllActivos();
            listPermisos = isPermisosFacade.findAllActivos();
            listPermisosDrop = new ArrayList<>();
            List<IsRolesPermisos> collIsPermisos = isRolesPermisosFacade.findAllByRol(isRoles);
            for (IsRolesPermisos collIsPermiso : collIsPermisos) {
                listPermisosDrop.add(collIsPermiso.getIdPermisos());
                listPermisos.remove(collIsPermiso.getIdPermisos());
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public LazyDataModel<IsRoles> getAll() {
        if (lazyIsRoles == null) {
            lazyIsRoles = new LazyRolesModel(usuario.getIdEmpresa().getIdEmpresa(), usuario.getIdCiudad().getIdCiudad(), usuario.getIdSector().getIdSector(), estado);
        }
        return lazyIsRoles;
    }
    
    public void redireccionar(){
        //FacesContext context = FacesContext.getCurrentInstance();
        try {
            isRoles = new IsRoles();
            estado="A";
            labelMant="Ingresar";
            //listaRoles=isRolesFacade.findAllActivos();
            listPermisos = isPermisosFacade.findAllActivos();
            listPermisosDrop = new ArrayList<>();
        } catch (Exception e) {
        }       
    }
    
    public void eliminar(){
        FacesMessage msg;
        try {
            userTransaction.begin();
            List<IsRolesPermisos> listIsRolesPermisos;
            listIsRolesPermisos=isRolesPermisosFacade.findByRol(isRoles);
            for (int i = 0; i < listIsRolesPermisos.size(); i++) {
                isRolesPermisos=listIsRolesPermisos.get(i);
                isRolesPermisosFacade.remove(isRolesPermisos);
                isRolesPermisosFacade.flush();
            }
            
            isRolesFacade.remove(isRoles);
            isRolesFacade.flush();
            userTransaction.commit();               
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error",e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void guardarAct() throws SystemException{
        FacesMessage msg=null;
        boolean guardar=true;
        if(isRoles.getIdRoles()!=null)
                guardar=false;
        try {
            if(!listPermisosDrop.isEmpty())
            {
                userTransaction.begin();
                if(guardar)
                {
                    isRoles.setIdEmpresa(usuario.getIdEmpresa());
                    isRoles.setIdCiudad(usuario.getIdCiudad());
                    isRoles.setIdSector(usuario.getIdSector());
                    isRoles.setUsuarioIngreso(usuario.getUsuario());
                    isRoles.setFechaIngreso(objSDF.parse(objSDF.format(new Date())));
                    isRolesFacade.createWithValidator(isRoles);
                    isRolesFacade.flush();
                }else
                {
                    isRoles.setUsuarioModificacion(usuario.getUsuario());
                    isRoles.setFechaModificacion(objSDF.parse(objSDF.format(new Date())));  
                    isRolesFacade.editWithValidator(isRoles);
                    isRolesFacade.flush();
                    Collection<IsRolesPermisos> permisosAsociados = isRolesPermisosFacade.findAllByRol(isRoles);
                    for (IsRolesPermisos permisoAsociado : permisosAsociados) {
                        isRolesPermisosFacade.remove(permisoAsociado);
                        isRolesPermisosFacade.flush();
                    }
                }
                IsRolesPermisos isRolesPermisos = null;
                for (IsPermisos isPermisos : listPermisosDrop) {
                    isRolesPermisos = new IsRolesPermisos();
                    isRolesPermisos.setIdPermisos(isPermisos);
                    isRolesPermisos.setIdRoles(isRoles);
                    isRolesPermisos.setIdEmpresa(usuario.getIdEmpresa());
                    isRolesPermisos.setIdCiudad(usuario.getIdCiudad());
                    isRolesPermisos.setIdSector(usuario.getIdSector());
                    isRolesPermisos.setEstado(isRoles.getEstado());
                    isRolesPermisos.setFechaIngreso(isRoles.getFechaIngreso());
                    isRolesPermisos.setUsuarioIngreso(isRoles.getUsuarioIngreso());
                    isRolesPermisos.setFechaModificacion(isRoles.getFechaModificacion());
                    isRolesPermisos.setUsuarioModificacion(isRoles.getUsuarioModificacion());
                    isRolesPermisosFacade.createWithValidator(isRolesPermisos);
                    isRolesPermisosFacade.flush();
                }
                userTransaction.commit();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Se realizo la transaccion con exito." );
                redireccionar();
            }else
            {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error","Debe de agregar por lo menos un rol." );
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error",e.toString());
            userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onCarDrop(DragDropEvent ddEvent) {
        IsPermisos car = (IsPermisos) ddEvent.getData();
        listPermisosDrop.add(car);
        listPermisos.remove(car);
    }
    
    public void onCarDrop1(DragDropEvent ddEvent) {
        IsPermisos car = (IsPermisos) ddEvent.getData();
        listPermisos.add(car);
        listPermisosDrop.remove(car);
    }

    public IsRoles getIsRoles() {
        return isRoles;
    }

    public void setIsRoles(IsRoles isRoles) {
        this.isRoles = isRoles;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLabelMant() {
        return labelMant;
    }

    public void setLabelMant(String labelMant) {
        this.labelMant = labelMant;
    }

    public IsUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(IsUsuarios usuario) {
        this.usuario = usuario;
    }

    public String getFiltroConsulta() {
        return filtroConsulta;
    }

    public void setFiltroConsulta(String filtroConsulta) {
        this.filtroConsulta = filtroConsulta;
    }

    public Estado getEstadoObj() {
        return estadoObj;
    }

    public void setEstadoObj(Estado estadoObj) {
        this.estadoObj = estadoObj;
    }

    public List<Estado> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }
    
    public List<IsPermisos> getListPermisos() {
        return listPermisos;
    }

    public void setListPermisos(List<IsPermisos> listPermisos) {
        this.listPermisos = listPermisos;
    }

    public List<IsPermisos> getListPermisosDrop() {
        return listPermisosDrop;
    }

    public void setListPermisosDrop(List<IsPermisos> listPermisosDrop) {
        this.listPermisosDrop = listPermisosDrop;
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

