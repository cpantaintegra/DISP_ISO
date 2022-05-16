package com.Session;

import com.Entity.DispExamen;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispExamenFacade extends AbstractFacade<DispExamen> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispExamenFacade() {
        super(DispExamen.class);
    }
    
    public List<DispExamen> findByIdEstudiosMedicos(int idEstudiosMedicos) {
        Query q = this.em.createNamedQuery("DispExamen.findByIdEstudiosMedicos");
        q.setParameter("idEstudiosMedicos", idEstudiosMedicos);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public DispExamen findByNombre(String nombre, Integer estudiosMedicos, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispExamen.findByNombreByIDs");
        q.setParameter("nombre", nombre);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        q.setParameter("idEstudiosMedicos", estudiosMedicos);
        try {
            DispExamen servicio = (DispExamen) q.getSingleResult();
            return servicio;
        } catch (Exception e) {
            return null;
        }
    }
    
    public int countDispExamen(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_examen WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4 AND\n"
                    + "(upper(nombre) LIKE ?5 or "
                    + "upper(descripcion) LIKE ?5 )";
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

    public List<DispExamen> listaDispExamen(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_examen WHERE \n"
                + "id_empresa = ?1 AND\n"
                + "id_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4 AND\n"
                + "(upper(nombre) LIKE ?5 or "
                + "upper(descripcion) LIKE ?5 )";
        Query query = this.em.createNativeQuery(StringQuery, DispExamen.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispExamen findById(int id) {
        Query q = this.em.createNamedQuery("DispExamen.findByIdExamen");
        q.setParameter("idExamen", id);
        DispExamen examen = (DispExamen) q.getSingleResult();
        return examen;
    }
}
