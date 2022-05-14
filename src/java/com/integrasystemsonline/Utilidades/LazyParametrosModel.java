package com.integrasystemsonline.Utilidades;

import com.Entity.IsParametros;
import com.Session.IsParametrosFacade;
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

public class LazyParametrosModel extends LazyDataModel<IsParametros> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<IsParametros> listaIsParametros;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyParametrosModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<IsParametros> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsParametrosFacade isParametrosFacade = (IsParametrosFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsParametrosFacade");
            this.listaIsParametros = new ArrayList<>();
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
            setRowCount(isParametrosFacade.countIsParametros(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaIsParametros = isParametrosFacade.listaIsParametros(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsParametros;
    }

    @Override
    public Object getRowKey(IsParametros transControlInventario) {
        return transControlInventario.getIdParametro();
    }

    @Override
    public IsParametros getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsParametrosFacade isParametrosFacade = (IsParametrosFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsParametrosFacade");
            int idValor = Integer.parseInt(id);
            IsParametros parametro = isParametrosFacade.findById(idValor);
            return parametro;
        } catch (NamingException ex) {
            Logger.getLogger(LazyParametrosModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<IsParametros> getListaIsParametros() {
        return this.listaIsParametros;
    }

    public void setListaIsParametros(List<IsParametros> listaIsParametros) {
        this.listaIsParametros = listaIsParametros;
    }
}
