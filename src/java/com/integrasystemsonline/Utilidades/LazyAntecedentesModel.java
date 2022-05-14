package com.integrasystemsonline.Utilidades;

import com.Entity.DispAntecedentes;
import com.Session.DispAntecedentesFacade;
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

public class LazyAntecedentesModel extends LazyDataModel<DispAntecedentes> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private Integer cliente;

    private String estado;

    private List<DispAntecedentes> listaDispAntecedentes;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyAntecedentesModel(Integer cliente, Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.cliente = cliente;
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispAntecedentes> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispAntecedentesFacade dispAntecedentesFacade = (DispAntecedentesFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispAntecedentesFacade");
            this.listaDispAntecedentes = new ArrayList<>();
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
            setRowCount(dispAntecedentesFacade.countDispAntecedentes(this.cliente, this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispAntecedentes = dispAntecedentesFacade.listaDispAntecedentes(this.cliente, this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispAntecedentes;
    }

    @Override
    public Object getRowKey(DispAntecedentes transControlInventario) {
        return transControlInventario.getIdAntecedentes();
    }

    @Override
    public DispAntecedentes getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispAntecedentesFacade dispAntecedentesFacade = (DispAntecedentesFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispAntecedentesFacade");
            int idValor = Integer.parseInt(id);
            DispAntecedentes antecedente = dispAntecedentesFacade.findById(idValor);
            return antecedente;
        } catch (NamingException ex) {
            Logger.getLogger(LazyAntecedentesModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispAntecedentes> getListaDispAntecedentes() {
        return this.listaDispAntecedentes;
    }

    public void setListaDispAntecedentes(List<DispAntecedentes> listaDispAntecedentes) {
        this.listaDispAntecedentes = listaDispAntecedentes;
    }
}
