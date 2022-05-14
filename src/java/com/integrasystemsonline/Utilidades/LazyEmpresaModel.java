package com.integrasystemsonline.Utilidades;

import com.Entity.IsEmpresa;
import com.Session.IsEmpresaFacade;
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

public class LazyEmpresaModel extends LazyDataModel<IsEmpresa> {

    private String estado;

    private String sri;

    private List<IsEmpresa> listaIsEmpresa;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    Estado estadoObj;

    SRI sriObj;

    public LazyEmpresaModel(String sri, String estado) {
        this.estadoObj = new Estado();
        this.sriObj = new SRI();
        this.estado = estado;
        this.sri = sri;
    }

    @Override
    public List<IsEmpresa> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsEmpresaFacade isEmpresaFacade = (IsEmpresaFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsEmpresaFacade");
            this.listaIsEmpresa = new ArrayList<>();
            Set set = filters.entrySet();
            for (Object object : set) {
                try {
                    Map.Entry me = (Map.Entry) object;
                    String valoDesc = (String) me.getKey();
                    if (valoDesc.equals("facSri")) {
                        this.sriObj = (SRI) me.getValue();
                        if (this.sriObj.getValor() != null) {
                            try {
                                this.sri = this.sriObj.getValor();
                            } catch (Exception e) {
                                this.sri = null;
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
            setRowCount(isEmpresaFacade.countIsEmpresa(this.estado, this.sri, filterValue));
            if (getRowCount() > 0) {
                this.listaIsEmpresa = isEmpresaFacade.listaIsEmpresa(this.estado, this.sri, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsEmpresa;
    }

    @Override
    public Object getRowKey(IsEmpresa transControlInventario) {
        return transControlInventario.getIdEmpresa();
    }

    @Override
    public IsEmpresa getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsEmpresaFacade isEmpresaFacade = (IsEmpresaFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsEmpresaFacade");
            int idValor = Integer.parseInt(id);
            IsEmpresa empresa = isEmpresaFacade.findById(idValor);
            return empresa;
        } catch (NamingException ex) {
            Logger.getLogger(LazyEmpresaModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<IsEmpresa> getListaIsEmpresa() {
        return this.listaIsEmpresa;
    }

    public void setListaIsEmpresa(List<IsEmpresa> listaIsEmpresa) {
        this.listaIsEmpresa = listaIsEmpresa;
    }
}
