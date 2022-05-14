package com.Session;

import com.Entity.IsRoles;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IsRolesFacade extends AbstractFacade<IsRoles> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsRolesFacade() {
        super(IsRoles.class);
    }

    public List<IsRoles> findAllActivos() {
        Query q = this.em.createNamedQuery("IsRoles.findAllActivos");
        return q.getResultList();
    }

    public List<IsRoles> findRolesPrincipales() {
        Query q = this.em.createNamedQuery("IsRoles.findRolesPrincipales");
        return q.getResultList();
    }

    public int countIsRoles(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) {
        int resp = 0;
        try {
            String strQuery = "SELECT count(*) FROM is_roles WHERE "
                    + "id_empresa=?1 AND "
                    + "id_ciudad=?2 AND "
                    + "id_sector=?3 AND "
                    + "estado=?4 AND "
                    + "(upper(nombre) LIKE ?5 OR upper(detalle) LIKE ?5) ORDER BY id_roles";
            Query query = this.em.createNativeQuery(strQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            query.setParameter(4, estado);
            query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<IsRoles> listaIsRoles(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String strQuery = "SELECT * FROM is_roles WHERE "
                + "id_empresa=?1 AND "
                + "id_ciudad=?2 AND "
                + "id_sector=?3 AND "
                + "estado=?4 AND "
                + "(upper(nombre) LIKE ?5 OR upper(detalle) LIKE ?5) ORDER BY id_roles";
        Query query = this.em.createNativeQuery(strQuery, IsRoles.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public IsRoles findById(int id) {
        Query q = this.em.createNamedQuery("IsRoles.findByIdRoles");
        q.setParameter("idRoles", id);
        IsRoles roles = (IsRoles) q.getSingleResult();
        return roles;
    }

    public List<IsRoles> listaIsRolesActivos(Integer empresa, Integer ciudad, Integer sector, String estado) throws Exception {
        String strQuery = "SELECT * FROM is_roles WHERE "
                + "id_empresa=?1 AND "
                + "id_ciudad=?2 AND "
                + "id_sector=?3 AND "
                + "estado='A' ORDER BY id_roles";
        Query query = this.em.createNativeQuery(strQuery, IsRoles.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        return query.getResultList();
    }
}
