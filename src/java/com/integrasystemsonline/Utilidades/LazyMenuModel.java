package com.integrasystemsonline.Utilidades;

import com.Entity.IsMenu;
import com.Session.IsMenuFacade;
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

public class LazyMenuModel extends LazyDataModel<IsMenu> {

    private Integer empresa;

    private Integer ciudad;

    private Integer sector;

    private Integer idMenuPadre;

    private String estado;

    private List<IsMenu> listaIsMenu;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    Estado estadoObj;

    public LazyMenuModel(Integer empresa, Integer ciudad, Integer sector, Integer idMenuPadre, String estado) {
        this.estadoObj = new Estado();
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.sector = sector;
        this.idMenuPadre = idMenuPadre;
        this.estado = estado;
    }

    @Override
    public List<IsMenu> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String filterValue = "";
        try {
            Context ctx = new InitialContext();
            IsMenuFacade isMenuFacade = (IsMenuFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsMenuFacade");
            this.listaIsMenu = new ArrayList<>();
            Set set = filters.entrySet();
            for (Object object : set) {
                try {
                    Map.Entry me = (Map.Entry) object;
                    String valoDesc = (String) me.getKey();
                    if (valoDesc.equals("idMenuPadre")) {
                        this.estadoObj = (Estado) me.getValue();
                        if (this.estadoObj.getDetalle() == "-SELECCIONE-") {
                            this.idMenuPadre = null;
                            continue;
                        }
                        if (this.estadoObj.getValor() != null) {
                            try {
                                this.idMenuPadre = Integer.valueOf(this.estadoObj.getValor());
                            } catch (Exception e) {
                                this.idMenuPadre = null;
                            }
                        }
                        continue;
                    }
                    if (valoDesc.equals("estado")) {
                        this.estadoObj = (Estado) me.getValue();
                        if (this.estadoObj.getValor() != null) {
                            this.estado = this.estadoObj.getValor();
                        }
                        continue;
                    }
                    if (valoDesc.equals("globalFilter")) {
                        filterValue = (String) me.getValue();
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            setRowCount(isMenuFacade.countIsMenu(this.empresa, this.ciudad, this.sector, this.idMenuPadre, this.estado, filterValue));
            if (getRowCount() > 0) {
                this.listaIsMenu = isMenuFacade.listaIsMenu(this.empresa, this.ciudad, this.sector, this.idMenuPadre, this.estado, startingAt, maxPerPage, filterValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPageSize(maxPerPage);
        return this.listaIsMenu;
    }

    @Override
    public Object getRowKey(IsMenu transControlInventario) {
        return transControlInventario.getIdMenu();
    }

    @Override
    public IsMenu getRowData(String id) {
        try {
            Context ctx = new InitialContext();
            IsMenuFacade isMenuFacade = (IsMenuFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsMenuFacade");
            int idValor = Integer.parseInt(id);
            IsMenu menu = isMenuFacade.findById(idValor);
            return menu;
        } catch (NamingException ex) {
            Logger.getLogger(LazyMenuModel.class.getName()).log(Level.SEVERE, (String) null, ex);
            return null;
        }
    }
}
