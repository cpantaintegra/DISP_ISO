package com.integrasystemsonline.Utilidades;

import com.Entity.IsUsuarios;
import com.Session.IsUsuariosFacade;
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

public class LazyUsuariosModel extends LazyDataModel<IsUsuarios> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private String tipo_persona;

    private List<IsUsuarios> listaIsUsuarios;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    Estado estadoObj;

    TipoPersona tipoPersonaObj;

    public LazyUsuariosModel(Integer empresa, Integer ciudad, Integer sector, String tipo_persona, String estado) {
        this.estadoObj = new Estado();
        this.tipoPersonaObj = new TipoPersona();
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.tipo_persona = tipo_persona;
        this.estado = estado;
    }

    @Override
    public List<IsUsuarios> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsUsuariosFacade isUsuariosFacade = (IsUsuariosFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsUsuariosFacade");
            this.listaIsUsuarios = new ArrayList<>();
            Set set = filters.entrySet();
            for (Object object : set) {
                try {
                    Map.Entry me = (Map.Entry) object;
                    String valoDesc = (String) me.getKey();
                    if (valoDesc.equals("tipoPersona")) {
                        this.tipoPersonaObj = (TipoPersona) me.getValue();
                        if (this.tipoPersonaObj.getDetalle().equals("-Seleccione-")) {
                            this.tipo_persona = null;
                            continue;
                        }
                        if (this.tipoPersonaObj.getValor() != null) {
                            try {
                                this.tipo_persona = this.tipoPersonaObj.getValor();
                            } catch (Exception e) {
                                this.tipo_persona = null;
                            }
                        }
                        continue;
                    }
                    if (valoDesc.equals("globalFilter")) {
                        filterValue = (String) me.getValue();
                        continue;
                    }
                    if (valoDesc.equals("estado")) {
                        this.estadoObj = (Estado) me.getValue();
                        if (this.estadoObj.getValor() != null) {
                            this.estado = this.estadoObj.getValor();
                        }
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            setRowCount(isUsuariosFacade.countIsUsuarios(this.empresa, this.ciudad, this.sector, this.tipo_persona, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaIsUsuarios = isUsuariosFacade.listaIsUsuarios(this.empresa, this.ciudad, this.sector, this.tipo_persona, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsUsuarios;
    }

    @Override
    public Object getRowKey(IsUsuarios transControlInventario) {
        return transControlInventario.getIdUsuarios();
    }

    @Override
    public IsUsuarios getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsUsuariosFacade isUsuariosFacade = (IsUsuariosFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsUsuariosFacade");
            int idValor = Integer.parseInt(id);
            IsUsuarios usuarios = isUsuariosFacade.findById(idValor);
            return usuarios;
        } catch (NamingException ex) {
            Logger.getLogger(LazyUsuariosModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }

    public List<IsUsuarios> getListaIsUsuarios() {
        return this.listaIsUsuarios;
    }

    public void setListaIsUsuarios(List<IsUsuarios> listaIsUsuarios) {
        this.listaIsUsuarios = listaIsUsuarios;
    }
}
