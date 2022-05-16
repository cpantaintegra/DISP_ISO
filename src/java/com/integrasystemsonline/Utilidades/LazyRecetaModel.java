package com.integrasystemsonline.Utilidades;

import com.Entity.DispReceta;
import com.Session.DispRecetaFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyRecetaModel extends LazyDataModel<DispReceta> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;
    
    private Integer cliente;
    
    private Integer medico;
    
    private List<DispReceta> listaDispReceta;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
    
    Estado estadoObj;
    Estado clienteObj;
    Estado medicoObj;
    
    public LazyRecetaModel(Integer cliente, Integer medico, Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
        this.cliente = cliente;
        this.medico = medico;
    }

    @Override
    public List<DispReceta> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispRecetaFacade dispRecetaFacade = (DispRecetaFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispRecetaFacade");
            this.listaDispReceta = new ArrayList<>();
            Set set = filters.entrySet();
            Estado estadoObj = new Estado();
            for (Object object : set) {
                try {
                    Map.Entry me = (Map.Entry) object;
                    String valoDesc = (String) me.getKey();
                    
                    if (valoDesc.equals("estado")) {
                        this.estadoObj = (Estado) me.getValue();
                        if (this.estadoObj.getValor() != null) {
                            this.estado = this.estadoObj.getValor();
                        }
                    }
                    
                    if (valoDesc.equals("idAgendamiento.idMedicoPersonal")) {
                        this.medicoObj = (Estado) me.getValue();
                        if (this.medicoObj.getValor()!= null) {
                            this.medico = Integer.parseInt(this.medicoObj.getValor());
                        }
                        else{
                            this.medico = null;
                        }
                        continue;
                    }
                    
                    if (valoDesc.equals("idAgendamiento.idCliente")) {
                        this.clienteObj = (Estado) me.getValue();
                        if (this.clienteObj.getValor()!= null) {
                            this.cliente = Integer.parseInt(this.clienteObj.getValor());
                        }
                        else{
                            this.cliente = null;
                        }
                        continue;
                    }
                    
                    if (valoDesc.equals("globalFilter")) {
                        if(!me.getValue().toString().isEmpty()){
                            filterValue = (String) me.getValue();
                            continue;
                        }
                    }
                } catch (Exception e) {
                    Map.Entry me = (Map.Entry) object;
                    filterValue = (String) me.getValue();
                }
            }
            setRowCount(dispRecetaFacade.countDispReceta(this.cliente, this.medico, this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispReceta = dispRecetaFacade.listaDispReceta(this.cliente, this.medico, this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispReceta;
    }

    @Override
    public Object getRowKey(DispReceta transControlInventario) {
        return transControlInventario.getIdReceta();
    }

    @Override
    public DispReceta getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispRecetaFacade dispRecetaFacade = (DispRecetaFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispRecetaFacade");
            int idValor = Integer.parseInt(id);
            DispReceta receta = dispRecetaFacade.findById(idValor);
            return receta;
        } catch (NamingException ex) {
            Logger.getLogger(LazyRecetaModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispReceta> getListaDispReceta() {
        return this.listaDispReceta;
    }

    public void setListaDispReceta(List<DispReceta> listaDispReceta) {
        this.listaDispReceta = listaDispReceta;
    }
}
