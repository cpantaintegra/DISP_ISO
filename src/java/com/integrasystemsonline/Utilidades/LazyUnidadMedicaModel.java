package com.integrasystemsonline.Utilidades;

import com.Entity.DispUnidadMedica;
import com.Session.DispUnidadMedicaFacade;
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

public class LazyUnidadMedicaModel extends LazyDataModel<DispUnidadMedica> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispUnidadMedica> listaDispUnidadMedica;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyUnidadMedicaModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispUnidadMedica> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispUnidadMedicaFacade dispUnidadMedicaFacade = (DispUnidadMedicaFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispUnidadMedicaFacade");
            this.listaDispUnidadMedica = new ArrayList<>();
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
            setRowCount(dispUnidadMedicaFacade.countDispUnidadMedica(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispUnidadMedica = dispUnidadMedicaFacade.listaDispUnidadMedica(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispUnidadMedica;
    }

    @Override
    public Object getRowKey(DispUnidadMedica transControlInventario) {
        return transControlInventario.getIdUnidadMedica();
    }

    @Override
    public DispUnidadMedica getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispUnidadMedicaFacade dispUnidadMedicaFacade = (DispUnidadMedicaFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispUnidadMedicaFacade");
            int idValor = Integer.parseInt(id);
            DispUnidadMedica unidadMedica = dispUnidadMedicaFacade.findById(idValor);
            return unidadMedica;
        } catch (NamingException ex) {
            Logger.getLogger(LazyUnidadMedicaModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispUnidadMedica> getListaDispUnidadMedica() {
        return this.listaDispUnidadMedica;
    }

    public void setListaDispUnidadMedica(List<DispUnidadMedica> listaDispUnidadMedica) {
        this.listaDispUnidadMedica = listaDispUnidadMedica;
    }
}
