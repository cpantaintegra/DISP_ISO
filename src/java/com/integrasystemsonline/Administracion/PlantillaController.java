/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsUsuarios;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

/**
 *
 * @author user
 */
@Named
@SessionScoped
public class PlantillaController implements Serializable {
    static IsUsuarios usuarioSession;
    /**
     * Creates a new instance of PlantillaController
     */
    public void verificarSession()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            IsUsuarios usuario = (IsUsuarios) context.getExternalContext().getSessionMap().get("usuario");
            //IsUsuarios usuario=LoginMB.usuario;
            usuarioSession=usuario;
            
            if(usuario==null)
            {
                System.out.println("Bloqueo");
                context.getExternalContext().redirect("./../seguridades/permisosLogin.xhtml");
            }else
            {
                System.out.println("No Bloqueo"+usuario.getApellidos());
            }
        }catch(IOException e)
        {try 
            {
                context.getExternalContext().redirect("./../seguridades/permisosLogin.xhtml");
            }catch (IOException ex) {
                Logger.getLogger(PlantillaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

