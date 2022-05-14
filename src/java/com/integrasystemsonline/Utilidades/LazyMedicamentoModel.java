package com.integrasystemsonline.Utilidades;

import com.Entity.DispMedicamento;
import com.Session.DispMedicamentoFacade;
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

public class LazyMedicamentoModel extends LazyDataModel<DispMedicamento> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<DispMedicamento> listaDispMedicamento;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyMedicamentoModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    public List<DispMedicamento> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispMedicamentoFacade dispMedicamentoFacade = (DispMedicamentoFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispMedicamentoFacade");
            this.listaDispMedicamento = new ArrayList<>();
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
            setRowCount(dispMedicamentoFacade.countDispMedicamento(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispMedicamento = dispMedicamentoFacade.listaDispMedicamento(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispMedicamento;
    }

    @Override
    public Object getRowKey(DispMedicamento transControlInventario) {
        return transControlInventario.getIdMedicamento();
    }

    @Override
    public DispMedicamento getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispMedicamentoFacade dispMedicamentoFacade = (DispMedicamentoFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispMedicamentoFacade");
            int idValor = Integer.parseInt(id);
            DispMedicamento medicamento = dispMedicamentoFacade.findById(idValor);
            return medicamento;
        } catch (NamingException ex) {
            Logger.getLogger(LazyMedicamentoModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispMedicamento> getListaDispMedicamento() {
        return this.listaDispMedicamento;
    }

    public void setListaDispMedicamento(List<DispMedicamento> listaDispMedicamento) {
        this.listaDispMedicamento = listaDispMedicamento;
    }
}
