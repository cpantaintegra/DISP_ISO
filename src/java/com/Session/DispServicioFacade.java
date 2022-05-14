package com.Session;

import com.Entity.DispServicio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispServicioFacade extends AbstractFacade<DispServicio> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispServicioFacade() {
        super(DispServicio.class);
    }

    public int countDispServicio(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_servicio WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4 AND\n"
                    + "(upper(nombre) LIKE ?5)";
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

    public List<DispServicio> listaDispServicio(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_servicio WHERE \n"
                + "id_empresa = ?1 AND\n"
                + "id_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4 AND\n"
                + "(upper(nombre) LIKE ?5)";
        Query query = this.em.createNativeQuery(StringQuery, DispServicio.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispServicio findById(int id) {
        Query q = this.em.createNamedQuery("DispServicio.findByIdServicio");
        q.setParameter("idServicio", id);
        DispServicio servicio = (DispServicio) q.getSingleResult();
        return servicio;
    }

    public DispServicio findByNombre(String nombre, Integer especialidad, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispServicio.findByNombreByIDs");
        q.setParameter("nombre", nombre);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        q.setParameter("especialidad", especialidad);
        try {
            DispServicio servicio = (DispServicio) q.getSingleResult();
            return servicio;
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispServicio> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispServicio.findAllActivosByIDs");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispServicio> findByIdEspecialidad(int idEspecialidad) {
        Query q = this.em.createNamedQuery("DispServicio.findByIdEspecialidad");
        q.setParameter("idEspecialidad", idEspecialidad);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
