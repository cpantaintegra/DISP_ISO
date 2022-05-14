package com.Session;

import com.Entity.IsMenu;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IsMenuFacade extends AbstractFacade<IsMenu> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsMenuFacade() {
        super(IsMenu.class);
    }

    public int countIsMenu(Integer empresa, Integer ciudad, Integer sector, Integer idMenuPadre, String estado, String filtro) {
        int resp = 0;
        try {
            String strQuery = "SELECT count(*) FROM is_menu WHERE "
                    + "id_empresa=?1 AND "
                    + "id_ciudad=?2 AND "
                    + "id_sector=?3 AND ";
            if (idMenuPadre != null) {
                if (idMenuPadre == 0) {
                    strQuery = strQuery + "id_menu_padre is null AND ";
                } else {
                    strQuery = strQuery + "id_menu_padre = ?4 AND ";
                }
            }
            strQuery = strQuery + "estado=?5 AND (upper(nombre) LIKE ?6 OR "
                    + "upper(detalle) LIKE ?6 OR "
                    + "upper(url) LIKE ?6) ORDER BY id_menu_padre,orden";
            Query query = this.em.createNativeQuery(strQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            if (idMenuPadre != null) {
                query.setParameter(4, idMenuPadre);
            }
            query.setParameter(5, estado);
            query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<IsMenu> listaIsMenu(Integer empresa, Integer ciudad, Integer sector, Integer idMenuPadre, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String strQuery = "SELECT * FROM is_menu WHERE "
                + "id_empresa=?1 AND "
                + "id_ciudad=?2 AND "
                + "id_sector=?3 AND ";
        if (idMenuPadre != null) {
            if (idMenuPadre == 0) {
                strQuery = strQuery + "id_menu_padre is null AND ";
            } else {
                strQuery = strQuery + "id_menu_padre = ?4 AND ";
            }
        }
        strQuery = strQuery + "estado=?5 AND (upper(nombre) LIKE ?6 OR "
                + "upper(detalle) LIKE ?6 OR "
                + "upper(url) LIKE ?6) ORDER BY id_menu_padre,orden";
        Query query = this.em.createNativeQuery(strQuery, IsMenu.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        if (idMenuPadre != null) {
            query.setParameter(4, idMenuPadre);
        }
        query.setParameter(5, estado);
        query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public IsMenu findById(int id) {
        Query q = this.em.createNamedQuery("IsMenu.findByIdMenu");
        q.setParameter("idMenu", id);
        IsMenu menu = (IsMenu) q.getSingleResult();
        return menu;
    }

    public List<IsMenu> listaIsMenuActivos(Integer empresa, Integer ciudad, Integer sector) throws Exception {
        String strQuery = "SELECT * FROM is_menu WHERE "
                + "id_empresa=?1 AND "
                + "id_ciudad=?2 AND "
                + "id_sector=?3 AND "
                + "id_menu_padre is null and "
                + "estado='A' ORDER BY orden";
        Query query = this.em.createNativeQuery(strQuery, IsMenu.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        return query.getResultList();
    }
}
