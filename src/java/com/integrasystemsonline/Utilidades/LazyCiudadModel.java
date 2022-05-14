package com.integrasystemsonline.Utilidades;

import com.Entity.IsCiudad;
import com.Session.IsCiudadFacade;
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

public class LazyCiudadModel extends LazyDataModel<IsCiudad> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<IsCiudad> listaIsCiudad;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyCiudadModel(String estado) {
        this.estado = estado;
    }

    public List<IsCiudad> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsCiudadFacade isCiudadFacade = (IsCiudadFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsCiudadFacade");
            this.listaIsCiudad = new ArrayList<>();
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
            setRowCount(isCiudadFacade.countIsCiudad(this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaIsCiudad = isCiudadFacade.listaIsCiudad(this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsCiudad;
    }

    public Object getRowKey(IsCiudad transControlInventario) {
        return transControlInventario.getIdCiudad();
    }

    public IsCiudad getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsCiudadFacade isCiudadFacade = (IsCiudadFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsCiudadFacade");
            int idValor = Integer.parseInt(id);
            IsCiudad ciudad = isCiudadFacade.findById(idValor);
            return ciudad;
        } catch (NamingException ex) {
            Logger.getLogger(LazyCiudadModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<IsCiudad> getListaIsCiudad() {
        return this.listaIsCiudad;
    }

    public void setListaIsCiudad(List<IsCiudad> listaIsCiudad) {
        this.listaIsCiudad = listaIsCiudad;
    }
}
