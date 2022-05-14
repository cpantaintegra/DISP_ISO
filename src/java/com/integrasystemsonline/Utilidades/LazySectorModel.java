package com.integrasystemsonline.Utilidades;

import com.Entity.IsSector;
import com.Session.IsSectorFacade;
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

public class LazySectorModel extends LazyDataModel<IsSector> {

    private String estado;

    private List<IsSector> listaIsSector;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazySectorModel(String estado) {
        this.estado = estado;
    }

    @Override
    public List<IsSector> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsSectorFacade isSectorFacade = (IsSectorFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsSectorFacade");
            this.listaIsSector = new ArrayList<>();
            Set set = filters.entrySet();
            Estado estadoObj = new Estado();
            for (Object object : set) {
                try {
                    Map.Entry me = (Map.Entry) object;
                    estadoObj = (Estado) me.getValue();
                    if (estadoObj.getValor() != null) {
                        this.estado = estadoObj.getValor();
                    }
                } catch (Exception e) {
                    Map.Entry me = (Map.Entry) object;
                    filterValue = (String) me.getValue();
                }
            }
            setRowCount(isSectorFacade.countIsSector(this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaIsSector = isSectorFacade.listaIsSector(this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsSector;
    }

    @Override
    public Object getRowKey(IsSector transControlInventario) {
        return transControlInventario.getIdSector();
    }

    @Override
    public IsSector getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsSectorFacade isSectorFacade = (IsSectorFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsSectorFacade");
            int idValor = Integer.parseInt(id);
            IsSector sector = isSectorFacade.findById(idValor);
            return sector;
        } catch (NamingException ex) {
            Logger.getLogger(LazySectorModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<IsSector> getListaIsSector() {
        return this.listaIsSector;
    }

    public void setListaIsSector(List<IsSector> listaIsSector) {
        this.listaIsSector = listaIsSector;
    }
}
