package com.integrasystemsonline.Dispensario;

import com.Entity.DispConsultorio;
import com.Entity.DispEspecialidad;
import com.Entity.DispMedicoPersonal;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispConsultorioFacade;
import com.Session.DispEspecialidadFacade;
import com.Session.DispMedicoPersonalFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.Session.IsUsuariosFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.EstadoCivil;
import com.integrasystemsonline.Utilidades.LazyMedicoPersonalModel;
import com.integrasystemsonline.Utilidades.Sexo;
import com.integrasystemsonline.Utilidades.TipoDocumento;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("medicoPersonalMB")
@ViewScoped
public class MedicoPersonalMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispMedicoPersonalFacade dispMedicoPersonalFacade;

    @EJB
    DispEspecialidadFacade dispEspecialidadFacade;

    @EJB
    DispConsultorioFacade dispConsultorioFacade;

    @EJB
    IsEmpresaFacade isEmpresaFacade;

    @EJB
    IsCiudadFacade isCiudadFacade;

    @EJB
    IsSectorFacade isSectorFacade;

    @EJB
    IsUsuariosFacade isUsuariosFacade;

    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;

    @EJB
    IsParametrosFacade isParametrosFacade;

    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<DispMedicoPersonal> listDispMedicoPersonal = new ArrayList<>();

    private DispMedicoPersonal dispMedicoPersonal = new DispMedicoPersonal();

    private List<DispEspecialidad> listEspecialidad;

    private List<TipoDocumento> listaTipoDocumento;

    private List<Sexo> listaSexo;

    private UIData dataTable;

    private LazyDataModel<DispMedicoPersonal> lazyDispMedicoPersonal;

    private String filtroConsulta;

    IsUsuarios usuario;

    private String estado;

    private String labelMant;

    private List<Estado> listaEstado;

    private Estado estadoObj;

    private List<EstadoCivil> listaEstadoCivil;

    private EstadoCivil estadoCivilObj;

    private DispEspecialidad especialidadObj;

    private DispConsultorio consultorioObj;

    private TipoDocumento tipoDocumentoObj;

    private Sexo sexoObj;

    private String strUsuario;

    private List<IsUsuarios> listIsUsuarios = new ArrayList<>();

    private IsUsuarios usuarioObj;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    private String medico;

    private List<DispConsultorio> listConsultorio;

    private List<DispConsultorio> listConsultorioDrop;

    private String timeZone = "";

    @PostConstruct
    public void ini() {
        try {
            this.estado = "A";
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
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
            this.listaSexo = new ArrayList<>();
            Sexo sexo = new Sexo();
            sexo.setDetalle("-Seleccione-");
            this.sexoObj = sexo;
            this.listaSexo.add(sexo);
            sexo = new Sexo();
            sexo.setValor("M");
            sexo.setDetalle("Masculino");
            this.listaSexo.add(sexo);
            sexo = new Sexo();
            sexo.setValor("F");
            sexo.setDetalle("Femenino");
            this.listaSexo.add(sexo);
            this.listaEstadoCivil = new ArrayList<>();
            EstadoCivil estadoCivil = new EstadoCivil();
            estadoCivil.setDetalle("-Seleccione-");
            this.estadoCivilObj = estadoCivil;
            this.listaEstadoCivil.add(estadoCivil);
            estadoCivil = new EstadoCivil();
            estadoCivil.setValor("S");
            estadoCivil.setDetalle("Soltero");
            this.listaEstadoCivil.add(estadoCivil);
            estadoCivil = new EstadoCivil();
            estadoCivil.setValor("C");
            estadoCivil.setDetalle("Casado");
            this.listaEstadoCivil.add(estadoCivil);
            estadoCivil = new EstadoCivil();
            estadoCivil.setValor("D");
            estadoCivil.setDetalle("Divorciado");
            this.listaEstadoCivil.add(estadoCivil);
            estadoCivil = new EstadoCivil();
            estadoCivil.setValor("V");
            estadoCivil.setDetalle("Viudo");
            this.listaEstadoCivil.add(estadoCivil);
            estadoCivil = new EstadoCivil();
            estadoCivil.setValor("O");
            estadoCivil.setDetalle("Concubinato");
            this.listaEstadoCivil.add(estadoCivil);
            estadoCivil = new EstadoCivil();
            estadoCivil.setValor("E");
            estadoCivil.setDetalle("Separacien proceso judicial");
            this.listaEstadoCivil.add(estadoCivil);
            this.listaTipoDocumento = new ArrayList<>();
            TipoDocumento tipoDocumento = new TipoDocumento();
            tipoDocumento.setDetalle("-Seleccione-");
            this.tipoDocumentoObj = tipoDocumento;
            this.listaTipoDocumento.add(tipoDocumento);
            tipoDocumento = new TipoDocumento();
            tipoDocumento.setValor("C");
            tipoDocumento.setDetalle("Cedula");
            this.listaTipoDocumento.add(tipoDocumento);
            tipoDocumento = new TipoDocumento();
            tipoDocumento.setValor("R");
            tipoDocumento.setDetalle("Ruc");
            this.listaTipoDocumento.add(tipoDocumento);
            tipoDocumento = new TipoDocumento();
            tipoDocumento.setValor("P");
            tipoDocumento.setDetalle("Pasaporte");
            this.listaTipoDocumento.add(tipoDocumento);
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
            this.listEspecialidad = this.dispEspecialidadFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> completeText(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> medicoList = new ArrayList<>();
        List<DispMedicoPersonal> medicos = this.dispMedicoPersonalFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        for (DispMedicoPersonal medico1 : medicos) {
            medicoList.add(medico1.getApaterno() + " " + medico1.getAmaterno() + " " + medico1.getNombre());
        }
        return (List<String>) medicoList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public void onItemSelect(SelectEvent event) {
        this.medico = (String) event.getObject();
        String[] subCliente = this.medico.split(" ");
        this.dispMedicoPersonal = this.dispMedicoPersonalFacade.findByNames(subCliente[0], subCliente[1], subCliente[2], this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        this.listConsultorio = this.dispConsultorioFacade.findByConsultorioByEspecialidad(this.dispMedicoPersonal.getIdEspecialidad().getIdEspecialidad());
    }

    public List<String> completeTextUsuarios(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> usuarioList = new ArrayList<>();
        List<IsUsuarios> usuarios = this.isUsuariosFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        for (IsUsuarios usuario1 : usuarios) {
            usuarioList.add(usuario1.getUsuario());
        }
        return (List<String>) usuarioList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public void onItemSelectUsuarios(SelectEvent event) {
        this.strUsuario = (String) event.getObject();
        this.usuarioObj = this.isUsuariosFacade.findByUser(this.strUsuario, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        String aMaterno = "";
        if (this.usuarioObj != null) {
            this.dispMedicoPersonal.setTelefono(this.usuarioObj.getTelefonoCasa());
            this.dispMedicoPersonal.setCelular(this.usuarioObj.getTelefonoCel());
            this.dispMedicoPersonal.setDireccion(this.usuarioObj.getDireccionDom());
            this.dispMedicoPersonal.setEmail(this.usuarioObj.getCorreo());
            this.dispMedicoPersonal.setNombre(this.usuarioObj.getNombres());
            String[] apellidos = this.usuarioObj.getApellidos().split(" ");
            String aPaterno = apellidos[0];
            if (apellidos.length > 1) {
                aMaterno = apellidos[1];
            }
            this.dispMedicoPersonal.setApaterno(aPaterno);
            this.dispMedicoPersonal.setAmaterno(aMaterno);
            this.dispMedicoPersonal.setNumDocumento(this.usuarioObj.getCedula());
        }
    }

    public void onCarDrop(DragDropEvent ddEvent) {
        DispConsultorio car = (DispConsultorio) ddEvent.getData();
        this.listConsultorioDrop.add(car);
        this.listConsultorio.remove(car);
    }

    public void onCarDrop1(DragDropEvent ddEvent) {
        DispConsultorio car = (DispConsultorio) ddEvent.getData();
        this.listConsultorio.add(car);
        this.listConsultorioDrop.remove(car);
    }

    public void asignarConsultorio() throws SystemException {
        FacesMessage msg = null;
        try {
            if (this.listConsultorioDrop.size() == 1) {
                this.userTransaction.begin();
                this.dispMedicoPersonal.setIdConsultorio(this.listConsultorioDrop.get(0));
                this.dispMedicoPersonal.setUsuarioModificacion(this.usuario.getUsuario());
                this.dispMedicoPersonal.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                this.dispMedicoPersonalFacade.editWithValidator(this.dispMedicoPersonal);
                this.dispMedicoPersonalFacade.flush();
                this.userTransaction.commit();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                redireccionarCosnultorioMedico();
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Solo puede seleccionar un consultorio por medico.");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispMedicoPersonal.getIdMedicoPersonal() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (this.usuarioObj != null) {
                this.dispMedicoPersonal.setIdUsuario(this.usuarioObj);
                this.dispMedicoPersonal.setIdEspecialidad(this.especialidadObj);
                List<DispConsultorio> listDispConsultorio = this.dispConsultorioFacade.findByConsultorioByEspecialidad(this.especialidadObj.getIdEspecialidad());
                if (!listDispConsultorio.isEmpty()) {
                    this.consultorioObj = listDispConsultorio.get(0);
                } else {
                    this.consultorioObj = this.dispConsultorioFacade.findById(1);
                }
                this.dispMedicoPersonal.setIdConsultorio(this.consultorioObj);
                if (guardar) {
                    this.dispMedicoPersonal.setIdEmpresa(this.usuario.getIdEmpresa());
                    this.dispMedicoPersonal.setIdCiudad(this.usuario.getIdCiudad());
                    this.dispMedicoPersonal.setIdSector(this.usuario.getIdSector());
                    this.dispMedicoPersonal.setUsuarioIngreso(this.usuario.getUsuario());
                    this.dispMedicoPersonal.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispMedicoPersonalFacade.createWithValidator(this.dispMedicoPersonal);
                    this.dispMedicoPersonalFacade.flush();
                } else {
                    this.dispMedicoPersonal.setUsuarioModificacion(this.usuario.getUsuario());
                    this.dispMedicoPersonal.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
                    this.dispMedicoPersonalFacade.editWithValidator(this.dispMedicoPersonal);
                    this.dispMedicoPersonalFacade.flush();
                }
                this.userTransaction.commit();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
                redireccionar();
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario no existe.");
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccionar() {
        try {
            this.dispMedicoPersonal = new DispMedicoPersonal();
            this.estado = "A";
            this.labelMant = "Ingresar";
            this.strUsuario = "";
            this.usuarioObj = null;
            try {
                this.especialidadObj = this.listEspecialidad.get(0);
            } catch (Exception exception) {
            }
        } catch (Exception exception) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.dispMedicoPersonalFacade.remove(this.dispMedicoPersonal);
            this.dispMedicoPersonalFacade.flush();
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispMedicoPersonal = (DispMedicoPersonal) event.getObject();
            this.labelMant = "Actualizar";
            this.strUsuario = this.dispMedicoPersonal.getIdUsuario().getUsuario();
            this.usuarioObj = this.dispMedicoPersonal.getIdUsuario();
            this.especialidadObj = this.dispMedicoPersonal.getIdEspecialidad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redireccionarCosnultorioMedico() {
        try {
            this.dispMedicoPersonal = new DispMedicoPersonal();
            this.estado = "A";
            this.labelMant = "Ingresar";
            this.medico = "";
            this.dispMedicoPersonal = null;
            this.listConsultorio = new ArrayList<>();
            this.listConsultorioDrop = new ArrayList<>();
        } catch (Exception exception) {
        }
    }

    public void onRowDblClckSelectCosnultorio(SelectEvent event) {
        try {
            this.dispMedicoPersonal = (DispMedicoPersonal) event.getObject();
            this.medico = this.dispMedicoPersonal.getApaterno() + " " + this.dispMedicoPersonal.getAmaterno() + " " + this.dispMedicoPersonal.getNombre();
            this.labelMant = "Actualizar";
            this.listConsultorio = this.dispConsultorioFacade.findByConsultorioByEspecialidad(this.dispMedicoPersonal.getIdEspecialidad().getIdEspecialidad());
            this.listConsultorioDrop = new ArrayList<>();
            this.listConsultorioDrop.add(this.dispMedicoPersonal.getIdConsultorio());
            this.listConsultorio.remove(this.dispMedicoPersonal.getIdConsultorio());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<DispMedicoPersonal> getAll() {
        if (this.lazyDispMedicoPersonal == null) {
            this.lazyDispMedicoPersonal = new LazyMedicoPersonalModel(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(),tipoDocumentoObj.getValor(), this.estado);
        }
        return this.lazyDispMedicoPersonal;
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispMedicoPersonal> getListDispMedicoPersonal() {
        return this.listDispMedicoPersonal;
    }

    public void setListDispMedicoPersonal(List<DispMedicoPersonal> listDispMedicoPersonal) {
        this.listDispMedicoPersonal = listDispMedicoPersonal;
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

    public DispMedicoPersonal getDispMedicoPersonal() {
        return this.dispMedicoPersonal;
    }

    public void setDispMedicoPersonal(DispMedicoPersonal dispMedicoPersonal) {
        this.dispMedicoPersonal = dispMedicoPersonal;
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

    public List<DispEspecialidad> getListEspecialidad() {
        return this.listEspecialidad;
    }

    public void setListEspecialidad(List<DispEspecialidad> listEspecialidad) {
        this.listEspecialidad = listEspecialidad;
    }

    public DispEspecialidad getEspecialidadObj() {
        return this.especialidadObj;
    }

    public void setEspecialidadObj(DispEspecialidad especialidadObj) {
        this.especialidadObj = especialidadObj;
    }

    public List<TipoDocumento> getListaTipoDocumento() {
        return this.listaTipoDocumento;
    }

    public void listaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public TipoDocumento getTipoDocumentoObj() {
        return this.tipoDocumentoObj;
    }

    public void setTipoDocumentoObj(TipoDocumento tipoPersonaObj) {
        this.tipoDocumentoObj = tipoPersonaObj;
    }

    public List<Sexo> getListaSexo() {
        return this.listaSexo;
    }

    public void setListaSexo(List<Sexo> listaSexo) {
        this.listaSexo = listaSexo;
    }

    public List<EstadoCivil> getListaEstadoCivil() {
        return this.listaEstadoCivil;
    }

    public void setListaEstadoCivil(List<EstadoCivil> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    public EstadoCivil getEstadoCivilObj() {
        return this.estadoCivilObj;
    }

    public void setEstadoCivilObj(EstadoCivil estadoCivilObj) {
        this.estadoCivilObj = estadoCivilObj;
    }

    public Sexo getSexoObj() {
        return this.sexoObj;
    }

    public void setSexoObj(Sexo sexoObj) {
        this.sexoObj = sexoObj;
    }

    public String getMedico() {
        return this.medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public List<DispConsultorio> getListConsultorio() {
        return this.listConsultorio;
    }

    public void setListConsultorio(List<DispConsultorio> listConsultorio) {
        this.listConsultorio = listConsultorio;
    }

    public List<DispConsultorio> getListConsultorioDrop() {
        return this.listConsultorioDrop;
    }

    public void setListConsultorioDrop(List<DispConsultorio> listConsultorioDrop) {
        this.listConsultorioDrop = listConsultorioDrop;
    }

    public DispConsultorio getConsultorioObj() {
        return this.consultorioObj;
    }

    public void setConsultorioObj(DispConsultorio consultorioObj) {
        this.consultorioObj = consultorioObj;
    }

    public List<IsUsuarios> getListIsUsuarios() {
        return this.listIsUsuarios;
    }

    public void setListIsUsuarios(List<IsUsuarios> listIsUsuarios) {
        this.listIsUsuarios = listIsUsuarios;
    }

    public IsUsuarios getUsuarioObj() {
        return this.usuarioObj;
    }

    public void setUsuarioObj(IsUsuarios usuarioObj) {
        this.usuarioObj = usuarioObj;
    }

    public String getStrUsuario() {
        return this.strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }
}
