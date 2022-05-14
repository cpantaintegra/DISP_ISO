package com.integrasystemsonline.Utilidades;

import com.Entity.DispCliente;
import com.Session.DispClienteFacade;
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

public class LazyClienteModel extends LazyDataModel<DispCliente> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private String tipo_documento;

    private List<DispCliente> listaDispCliente;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    Estado estadoObj;

    TipoDocumento tipo_documentoObj;

    public LazyClienteModel(Integer empresa, Integer ciudad, Integer sector, String tipo_documento, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.tipo_documento = tipo_documento;
        this.estado = estado;
    }

    @Override
    public List<DispCliente> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispClienteFacade disClienteFacade = (DispClienteFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispClienteFacade");
            this.listaDispCliente = new ArrayList<>();
            Set set = filters.entrySet();
            for (Object object : set) {
                try {
                    Map.Entry me = (Map.Entry) object;
                    String valoDesc = (String) me.getKey();
                    if (valoDesc.equals("tipoDocumento")) {
                        this.tipo_documentoObj = (TipoDocumento) me.getValue();
                        if (this.tipo_documentoObj.getDetalle().equals("-Seleccione-")) {
                            this.tipo_documento = null;
                            continue;
                        }
                        if (this.tipo_documentoObj.getValor() != null) {
                            try {
                                this.tipo_documento = this.tipo_documentoObj.getValor();
                            } catch (Exception e) {
                                this.tipo_documento = null;
                            }
                        }
                        continue;
                    }
                    if (valoDesc.equals("globalFilter")) {
                        filterValue = (String) me.getValue();
                        continue;
                    }
                    if (valoDesc.equals("estado")) {
                        this.estadoObj = (Estado) me.getValue();
                        if (this.estadoObj.getValor() != null) {
                            this.estado = this.estadoObj.getValor();
                        }
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            setRowCount(disClienteFacade.countDispCliente(this.empresa, this.ciudad, this.sector, this.tipo_documento, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispCliente = disClienteFacade.listaDispCliente(this.empresa, this.ciudad, this.sector, this.tipo_documento, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispCliente;
    }

    @Override
    public Object getRowKey(DispCliente transDisCliente) {
        return transDisCliente.getIdCliente();
    }

    @Override
    public DispCliente getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispClienteFacade facClienteFacade = (DispClienteFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispClienteFacade");
            int idValor = Integer.parseInt(id);
            DispCliente disCliente = facClienteFacade.findById(idValor);
            return disCliente;
        } catch (NamingException ex) {
            Logger.getLogger(LazyClienteModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispCliente> getListaDispCliente() {
        return this.listaDispCliente;
    }

    public void setListaDispCliente(List<DispCliente> listaDispCliente) {
        this.listaDispCliente = listaDispCliente;
    }
}
