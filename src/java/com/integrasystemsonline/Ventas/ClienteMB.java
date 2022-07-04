package com.integrasystemsonline.Ventas;

import com.Entity.DispCliente;
import com.Entity.DispOrigen;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispAntecedentesFacade;
import com.Session.DispClienteFacade;
import com.Session.DispOrigenFacade;
import com.Session.DispTriajeFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Process.GenerarReporte;
import com.integrasystemsonline.Process.ModeloExcel;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.EstadoCivil;
import com.integrasystemsonline.Utilidades.LazyClienteModel;
import com.integrasystemsonline.Utilidades.Sexo;
import com.integrasystemsonline.Utilidades.TipoDocumento;
import com.integrasystemsonline.Utilidades.Utilidades;
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
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;

@Named("cliente")
@ViewScoped
public class ClienteMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    DispClienteFacade disClienteFacade;

    @EJB
    DispTriajeFacade dispTriajeFacade;

    @EJB
    DispAntecedentesFacade dispAntecedentesFacade;

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    DispOrigenFacade dispOrigenFacade;

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

    private DispCliente dispCliente = new DispCliente();

    private List<TipoDocumento> listaTipoDocumento;

    private List<Sexo> listaSexo;

    private UIData dataTable;

    private LazyDataModel<DispCliente> lazyDispCliente;

    private String filtroConsulta;

    IsUsuarios usuario;

    private String estado;

    private String labelMant;

    private List<Estado> listaEstado;

    private Estado estadoObj;

    private TipoDocumento tipoDocumentoObj;

    private Sexo sexoObj;

    private List<EstadoCivil> listaEstadoCivil;

    private EstadoCivil estadoCivilObj;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true;

    private boolean eliminarBl = true;

    private boolean consultar = true;

    private boolean ingresar = true;

    private PanelGrid panel;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    private String nombArchivo;

    private String ruta;

    private StreamedContent file;

    private DispOrigen origenObj = new DispOrigen();

    private List<DispOrigen> listDispOrigen = new ArrayList<>();

    private boolean numDocumento = true;

    private boolean tipoDocumento = true;

    private boolean nombre = true;

    private boolean paterno = true;

    private boolean materno = true;

    private boolean movil = true;

    private boolean convencional = true;

    private boolean direccion = true;

    private boolean correo = true;

    private boolean fechaNacimiento = true;

    private boolean sexo = true;

    private boolean estadoCivil = true;

    private boolean personaResponsable = true;

    private boolean ocupacion = true;

    private boolean boolEstado = true;

    @PostConstruct
    public void ini() {
        try {
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
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
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
            this.listDispOrigen = this.dispOrigenFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            try {
                this.origenObj = this.listDispOrigen.get(0);
            } catch (Exception exception) {
            }
            generarPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generarPanel() {
        try {
            GenerarReporte obj = new GenerarReporte();
            obj.listarColumnas(this.dispCliente);
            this.panel = obj.getPanel();
        } catch (Exception exception) {
        }
    }

    public void leerCampos() {
        try {
            List<DispCliente> lstCliente = this.disClienteFacade.listaAllCliente(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            DispCliente cliente = new DispCliente();
            Class<? extends DispCliente> clienteInfo = (Class) cliente.getClass();
            int cantAtributtes = (clienteInfo.getDeclaredFields()).length;
            Field[] fields = clienteInfo.getDeclaredFields();
            List<Field> lstField = new ArrayList<>();
            int indice = 0;
            int inicio = 0;
            int fin = 0;
            int i;
            for (i = 0; i < cantAtributtes; i++) {
                if (fields[i].getName().equals("idCliente")) {
                    inicio = i;
                }
                if (fields[i].getName().equals("estado")) {
                    fin = i;
                    i = cantAtributtes;
                }
            }
            for (i = inicio; i <= fin; i++) {
                lstField.add(fields[i]);
            }
            for (DispCliente object : lstCliente) {
                Class<? extends DispCliente> clazz = (Class) object.getClass();
                for (Field variable : lstField) {
                    boolean flag = variable.isAccessible();
                    Type tipoCampo = variable.getGenericType();
                    String nombreCampo = variable.getName();
                    Field field = clazz.getDeclaredField(nombreCampo);
                    field.setAccessible(true);
                    Object fieldValue = field.get(object);
                    System.out.println(nombreCampo + " " + tipoCampo + " " + String.valueOf(fieldValue));
                    field.setAccessible(flag);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void exportar() {
        FacesMessage msg = null;
        try {
            ModeloExcel excelObj = new ModeloExcel();
            List<DispCliente> lstCliente = new ArrayList<>();
            ArrayList<String> lstEncabezados = new ArrayList<>();
            lstCliente = this.disClienteFacade.listaAllCliente(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            DispCliente cliente = new DispCliente();
            Class<? extends DispCliente> clienteInfo = (Class) cliente.getClass();
            int cantAtributtes = (clienteInfo.getDeclaredFields()).length;
            Field[] fields = clienteInfo.getDeclaredFields();
            List<Field> lstField = new ArrayList<>();
            int inicio = 0;
            int fin = 0;
            int i;
            for (i = 0; i < cantAtributtes; i++) {
                if (fields[i].getName().equals("idCliente")) {
                    inicio = i;
                }
                if (fields[i].getName().equals("estado")) {
                    fin = i;
                    i = cantAtributtes;
                }
            }
            for (i = inicio; i <= fin; i++) {
                if (this.numDocumento == true
                        && fields[i].getName().equals("numDocumento")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.tipoDocumento == true
                        && fields[i].getName().equals("tipoDocumento")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.nombre == true
                        && fields[i].getName().equals("nombre")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.paterno == true
                        && fields[i].getName().equals("apaterno")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.materno == true
                        && fields[i].getName().equals("amaterno")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.movil == true
                        && fields[i].getName().equals("celular")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.convencional == true
                        && fields[i].getName().equals("telefono")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.direccion == true
                        && fields[i].getName().equals("direccion")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.correo == true
                        && fields[i].getName().equals("email")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.ocupacion == true
                        && fields[i].getName().equals("ocupacion")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.fechaNacimiento == true
                        && fields[i].getName().equals("fechaNacimiento")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.sexo == true
                        && fields[i].getName().equals("sexo")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.estadoCivil == true
                        && fields[i].getName().equals("estadoCivil")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
                if (this.personaResponsable == true
                        && fields[i].getName().equals("personaResponsable")) {
                    lstField.add(fields[i]);
                    lstEncabezados.add(fields[i].getName().toUpperCase());
                }
            }
            int col = lstField.size();
            int row = lstCliente.size();
            String[][] strHistorial = new String[row][col];
            for (int j = 0; j < row; j++) {
                DispCliente object = lstCliente.get(j);
                Class<? extends DispCliente> clazz = (Class) object.getClass();
                int k = 0;
                for (Field variable : lstField) {
                    boolean flag = variable.isAccessible();
                    Type tipoCampo = variable.getGenericType();
                    String nombreCampo = variable.getName();
                    Field field = clazz.getDeclaredField(nombreCampo);
                    field.setAccessible(true);
                    Object fieldValue = field.get(object);
                    if (fieldValue != null) {
                        if (nombreCampo.equals("tipoDocumento")) {
                            switch (fieldValue.toString()) {
                                case "C":
                                    strHistorial[j][k] = "CEDULA";
                                    break;
                                case "R":
                                    strHistorial[j][k] = "RUC";
                                    break;
                                case "P":
                                    strHistorial[j][k] = "PASAPORTE";
                                    break;
                                default:
                                    strHistorial[j][k] = "ERROR EN CONVERSION";
                                    break;
                            }
                        } else if (nombreCampo.equals("sexo")) {
                            switch (fieldValue.toString()) {
                                case "M":
                                    strHistorial[j][k] = "MASCULINO";
                                    break;
                                case "F":
                                    strHistorial[j][k] = "FEMENINO";
                                    break;
                                default:
                                    strHistorial[j][k] = "ERROR EN CONVERSION";
                                    break;
                            }
                        } else if (nombreCampo.equals("estadoCivil")) {
                            switch (fieldValue.toString()) {
                                case "S":
                                    strHistorial[j][k] = "SOLTERO";
                                    break;
                                case "C":
                                    strHistorial[j][k] = "CASADO";
                                    break;
                                case "D":
                                    strHistorial[j][k] = "DIVORCIADO";
                                    break;
                                case "V":
                                    strHistorial[j][k] = "VIUDO";
                                    break;
                                case "O":
                                    strHistorial[j][k] = "CONCUBINATO";
                                    break;
                                case "E":
                                    strHistorial[j][k] = "SEPARACION EN PROCESO JUDICIAL";
                                    break;
                                default:
                                    strHistorial[j][k] = "ERROR EN CONVERSION";
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
            this.nombArchivo = "listado_clientes";
            excelObj.exportar(this.nombArchivo, this.ruta + this.nombArchivo + ".xlsx", lstEncabezados, strHistorial, col, row);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Export file success");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public StreamedContent cargaArchivo() {
        exportar();
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

    public void ocultarNumDocumento() {
        try {
            if (this.numDocumento == true) {
                this.numDocumento = false;
            } else {
                this.numDocumento = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarTipoDocumento() {
        try {
            if (this.tipoDocumento == true) {
                this.tipoDocumento = false;
            } else {
                this.tipoDocumento = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarNombre() {
        try {
            if (this.nombre == true) {
                this.nombre = false;
            } else {
                this.nombre = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarApaterno() {
        try {
            if (this.paterno == true) {
                this.paterno = false;
            } else {
                this.paterno = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarAmaterno() {
        try {
            if (this.materno == true) {
                this.materno = false;
            } else {
                this.materno = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarMovil() {
        try {
            if (this.movil == true) {
                this.movil = false;
            } else {
                this.movil = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarConvencional() {
        try {
            if (this.convencional == true) {
                this.convencional = false;
            } else {
                this.convencional = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarCorreo() {
        try {
            if (this.correo == true) {
                this.correo = false;
            } else {
                this.correo = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarFechaNacimiento() {
        try {
            if (this.fechaNacimiento == true) {
                this.fechaNacimiento = false;
            } else {
                this.fechaNacimiento = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarSexo() {
        try {
            if (this.sexo == true) {
                this.sexo = false;
            } else {
                this.sexo = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarEstadoCivil() {
        try {
            if (this.estadoCivil == true) {
                this.estadoCivil = false;
            } else {
                this.estadoCivil = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarDireccion() {
        try {
            if (this.direccion == true) {
                this.direccion = false;
            } else {
                this.direccion = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarOcupacion() {
        try {
            if (this.ocupacion == true) {
                this.ocupacion = false;
            } else {
                this.ocupacion = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarResponsable() {
        try {
            if (this.personaResponsable == true) {
                this.personaResponsable = false;
            } else {
                this.personaResponsable = true;
            }
        } catch (Exception exception) {
        }
    }

    public void ocultarEstado() {
        try {
            if (this.boolEstado == true) {
                this.boolEstado = false;
            } else {
                this.boolEstado = true;
            }
        } catch (Exception exception) {
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (this.dispCliente.getIdCliente() != null) {
            guardar = false;
        }
        try {
            this.userTransaction.begin();
            this.dispCliente.setIdOrigen(this.origenObj);
            this.dispCliente.setApellsNomb(dispCliente.getApaterno().concat(" ").concat(dispCliente.getAmaterno()).concat(" ").concat(dispCliente.getNombre()));
            if (guardar) {
                this.dispCliente.setIdEmpresa(this.usuario.getIdEmpresa());
                this.dispCliente.setIdCiudad(this.usuario.getIdCiudad());
                this.dispCliente.setIdSector(this.usuario.getIdSector());
                this.dispCliente.setUsuarioIngreso(this.usuario.getUsuario());
                this.dispCliente.setFechaIngreso(this.objSDF.parse(this.objSDF.format(new Date())));
                this.disClienteFacade.createWithValidator(this.dispCliente);
                this.disClienteFacade.flush();
            } else {
                this.dispCliente.setUsuarioModificacion(this.usuario.getUsuario());
                this.dispCliente.setFechaModificacion(this.objSDF.parse(this.objSDF.format(new Date())));
                this.disClienteFacade.editWithValidator(this.dispCliente);
                this.disClienteFacade.flush();
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

    public void redireccionar() {
        try {
            this.dispCliente = new DispCliente();
            this.estado = "A";
            this.labelMant = "Ingresar";
            try {
                this.sexoObj = this.listaSexo.get(0);
            } catch (Exception exception) {
            }
            try {
                this.estadoCivilObj = this.listaEstadoCivil.get(0);
            } catch (Exception exception) {
            }
        } catch (Exception exception) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            this.userTransaction.begin();
            this.disClienteFacade.remove(this.dispCliente);
            this.disClienteFacade.flush();
            this.userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            this.dispCliente = (DispCliente) event.getObject();
            this.labelMant = "Actualizar";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LazyDataModel<DispCliente> getAll() {
        if (this.lazyDispCliente == null) {
            this.lazyDispCliente = new LazyClienteModel(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector(), this.tipoDocumentoObj.getValor(), this.estado);
        }
        return this.lazyDispCliente;
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

    public DispCliente getDispCliente() {
        return this.dispCliente;
    }

    public void setDispCliente(DispCliente dispCliente) {
        this.dispCliente = dispCliente;
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

    public List<TipoDocumento> getListaTipoDocumento() {
        return this.listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public List<Sexo> getListaSexo() {
        return this.listaSexo;
    }

    public void setListaSexo(List<Sexo> listaSexo) {
        this.listaSexo = listaSexo;
    }

    public TipoDocumento getTipoDocumentoObj() {
        return this.tipoDocumentoObj;
    }

    public void setTipoDocumentoObj(TipoDocumento tipoDocumentoObj) {
        this.tipoDocumentoObj = tipoDocumentoObj;
    }

    public Sexo getSexoObj() {
        return this.sexoObj;
    }

    public void setSexoObj(Sexo sexoObj) {
        this.sexoObj = sexoObj;
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

    public PanelGrid getPanel() {
        return this.panel;
    }

    public void setPanel(PanelGrid panel) {
        this.panel = panel;
    }

    public String getNombArchivo() {
        return this.nombArchivo;
    }

    public void setNombArchivo(String nombArchivo) {
        this.nombArchivo = nombArchivo;
    }

    public String getRuta() {
        return this.ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public StreamedContent getFile() {
        return this.file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public boolean isNumDocumento() {
        return this.numDocumento;
    }

    public void setNumDocumento(boolean numDocumento) {
        this.numDocumento = numDocumento;
    }

    public boolean isTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(boolean tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public boolean isNombre() {
        return this.nombre;
    }

    public void setNombre(boolean nombre) {
        this.nombre = nombre;
    }

    public boolean isPaterno() {
        return this.paterno;
    }

    public void setPaterno(boolean paterno) {
        this.paterno = paterno;
    }

    public boolean isMaterno() {
        return this.materno;
    }

    public void setMaterno(boolean materno) {
        this.materno = materno;
    }

    public boolean isMovil() {
        return this.movil;
    }

    public void setMovil(boolean movil) {
        this.movil = movil;
    }

    public boolean isConvencional() {
        return this.convencional;
    }

    public void setConvencional(boolean convencional) {
        this.convencional = convencional;
    }

    public boolean isDireccion() {
        return this.direccion;
    }

    public void setDireccion(boolean direccion) {
        this.direccion = direccion;
    }

    public boolean isCorreo() {
        return this.correo;
    }

    public void setCorreo(boolean correo) {
        this.correo = correo;
    }

    public boolean isFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(boolean fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isSexo() {
        return this.sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public boolean isEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(boolean estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public boolean isPersonaResponsable() {
        return this.personaResponsable;
    }

    public void setPersonaResponsable(boolean personaResponsable) {
        this.personaResponsable = personaResponsable;
    }

    public boolean isOcupacion() {
        return this.ocupacion;
    }

    public void setOcupacion(boolean ocupacion) {
        this.ocupacion = ocupacion;
    }

    public boolean isBoolEstado() {
        return this.boolEstado;
    }

    public void setBoolEstado(boolean boolEstado) {
        this.boolEstado = boolEstado;
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
}
