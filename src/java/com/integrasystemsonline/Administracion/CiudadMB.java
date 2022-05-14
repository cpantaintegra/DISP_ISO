/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsCiudad;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.IsCiudadFacade;
import com.Session.IsRolesPermisosFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyCiudadModel;
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

@Named(value = "ciudad")
@ViewScoped
public class CiudadMB implements Serializable{
    @Resource
    UserTransaction userTransaction;
    @EJB
    IsCiudadFacade isCiudadFacade;
    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;
    
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // La cadena de formato de fecha se pasa como un argumento al objeto de formato de fecha  
    private List<IsCiudad> listIsCiudad=new ArrayList();
    private IsCiudad isCiudad = new IsCiudad();
    private UIData dataTable;
    private LazyDataModel<IsCiudad> lazyIsCiudad;
    private String filtroConsulta;
    IsUsuarios usuario;
    private String estado, labelMant;
    private List<Estado> listaEstado;    
    private Estado estadoObj;
    private List<IsRolesPermisos> listIsRolesPermisos;
    private boolean editar=true, eliminarBl=true, consultar=true, ingresar=true;
    
    @PostConstruct
    public void ini() 
    {
        try
        {
            estado="A";
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
    
    public void guardar() throws SystemException{
        FacesMessage msg=null;
        boolean guardar=true;
        if(isCiudad.getIdCiudad()!=null)
                guardar=false;
        try {
            userTransaction.begin();
            if(guardar)
            {
                isCiudad.setUsuarioIngreso(usuario.getUsuario());
                isCiudad.setFechaIngreso(objSDF.parse(objSDF.format(new Date())));
                isCiudadFacade.createWithValidator(isCiudad);
                isCiudadFacade.flush();
            }else
            {
                isCiudad.setUsuarioModificacion(usuario.getUsuario());
                isCiudad.setFechaModificacion(objSDF.parse(objSDF.format(new Date())));  
                isCiudadFacade.editWithValidator(isCiudad);
                isCiudadFacade.flush();
            }
            userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Se realizo la transaccion con exito." );
            redireccionar();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error",e.toString());
            userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    public void redireccionar(){
        try {
            isCiudad = new IsCiudad();
            estado="A";
            labelMant="Ingresar";
        } catch (Exception e) {
        }       
    }   
    
    public void eliminar(){
        FacesMessage msg=null;
        try {
            userTransaction.begin();            
            isCiudadFacade.remove(isCiudad);
            isCiudadFacade.flush();
            userTransaction.commit();               
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error",e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowDblClckSelect(SelectEvent event) {
        try
        {
            isCiudad = (IsCiudad) event.getObject();
            labelMant="Actualizar";
        }catch(Exception e){e.printStackTrace();}
    }
    
    public LazyDataModel<IsCiudad> getAll() {
        if (lazyIsCiudad == null) {
            lazyIsCiudad = new LazyCiudadModel(estado);
        }
        return lazyIsCiudad;
    }

    public UIData getDataTable() {
        return dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }
    
    public List<IsCiudad> getListIsCiudad() {
        return listIsCiudad;
    }

    public void setListIsCiudad(List<IsCiudad> listIsCiudad) {
        this.listIsCiudad = listIsCiudad;
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

    public IsCiudad getIsCiudad() {
        return isCiudad;
    }

    public void setIsCiudad(IsCiudad isCiudad) {
        this.isCiudad = isCiudad;
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
