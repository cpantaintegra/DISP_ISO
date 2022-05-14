package com.integrasystemsonline.Dispensario;

import com.Entity.DispEspecialidad;
import com.Entity.DispOrigen;
import com.Entity.DispPrecio;
import com.Entity.DispServicio;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispEspecialidadFacade;
import com.Session.DispOrigenFacade;
import com.Session.DispPrecioFacade;
import com.Session.DispServicioFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyServicioModel;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("serviciosMB")
@ViewScoped
public class ServiciosMB implements Serializable {
  @Resource
  UserTransaction userTransaction;
  
  @EJB
  DispServicioFacade dispServicioFacade;
  
  @EJB
  DispEspecialidadFacade dispEspecialidadFacade;
  
  @EJB
  DispOrigenFacade dispOrigenFacade;
  
  @EJB
  DispPrecioFacade dispPrecioFacade;
  
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
  
  private List<DispServicio> listDispServicio = new ArrayList<>();
  
  private DispServicio dispServicio = new DispServicio();
  
  private List<DispEspecialidad> listDispEspecialidad = new ArrayList<>();
  
  private DispEspecialidad especialidadObj = new DispEspecialidad();
  
  private List<DispPrecio> listDispPrecio = new ArrayList<>();
  
  private DispPrecio precioObj = new DispPrecio();
  
  private DispOrigen origenObj = new DispOrigen();
  
  private List<DispOrigen> listDispOrigen = new ArrayList<>();
  
  private UIData dataTable;
  
  private LazyDataModel<DispServicio> lazyDispServicio;
  
  private String filtroConsulta;
  
  IsUsuarios usuario;
  
  private String estado;
  
  private String labelMant;
  
  private String labelMantPrecio;
  
  private List<Estado> listaEstado;
  
  private Estado estadoObj;
  
  private List<IsRolesPermisos> listIsRolesPermisos;
  
  private boolean editar = true;
  
  private boolean eliminarBl = true;
  
  private boolean consultar = true;
  
  private boolean ingresar = true;
  
  private String timeZone = "";
  
  @PostConstruct
  public void ini() {
    try {
      this.usuario = (IsUsuarios)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
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
      } catch (Exception exception) {}
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
        if (isRolesPermisos.getIdPermisos().getNombre().equals(ing))
          this.ingresar = false; 
      } 
      this.listDispEspecialidad = this.dispEspecialidadFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
      try {
        this.especialidadObj = this.listDispEspecialidad.get(0);
      } catch (Exception exception) {}
      this.listDispOrigen = this.dispOrigenFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
      try {
        this.origenObj = this.listDispOrigen.get(0);
      } catch (Exception exception) {}
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void agregarPrecios() {
    FacesMessage msg = null;
    boolean guardar = true;
    boolean precioFlag = true;
    try {
      if (this.precioObj.getIdPrecio() != null)
        guardar = false; 
      if (this.origenObj.getIdOrigen() != null)
        this.precioObj.setIdOrigen(this.origenObj); 
      for (int i = 0; i < this.listDispPrecio.size(); i++) {
        if (this.precioObj.getIdOrigen() == ((DispPrecio)this.listDispPrecio.get(i)).getIdOrigen()) {
          i = this.listDispPrecio.size();
          precioFlag = false;
        } else {
          precioFlag = true;
        } 
      } 
      if (precioFlag == true) {
        if (guardar) {
          this.listDispPrecio.add(this.precioObj);
        } else {
          this.listDispPrecio.remove(this.precioObj);
          this.listDispPrecio.add(this.precioObj);
        } 
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
        redireccionarPrecio();
      } else {
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ya se registro un precio para este origen");
      } 
    } catch (Exception e) {
      msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
    } 
    if (msg != null)
      FacesContext.getCurrentInstance().addMessage(null, msg); 
  }
  
  public void guardar() throws SystemException {
    FacesMessage msg = null;
    boolean guardar = true;
    if (this.dispServicio.getIdServicio() != null)
      guardar = false; 
    try {
      this.userTransaction.begin();
      if (this.especialidadObj.getIdEspecialidad() != null) {
        this.dispServicio.setIdEspecialidad(this.especialidadObj);
        if (guardar) {
          DispServicio servicioObj = this.dispServicioFacade.findByNombre(this.dispServicio.getNombre(), this.especialidadObj.getIdEspecialidad(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
          if (servicioObj == null) {
            this.dispServicio.setEstado("A");
            this.dispServicio.setIdEmpresa(this.usuario.getIdEmpresa());
            this.dispServicio.setIdCiudad(this.usuario.getIdCiudad());
            this.dispServicio.setIdSector(this.usuario.getIdSector());
            this.dispServicio.setUsuarioIngreso(this.usuario.getUsuario());
            this.dispServicio.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
            this.dispServicioFacade.createWithValidator(this.dispServicio);
            this.dispServicioFacade.flush();
          } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Servicio ya registrado");
          } 
        } else {
          this.dispServicio.setUsuarioModificacion(this.usuario.getUsuario());
          this.dispServicio.setFechaModificacion(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
          this.dispServicioFacade.editWithValidator(this.dispServicio);
          this.dispServicioFacade.flush();
          List<DispPrecio> lst = this.dispPrecioFacade.findByServicio(this.dispServicio.getIdServicio());
          for (int i = 0; i < lst.size(); i++) {
            this.dispPrecioFacade.remove(lst.get(i));
            this.dispPrecioFacade.flush();
          } 
        } 
        if (this.dispServicio.getIdServicio() != null) {
          for (int i = 0; i < this.listDispPrecio.size(); i++) {
            this.precioObj = new DispPrecio();
            this.precioObj.setValor(((DispPrecio)this.listDispPrecio.get(i)).getValor());
            this.precioObj.setIdOrigen(((DispPrecio)this.listDispPrecio.get(i)).getIdOrigen());
            this.precioObj.setEstado("A");
            this.precioObj.setIdServicio(this.dispServicio);
            this.precioObj.setIdEmpresa(this.usuario.getIdEmpresa());
            this.precioObj.setIdCiudad(this.usuario.getIdCiudad());
            this.precioObj.setIdSector(this.usuario.getIdSector());
            this.precioObj.setUsuarioIngreso(this.usuario.getUsuario());
            this.precioObj.setFechaIngreso(this.objSDF.parse(this.objSDF.format(Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone))));
            this.dispPrecioFacade.createWithValidator(this.precioObj);
            this.dispPrecioFacade.flush();
          } 
          msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
          redireccionar();
        } 
      } else {
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Por favor, seleccione la especialidad");
      } 
      this.userTransaction.commit();
    } catch (Exception e) {
      msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
      this.userTransaction.rollback();
    } 
    if (msg != null)
      FacesContext.getCurrentInstance().addMessage(null, msg); 
  }
  
  public void redireccionar() {
    try {
      this.dispServicio = new DispServicio();
      this.estado = "A";
      this.labelMant = "Ingresar";
      try {
        this.especialidadObj = this.listDispEspecialidad.get(0);
      } catch (Exception exception) {}
      this.listDispPrecio = new ArrayList<>();
    } catch (Exception exception) {}
  }
  
  public void redireccionarPrecio() {
    FacesMessage msg = null;
    try {
      if (!this.dispServicio.getNombre().isEmpty() && this.dispServicio.getNombre() != null) {
        if (!this.listDispOrigen.isEmpty()) {
          this.origenObj = this.listDispOrigen.get(0);
        } else {
          msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No existe ningun origen");
        } 
      } else {
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese el nombre del servicio");
      } 
      this.precioObj = new DispPrecio();
      this.estado = "A";
      this.labelMantPrecio = "Ingresar";
    } catch (Exception e) {
      msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
    } 
    if (msg != null)
      FacesContext.getCurrentInstance().addMessage(null, msg); 
  }
  
  public void eliminar() {
    FacesMessage msg = null;
    try {
      this.userTransaction.begin();
      this.userTransaction.commit();
    } catch (Exception e) {
      msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
    } 
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }
  
  public void onRowDblClckSelect(SelectEvent event) {
    try {
      this.dispServicio = (DispServicio)event.getObject();
      this.labelMant = "Actualizar";
      this.especialidadObj = this.dispServicio.getIdEspecialidad();
      this.listDispPrecio = new ArrayList<>();
      this.listDispPrecio = this.dispPrecioFacade.findByServicio(this.dispServicio.getIdServicio());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void onRowDblClckSelectPrecio(SelectEvent event) {
    try {
      this.precioObj = (DispPrecio)event.getObject();
      this.labelMantPrecio = "Actualizar";
      this.dispServicio = this.precioObj.getIdServicio();
      this.origenObj = this.precioObj.getIdOrigen();
      this.listDispPrecio = new ArrayList<>();
      this.listDispPrecio = this.dispPrecioFacade.findByServicio(this.dispServicio.getIdServicio());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public LazyDataModel<DispServicio> getAll() {
    if (this.lazyDispServicio == null)
      this.lazyDispServicio = new LazyServicioModel(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado); 
    return this.lazyDispServicio;
  }
  
  public UIData getDataTable() {
    return this.dataTable;
  }
  
  public void setDataTable(UIData usersDataTable) {
    this.dataTable = usersDataTable;
  }
  
  public List<DispServicio> getListDispServicio() {
    return this.listDispServicio;
  }
  
  public void setListDispServicio(List<DispServicio> listDispServicio) {
    this.listDispServicio = listDispServicio;
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
  
  public DispServicio getDispServicio() {
    return this.dispServicio;
  }
  
  public void setDispServicio(DispServicio dispServicio) {
    this.dispServicio = dispServicio;
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
  
  public List<DispEspecialidad> getListDispEspecialidad() {
    return this.listDispEspecialidad;
  }
  
  public void setListDispEspecialidad(List<DispEspecialidad> listDispEspecialidad) {
    this.listDispEspecialidad = listDispEspecialidad;
  }
  
  public DispEspecialidad getEspecialidadObj() {
    return this.especialidadObj;
  }
  
  public void setEspecialidadObj(DispEspecialidad especialidadObj) {
    this.especialidadObj = especialidadObj;
  }
  
  public List<DispPrecio> getListDispPrecio() {
    return this.listDispPrecio;
  }
  
  public void setListDispPrecio(List<DispPrecio> listDispPrecio) {
    this.listDispPrecio = listDispPrecio;
  }
  
  public DispPrecio getPrecioObj() {
    return this.precioObj;
  }
  
  public void setPrecioObj(DispPrecio precioObj) {
    this.precioObj = precioObj;
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
  
  public String getLabelMantPrecio() {
    return this.labelMantPrecio;
  }
  
  public void setLabelMantPrecio(String labelMantPrecio) {
    this.labelMantPrecio = labelMantPrecio;
  }
}