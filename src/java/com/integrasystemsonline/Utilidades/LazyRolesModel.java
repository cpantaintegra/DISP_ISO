package com.integrasystemsonline.Utilidades;

import com.Entity.IsRoles;
import com.Session.IsRolesFacade;
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

public class LazyRolesModel extends LazyDataModel<IsRoles> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private String estado;

    private List<IsRoles> listaIsRoles;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    public LazyRolesModel(Integer empresa, Integer ciudad, Integer sector, String estado) {
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.estado = estado;
    }

    @Override
    public List<IsRoles> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsRolesFacade isRolesFacade = (IsRolesFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsRolesFacade");
            this.listaIsRoles = new ArrayList<>();
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
            setRowCount(isRolesFacade.countIsRoles(this.empresa, this.ciudad, this.sector, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaIsRoles = isRolesFacade.listaIsRoles(this.empresa, this.ciudad, this.sector, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsRoles;
    }

    @Override
    public Object getRowKey(IsRoles transControlInventario) {
        return transControlInventario.getIdRoles();
    }

    @Override
    public IsRoles getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsRolesFacade isRolesFacade = (IsRolesFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsRolesFacade");
            int idValor = Integer.parseInt(id);
            IsRoles roles = isRolesFacade.findById(idValor);
            return roles;
        } catch (NamingException ex) {
            Logger.getLogger(LazyRolesModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }
}
