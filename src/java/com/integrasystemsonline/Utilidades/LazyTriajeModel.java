package com.integrasystemsonline.Utilidades;

import com.Entity.DispTriaje;
import com.Session.DispTriajeFacade;
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

public class LazyTriajeModel extends LazyDataModel<DispTriaje> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispTriaje> listaDispTriaje;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyTriajeModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispTriaje> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispTriajeFacade dispTriajeFacade = (DispTriajeFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispTriajeFacade");
            this.listaDispTriaje = new ArrayList<>();
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
            setRowCount(dispTriajeFacade.countDispTriaje(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispTriaje = dispTriajeFacade.listaDispTriaje(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispTriaje;
    }

    @Override
    public Object getRowKey(DispTriaje transControlInventario) {
        return transControlInventario.getIdTriaje();
    }

    @Override
    public DispTriaje getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispTriajeFacade dispTriajeFacade = (DispTriajeFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispTriajeFacade");
            int idValor = Integer.parseInt(id);
            DispTriaje servicio = dispTriajeFacade.findById(idValor);
            return servicio;
        } catch (NamingException ex) {
            Logger.getLogger(LazyTriajeModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispTriaje> getListaDispTriaje() {
        return this.listaDispTriaje;
    }

    public void setListaDispTriaje(List<DispTriaje> listaDispTriaje) {
        this.listaDispTriaje = listaDispTriaje;
    }
}
