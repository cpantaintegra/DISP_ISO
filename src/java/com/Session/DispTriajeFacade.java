package com.Session;

import com.Entity.DispTriaje;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispTriajeFacade extends AbstractFacade<DispTriaje> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispTriajeFacade() {
        super(DispTriaje.class);
    }

    public int countDispTriaje(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_triaje WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4 AND\n"
                    + "(upper(temperatura) LIKE ?5 OR\n"
                    + "upper(imc) LIKE ?5)";
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

    public List<DispTriaje> listaDispTriaje(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_triaje WHERE \n"
                + "id_empresa = ?1 AND\n"
                + "id_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4 AND\n"
                + "(upper(temperatura) LIKE ?5 OR\n"
                + "upper(imc) LIKE ?5)";
        Query query = this.em.createNativeQuery(StringQuery, DispTriaje.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispTriaje findById(int id) {
        Query q = this.em.createNamedQuery("DispTriaje.findByIdTriaje");
        q.setParameter("idTriaje", id);
        DispTriaje triaje = (DispTriaje) q.getSingleResult();
        return triaje;
    }

    public List<DispTriaje> findByIdAgendamiento(int idAgendamiento) {
        Query q = this.em.createNamedQuery("DispTriaje.findByIdAgendamiento");
        q.setParameter("idAgendamiento", idAgendamiento);
        return q.getResultList();
    }
}
