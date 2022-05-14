package com.integrasystemsonline.Utilidades;

import com.Entity.IsArea;
import com.Session.IsAreaFacade;
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

public class LazyAreaModel extends LazyDataModel<IsArea> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<IsArea> listaIsArea;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyAreaModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<IsArea> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsAreaFacade isAreaFacade = (IsAreaFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsAreaFacade");
            this.listaIsArea = new ArrayList<>();
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
            setRowCount(isAreaFacade.countIsArea(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaIsArea = isAreaFacade.listaIsArea(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsArea;
    }

    @Override
    public Object getRowKey(IsArea transControlInventario) {
        return transControlInventario.getIdArea();
    }

    @Override
    public IsArea getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsAreaFacade isAreaFacade = (IsAreaFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsAreaFacade");
            int idValor = Integer.parseInt(id);
            IsArea area = isAreaFacade.findById(idValor);
            return area;
        } catch (NamingException ex) {
            Logger.getLogger(LazyAreaModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<IsArea> getListaIsArea() {
        return this.listaIsArea;
    }

    public void setListaIsArea(List<IsArea> listaIsArea) {
        this.listaIsArea = listaIsArea;
    }
}
