package com.Session;

import com.Entity.IsArea;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Stateless
public class IsAreaFacade extends AbstractFacade<IsArea> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsAreaFacade() {
        super(IsArea.class);
    }

    public int countIsArea(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            Query query = this.em.createNativeQuery("SELECT count(*) FROM is_area WHERE "
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

    public List<IsArea> listaIsArea(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        Query query = this.em.createNativeQuery("SELECT * FROM is_area WHERE "
                + "id_empresa = ?1 AND "
                + "id_ciudad = ?2 AND "
                + "id_sector = ?3 AND "
                + "ESTADO = ?4  AND "
                + "(upper(NOMBRE) LIKE ?5 OR upper(DETALLE) LIKE ?5)", IsArea.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public IsArea findById(int id) {
        Query q = this.em.createNamedQuery("IsArea.findByIdArea");
        q.setParameter("idArea", id);
        IsArea area = (IsArea) q.getSingleResult();
        return area;
    }

    @Transactional
    public List<IsArea> getAreaList() {
        return this.em.createQuery("select a from is_area a order by a.id_area").getResultList();
    }

    @Transactional
    public void saveArea(IsArea area) {
        this.em.merge(area);
    }

    public List<IsArea> findAllActivos() {
        Query q = this.em.createNamedQuery("IsArea.findAllActivos");
        return q.getResultList();
    }
}
