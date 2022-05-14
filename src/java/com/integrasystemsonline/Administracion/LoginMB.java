/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsUsuarios;
import com.Session.IsUsuariosFacade;
import com.integrasystemsonline.Utilidades.Aes;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

/**
 *
 * @author user
 */
@Named(value = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

    @EJB
    private IsUsuariosFacade isUsuariosFacade;
    private String dummyEmail;
    private String dummyPassword;
    private boolean logeado = false;
    private IsUsuarios usuario;
    @Resource
    UserTransaction userTransaction;
    /**
     * Creates a new instance of LoginMB
     */
    @PostConstruct
    public void init() {
    
    }
    
    public LoginMB() {
    }
    
    public void login() {
        try {
            FacesMessage msg = null;
            String keyaes="",ivaes="";
            Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
            try {
                keyaes=propParam.getProperty("keyaes");
                ivaes=propParam.getProperty("ivaes");
            }catch(Exception e){}
            String passEncriptado = Aes.encrypt(keyaes, ivaes, dummyPassword);
            userTransaction.begin();
            usuario = isUsuariosFacade.findByUsuarioClave(dummyEmail, passEncriptado);
            if (usuario!=null) {          
                usuario.setIntentos(null);
                isUsuariosFacade.editWithValidator(usuario);
                isUsuariosFacade.flush();                
                logeado = true;
                
                File directorio = new File("C:/archivoAula");
                if (!directorio.exists()) {
                    if (directorio.mkdirs()) {
                        System.out.println("Directorio creado");
                    } else {
                        System.out.println("Error al crear directorio");
                    }
                }
                else{
                    File subDirectorio = new File("C:/archivoAula/Logotipos");
                    
                    if(!subDirectorio.exists()){
                        if (subDirectorio.mkdirs()) {
                            System.out.println("Directorio creado para logos");
                        } else {
                            System.out.println("Error al crear directorio");
                        }
                    }
                }
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", dummyEmail);
            } else {
                logeado = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                        "Credenciales no válidas");
                usuario = isUsuariosFacade.findUser(dummyEmail.toUpperCase());
                
                if(usuario.getIntentos()==null){
                    usuario.setIntentos(0);
                }
                usuario.setIntentos(usuario.getIntentos()+1); 
                isUsuariosFacade.editWithValidator(usuario);
                isUsuariosFacade.flush();               
            }            
            
            PrimeFaces.current().ajax().addCallbackParam("estaLogeado", logeado);
            if (logeado)
            {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                System.out.println("Se ha logeado faces/seguridades/main.xhtml");
                PrimeFaces.current().ajax().addCallbackParam("view", "faces/seguridades/main.xhtml");
            }
            
            if(usuario.getIntentos()!=null && usuario.getIntentos()>=3){
                usuario.setEstado("I");
                isUsuariosFacade.editWithValidator(usuario);
                isUsuariosFacade.flush();
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario Bloqueado", "Supero el numero de intentos\npor favor contacte al administrador del sistema");
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
            userTransaction.commit();
        }catch(Exception ex){
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logout()
    {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", null);
            context.getExternalContext().redirect("./../../");
        } catch (IOException ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public String getDummyEmail() {
        return dummyEmail;
    }

    public void setDummyEmail(String dummyEmail) {
        this.dummyEmail = dummyEmail;
    }

    public String getDummyPassword() {
        return dummyPassword;
    }

    public void setDummyPassword(String dummyPassword) {
        this.dummyPassword = dummyPassword;
    }    
}