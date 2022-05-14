package com.Session;

import com.Entity.IsPermisos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Stateless
public class IsPermisosFacade extends AbstractFacade<IsPermisos> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsPermisosFacade() {
        super(IsPermisos.class);
    }

    public int countIsPermisos(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            Query query = this.em.createNativeQuery("SELECT count(*) FROM is_permisos WHERE "
                    + "id_empresa = ?1 AND "
                    + "id_ciudad=?2 AND "
                    + "id_sector=?3 AND "
                    + "ESTADO = ?4  AND "
                    + "(upper(NOMBRE) LIKE ?5 OR upper(DETALLE) LIKE ?5)");
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

    public List<IsPermisos> listaIsPermisos(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        Query query = this.em.createNativeQuery("SELECT * FROM is_permisos WHERE "
                + "id_empresa = ?1 AND "
                + "id_ciudad = ?2 AND "
                + "id_sector = ?3 AND "
                + "ESTADO = ?4  AND "
                + "(upper(NOMBRE) LIKE ?5 OR upper(DETALLE) LIKE ?5)", IsPermisos.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public IsPermisos findById(int id) {
        Query q = this.em.createNamedQuery("IsPermisos.findByIdPermisos");
        q.setParameter("idPermisos", id);
        IsPermisos permisos = (IsPermisos) q.getSingleResult();
        return permisos;
    }

    @Transactional
    public List<IsPermisos> getPermisosList() {
        return this.em.createQuery("select a from is_permisos a order by a.id_permisos").getResultList();
    }

    public List<IsPermisos> findAllActivos() {
        Query q = this.em.createNamedQuery("IsPermisos.findAllActivos");
        return q.getResultList();
    }
}
