package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispAntecedentes;
import com.Entity.DispDetalleDiagnostico;
import com.Entity.DispDetalleReceta;
import com.Entity.DispDiagnostico;
import com.Entity.DispEstudiosMedicos;
import com.Entity.DispExamen;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispReceta;
import com.Entity.DispResultado;
import com.Entity.DispSolicitudExamen;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispAntecedentesFacade;
import com.Session.DispDetalleDiagnosticoFacade;
import com.Session.DispDetalleRecetaFacade;
import com.Session.DispDiagnosticoFacade;
import com.Session.DispEstudiosMedicosFacade;
import com.Session.DispExamenFacade;
import com.Session.DispMedicoPersonalFacade;
import com.Session.DispRecetaFacade;
import com.Session.DispResultadoFacade;
import com.Session.DispSolicitudExamenFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Process.TablaDinamica;
import com.integrasystemsonline.Utilidades.LazyAntecedentesModel;
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
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.spacer.Spacer;
import org.primefaces.model.LazyDataModel;

@Named("registroDiagnosticoMB")
@ViewScoped
public class RegistroDiagnosticoMB implements Serializable {

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

    @EJB
    DispRecetaFacade dispRecetaFacade;

    @EJB
    DispDetalleRecetaFacade dispDetalleRecetaFacade;

    @EJB
    DispEstudiosMedicosFacade dispEstudiosMedicosFacade;
    
    @EJB
    DispExamenFacade dispExamenFacade;
    
    @EJB
    DispMedicoPersonalFacade dispMedicoPersonalFacade;
    
    @EJB
    DispSolicitudExamenFacade dispSolicitudExamenFacade;
    
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

    private DispReceta dispReceta = new DispReceta();

    private List<DispDetalleReceta> listDispDetalleReceta = new ArrayList<>();

    private DispDetalleReceta dispDetalleReceta = new DispDetalleReceta();
    
    DataTable table = new DataTable();
    
    HtmlForm form = new HtmlForm();
    
    DispExamen dispExamen = new DispExamen();
    
    List<DispExamen> listDispExamen = new ArrayList<>();
    
    List<DispExamen> listDispExamenesSeleccionados = new ArrayList<>();
    
    List<DispEstudiosMedicos> listDispEstudiosMedicos = new ArrayList<>();
    
    private boolean ayuno = false;
    
    private boolean vejigaLlena = false;
    
    private String comentario = "";
    
    @PostConstruct
    public void init() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
            this.dispAgendamiento = (DispAgendamiento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dispAgendamiento");
            if (this.dispAgendamiento != null) {
                generarCodigoDiagnostico(this.dispAgendamiento);
            }
            this.dispDetalleDiagnostico = (DispDetalleDiagnostico) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("plantillaDetalleDiagnostico");
            if (this.dispDetalleDiagnostico == null) {
                this.dispDetalleDiagnostico = new DispDetalleDiagnostico();
                this.dispDiagnostico = new DispDiagnostico();
                if (this.dispAgendamiento != null) {
                    this.dispDiagnostico.setCodigo(generarCodigoDiagnostico(this.dispAgendamiento));
                }
                this.dispDiagnostico.setEnfermedad("n/a");
                this.dispResultado = new DispResultado();
                this.dispResultado.setMotivoConsulta("n/a");
            } else {
                this.dispDiagnostico = new DispDiagnostico();
                this.dispDiagnostico.setCodigo(this.dispDetalleDiagnostico.getIdDiagnostico().getCodigo());
                this.dispDiagnostico.setEnfermedad(this.dispDetalleDiagnostico.getIdDiagnostico().getEnfermedad());
                this.dispResultado = new DispResultado();
                this.dispResultado.setAntecedentes(this.dispDetalleDiagnostico.getIdResultado().getAntecedentes());
                this.dispResultado.setExamenFisico(this.dispDetalleDiagnostico.getIdResultado().getExamenFisico());
                this.dispResultado.setIdAgendamiento(this.dispAgendamiento);
                this.dispResultado.setFecha(this.dispDetalleDiagnostico.getIdResultado().getFecha());
                this.dispResultado.setMotivoConsulta(this.dispDetalleDiagnostico.getIdResultado().getMotivoConsulta());
                this.dispResultado.setPlan(this.dispDetalleDiagnostico.getIdResultado().getPlan());
                this.dispResultado.setTratamiento(this.dispDetalleDiagnostico.getIdResultado().getTratamiento());
                this.dispResultado.setTiempoEnfermedad(this.dispDetalleDiagnostico.getIdResultado().getTiempoEnfermedad());
                
                
                DispReceta recetaPlantila = dispRecetaFacade.findByIdAgendamiento(dispDetalleDiagnostico.getIdResultado().getIdAgendamiento().getIdAgendamiento());
                //receta de la plantilla
                if(recetaPlantila!=null){
                    dispReceta = new DispReceta();
                    dispReceta.setObservaciones(recetaPlantila.getObservaciones());
                    List<DispDetalleReceta> listDetalleRecetaTemp = dispDetalleRecetaFacade.findByIdReceta(recetaPlantila.getIdReceta());
                    listDispDetalleReceta  = new ArrayList<>();
                    for (int i = 0; i < listDetalleRecetaTemp.size(); i++) {
                        DispDetalleReceta detalleReceta = new DispDetalleReceta();
                        detalleReceta.setCantidad(listDetalleRecetaTemp.get(i).getCantidad());
                        detalleReceta.setDosis(listDetalleRecetaTemp.get(i).getDosis());
                        detalleReceta.setDuracion(listDetalleRecetaTemp.get(i).getDuracion());
                        detalleReceta.setIdMedicamento(listDetalleRecetaTemp.get(i).getIdMedicamento());
                        listDispDetalleReceta.add(detalleReceta);
                    }
                }
                
//                if (this.listDispDetalleReceta == null) {
//                    this.listDispDetalleReceta = new ArrayList<>();
//                }
            }
            
            //receta en memoria
            //prioriza la receta en memoria
            DispReceta receta = (DispReceta) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dispReceta");
            List<DispDetalleReceta> listDetalleReceta = (List<DispDetalleReceta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listDispDetalleReceta");
            if(receta!=null){
                dispReceta = receta;
            }
            
            if(listDetalleReceta!=null){
                if(!listDetalleReceta.isEmpty()){
                    listDispDetalleReceta = listDetalleReceta;
                }
            }
            
            
            if (this.listDispDetalleReceta == null) {
                this.listDispDetalleReceta = new ArrayList<>();
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
            
            crearTablasOrdenes();
        } catch (Exception ex) {
            Logger.getLogger(RegistroDiagnosticoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
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

    public void guardar(String destino) throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispDiagnostico.getIdDiagnostico() != null);
        try {
            DispResultado resultado = this.dispResultadoFacade.findByIdAgendamiento(this.dispAgendamiento.getIdAgendamiento());
            if (resultado == null) {
                if (this.dispDiagnostico.getEnfermedad() != null && !this.dispDiagnostico.getEnfermedad().isEmpty()) {
                    if (this.dispResultado.getMotivoConsulta() != null && !this.dispResultado.getMotivoConsulta().isEmpty()) {
                        this.userTransaction.begin();
                        if (guardar) {
                            this.dispAgendamiento.setIntentos(0);
                            this.dispAgendamiento.setFechaAtendidoMedico(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                            this.dispAgendamiento.setEstado("AM");
                            this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                            this.dispAgendamientoFacade.flush();
                            this.dispDiagnostico.setEstado("A");
                            this.dispDiagnostico.setIdEmpresa(this.usuario.getIdEmpresa());
                            this.dispDiagnostico.setIdCiudad(this.usuario.getIdCiudad());
                            this.dispDiagnostico.setIdSector(this.usuario.getIdSector());
                            this.dispDiagnostico.setUsuarioIngreso(this.usuario.getUsuario());
                            this.dispDiagnostico.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                            this.dispDiagnosticoFacade.createWithValidator(this.dispDiagnostico);
                            this.dispDiagnosticoFacade.flush();
                            if (this.dispResultado.getProximaCita() != null) {
                                this.dispResultado.setFecha(this.objSDFOnlyDay.parse(this.objSDFOnlyDay.format(this.dispResultado.getProximaCita())));
                                this.dispResultado.setHora(this.objSDFOnlyHour.parse(this.objSDFOnlyHour.format(this.dispResultado.getProximaCita())));
                            }
                            this.dispResultado.setEstado("A");
                            this.dispResultado.setIdAgendamiento(this.dispAgendamiento);
                            this.dispResultado.setIdEmpresa(this.usuario.getIdEmpresa());
                            this.dispResultado.setIdCiudad(this.usuario.getIdCiudad());
                            this.dispResultado.setIdSector(this.usuario.getIdSector());
                            this.dispResultado.setUsuarioIngreso(this.usuario.getUsuario());
                            this.dispResultado.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                            this.dispResultadoFacade.createWithValidator(this.dispResultado);
                            this.dispResultadoFacade.flush();
                            this.dispDetalleDiagnostico.setTipo("N");
                            this.dispDetalleDiagnostico.setEstado("A");
                            this.dispDetalleDiagnostico.setIdDiagnostico(this.dispDiagnostico);
                            this.dispDetalleDiagnostico.setIdCliente(this.dispAgendamiento.getIdCliente());
                            this.dispDetalleDiagnostico.setIdMedicoPersonal(this.dispAgendamiento.getIdMedicoPersonal());
                            this.dispDetalleDiagnostico.setIdResultado(this.dispResultado);
                            this.dispDetalleDiagnostico.setIdImagen(null);
                            this.dispDetalleDiagnostico.setIdEmpresa(this.usuario.getIdEmpresa());
                            this.dispDetalleDiagnostico.setIdCiudad(this.usuario.getIdCiudad());
                            this.dispDetalleDiagnostico.setIdSector(this.usuario.getIdSector());
                            this.dispDetalleDiagnostico.setUsuarioIngreso(this.usuario.getUsuario());
                            this.dispDetalleDiagnostico.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                            this.dispDetalleDiagnosticoFacade.createWithValidator(this.dispDetalleDiagnostico);
                            this.dispDetalleDiagnosticoFacade.flush();
                            if (this.dispReceta != null) {
                                this.dispReceta.setEstado("A");
                                this.dispReceta.setIdAgendamiento(this.dispAgendamiento);
                                this.dispReceta.setIdEmpresa(this.usuario.getIdEmpresa());
                                this.dispReceta.setIdCiudad(this.usuario.getIdCiudad());
                                this.dispReceta.setIdSector(this.usuario.getIdSector());
                                this.dispReceta.setUsuarioIngreso(this.usuario.getUsuario());
                                this.dispReceta.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                                this.dispRecetaFacade.createWithValidator(this.dispReceta);
                                this.dispRecetaFacade.flush();
                            }
                            for (int i = 0; i < this.listDispDetalleReceta.size(); i++) {
                                this.dispDetalleReceta = this.listDispDetalleReceta.get(i);
                                this.dispDetalleReceta.setEstado("A");
                                this.dispDetalleReceta.setIdReceta(this.dispReceta);
                                this.dispDetalleReceta.setIdEmpresa(this.usuario.getIdEmpresa());
                                this.dispDetalleReceta.setIdCiudad(this.usuario.getIdCiudad());
                                this.dispDetalleReceta.setIdSector(this.usuario.getIdSector());
                                this.dispDetalleReceta.setUsuarioIngreso(this.usuario.getUsuario());
                                this.dispDetalleReceta.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                                this.dispDetalleRecetaFacade.createWithValidator(this.dispDetalleReceta);
                                this.dispDetalleRecetaFacade.flush();
                            }
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                            this.userTransaction.commit();
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("plantillaDetalleDiagnostico", null);
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispAgendamiento", null);
                            redireccionarTriajeMedico();
                        }
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese el motivo de la consulta.");
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese la enfermedad.");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ya existe un diagnostico para esta cita.");
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

    public void redireccionarHistorial() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ClientePlantilla", this.dispAgendamiento.getIdCliente());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_plantilla_historial.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(RegistroDiagnosticoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void redireccionarRecetas() {
        FacesMessage msg = null;
        try {
            if (this.dispResultado.getMotivoConsulta() != null && !this.dispResultado.getMotivoConsulta().isEmpty()) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispDiagnostico", this.dispDiagnostico);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispResultado", this.dispResultado);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispReceta", this.dispReceta);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listDispDetalleReceta", this.listDispDetalleReceta);
                this.dispDetalleDiagnostico.setIdCliente(this.dispAgendamiento.getIdCliente());
                this.dispDetalleDiagnostico.setIdDiagnostico(this.dispDiagnostico);
                this.dispDetalleDiagnostico.setIdResultado(this.dispResultado);
                this.dispDetalleDiagnostico.setIdMedicoPersonal(this.dispAgendamiento.getIdMedicoPersonal());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispDetalleDiagnostico", this.dispDetalleDiagnostico);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispAgendamiento", this.dispAgendamiento);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().redirect("../dispensario/disp_registro_receta.xhtml");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese el motivo de la consulta.");
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistroDiagnosticoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void crearTablasOrdenes(){
        try {
            List<DispEstudiosMedicos> lstDispEstudiosMedicos = new ArrayList<>();
            List<String> lstExamen = new ArrayList<>();
            List<String> lstEstudios = new ArrayList<>();
            List<DataTable> lst = new ArrayList<>();

            DispMedicoPersonal medico = dispMedicoPersonalFacade.findByIdUsuario(usuario.getIdUsuarios());
            if(medico!=null){
                lstDispEstudiosMedicos = dispEstudiosMedicosFacade.findByIdEspecialidad(medico.getIdEspecialidad().getIdEspecialidad());
                lstEstudios = new ArrayList<>();
                for (int j = 0; j < lstDispEstudiosMedicos.size(); j++) {
                    lstEstudios.add(lstDispEstudiosMedicos.get(j).getNombre());
                }

                TablaDinamica myTable = new TablaDinamica();
                myTable.construirPanel(2, "text-align: left");
                for (int i = 0; i < lstDispEstudiosMedicos.size(); i++) {
                    List<DispExamen> lstDispExamen = dispExamenFacade.findByIdEstudiosMedicos(lstDispEstudiosMedicos.get(i).getIdEstudiosMedicos());
                    lstExamen = new ArrayList<>();
                    for (int j = 0; j < lstDispExamen.size(); j++) {
                        lstExamen.add(lstDispExamen.get(j).getNombre());
                    }

                    if(!lstExamen.isEmpty()){
                        myTable.TablaExamenes(1, lstEstudios.get(i),lstExamen);
                        //myTable.NuevaTabla(lstExamen.size(), lstExamen);
                        this.table = myTable.getMyDataTable();
                        //lst.add(table);
                        List<String> lstStr = myTable.getLstExamenes();
                        form.getChildren().add(myTable.getPanel());
                        Spacer spacer = new Spacer();
                        spacer.setHeight("50");
                        form.getChildren().add(spacer);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public List<DispExamen> cargarListaExamen(DispEstudiosMedicos estudios){
        listDispExamen = new ArrayList<>();
        try {
            listDispExamen = dispExamenFacade.findByIdEstudiosMedicos(estudios.getIdEstudiosMedicos());
        } catch (Exception e) {
        }
        
        return listDispExamen;
    }
    
    public void cargarListaEstudiosMedicos(){
        try {
            DispMedicoPersonal medico = dispMedicoPersonalFacade.findByIdUsuario(usuario.getIdUsuarios());
            if(medico!=null){
                Integer idEspecialidad = medico.getIdEspecialidad().getIdEspecialidad();
                if(idEspecialidad!=null){
                    listDispEstudiosMedicos = dispEstudiosMedicosFacade.findByIdEspecialidad(idEspecialidad);
                }
            }            
        } catch (Exception e) {
        }
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
        }
    }
    
    public void generarOrdenes() throws SystemException{
        FacesMessage msg = null;
        try {
            userTransaction.begin();
            for (int i = 0; i < listDispExamenesSeleccionados.size(); i++) {
                DispSolicitudExamen dispSolicitudExamen = new DispSolicitudExamen();
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
    
    public void redireccionarTriajeMedico() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_triaje_medico.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(RegistroDiagnosticoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void redireccionarHistorialPaciente() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_historial_paciente.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(RegistroDiagnosticoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public LazyDataModel<DispAntecedentes> getAllAntecedentes() {
        if (this.lazyDispAntecedentes == null) {
            this.lazyDispAntecedentes = new LazyAntecedentesModel(this.dispAgendamiento.getIdCliente().getIdCliente(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), "A");
        }
        return this.lazyDispAntecedentes;
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

    public DataTable getTable() {
        return table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public HtmlForm getForm() {
        return form;
    }

    public void setForm(HtmlForm form) {
        this.form = form;
    }

    public DispExamen getDispExamen() {
        return dispExamen;
    }

    public void setDispExamen(DispExamen dispExamen) {
        this.dispExamen = dispExamen;
    }

    public List<DispExamen> getListDispExamen() {
        return listDispExamen;
    }

    public void setListDispExamen(List<DispExamen> listDispExamen) {
        this.listDispExamen = listDispExamen;
    }

    public List<DispEstudiosMedicos> getListDispEstudiosMedicos() {
        return listDispEstudiosMedicos;
    }

    public void setListDispEstudiosMedicos(List<DispEstudiosMedicos> listDispEstudiosMedicos) {
        this.listDispEstudiosMedicos = listDispEstudiosMedicos;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    
}
