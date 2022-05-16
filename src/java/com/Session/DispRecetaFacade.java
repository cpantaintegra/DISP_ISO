package com.Session;

import com.Entity.DispReceta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispRecetaFacade extends AbstractFacade<DispReceta> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispRecetaFacade() {
        super(DispReceta.class);
    }

    public int countDispReceta(Integer cliente, Integer medico, Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_receta r, disp_agendamiento a, disp_medico_personal m, disp_cliente c WHERE \n"
                + "r.id_empresa = ?1 AND  "
                + "r.id_ciudad = ?2 AND "
                + "r.id_sector = ?3 AND "
                + "r.ESTADO = ?4 AND "
                + "r.id_agendamiento = a.id_agendamiento AND "
                + "a.id_medico_personal = m.id_medico_personal AND "
                + "a.id_cliente = c.id_cliente AND ";
                if(cliente!=null){
                    StringQuery = StringQuery + "c.id_cliente = ?5 AND ";
                }
                
                if(medico!=null){
                    StringQuery = StringQuery + "m.id_medico_personal = ?6 AND ";
                }
                StringQuery = StringQuery + "(UPPER(r.observaciones) LIKE ?7 OR "
                + "UPPER(r.observaciones) IS NULL) ORDER BY r.fecha_ingreso DESC";
            Query query = this.em.createNativeQuery(StringQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            query.setParameter(4, estado);
            if(cliente!=null){
                query.setParameter(5, cliente);
            }

            if(medico!=null){
                query.setParameter(6, medico);
            }
            query.setParameter(7, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<DispReceta> listaDispReceta(Integer cliente, Integer medico, Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT r.* FROM disp_receta r, disp_agendamiento a, disp_medico_personal m, disp_cliente c WHERE \n"
                + "r.id_empresa = ?1 AND  "
                + "r.id_ciudad = ?2 AND "
                + "r.id_sector = ?3 AND "
                + "r.ESTADO = ?4 AND "
                + "r.id_agendamiento = a.id_agendamiento AND "
                + "a.id_medico_personal = m.id_medico_personal AND "
                + "a.id_cliente = c.id_cliente AND ";
                if(cliente!=null){
                    StringQuery = StringQuery + "c.id_cliente = ?5 AND ";
                }
                
                if(medico!=null){
                    StringQuery = StringQuery + "m.id_medico_personal = ?6 AND ";
                }
                StringQuery = StringQuery + "(UPPER(r.observaciones) LIKE ?7 OR "
                + "UPPER(r.observaciones) IS NULL) ORDER BY r.fecha_ingreso DESC";
        Query query = this.em.createNativeQuery(StringQuery, DispReceta.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        if(cliente!=null){
            query.setParameter(5, cliente);
        }
        
        if(medico!=null){
            query.setParameter(6, medico);
        }
        query.setParameter(7, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispReceta findById(int id) {
        Query q = this.em.createNamedQuery("DispReceta.findByIdReceta");
        q.setParameter("idReceta", id);
        try {
            DispReceta receta = (DispReceta) q.getSingleResult();
            return receta;
        } catch (Exception e) {
            return null;
        }
    }

    public DispReceta findByIdAgendamiento(int id) {
        Query q = this.em.createNamedQuery("DispReceta.findByIdAgendamiento");
        q.setParameter("idAgendamiento", id);
        try {
            DispReceta receta = (DispReceta) q.getSingleResult();
            return receta;
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispReceta> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispReceta.findAllActivos");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }
}
