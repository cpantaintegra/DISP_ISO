package com.Session;

import com.Entity.DispEspecialidad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispEspecialidadFacade extends AbstractFacade<DispEspecialidad> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispEspecialidadFacade() {
        super(DispEspecialidad.class);
    }

    public int countDispEspecialidad(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_especialidad WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4 AND\n(upper(nombre) LIKE ?5 OR\n"
                    + "upper(codigo) LIKE ?5 OR\n"
                    + "upper(descripcion) LIKE ?5)\n";
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

    public List<DispEspecialidad> listaDispEspecialidad(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_especialidad WHERE \n"
                + "id_empresa = ?1 AND\n"
                + "id_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4 AND\n"
                + "(upper(nombre) LIKE ?5 OR\n"
                + "upper(codigo) LIKE ?5 OR\n"
                + "upper(descripcion) LIKE ?5)\n";
        Query query = this.em.createNativeQuery(StringQuery, DispEspecialidad.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispEspecialidad findById(int id) {
        Query q = this.em.createNamedQuery("DispEspecialidad.findByIdEspecialidad");
        q.setParameter("idEspecialidad", id);
        DispEspecialidad especialidad = (DispEspecialidad) q.getSingleResult();
        return especialidad;
    }

    public DispEspecialidad findByNombre(String nombre, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispEspecialidad.findByNombreByIDs");
        q.setParameter("nombre", nombre);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            DispEspecialidad especialidad = (DispEspecialidad) q.getSingleResult();
            return especialidad;
        } catch (Exception e) {
            return null;
        }
    }

    public DispEspecialidad findByCodigo(String codigo, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispEspecialidad.findByCodigoByIDs");
        q.setParameter("codigo", codigo);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            DispEspecialidad especialidad = (DispEspecialidad) q.getSingleResult();
            return especialidad;
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispEspecialidad> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispEspecialidad.findAllActivos");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }
}
