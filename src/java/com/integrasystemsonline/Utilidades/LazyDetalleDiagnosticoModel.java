package com.integrasystemsonline.Utilidades;

import com.Entity.DispDetalleDiagnostico;
import com.Session.DispDetalleDiagnosticoFacade;
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

public class LazyDetalleDiagnosticoModel extends LazyDataModel<DispDetalleDiagnostico> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private Integer cliente;

    private String estado;
    
    private String medico;
    
    private String especialidad;
    
    private List<DispDetalleDiagnostico> listaDispDetalleDiagnostico;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    Estado estadoObj;
    
    public LazyDetalleDiagnosticoModel(Integer cliente, Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.cliente = cliente;
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<DispDetalleDiagnostico> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            DispDetalleDiagnosticoFacade dispDetalleDiagnosticoFacade = (DispDetalleDiagnosticoFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispDetalleDiagnosticoFacade");
            this.listaDispDetalleDiagnostico = new ArrayList<>();
            Set set = filters.entrySet();
            Estado estadoObj = new Estado();
            for (Object object : set) {
                try {
                    Map.Entry me = (Map.Entry) object;
                    String valoDesc = (String) me.getKey();
                    
                    if (valoDesc.equals("estado")) {
                        this.estadoObj = (Estado) me.getValue();
                        if (this.estadoObj.getValor() != null) {
                            this.estado = this.estadoObj.getValor();
                        }
                    }
                    
                    if (valoDesc.equals("idMedicoPersonal.apaterno")) {
                        filterValue = (String) me.getValue();
                        continue;
                    }
                    
                    if (valoDesc.equals("idMedicoPersonal.idEspecialidad.nombre")) {
                        filterValue = (String) me.getValue();
                        continue;
                    }
                    
                    if (valoDesc.equals("globalFilter")) {
                        if(!me.getValue().toString().isEmpty()){
                            filterValue = (String) me.getValue();
                            continue;
                        }
                    }
                } catch (Exception e) {
                    Map.Entry me = (Map.Entry) object;
                    filterValue = (String) me.getValue();
                }
            }
            setRowCount(dispDetalleDiagnosticoFacade.countDispDetalleDiagnostico(this.cliente, this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaDispDetalleDiagnostico = dispDetalleDiagnosticoFacade.listaDispDetalleDiagnostico(this.cliente, this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaDispDetalleDiagnostico;
    }

    @Override
    public Object getRowKey(DispDetalleDiagnostico transControlInventario) {
        return transControlInventario.getIdDetalleDiagnostico();
    }

    @Override
    public DispDetalleDiagnostico getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            DispDetalleDiagnosticoFacade dispDetalleDiagnosticoFacade = (DispDetalleDiagnosticoFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/DispDetalleDiagnosticoFacade");
            int idValor = Integer.parseInt(id);
            DispDetalleDiagnostico detalleDiagnostico = dispDetalleDiagnosticoFacade.findById(idValor);
            return detalleDiagnostico;
        } catch (NamingException ex) {
            Logger.getLogger(LazyDetalleDiagnosticoModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<DispDetalleDiagnostico> getListaDispDetalleDiagnostico() {
        return this.listaDispDetalleDiagnostico;
    }

    public void setListaDispDetalleDiagnostico(List<DispDetalleDiagnostico> listaDispDetalleDiagnostico) {
        this.listaDispDetalleDiagnostico = listaDispDetalleDiagnostico;
    }
}
