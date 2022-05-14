package com.Session;

import com.Entity.DispDetalleReceta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispDetalleRecetaFacade extends AbstractFacade<DispDetalleReceta> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispDetalleRecetaFacade() {
        super(DispDetalleReceta.class);
    }

    public int countDispDetalleReceta(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_detalle_receta WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4";
            Query query = this.em.createNativeQuery(StringQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            query.setParameter(4, estado);
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<DispDetalleReceta> listaDispDetalleReceta(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_detalle_receta WHERE "
                + "id_empresa = ?1 AND "
                + "id_ciudad = ?2 AND "
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4";
        Query query = this.em.createNativeQuery(StringQuery, DispDetalleReceta.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispDetalleReceta findById(int id) {
        Query q = this.em.createNamedQuery("DispDetalleReceta.findByIdDetalleReceta");
        q.setParameter("idDetalleReceta", id);
        DispDetalleReceta detallereceta = (DispDetalleReceta) q.getSingleResult();
        return detallereceta;
    }

    public List<DispDetalleReceta> findByIdReceta(int id) {
        Query q = this.em.createNamedQuery("DispDetalleReceta.findByIdReceta");
        q.setParameter("idReceta", id);
        return q.getResultList();
    }
}
