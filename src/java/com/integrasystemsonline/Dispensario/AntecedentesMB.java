package com.integrasystemsonline.Dispensario;

import com.Entity.DispAntecedentes;
import com.Entity.DispCliente;
import com.Entity.IsParametros;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAntecedentesFacade;
import com.Session.DispClienteFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsParametrosFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("antecedentesMB")
@ViewScoped
public class AntecedentesMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

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
    DispClienteFacade dispClienteFacade;

    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<DispAntecedentes> listDispAntecedentes = new ArrayList<>();

    private DispAntecedentes dispAntecedentes = new DispAntecedentes();

    private DispCliente dispCliente;

    private List<DispCliente> listDispCliente = new ArrayList<>();

    private UIData dataTable;

    private LazyDataModel<DispAntecedentes> lazyDispAntecedentes;

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

    private String timeZone = "";
    
    private String limiteEdicion = "";
    
    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
            isParametros = this.isParametrosFacade.findByCodigo("limiteEdicion", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.limiteEdicion = isParametros.getValor();
            this.dispCliente = (DispCliente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dispCliente");
            this.listDispCliente = this.dispClienteFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            if (this.dispCliente == null
                    && !this.listDispCliente.isEmpty()) {
                this.dispCliente = this.listDispCliente.get(0);
            }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean editarAntecedentes(DispAntecedentes dispAntecedentes) {
        Integer limite = 4;
        Date hasta = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
        if (dispAntecedentes != null) {
            if (dispAntecedentes.getIdAntecedentes()!= null) {
                Date fechaIngreso = Utilidades.obtenerFechaZonaHoraria(dispAntecedentes.getFechaIngreso(), "0", this.timeZone);
                long diff = hasta.getTime() - fechaIngreso.getTime();
                long diffHours = diff / 3600000;
                if (limiteEdicion != null) {
                    limite = Integer.parseInt(limiteEdicion);
                }

                if (diffHours < limite) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    
    public void handleComboCliente() throws Exception {
        this.lazyDispAntecedentes = null;
        getAll();
    }

    public String nombreCliente() {
        String nombre = "";
        try {
            nombre = this.dispCliente.getApaterno().toUpperCase() + " " + this.dispCliente.getAmaterno().toUpperCase() + " " + this.dispCliente.getNombre().toUpperCase();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return nombre;
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispAntecedentes.getIdAntecedentes() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            if (guardar) {
                this.dispAntecedentes.setEstado("A");
                this.dispAntecedentes.setIdCliente(this.dispCliente);
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
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
            redireccionar();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            this.userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccionarAntecedentes() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_registro_diagnostico.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(AntecedentesMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void redireccionar() {
        try {
            this.dispAntecedentes = new DispAntecedentes();
            this.estado = "A";
            this.labelMant = "Ingresar";
        } catch (Exception exception) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.dispAntecedentesFacade.remove(this.dispAntecedentes);
            this.dispAntecedentesFacade.flush();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
            this.userTransaction.commit();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispAntecedentes = (DispAntecedentes) event.getObject();
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<DispAntecedentes> getAll() {
        if (this.lazyDispAntecedentes == null) {
            this.lazyDispAntecedentes = new LazyAntecedentesModel(this.dispCliente.getIdCliente(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispAntecedentes;
    }

    public UIData getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<DispAntecedentes> getListDispAntecedentes() {
        return this.listDispAntecedentes;
    }

    public void setListDispAntecedentes(List<DispAntecedentes> listDispAntecedentes) {
        this.listDispAntecedentes = listDispAntecedentes;
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

    public DispAntecedentes getDispAntecedentes() {
        return this.dispAntecedentes;
    }

    public void setDispAntecedentes(DispAntecedentes dispAntecedentes) {
        this.dispAntecedentes = dispAntecedentes;
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

    public DispCliente getDispCliente() {
        return this.dispCliente;
    }

    public void setDispCliente(DispCliente dispCliente) {
        this.dispCliente = dispCliente;
    }

    public List<DispCliente> getListDispCliente() {
        return this.listDispCliente;
    }

    public void setListDispCliente(List<DispCliente> listDispCliente) {
        this.listDispCliente = listDispCliente;
    }
}
