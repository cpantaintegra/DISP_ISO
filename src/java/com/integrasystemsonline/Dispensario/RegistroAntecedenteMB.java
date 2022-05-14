package com.integrasystemsonline.Dispensario;

import com.Entity.DispAntecedentes;
import com.Entity.DispCliente;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAntecedentesFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Named("registroAntecedenteMB")
@ViewScoped
public class RegistroAntecedenteMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispAntecedentesFacade dispAntecedentesFacade;

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

    private DispCliente dispCliente;

    private IsUsuarios usuario;

    private DispAntecedentes dispAntecedentes = new DispAntecedentes();

    private List<DispAntecedentes> listDispAntecedentes = new ArrayList<>();

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    private String labelMant;

    private List<IsRolesPermisos> listIsRolesPermisos;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    private String timeZone = "";

    @PostConstruct
    public void init() {
        try {
            this.dispCliente = (DispCliente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dispCliente");
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
            this.listIsRolesPermisos = this.isRolesPermisosFacade.findByRol(this.usuario.getIdRoles());
            String eli = "", edi = "", con = "", ing = "";
            try {
                eli = this.propParam.getProperty("eliminar");
                edi = this.propParam.getProperty("editar");
                con = this.propParam.getProperty("consultar");
                ing = this.propParam.getProperty("ingresar");
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
        } catch (Exception ex) {
            Logger.getLogger(RegistroAntecedenteMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispAntecedentes.getIdAntecedentes() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (guardar) {
                this.dispAntecedentes.setIdCliente(this.dispCliente);
                this.dispAntecedentes.setEstado("A");
                this.dispAntecedentes.setIdEmpresa(this.usuario.getIdEmpresa());
                this.dispAntecedentes.setIdCiudad(this.usuario.getIdCiudad());
                this.dispAntecedentes.setIdSector(this.usuario.getIdSector());
                this.dispAntecedentes.setUsuarioIngreso(this.usuario.getUsuario());
                this.dispAntecedentes.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispAntecedentesFacade.createWithValidator(this.dispAntecedentes);
                this.dispAntecedentesFacade.flush();
            } else {
                this.dispAntecedentes.setUsuarioModificacion(this.usuario.getUsuario());
                this.dispAntecedentes.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispAntecedentesFacade.editWithValidator(this.dispAntecedentes);
                this.dispAntecedentesFacade.flush();
            }
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public DispCliente getDispCliente() {
        return this.dispCliente;
    }

    public void setDispCliente(DispCliente dispCliente) {
        this.dispCliente = dispCliente;
    }

    public DispAntecedentes getDispAntecedentes() {
        return this.dispAntecedentes;
    }

    public void setDispAntecedentes(DispAntecedentes dispAntecedentes) {
        this.dispAntecedentes = dispAntecedentes;
    }

    public String getLabelMant() {
        return this.labelMant;
    }

    public void setLabelMant(String labelMant) {
        this.labelMant = labelMant;
    }

    public List<DispAntecedentes> getListDispAntecedentes() {
        return this.listDispAntecedentes;
    }

    public void setListDispAntecedentes(List<DispAntecedentes> listDispAntecedentes) {
        this.listDispAntecedentes = listDispAntecedentes;
    }

    public boolean isEditar() {
        return this.editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
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
}
