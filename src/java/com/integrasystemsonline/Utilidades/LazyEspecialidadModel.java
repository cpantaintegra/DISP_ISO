package com.integrasystemsonline.Utilidades;

import com.Entity.DispEspecialidad;
import com.Session.DispEspecialidadFacade;
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

public class LazyEspecialidadModel extends LazyDataModel<DispEspecialidad> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispEspecialidad> listaDispEspecialidad;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyEspecialidadModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispEspecialidad> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispEspecialidadFacade dispEspecialidadFacade = (DispEspecialidadFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispEspecialidadFacade");
            this.listaDispEspecialidad = new ArrayList<>();
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
            setRowCount(dispEspecialidadFacade.countDispEspecialidad(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispEspecialidad = dispEspecialidadFacade.listaDispEspecialidad(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispEspecialidad;
    }

    @Override
    public Object getRowKey(DispEspecialidad transControlInventario) {
        return transControlInventario.getIdEspecialidad();
    }

    @Override
    public DispEspecialidad getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispEspecialidadFacade dispEspecialidadFacade = (DispEspecialidadFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispEspecialidadFacade");
            int idValor = Integer.parseInt(id);
            DispEspecialidad especialidad = dispEspecialidadFacade.findById(idValor);
            return especialidad;
        } catch (NamingException ex) {
            Logger.getLogger(LazyEspecialidadModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispEspecialidad> getListaDispEspecialidad() {
        return this.listaDispEspecialidad;
    }

    public void setListaDispEspecialidad(List<DispEspecialidad> listaDispEspecialidad) {
        this.listaDispEspecialidad = listaDispEspecialidad;
    }
}
