package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispAntecedentes;
import com.Entity.DispCliente;
import com.Entity.DispDetalleDiagnostico;
import com.Entity.DispDetalleReceta;
import com.Entity.DispDiagnostico;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispReceta;
import com.Entity.DispResultado;
import com.Entity.DispResultadoDetalle;
import com.Entity.DispResultadoExamen;
import com.Entity.DispSolicitudExamen;
import com.Entity.DispTriaje;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispAntecedentesFacade;
import com.Session.DispDetalleDiagnosticoFacade;
import com.Session.DispDetalleRecetaFacade;
import com.Session.DispDiagnosticoFacade;
import com.Session.DispMedicoPersonalFacade;
import com.Session.DispRecetaFacade;
import com.Session.DispResultadoFacade;
import com.Session.DispTriajeFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyAntecedentesModel;
import com.integrasystemsonline.Utilidades.LazyTriajeModel;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("triajeMedicoMB")
@ViewScoped
public class TriajeMedicoMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispTriajeFacade dispTriajeFacade;

    @EJB
    DispAntecedentesFacade dispAntecedentesFacade;

    @EJB
    DispDiagnosticoFacade dispDiagnosticoFacade;

    @EJB
    DispDetalleDiagnosticoFacade dispDetalleDiagnosticoFacade;

    @EJB
    DispResultadoFacade dispResultadoFacade;

    @EJB
    DispMedicoPersonalFacade dispMedicoPersonalFacade;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    DispRecetaFacade dispRecetaFacade;

    @EJB
    DispDetalleRecetaFacade dispDetalleRecetaFacade;

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

    private DispTriaje dispTriaje = new DispTriaje();

    private List<DispTriaje> listDispTriaje = new ArrayList<>();

    private DispAgendamiento dispAgendamiento = new DispAgendamiento();

    private List<DispAgendamiento> listDispAgendamiento = new ArrayList<>();

    private DispDiagnostico dispDiagnostico = new DispDiagnostico();

    private DispDetalleDiagnostico dispDetalleDiagnostico = new DispDetalleDiagnostico();

    private DispResultado dispResultado = new DispResultado();

    private DispResultadoDetalle dispResultadoDetalle = new DispResultadoDetalle();

    private DispResultadoExamen dispResultadoExamen = new DispResultadoExamen();

    private DispSolicitudExamen dispSolicitudExamen = new DispSolicitudExamen();

    private DispCliente dispCliente = new DispCliente();

    private DispAntecedentes dispAntecedentes = new DispAntecedentes();

    DispMedicoPersonal medico = new DispMedicoPersonal();

    private DispReceta dispReceta = new DispReceta();

    private DispDetalleReceta dispDetalleReceta = new DispDetalleReceta();

    private List<DispDetalleReceta> listDispDetalleReceta = new ArrayList<>();

    private UIData dataTable;

    private LazyDataModel<DispTriaje> lazyDispTriaje;

    private String filtroConsulta;

    IsUsuarios usuario;

    private String estado;

    private String labelMant;

    private List<Estado> listaEstado;

    private Estado estadoObj;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    private Date fechaActual;

    private Date fechaIni;

    private Date fechaFin;

    private int tiempoEspera;

    private int tiempoCita;

    private int DuracionCita;

    private String NArchivo;

    private byte[] arch;

    private LazyDataModel<DispAntecedentes> lazyDispAntecedentes;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    private String timeZone = "";

    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            this.estado = "A";
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
            IsParametros parametros = null;
            parametros = this.isParametrosFacade.findByCodigo("timePlus", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.DuracionCita = Integer.parseInt(parametros.getValor());
            parametros = this.isParametrosFacade.findByCodigo("tiempoEspera", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.tiempoEspera = Integer.parseInt(parametros.getValor());
            parametros = this.isParametrosFacade.findByCodigo("duracionCita", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.tiempoCita = Integer.parseInt(parametros.getValor());
            parametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = parametros.getValor();
            this.fechaActual = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
            parametros = this.isParametrosFacade.findByCodigo("minTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            String ini = parametros.getValor();
            String[] partesHoraIni = ini.split(":");
            int horaIni = Integer.parseInt(partesHoraIni[0]);
            int minutosIni = Integer.parseInt(partesHoraIni[1]);
            DateTime dataSelecionadaJoda = new DateTime(this.fechaActual.getTime());
            this.fechaIni = dataSelecionadaJoda.withHourOfDay(horaIni).withMinuteOfHour(minutosIni).withSecondOfMinute(0).toDate();
            parametros = this.isParametrosFacade.findByCodigo("maxTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            String fin = parametros.getValor();
            String[] partesHoraFin = fin.split(":");
            int horaFin = Integer.parseInt(partesHoraFin[0]);
            int minutosFin = Integer.parseInt(partesHoraFin[1]);
            dataSelecionadaJoda = new DateTime(this.fechaActual.getTime());
            this.fechaFin = dataSelecionadaJoda.withHourOfDay(horaFin).withMinuteOfHour(minutosFin).withSecondOfMinute(0).toDate();
            this.medico = this.dispMedicoPersonalFacade.findByIdUsuario(this.usuario.getIdUsuarios());
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            String timeZone = isParametros.getValor();
            if (this.usuario.getIdEmpresa().getFlujoCompleto().equals("S")) {
                if (this.usuario.getIdRoles().getNombre().toUpperCase().equals("MEDICO")) {
                    this.listDispAgendamiento = this.dispAgendamientoFacade.findAllAtendidoTriaje(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                }
            } else {
                this.listDispAgendamiento = this.dispAgendamientoFacade.findAllMedico(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            }
            if (!this.listDispAgendamiento.isEmpty()) {
                this.dispAgendamiento = this.listDispAgendamiento.get(0);
                this.dispCliente = this.dispAgendamiento.getIdCliente();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iniciarLlamado() {
        TimerTask task = new TimerTask() {
            public void run() {
                TriajeMedicoMB.this.llamar();
            }
        };
        Timer timer = new Timer();
        long delay = 500L;
        timer.schedule(task, delay);
    }

    public void refreshListAgendamiento() {
        FacesMessage msg = null;
        Date fechaActual = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
        DateTime dataSelecionadaJoda = new DateTime(fechaActual.getTime());
        try {
            if (this.dispAgendamiento.getIdAgendamiento() != null && (this.dispAgendamiento.getEstado().equals("AT") || this.dispAgendamiento.getEstado().equals("DM") || this.dispAgendamiento.getEstado().equals("RT"))) {
                Date fechaCita = this.dispAgendamiento.getFecha();
                long diff = fechaCita.getTime() - fechaActual.getTime();
                long diffSeconds = diff / 1000L;
                long diffMinutes = diff / 60000L;
                long diffHours = diff / 3600000L;
                System.out.println("Time in segundos: " + diffSeconds + " segundos. id " + this.dispAgendamiento.getIdAgendamiento());
                if (diffSeconds > this.tiempoEspera) {
                    System.out.println("Estado libre");
                } else if (diffSeconds > -this.tiempoCita && diffSeconds <= this.tiempoCita) {
                    System.out.println("Estado disponible");
                    this.userTransaction.begin();
                    this.dispAgendamiento.setEstado("DM");
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                    if (this.usuario.getIdEmpresa().getFlujoCompleto().equals("S")) {
                        this.listDispAgendamiento = this.dispAgendamientoFacade.findAllAtendidoTriaje(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                    } else {
                        this.listDispAgendamiento = this.dispAgendamientoFacade.findAllMedico(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String mostrarTurno() {
        String turnoActual = "-";
        try {
            if (!this.listDispAgendamiento.isEmpty() && (this.dispAgendamiento.getEstado().equals("LM") || this.dispAgendamiento.getEstado().equals("DM"))) {
                if (this.dispAgendamiento.getTurno() == 0) {
                    turnoActual = this.dispAgendamiento.getIdCliente().getApaterno().toUpperCase().concat(" ").concat(this.dispAgendamiento.getIdCliente().getAmaterno().toUpperCase()).concat(" ").concat(this.dispAgendamiento.getIdCliente().getNombre().toUpperCase());
                } else {
                    turnoActual = this.dispAgendamiento.getIdEspecialidad().getCodigo().concat("-").concat(String.format("%03d", new Object[]{Integer.valueOf(this.dispAgendamiento.getTurno())}));
                }
            }
        } catch (Exception exception) {
        }
        return turnoActual;
    }

    public void cargarCodigo() {
        String codigo = this.dispAgendamiento.getIdMedicoPersonal().getIdEspecialidad().getCodigo() + this.dispAgendamiento.getIdMedicoPersonal().getApaterno().toUpperCase() + this.dispAgendamiento.getIdMedicoPersonal().getAmaterno().toUpperCase() + this.dispAgendamiento.getIdMedicoPersonal().getNombre().toUpperCase();
        this.dispDiagnostico.setCodigo(this.estado);
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispDiagnostico.getIdDiagnostico() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (guardar) {
                this.dispAgendamiento.setIntentos(0);
                this.dispAgendamiento.setFechaInicioMedico(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
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
                this.dispDetalleDiagnostico.setIdCliente(this.dispCliente);
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
            } else {
                this.dispDiagnostico.setUsuarioModificacion(this.usuario.getUsuario());
                this.dispDiagnostico.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispDiagnosticoFacade.editWithValidator(this.dispDiagnostico);
                this.dispDiagnosticoFacade.flush();
            }
            this.userTransaction.commit();
            cargarArchivos(this.NArchivo, this.arch);
            redireccionarReceta();
            redireccionar();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
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
            redireccionarAntecedentes();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void guardarReceta() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispReceta.getIdReceta() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (guardar) {
                this.dispReceta.setIdAgendamiento(this.dispAgendamiento);
                this.dispReceta.setEstado("A");
                this.dispReceta.setIdEmpresa(this.usuario.getIdEmpresa());
                this.dispReceta.setIdCiudad(this.usuario.getIdCiudad());
                this.dispReceta.setIdSector(this.usuario.getIdSector());
                this.dispReceta.setUsuarioIngreso(this.usuario.getUsuario());
                this.dispReceta.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispRecetaFacade.createWithValidator(this.dispReceta);
                this.dispRecetaFacade.flush();
            } else {
                this.dispReceta.setUsuarioModificacion(this.usuario.getUsuario());
                this.dispReceta.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispRecetaFacade.editWithValidator(this.dispReceta);
                this.dispRecetaFacade.flush();
            }
            this.userTransaction.commit();
            generarPDFReceta(this.dispReceta.getIdReceta());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void generarPDFReceta(int idAgendamiento) {
        FacesMessage msg = null;
        try {
            if (idAgendamiento > 0) {
                PrimeFaces.current().executeScript("window.open('../ServletReceta?agendamientoID=" + idAgendamiento + "');");
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

    public String condicionintentos() {
        String resultado = "false";
        if (this.dispAgendamiento.getIdAgendamiento() != null) {
            if (this.dispAgendamiento.getIntentos() > 2) {
                resultado = "true";
            } else {
                resultado = "false";
            }
        }
        return resultado;
    }

    public void llamar() {
        FacesMessage msg = null;
        try {
            if (!this.listDispAgendamiento.isEmpty()) {
                this.dispAgendamiento = this.listDispAgendamiento.get(0);
                if (this.dispAgendamiento.getIntentos() < 3) {
                    this.userTransaction.begin();
                    this.dispAgendamiento.setIntentos(this.dispAgendamiento.getIntentos() + 1);
                    this.dispAgendamiento.setFechaLlamarMedico(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispAgendamiento.setEstado("LM");
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                    this.listDispAgendamiento = new ArrayList<>();
                    if (this.usuario.getIdEmpresa().getFlujoCompleto().equals("S")) {
                        this.listDispAgendamiento = this.dispAgendamientoFacade.findAllAtendidoTriaje(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                    } else {
                        this.listDispAgendamiento = this.dispAgendamientoFacade.findAllMedico(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                    }
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No existen reservaciones");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void inicioAtencion() {
        FacesMessage msg = null;
        try {
            if (!this.listDispAgendamiento.isEmpty()) {
                this.userTransaction.begin();
                this.dispAgendamiento.setIntentos(0);
                this.dispAgendamiento.setFechaInicioMedico(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispAgendamiento.setEstado("IM");
                this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                this.dispAgendamientoFacade.flush();
                this.userTransaction.commit();
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No existen reservaciones");
            }
            if (this.usuario.getIdEmpresa().getFlujoCompleto().equals("S")) {
                this.listDispAgendamiento = this.dispAgendamientoFacade.findAllAtendidoTriaje(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            } else {
                this.listDispAgendamiento = this.dispAgendamientoFacade.findAllMedico(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void finalizar() {
        FacesMessage msg = null;
        try {
            if (!this.listDispAgendamiento.isEmpty()) {
                if (this.dispAgendamiento.getIdAgendamiento() != null) {
                    this.userTransaction.begin();
                    this.dispAgendamiento.setFechaFinalizado(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispAgendamiento.setEstado("FM");
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se ha llamdo a ningun paciente");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No existen reservaciones");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            if (this.usuario.getIdEmpresa().getFlujoCompleto().equals("S")) {
                this.listDispAgendamiento = this.dispAgendamientoFacade.findAllAtendidoTriaje(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            } else {
                this.listDispAgendamiento = this.dispAgendamientoFacade.findAllMedico(this.fechaIni, this.fechaFin, this.medico.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            }
            try {
                this.dispAgendamiento = this.listDispAgendamiento.get(0);
            } catch (Exception exception) {
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void redireccionarReceta() {
        try {
            this.dispReceta = new DispReceta();
            this.listDispDetalleReceta = new ArrayList<>();
            this.dispDetalleReceta = new DispDetalleReceta();
        } catch (Exception exception) {
        }
    }

    public void redireccionar() {
        try {
            this.dispTriaje = new DispTriaje();
            this.dispDiagnostico = new DispDiagnostico();
            this.dispResultado = new DispResultado();
            this.dispDetalleDiagnostico = new DispDetalleDiagnostico();
            this.estado = "A";
            this.labelMant = "Ingresar";
        } catch (Exception exception) {
        }
    }

    public void redireccionarAntecedentes() {
        try {
            this.dispAntecedentes = new DispAntecedentes();
            this.dispAntecedentes.setIdCliente(this.dispAgendamiento.getIdCliente());
        } catch (Exception exception) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.dispTriajeFacade.remove(this.dispTriaje);
            this.dispTriajeFacade.flush();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispTriaje = (DispTriaje) event.getObject();
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redireccionarRegistroDiagnostico() {
        try {
            inicioAtencion();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispAgendamiento", this.dispAgendamiento);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_registro_diagnostico.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(TriajeMedicoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            this.NArchivo = event.getFile().getFileName();
            this.arch = event.getFile().getContents();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se cargo el archivo con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void cargarArchivos(String nombre, byte[] archivo) {
        try {
            if (archivo != null) {
                ByteArrayInputStream is = new ByteArrayInputStream(archivo);
                String ruta = this.propParam.getProperty("rutaArchivo");
                FileOutputStream fileOuputStream = new FileOutputStream(ruta + nombre);
                fileOuputStream.write(archivo);
                fileOuputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<DispTriaje> getAll() {
        if (this.lazyDispTriaje == null) {
            this.lazyDispTriaje = new LazyTriajeModel(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispTriaje;
    }

    public LazyDataModel<DispAntecedentes> getAllAntecedentes() {
        if (this.lazyDispAntecedentes == null && this.dispCliente.getIdCliente() != null) {
            this.lazyDispAntecedentes = new LazyAntecedentesModel(this.dispCliente.getIdCliente(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), "A");
        }
        return this.lazyDispAntecedentes;
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispTriaje> getListDispTriaje() {
        return this.listDispTriaje;
    }

    public void setListDispTriaje(List<DispTriaje> listDispTriaje) {
        this.listDispTriaje = listDispTriaje;
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

    public DispTriaje getDispTriaje() {
        return this.dispTriaje;
    }

    public void setDispTriaje(DispTriaje dispTriaje) {
        this.dispTriaje = dispTriaje;
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

    public List<DispAgendamiento> getListDispAgendamiento() {
        return this.listDispAgendamiento;
    }

    public void setListDispAgendamiento(List<DispAgendamiento> listDispAgendamiento) {
        this.listDispAgendamiento = listDispAgendamiento;
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

    public DispResultadoDetalle getDispResultadoDetalle() {
        return this.dispResultadoDetalle;
    }

    public void setDispResultadoDetalle(DispResultadoDetalle dispResultadoDetalle) {
        this.dispResultadoDetalle = dispResultadoDetalle;
    }

    public DispResultadoExamen getDispResultadoExamen() {
        return this.dispResultadoExamen;
    }

    public void setDispResultadoExamen(DispResultadoExamen dispResultadoExamen) {
        this.dispResultadoExamen = dispResultadoExamen;
    }

    public DispSolicitudExamen getDispSolicitudExamen() {
        return this.dispSolicitudExamen;
    }

    public void setDispSolicitudExamen(DispSolicitudExamen dispSolicitudExamen) {
        this.dispSolicitudExamen = dispSolicitudExamen;
    }

    public DispAntecedentes getDispAntecedentes() {
        return this.dispAntecedentes;
    }

    public void setDispAntecedentes(DispAntecedentes dispAntecedentes) {
        this.dispAntecedentes = dispAntecedentes;
    }

    public String getNArchivo() {
        return this.NArchivo;
    }

    public void setNArchivo(String NArchivo) {
        this.NArchivo = NArchivo;
    }

    public DispReceta getDispReceta() {
        return this.dispReceta;
    }

    public void setDispReceta(DispReceta dispReceta) {
        this.dispReceta = dispReceta;
    }

    public DispDetalleReceta getDispDetalleReceta() {
        return this.dispDetalleReceta;
    }

    public void setDispDetalleReceta(DispDetalleReceta dispDetalleReceta) {
        this.dispDetalleReceta = dispDetalleReceta;
    }

    public List<DispDetalleReceta> getListDispDetalleReceta() {
        return this.listDispDetalleReceta;
    }

    public void setListDispDetalleReceta(List<DispDetalleReceta> listDispDetalleReceta) {
        this.listDispDetalleReceta = listDispDetalleReceta;
    }

    public Date getFechaActual() {
        return this.fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
}
