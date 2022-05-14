package com.Session;

import com.Entity.DispOrigen;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispOrigenFacade extends AbstractFacade<DispOrigen> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispOrigenFacade() {
        super(DispOrigen.class);
    }

    public int countDispOrigen(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_origen WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4 AND\n"
                    + "(upper(nombre) LIKE ?5 OR\n"
                    + "upper(detalle) LIKE ?5)\n";
            Query query = this.em.createNativeQuery(StringQuery);
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

    public List<DispOrigen> listaDispOrigen(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_origen WHERE \n"
                + "id_empresa = ?1 AND\n"
                + "id_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4 AND\n"
                + "(upper(nombre) LIKE ?5 OR\n"
                + "upper(detalle) LIKE ?5)\n";
        Query query = this.em.createNativeQuery(StringQuery, DispOrigen.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispOrigen findById(int id) {
        Query q = this.em.createNamedQuery("DispOrigen.findByIdOrigen");
        q.setParameter("idOrigen", id);
        DispOrigen origen = (DispOrigen) q.getSingleResult();
        return origen;
    }

    public List<DispOrigen> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispOrigen.findAllActivos");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }
}
