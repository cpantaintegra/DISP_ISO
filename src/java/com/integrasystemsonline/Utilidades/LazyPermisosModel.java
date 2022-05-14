package com.integrasystemsonline.Utilidades;

import com.Entity.IsPermisos;
import com.Session.IsPermisosFacade;
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

public class LazyPermisosModel extends LazyDataModel<IsPermisos> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<IsPermisos> listaIsPermisos;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyPermisosModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<IsPermisos> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsPermisosFacade isPermisosFacade = (IsPermisosFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsPermisosFacade");
            this.listaIsPermisos = new ArrayList<>();
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
            setRowCount(isPermisosFacade.countIsPermisos(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaIsPermisos = isPermisosFacade.listaIsPermisos(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsPermisos;
    }

    @Override
    public Object getRowKey(IsPermisos transControlInventario) {
        return transControlInventario.getIdPermisos();
    }

    @Override
    public IsPermisos getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsPermisosFacade isPermisosFacade = (IsPermisosFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsPermisosFacade");
            int idValor = Integer.parseInt(id);
            IsPermisos permisos = isPermisosFacade.findById(idValor);
            return permisos;
        } catch (NamingException ex) {
            Logger.getLogger(LazyPermisosModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<IsPermisos> getListaIsPermisos() {
        return this.listaIsPermisos;
    }

    public void setListaIsPermisos(List<IsPermisos> listaIsPermisos) {
        this.listaIsPermisos = listaIsPermisos;
    }
}
