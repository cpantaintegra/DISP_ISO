/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Administracion;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsRoles;
import com.Entity.IsRolesPermisos;
import com.Entity.IsSector;
import com.Entity.IsUsuarios;
import com.Session.IsCiudadFacade;
import com.Session.IsEmpresaFacade;
import com.Session.IsRolesPermisosFacade;
import com.Session.IsSectorFacade;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.LazyEmpresaModel;
import com.integrasystemsonline.Utilidades.SRI;
import com.integrasystemsonline.Utilidades.Utilidades;
import javax.inject.Named;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@Named(value = "empresa")
@ViewScoped
public class EmpresaMB implements Serializable{
    @Resource
    UserTransaction userTransaction;
    @EJB
    IsEmpresaFacade isEmpresaFacade;
    @EJB
    IsCiudadFacade isCiudadFacade;
    @EJB
    IsSectorFacade isSectorFacade;
    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;
    
    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // La cadena de formato de fecha se pasa como un argumento al objeto de formato de fecha  
    private List<IsEmpresa> listIsEmpresa=new ArrayList();
    private IsEmpresa isEmpresa = new IsEmpresa();
    private UIData dataTable;
    private LazyDataModel<IsEmpresa> lazyIsEmpresa;
    private String filtroConsulta;
    IsUsuarios usuario;
    private String estado, labelMant, sri;
    private IsCiudad ciudad;
    private IsSector sector;
    private List<Estado> listaEstado;   
    private List<IsCiudad> listaCiudad; 
    private List<IsSector> listaSector; 
    private List<SRI> listaFacSri;   
    private Estado estadoObj;
    private SRI sriObj;
    private IsCiudad ciudadObj;
    private IsSector sectorObj;
    private List<IsRolesPermisos> listIsRolesPermisos;
    private boolean editar=true, eliminarBl=true, consultar=true, ingresar=true;
    
    @PostConstruct
    public void ini() 
    {
        try
        {
            this.estado="A";
            
            labelMant="Ingresar";
            listaEstado = new ArrayList<>();
            Estado estado = new Estado();
            estado.setValor("A");
            estado.setDetalle("Activo");
            estadoObj=estado;
            listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("I");
            estado.setDetalle("Inactivo");
            listaEstado.add(estado);
            
            listaFacSri = new ArrayList<>();           
            SRI sri = new SRI();
            sri.setDetalle("-Seleccione-");
            sriObj=sri;            
            listaFacSri.add(sri);
            
            sri = new SRI();
            sri.setValor("S");
            sri.setDetalle("SI");
            listaFacSri.add(sri);
            
            sri = new SRI();
            sri.setValor("N");
            sri.setDetalle("NO");
            listaFacSri.add(sri);
            
            listaCiudad = isCiudadFacade.findAllActivos();
            listaSector = isSectorFacade.findAllActivos();
            usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listIsRolesPermisos = isRolesPermisosFacade.findByRol(usuario.getIdRoles());
            
            String eli="",edi="",con="",ing="";
            Properties propParam=Utilidades.obtenerProperties("MessageResources.properties");
            try {
                eli=propParam.getProperty("eliminar");
                edi=propParam.getProperty("editar");
                con=propParam.getProperty("consultar");
                ing=propParam.getProperty("ingresar");
            } catch (Exception e) {
            }
            
            for (IsRolesPermisos isRolesPermisos : listIsRolesPermisos) {
                if(isRolesPermisos.getIdPermisos().getNombre().equals(edi))
                    editar=false;
                else if(isRolesPermisos.getIdPermisos().getNombre().equals(eli))
                    eliminarBl=false;
                else if(isRolesPermisos.getIdPermisos().getNombre().equals(con))
                    consultar=false;
                else if(isRolesPermisos.getIdPermisos().getNombre().equals(ing))
                    ingresar=false;
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void guardar() throws SystemException{
        FacesMessage msg=null;
        boolean guardar=true;
        if(isEmpresa.getIdEmpresa()!=null)
                guardar=false;
        try {
            userTransaction.begin();
           
            isEmpresa.setIdCiudad(ciudadObj);
            isEmpresa.setIdSector(sectorObj);
            if(guardar)
            {               
                isEmpresa.setUsuarioIngreso(usuario.getUsuario());
                isEmpresa.setFechaIngreso(objSDF.parse(objSDF.format(new Date())));
                isEmpresaFacade.createWithValidator(isEmpresa);
                isEmpresaFacade.flush();
            }else
            {
                isEmpresa.setUsuarioModificacion(usuario.getUsuario());
                isEmpresa.setFechaModificacion(objSDF.parse(objSDF.format(new Date())));  
                isEmpresaFacade.editWithValidator(isEmpresa);
                isEmpresaFacade.flush();
            }
            userTransaction.commit();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Se realizo la transaccion con exito." );
            redireccionar();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error",e.toString());
            userTransaction.rollback();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    public void redireccionar(){
        try {
            isEmpresa = new IsEmpresa();
            estado="A";
            labelMant="Ingresar";
            try{ciudadObj = listaCiudad.get(0);}catch(Exception e){}
            try{sectorObj = listaSector.get(0);}catch(Exception e){}
        } catch (Exception e) {
        }       
    }   
    
    public void eliminar(){
        FacesMessage msg=null;
        try {
            userTransaction.begin();            
            isEmpresaFacade.remove(isEmpresa);
            isEmpresaFacade.flush();
            userTransaction.commit();               
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Eliminado con exito");
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error",e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowDblClckSelect(SelectEvent event) {
        try
        {
            isEmpresa = (IsEmpresa) event.getObject();
            labelMant="Actualizar";
            ciudadObj = isEmpresa.getIdCiudad();
            sectorObj = isEmpresa.getIdSector();
        }catch(Exception e){e.printStackTrace();}
    }
    
    public LazyDataModel<IsEmpresa> getAll() {
        if (lazyIsEmpresa == null) {
            lazyIsEmpresa = new LazyEmpresaModel(null,estado);
        }
        return lazyIsEmpresa;
    }

    public UIData getDataTable() {
        return dataTable;
    }

    public void setDataTable(UIData usersDataTable) {
        this.dataTable = usersDataTable;
    }
    
    public List<IsEmpresa> getListIsEmpresa() {
        return listIsEmpresa;
    }

    public void setListIsEmpresa(List<IsEmpresa> listIsEmpresa) {
        this.listIsEmpresa = listIsEmpresa;
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

    public IsEmpresa getIsEmpresa() {
        return isEmpresa;
    }

    public void setIsEmpresa(IsEmpresa isEmpresa) {
        this.isEmpresa = isEmpresa;
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

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
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

    public List<IsRolesPermisos> getListIsRolesPermisos() {
        return listIsRolesPermisos;
    }

    public void setListIsRolesPermisos(List<IsRolesPermisos> listIsRolesPermisos) {
        this.listIsRolesPermisos = listIsRolesPermisos;
    }

    public List<IsCiudad> getListaCiudad() {
        return listaCiudad;
    }

    public void setListaCiudad(List<IsCiudad> listaCiudad) {
        this.listaCiudad = listaCiudad;
    }

    public List<IsSector> getListaSector() {
        return listaSector;
    }

    public void setListaSector(List<IsSector> listaSector) {
        this.listaSector = listaSector;
    }

    public IsCiudad getCiudadObj() {
        return ciudadObj;
    }

    public void setCiudadObj(IsCiudad ciudadObj) {
        this.ciudadObj = ciudadObj;
    }

    public IsSector getSectorObj() {
        return sectorObj;
    }

    public void setSectorObj(IsSector sectorObj) {
        this.sectorObj = sectorObj;
    } 

    public List<SRI> getListaFacSri() {
        return listaFacSri;
    }

    public void setListaFacSri(List<SRI> listaFacSri) {
        this.listaFacSri = listaFacSri;
    }

    public SRI getSriObj() {
        return sriObj;
    }

    public void setSriObj(SRI sriObj) {
        this.sriObj = sriObj;
    }

    
    
}
