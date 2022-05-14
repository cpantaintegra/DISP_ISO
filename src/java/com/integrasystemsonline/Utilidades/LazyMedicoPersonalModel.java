package com.integrasystemsonline.Utilidades;

import com.Entity.DispMedicoPersonal;
import com.Session.DispMedicoPersonalFacade;
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

public class LazyMedicoPersonalModel extends LazyDataModel<DispMedicoPersonal> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispMedicoPersonal> listaDispMedicoPersonal;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    Estado estadoObj;

    String tipo_documento;
    
    TipoDocumento tipo_documentoObj;

    public LazyMedicoPersonalModel(Integer empresa, Integer ciudad, Integer sector, String tipo_documento, String estado) {
        this.estadoObj = new Estado();
        this.tipo_documento = tipo_documento;
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispMedicoPersonal> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispMedicoPersonalFacade dispMedicoPersonalFacade = (DispMedicoPersonalFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispMedicoPersonalFacade");
            this.listaDispMedicoPersonal = new ArrayList<>();
            Set set = filters.entrySet();
            for (Object object : set) {
                try {
                    Map.Entry me = (Map.Entry) object;
                    String valoDesc = (String) me.getKey();
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
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            setRowCount(dispMedicoPersonalFacade.countDispMedicoPersonal(this.empresa, this.ciudad, this.sector, this.tipo_documento, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispMedicoPersonal = dispMedicoPersonalFacade.listaDispMedicoPersonal(this.empresa, this.ciudad, this.sector, this.tipo_documento, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispMedicoPersonal;
    }

    @Override
    public Object getRowKey(DispMedicoPersonal transDisMedico) {
        return transDisMedico.getIdMedicoPersonal();
    }

    @Override
    public DispMedicoPersonal getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispMedicoPersonalFacade dispMedicoPersonalFacade = (DispMedicoPersonalFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispMedicoPersonalFacade");
            int idValor = Integer.parseInt(id);
            DispMedicoPersonal medico = dispMedicoPersonalFacade.findById(idValor);
            return medico;
        } catch (NamingException ex) {
            Logger.getLogger(LazyMedicoPersonalModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispMedicoPersonal> getListaDispMedicoPersonal() {
        return this.listaDispMedicoPersonal;
    }

    public void setListaDispMedicoPersonal(List<DispMedicoPersonal> listaDispMedicoPersonal) {
        this.listaDispMedicoPersonal = listaDispMedicoPersonal;
    }
}
