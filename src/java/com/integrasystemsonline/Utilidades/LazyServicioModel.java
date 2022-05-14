package com.integrasystemsonline.Utilidades;

import com.Entity.DispServicio;
import com.Session.DispServicioFacade;
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

public class LazyServicioModel extends LazyDataModel<DispServicio> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispServicio> listaDispServicio;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyServicioModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispServicio> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispServicioFacade dispServicioFacade = (DispServicioFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispServicioFacade");
            this.listaDispServicio = new ArrayList<>();
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
            setRowCount(dispServicioFacade.countDispServicio(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispServicio = dispServicioFacade.listaDispServicio(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispServicio;
    }

    @Override
    public Object getRowKey(DispServicio transControlInventario) {
        return transControlInventario.getIdServicio();
    }

    @Override
    public DispServicio getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispServicioFacade dispServicioFacade = (DispServicioFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispServicioFacade");
            int idValor = Integer.parseInt(id);
            DispServicio servicio = dispServicioFacade.findById(idValor);
            return servicio;
        } catch (NamingException ex) {
            Logger.getLogger(LazyServicioModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispServicio> getListaDispServicio() {
        return this.listaDispServicio;
    }

    public void setListaDispServicio(List<DispServicio> listaDispServicio) {
        this.listaDispServicio = listaDispServicio;
    }
}
