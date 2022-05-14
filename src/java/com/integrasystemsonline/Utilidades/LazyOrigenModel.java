package com.integrasystemsonline.Utilidades;

import com.Entity.DispOrigen;
import com.Session.DispOrigenFacade;
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

public class LazyOrigenModel extends LazyDataModel<DispOrigen> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispOrigen> listaDispOrigen;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyOrigenModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispOrigen> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispOrigenFacade dispOrigenFacade = (DispOrigenFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispOrigenFacade");
            this.listaDispOrigen = new ArrayList<>();
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
            setRowCount(dispOrigenFacade.countDispOrigen(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispOrigen = dispOrigenFacade.listaDispOrigen(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispOrigen;
    }

    @Override
    public Object getRowKey(DispOrigen transControlInventario) {
        return transControlInventario.getIdOrigen();
    }

    @Override
    public DispOrigen getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispOrigenFacade dispOrigenFacade = (DispOrigenFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispOrigenFacade");
            int idValor = Integer.parseInt(id);
            DispOrigen origen = dispOrigenFacade.findById(idValor);
            return origen;
        } catch (NamingException ex) {
            Logger.getLogger(LazyOrigenModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispOrigen> getListaDispOrigen() {
        return this.listaDispOrigen;
    }

    public void setListaDispOrigen(List<DispOrigen> listaDispOrigen) {
        this.listaDispOrigen = listaDispOrigen;
    }
}
