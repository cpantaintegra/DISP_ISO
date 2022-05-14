package com.Session;

import com.Entity.DispMedicamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispMedicamentoFacade extends AbstractFacade<DispMedicamento> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispMedicamentoFacade() {
        super(DispMedicamento.class);
    }

    public int countDispMedicamento(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_medicamento WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4 AND\n"
                    + "(upper(nombre) LIKE ?5 OR\n"
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

    public List<DispMedicamento> listaDispMedicamento(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_medicamento WHERE \n"
                + "id_empresa = ?1 AND\n"
                + "id_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4 AND\n"
                + "(upper(nombre) LIKE ?5 OR\n"
                + "upper(descripcion) LIKE ?5)\n";
        Query query = this.em.createNativeQuery(StringQuery, DispMedicamento.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispMedicamento findById(int id) {
        Query q = this.em.createNamedQuery("DispMedicamento.findByIdMedicamento");
        q.setParameter("idMedicamento", id);
        try {
            DispMedicamento medicamento = (DispMedicamento) q.getSingleResult();
            return medicamento;
        } catch (Exception e) {
            return null;
        }
    }

    public DispMedicamento findByNombre(String nombre, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispMedicamento.findByNombre");
        q.setParameter("nombre", nombre);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            DispMedicamento medicamento = (DispMedicamento) q.getSingleResult();
            return medicamento;
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispMedicamento> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispMedicamento.findAllActivos");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }
}
