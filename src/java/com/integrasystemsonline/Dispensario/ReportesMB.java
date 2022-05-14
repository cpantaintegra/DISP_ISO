package com.integrasystemsonline.Dispensario;

import com.Entity.AgendaDiagnostico;
import com.Entity.DispAgendamiento;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Process.ModeloExcel;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.InfoAgendamiento;
import com.integrasystemsonline.Utilidades.Utilidades;
import com.integrasystemsonline.Ventas.ClienteMB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.joda.time.DateTime;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("reportesMB")
@ViewScoped
public class ReportesMB implements Serializable {

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

    private List<DispAgendamiento> listDispAgendamiento = new ArrayList<>();

    private List<InfoAgendamiento> listInfoAgendamiento = new ArrayList<>();

    private List<AgendaDiagnostico> lstAgendaDiagnostico = new ArrayList<>();

    private InfoAgendamiento infoAgendamiento;

    private IsUsuarios usuario;

    private String filtroConsulta;

    private String timePlus = "";

    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    private String nombArchivo;

    private String ruta;

    private StreamedContent file;

    private Date fechaini = new Date();

    private Date fechafin = new Date();

    private Estado estadoObj;

    private String pagado = "All";

    private List<Estado> listaEstado = new ArrayList<>();

    private boolean colNombrePaciente = true;

    private boolean colEspecialidad = true;

    private boolean colServicio = true;

    private boolean colDiagnostico = true;

    private boolean colMotivoConsulta = true;

    private boolean colFecha = true;

    private boolean colFechaAtendido = true;

    private boolean colFechaLlamada = true;

    private boolean colFechaInicioAtencion = true;

    private boolean colFechaInicioMedico = true;

    private boolean colFechaLlamarMedico = true;

    private boolean colFechaAtendidoMedico = true;

    private boolean colEstado = true;

    private SimpleDateFormat objSDFOnlyHour = new SimpleDateFormat("HH:mm:ss");

    private String timeZone = "";

    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
            this.fechaini = Utilidades.obtenerFechaZonaHoraria(this.fechaini, "0", this.timeZone);
            this.fechafin = Utilidades.obtenerFechaZonaHoraria(this.fechafin, "0", this.timeZone);
            this.listIsRolesPermisos = this.isRolesPermisosFacade.findByRol(this.usuario.getIdRoles());
            this.listaEstado = new ArrayList<>();
            Estado estado = new Estado();
            estado.setValor(null);
            estado.setDetalle("Todo");
            this.estadoObj = estado;
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("C");
            estado.setDetalle("Creado");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("D");
            estado.setDetalle("Disponible Triaje");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("L");
            estado.setDetalle("Llamado Triaje");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("IA");
            estado.setDetalle("Inicio Atencion Triaje");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("AT");
            estado.setDetalle("Atendido Triaje");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("DM");
            estado.setDetalle("Disponible Medico");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("LM");
            estado.setDetalle("Llamado Medico");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("IM");
            estado.setDetalle("Inicio Atencion Medico");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("AM");
            estado.setDetalle("Atendido Medico");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("FT");
            estado.setDetalle("Finalizado Triaje");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("FM");
            estado.setDetalle("Finalizado Medico");
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("RT");
            estado.setDetalle("Turno Recuperado");
            this.listaEstado.add(estado);
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
            IsParametros parametros = this.isParametrosFacade.findByCodigo("timePlus", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timePlus = parametros.getValor();
            this.listDispAgendamiento = this.dispAgendamientoFacade.findAllByEmpresa(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            for (int i = 0; i < this.listDispAgendamiento.size(); i++) {
                this.infoAgendamiento = new InfoAgendamiento();
                this.infoAgendamiento.setCliente(((DispAgendamiento) this.listDispAgendamiento.get(i)).getIdCliente().getApaterno() + " " + ((DispAgendamiento) this.listDispAgendamiento.get(i)).getIdCliente().getAmaterno() + " " + ((DispAgendamiento) this.listDispAgendamiento.get(i)).getIdCliente().getNombre());
                this.infoAgendamiento.setMedico(((DispAgendamiento) this.listDispAgendamiento.get(i)).getIdMedicoPersonal().getApaterno() + " " + ((DispAgendamiento) this.listDispAgendamiento.get(i)).getIdMedicoPersonal().getAmaterno() + " " + ((DispAgendamiento) this.listDispAgendamiento.get(i)).getIdMedicoPersonal().getNombre());
                this.infoAgendamiento.setFechaDesde(this.objSDF.format(((DispAgendamiento) this.listDispAgendamiento.get(i)).getFecha()));
                DateTime dateTime = new DateTime(((DispAgendamiento) this.listDispAgendamiento.get(i)).getFecha().getTime());
                Date fechaHasta = dateTime.plusMinutes(Integer.parseInt(this.timePlus)).toDate();
                this.infoAgendamiento.setFechaHasta(this.objSDF.format(fechaHasta));
                switch (((DispAgendamiento) this.listDispAgendamiento.get(i)).getEstado()) {
                    case "C":
                        this.infoAgendamiento.setEstado("Creado");
                        break;
                    case "D":
                        this.infoAgendamiento.setEstado("Disponible");
                        break;
                    case "L":
                        this.infoAgendamiento.setEstado("Llamado");
                        break;
                    case "IA":
                        this.infoAgendamiento.setEstado("Inicio Atencion");
                        break;
                    case "AT":
                        this.infoAgendamiento.setEstado("Atentido Triaje");
                        break;
                    case "DM":
                        this.infoAgendamiento.setEstado("Disponible Medico");
                        break;
                    case "LM":
                        this.infoAgendamiento.setEstado("Llamado Medico");
                        break;
                    case "IM":
                        this.infoAgendamiento.setEstado("Inicio Medico");
                        break;
                    case "AM":
                        this.infoAgendamiento.setEstado("Atendido Medico");
                        break;
                    case "F":
                        this.infoAgendamiento.setEstado("Perdido");
                        break;
                    case "RT":
                        this.infoAgendamiento.setEstado("Turno Recuperado");
                        break;
                }
                this.listInfoAgendamiento.add(this.infoAgendamiento);
            }
            DateTime dataSelecionadaJoda = new DateTime(this.fechaini.getTime());
            this.fechaini = dataSelecionadaJoda.withHourOfDay(0).withMinuteOfHour(0).toDate();
            this.fechafin = dataSelecionadaJoda.withHourOfDay(23).withMinuteOfHour(59).toDate();
            this.lstAgendaDiagnostico = this.dispAgendamientoFacade.listaAgendaDiagnostico(this.fechaini, this.fechafin, this.estadoObj.getValor(), this.pagado, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchByFilter() throws Exception {
        try {
            DateTime dataSelecionadaJoda = new DateTime(this.fechaini.getTime());
            this.fechaini = dataSelecionadaJoda.withHourOfDay(0).withMinuteOfHour(0).toDate();
            dataSelecionadaJoda = new DateTime(this.fechafin.getTime());
            this.fechafin = dataSelecionadaJoda.withHourOfDay(23).withMinuteOfHour(59).toDate();
            this.lstAgendaDiagnostico = this.dispAgendamientoFacade.listaAgendaDiagnostico(this.fechaini, this.fechafin, this.estadoObj.getValor(), this.pagado, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            System.out.println(this.lstAgendaDiagnostico.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StreamedContent cargaArchivo() {
        exportarAgendamiento();
        if (this.nombArchivo != null) {
            try {
                InputStream stream = new FileInputStream(this.ruta + this.nombArchivo + ".xlsx");
                this.file = (StreamedContent) new DefaultStreamedContent(stream, this.ruta + this.nombArchivo + ".xlsx", this.nombArchivo + ".xlsx");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, (String) null, ex);
            }
        }
        return this.file;
    }

    public void ocultarNombrePaciente() {
        try {
            if (this.colNombrePaciente == true) {
                this.colNombrePaciente = false;
            } else {
                this.colNombrePaciente = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarEspecialidad() {
        try {
            if (this.colEspecialidad == true) {
                this.colEspecialidad = false;
            } else {
                this.colEspecialidad = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarServicio() {
        try {
            if (this.colServicio == true) {
                this.colServicio = false;
            } else {
                this.colServicio = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarDiagnostico() {
        try {
            if (this.colDiagnostico == true) {
                this.colDiagnostico = false;
            } else {
                this.colDiagnostico = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarMotivoConsulta() {
        try {
            if (this.colMotivoConsulta == true) {
                this.colMotivoConsulta = false;
            } else {
                this.colMotivoConsulta = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarFecha() {
        try {
            if (this.colFecha == true) {
                this.colFecha = false;
            } else {
                this.colFecha = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarFechaAtendido() {
        try {
            if (this.colFechaAtendido == true) {
                this.colFechaAtendido = false;
            } else {
                this.colFechaAtendido = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarFechaAtendidoMedico() {
        try {
            if (this.colFechaAtendidoMedico == true) {
                this.colFechaAtendidoMedico = false;
            } else {
                this.colFechaAtendidoMedico = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarFechaInicioAtencion() {
        try {
            if (this.colFechaInicioAtencion == true) {
                this.colFechaInicioAtencion = false;
            } else {
                this.colFechaInicioAtencion = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarFechaInicioMedico() {
        try {
            if (this.colFechaInicioMedico == true) {
                this.colFechaInicioMedico = false;
            } else {
                this.colFechaInicioMedico = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarFechaLlamada() {
        try {
            if (this.colFechaLlamada == true) {
                this.colFechaLlamada = false;
            } else {
                this.colFechaLlamada = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarFechaLlamarMedico() {
        try {
            if (this.colFechaLlamarMedico == true) {
                this.colFechaLlamarMedico = false;
            } else {
                this.colFechaLlamarMedico = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarEstado() {
        try {
            if (this.colEstado == true) {
                this.colEstado = false;
            } else {
                this.colEstado = true;
            }
        } catch (Exception exception) {
        }
    }

    public void leerCampos(Object obj) {
        try {
            DispAgendamiento agendamiento = null;
            Field[] arrayOfField = agendamiento.getClass().getFields();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void exportar() {
        FacesMessage msg = null;
        try {
            ModeloExcel excelObj = new ModeloExcel();
            List<DispAgendamiento> lstAgendamiento = new ArrayList<>();
            List<AgendaDiagnostico> lstAgendaDiagnostico = new ArrayList<>();
            ArrayList<String> lstEncabezados = new ArrayList<>();
            IsParametros parametros = this.isParametrosFacade.findByCodigo("excel_agendamiento", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            String[] strColumnas = parametros.getValor().split(",");
            for (int i = 0; i < strColumnas.length; i++) {
                lstEncabezados.add(strColumnas[i]);
            }
            lstAgendaDiagnostico = this.dispAgendamientoFacade.listaAgendaDiagnostico(this.fechaini, this.fechafin, this.estadoObj.getValor(), this.pagado, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            lstAgendamiento = this.dispAgendamientoFacade.findAllByEmpresa(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            int row = lstAgendamiento.size();
            int col = lstEncabezados.size();
            String[][] strHistorial = new String[row][col];
            for (int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    if (((DispAgendamiento) lstAgendamiento.get(j)).getIdCliente() != null) {
                        strHistorial[j][0] = ((DispAgendamiento) lstAgendamiento.get(j)).getIdCliente().getApaterno() + " " + ((DispAgendamiento) lstAgendamiento.get(j)).getIdCliente().getAmaterno() + " " + ((DispAgendamiento) lstAgendamiento.get(j)).getIdCliente().getNombre();
                    } else {
                        strHistorial[j][0] = "";
                    }
                    if (((DispAgendamiento) lstAgendamiento.get(j)).getIdCliente() != null) {
                        strHistorial[j][1] = ((DispAgendamiento) lstAgendamiento.get(j)).getIdEspecialidad().getNombre();
                    } else {
                        strHistorial[j][1] = "";
                    }
                    if (((DispAgendamiento) lstAgendamiento.get(j)).getFecha() != null) {
                        DateTime dataSelecionadaJoda = new DateTime(((DispAgendamiento) lstAgendamiento.get(j)).getFecha().getTime());
                        Date fechaHasta = dataSelecionadaJoda.plusMinutes(Integer.parseInt(this.timePlus)).toDate();
                        String horario = this.objSDF.format(((DispAgendamiento) lstAgendamiento.get(j)).getFecha()) + " - " + this.objSDF.format(fechaHasta);
                        strHistorial[j][2] = horario;
                    } else {
                        strHistorial[j][2] = "";
                    }
                }
            }
            this.nombArchivo = "listado_Horarios_disponibles";
            excelObj.exportar(this.nombArchivo, this.ruta + this.nombArchivo + ".xlsx", lstEncabezados, strHistorial, col, row);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Export file success");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void exportarAgendamiento() {
        FacesMessage msg = null;
        try {
            ModeloExcel excelObj = new ModeloExcel();
            List<AgendaDiagnostico> lstAgendamiento = new ArrayList<>();
            ArrayList<String> lstEncabezados = new ArrayList<>();
            DateTime dataSelecionadaJoda = new DateTime(this.fechafin.getTime());
            this.fechafin = dataSelecionadaJoda.withHourOfDay(23).withMinuteOfHour(59).toDate();
            lstAgendamiento = this.dispAgendamientoFacade.listaAgendaDiagnostico(this.fechaini, this.fechafin, this.estadoObj.getValor(), this.pagado, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            AgendaDiagnostico agendaDiagnostico = new AgendaDiagnostico();
            Class<? extends AgendaDiagnostico> agendamientoInfo = (Class) agendaDiagnostico.getClass();
            int cantAtributtes = (agendamientoInfo.getDeclaredFields()).length;
            Field[] fields = agendamientoInfo.getDeclaredFields();
            List<Field> lstField = new ArrayList<>();
            for (int i = 0; i < fields.length; i++) {
                if (this.colNombrePaciente == true
                        && fields[i].getName().equals("nombPaciente")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colEspecialidad == true
                        && fields[i].getName().equals("especialidad")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colServicio == true
                        && fields[i].getName().equals("servicio")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colDiagnostico == true
                        && fields[i].getName().equals("diagnostico")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colMotivoConsulta == true
                        && fields[i].getName().equals("motivoConsulta")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colFecha == true
                        && fields[i].getName().equals("fecha")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colFechaLlamada == true
                        && fields[i].getName().equals("fechaLlamada")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colFechaInicioAtencion == true
                        && fields[i].getName().equals("fechaInicioAtencion")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colFechaAtendido == true
                        && fields[i].getName().equals("fechaAtendido")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colFechaLlamarMedico == true
                        && fields[i].getName().equals("fechaLlamarMedico")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colFechaInicioMedico == true
                        && fields[i].getName().equals("fechaInicioMedico")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colFechaAtendidoMedico == true
                        && fields[i].getName().equals("fechaAtendidoMedico")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.colEstado == true
                        && fields[i].getName().equals("estado")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
            }
            int col = lstField.size();
            int row = lstAgendamiento.size();
            String[][] strHistorial = new String[row][col];
            for (int j = 0; j < row; j++) {
                AgendaDiagnostico object = lstAgendamiento.get(j);
                Class<? extends AgendaDiagnostico> clazz = (Class) object.getClass();
                int k = 0;
                for (Field variable : lstField) {
                    boolean flag = variable.isAccessible();
                    Type tipoCampo = variable.getGenericType();
                    String nombreCampo = variable.getName();
                    Field field = clazz.getDeclaredField(nombreCampo);
                    field.setAccessible(true);
                    Object fieldValue = field.get(object);
                    if (fieldValue != null) {
                        if (nombreCampo.equals("estado")) {
                            switch (fieldValue.toString()) {
                                case "C":
                                    strHistorial[j][k] = "Creado";
                                    break;
                                case "D":
                                    strHistorial[j][k] = "Disponible Triaje";
                                    break;
                                case "L":
                                    strHistorial[j][k] = "Llamado Triaje";
                                    break;
                                case "IA":
                                    strHistorial[j][k] = "Inicio Atencion Triaje";
                                    break;
                                case "AT":
                                    strHistorial[j][k] = "Atentido Triaje";
                                    break;
                                case "DM":
                                    strHistorial[j][k] = "Disponible Medico";
                                    break;
                                case "LM":
                                    strHistorial[j][k] = "Llamado Medico";
                                    break;
                                case "IM":
                                    strHistorial[j][k] = "Inicio Atencion Medico";
                                    break;
                                case "AM":
                                    strHistorial[j][k] = "Atendido Medico";
                                    break;
                                case "FT":
                                    strHistorial[j][k] = "Perdido Triaje";
                                    break;
                                case "FM":
                                    strHistorial[j][k] = "Perdido Medico";
                                    break;
                                case "RT":
                                    strHistorial[j][k] = "Turno Recuperado";
                                    break;
                                default:
                                    strHistorial[j][k] = "Finalizado";
                                    break;
                            }
                        } else {
                            strHistorial[j][k] = String.valueOf(fieldValue).toUpperCase();
                        }
                    } else {
                        strHistorial[j][k] = "";
                    }
                    k++;
                    field.setAccessible(flag);
                }
            }
            this.ruta = this.propParam.getProperty("rutaArchivo");
            this.nombArchivo = "listado_agendamientos";
            excelObj.exportar(this.nombArchivo, this.ruta + this.nombArchivo + ".xlsx", lstEncabezados, strHistorial, col, row);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Export file success");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<DispAgendamiento> getListDispAgendamiento() {
        return this.listDispAgendamiento;
    }

    public void setListDispAgendamiento(List<DispAgendamiento> listDispAgendamiento) {
        this.listDispAgendamiento = listDispAgendamiento;
    }

    public List<InfoAgendamiento> getListInfoAgendamiento() {
        return this.listInfoAgendamiento;
    }

    public void setListInfoAgendamiento(List<InfoAgendamiento> listInfoAgendamiento) {
        this.listInfoAgendamiento = listInfoAgendamiento;
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

    public String getFiltroConsulta() {
        return this.filtroConsulta;
    }

    public void setFiltroConsulta(String filtroConsulta) {
        this.filtroConsulta = filtroConsulta;
    }

    public StreamedContent getFile() {
        return this.file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public InfoAgendamiento getInfoAgendamiento() {
        return this.infoAgendamiento;
    }

    public void setInfoAgendamiento(InfoAgendamiento infoAgendamiento) {
        this.infoAgendamiento = infoAgendamiento;
    }

    public String getNombArchivo() {
        return this.nombArchivo;
    }

    public void setNombArchivo(String nombArchivo) {
        this.nombArchivo = nombArchivo;
    }

    public boolean isColNombrePaciente() {
        return this.colNombrePaciente;
    }

    public void setColNombrePaciente(boolean colNombrePaciente) {
        this.colNombrePaciente = colNombrePaciente;
    }

    public boolean isColEspecialidad() {
        return this.colEspecialidad;
    }

    public void setColEspecialidad(boolean colEspecialidad) {
        this.colEspecialidad = colEspecialidad;
    }

    public boolean isColServicio() {
        return this.colServicio;
    }

    public void setColServicio(boolean colServicio) {
        this.colServicio = colServicio;
    }

    public boolean isColDiagnostico() {
        return this.colDiagnostico;
    }

    public void setColDiagnostico(boolean colDiagnostico) {
        this.colDiagnostico = colDiagnostico;
    }

    public boolean isColMotivoConsulta() {
        return this.colMotivoConsulta;
    }

    public void setColMotivoConsulta(boolean colMotivoConsulta) {
        this.colMotivoConsulta = colMotivoConsulta;
    }

    public boolean isColFecha() {
        return this.colFecha;
    }

    public void setColFecha(boolean colFecha) {
        this.colFecha = colFecha;
    }

    public boolean isColFechaAtendido() {
        return this.colFechaAtendido;
    }

    public void setColFechaAtendido(boolean colFechaAtendido) {
        this.colFechaAtendido = colFechaAtendido;
    }

    public boolean isColFechaLlamada() {
        return this.colFechaLlamada;
    }

    public void setColFechaLlamada(boolean colFechaLlamada) {
        this.colFechaLlamada = colFechaLlamada;
    }

    public boolean isColFechaInicioAtencion() {
        return this.colFechaInicioAtencion;
    }

    public void setColFechaInicioAtencion(boolean colFechaInicioAtencion) {
        this.colFechaInicioAtencion = colFechaInicioAtencion;
    }

    public boolean isColFechaInicioMedico() {
        return this.colFechaInicioMedico;
    }

    public void setColFechaInicioMedico(boolean colFechaInicioMedico) {
        this.colFechaInicioMedico = colFechaInicioMedico;
    }

    public boolean isColFechaLlamarMedico() {
        return this.colFechaLlamarMedico;
    }

    public void setColFechaLlamarMedico(boolean colFechaLlamarMedico) {
        this.colFechaLlamarMedico = colFechaLlamarMedico;
    }

    public boolean isColFechaAtendidoMedico() {
        return this.colFechaAtendidoMedico;
    }

    public void setColFechaAtendidoMedico(boolean colFechaAtendidoMedico) {
        this.colFechaAtendidoMedico = colFechaAtendidoMedico;
    }

    public boolean isColEstado() {
        return this.colEstado;
    }

    public void setColEstado(boolean colEstado) {
        this.colEstado = colEstado;
    }

    public List<AgendaDiagnostico> getLstAgendaDiagnostico() {
        return this.lstAgendaDiagnostico;
    }

    public void setLstAgendaDiagnostico(List<AgendaDiagnostico> lstAgendaDiagnostico) {
        this.lstAgendaDiagnostico = lstAgendaDiagnostico;
    }

    public Date getFechaini() {
        return this.fechaini;
    }

    public void setFechaini(Date fechaini) {
        this.fechaini = fechaini;
    }

    public Date getFechafin() {
        return this.fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Estado getEstadoObj() {
        return this.estadoObj;
    }

    public void setEstadoObj(Estado estadoObj) {
        this.estadoObj = estadoObj;
    }

    public List<Estado> getListaEstado() {
        return this.listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public String getPagado() {
        return this.pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }
}
