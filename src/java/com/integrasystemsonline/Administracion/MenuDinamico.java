/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsMenu;
import com.Entity.IsRolesMenu;
import com.Entity.IsUsuarios;
import com.Session.IsMenuFacade;
import com.Session.IsRolesMenuFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author user
 */
@Named(value = "menuDinamico")
@ViewScoped
public class MenuDinamico implements Serializable {

    @EJB
    private IsMenuFacade isMenuFacade;
    @EJB
    private IsRolesMenuFacade isRolesMenuFacade;
    private IsMenu isMenu;
    private MenuModel model;
    private IsUsuarios usuario;
    Properties propParam=Utilidades.obtenerProperties("MessageResources.properties");
    
    @PostConstruct
    public void init() 
    {
        model = new DefaultMenuModel();
        usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        /*usuario = LoginMB.usuario;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);*/
        //First submenu
        List<IsMenu> listaMenuPadre = new ArrayList<>();
        List<IsMenu> listaMenuHijo = new ArrayList<>();
        DefaultSubMenu firstSubmenu=null;
        DefaultMenuItem item=null;
        List<IsRolesMenu> listRolMenu = isRolesMenuFacade.findByEstadoOrdenRol(usuario.getIdRoles().getIdRoles());
        for (IsRolesMenu menu : listRolMenu) {
            if(menu.getIdMenu().getIdMenuPadre()==null)
                listaMenuPadre.add(menu.getIdMenu());
            else
                listaMenuHijo.add(menu.getIdMenu());
        }
        for (IsMenu isMenu1 : listaMenuPadre) {
            firstSubmenu = new DefaultSubMenu(isMenu1.getNombre());
            firstSubmenu.setIcon(isMenu1.getIcono());
            for (IsMenu isMenu2 : listaMenuHijo) {
                if(Objects.equals(isMenu1.getIdMenu(), isMenu2.getIdMenuPadre()))
                {
                    item = new DefaultMenuItem(isMenu2.getNombre());
                    item.setUrl("/" +propParam.getProperty("nombreProyecto")+ isMenu2.getUrl());
                    item.setIcon(isMenu2.getIcono());
                    firstSubmenu.addElement(item);
                }
            }
            model.addElement(firstSubmenu);
        }
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public IsUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(IsUsuarios usuario) {
        this.usuario = usuario;
    }

    public IsMenu getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(IsMenu isMenu) {
        this.isMenu = isMenu;
    }
    
}