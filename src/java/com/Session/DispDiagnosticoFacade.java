package com.Session;

import com.Entity.DispDiagnostico;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispDiagnosticoFacade extends AbstractFacade<DispDiagnostico> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispDiagnosticoFacade() {
        super(DispDiagnostico.class);
    }

    public int countDispDiagnostico(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_diagnostico WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4 AND\n"
                    + "(upper(codigo) LIKE ?5 OR\n"
                    + "upper(enfermedad) LIKE ?5)\n";
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

    public List<DispDiagnostico> listaDispDiagnostico(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_diagnostico WHERE \n"
                + "id_empresa = ?1 AND\nid_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4 AND\n"
                + "(upper(codigo) LIKE ?5 OR\n"
                + "upper(enfermedad) LIKE ?5)\n";
        Query query = this.em.createNativeQuery(StringQuery, DispDiagnostico.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispDiagnostico findById(int id) {
        Query q = this.em.createNamedQuery("DispDiagnostico.findByIdDiagnostico");
        q.setParameter("idDiagnostico", id);
        DispDiagnostico diagnostico = (DispDiagnostico) q.getSingleResult();
        return diagnostico;
    }
}
