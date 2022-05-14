/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsMenu;
import com.Entity.IsRoles;
import com.Entity.IsRolesMenu;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.IsMenuFacade;
import com.Session.IsRolesFacade;
import com.Session.IsRolesMenuFacade;
import com.Session.IsRolesPermisosFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyMenuModel;
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
@Named(value = "menuMB")
@ViewScoped
public class MenuMB implements Serializable {
    @Resource
    UserTransaction userTransaction;
    @EJB
    IsMenuFacade isMenuFacade;
    @EJB
    IsRolesFacade isRolesFacade;
    @EJB
    IsRolesMenuFacade isRolesMenuFacade;
    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;
    private IsMenu isMenu;
    private String estado;
    private String labelMant;
    private LazyDataModel<IsMenu> lazyIsMenu;
    IsUsuarios usuario;
    IsRolesMenu isRolesMenu;
    private String filtroConsulta;
    private List<Estado> listaEstado;    
    private Estado estadoObj;
    private List<Estado> listaMenuPadre;    
    private Estado menuPadreObj;
    private List<Estado> listaMenuPadreMant;    
    private Estado menuPadreMantObj;
    private List<IsRoles> listRoles;
    private List<IsRoles> listRolesDrop;
    private IsRoles selectedRolesObj; 
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // La cadena de formato de fecha se pasa como un argumento al objeto de formato de fecha  
    private List<IsRolesPermisos> listIsRolesPermisos;
    private boolean editar=true, eliminarBl=true, consultar=true, ingresar=true;
    /**
     * Creates a new instance of MenuMB
     */
    public MenuMB() {
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
            listaMenuPadre = new ArrayList<>();
            listaMenuPadreMant = new ArrayList<>();
            estado = new Estado();
            estado.setDetalle("-SELECCIONE-");
            listaMenuPadre.add(estado);
            listaMenuPadreMant.add(estado);
            estado = new Estado();
            estado.setDetalle("Menu Padre");
            estado.setValor("0");
            listaMenuPadre.add(estado);
            List<IsMenu> listaMe = isMenuFacade.listaIsMenuActivos(usuario.getIdEmpresa().getIdEmpresa(), usuario.getIdCiudad().getIdCiudad(), usuario.getIdSector().getIdSector());
            for (IsMenu isMenu1 : listaMe) {
                estado = new Estado();
                estado.setValor(String.valueOf(isMenu1.getIdMenu()));
                estado.setDetalle(isMenu1.getNombre());
                listaMenuPadre.add(estado);
                listaMenuPadreMant.add(estado);
            }
            listRoles = isRolesFacade.findAllActivos();
            listRolesDrop = new ArrayList<>();
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
            IsMenu menuPadre=new IsMenu();
            isMenu = (IsMenu) event.getObject();
            labelMant="Actualizar";           
            Estado estado = new Estado();
            if(isMenu.getIdMenuPadre()!=null)
            {
                menuPadre = isMenuFacade.findById(isMenu.getIdMenuPadre());
                if(menuPadre!=null){
                    if(!listaMenuPadreMant.isEmpty()){
                    for (int i = 0; i < listaMenuPadreMant.size(); i++) {
                        if(String.valueOf(menuPadre.getIdMenu()).equals(listaMenuPadreMant.get(i).getValor())){
                            menuPadreMantObj=listaMenuPadreMant.get(i);
                        }
                    }
                    }
                }
                
                
//                estado.setValor(String.valueOf(menuPadre.getIdMenu()));
//                estado.setDetalle(menuPadre.getNombre());
//                menuPadreMantObj=estado;
            }
            listRoles = isRolesFacade.findAllActivos();
            listRolesDrop = new ArrayList<>();
            List<IsRolesMenu> collIsRoles = isRolesMenuFacade.findAllByMenu(isMenu);
            for (IsRolesMenu collIsRole : collIsRoles) {
                listRolesDrop.add(collIsRole.getIdRoles());
                listRoles.remove(collIsRole.getIdRoles());
            }
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    public LazyDataModel<IsMenu> getAll() {
        if (lazyIsMenu == null) {
            lazyIsMenu = new LazyMenuModel(usuario.getIdEmpresa().getIdEmpresa(), usuario.getIdCiudad().getIdCiudad(), usuario.getIdSector().getIdSector(), null, estado);
        }
        return lazyIsMenu;
    }
    
    public void redireccionar(){
        //FacesContext context = FacesContext.getCurrentInstance();
        try {
            isMenu = new IsMenu();
            estado="A";
            labelMant="Ingresar";
            listRoles = isRolesFacade.findAllActivos();
            listRolesDrop = new ArrayList<>();
        } catch (Exception e) {
        }       
    }
    
    public void eliminar(){
        FacesMessage msg;
        try {
            userTransaction.begin();
            List<IsRolesMenu> listIsRolesMenu;
            listIsRolesMenu=isRolesMenuFacade.findByMenu(isMenu);
            for (int i = 0; i < listIsRolesMenu.size(); i++) {
                isRolesMenu=listIsRolesMenu.get(i);
                isRolesMenuFacade.remove(isRolesMenu);
                isRolesMenuFacade.flush();
            }
            
            isMenuFacade.remove(isMenu);
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
        if(isMenu.getIdMenu()!=null)
                guardar=false;
        try {
            if(!listRolesDrop.isEmpty())
            {
                userTransaction.begin();
                try{isMenu.setIdMenuPadre(Integer.valueOf(menuPadreMantObj.getValor()));}catch(Exception e){}
                if(guardar)
                {
                    isMenu.setIdEmpresa(usuario.getIdEmpresa());
                    isMenu.setIdCiudad(usuario.getIdCiudad());
                    isMenu.setIdSector(usuario.getIdSector());
                    isMenu.setUsuarioIngreso(usuario.getUsuario());
                    isMenu.setFechaIngreso(objSDF.parse(objSDF.format(new Date())));
                    isMenuFacade.createWithValidator(isMenu);
                    isMenuFacade.flush();
                }else
                {
                    isMenu.setUsuarioModificacion(usuario.getUsuario());
                    isMenu.setFechaModificacion(objSDF.parse(objSDF.format(new Date())));  
                    isMenuFacade.editWithValidator(isMenu);
                    isMenuFacade.flush();
                    Collection<IsRolesMenu> rolesAsociados = isRolesMenuFacade.findAllByMenu(isMenu);
                    for (IsRolesMenu rolesAsociado : rolesAsociados) {
                        isRolesMenuFacade.remove(rolesAsociado);
                        isRolesMenuFacade.flush();
                    }
                }
                IsRolesMenu isRolesMenu = null;
                for (IsRoles isRoles : listRolesDrop) {
                    isRolesMenu = new IsRolesMenu();
                    isRolesMenu.setIdRoles(isRoles);
                    isRolesMenu.setIdMenu(isMenu);
                    isRolesMenu.setIdEmpresa(usuario.getIdEmpresa());
                    isRolesMenu.setIdCiudad(usuario.getIdCiudad());
                    isRolesMenu.setIdSector(usuario.getIdSector());
                    isRolesMenu.setEstado(isMenu.getEstado());
                    isRolesMenu.setFechaIngreso(isMenu.getFechaIngreso());
                    isRolesMenu.setUsuarioIngreso(isMenu.getUsuarioIngreso());
                    isRolesMenu.setFechaModificacion(isMenu.getFechaModificacion());
                    isRolesMenu.setUsuarioModificacion(isMenu.getUsuarioModificacion());
                    isRolesMenuFacade.createWithValidator(isRolesMenu);
                    isRolesMenuFacade.flush();
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
        IsRoles car = (IsRoles) ddEvent.getData();
        listRolesDrop.add(car);
        listRoles.remove(car);
    }
    
    public void onCarDrop1(DragDropEvent ddEvent) {
        IsRoles car = (IsRoles) ddEvent.getData();
        listRoles.add(car);
        listRolesDrop.remove(car);
    }

    public IsMenu getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(IsMenu isMenu) {
        this.isMenu = isMenu;
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

    public List<Estado> getListaMenuPadre() {
        return listaMenuPadre;
    }

    public void setListaMenuPadre(List<Estado> listaMenuPadre) {
        this.listaMenuPadre = listaMenuPadre;
    }

    public Estado getMenuPadreObj() {
        return menuPadreObj;
    }

    public void setMenuPadreObj(Estado menuPadreObj) {
        this.menuPadreObj = menuPadreObj;
    }

    public List<Estado> getListaMenuPadreMant() {
        return listaMenuPadreMant;
    }

    public void setListaMenuPadreMant(List<Estado> listaMenuPadreMant) {
        this.listaMenuPadreMant = listaMenuPadreMant;
    }

    public Estado getMenuPadreMantObj() {
        return menuPadreMantObj;
    }

    public void setMenuPadreMantObj(Estado menuPadreMantObj) {
        this.menuPadreMantObj = menuPadreMantObj;
    }

    public List<IsRoles> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<IsRoles> listRoles) {
        this.listRoles = listRoles;
    }

    public IsRoles getSelectedRolesObj() {
        return selectedRolesObj;
    }

    public void setSelectedRolesObj(IsRoles selectedRolesObj) {
        this.selectedRolesObj = selectedRolesObj;
    }

    public List<IsRoles> getListRolesDrop() {
        return listRolesDrop;
    }

    public void setListRolesDrop(List<IsRoles> listRolesDrop) {
        this.listRolesDrop = listRolesDrop;
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
