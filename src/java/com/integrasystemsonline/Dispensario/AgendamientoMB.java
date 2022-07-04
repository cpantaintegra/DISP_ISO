package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispCliente;
import com.Entity.DispDetalleDiagnostico;
import com.Entity.DispDiagnostico;
import com.Entity.DispEspecialidad;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispOrigen;
import com.Entity.DispPrecio;
import com.Entity.DispResultado;
import com.Entity.DispServicio;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispClienteFacade;
import com.Session.DispDetalleDiagnosticoFacade;
import com.Session.DispDiagnosticoFacade;
import com.Session.DispEspecialidadFacade;
import com.Session.DispMedicoPersonalFacade;
import com.Session.DispOrigenFacade;
import com.Session.DispPrecioFacade;
import com.Session.DispResultadoFacade;
import com.Session.DispServicioFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.CustomScheduleEvent;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.EstadoDia;
import com.integrasystemsonline.Utilidades.Evento;
import com.integrasystemsonline.Utilidades.InfoAgendamiento;
import com.integrasystemsonline.Utilidades.SMTPConfig;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named("agendamientoMB")
@ViewScoped
public class AgendamientoMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    DispEspecialidadFacade dispEspecialidadFacade;

    @EJB
    DispOrigenFacade dispOrigenFacade;

    @EJB
    DispPrecioFacade dispPrecioFacade;

    @EJB
    DispServicioFacade dispServicioFacade;

    @EJB
    DispMedicoPersonalFacade dispMedicoPersonalFacade;

    @EJB
    DispClienteFacade dispClienteFacade;

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
    DispDiagnosticoFacade dispDiagnosticoFacade;
    
    @EJB
    DispDetalleDiagnosticoFacade dispDetalleDiagnosticoFacade;
    
    @EJB
    DispResultadoFacade dispResultadoFacade;
    
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat objSDFReserva = new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat objSDFHora = new SimpleDateFormat("HH:mm");

    private List<DispCliente> listDispCliente = new ArrayList<>();

    private List<DispAgendamiento> listDispAgendamiento = new ArrayList<>();

    private List<DispMedicoPersonal> listDispMedicoPersonal = new ArrayList<>();

    private DispAgendamiento dispAgendamiento = new DispAgendamiento();

    private DispCliente clienteObj = new DispCliente();

    private DispMedicoPersonal medicoPersonalObj = new DispMedicoPersonal();

    private List<DispEspecialidad> listDispEspecialidad = new ArrayList<>();

    private List<DispServicio> listDispServicio = new ArrayList<>();

    private DispEspecialidad especialidadoObj = new DispEspecialidad();

    private DispServicio servicioObj = new DispServicio();

    private List<String> listDias = new ArrayList<>();

    private List<String> listHoras = new ArrayList<>();

    private UIData dataTable;

    private String filtroConsulta;

    IsUsuarios usuario;

    private String estado;

    private String labelMant;

    private String labelMantCliente;

    private List<Estado> listaEstado;

    private List<EstadoDia> listaEstadoDia;

    private Estado estadoObj;

    private EstadoDia estadoDiaObj;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private IsParametros isParametros = new IsParametros();

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    String disponibilidad = "Disponible";

    int numFilas = 0;

    DataTable table = new DataTable();

    private String scrollTime = "";

    private String minTime = "";

    private String maxTime = "";

    private String slotDuration = "";

    private String view = "";

    private String slotMinutes = "";

    private String timeZone = "";

    private String timePlus = "";

    private String tiempoEspera = "";

    private String showWeekend = "";

    private String timeDif = "";
    
    private String timeDIffPasado = "";
    
    private ScheduleModel model = (ScheduleModel) new DefaultScheduleModel();

    private Evento evento = new Evento();

    private ScheduleEvent eventoTemp = (ScheduleEvent) new DefaultScheduleEvent();

    private ScheduleEvent event = (ScheduleEvent) new DefaultScheduleEvent();

    private List<ScheduleEvent> scheduleEvents;

    private String cliente = "";

    private String nombArchivo;

    private String ruta;

    private DispOrigen origenObj = new DispOrigen();

    private List<DispOrigen> listDispOrigen = new ArrayList<>();

    private InfoAgendamiento infoAgendamiento;

    private BigDecimal precioCita = new BigDecimal(0);

    private String pagado;

    @PostConstruct
    public void ini() {
        try {
            this.estado = "A";
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            this.listDispEspecialidad = this.dispEspecialidadFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            if (!this.listDispEspecialidad.isEmpty()) {
                this.especialidadoObj = this.listDispEspecialidad.get(0);
            }
            this.listDispServicio = this.dispServicioFacade.findByIdEspecialidad(this.especialidadoObj.getIdEspecialidad());
            if (!this.listDispServicio.isEmpty()) {
                this.servicioObj = this.listDispServicio.get(0);
            }
            this.listDispMedicoPersonal = this.dispMedicoPersonalFacade.listaDispMedicoPersonalByEspecialidad(this.especialidadoObj.getIdEspecialidad());
            if (!this.listDispMedicoPersonal.isEmpty()) {
                this.medicoPersonalObj = this.listDispMedicoPersonal.get(0);
            }
            this.labelMant = "Guardar";
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
            this.isParametros = this.isParametrosFacade.findByCodigo("scrollTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.scrollTime = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("minTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.minTime = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("maxTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.maxTime = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("slotDuration", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.slotDuration = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("view", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.view = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("slotMinutes", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.slotMinutes = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("timePlus", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timePlus = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("tiempoEspera", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.tiempoEspera = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("showWeekend", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.showWeekend = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("timeDif", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeDif = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("timeDif", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeDif = this.isParametros.getValor();
            this.isParametros = this.isParametrosFacade.findByCodigo("timeDIffPasado", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeDIffPasado = this.isParametros.getValor();
            cargarEventos();
            this.listDispOrigen = this.dispOrigenFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            try {
                this.origenObj = this.listDispOrigen.get(0);
            } catch (Exception exception) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validarCancelar() {
        if (this.dispAgendamiento.getIdAgendamiento() != null) {
            if (this.dispAgendamiento.getEstado().equals("C") || this.dispAgendamiento.getEstado().equals("D")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void enviarCorreos() {
        try {
            String detalle = Utilidades.plantillaAgendamientoCliente(this.infoAgendamiento.getCliente(), this.infoAgendamiento.getFechaDesde(), this.infoAgendamiento.getFechaHasta(), this.infoAgendamiento.getServicio(), this.infoAgendamiento.getMedico(), this.infoAgendamiento.getCedulaMedico());
            String mensaje = Utilidades.plantillas(this.infoAgendamiento.getCliente(), detalle, "", "de que se ha agendado con exito, acontinuacion se detalla la informacion de la cita");
            if (!mensaje.equals("")) {
                SMTPConfig smtpConfig = new SMTPConfig();
                smtpConfig.sendMail("Mensaje del Sistema", mensaje, this.clienteObj.getEmail());
            }
            detalle = Utilidades.plantillaAgendamientoMedico(this.infoAgendamiento.getCliente(), this.infoAgendamiento.getFechaDesde(), this.infoAgendamiento.getFechaHasta(), this.infoAgendamiento.getServicio());
            mensaje = Utilidades.plantillas(this.infoAgendamiento.getMedico(), detalle, "", "de que se ha agendado con exito, acontinuacion se detalla la informacion de la cita");
            if (!mensaje.equals("")) {
                SMTPConfig smtpConfig = new SMTPConfig();
                smtpConfig.sendMail("Mensaje del Sistema", mensaje, this.medicoPersonalObj.getEmail());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void resetCombos() {
        try {
            this.listDispServicio = this.dispServicioFacade.findByIdEspecialidad(this.especialidadoObj.getIdEspecialidad());
            if (!this.listDispServicio.isEmpty()) {
                this.servicioObj = this.listDispServicio.get(0);
            }
            this.listDispMedicoPersonal = this.dispMedicoPersonalFacade.listaDispMedicoPersonalByEspecialidad(this.especialidadoObj.getIdEspecialidad());
            this.medicoPersonalObj = this.dispAgendamiento.getIdMedicoPersonal();
            if (!this.listDispMedicoPersonal.isEmpty()) {
                this.medicoPersonalObj = this.listDispMedicoPersonal.get(0);
            }
        } catch (Exception exception) {
        }
    }

    public List<Evento> listarTodos() throws ParseException {
        ArrayList<Evento> lst = new ArrayList<>();
        try {
            Date fechaActual = new Date();
            fechaActual = Utilidades.obtenerFechaZonaHoraria(fechaActual, this.timeDif, this.timeZone);
            this.listDispAgendamiento = this.dispAgendamientoFacade.findAllCalendario(fechaActual, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            for (int i = 0; i < this.listDispAgendamiento.size(); i++) {
                Evento evento = new Evento();
                DispAgendamiento agendamiento = this.listDispAgendamiento.get(i);
                evento.setDateInicio(agendamiento.getFecha());
                Date dataSelecionada = evento.getDateInicio();
                DateTime dataSelecionadaJoda = new DateTime(dataSelecionada.getTime());
                evento.setDiaEntero(false);
                evento.setDateFin(dataSelecionadaJoda.plusMinutes(Integer.parseInt(this.timePlus)).toDate());
                String titulo = "Reservacion para " + agendamiento.getIdEspecialidad().getNombre() + " del cliente " + agendamiento.getIdCliente().getApaterno() + " " + agendamiento.getIdCliente().getAmaterno() + " " + agendamiento.getIdCliente().getNombre();
                evento.setTitulo(titulo);
                evento.setId(new Long(agendamiento.getIdAgendamiento()));
                lst.add(evento);
            }
        } catch (Exception exception) {
        }
        return lst;
    }

    public void onDateSelect(SelectEvent selectEvent) {
        this.dispAgendamiento = new DispAgendamiento();
        this.evento = new Evento();
        Date dataSelecionada = (Date) selectEvent.getObject();
        DateTime dataSelecionadaJoda = new DateTime(dataSelecionada.getTime());
        this.evento.setDateInicio(dataSelecionadaJoda.toDate());
        this.evento.setDateFin(dataSelecionadaJoda.plusMinutes(Integer.parseInt(this.timePlus)).toDate());
        this.event = (ScheduleEvent) new DefaultScheduleEvent(this.evento.getTitulo(), this.evento.getDateInicio(), this.evento.getDateFin(), this.evento);
        this.labelMant = "Guardar";
        //resetCombos();
////        if(event.getId()==null){
////            this.clienteObj = null;
////            this.cliente = "";
////            this.precioCita = new BigDecimal(0);
////            this.especialidadoObj = this.listDispEspecialidad.get(0);
////        }
////        this.pagado = null;
    }

    public void onEventMove(ScheduleEntryMoveEvent moveEvent) {
        FacesMessage msg = null;
        try {
            this.event = moveEvent.getScheduleEvent();
            this.evento = (Evento) this.event.getData();
            Date fechaActual = new Date();
            fechaActual = Utilidades.obtenerFechaZonaHoraria(fechaActual, this.timeDif, this.timeZone);
            DispAgendamiento agendamiento = this.dispAgendamientoFacade.findByEspecialidadMedico(this.evento.getDateInicio(), this.especialidadoObj.getIdEspecialidad(), this.medicoPersonalObj.getIdMedicoPersonal());
            if (agendamiento == null) {
                this.userTransaction.begin();
                this.dispAgendamiento = this.dispAgendamientoFacade.findById(Integer.parseInt(String.valueOf(this.evento.getId())));
                if (this.evento.getDateInicio().after(fechaActual)) {
                    this.dispAgendamiento.setFecha(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio().getTime())));
                    this.dispAgendamiento.setTurno(0);
                    this.dispAgendamiento.setUsuarioModificacion(this.usuario.getUsuario());
                    this.dispAgendamiento.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Transaccion exitosa");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se puede agendar en la fecha seleccionada.");
                }
                this.userTransaction.commit();
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ya existe una reservaa en el horario seleccionado.");
            }
            cargarEventos();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            System.out.println(e.getMessage());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onEventSelect(SelectEvent selectEvent) {
        FacesMessage msg = null;
        try {
            this.event = (ScheduleEvent) selectEvent.getObject();
            this.evento = (Evento) this.event.getData();
            this.eventoTemp = (ScheduleEvent) selectEvent.getObject();
            this.dispAgendamiento = this.dispAgendamientoFacade.findById(this.evento.getId().intValue());
            this.clienteObj = this.dispAgendamiento.getIdCliente();
            if (this.clienteObj.getIdCliente() != 1) {
                this.cliente = this.clienteObj.getApaterno() + " " + this.clienteObj.getAmaterno() + " " + this.clienteObj.getNombre();
            }
            this.especialidadoObj = this.dispAgendamiento.getIdEspecialidad();
            resetCombos();
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            System.out.println("ERROR: " + e.getMessage());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public List<String> completeText(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> locationsList = new ArrayList<>();
        List<DispCliente> clientes = this.dispClienteFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        for (DispCliente cliente1 : clientes) {
            locationsList.add(cliente1.getApaterno().trim() + " " + cliente1.getAmaterno().trim() + " " + cliente1.getNombre().trim());
        }
        return (List<String>) locationsList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public void onItemSelect(SelectEvent event) {
        this.cliente = (String) event.getObject();
        this.clienteObj = this.dispClienteFacade.findByNames(cliente, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
//        String[] subCliente = this.cliente.split(" ");
//        if (subCliente.length == 3) {
//            this.clienteObj = this.dispClienteFacade.findByNames(subCliente[0], subCliente[1], subCliente[2], this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
//        } else {
//            this.clienteObj = this.dispClienteFacade.findByNames(subCliente[0], subCliente[1], subCliente[2] + " " + subCliente[3], this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
//        }
    }

    public void agregarCliente() {
        FacesMessage msg = null;
        try {
            if (!this.cliente.isEmpty()) {
                String[] subCliente = this.cliente.split(" ");
                this.clienteObj = this.dispClienteFacade.findByNames(cliente, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
//                if (subCliente.length == 3) {
//                    this.clienteObj = this.dispClienteFacade.findByNames(subCliente[0], subCliente[1], subCliente[2], this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
//                } else {
//                    this.clienteObj = this.dispClienteFacade.findByNames(subCliente[0], subCliente[1], subCliente[2] + " " + subCliente[3], this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
//                }
                if (this.clienteObj == null) {
                    guardarCliente();
                }
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void guardarCliente() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.clienteObj.getIdCliente() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            this.clienteObj.setIdOrigen(this.origenObj);
            if (guardar) {
                this.clienteObj.setIdEmpresa(this.usuario.getIdEmpresa());
                this.clienteObj.setIdCiudad(this.usuario.getIdCiudad());
                this.clienteObj.setIdSector(this.usuario.getIdSector());
                this.clienteObj.setUsuarioIngreso(this.usuario.getUsuario());
                this.clienteObj.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispClienteFacade.createWithValidator(this.clienteObj);
                this.dispClienteFacade.flush();
            } else {
                this.clienteObj.setUsuarioModificacion(this.usuario.getUsuario());
                this.clienteObj.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispClienteFacade.editWithValidator(this.clienteObj);
                this.dispClienteFacade.flush();
            }
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        try {
            Date fechaActual = Utilidades.obtenerFechaZonaHoraria(new Date(), this.timeDif, this.timeZone);
            this.userTransaction.begin();
            if (this.clienteObj != null) {
                if (this.clienteObj.getIdCliente() != null) {
                    String titulo = this.servicioObj.getNombre() + " del paciente " + this.clienteObj.getApaterno() + " " + this.clienteObj.getAmaterno() + " " + this.clienteObj.getNombre();
                    DefaultScheduleEvent defaultScheduleEvent = new DefaultScheduleEvent(titulo, this.event.getStartDate(), this.event.getEndDate(), true);
                    List<DispPrecio> lstPrecio = this.dispPrecioFacade.findByServicio(this.servicioObj.getIdServicio());
                    String origen = clienteObj.getIdOrigen().getNombre();
                    for (int i = 0; i < lstPrecio.size(); i++) {
                        if (origen.equals(((DispPrecio) lstPrecio.get(i)).getIdOrigen().getNombre())) {
                            this.precioCita = ((DispPrecio) lstPrecio.get(i)).getValor();
                        }
                    }
                    if (this.evento.getId() == null) {
                        this.dispAgendamiento = this.dispAgendamientoFacade.findByEspecialidadMedico(this.evento.getDateInicio(), this.especialidadoObj.getIdEspecialidad(), this.medicoPersonalObj.getIdMedicoPersonal());
                        if (this.dispAgendamiento == null) {
                            if (fechaActual.before(this.event.getStartDate())) {
                                this.model.addEvent((ScheduleEvent) defaultScheduleEvent);
                                this.dispAgendamiento = new DispAgendamiento();
                                this.dispAgendamiento.setFecha(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio())));
                                this.dispAgendamiento.setTurno(0);
                                this.dispAgendamiento.setCosto(this.precioCita.setScale(2, RoundingMode.HALF_EVEN));
                                this.dispAgendamiento.setIdCliente(this.clienteObj);
                                this.dispAgendamiento.setIdEspecialidad(this.especialidadoObj);
                                this.dispAgendamiento.setIdMedicoPersonal(this.medicoPersonalObj);
                                this.dispAgendamiento.setIdServicio(this.servicioObj);
                                this.dispAgendamiento.setEstado("C");
                                this.dispAgendamiento.setEnTiempoPasado("N");
                                this.dispAgendamiento.setIdEmpresa(this.usuario.getIdEmpresa());
                                this.dispAgendamiento.setIdCiudad(this.usuario.getIdCiudad());
                                this.dispAgendamiento.setIdSector(this.usuario.getIdSector());
                                this.dispAgendamiento.setUsuarioIngreso(this.usuario.getUsuario());
                                this.dispAgendamiento.setFechaIngreso(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone));
                                this.dispAgendamientoFacade.createWithValidator(this.dispAgendamiento);
                                this.dispAgendamientoFacade.flush();
                                cargarInfoAgendamiento();
                                PrimeFaces.current().executeScript("PF('myschedule').update();PF('eventDialog').hide();PF('dlgAgendamiento').show()");
                                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                            } else {
                                Long diff = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone).getTime() - this.event.getStartDate().getTime();
                                long diffseg = diff / 1000;
                                Integer tiempoPasado = Integer.parseInt(timeDIffPasado);
                                if(diffseg > tiempoPasado){
                                    this.dispAgendamiento = new DispAgendamiento();
                                    this.dispAgendamiento.setFecha(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio())));
                                    this.dispAgendamiento.setFechaLlamada(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio())));
                                    this.dispAgendamiento.setFechaInicioAtencion(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio())));
                                    this.dispAgendamiento.setFechaAtendido(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio())));
                                    this.dispAgendamiento.setFechaLlamarMedico(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio())));
                                    this.dispAgendamiento.setFechaInicioMedico(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio())));
                                    this.dispAgendamiento.setFechaAtendidoMedico(this.objSDF.parse(this.objSDF.format(this.evento.getDateInicio())));
                                    this.dispAgendamiento.setTurno(0);
                                    this.dispAgendamiento.setCosto(this.precioCita.setScale(2, RoundingMode.HALF_EVEN));
                                    this.dispAgendamiento.setIdCliente(this.clienteObj);
                                    this.dispAgendamiento.setIdEspecialidad(this.especialidadoObj);
                                    this.dispAgendamiento.setIdMedicoPersonal(this.medicoPersonalObj);
                                    this.dispAgendamiento.setIdServicio(this.servicioObj);
                                    this.dispAgendamiento.setEstado("AM");
                                    this.dispAgendamiento.setEnTiempoPasado("S");
                                    this.dispAgendamiento.setIdEmpresa(this.usuario.getIdEmpresa());
                                    this.dispAgendamiento.setIdCiudad(this.usuario.getIdCiudad());
                                    this.dispAgendamiento.setIdSector(this.usuario.getIdSector());
                                    this.dispAgendamiento.setUsuarioIngreso(this.usuario.getUsuario());
                                    this.dispAgendamiento.setFechaIngreso(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone));
                                    this.dispAgendamientoFacade.createWithValidator(this.dispAgendamiento);
                                    this.dispAgendamientoFacade.flush();
                                    
                                    DispResultado resultado = new DispResultado();
                                    resultado.setIdAgendamiento(dispAgendamiento);
                                    resultado.setMotivoConsulta("n/a");
                                    resultado.setEstado("A");
                                    resultado.setIdEmpresa(this.usuario.getIdEmpresa());
                                    resultado.setIdCiudad(this.usuario.getIdCiudad());
                                    resultado.setIdSector(this.usuario.getIdSector());
                                    resultado.setUsuarioIngreso(this.usuario.getUsuario());
                                    resultado.setFechaIngreso(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone));
                                    dispResultadoFacade.createWithValidator(resultado);
                                    dispResultadoFacade.flush();
                                    
                                    DispDiagnostico diagnostico = new DispDiagnostico();
                                    diagnostico.setEnfermedad("n/a");
                                    String codigo = dispAgendamiento.getIdMedicoPersonal().getIdEspecialidad().getCodigo() + dispAgendamiento.getIdMedicoPersonal().getApaterno().substring(0, 2).toUpperCase() + dispAgendamiento.getIdMedicoPersonal().getAmaterno().substring(0, 2).toUpperCase() + dispAgendamiento.getIdMedicoPersonal().getNombre().substring(0, 2).toUpperCase();
                                    diagnostico.setCodigo(codigo);
                                    diagnostico.setEstado("A");
                                    diagnostico.setIdEmpresa(this.usuario.getIdEmpresa());
                                    diagnostico.setIdCiudad(this.usuario.getIdCiudad());
                                    diagnostico.setIdSector(this.usuario.getIdSector());
                                    diagnostico.setUsuarioIngreso(this.usuario.getUsuario());
                                    diagnostico.setFechaIngreso(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone));
                                    dispDiagnosticoFacade.createWithValidator(diagnostico);
                                    dispDiagnosticoFacade.flush();
                                    
                                    DispDetalleDiagnostico detalleDiagnostico = new DispDetalleDiagnostico();
                                    detalleDiagnostico.setTipo("N");
                                    detalleDiagnostico.setIdResultado(resultado);
                                    detalleDiagnostico.setIdDiagnostico(diagnostico);
                                    detalleDiagnostico.setIdCliente(dispAgendamiento.getIdCliente());
                                    detalleDiagnostico.setIdMedicoPersonal(dispAgendamiento.getIdMedicoPersonal());
                                    detalleDiagnostico.setEstado("A");
                                    detalleDiagnostico.setIdEmpresa(this.usuario.getIdEmpresa());
                                    detalleDiagnostico.setIdCiudad(this.usuario.getIdCiudad());
                                    detalleDiagnostico.setIdSector(this.usuario.getIdSector());
                                    detalleDiagnostico.setUsuarioIngreso(this.usuario.getUsuario());
                                    detalleDiagnostico.setFechaIngreso(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone));
                                    dispDetalleDiagnosticoFacade.createWithValidator(detalleDiagnostico);
                                    dispDetalleDiagnosticoFacade.flush();

                                    cargarInfoAgendamiento();
                                    PrimeFaces.current().executeScript("PF('myschedule').update();PF('eventDialog').hide();PF('dlgAgendamiento').show()");
                                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                                }
                                else{
                                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se puede agendar en la fecha seleccionada.");
                                }
                            }
                        } else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ya existe una reservaa en el horario seleccionado.");
                        }
                    } else {
                        DispAgendamiento agendamiento = this.dispAgendamientoFacade.findById(this.evento.getId().intValue());
                        if (agendamiento != null) {
                            this.model.deleteEvent(this.eventoTemp);
                            this.model.updateEvent((ScheduleEvent) defaultScheduleEvent);
                            this.dispAgendamiento.setCosto(this.precioCita.setScale(2, RoundingMode.HALF_EVEN));
                            this.dispAgendamiento.setIdCliente(this.clienteObj);
                            this.dispAgendamiento.setPagado(this.pagado);
                            this.dispAgendamiento.setIdEspecialidad(this.especialidadoObj);
                            this.dispAgendamiento.setIdMedicoPersonal(this.medicoPersonalObj);
                            this.dispAgendamiento.setIdServicio(this.servicioObj);
                            this.dispAgendamiento.setUsuarioModificacion(this.usuario.getUsuario());
                            this.dispAgendamiento.setFechaModificacion(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone));
                            this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                            this.dispAgendamientoFacade.flush();
                            cargarInfoAgendamiento();
                            PrimeFaces.current().executeScript("PF('myschedule').update();PF('eventDialog').hide();PF('dlgAgendamiento').show()");
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                        }
                    }
                    cargarEventos();
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Seleccione un paciente.");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Seleccione un paciente.");
            }
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cargarInfoAgendamiento() throws SystemException {
        try {
            DateTime dataSelecionadaJoda = new DateTime(this.dispAgendamiento.getFecha().getTime());
            Date fechaHasta = dataSelecionadaJoda.plusMinutes(Integer.parseInt(this.timePlus)).toDate();
            this.infoAgendamiento = new InfoAgendamiento();
            this.infoAgendamiento.setFechaDesde(this.objSDF.format(this.dispAgendamiento.getFecha()));
            this.infoAgendamiento.setFechaHasta(this.objSDF.format(fechaHasta));
            this.infoAgendamiento.setCliente(this.clienteObj.getApaterno() + " " + this.clienteObj.getAmaterno() + " " + this.clienteObj.getNombre());
            this.infoAgendamiento.setMedico(this.medicoPersonalObj.getApaterno() + " " + this.medicoPersonalObj.getAmaterno() + " " + this.medicoPersonalObj.getNombre());
            this.infoAgendamiento.setServicio(this.servicioObj.getNombre());
            this.infoAgendamiento.setCedulaMedico(this.medicoPersonalObj.getNumDocumento());
//            String origen = this.dispAgendamiento.getIdCliente().getIdOrigen().getNombre();
//            List<DispPrecio> lstPrecio = this.dispPrecioFacade.findByServicio(this.servicioObj.getIdServicio());
//            for (int i = 0; i < lstPrecio.size(); i++) {
//                if (origen.equals(((DispPrecio) lstPrecio.get(i)).getIdOrigen().getNombre())) {
//                    this.precioCita = ((DispPrecio) lstPrecio.get(i)).getValor();
//                }
//            }
            this.infoAgendamiento.setPrecio(Float.parseFloat(String.valueOf(this.dispAgendamiento.getCosto())));
        } catch (Exception e) {
            this.userTransaction.rollback();
        }
    }

    public void actualizarPrecio() {
        try {
            if (dispAgendamiento.getIdAgendamiento() != null) {
                userTransaction.begin();
                dispAgendamiento.setCosto(this.precioCita.setScale(2, RoundingMode.HALF_EVEN));
                dispAgendamiento.setPagado(this.pagado);
                dispAgendamientoFacade.editWithValidator(dispAgendamiento);
                dispAgendamientoFacade.flush();
                userTransaction.commit();
            }
            Date fechaActual = Utilidades.obtenerFechaZonaHoraria(new Date(), this.timeDif, this.timeZone);
            if (fechaActual.before(this.event.getStartDate())) {
                enviarCorreos();
                printInfoAgendamiento(this.dispAgendamiento.getIdAgendamiento(), this.servicioObj.getIdServicio());
            }
            
            this.clienteObj = null;
            this.cliente = "";
            this.precioCita = new BigDecimal(0);
            this.especialidadoObj = this.listDispEspecialidad.get(0);
            this.pagado = null;
            resetCombos();
            cargarEventos();
        } catch (Exception exception) {
        }
    }

    public void cargarEventos() throws ParseException {
        if (this.model != null) {
            List<Evento> eventos = listarTodos();
            if (this.scheduleEvents == null) {
                this.scheduleEvents = new ArrayList<>();
            }
            
            for (Evento eventoAtual : eventos) {
                DefaultScheduleEvent newEvent = new DefaultScheduleEvent(eventoAtual.getTitulo(), eventoAtual.getDateInicio(), eventoAtual.getDateFin(), eventoAtual);
                if (!this.scheduleEvents.contains(newEvent)) {
                    newEvent.setId(eventoAtual.getId().toString());
                    this.scheduleEvents.add(newEvent);
                    this.model.addEvent((ScheduleEvent) newEvent);
                    continue;
                }
                this.model.updateEvent((ScheduleEvent) newEvent);
            }
        }
    }

    public void onViewChange(SelectEvent selectEvent) {
        this.view = (String) selectEvent.getObject();
        this.event = (ScheduleEvent) new CustomScheduleEvent(this.evento.getTitulo(), this.evento.getDateInicio(), this.evento.getDateFin(), false, this.evento);
        this.model.addEvent(this.event);
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            if (this.dispAgendamiento.getIdAgendamiento() != null) {
                this.userTransaction.begin();
                this.dispAgendamiento.setEstado("CA");
                this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                this.dispAgendamientoFacade.flush();
                this.userTransaction.commit();
                cargarEventos();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Cancelado con exito");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se encontro la reservacion");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleComboEspecialidad() throws Exception {
        try {
            this.listDispMedicoPersonal = new ArrayList<>();
            this.listDispServicio = new ArrayList<>();
            if (this.especialidadoObj.getIdEspecialidad() != null) {
                this.listDispMedicoPersonal = this.dispMedicoPersonalFacade.listaDispMedicoPersonalByEspecialidad(this.especialidadoObj.getIdEspecialidad());
                if (!this.listDispMedicoPersonal.isEmpty()) {
                    this.medicoPersonalObj = this.listDispMedicoPersonal.get(0);
                }
                this.listDispServicio = this.dispServicioFacade.findByIdEspecialidad(this.especialidadoObj.getIdEspecialidad());
                if (!this.listDispServicio.isEmpty()) {
                    this.servicioObj = this.listDispServicio.get(0);
                }
            }
        } catch (Exception exception) {
        }
    }

    public void handleComboServicio(DispServicio servicio) throws Exception {
        try {
            if (servicio.getIdServicio() != null
                    && !this.listDispServicio.isEmpty()) {
                this.servicioObj = servicio;
            }
        } catch (Exception exception) {
        }
    }

    public void handleComboMedico(DispMedicoPersonal medico) throws Exception {
        try {
            if (medico.getIdMedicoPersonal() != null
                    && !this.listDispMedicoPersonal.isEmpty()) {
                this.medicoPersonalObj = medico;
            }
        } catch (Exception exception) {
        }
    }

    public void redireccionarCliente() {
        FacesMessage msg = null;
        try {
            if (this.cliente == null) {
                this.labelMantCliente = "Ingresar";
                this.estado = "A";
            } else if (this.cliente.isEmpty()) {
                this.labelMantCliente = "Ingresar";
                this.estado = "A";
            } else {
                String[] subCliente = this.cliente.split(" ");
//                if (subCliente.length == 3) {
                    this.clienteObj = this.dispClienteFacade.findByNames(cliente, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                    //this.clienteObj = this.dispClienteFacade.findByNames(subCliente[0], subCliente[1], subCliente[2], this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                    if (this.clienteObj == null) {
                        this.labelMantCliente = "Ingresar";
                        this.clienteObj = new DispCliente();
                        if(subCliente.length>=3){
                            this.clienteObj.setApaterno(subCliente[subCliente.length-3]);
                            this.clienteObj.setAmaterno(subCliente[subCliente.length-2]);
                        }
                        
                        if(subCliente.length==2){
                            this.clienteObj.setApaterno(subCliente[subCliente.length-2]);
                        }
                        
                        this.clienteObj.setNombre(subCliente[subCliente.length-1]);
                    } else {
                        this.labelMantCliente = "Actualizar";
                    }
                    this.estado = "A";
//                } else {
//                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La informacion ingresada no es correcta debe constar Apellido Paterno, Apellido Materno y nombre");
//                    PrimeFaces.current().executeScript("PF('wdlgIngresarCliente').hide();");
//                }
            }
        } catch (Exception exception) {
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printInfoAgendamiento(int idAgendamiento, int idServicio) {
        FacesMessage msg = null;
        try {
            if (idAgendamiento > 0 && idServicio > 0) {
                PrimeFaces.current().executeScript("window.open('../ServletAgendamiento?agendamientoID=" + idAgendamiento + "&servicioID=" + idServicio + "');");
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
        return this.clienteObj;
    }

    public void setClienteObj(DispCliente clienteObj) {
        this.clienteObj = clienteObj;
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

    public List<String> getListDias() {
        return this.listDias;
    }

    public void setListDias(List<String> listDias) {
        this.listDias = listDias;
    }

    public List<String> getListHoras() {
        return this.listHoras;
    }

    public void setListHoras(List<String> listHoras) {
        this.listHoras = listHoras;
    }

    public String getDisponibilidad() {
        return this.disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getNumFilas() {
        return this.numFilas;
    }

    public void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }

    public List<EstadoDia> getListaEstadoDia() {
        return this.listaEstadoDia;
    }

    public void setListaEstadoDia(List<EstadoDia> listaEstadoDia) {
        this.listaEstadoDia = listaEstadoDia;
    }

    public EstadoDia getEstadoDiaObj() {
        return this.estadoDiaObj;
    }

    public void setEstadoDiaObj(EstadoDia estadoDiaObj) {
        this.estadoDiaObj = estadoDiaObj;
    }

    public DataTable getTable() {
        return this.table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public Evento getEvento() {
        return this.evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public ScheduleModel getModel() {
        return this.model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }

    public List<DispEspecialidad> getListDispEspecialidad() {
        return this.listDispEspecialidad;
    }

    public void setListDispEspecialidad(List<DispEspecialidad> listDispEspecialidad) {
        this.listDispEspecialidad = listDispEspecialidad;
    }

    public DispEspecialidad getEspecialidadoObj() {
        return this.especialidadoObj;
    }

    public void setEspecialidadoObj(DispEspecialidad especialidadoObj) {
        this.especialidadoObj = especialidadoObj;
    }

    public String getScrollTime() {
        return this.scrollTime;
    }

    public void setScrollTime(String scrollTime) {
        this.scrollTime = scrollTime;
    }

    public String getMinTime() {
        return this.minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getMaxTime() {
        return this.maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getSlotDuration() {
        return this.slotDuration;
    }

    public void setSlotDuration(String slotDuration) {
        this.slotDuration = slotDuration;
    }

    public String getView() {
        return this.view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getSlotMinutes() {
        return this.slotMinutes;
    }

    public void setSlotMinutes(String slotMinutes) {
        this.slotMinutes = slotMinutes;
    }

    public ScheduleEvent getEvent() {
        return this.event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public String getCliente() {
        return this.cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<DispAgendamiento> getListDispAgendamiento() {
        return this.listDispAgendamiento;
    }

    public void setListDispAgendamiento(List<DispAgendamiento> listDispAgendamiento) {
        this.listDispAgendamiento = listDispAgendamiento;
    }

    public List<DispMedicoPersonal> getListDispMedicoPersonal() {
        return this.listDispMedicoPersonal;
    }

    public void setListDispMedicoPersonal(List<DispMedicoPersonal> listDispMedicoPersonal) {
        this.listDispMedicoPersonal = listDispMedicoPersonal;
    }

    public DispMedicoPersonal getMedicoPersonalObj() {
        return this.medicoPersonalObj;
    }

    public void setMedicoPersonalObj(DispMedicoPersonal medicoPersonalObj) {
        this.medicoPersonalObj = medicoPersonalObj;
    }

    public List<DispServicio> getListDispServicio() {
        return this.listDispServicio;
    }

    public void setListDispServicio(List<DispServicio> listDispServicio) {
        this.listDispServicio = listDispServicio;
    }

    public DispServicio getServicioObj() {
        return this.servicioObj;
    }

    public void setServicioObj(DispServicio servicioObj) {
        this.servicioObj = servicioObj;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLabelMantCliente() {
        return this.labelMantCliente;
    }

    public void setLabelMantCliente(String labelMantCliente) {
        this.labelMantCliente = labelMantCliente;
    }

    public String getShowWeekend() {
        return this.showWeekend;
    }

    public void setShowWeekend(String showWeekend) {
        this.showWeekend = showWeekend;
    }

    public DispAgendamiento getDispAgendamiento() {
        return this.dispAgendamiento;
    }

    public void setDispAgendamiento(DispAgendamiento dispAgendamiento) {
        this.dispAgendamiento = dispAgendamiento;
    }

    public DispOrigen getOrigenObj() {
        return this.origenObj;
    }

    public void setOrigenObj(DispOrigen origenObj) {
        this.origenObj = origenObj;
    }

    public List<DispOrigen> getListDispOrigen() {
        return this.listDispOrigen;
    }

    public void setListDispOrigen(List<DispOrigen> listDispOrigen) {
        this.listDispOrigen = listDispOrigen;
    }

    public InfoAgendamiento getInfoAgendamiento() {
        return this.infoAgendamiento;
    }

    public void setInfoAgendamiento(InfoAgendamiento infoAgendamiento) {
        this.infoAgendamiento = infoAgendamiento;
    }

    public BigDecimal getPrecioCita() {
        return this.precioCita;
    }

    public void setPrecioCita(BigDecimal precioCita) {
        this.precioCita = precioCita;
    }

    public String getPagado() {
        return this.pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }
}
