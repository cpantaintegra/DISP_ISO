package com.integrasystemsonline.Utilidades;

import com.Entity.DispConsultorio;
import com.Session.DispConsultorioFacade;
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

public class LazyConsultorioModel extends LazyDataModel<DispConsultorio> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private Integer especialidad;

    private String estado;

    private List<DispConsultorio> listaDispConsultorio;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyConsultorioModel(Integer especialidad, Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.especialidad = especialidad;
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispConsultorio> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispConsultorioFacade dispConsultorioFacade = (DispConsultorioFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispConsultorioFacade");
            this.listaDispConsultorio = new ArrayList<>();
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
            setRowCount(dispConsultorioFacade.countDispConsultorio(this.especialidad, this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispConsultorio = dispConsultorioFacade.listaDispConsultorio(this.especialidad, this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispConsultorio;
    }

    @Override
    public Object getRowKey(DispConsultorio transControlInventario) {
        return transControlInventario.getIdConsultorio();
    }

    @Override
    public DispConsultorio getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispConsultorioFacade dispConsultorioFacade = (DispConsultorioFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispConsultorioFacade");
            int idValor = Integer.parseInt(id);
            DispConsultorio consultorio = dispConsultorioFacade.findById(idValor);
            return consultorio;
        } catch (NamingException ex) {
            Logger.getLogger(LazyConsultorioModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispConsultorio> getListaDispConsultorio() {
        return this.listaDispConsultorio;
    }

    public void setListaDispConsultorio(List<DispConsultorio> listaDispConsultorio) {
        this.listaDispConsultorio = listaDispConsultorio;
    }
}
