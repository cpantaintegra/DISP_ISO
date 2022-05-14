package com.integrasystemsonline.Utilidades;

import com.Entity.DispDiagnostico;
import com.Session.DispDiagnosticoFacade;
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

public class LazyDiagnosticoModel extends LazyDataModel<DispDiagnostico> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispDiagnostico> listaDispDiagnostico;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyDiagnosticoModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispDiagnostico> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispDiagnosticoFacade dispDiagnosticoFacade = (DispDiagnosticoFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispDiagnosticoFacade");
            this.listaDispDiagnostico = new ArrayList<>();
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
            setRowCount(dispDiagnosticoFacade.countDispDiagnostico(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispDiagnostico = dispDiagnosticoFacade.listaDispDiagnostico(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispDiagnostico;
    }

    @Override
    public Object getRowKey(DispDiagnostico transControl) {
        return transControl.getIdDiagnostico();
    }

    @Override
    public DispDiagnostico getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispDiagnosticoFacade dispDiagnosticoFacade = (DispDiagnosticoFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispDiagnosticoFacade");
            int idValor = Integer.parseInt(id);
            DispDiagnostico diagnostico = dispDiagnosticoFacade.findById(idValor);
            return diagnostico;
        } catch (NamingException ex) {
            Logger.getLogger(LazyDiagnosticoModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispDiagnostico> getListaDispDiagnostico() {
        return this.listaDispDiagnostico;
    }

    public void setListaDispDiagnostico(List<DispDiagnostico> listaDispDiagnostico) {
        this.listaDispDiagnostico = listaDispDiagnostico;
    }
}
