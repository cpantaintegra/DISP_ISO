package com.integrasystemsonline.Utilidades;

import com.Entity.DispExamen;
import com.Session.DispExamenFacade;
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

public class LazyExamenModel extends LazyDataModel<DispExamen> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispExamen> listaDispExamen;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyExamenModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispExamen> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispExamenFacade dispExamenFacade = (DispExamenFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispExamenFacade");
            this.listaDispExamen = new ArrayList<>();
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
            setRowCount(dispExamenFacade.countDispExamen(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispExamen = dispExamenFacade.listaDispExamen(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispExamen;
    }

    @Override
    public Object getRowKey(DispExamen transControlInventario) {
        return transControlInventario.getIdExamen();
    }

    @Override
    public DispExamen getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispExamenFacade dispExamenFacade = (DispExamenFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispExamenFacade");
            int idValor = Integer.parseInt(id);
            DispExamen examen = dispExamenFacade.findById(idValor);
            return examen;
        } catch (NamingException ex) {
            Logger.getLogger(LazyExamenModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispExamen> getListaDispExamen() {
        return this.listaDispExamen;
    }

    public void setListaDispExamen(List<DispExamen> listaDispExamen) {
        this.listaDispExamen = listaDispExamen;
    }
}
