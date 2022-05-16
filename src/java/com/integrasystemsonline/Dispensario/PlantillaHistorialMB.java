package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.DispAntecedentes;
import com.Entity.DispCliente;
import com.Entity.DispDetalleDiagnostico;
import com.Entity.DispDiagnostico;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispResultado;
import com.Entity.DispTriaje;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispAntecedentesFacade;
import com.Session.DispClienteFacade;
import com.Session.DispDetalleDiagnosticoFacade;
import com.Session.DispTriajeFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyDetalleDiagnosticoModel;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named("plantillaHistorial")
@ViewScoped
public class PlantillaHistorialMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispAntecedentesFacade dispAntecedentesFacade;

    @EJB
    DispClienteFacade dispClienteFacade;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    DispDetalleDiagnosticoFacade dispDetalleDiagnosticoFacade;

    @EJB
    DispTriajeFacade dispTriajeFacade;

    @EJB
    IsEmpresaFacade isEmpresaFacade;

    @EJB
    IsCiudadFacade isCiudadFacade;

    @EJB
    IsSectorFacade isSectorFacade;

    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;

    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<DispCliente> listDispCliente = new ArrayList<>();

    private DispCliente ClienteObj = new DispCliente();

    private DispAntecedentes dispAntecedentes = new DispAntecedentes();

    private List<DispAntecedentes> listDispAntecedentes = new ArrayList<>();

    private UIData dataTable;

    private LazyDataModel<DispDetalleDiagnostico> lazyDispDetalleDiagnostico;

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

    private int rowsAntecedentes;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    private DispDetalleDiagnostico dispDetalleDiagnostico;

    private DispResultado dispResultado;

    private DispAgendamiento dispAgendamiento;

    private DispTriaje dispTriaje;

    private List<DispAgendamiento> listAgendamiento = new ArrayList<>();

    private List<DispResultado> listResultado = new ArrayList<>();

    private List<DispTriaje> listTriaje = new ArrayList<>();

    private List<DispDetalleDiagnostico> listDetalleDiagnostico = new ArrayList<>();

    private DispDiagnostico dispDiagnostico = new DispDiagnostico();

    private boolean verHistorial = true;

    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            this.estado = "A";
            this.listDispCliente = this.dispClienteFacade.findAllHistorialActivo(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.ClienteObj = (DispCliente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dispCliente");
            if (this.ClienteObj == null) {
                try {
                    this.ClienteObj = this.listDispCliente.get(0);
                } catch (Exception exception) {
                }
            }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleComboCliente() throws Exception {
        this.lazyDispDetalleDiagnostico = null;
        getAll();
    }

    public String nombreCliente(DispCliente cliente) {
        String nombre = "";
        try {
            nombre = cliente.getApaterno().toUpperCase() + " " + cliente.getAmaterno().toUpperCase() + " " + cliente.getNombre().toUpperCase();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return nombre;
    }

    public String nombreMedico(DispMedicoPersonal medico) {
        String nombre = "";
        try {
            nombre = medico.getApaterno().toUpperCase() + " " + medico.getAmaterno().toUpperCase() + " " + medico.getNombre().toUpperCase();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return nombre;
    }
    
    public String cargarAntecedentes(DispCliente cliente) {
        String antecedentes = "";
        this.rowsAntecedentes = 0;
        try {
            this.listDispAntecedentes = new ArrayList<>();
            if (cliente.getIdCliente() != null) {
                this.listDispAntecedentes = this.dispAntecedentesFacade.findByIdCliente(cliente.getIdCliente());
                for (int i = 0; i < this.listDispAntecedentes.size(); i++) {
                    antecedentes = antecedentes + " " + ((DispAntecedentes) this.listDispAntecedentes.get(i)).getAnteFamiliares() + ", " + ((DispAntecedentes) this.listDispAntecedentes.get(i)).getAnteOftalmologicos() + ", " + ((DispAntecedentes) this.listDispAntecedentes.get(i)).getAntePersonales() + "\n";
                    this.rowsAntecedentes++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return antecedentes;
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
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispDetalleDiagnostico = (DispDetalleDiagnostico) event.getObject();
            this.dispDiagnostico = this.dispDetalleDiagnostico.getIdDiagnostico();
            this.dispResultado = this.dispDetalleDiagnostico.getIdResultado();
            this.dispAgendamiento = this.dispResultado.getIdAgendamiento();
            this.listTriaje = this.dispTriajeFacade.findByIdAgendamiento(this.dispAgendamiento.getIdAgendamiento());
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redireccionarRegistroDiagnostico(SelectEvent event) {
        try {
            this.dispDetalleDiagnostico = (DispDetalleDiagnostico) event.getObject();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dispDetalleDiagnostico", this.dispDetalleDiagnostico);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("plantillaDetalleDiagnostico", this.dispDetalleDiagnostico);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("../dispensario/disp_registro_diagnostico.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(PlantillaHistorialMB.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void cargarHistorial() {
    }

    public LazyDataModel<DispDetalleDiagnostico> getAll() {
        if (this.lazyDispDetalleDiagnostico == null) {
            this.lazyDispDetalleDiagnostico = new LazyDetalleDiagnosticoModel(this.ClienteObj.getIdCliente(), this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.estado);
        }
        return this.lazyDispDetalleDiagnostico;
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

    public DispAntecedentes getDispAntecedentes() {
        return this.dispAntecedentes;
    }

    public void setDispAntecedentes(DispAntecedentes dispAntecedentes) {
        this.dispAntecedentes = dispAntecedentes;
    }

    public List<DispAntecedentes> getListDispAntecedentes() {
        return this.listDispAntecedentes;
    }

    public void setListDispAntecedentes(List<DispAntecedentes> listDispAntecedentes) {
        this.listDispAntecedentes = listDispAntecedentes;
    }

    public int getRowsAntecedentes() {
        return this.rowsAntecedentes;
    }

    public void setRowsAntecedentes(int rowsAntecedentes) {
        this.rowsAntecedentes = rowsAntecedentes;
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

    public DispAgendamiento getDispAgendamiento() {
        return this.dispAgendamiento;
    }

    public void setDispAgendamiento(DispAgendamiento dispAgendamiento) {
        this.dispAgendamiento = dispAgendamiento;
    }

    public DispTriaje getDispTriaje() {
        return this.dispTriaje;
    }

    public void setDispTriaje(DispTriaje dispTriaje) {
        this.dispTriaje = dispTriaje;
    }

    public List<DispAgendamiento> getListAgendamiento() {
        return this.listAgendamiento;
    }

    public void setListAgendamiento(List<DispAgendamiento> listAgendamiento) {
        this.listAgendamiento = listAgendamiento;
    }

    public List<DispResultado> getListResultado() {
        return this.listResultado;
    }

    public void setListResultado(List<DispResultado> listResultado) {
        this.listResultado = listResultado;
    }

    public List<DispTriaje> getListTriaje() {
        return this.listTriaje;
    }

    public void setListTriaje(List<DispTriaje> listTriaje) {
        this.listTriaje = listTriaje;
    }

    public List<DispDetalleDiagnostico> getListDetalleDiagnostico() {
        return this.listDetalleDiagnostico;
    }

    public void setListDetalleDiagnostico(List<DispDetalleDiagnostico> listDetalleDiagnostico) {
        this.listDetalleDiagnostico = listDetalleDiagnostico;
    }

    public DispDiagnostico getDispDiagnostico() {
        return this.dispDiagnostico;
    }

    public void setDispDiagnostico(DispDiagnostico dispDiagnostico) {
        this.dispDiagnostico = dispDiagnostico;
    }

    public boolean isVerHistorial() {
        return this.verHistorial;
    }

    public void setVerHistorial(boolean verHistorial) {
        this.verHistorial = verHistorial;
    }
}
