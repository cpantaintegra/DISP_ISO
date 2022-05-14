package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispTriaje;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispTriajeFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.IOException;
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
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.joda.time.DateTime;
import org.primefaces.model.LazyDataModel;

@Named("recuperarTurnoMB")
@SessionScoped
public class RecuperarTurnoMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispTriajeFacade dispTriajeFacade;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

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

    private Date fechaActual = new Date();

    private Date fechaFin = new Date();

    private Date fechaIni = new Date();

    private int tiempoEspera;

    private int tiempoCita;

    private int DuracionCita;

    private int timePlus;

    private String minTime = "";

    private String maxTime = "";

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    private String timeZone = "";

    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
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
            isParametros = this.isParametrosFacade.findByCodigo("timePlus", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.DuracionCita = Integer.parseInt(isParametros.getValor());
            isParametros = this.isParametrosFacade.findByCodigo("tiempoEspera", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.tiempoEspera = Integer.parseInt(isParametros.getValor());
            isParametros = this.isParametrosFacade.findByCodigo("tiempoEspera", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.tiempoCita = Integer.parseInt(isParametros.getValor());
            isParametros = this.isParametrosFacade.findByCodigo("timePlus", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timePlus = Integer.parseInt(isParametros.getValor());
            this.fechaActual = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
            DateTime dataSelecionadaJoda = new DateTime(this.fechaActual.getTime());
            isParametros = this.isParametrosFacade.findByCodigo("minTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.minTime = isParametros.getValor();
            String[] partesHoraIni = this.minTime.split(":");
            int horaIni = Integer.parseInt(partesHoraIni[0]);
            int minutosIni = Integer.parseInt(partesHoraIni[1]);
            this.fechaIni = dataSelecionadaJoda.withHourOfDay(horaIni).withMinuteOfHour(minutosIni).withSecondOfMinute(0).toDate();
            isParametros = this.isParametrosFacade.findByCodigo("maxTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.maxTime = isParametros.getValor();
            String[] partesHora = this.maxTime.split(":");
            int horaFin = Integer.parseInt(partesHora[0]);
            int minutosFin = Integer.parseInt(partesHora[1]);
            this.fechaFin = dataSelecionadaJoda.withHourOfDay(horaFin).withMinuteOfHour(minutosFin).withSecondOfMinute(0).toDate();
            refrescarLista();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshListAgendamiento() {
        FacesMessage msg = null;
        DateTime dataSelecionadaJoda = new DateTime(this.fechaActual.getTime());
        Date fecha = dataSelecionadaJoda.minusMinutes(this.DuracionCita).toDate();
        try {
            if (this.dispAgendamiento.getIdAgendamiento() != null
                    && this.dispAgendamiento.getEstado().equals("F")) {
                Date fechaCita = this.dispAgendamiento.getFecha();
                long diff = fechaCita.getTime() - this.fechaActual.getTime();
                long diffSeconds = diff / 1000L;
                long diffMinutes = diff / 60000L;
                long diffHours = diff / 3600000L;
                System.out.println("Time in segundos: " + diffSeconds + " segundos. id " + this.dispAgendamiento.getIdAgendamiento());
                if (diffSeconds > this.tiempoEspera) {
                    System.out.println("Estado libre");
                } else if (diffSeconds > -this.tiempoCita && diffSeconds <= this.tiempoCita) {
                    System.out.println("Estado disponible");
                    this.userTransaction.begin();
                    if (this.dispAgendamiento.getFechaAtendido() == null) {
                        this.dispAgendamiento.setEstado("D");
                    } else {
                        this.dispAgendamiento.setEstado("DM");
                    }
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                    fecha = Utilidades.obtenerFechaZonaHoraria(fecha, "0", this.timeZone);
                    this.listDispAgendamiento = this.dispAgendamientoFacade.findAllFinalizados(fecha, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                    try {
                        this.dispAgendamiento = this.listDispAgendamiento.get(0);
                    } catch (Exception exception) {
                    }
                    redireccionar();
                } else if (diffSeconds <= -this.tiempoCita) {
                    System.out.println("Estado expirado");
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Estado Finalizado " + this.dispAgendamiento.getIdAgendamiento());
                    this.userTransaction.begin();
                    this.dispAgendamiento.setFechaFinalizado(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispAgendamiento.setEstado("F");
                    this.dispAgendamientoFacade.editWithValidator(this.dispAgendamiento);
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                    this.listDispAgendamiento = this.dispAgendamientoFacade.findAllFinalizados(this.fechaActual, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                    try {
                        this.dispAgendamiento = this.listDispAgendamiento.get(0);
                    } catch (Exception exception) {
                    }
                    redireccionar();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void guardar(DispAgendamiento agendamiento) throws SystemException {
        FacesMessage msg = null;
        try {
            Date fechaCorrecion = null;
            List<DispAgendamiento> listAgendamiento = new ArrayList<>();
            DispAgendamiento agendamientoObj = null;
            Date now = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
            DateTime fechaCita = new DateTime(now.getTime());
            int duracion = this.timePlus;
            int hora = fechaCita.getHourOfDay();
            int minuto = fechaCita.getMinuteOfHour();
            int residuo = 0;
            if (duracion > 0) {
                residuo = minuto % duracion;
            }
            int diff = duracion - residuo;
            if (minuto + diff == 60) {
                hora++;
                diff = 0;
                minuto = 0;
            } else if (minuto + diff + duracion == 60) {
                hora++;
                diff = 0;
                minuto = 0;
                duracion = 0;
            }
            fechaCorrecion = fechaCita.withHourOfDay(hora).withMinuteOfHour(minuto + diff + duracion).withSecondOfMinute(0).toDate();
            Date fechaNueva = fechaCita.withHourOfDay(hora).withMinuteOfHour(minuto + diff).withSecondOfMinute(0).toDate();
            if (agendamiento.getFechaAtendido() == null) {
                listAgendamiento = this.dispAgendamientoFacade.findAllCreated(fechaNueva, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            } else {
                listAgendamiento = this.dispAgendamientoFacade.findAllAtendidoTriaje(fechaNueva, this.fechaFin, null, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            }
            aumentarTiempo(listAgendamiento);
            this.userTransaction.begin();
            agendamiento.setFechaRecuperacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
            agendamiento.setFecha(this.objSDF.parse(this.objSDF.format(fechaNueva)));
            agendamiento.setEstado("RT");
            agendamiento.setIntentos(0);
            agendamiento.setUsuarioModificacion(this.usuario.getUsuario());
            agendamiento.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
            this.dispAgendamientoFacade.editWithValidator(agendamiento);
            this.dispAgendamientoFacade.flush();
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Turno recuperado");
            this.listDispAgendamiento = this.dispAgendamientoFacade.findAllFinalizados(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        if (msg != null) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void aumentarTiempo(List<DispAgendamiento> listAgendamiento) {
        try {
            List<DispAgendamiento> lstTemp = new ArrayList<>();
            DispAgendamiento dispAgendamientoObj = null;
            for (int i = 0; i < listAgendamiento.size(); i++) {
                Date now = new Date();
                DateTime fechaCita = new DateTime(((DispAgendamiento) listAgendamiento.get(i)).getFecha().getTime());
                int duracion = this.timePlus;
                int hora = fechaCita.getHourOfDay();
                int minuto = fechaCita.getMinuteOfHour();
                if (minuto + duracion == 60) {
                    hora++;
                    minuto = 0;
                }
                Date nuevaFecha = fechaCita.withHourOfDay(hora).withMinuteOfHour(minuto + duracion).withSecondOfMinute(0).toDate();
                dispAgendamientoObj = this.dispAgendamientoFacade.findByFecha(nuevaFecha, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                if (dispAgendamientoObj != null) {
                    if (dispAgendamientoObj.getEstado().equals("D") || dispAgendamientoObj.getEstado().equals("DM")) {
                        nuevaFecha = fechaCita.plusMinutes(duracion).toDate();
                    }
                    this.userTransaction.begin();
                    ((DispAgendamiento) listAgendamiento.get(i)).setFecha(this.objSDF.parse(this.objSDF.format(nuevaFecha)));
                    ((DispAgendamiento) listAgendamiento.get(i)).setUsuarioModificacion(this.usuario.getUsuario());
                    ((DispAgendamiento) listAgendamiento.get(i)).setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    lstTemp.add(listAgendamiento.get(i));
                    this.dispAgendamientoFacade.editWithValidator(listAgendamiento.get(i));
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                } else {
                    this.userTransaction.begin();
                    ((DispAgendamiento) listAgendamiento.get(i)).setFecha(this.objSDF.parse(this.objSDF.format(nuevaFecha)));
                    ((DispAgendamiento) listAgendamiento.get(i)).setUsuarioModificacion(this.usuario.getUsuario());
                    ((DispAgendamiento) listAgendamiento.get(i)).setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    lstTemp.add(listAgendamiento.get(i));
                    this.dispAgendamientoFacade.editWithValidator(listAgendamiento.get(i));
                    this.dispAgendamientoFacade.flush();
                    this.userTransaction.commit();
                    break;
                }
            }
        } catch (Exception exception) {
        }
    }

    public List<DispAgendamiento> cargarLista() {
        this.listDispAgendamiento = this.dispAgendamientoFacade.findAllFinalizados(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        return this.listDispAgendamiento;
    }

    public void refrescarLista() {
        this.listDispAgendamiento = this.dispAgendamientoFacade.findAllFinalizados(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        try {
            this.dispAgendamiento = this.listDispAgendamiento.get(0);
        } catch (Exception exception) {
        }
    }

    public void redireccionar() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_recuperar_turno.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(RecuperarTurnoMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
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
