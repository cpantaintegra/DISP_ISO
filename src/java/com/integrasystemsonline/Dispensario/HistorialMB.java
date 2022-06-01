package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispAntecedentes;
import com.Entity.DispCliente;
import com.Entity.DispDetalleDiagnostico;
import com.Entity.DispDetalleReceta;
import com.Entity.DispDiagnostico;
import com.Entity.DispEstudiosMedicos;
import com.Entity.DispExamen;
import com.Entity.DispMedicamento;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispReceta;
import com.Entity.DispResultado;
import com.Entity.DispSolicitudExamen;
import com.Entity.DispTriaje;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispAntecedentesFacade;
import com.Session.DispClienteFacade;
import com.Session.DispDetalleDiagnosticoFacade;
import com.Session.DispDetalleRecetaFacade;
import com.Session.DispEstudiosMedicosFacade;
import com.Session.DispExamenFacade;
import com.Session.DispMedicamentoFacade;
import com.Session.DispRecetaFacade;
import com.Session.DispSolicitudExamenFacade;
import com.Session.DispTriajeFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.Session.IsUsuariosFacade;
import com.integrasystemsonline.Utilidades.Aes;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyAntecedentesModel;
import com.integrasystemsonline.Utilidades.LazyDetalleDiagnosticoModel;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.PrimeFaces;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("historial")
@ViewScoped
public class HistorialMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispAntecedentesFacade dispAntecedentesFacade;

    @EJB
    DispClienteFacade dispClienteFacade;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    DispDetalleDiagnosticoFacade dispDetalleDiagnosticoFacade;

    @EJB
    DispTriajeFacade dispTriajeFacade;

    @EJB
    IsEmpresaFacade isEmpresaFacade;

    @EJB
    IsCiudadFacade isCiudadFacade;

    @EJB
    IsSectorFacade isSectorFacade;

    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;

    @EJB
    IsUsuariosFacade isUsuariosFacade;

    @EJB
    DispDetalleRecetaFacade dispDetalleRecetaFacade;

    @EJB
    DispRecetaFacade dispRecetaFacade;

    @EJB
    IsParametrosFacade isParametrosFacade;

    @EJB
    DispMedicamentoFacade dispMedicamentoFacade;

    @EJB
    DispExamenFacade dispExamenFacade;
    
    @EJB
    DispSolicitudExamenFacade dispSolicitudExamenFacade;
    
    @EJB
    DispEstudiosMedicosFacade dispEstudiosMedicosFacade;
    
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<DispCliente> listDispCliente = new ArrayList<>();

    private DispCliente ClienteObj = new DispCliente();

    private DispAntecedentes dispAntecedentes = new DispAntecedentes();

    private List<DispAntecedentes> listDispAntecedentes = new ArrayList<>();

    private UIData dataTable;

    private LazyDataModel<DispDetalleDiagnostico> lazyDispDetalleDiagnostico;
    private LazyDataModel<DispAntecedentes> lazyDispAntecedentes;
    private String filtroConsulta, filtroConsultaAntecedentes;
    IsUsuarios usuario;
    private String estado;
    private String labelMant,labelMantAntecedentes;
    private List<Estado> listaEstado;
    private Estado estadoObj;
    private List<IsRolesPermisos> listIsRolesPermisos;
    private boolean editar = true;
    private boolean eliminarBl = true;
    private boolean consultar = true;
    private boolean ingresar = true;
    private int rowsAntecedentes;
    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
    private DispDetalleDiagnostico dispDetalleDiagnostico;
    private DispResultado dispResultado;
    private DispAgendamiento dispAgendamiento;
    private DispTriaje dispTriaje;
    private List<DispAgendamiento> listAgendamiento = new ArrayList<>();
    private List<DispResultado> listResultado = new ArrayList<>();
    private List<DispTriaje> listTriaje = new ArrayList<>();
    private List<DispDetalleDiagnostico> listDetalleDiagnostico = new ArrayList<>();
    private DispDiagnostico dispDiagnostico = new DispDiagnostico();
    private boolean verHistorial = true;
    private IsUsuarios usuarioObj = new IsUsuarios();
    private String strUsuario;
    private String password;
    private DispReceta receta = new DispReceta();
    private List<DispDetalleReceta> listDetalleReceta = new ArrayList<>();
    private String timeZone = "";
    private String limiteEdicion = "";
    private Date hoy = new Date();
    private DispCliente cliente = new DispCliente();
    private DispResultado resultadoReceta = new DispResultado();
    private List<DispMedicamento> listDispMedicamento = new ArrayList<>();
    private DispMedicamento dispMedicamento = new DispMedicamento();
    private String medicamento;
    List<DispExamen> listDispExamen = new ArrayList<>();
    List<DispEstudiosMedicos> listDispEstudiosMedicos = new ArrayList<>();
    private boolean ayuno = false;
    private boolean vejigaLlena = false;
    List<DispExamen> listDispExamenesSeleccionados = new ArrayList<>();
    DispSolicitudExamen dispSolicitudExamen = new DispSolicitudExamen();
    List<DispSolicitudExamen> lstDispSolicitudExamen = new ArrayList<>();
    List<String> medicamentosList = new ArrayList<>();
    private int idDetalleRecetaTemp = 0;
    private boolean boolImprimirOrden = false;
    private String comentario = "";
    
    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            this.estado = "A";
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
            isParametros = this.isParametrosFacade.findByCodigo("limiteEdicion", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.limiteEdicion = isParametros.getValor();
            this.listDispCliente = this.dispClienteFacade.findAllHistorialActivo(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            if (!this.listDispCliente.isEmpty()) {
                this.ClienteObj = this.listDispCliente.get(0);
            }
            this.labelMant = "Ingresar";
            this.listaEstado = new ArrayList<>();
            Estado estado = new Estado();
            estado.setValor("A");
            estado.setDetalle("Activo");
            this.estadoObj = estado;
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("I");
            estado.setDetalle("Inactivo");
            this.listaEstado.add(estado);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean tieneordenExamen(DispDetalleDiagnostico detalleDiagnostico) {
        try {
            if (detalleDiagnostico.getIdDetalleDiagnostico() != null) {
                int idAgendamiento = detalleDiagnostico.getIdResultado().getIdAgendamiento().getIdAgendamiento();
                lstDispSolicitudExamen = this.dispSolicitudExamenFacade.findByIdAgendamiento(idAgendamiento);
                
                if (this.lstDispSolicitudExamen.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }
    }
    
    public boolean tieneReceta(DispDetalleDiagnostico detalleDiagnostico) {
        try {
            if (detalleDiagnostico.getIdDetalleDiagnostico() != null) {
                int idAgendamiento = detalleDiagnostico.getIdResultado().getIdAgendamiento().getIdAgendamiento();
                this.receta = this.dispRecetaFacade.findByIdAgendamiento(idAgendamiento);
                if (this.receta == null) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }
    }

    public String calcularEdadPaciente(DispDetalleDiagnostico detalleDiagnostico) {
        long diff = 0L;
        long years = 0L;
        try {
            if (detalleDiagnostico.getIdDetalleDiagnostico() != null) {
                Date fechaNac = detalleDiagnostico.getIdCliente().getFechaNacimiento();
                Date hoy = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
                diff = fechaNac.getTime() - hoy.getTime();
                years = diff / 1039228928L;
            }
        } catch (Exception e) {
            return "0 años";
        }
        return String.valueOf(years) + " años";
    }

    public List<DispExamen> cargarListaExamen(DispEstudiosMedicos estudios){
        listDispExamen = new ArrayList<>();
        try {
            listDispExamen = dispExamenFacade.findByIdEstudiosMedicos(estudios.getIdEstudiosMedicos());
            if(!listDispExamenesSeleccionados.isEmpty()){
                for (int i = 0; i <listDispExamen.size(); i++) {
                    if(listDispExamenesSeleccionados.contains(listDispExamen.get(i))){
                        listDispExamen.get(i).setExamenAgregado(true);
                    }
                    else{
                        listDispExamen.get(i).setExamenAgregado(false);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return listDispExamen;
    }
    
    public void agregarExamenesSeleccionados(DispExamen examen){
        try {
            if(examen.getIdExamen()!=null){
                if(listDispExamenesSeleccionados.contains(examen)){
                    listDispExamenesSeleccionados.remove(examen);
                }
                else{
                    listDispExamenesSeleccionados.add(examen);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void nuevaOrden(DispDetalleDiagnostico detalleDiagnostico){
        try {
            comentario = "";
            ayuno = false;
            vejigaLlena = false;
            boolImprimirOrden = false;
            dispSolicitudExamen = new DispSolicitudExamen();
            listDispExamenesSeleccionados = new ArrayList<>();
            dispAgendamiento = detalleDiagnostico.getIdResultado().getIdAgendamiento();
            listDispEstudiosMedicos = dispEstudiosMedicosFacade.findByIdEspecialidad(dispAgendamiento.getIdEspecialidad().getIdEspecialidad());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public boolean ordenAgregada(DispExamen examen){
        try {
            if(listDispExamenesSeleccionados.contains(examen)){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public void cargarOrdenes(DispDetalleDiagnostico detalleDiagnostico) {
        try {
            this.hoy = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
            this.labelMant = "Actualizar";
            if (detalleDiagnostico.getIdDetalleDiagnostico() != null) {
                dispAgendamiento = detalleDiagnostico.getIdResultado().getIdAgendamiento();
                this.lstDispSolicitudExamen = this.dispSolicitudExamenFacade.findByIdAgendamiento(dispAgendamiento.getIdAgendamiento());
                if(!lstDispSolicitudExamen.isEmpty()){
                    comentario = lstDispSolicitudExamen.get(0).getComentario();
                    ayuno = lstDispSolicitudExamen.get(0).getAyuno();
                    vejigaLlena = lstDispSolicitudExamen.get(0).getVejigaLlena();
                }
                listDispExamenesSeleccionados = new ArrayList<>();
                for (int i = 0; i < lstDispSolicitudExamen.size(); i++) {
                    agregarExamenesSeleccionados(lstDispSolicitudExamen.get(i).getIdExamen());
                }
                listDispEstudiosMedicos = new ArrayList<>();
                listDispEstudiosMedicos = dispEstudiosMedicosFacade.findByIdEspecialidad(dispAgendamiento.getIdEspecialidad().getIdEspecialidad());
                //listDispEstudiosMedicos = dispEstudiosMedicosFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                
                
                
                //listDispEstudiosMedicos = dispEstudiosMedicosFacade.findByIdEspecialidad(dispAgendamiento.getIdEspecialidad().getIdEspecialidad());
            }
            boolImprimirOrden = true;
        } catch (Exception exception) {
        }
    }
    
    public void imprimirOrden(){
        try {
            if(dispAgendamiento!=null){
                if(dispAgendamiento.getIdAgendamiento()!=null){
                    printSolicitudExamen(dispAgendamiento.getIdAgendamiento());
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void generarOrdenes() throws SystemException{
        FacesMessage msg = null;
        try {
            userTransaction.begin();
            List<DispSolicitudExamen> lstSolicitud = dispSolicitudExamenFacade.findByIdAgendamiento(dispAgendamiento.getIdAgendamiento());
            for (int i = 0; i < lstSolicitud.size(); i++) {
                dispSolicitudExamenFacade.remove(lstSolicitud.get(i));
                dispSolicitudExamenFacade.flush();
            }
            
            for (int i = 0; i < listDispExamenesSeleccionados.size(); i++) {
                dispSolicitudExamen = new DispSolicitudExamen();
                dispSolicitudExamen.setAyuno(ayuno);
                dispSolicitudExamen.setVejigaLlena(vejigaLlena);
                dispSolicitudExamen.setComentario(comentario);
                dispSolicitudExamen.setFecha(objSDF.parse(objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                dispSolicitudExamen.setEstado("A");
                dispSolicitudExamen.setIdAgendamiento(dispAgendamiento);
                dispSolicitudExamen.setIdExamen(listDispExamenesSeleccionados.get(i));
                dispSolicitudExamen.setIdEmpresa(this.usuario.getIdEmpresa());
                dispSolicitudExamen.setIdCiudad(this.usuario.getIdCiudad());
                dispSolicitudExamen.setIdSector(this.usuario.getIdSector());
                dispSolicitudExamen.setUsuarioIngreso(this.usuario.getUsuario());
                dispSolicitudExamen.setFechaIngreso(objSDF.parse(objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                dispSolicitudExamenFacade.createWithValidator(dispSolicitudExamen);
                dispSolicitudExamenFacade.flush();
            }
            printSolicitudExamen(dispAgendamiento.getIdAgendamiento());
            userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            userTransaction.rollback();
        }
        
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void printSolicitudExamen(int idAgendamiento) {
        FacesMessage msg = null;
        try {
            if (idAgendamiento > 0) {
                PrimeFaces.current().executeScript("window.open('../ServletOrden?agendamientoID=" + idAgendamiento + "');");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ImpresiFallido", "Favor comunicarse con el administrador del Sistema.");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.toString());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void nuevaReceta(DispDetalleDiagnostico detalleDiagnostico){
        try {
            receta = new DispReceta();
            this.labelMant = "Ingresar";
            listDetalleReceta = new ArrayList<>();
            medicamento = "";
            dispMedicamento = null;
            dispAgendamiento = detalleDiagnostico.getIdResultado().getIdAgendamiento();
            listDispExamenesSeleccionados = new ArrayList<>();
            this.listDispMedicamento = this.dispMedicamentoFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        } catch (Exception e) {
        }
    }
    
    public void cargarReceta(DispDetalleDiagnostico detalleDiagnostico) {
        try {
            this.hoy = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
            this.labelMant = "Actualizar";
            if (detalleDiagnostico.getIdDetalleDiagnostico() != null) {
                int idAgendamiento = detalleDiagnostico.getIdResultado().getIdAgendamiento().getIdAgendamiento();
                this.receta = this.dispRecetaFacade.findByIdAgendamiento(idAgendamiento);
                this.dispAgendamiento = this.receta.getIdAgendamiento();
                this.listDetalleReceta = this.dispDetalleRecetaFacade.findByIdReceta(this.receta.getIdReceta());
                this.cliente = detalleDiagnostico.getIdCliente();
                this.resultadoReceta = detalleDiagnostico.getIdResultado();
                this.listDispMedicamento = this.dispMedicamentoFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                List<DispDetalleReceta> collDispDetalleRecetas = this.dispDetalleRecetaFacade.findByIdReceta(this.receta.getIdReceta());
                for (DispDetalleReceta collDispDetalleReceta : collDispDetalleRecetas) {
                    this.listDispMedicamento.remove(collDispDetalleReceta.getIdMedicamento());
                }
            }
        } catch (Exception exception) {
        }
    }

    public void cargarAntecedentes(DispDetalleDiagnostico detalleDiagnostico) {
        try {
            if (detalleDiagnostico.getIdDetalleDiagnostico() != null) {
                this.cliente = detalleDiagnostico.getIdCliente();
                lazyDispAntecedentes = null;
                getAllAntecedentes();
                //listDispAntecedentes = dispAntecedentesFacade.findByIdCliente(cliente.getIdCliente());
            }
        } catch (Exception exception) {
        }
    }

    public void handleComboCliente() throws Exception {
        this.lazyDispDetalleDiagnostico = null;
        getAll();
    }

    public String nombreMedico(DispMedicoPersonal medico) {
        String nombre = "";
        try {
            nombre = medico.getApaterno().toUpperCase() + " " + medico.getAmaterno().toUpperCase() + " " + medico.getNombre().toUpperCase();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return nombre;
    }

    public String nombreCliente() {
        String nombre = "";
        try {
            nombre = this.cliente.getApaterno().toUpperCase() + " " + this.cliente.getAmaterno().toUpperCase() + " " + this.cliente.getNombre().toUpperCase();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return nombre;
    }

    public void redireccionar() {
        try {
            this.dispAntecedentes = new DispAntecedentes();
            this.estado = "A";
            this.labelMant = "Ingresar";
        } catch (Exception exception) {
        }
    }

    public void redireccionarAntecdentes() {
        try {
            this.dispAntecedentes = new DispAntecedentes();
            this.estado = "A";
            this.labelMantAntecedentes = "Ingresar";
        } catch (Exception exception) {
        }
    }
    
    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.dispAntecedentesFacade.remove(this.dispAntecedentes);
            this.dispAntecedentesFacade.flush();
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispDetalleDiagnostico = (DispDetalleDiagnostico) event.getObject();
            this.dispDiagnostico = this.dispDetalleDiagnostico.getIdDiagnostico();
            this.dispResultado = this.dispDetalleDiagnostico.getIdResultado();
            this.dispAgendamiento = this.dispResultado.getIdAgendamiento();
            this.listTriaje = this.dispTriajeFacade.findByIdAgendamiento(this.dispAgendamiento.getIdAgendamiento());
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRowDblClckSelectAntecedentes(SelectEvent event) {
        try {
            this.dispAntecedentes = (DispAntecedentes) event.getObject();
            this.labelMantAntecedentes = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void redireccionarEditarDiagnostico(DispDetalleDiagnostico dispDetalleDiagnostico) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispDetalleDiagnostico", dispDetalleDiagnostico);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_editar_diagnostico.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(TriajeMedicoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void llenarHistorial(SelectEvent event) {
        try {
            this.dispDetalleDiagnostico = (DispDetalleDiagnostico) event.getObject();
            //Date now = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
            redireccionarEditarDiagnostico(this.dispDetalleDiagnostico);
        } catch (Exception exception) {
        }
    }

    public void iniciarEdicion(DispDetalleDiagnostico dispDetalleDiagnostico) {
        FacesMessage msg = null;
        Integer limite = 0;

        if (dispDetalleDiagnostico.getIdDetalleDiagnostico() != null) {
            Date hasta = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
            Date fechaIngreso = Utilidades.obtenerFechaZonaHoraria(dispDetalleDiagnostico.getFechaIngreso(), "14400", this.timeZone);
            long diff = hasta.getTime() - fechaIngreso.getTime();
            long diffHours = diff / 3600000;
            if (limiteEdicion != null) {
                limite = Integer.parseInt(limiteEdicion);
            }

            if (diffHours < limite) {
                redireccionarEditarDiagnostico(dispDetalleDiagnostico);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Periodo de edicion fuera de rango");
            }
        }

        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public boolean editarHistorial(DispDetalleDiagnostico dispDetalleDiagnostico) {
        Integer limite = 4;
        Date hasta = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
        Date fechaIngreso = Utilidades.obtenerFechaZonaHoraria(dispDetalleDiagnostico.getFechaIngreso(), "0", this.timeZone);
        long diff = hasta.getTime() - fechaIngreso.getTime();
        long diffHours = diff / 3600000;
        if (limiteEdicion != null) {
            limite = Integer.parseInt(limiteEdicion);
        }

        if (dispDetalleDiagnostico.getIdDetalleDiagnostico() != null) {
            if (diffHours < limite) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean editarReceta(DispReceta dispReceta) {
        Integer limite = 4;
        Date hasta = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
        if (dispReceta != null) {
            if (dispReceta.getIdReceta() != null) {
                Date fechaIngreso = Utilidades.obtenerFechaZonaHoraria(dispReceta.getFechaIngreso(), "0", this.timeZone);
                long diff = hasta.getTime() - fechaIngreso.getTime();
                long diffHours = diff / 3600000;
                if (limiteEdicion != null) {
                    limite = Integer.parseInt(limiteEdicion);
                }

                if (diffHours < limite) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean editarAntecedentes(DispAntecedentes dispAntecedentes) {
        Integer limite = 4;
        Date hasta = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
        if (dispAntecedentes != null) {
            if (dispAntecedentes.getIdAntecedentes()!= null) {
                Date fechaIngreso = Utilidades.obtenerFechaZonaHoraria(dispAntecedentes.getFechaIngreso(), "0", this.timeZone);
                long diff = hasta.getTime() - fechaIngreso.getTime();
                long diffHours = diff / 3600000;
                if (limiteEdicion != null) {
                    limite = Integer.parseInt(limiteEdicion);
                }

                if (diffHours < limite) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    
    public boolean editarOrden(List<DispSolicitudExamen> lstDispSolicitudExamen) {
        Integer limite = 4;
        Date hasta = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
        if (!lstDispSolicitudExamen.isEmpty()) {
            Date fechaIngreso = Utilidades.obtenerFechaZonaHoraria(lstDispSolicitudExamen.get(0).getFechaIngreso(), "0", this.timeZone);
            long diff = hasta.getTime() - fechaIngreso.getTime();
            long diffHours = diff / 3600000;
            if (limiteEdicion != null) {
                limite = Integer.parseInt(limiteEdicion);
            }

            if (diffHours < limite) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    
    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.receta.getIdReceta() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (guardar) {
                if (this.dispAgendamiento != null) {
                    if (this.dispAgendamiento.getIdAgendamiento() != null) {
                        this.receta.setEstado("A");
                        this.receta.setIdAgendamiento(this.dispAgendamiento);
                        this.receta.setIdEmpresa(this.usuario.getIdEmpresa());
                        this.receta.setIdCiudad(this.usuario.getIdCiudad());
                        this.receta.setIdSector(this.usuario.getIdSector());
                        this.receta.setUsuarioIngreso(this.usuario.getUsuario());
                        this.receta.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                        this.dispRecetaFacade.createWithValidator(this.receta);
                        this.dispRecetaFacade.flush();
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                        //redireccionar();
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe seleccionar una reservacion");
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe seleccionar una reservacion");
                }
            } else {
                this.receta.setUsuarioModificacion(this.usuario.getUsuario());
                this.receta.setFechaModificacion(this.objSDF.parse(this.objSDF.format(new Date())));
                this.dispRecetaFacade.editWithValidator(this.receta);
                this.dispRecetaFacade.flush();
                Collection<DispDetalleReceta> detalleRecetas = this.dispDetalleRecetaFacade.findByIdReceta(this.receta.getIdReceta());
                for (DispDetalleReceta detalleReceta : detalleRecetas) {
                    this.dispDetalleRecetaFacade.remove(detalleReceta);
                    this.dispDetalleRecetaFacade.flush();
                }
            }
            
            for (int i = 0; i < this.listDetalleReceta.size(); i++) {
                DispDetalleReceta dispDetalleReceta = new DispDetalleReceta();
                dispDetalleReceta.setEstado("A");
                dispDetalleReceta.setIdMedicamento(this.listDetalleReceta.get(i).getIdMedicamento());
                dispDetalleReceta.setCantidad(this.listDetalleReceta.get(i).getCantidad());
                dispDetalleReceta.setDosis(this.listDetalleReceta.get(i).getDosis());
                dispDetalleReceta.setDuracion( this.listDetalleReceta.get(i).getDuracion());
                dispDetalleReceta.setIdReceta(this.receta);
                dispDetalleReceta.setIdEmpresa(this.usuario.getIdEmpresa());
                dispDetalleReceta.setIdCiudad(this.usuario.getIdCiudad());
                dispDetalleReceta.setIdSector(this.usuario.getIdSector());
                dispDetalleReceta.setUsuarioIngreso(this.usuario.getUsuario());
                dispDetalleReceta.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispDetalleRecetaFacade.createWithValidator(dispDetalleReceta);
                this.dispDetalleRecetaFacade.flush();
            }
            generarPDFReceta(this.receta.getIdReceta());
            this.userTransaction.commit();
            this.dispAgendamiento = null;
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void imprimir(DispReceta receta) {
        if (receta.getIdReceta() != null) {
            generarPDFReceta(receta.getIdReceta());
        }
    }

    public void generarPDFReceta(int idReceta) {
        FacesMessage msg = null;
        try {
            if (idReceta > 0) {
                PrimeFaces.current().executeScript("window.open('../ServletReceta?recetaID=" + idReceta + "');");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ImpresiFallido", "Favor comunicarse con el administrador del Sistema.");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.toString());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void validarCredenciales() {
        FacesMessage msg = null;
        String keyaes = "", ivaes = "";
        try {
            keyaes = this.propParam.getProperty("keyaes");
            ivaes = this.propParam.getProperty("ivaes");
            String passEncriptado = Aes.encrypt(keyaes, ivaes, this.password);
            this.usuarioObj = this.isUsuariosFacade.findByUsuarioClave(this.strUsuario, passEncriptado);
            if (this.usuarioObj != null) {
                if (this.usuarioObj.getIdRoles().getNombre().equals("OPERADOR")) {
                    redireccionarEditarDiagnostico(this.dispDetalleDiagnostico);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario " + this.strUsuario + " no posee los permisos para esta accion");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o password incorrectos");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCarDrop(DragDropEvent ddEvent) {
        DispMedicamento car = (DispMedicamento) ddEvent.getData();
        DispDetalleReceta detalleRecetaObj = new DispDetalleReceta();
        if(detalleRecetaObj.getIdDetalleReceta()==null){
            detalleRecetaObj.setIdDetalleReceta(idDetalleRecetaTemp+1);
        }
        detalleRecetaObj.setIdMedicamento(car);
        detalleRecetaObj.setDosis("");
        detalleRecetaObj.setDuracion("");
        detalleRecetaObj.setCantidad(0);
        this.listDetalleReceta.add(detalleRecetaObj);
        this.medicamentosList.remove(car.getNombre());
        this.listDispMedicamento.remove(car);
        idDetalleRecetaTemp=idDetalleRecetaTemp+1;
    }

    public void onCarDrop1(DragDropEvent ddEvent) {
        DispDetalleReceta car = (DispDetalleReceta) ddEvent.getData();
        this.listDispMedicamento.add(car.getIdMedicamento());
        this.medicamentosList.add(car.getIdMedicamento().getNombre());
        this.listDetalleReceta.remove(car);
    }

    public void agregarMedicamento(){
        FacesMessage msg = null;
        try {
            dispMedicamento = this.dispMedicamentoFacade.findByNombre(this.medicamento.toUpperCase(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            userTransaction.begin();
            if(dispMedicamento==null || dispMedicamento.getIdMedicamento()==null){
                dispMedicamento = new DispMedicamento();
                dispMedicamento.setNombre(medicamento.toUpperCase());
                dispMedicamento.setDescripcion("medicamento " + medicamento);
                dispMedicamento.setEstado("A");
                dispMedicamento.setIdEmpresa(this.usuario.getIdEmpresa());
                dispMedicamento.setIdCiudad(this.usuario.getIdCiudad());
                dispMedicamento.setIdSector(this.usuario.getIdSector());
                dispMedicamento.setIdEmpresa(this.usuario.getIdEmpresa());
                dispMedicamento.setUsuarioIngreso(this.usuario.getUsuario());
                dispMedicamento.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                dispMedicamentoFacade.createWithValidator(this.dispMedicamento);
                dispMedicamentoFacade.flush();
            }
            userTransaction.commit();
            
            if(dispMedicamento!=null){
                DispDetalleReceta detalleRecetaObj = new DispDetalleReceta();
                if(detalleRecetaObj.getIdDetalleReceta()==null){
                    detalleRecetaObj.setIdDetalleReceta(idDetalleRecetaTemp+1);
                }
                detalleRecetaObj.setIdMedicamento(dispMedicamento);
                detalleRecetaObj.setDosis("");
                detalleRecetaObj.setDuracion("");
                detalleRecetaObj.setCantidad(0);
                this.listDetalleReceta.add(detalleRecetaObj);
                this.medicamentosList.remove(dispMedicamento.getNombre());
                this.listDispMedicamento.remove(dispMedicamento);
                this.medicamento = "";
                dispMedicamento = null;
                idDetalleRecetaTemp=idDetalleRecetaTemp+1;
            }
            else{
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No se encontro el medicamento.");
            }
        } catch (Exception e) {
        }
    }
    
    public void guardarMedicamento() throws SystemException {
        FacesMessage msg = null;
        try {
            if (this.medicamento != null && !this.medicamento.isEmpty()) {
                this.userTransaction.begin();
                this.dispMedicamento = new DispMedicamento();
                this.dispMedicamento.setNombre(this.medicamento);
                this.dispMedicamento.setDescripcion("Sin descripcion");
                this.dispMedicamento.setEstado("A");
                this.dispMedicamento.setIdEmpresa(this.usuario.getIdEmpresa());
                this.dispMedicamento.setIdCiudad(this.usuario.getIdCiudad());
                this.dispMedicamento.setIdSector(this.usuario.getIdSector());
                this.dispMedicamento.setIdEmpresa(this.usuario.getIdEmpresa());
                this.dispMedicamento.setUsuarioIngreso(this.usuario.getUsuario());
                this.dispMedicamento.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispMedicamentoFacade.createWithValidator(this.dispMedicamento);
                this.dispMedicamentoFacade.flush();
                this.userTransaction.commit();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese el nombre del medicamento");
            }
            this.listDispMedicamento.add(this.dispMedicamento);
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void guardarAntecedentes() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispAntecedentes.getIdAntecedentes() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (guardar) {
                this.dispAntecedentes.setEstado("A");
                this.dispAntecedentes.setIdCliente(this.cliente);
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
            redireccionar();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<String> completeText(String query) {
        String queryUpperrCase = query.toUpperCase();
        List<DispMedicamento> medicamentos = this.dispMedicamentoFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        //listDetalleReceta = this.dispDetalleRecetaFacade.findByIdReceta(this.receta.getIdReceta());
        for (DispDetalleReceta collDispDetalleReceta : listDetalleReceta) {
            medicamentos.remove(collDispDetalleReceta.getIdMedicamento());
        }
        
        for (DispMedicamento medicamento : medicamentos) {
            if(!medicamentosList.contains(medicamento.getNombre())){
                medicamentosList.add(medicamento.getNombre());
            }
        }
        return (List<String>) medicamentosList.stream().filter(t -> t.toUpperCase().startsWith(queryUpperrCase)).collect(Collectors.toList());
    }

    public void onItemSelect(SelectEvent event) {
        FacesMessage msg = null;
        try {
            this.medicamento = (String) event.getObject();
            //this.dispMedicamento = this.dispMedicamentoFacade.findByNombre(this.medicamento.toUpperCase(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        } catch (Exception exception) {
        }
        
        if(msg!=null){
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onItemSelectOrAdd(SelectEvent event) {
        try {
            this.medicamento = (String) event.getObject();
            this.dispMedicamento = this.dispMedicamentoFacade.findByNombre(this.medicamento.toUpperCase(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            if (this.dispMedicamento == null) {
                this.userTransaction.begin();
                this.dispMedicamento = new DispMedicamento();
                this.dispMedicamento.setNombre(this.medicamento);
                this.dispMedicamento.setDescripcion("Sin descripcion");
                this.dispMedicamento.setEstado("A");
                this.dispMedicamento.setIdEmpresa(this.usuario.getIdEmpresa());
                this.dispMedicamento.setIdCiudad(this.usuario.getIdCiudad());
                this.dispMedicamento.setIdSector(this.usuario.getIdSector());
                this.dispMedicamento.setIdEmpresa(this.usuario.getIdEmpresa());
                this.dispMedicamento.setUsuarioIngreso(this.usuario.getUsuario());
                this.dispMedicamento.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispMedicamentoFacade.createWithValidator(this.dispMedicamento);
                this.dispMedicamentoFacade.flush();
                this.userTransaction.commit();
            }
        } catch (Exception exception) {
        }
    }

    public LazyDataModel<DispDetalleDiagnostico> getAll() {
        if (this.lazyDispDetalleDiagnostico == null) {
            this.lazyDispDetalleDiagnostico = new LazyDetalleDiagnosticoModel(this.ClienteObj.getIdCliente(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispDetalleDiagnostico;
    }

    public LazyDataModel<DispAntecedentes> getAllAntecedentes() {
        if (cliente != null && cliente.getIdCliente() != null) {
            if (this.lazyDispAntecedentes == null) {
                this.lazyDispAntecedentes = new LazyAntecedentesModel(this.cliente.getIdCliente(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
            }
            return this.lazyDispAntecedentes;
        } else {
            return null;
        }
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispCliente> getListDispCliente() {
        return this.listDispCliente;
    }

    public void setListDispCliente(List<DispCliente> listDispCliente) {
        this.listDispCliente = listDispCliente;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFiltroConsulta() {
        return this.filtroConsulta;
    }

    public void setFiltroConsulta(String filtroConsulta) {
        this.filtroConsulta = filtroConsulta;
    }

    public DispCliente getClienteObj() {
        return this.ClienteObj;
    }

    public void setClienteObj(DispCliente ClienteObj) {
        this.ClienteObj = ClienteObj;
    }

    public IsUsuarios getUsuario() {
        return this.usuario;
    }

    public void setUsuario(IsUsuarios usuario) {
        this.usuario = usuario;
    }

    public String getLabelMant() {
        return this.labelMant;
    }

    public void setLabelMant(String labelMant) {
        this.labelMant = labelMant;
    }

    public List<Estado> getListaEstado() {
        return this.listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public Estado getEstadoObj() {
        return this.estadoObj;
    }

    public void setEstadoObj(Estado estadoObj) {
        this.estadoObj = estadoObj;
    }

    public List<IsRolesPermisos> getListIsRolesPermisos() {
        return this.listIsRolesPermisos;
    }

    public void setListIsRolesPermisos(List<IsRolesPermisos> listIsRolesPermisos) {
        this.listIsRolesPermisos = listIsRolesPermisos;
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

    public boolean isEditar() {
        return this.editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public DispAntecedentes getDispAntecedentes() {
        return this.dispAntecedentes;
    }

    public void setDispAntecedentes(DispAntecedentes dispAntecedentes) {
        this.dispAntecedentes = dispAntecedentes;
    }

    public List<DispAntecedentes> getListDispAntecedentes() {
        return this.listDispAntecedentes;
    }

    public void setListDispAntecedentes(List<DispAntecedentes> listDispAntecedentes) {
        this.listDispAntecedentes = listDispAntecedentes;
    }

    public int getRowsAntecedentes() {
        return this.rowsAntecedentes;
    }

    public void setRowsAntecedentes(int rowsAntecedentes) {
        this.rowsAntecedentes = rowsAntecedentes;
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

    public DispAgendamiento getDispAgendamiento() {
        return this.dispAgendamiento;
    }

    public void setDispAgendamiento(DispAgendamiento dispAgendamiento) {
        this.dispAgendamiento = dispAgendamiento;
    }

    public DispTriaje getDispTriaje() {
        return this.dispTriaje;
    }

    public void setDispTriaje(DispTriaje dispTriaje) {
        this.dispTriaje = dispTriaje;
    }

    public List<DispAgendamiento> getListAgendamiento() {
        return this.listAgendamiento;
    }

    public void setListAgendamiento(List<DispAgendamiento> listAgendamiento) {
        this.listAgendamiento = listAgendamiento;
    }

    public List<DispResultado> getListResultado() {
        return this.listResultado;
    }

    public void setListResultado(List<DispResultado> listResultado) {
        this.listResultado = listResultado;
    }

    public List<DispTriaje> getListTriaje() {
        return this.listTriaje;
    }

    public void setListTriaje(List<DispTriaje> listTriaje) {
        this.listTriaje = listTriaje;
    }

    public List<DispDetalleDiagnostico> getListDetalleDiagnostico() {
        return this.listDetalleDiagnostico;
    }

    public void setListDetalleDiagnostico(List<DispDetalleDiagnostico> listDetalleDiagnostico) {
        this.listDetalleDiagnostico = listDetalleDiagnostico;
    }

    public DispDiagnostico getDispDiagnostico() {
        return this.dispDiagnostico;
    }

    public void setDispDiagnostico(DispDiagnostico dispDiagnostico) {
        this.dispDiagnostico = dispDiagnostico;
    }

    public boolean isVerHistorial() {
        return this.verHistorial;
    }

    public void setVerHistorial(boolean verHistorial) {
        this.verHistorial = verHistorial;
    }

    public String getStrUsuario() {
        return this.strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DispReceta getReceta() {
        return this.receta;
    }

    public void setReceta(DispReceta receta) {
        this.receta = receta;
    }

    public List<DispDetalleReceta> getListDetalleReceta() {
        return this.listDetalleReceta;
    }

    public void setListDetalleReceta(List<DispDetalleReceta> listDetalleReceta) {
        this.listDetalleReceta = listDetalleReceta;
    }

    public Date getHoy() {
        return this.hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public DispCliente getCliente() {
        return this.cliente;
    }

    public void setCliente(DispCliente cliente) {
        this.cliente = cliente;
    }

    public DispResultado getResultadoReceta() {
        return this.resultadoReceta;
    }

    public void setResultadoReceta(DispResultado resultadoReceta) {
        this.resultadoReceta = resultadoReceta;
    }

    public List<DispMedicamento> getListDispMedicamento() {
        return this.listDispMedicamento;
    }

    public void setListDispMedicamento(List<DispMedicamento> listDispMedicamento) {
        this.listDispMedicamento = listDispMedicamento;
    }

    public String getMedicamento() {
        return this.medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public DispMedicamento getDispMedicamento() {
        return this.dispMedicamento;
    }

    public void setDispMedicamento(DispMedicamento dispMedicamento) {
        this.dispMedicamento = dispMedicamento;
    }

    public String getFiltroConsultaAntecedentes() {
        return filtroConsultaAntecedentes;
    }

    public void setFiltroConsultaAntecedentes(String filtroConsultaAntecedentes) {
        this.filtroConsultaAntecedentes = filtroConsultaAntecedentes;
    }

    public String getLabelMantAntecedentes() {
        return labelMantAntecedentes;
    }

    public void setLabelMantAntecedentes(String labelMantAntecedentes) {
        this.labelMantAntecedentes = labelMantAntecedentes;
    }

    public boolean isAyuno() {
        return ayuno;
    }

    public void setAyuno(boolean ayuno) {
        this.ayuno = ayuno;
    }

    public boolean isVejigaLlena() {
        return vejigaLlena;
    }

    public void setVejigaLlena(boolean vejigaLlena) {
        this.vejigaLlena = vejigaLlena;
    }

    public List<DispEstudiosMedicos> getListDispEstudiosMedicos() {
        return listDispEstudiosMedicos;
    }

    public void setListDispEstudiosMedicos(List<DispEstudiosMedicos> listDispEstudiosMedicos) {
        this.listDispEstudiosMedicos = listDispEstudiosMedicos;
    }

    public List<DispSolicitudExamen> getLstDispSolicitudExamen() {
        return lstDispSolicitudExamen;
    }

    public void setLstDispSolicitudExamen(List<DispSolicitudExamen> lstDispSolicitudExamen) {
        this.lstDispSolicitudExamen = lstDispSolicitudExamen;
    }

    public boolean isBoolImprimirOrden() {
        return boolImprimirOrden;
    }

    public void setBoolImprimirOrden(boolean boolImprimirOrden) {
        this.boolImprimirOrden = boolImprimirOrden;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
