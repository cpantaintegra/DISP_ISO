package com.integrasystemsonline.Dispensario;

import com.Entity.DispCliente;
import com.Entity.DispEstudiosMedicos;
import com.Entity.DispExamen;
import com.Entity.DispMedicoPersonal;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispClienteFacade;
import com.Session.DispEstudiosMedicosFacade;
import com.Session.DispExamenFacade;
import com.Session.DispMedicoPersonalFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Process.TablaDinamica;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.EstadoDia;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlMessage;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.spacer.Spacer;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named("calendarioMB")
@ViewScoped
public class CalendarioMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

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
    DispEstudiosMedicosFacade dispEstudiosMedicosFacade;
    
    @EJB
    DispExamenFacade dispExamenFacade;
    
    @EJB
    DispMedicoPersonalFacade dispMedicoPersonalFacade;
    
    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = (ScheduleEvent) new DefaultScheduleEvent();

    private boolean showWeekends = true;

    private boolean tooltip = true;

    private boolean allDaySlot = true;

    private String timeFormat;

    private String slotDuration = "00:30:00";

    private String slotLabelInterval;

    private String scrollTime = "06:00:00";

    private String minTime = "04:00:00";

    private String maxTime = "20:00:00";

    private String locale = "en";

    private String timeZone = "";

    private String clientTimeZone = "local";

    private String columnHeaderFormat = "";

    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat objSDFHora = new SimpleDateFormat("HH:mm");

    private List<DispCliente> listDispCliente = new ArrayList<>();

    private DispCliente ClienteObj = new DispCliente();

    private List<String> listDias = new ArrayList<>();

    private List<String> listHoras = new ArrayList<>();

    private UIData dataTable;

    private String filtroConsulta;

    IsUsuarios usuario;

    private String estado;

    private String labelMant;

    private List<Estado> listaEstado;

    private List<EstadoDia> listaEstadoDia;

    private Estado estadoObj;

    private EstadoDia estadoDiaObj;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    String disponibilidad = "Disponible";

    int numFilas = 0;

    DataTable table = new DataTable();
    HtmlForm form = new HtmlForm();
    TablaDinamica myTable = new TablaDinamica();
    DispExamen examen = new DispExamen();
    List<DispExamen> lstDispExamen = new ArrayList<>();
    List<DispExamen> lstDispExamenesSeleccionados = new ArrayList<>();
    List<DispEstudiosMedicos> lstDispEstudiosMedicos = new ArrayList<>();
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
            //cargarHorarios();
            lstDispEstudiosMedicos = dispEstudiosMedicosFacade.findByIdEspecialidad(2);
            crearTablasOrdenes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarExamenesSeleccionados(DispExamen examen){
        try {
            if(examen.getIdExamen()!=null){
                if(lstDispExamenesSeleccionados.contains(examen)){
                    lstDispExamenesSeleccionados.remove(examen);
                }
                else{
                    lstDispExamenesSeleccionados.add(examen);
                }
            }
        } catch (Exception e) {
        }
    }
    
    public List<DispExamen> cargarListaExamen(DispEstudiosMedicos estudios){
        lstDispExamen = new ArrayList<>();
        try {
            lstDispExamen = dispExamenFacade.findByIdEstudiosMedicos(estudios.getIdEstudiosMedicos());
        } catch (Exception e) {
        }
        
        return lstDispExamen;
    }
    
    public void verLista(){
        FacesMessage msg = null;
        
        for (int i = 0; i < lstDispExamenesSeleccionados.size(); i++) {
            
        }
        
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", String.valueOf(lstDispExamenesSeleccionados.size()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
    
    public HtmlForm crearTablasOrdenes(){
        List<DispEstudiosMedicos> lstDispEstudiosMedicos = new ArrayList<>();
        List<String> lstExamen = new ArrayList<>();
        List<String> lstEstudios = new ArrayList<>();
        List<DataTable> lst = new ArrayList<>();
        
        DispMedicoPersonal medico = dispMedicoPersonalFacade.findByIdUsuario(usuario.getIdUsuarios());
        //if(medico!=null){
            lstDispEstudiosMedicos = dispEstudiosMedicosFacade.findByIdEspecialidad(2);//medico.getIdEspecialidad().getIdEspecialidad()
            lstEstudios = new ArrayList<>();
            for (int j = 0; j < lstDispEstudiosMedicos.size(); j++) {
                lstEstudios.add(lstDispEstudiosMedicos.get(j).getNombre());
            }

            myTable.construirPanel(2, "width:100%;text-align: left");
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
        //}
        return form;
    }
    
    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleComboCliente() throws Exception {
    
    }

    public void iniciarReserva() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cargarHorarios() {
        this.listHoras = new ArrayList<>();
        try {
            int min = Integer.parseInt(this.propParam.getProperty("rango_tiempo"));
            String nombre_dias = this.propParam.getProperty("dias");
            String[] dias = nombre_dias.split(",");
            for (int i = 0; i < dias.length; i++) {
                this.listDias.add(dias[i]);
            }
            float hora_inicio = Float.parseFloat(this.propParam.getProperty("hora_inicio"));
            float hora_fin = Float.parseFloat(this.propParam.getProperty("hora_fin"));
            float hora_dif = hora_fin - hora_inicio;
            String strHora = String.valueOf(this.objSDFHora.format(Float.valueOf((hora_inicio - 7.0F) * 3600000.0F)));
            String[] partes_hora = String.valueOf(strHora).split(":");
            int minutos = Integer.parseInt(partes_hora[1]);
            int hora = Integer.parseInt(partes_hora[0]);
            float numHorarios = hora_dif * 60.0F / min;
            String horarioInicial = strHora;
            String horarioFinal = "";
            for (int j = 0; j < numHorarios; j++) {
                if (minutos + min < 60) {
                    minutos += min;
                } else {
                    minutos = 0;
                    hora++;
                }
                if (hora < 10) {
                    horarioFinal = horarioInicial + " - 0" + String.valueOf(hora) + ":" + String.valueOf(minutos);
                } else {
                    horarioFinal = horarioInicial + " - " + String.valueOf(hora) + ":" + String.valueOf(minutos);
                }
                horarioInicial = String.valueOf(hora) + ":" + String.valueOf(minutos);
                if (minutos == 0) {
                    horarioFinal = horarioFinal + "0";
                    horarioInicial = String.valueOf(hora) + ":" + String.valueOf(minutos) + "0";
                }
                if (hora < 10) {
                    horarioInicial = "0" + horarioInicial;
                }
                this.listHoras.add(horarioFinal);
            }
            this.numFilas = this.listHoras.size();
            TablaDinamica myTable = new TablaDinamica();
            this.table = myTable.getMyDataTable();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void reservar() {
    }

    public void onEventSelect(SelectEvent selectEvent) {
        this.event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        this.event = (ScheduleEvent) selectEvent.getObject();
    }

    public void addEvent() {
        if (this.event.isAllDay()) {
            if (this.event.getStartDate().toLocaleString().equals(this.event.getEndDate().toLocaleString()));
        }
        if (this.event.getId() == null) {
            this.eventModel.addEvent(this.event);
        } else {
            this.eventModel.updateEvent(this.event);
        }
        this.event = (ScheduleEvent) new DefaultScheduleEvent();
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
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

    public ScheduleModel getEventModel() {
        return this.eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleEvent getEvent() {
        return this.event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public boolean isShowWeekends() {
        return this.showWeekends;
    }

    public void setShowWeekends(boolean showWeekends) {
        this.showWeekends = showWeekends;
    }

    public boolean isTooltip() {
        return this.tooltip;
    }

    public void setTooltip(boolean tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isAllDaySlot() {
        return this.allDaySlot;
    }

    public void setAllDaySlot(boolean allDaySlot) {
        this.allDaySlot = allDaySlot;
    }

    public String getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getSlotDuration() {
        return this.slotDuration;
    }

    public void setSlotDuration(String slotDuration) {
        this.slotDuration = slotDuration;
    }

    public String getSlotLabelInterval() {
        return this.slotLabelInterval;
    }

    public void setSlotLabelInterval(String slotLabelInterval) {
        this.slotLabelInterval = slotLabelInterval;
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

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getClientTimeZone() {
        return this.clientTimeZone;
    }

    public void setClientTimeZone(String clientTimeZone) {
        this.clientTimeZone = clientTimeZone;
    }

    public String getColumnHeaderFormat() {
        return this.columnHeaderFormat;
    }

    public void setColumnHeaderFormat(String columnHeaderFormat) {
        this.columnHeaderFormat = columnHeaderFormat;
    }

    public HtmlForm getForm() {
        return form;
    }

    public void setForm(HtmlForm form) {
        this.form = form;
    }

    public DispExamen getExamen() {
        return examen;
    }

    public void setExamen(DispExamen examen) {
        this.examen = examen;
    }

    public List<DispExamen> getLstDispExamen() {
        return lstDispExamen;
    }

    public void setLstDispExamen(List<DispExamen> lstDispExamen) {
        this.lstDispExamen = lstDispExamen;
    }

    public List<DispEstudiosMedicos> getLstDispEstudiosMedicos() {
        return lstDispEstudiosMedicos;
    }

    public void setLstDispEstudiosMedicos(List<DispEstudiosMedicos> lstDispEstudiosMedicos) {
        this.lstDispEstudiosMedicos = lstDispEstudiosMedicos;
    }

    public List<DispExamen> getLstDispExamenesSeleccionados() {
        return lstDispExamenesSeleccionados;
    }

    public void setLstDispExamenesSeleccionados(List<DispExamen> lstDispExamenesSeleccionados) {
        this.lstDispExamenesSeleccionados = lstDispExamenesSeleccionados;
    }
    
}
