package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispTriaje;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispConsultorioFacade;
import com.Session.DispTriajeFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyTriajeModel;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("triajeMB")
@ViewScoped
public class TriajeMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispTriajeFacade dispTriajeFacade;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    DispConsultorioFacade dispConsultorioFacade;

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

    private List<DispTriaje> listDispTriaje = new ArrayList<>();

    private DispTriaje dispTriaje = new DispTriaje();

    private DispAgendamiento dispAgendamiento = new DispAgendamiento();

    private List<DispAgendamiento> listDispAgendamiento = new ArrayList<>();

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
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            String timeZone = isParametros.getValor();
            if (this.usuario.getIdEmpresa().getFlujoCompleto().equals("S")) {
                this.listDispAgendamiento = this.dispAgendamientoFacade.findAllTriaje(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            }
            try {
                this.dispAgendamiento = this.listDispAgendamiento.get(0);
            } catch (Exception exception) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iniciarLlamado() {
        TimerTask task = new TimerTask() {
            public void run() {
                TriajeMB.this.llamar();
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
        Date fecha = dataSelecionadaJoda.minusMinutes(this.DuracionCita).toDate();
        try {
            if (this.dispAgendamiento.getIdAgendamiento() != null && (this.dispAgendamiento.getEstado().equals("C") || this.dispAgendamiento.getEstado().equals("D") || this.dispAgendamiento.getEstado().equals("RT"))) {
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
                    this.dispAgendamiento.setEstado("D");
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                    this.listDispAgendamiento = this.dispAgendamientoFacade.findAllTriaje(fecha, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
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
            if (!this.listDispAgendamiento.isEmpty() && (this.dispAgendamiento.getEstado().equals("L") || this.dispAgendamiento.getEstado().equals("D"))) {
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

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispTriaje.getIdTriaje() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (guardar) {
                this.dispAgendamiento.setFechaAtendido(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispAgendamiento.setEstado("AT");
                this.dispAgendamiento.setUsuarioModificacion(this.usuario.getUsuario());
                this.dispAgendamiento.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                this.dispAgendamientoFacade.flush();
                this.dispTriaje.setEstado("A");
                this.dispTriaje.setIdAgendamiento(this.dispAgendamiento);
                this.dispTriaje.setIdEmpresa(this.usuario.getIdEmpresa());
                this.dispTriaje.setIdCiudad(this.usuario.getIdCiudad());
                this.dispTriaje.setIdSector(this.usuario.getIdSector());
                this.dispTriaje.setUsuarioIngreso(this.usuario.getUsuario());
                this.dispTriaje.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispTriajeFacade.createWithValidator(this.dispTriaje);
                this.dispTriajeFacade.flush();
                this.listDispAgendamiento = this.dispAgendamientoFacade.findAllTriaje(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                if (!this.listDispAgendamiento.isEmpty()) {
                    this.dispAgendamiento = this.listDispAgendamiento.get(0);
                }
            } else {
                this.dispTriaje.setUsuarioModificacion(this.usuario.getUsuario());
                this.dispTriaje.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispTriajeFacade.editWithValidator(this.dispTriaje);
                this.dispTriajeFacade.flush();
            }
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
            redireccionar();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
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
                    this.dispAgendamiento.setFechaLlamada(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispAgendamiento.setEstado("L");
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                    this.listDispAgendamiento = new ArrayList<>();
                    this.listDispAgendamiento = this.dispAgendamientoFacade.findAllTriaje(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
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
                this.dispAgendamiento.setFechaInicioAtencion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispAgendamiento.setEstado("IA");
                this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                this.dispAgendamientoFacade.flush();
                this.userTransaction.commit();
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No existen reservaciones");
            }
            this.listDispAgendamiento = this.dispAgendamientoFacade.findAllTriaje(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void abandonar() {
        FacesMessage msg = null;
        try {
            if (!this.listDispAgendamiento.isEmpty()) {
                if (this.dispAgendamiento.getIdAgendamiento() != null) {
                    this.userTransaction.begin();
                    this.dispAgendamiento.setFechaFinalizado(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispAgendamiento.setEstado("A");
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se ha llamdo a ningun paciente");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No existen reservaciones");
            }
            this.listDispAgendamiento = this.dispAgendamientoFacade.findAllTriaje(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            try {
                this.dispAgendamiento = this.listDispAgendamiento.get(0);
            } catch (Exception exception) {
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
                    this.dispAgendamiento.setEstado("FT");
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se ha llamdo a ningun paciente");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No existen reservaciones");
            }
            this.listDispAgendamiento = this.dispAgendamientoFacade.findAllTriaje(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            try {
                this.dispAgendamiento = this.listDispAgendamiento.get(0);
            } catch (Exception exception) {
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void redireccionar() {
        try {
            this.dispTriaje = new DispTriaje();
            this.estado = "A";
            this.labelMant = "Ingresar";
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

    public LazyDataModel<DispTriaje> getAll() {
        if (this.lazyDispTriaje == null) {
            this.lazyDispTriaje = new LazyTriajeModel(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispTriaje;
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
}
