/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsUsuarios;
import com.Session.IsUsuariosFacade;
import com.integrasystemsonline.Utilidades.Aes;
import com.integrasystemsonline.Utilidades.SMTPConfig;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author user
 */
@Named
@ViewScoped
public class CambiarClaveMB implements Serializable {

    /**
     * Creates a new instance of CambiarClaveMB
     */
    public CambiarClaveMB() {
    }
    
    private String usuario; 
    private String email;
    private String claveAnterior;
    private String claveNueva;
    private String confirmarClave;
    private boolean logeado;
    private IsUsuarios isUsuario;
    String keyaes="",ivaes="";
    Properties propParam=Utilidades.obtenerProperties("MessageResources.properties");
    
    @EJB
    IsUsuariosFacade isUsuariosFacade;
    
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    public void cambiar() throws Exception{
        FacesMessage msg = null;
            try {
                keyaes=propParam.getProperty("keyaes");
                ivaes=propParam.getProperty("ivaes");
            }catch(Exception e){}
        String detalle="",mensaje="",claveGenerada="",passEncriptado="";
        usuario=(usuario!=null?usuario.toUpperCase():"");
        isUsuario=isUsuariosFacade.findByUsuarioCorreo(usuario,email);
        
        if (isUsuario!=null && usuario != null && usuario.equals(isUsuario.getUsuario()) && email != null && email.equals(isUsuario.getCorreo())) {
            claveGenerada=Utilidades.generarClave();
            passEncriptado = Aes.encrypt(keyaes, ivaes, claveGenerada);
            isUsuario.setClave(passEncriptado);
            isUsuario.setUsuarioModificacion(isUsuario.getUsuario());
            isUsuario.setFechaModificacion(objSDF.parse(objSDF.format(new java.util.Date())));  
            isUsuariosFacade.editWithValidator(isUsuario);
            isUsuariosFacade.flush();
            detalle = Utilidades.plantillaCambioClave(isUsuario.getUsuario(),claveGenerada);
            mensaje = Utilidades.plantillas(isUsuario.getNombres()+" "+isUsuario.getApellidos(), detalle, "", "de que se ha cambiado la clave con exito");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cambio contraseña", "Su nueva contraseña ha sido generada y enviada al correo.");
        } else {
          logeado = false;
          msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Cambio contraseña Error", "El usuario o correo no es valido");
        }
        
        if(!mensaje.equals(""))
            {   
                SMTPConfig smtpConfig = new SMTPConfig();
                smtpConfig.sendMail("Mensaje del Sistema", mensaje, isUsuario.getCorreo());
            }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().addCallbackParam("estaLogeado", logeado);
        if (logeado)
            PrimeFaces.current().ajax().addCallbackParam("view", "/" + propParam.getProperty("nombreProyecto")+"/");
    }

    public void actualizar() throws Exception{
        FacesMessage msg = null;
        String detalle="",mensaje="",passEncriptado="";
        
        String keyaes="",ivaes="";
            try {
                keyaes=propParam.getProperty("keyaes");
                ivaes=propParam.getProperty("ivaes");
            }catch(Exception e){}
        isUsuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        String passDesencriptado=Aes.decrypt(keyaes, ivaes, isUsuario.getClave() );    
        
        if (claveAnterior != null && claveAnterior.equals(passDesencriptado) && claveNueva != null && !claveNueva.equals(passDesencriptado) && claveNueva.equals(confirmarClave) ) {
                     
            passEncriptado = Aes.encrypt(keyaes, ivaes, claveNueva);
            isUsuario.setClave(passEncriptado);
            isUsuario.setUsuarioModificacion(isUsuario.getUsuario());
            isUsuario.setFechaModificacion(objSDF.parse(objSDF.format(new java.util.Date())));  
            isUsuariosFacade.editWithValidator(isUsuario);
            isUsuariosFacade.flush();
            detalle = Utilidades.plantillaCambioClave(isUsuario.getUsuario(),claveNueva);
            mensaje = Utilidades.plantillas(isUsuario.getNombres()+" "+isUsuario.getApellidos(), detalle, "", "de que se ha cambiado la clave con exito");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cambio contraseña", "Su contraseña ha sido cambiada.");
        } else {
          logeado = false;
          msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Cambio contraseña Error", "Contraseña anterior no valida o "
                  + "nueva contraseña igual a la anterior");
        }
        if(!mensaje.equals(""))
            {   
                SMTPConfig smtpConfig = new SMTPConfig();
                smtpConfig.sendMail("Mensaje del Sistema", mensaje, isUsuario.getCorreo());
            }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().addCallbackParam("estaLogeado", logeado);
        if (logeado)
            PrimeFaces.current().ajax().addCallbackParam("view", "/" + propParam.getProperty("nombreProyecto")+"/");
    }
    
    public void cancelar(){
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../faces/seguridades/main.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public IsUsuarios getIsUsuario() {
        return isUsuario;
    }

    public void setIsUsuario(IsUsuarios isUsuario) {
        this.isUsuario = isUsuario;
    }

    public String getClaveAnterior() {
        return claveAnterior;
    }

    public void setClaveAnterior(String claveAnterior) {
        this.claveAnterior = claveAnterior;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }
    
}