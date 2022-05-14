package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispAntecedentes;
import com.Entity.DispDetalleDiagnostico;
import com.Entity.DispDiagnostico;
import com.Entity.DispResultado;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispAntecedentesFacade;
import com.Session.DispDetalleDiagnosticoFacade;
import com.Session.DispDiagnosticoFacade;
import com.Session.DispResultadoFacade;
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
import org.primefaces.model.LazyDataModel;

@Named("editarDiagnosticoMB")
@ViewScoped
public class EditarDiagnosticoMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispDiagnosticoFacade dispDiagnosticoFacade;

    @EJB
    DispDetalleDiagnosticoFacade dispDetalleDiagnosticoFacade;

    @EJB
    DispResultadoFacade dispResultadoFacade;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

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

    private SimpleDateFormat objSDFOnlyDay = new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat objSDFOnlyHour = new SimpleDateFormat("HH:mm:ss");

    private DispAgendamiento dispAgendamiento;

    private IsUsuarios usuario;

    private DispDiagnostico dispDiagnostico = new DispDiagnostico();

    private DispDetalleDiagnostico dispDetalleDiagnostico = new DispDetalleDiagnostico();

    private DispResultado dispResultado = new DispResultado();

    private DispAntecedentes dispAntecedentes;

    private List<DispAntecedentes> listDispAntecedentes = new ArrayList<>();

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    private String labelMant;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private LazyDataModel<DispAntecedentes> lazyDispAntecedentes;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    private String timeZone = "";

    @PostConstruct
    public void init() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
            this.dispDetalleDiagnostico = (DispDetalleDiagnostico) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dispDetalleDiagnostico");
            if (this.dispDetalleDiagnostico.getIdDetalleDiagnostico() != null) {
                this.dispDiagnostico = this.dispDetalleDiagnostico.getIdDiagnostico();
                this.dispResultado = this.dispDetalleDiagnostico.getIdResultado();
                this.dispAgendamiento = this.dispResultado.getIdAgendamiento();
            }
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
            Logger.getLogger(EditarDiagnosticoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public String mostrarNombrePaciente(DispAgendamiento agendamiento) {
        String nombre = "";
        if (agendamiento != null) {
            nombre = agendamiento.getIdCliente().getApaterno() + " " + agendamiento.getIdCliente().getAmaterno() + " " + agendamiento.getIdCliente().getNombre();
        }
        return nombre;
    }

    public String generarCodigoDiagnostico(DispAgendamiento agendamiento) {
        String codigo = "";
        try {
            if (agendamiento != null) {
                codigo = agendamiento.getIdMedicoPersonal().getIdEspecialidad().getCodigo() + agendamiento.getIdMedicoPersonal().getApaterno().substring(0, 2).toUpperCase() + agendamiento.getIdMedicoPersonal().getAmaterno().substring(0, 2).toUpperCase() + agendamiento.getIdMedicoPersonal().getNombre().substring(0, 2).toUpperCase();
                this.dispDiagnostico.setCodigo(codigo);
            }
        } catch (Exception exception) {
        }
        return codigo;
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispDiagnostico.getIdDiagnostico() != null) {
            guardar = false;
        }
        try {
            if (this.dispDiagnostico.getEnfermedad() != null && !this.dispDiagnostico.getEnfermedad().isEmpty()) {
                if (this.dispResultado.getMotivoConsulta() != null && !this.dispResultado.getMotivoConsulta().isEmpty()) {
                    this.userTransaction.begin();
                    if (!guardar) {
                        this.dispDiagnostico.setUsuarioModificacion(this.usuario.getUsuario());
                        this.dispDiagnostico.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                        this.dispDiagnosticoFacade.editWithValidator(this.dispDiagnostico);
                        this.dispDiagnosticoFacade.flush();
                        this.dispResultado.setUsuarioModificacion(this.usuario.getUsuario());
                        this.dispResultado.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                        this.dispResultadoFacade.editWithValidator(this.dispResultado);
                        this.dispResultadoFacade.flush();
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                        this.userTransaction.commit();
                        redireccionarHistorialPaciente();
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese el motivo de la consulta.");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese la enfermedad.");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            this.userTransaction.rollback();
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void redireccionarAntecedentes() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispCliente", this.dispAgendamiento.getIdCliente());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_antecedentes_paciente.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(RegistroDiagnosticoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void redireccionarHistorialPaciente() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_historial_paciente.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(EditarDiagnosticoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public DispAgendamiento getDispAgendamiento() {
        return this.dispAgendamiento;
    }

    public void setDispAgendamiento(DispAgendamiento dispAgendamiento) {
        this.dispAgendamiento = dispAgendamiento;
    }

    public DispDiagnostico getDispDiagnostico() {
        return this.dispDiagnostico;
    }

    public void setDispDiagnostico(DispDiagnostico dispDiagnostico) {
        this.dispDiagnostico = dispDiagnostico;
    }

    public DispDetalleDiagnostico getDispDetalleDiagnostico() {
        return this.dispDetalleDiagnostico;
    }

    public void setDispDetalleDiagnostico(DispDetalleDiagnostico dispDetalleDiagnostico) {
        this.dispDetalleDiagnostico = dispDetalleDiagnostico;
    }

    public DispResultado getDispResultado() {
        return this.dispResultado;
    }

    public void setDispResultado(DispResultado dispResultado) {
        this.dispResultado = dispResultado;
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
