/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsArea;
import com.Entity.IsRoles;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.IsAreaFacade;
import com.Session.IsUsuariosFacade;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsRolesFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Aes;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyUsuariosModel;
import com.integrasystemsonline.Utilidades.TipoPersona;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named(value = "usuarios")
@SessionScoped
public class UsuariosMB implements Serializable {

    @Resource
    UserTransaction userTransaction;
    @EJB
    IsUsuariosFacade isUsuariosFacade;
    @EJB
    IsEmpresaFacade isEmpresaFacade;
    @EJB
    IsCiudadFacade isCiudadFacade;
    @EJB
    IsSectorFacade isSectorFacade;
    @EJB
    IsAreaFacade isAreaFacade;
    @EJB
    IsRolesFacade isRolesFacade;
    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // La cadena de formato de fecha se pasa como un argumento al objeto de formato de fecha  
    private List<IsUsuarios> listIsUsuarios = new ArrayList();
    private IsUsuarios isUsuarios = new IsUsuarios();
    private UIData dataTable;
    private LazyDataModel<IsUsuarios> lazyIsUsuarios;
    private String filtroConsulta;
    IsUsuarios usuario;
    private String estado, labelMant, tipoPersona;
    private List<Estado> listaEstado;
    private List<TipoPersona> listaTipoPersona;
    private List<IsRoles> listaRoles;
    private List<IsArea> listaArea;
    private Estado estadoObj;
    private IsRoles rolesObj;
    private IsArea areaObj;
    private TipoPersona tipoPersonaObj;
    private List<IsRolesPermisos> listIsRolesPermisos;
    boolean editar = true, eliminarBl = true, consultar = true, ingresar = true;
    private String claveUser;
    String keyaes = "", ivaes = "";
    private String NArchivo;
    private byte[] arch;
    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    @PostConstruct
    public void ini() {
        try {
            Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
            try {
                keyaes = propParam.getProperty("keyaes");
                ivaes = propParam.getProperty("ivaes");
            } catch (Exception e) {
            }
            estado = "A";
            labelMant = "Ingresar";
            listaEstado = new ArrayList<>();
            Estado estado = new Estado();
            estado.setValor("A");
            estado.setDetalle("Activo");
            estadoObj = estado;
            listaEstado.add(estado);

            estado = new Estado();
            estado.setValor("I");
            estado.setDetalle("Inactivo");
            listaEstado.add(estado);

            listaRoles = isRolesFacade.findAllActivos();
            listaArea = isAreaFacade.findAllActivos();

            //tipoPersona="S";
            listaTipoPersona = new ArrayList<>();

            TipoPersona tipoPersona = new TipoPersona();
            //tipoPersona.setValor("S");
            tipoPersona.setDetalle("-Seleccione-");
            tipoPersonaObj = tipoPersona;
            listaTipoPersona.add(tipoPersona);

            tipoPersona = new TipoPersona();
            tipoPersona.setValor("N");
            tipoPersona.setDetalle("Natural");
            listaTipoPersona.add(tipoPersona);

            tipoPersona = new TipoPersona();
            tipoPersona.setValor("J");
            tipoPersona.setDetalle("Juridico");
            listaTipoPersona.add(tipoPersona);

            tipoPersona = new TipoPersona();
            tipoPersona.setValor("E");
            tipoPersona.setDetalle("Extranjero");
            listaTipoPersona.add(tipoPersona);

            usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listIsRolesPermisos = isRolesPermisosFacade.findByRol(usuario.getIdRoles());

            String eli = "", edi = "", con = "", ing = "";
            try {
                eli = propParam.getProperty("eliminar");
                edi = propParam.getProperty("editar");
                con = propParam.getProperty("consultar");
                ing = propParam.getProperty("ingresar");
            } catch (Exception e) {
            }
            for (IsRolesPermisos isRolesPermisos : listIsRolesPermisos) {
                if (isRolesPermisos.getIdPermisos().getNombre().equals(edi)) {
                    editar = false;
                } else if (isRolesPermisos.getIdPermisos().getNombre().equals(eli)) {
                    eliminarBl = false;
                } else if (isRolesPermisos.getIdPermisos().getNombre().equals(con)) {
                    consultar = false;
                } else if (isRolesPermisos.getIdPermisos().getNombre().equals(ing)) {
                    ingresar = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() throws SystemException {
        FacesMessage msg = null;
        boolean guardar = true;
        if (isUsuarios.getIdUsuarios() != null) {
            guardar = false;
        }
        try {
            userTransaction.begin();

            String passEncriptado = Aes.encrypt(keyaes, ivaes, claveUser);
            isUsuarios.setUsuario(isUsuarios.getUsuario().toUpperCase());
            isUsuarios.setIdArea(areaObj);
            isUsuarios.setIdRoles(rolesObj);
            isUsuarios.setClave(passEncriptado);
            isUsuarios.setLogo(NArchivo);
            isUsuarios.setLogoRuta(propParam.getProperty("rutaArchivo") + "Logotipos\\" + NArchivo);
            if (guardar) {
                isUsuarios.setIdEmpresa(usuario.getIdEmpresa());
                isUsuarios.setIdCiudad(usuario.getIdCiudad());
                isUsuarios.setIdSector(usuario.getIdSector());
                isUsuarios.setUsuarioIngreso(usuario.getUsuario());
                isUsuarios.setFechaIngreso(objSDF.parse(objSDF.format(new Date())));
                isUsuariosFacade.createWithValidator(isUsuarios);
                isUsuariosFacade.flush();
            } else {
                if (isUsuarios.getEstado().equals("A")) {
                    isUsuarios.setIntentos(null);
                }
                isUsuarios.setUsuarioModificacion(usuario.getUsuario());
                isUsuarios.setFechaModificacion(objSDF.parse(objSDF.format(new Date())));
                isUsuariosFacade.editWithValidator(isUsuarios);
                isUsuariosFacade.flush();
            }
            userTransaction.commit();
            cargarArchivos(NArchivo, arch);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Se realizo la transaccion con exito.");
            redireccionar();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
            userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void redireccionar() {
        try {
            isUsuarios = new IsUsuarios();
            estado = "A";
            labelMant = "Ingresar";
            claveUser = "";
            NArchivo = "";
            try {
                rolesObj = listaRoles.get(0);
            } catch (Exception e) {
            }
            try {
                areaObj = listaArea.get(0);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    public void eliminar() {
        FacesMessage msg = null;
        try {
            userTransaction.begin();
            isUsuariosFacade.remove(isUsuarios);
            isUsuariosFacade.flush();
            userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDblClckSelect(SelectEvent event) {
        try {
            isUsuarios = (IsUsuarios) event.getObject();
            claveUser = Aes.decrypt(keyaes, ivaes, isUsuarios.getClave());
            labelMant = "Actualizar";
            rolesObj = isUsuarios.getIdRoles();
            areaObj = isUsuarios.getIdArea();
            NArchivo = isUsuarios.getLogo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            NArchivo = event.getFile().getFileName();
            arch = event.getFile().getContents();
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se cargo el archivo con éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void cargarArchivos(String nombre, byte[] archivo) {
        try {
            if (archivo != null) {
                ByteArrayInputStream is = new ByteArrayInputStream(archivo);
                String ruta = propParam.getProperty("rutaArchivo") + "Logotipos\\";
                FileOutputStream fileOuputStream = new FileOutputStream(ruta + nombre);
                fileOuputStream.write(archivo);
                fileOuputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            /* Limpiamos la lista de los archivos cargados */
        }
    }

    public LazyDataModel<IsUsuarios> getAll() {
        if (lazyIsUsuarios == null) {
            lazyIsUsuarios = new LazyUsuariosModel(usuario.getIdEmpresa().getIdEmpresa(), usuario.getIdCiudad().getIdCiudad(), usuario.getIdSector().getIdSector(), tipoPersonaObj.getValor(), estado);
        }
        return lazyIsUsuarios;
    }

    public UIData getDataTable() {
        return dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }

    public List<IsUsuarios> getListIsUsuarios() {
        return listIsUsuarios;
    }

    public void setListIsUsuarios(List<IsUsuarios> listIsUsuarios) {
        this.listIsUsuarios = listIsUsuarios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFiltroConsulta() {
        return filtroConsulta;
    }

    public void setFiltroConsulta(String filtroConsulta) {
        this.filtroConsulta = filtroConsulta;
    }

    public IsUsuarios getIsUsuarios() {
        return isUsuarios;
    }

    public void setisUsuarios(IsUsuarios isUsuarios) {
        this.isUsuarios = isUsuarios;
    }

    public IsUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(IsUsuarios usuario) {
        this.usuario = usuario;
    }

    public String getLabelMant() {
        return labelMant;
    }

    public void setLabelMant(String labelMant) {
        this.labelMant = labelMant;
    }

    public List<Estado> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public Estado getEstadoObj() {
        return estadoObj;
    }

    public void setEstadoObj(Estado estadoObj) {
        this.estadoObj = estadoObj;
    }

    public List<IsRolesPermisos> getListIsRolesPermisos() {
        return listIsRolesPermisos;
    }

    public void setListIsRolesPermisos(List<IsRolesPermisos> listIsRolesPermisos) {
        this.listIsRolesPermisos = listIsRolesPermisos;
    }

    public boolean isEliminarBl() {
        return eliminarBl;
    }

    public void setEliminarBl(boolean eliminarBl) {
        this.eliminarBl = eliminarBl;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public boolean isIngresar() {
        return ingresar;
    }

    public void setIngresar(boolean ingresar) {
        this.ingresar = ingresar;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public List<TipoPersona> getListaTipoPersona() {
        return listaTipoPersona;
    }

    public void setListaTipoPersona(List<TipoPersona> listaTipoPersona) {
        this.listaTipoPersona = listaTipoPersona;
    }

    public TipoPersona getTipoPersonaObj() {
        return tipoPersonaObj;
    }

    public void setTipoPersonaObj(TipoPersona tipoPersonaObj) {
        this.tipoPersonaObj = tipoPersonaObj;
    }

    public String getClaveUser() {
        return claveUser;
    }

    public void setClaveUser(String claveUser) {
        this.claveUser = claveUser;
    }

    public List<IsRoles> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<IsRoles> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public List<IsArea> getListaArea() {
        return listaArea;
    }

    public void setListaArea(List<IsArea> listaArea) {
        this.listaArea = listaArea;
    }

    public IsRoles getRolesObj() {
        return rolesObj;
    }

    public void setRolesObj(IsRoles rolesObj) {
        this.rolesObj = rolesObj;
    }

    public IsArea getAreaObj() {
        return areaObj;
    }

    public void setAreaObj(IsArea areaObj) {
        this.areaObj = areaObj;
    }

    public String getNArchivo() {
        return NArchivo;
    }

    public void setNArchivo(String NArchivo) {
        this.NArchivo = NArchivo;
    }

}
