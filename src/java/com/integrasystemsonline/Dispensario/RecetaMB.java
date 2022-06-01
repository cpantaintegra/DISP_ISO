package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispCliente;
import com.Entity.DispDetalleDiagnostico;
import com.Entity.DispDetalleReceta;
import com.Entity.DispDiagnostico;
import com.Entity.DispMedicamento;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispReceta;
import com.Entity.DispResultado;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispClienteFacade;
import com.Session.DispDetalleRecetaFacade;
import com.Session.DispMedicamentoFacade;
import com.Session.DispMedicoPersonalFacade;
import com.Session.DispRecetaFacade;
import com.Session.DispResultadoFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.Session.IsUsuariosFacade;
import com.integrasystemsonline.Utilidades.Aes;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyRecetaModel;
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
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("recetaMB")
@ViewScoped
public class RecetaMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    DispRecetaFacade dispRecetaFacade;

    @EJB
    DispDetalleRecetaFacade dispDetalleRecetaFacade;

    @EJB
    DispMedicamentoFacade dispMedicamentoFacade;

    @EJB
    DispMedicoPersonalFacade dispMedicoPersonalFacade;

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
    IsUsuariosFacade isUsuariosFacade;

    @EJB
    DispResultadoFacade dispResultadoFacade;

    @EJB
    DispClienteFacade dispClienteFacade;
    
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<DispReceta> listDispReceta = new ArrayList<>();

    private DispReceta dispReceta = new DispReceta();

    private DispAgendamiento dispAgendamiento;

    private List<DispDetalleReceta> listDispDetalleReceta = new ArrayList<>();

    private DispDetalleReceta dispDetalleReceta = new DispDetalleReceta();

    private List<DispMedicamento> listDispMedicamento = new ArrayList<>();

    private DispMedicamento dispMedicamento = new DispMedicamento();

    private List<DispAgendamiento> lstDispAgendmaiento = new ArrayList<>();

    private DispDiagnostico dispDiagnostico = new DispDiagnostico();

    private List<DispDetalleDiagnostico> listDispDetalleDiagnostico = new ArrayList<>();

    private DispResultado dispResultado = new DispResultado();

    private UIData dataTable;

    private LazyDataModel<DispReceta> lazyDispReceta;

    private String filtroConsulta;

    IsUsuarios usuario;

    private String estado;

    private String labelMant;

    private List<Estado> listaEstado;
    private List<Estado> listaCliente;
    private List<Estado> listaMedico;
    private Estado estadoObj;
    
    private Estado clienteFilter;
    
    private List<DispCliente> lstClienteFilter;
    
    private Estado medicoFilter;
    
    private List<DispMedicoPersonal> lstMedicoFilter;
    
    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    private List<DispDetalleReceta> lstDetalleReceta = new ArrayList<>();

    private DispDetalleReceta detalleRecetaObj = new DispDetalleReceta();

    private String dosis;

    private String duracion;

    private int cantidad;

    private String medicamento;

    private String observaciones;

    private Date fechaActual = new Date();

    private Date fechaIni = new Date();

    private Date fechaFin = new Date();

    private String timeZone = "";

    private String strUsuario;

    private String password;

    private IsUsuarios usuarioObj = new IsUsuarios();

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    DispMedicoPersonal medicoObj = new DispMedicoPersonal();
    
    List<String> medicamentosList = new ArrayList<>();
    
    private int idDetalleRecetaTemp = 0;
    
    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            this.dispAgendamiento = (DispAgendamiento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dispAgendamiento");
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
            Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
            try {
                eli = propParam.getProperty("eliminar");
                edi = propParam.getProperty("editar");
                con = propParam.getProperty("consultar");
                ing = propParam.getProperty("ingresar");
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
            this.listDispMedicamento = this.dispMedicamentoFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            for (int i = 0; i < this.listDispMedicamento.size(); i++) {
                ((DispMedicamento) this.listDispMedicamento.get(i)).setNombre(((DispMedicamento) this.listDispMedicamento.get(i)).getNombre().toUpperCase());
            }
            if (!this.listDispMedicamento.isEmpty()) {
                this.dispMedicamento = this.listDispMedicamento.get(0);
            }
            this.fechaActual = Utilidades.obtenerFechaZonaHoraria(this.fechaActual, "0", this.timeZone);
            IsParametros parametros = null;
            parametros = this.isParametrosFacade.findByCodigo("minTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            String ini = parametros.getValor();
            String[] partesHoraIni = ini.split(":");
            int horaIni = Integer.parseInt(partesHoraIni[0]);
            int minutosIni = Integer.parseInt(partesHoraIni[1]);
            DateTime dataSelecionadaJoda = new DateTime(this.fechaActual.getTime());
            this.fechaIni = dataSelecionadaJoda.minusDays(7).withHourOfDay(horaIni).withMinuteOfHour(minutosIni).withSecondOfMinute(0).toDate();
            parametros = this.isParametrosFacade.findByCodigo("maxTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            String fin = parametros.getValor();
            String[] partesHoraFin = fin.split(":");
            int horaFin = Integer.parseInt(partesHoraFin[0]);
            int minutosFin = Integer.parseInt(partesHoraFin[1]);
            dataSelecionadaJoda = new DateTime(this.fechaActual.getTime());
            this.fechaFin = dataSelecionadaJoda.withHourOfDay(horaFin).withMinuteOfHour(minutosFin).withSecondOfMinute(0).toDate();
            if (this.usuario.getIdRoles().getNombre().toUpperCase().equals("MEDICO")) {
                this.medicoObj = this.dispMedicoPersonalFacade.findByIdUsuario(this.usuario.getIdUsuarios());
                this.lstDispAgendmaiento = this.dispAgendamientoFacade.findByAtendidoMedico(this.fechaIni, this.fechaFin, this.medicoObj.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            } else {
                this.lstDispAgendmaiento = this.dispAgendamientoFacade.findAllAtendidoMedicoOperador(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            }
            if (this.dispAgendamiento == null
                    && !this.lstDispAgendmaiento.isEmpty()) {
                this.dispAgendamiento = this.lstDispAgendmaiento.get(0);
            }
            
            lstClienteFilter = dispClienteFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.listaCliente = new ArrayList<>();
            estado = new Estado();
            estado.setValor(null);
            estado.setDetalle("-Seleccione-");
            this.clienteFilter = estado;
            this.listaCliente.add(estado);
            for (int c = 0; c < lstClienteFilter.size(); c++) {
                estado = new Estado();
                estado.setValor(String.valueOf(lstClienteFilter.get(c).getIdCliente()));
                estado.setDetalle(lstClienteFilter.get(c).getApaterno() + " " + lstClienteFilter.get(c).getAmaterno() + " " + lstClienteFilter.get(c).getNombre());
                this.listaCliente.add(estado);
            }
            
            lstMedicoFilter = dispMedicoPersonalFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.listaMedico = new ArrayList<>();
            estado = new Estado();
            estado.setValor(null);
            estado.setDetalle("-Seleccione-");
            this.medicoFilter = estado;
            this.listaMedico.add(estado);
            for (int c = 0; c < lstMedicoFilter.size(); c++) {
                estado = new Estado();
                estado.setValor(String.valueOf(lstMedicoFilter.get(c).getIdMedicoPersonal()));
                estado.setDetalle(lstMedicoFilter.get(c).getApaterno() + " " + lstMedicoFilter.get(c).getAmaterno() + " " + lstMedicoFilter.get(c).getNombre());
                this.listaMedico.add(estado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean renderAgendamiento() {
        if (this.labelMant.equals("Ingresar")) {
            if (this.lstDispAgendmaiento.isEmpty()) {
                return false;
            }
            return true;
        }
        if (this.labelMant.equals("Actualizar")) {
            return false;
        }
        return false;
    }

    public void validarCredenciales(String accion) {
        FacesMessage msg = null;
        String keyaes = "", ivaes = "";
        try {
            keyaes = this.propParam.getProperty("keyaes");
            ivaes = this.propParam.getProperty("ivaes");
            String passEncriptado = Aes.encrypt(keyaes, ivaes, this.password);
            this.usuarioObj = this.isUsuariosFacade.findByUsuarioClave(this.strUsuario, passEncriptado);
            if (this.usuarioObj != null) {
                if (this.usuarioObj.getIdRoles().getNombre().equals("OPERADOR")) {
                    if (accion.equals("E")) {
                        //onRowDblClckSelect(this.dispReceta);
                        PrimeFaces.current().executeScript("PF('wdlgIngresar').show();PF('wdlgConfirmacion').hide();");
                    } else if (accion.equals("I")) {
                        redireccionar();
                        PrimeFaces.current().executeScript("PF('wdlgIngresar').show();PF('wdlgConfirmacion2').hide();");
                    }
                    this.strUsuario = "";
                    this.password = "";
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

    public String cargarMedicamentos(DispReceta receta) {
        String medicamentos = "Sin medicamentos asociados";
        try {
            List<DispDetalleReceta> lstDetalle = this.dispDetalleRecetaFacade.findByIdReceta(receta.getIdReceta());
            if (!lstDetalle.isEmpty()) {
                medicamentos = ((DispDetalleReceta) lstDetalle.get(0)).getIdMedicamento().getNombre();
            }
            for (int i = 1; i < lstDetalle.size(); i++) {
                medicamentos = medicamentos + ", " + ((DispDetalleReceta) lstDetalle.get(i)).getIdMedicamento().getNombre();
            }
        } catch (Exception e) {
            medicamentos = e.toString();
        }
        return medicamentos;
    }

    public List<String> completeText(String query) {
        String queryUpperrCase = query.toUpperCase();
        
        List<DispMedicamento> medicamentos = this.dispMedicamentoFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        
        for (DispDetalleReceta collDispDetalleReceta : lstDetalleReceta) {
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

    public void agregarMedicamento() throws SystemException{
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
                this.lstDetalleReceta.add(detalleRecetaObj);
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
            userTransaction.rollback();
        }
    }
    
    public void guardarMedicamento() throws SystemException {
        FacesMessage msg = null;
        try {
            if (this.medicamento != null && !this.medicamento.isEmpty()) {
                DispMedicamento medicamentoObj = this.dispMedicamentoFacade.findByNombre(this.medicamento.toUpperCase(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
                if (medicamentoObj == null) {
                    this.userTransaction.begin();
                    this.dispMedicamento = new DispMedicamento();
                    this.dispMedicamento.setNombre(this.medicamento.toUpperCase());
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
                    this.listDispMedicamento.add(this.dispMedicamento);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El medicamento ya se encuentra registrado.");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese el nombre del medicamento");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void agregarObservacion(DispReceta receta) {
        try {
            this.dispReceta.setObservaciones(this.observaciones);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void redireccionar() {
        try {
            observaciones = "";
            this.dispReceta = new DispReceta();
            this.lstDetalleReceta = new ArrayList<>();
            if (this.usuario.getIdRoles().getNombre().toUpperCase().equals("MEDICO")) {
                this.lstDispAgendmaiento = this.dispAgendamientoFacade.findByAtendidoMedico(this.fechaIni, this.fechaFin, this.medicoObj.getIdMedicoPersonal(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            } else {
                this.lstDispAgendmaiento = this.dispAgendamientoFacade.findAllAtendidoMedicoOperador(this.fechaIni, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            }
            if (!this.lstDispAgendmaiento.isEmpty()) {
                this.dispAgendamiento = this.lstDispAgendmaiento.get(0);
            }
            this.estado = "A";
            this.labelMant = "Ingresar";
        } catch (Exception exception) {
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispReceta.getIdReceta() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (!this.lstDetalleReceta.isEmpty()) {
                if (this.dispAgendamiento != null) {
                    if (this.dispAgendamiento.getIdAgendamiento() != null) {
                        if (guardar) {
                            this.dispReceta.setEstado("A");
                            this.dispReceta.setObservaciones(this.observaciones);
                            this.dispReceta.setIdAgendamiento(this.dispAgendamiento);
                            this.dispReceta.setIdEmpresa(this.usuario.getIdEmpresa());
                            this.dispReceta.setIdCiudad(this.usuario.getIdCiudad());
                            this.dispReceta.setIdSector(this.usuario.getIdSector());
                            this.dispReceta.setUsuarioIngreso(this.usuario.getUsuario());
                            this.dispReceta.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                            this.dispRecetaFacade.createWithValidator(this.dispReceta);
                            this.dispRecetaFacade.flush();
                        } else {
                            this.dispReceta.setUsuarioModificacion(this.usuario.getUsuario());
                            this.dispReceta.setFechaModificacion(this.objSDF.parse(this.objSDF.format(new Date())));
                            this.dispRecetaFacade.editWithValidator(this.dispReceta);
                            this.dispRecetaFacade.flush();
                            Collection<DispDetalleReceta> detalleRecetas = this.dispDetalleRecetaFacade.findByIdReceta(this.dispReceta.getIdReceta());
                            for (DispDetalleReceta detalleReceta : detalleRecetas) {
                                this.dispDetalleRecetaFacade.remove(detalleReceta);
                                this.dispDetalleRecetaFacade.flush();
                            }
                        }
                        for (int i = 0; i < this.lstDetalleReceta.size(); i++) {
                            this.dispDetalleReceta = new DispDetalleReceta();
                            this.dispDetalleReceta.setEstado("A");
                            this.dispDetalleReceta.setIdMedicamento((this.lstDetalleReceta.get(i)).getIdMedicamento());
                            this.dispDetalleReceta.setCantidad((this.lstDetalleReceta.get(i)).getCantidad());
                            this.dispDetalleReceta.setDosis((this.lstDetalleReceta.get(i)).getDosis());
                            this.dispDetalleReceta.setDuracion((this.lstDetalleReceta.get(i)).getDuracion());
                            this.dispDetalleReceta.setIdReceta(this.dispReceta);
                            this.dispDetalleReceta.setIdEmpresa(this.usuario.getIdEmpresa());
                            this.dispDetalleReceta.setIdCiudad(this.usuario.getIdCiudad());
                            this.dispDetalleReceta.setIdSector(this.usuario.getIdSector());
                            this.dispDetalleReceta.setUsuarioIngreso(this.usuario.getUsuario());
                            this.dispDetalleReceta.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                            this.dispDetalleRecetaFacade.createWithValidator(this.dispDetalleReceta);
                            this.dispDetalleRecetaFacade.flush();
                        }
                        generarPDFReceta(this.dispReceta.getIdReceta());
                        this.userTransaction.commit();
                        this.dispAgendamiento = null;
                        this.observaciones = "";
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                        PrimeFaces.current().executeScript("PF('wdlgIngresar').hide();");
                        redireccionar();
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe seleccionar una reservacion");
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe seleccionar una reservacion");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe agregar al menos un medicamento");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccionarRegistroDiagnostico() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispCliente", this.dispAgendamiento.getIdCliente());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_triaje_medico.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(RecetaMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void redireccionarTriajeMedico() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_triaje_medico.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(RecetaMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.dispRecetaFacade.remove(this.dispReceta);
            this.dispRecetaFacade.flush();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCarDrop(DragDropEvent ddEvent) {
        DispMedicamento car = (DispMedicamento) ddEvent.getData();
        if(detalleRecetaObj.getIdDetalleReceta()==null){
            detalleRecetaObj.setIdDetalleReceta(idDetalleRecetaTemp+1);
        }
        this.detalleRecetaObj = new DispDetalleReceta();
        this.detalleRecetaObj.setIdMedicamento(car);
        this.detalleRecetaObj.setDosis("");
        this.detalleRecetaObj.setDuracion("");
        this.detalleRecetaObj.setCantidad(0);
        this.lstDetalleReceta.add(this.detalleRecetaObj);
        this.medicamentosList.remove(car.getNombre());
        this.listDispMedicamento.remove(car);
        idDetalleRecetaTemp=idDetalleRecetaTemp+1;
    }

    public void onCarDrop1(DragDropEvent ddEvent) {
        DispDetalleReceta car = (DispDetalleReceta) ddEvent.getData();
        this.listDispMedicamento.add(car.getIdMedicamento());
        this.medicamentosList.add(car.getIdMedicamento().getNombre());
        this.lstDetalleReceta.remove(car);
    }

    public void cargarReceta(SelectEvent event) {
        this.dispReceta = (DispReceta) event.getObject();
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.labelMant = "Actualizar";
            dispReceta = (DispReceta) event.getObject();
            this.dispAgendamiento = dispReceta.getIdAgendamiento();
            this.observaciones = dispReceta.getObservaciones();
            this.listDispMedicamento = this.dispMedicamentoFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.lstDetalleReceta = new ArrayList<>();
            List<DispDetalleReceta> collDispDetalleRecetas = this.dispDetalleRecetaFacade.findByIdReceta(dispReceta.getIdReceta());
            for (DispDetalleReceta collDispDetalleReceta : collDispDetalleRecetas) {
                this.lstDetalleReceta.add(collDispDetalleReceta);
                this.listDispMedicamento.remove(collDispDetalleReceta.getIdMedicamento());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarObservacion(DispDetalleReceta mr) {
        try {
            DispDetalleReceta car = mr;
            if (!this.lstDetalleReceta.isEmpty()) {
                for (int i = 0; i < this.lstDetalleReceta.size(); i++) {
                    if (car == this.lstDetalleReceta.get(i)) {
                        ((DispDetalleReceta) this.lstDetalleReceta.get(i)).setDuracion(car.getDuracion());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarDuracion(DispDetalleReceta mr) {
        try {
            DispDetalleReceta car = mr;
            if (!this.lstDetalleReceta.isEmpty()) {
                for (int i = 0; i < this.lstDetalleReceta.size(); i++) {
                    if (car == this.lstDetalleReceta.get(i)) {
                        ((DispDetalleReceta) this.lstDetalleReceta.get(i)).setDuracion(car.getDuracion());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarDosis(DispDetalleReceta mr) {
        try {
            DispDetalleReceta car = mr;
            if (!this.lstDetalleReceta.isEmpty()) {
                for (int i = 0; i < this.lstDetalleReceta.size(); i++) {
                    if (car == this.lstDetalleReceta.get(i)) {
                        ((DispDetalleReceta) this.lstDetalleReceta.get(i)).setDosis(car.getDosis());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarCantidad(DispDetalleReceta mr) {
        try {
            DispDetalleReceta car = mr;
            if (!this.lstDetalleReceta.isEmpty()) {
                for (int i = 0; i < this.lstDetalleReceta.size(); i++) {
                    if (car == this.lstDetalleReceta.get(i)) {
                        ((DispDetalleReceta) this.lstDetalleReceta.get(i)).setCantidad(car.getCantidad());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public LazyDataModel<DispReceta> getAll() {
        if (this.lazyDispReceta == null) {
            Integer idCliente = null;
            Integer idMedico = null;
            if(clienteFilter.getValor()!=null){
                idCliente = Integer.parseInt(clienteFilter.getValor());
            }
            
            if(medicoFilter.getValor()!=null){
                idMedico = Integer.parseInt(medicoFilter.getValor());
            }
            
            this.lazyDispReceta = new LazyRecetaModel(idCliente, idMedico, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispReceta;
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispReceta> getListDispReceta() {
        return this.listDispReceta;
    }

    public void setListDispReceta(List<DispReceta> listDispReceta) {
        this.listDispReceta = listDispReceta;
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

    public DispReceta getDispReceta() {
        return this.dispReceta;
    }

    public void setDispReceta(DispReceta dispReceta) {
        this.dispReceta = dispReceta;
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

    public DispAgendamiento getDispAgendamiento() {
        return this.dispAgendamiento;
    }

    public void setDispAgendamiento(DispAgendamiento dispAgendamiento) {
        this.dispAgendamiento = dispAgendamiento;
    }

    public List<DispDetalleReceta> getListDispDetalleReceta() {
        return this.listDispDetalleReceta;
    }

    public void setListDispDetalleReceta(List<DispDetalleReceta> listDispDetalleReceta) {
        this.listDispDetalleReceta = listDispDetalleReceta;
    }

    public DispDetalleReceta getDispDetalleReceta() {
        return this.dispDetalleReceta;
    }

    public void setDispDetalleReceta(DispDetalleReceta dispDetalleReceta) {
        this.dispDetalleReceta = dispDetalleReceta;
    }

    public List<DispMedicamento> getListDispMedicamento() {
        return this.listDispMedicamento;
    }

    public void setListDispMedicamento(List<DispMedicamento> listDispMedicamento) {
        this.listDispMedicamento = listDispMedicamento;
    }

    public DispMedicamento getDispMedicamento() {
        return this.dispMedicamento;
    }

    public void setDispMedicamento(DispMedicamento dispMedicamento) {
        this.dispMedicamento = dispMedicamento;
    }

    public List<DispDetalleReceta> getLstDetalleReceta() {
        return this.lstDetalleReceta;
    }

    public void setLstDetalleReceta(List<DispDetalleReceta> lstDetalleReceta) {
        this.lstDetalleReceta = lstDetalleReceta;
    }

    public String getDosis() {
        return this.dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDuracion() {
        return this.duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMedicamento() {
        return this.medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<DispAgendamiento> getLstDispAgendmaiento() {
        return this.lstDispAgendmaiento;
    }

    public void setLstDispAgendmaiento(List<DispAgendamiento> lstDispAgendmaiento) {
        this.lstDispAgendmaiento = lstDispAgendmaiento;
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

    public List<Estado> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Estado> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public List<Estado> getListaMedico() {
        return listaMedico;
    }

    public void setListaMedico(List<Estado> listaMedico) {
        this.listaMedico = listaMedico;
    }

    public Estado getClienteFilter() {
        return clienteFilter;
    }

    public void setClienteFilter(Estado clienteFilter) {
        this.clienteFilter = clienteFilter;
    }

    public Estado getMedicoFilter() {
        return medicoFilter;
    }

    public void setMedicoFilter(Estado medicoFilter) {
        this.medicoFilter = medicoFilter;
    }

}
