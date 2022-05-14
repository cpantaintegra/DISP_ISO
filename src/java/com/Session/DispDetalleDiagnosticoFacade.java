package com.Session;

import com.Entity.DispDetalleDiagnostico;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispDetalleDiagnosticoFacade extends AbstractFacade<DispDetalleDiagnostico> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispDetalleDiagnosticoFacade() {
        super(DispDetalleDiagnostico.class);
    }

    public List<DispDetalleDiagnostico> findByIdCliente(int id) {
        Query q = this.em.createNamedQuery("DispDetalleDiagnostico.findByIdCliente");
        q.setParameter("idCliente", id);
        return q.getResultList();
    }

    public int countDispDetalleDiagnostico(Integer cliente, Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_detalle_diagnostico dd, disp_medico_personal med, disp_especialidad e WHERE "
                + "dd.id_empresa = ?1 AND "
                + "dd.id_ciudad = ?2 AND "
                + "dd.id_sector = ?3 AND\n"
                + "dd.id_cliente = ?4 AND\n"
                + "dd.ESTADO = ?5 AND\n"
                + "dd.id_medico_personal = med.id_medico_personal AND "
                + "med.id_especialidad = e.id_especialidad AND "
                + "(upper(dd.tipo) LIKE ?6 OR "
                + "upper(med.apaterno) like ?6 OR "
                + "upper(med.amaterno) like ?6 OR "
                + "upper(med.nombre) like ?6 OR "
                + "upper(e.nombre) like ?6)";
            Query query = this.em.createNativeQuery(StringQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            query.setParameter(4, cliente);
            query.setParameter(5, estado);
            query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<DispDetalleDiagnostico> listaDispDetalleDiagnostico(Integer cliente, Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT dd.* FROM disp_detalle_diagnostico dd, disp_medico_personal med, disp_especialidad e WHERE "
                + "dd.id_empresa = ?1 AND "
                + "dd.id_ciudad = ?2 AND "
                + "dd.id_sector = ?3 AND\n"
                + "dd.id_cliente = ?4 AND\n"
                + "dd.ESTADO = ?5 AND\n"
                + "dd.id_medico_personal = med.id_medico_personal AND "
                + "med.id_especialidad = e.id_especialidad AND "
                + "(upper(dd.tipo) LIKE ?6 OR "
                + "upper(med.apaterno) like ?6 OR "
                + "upper(med.amaterno) like ?6 OR "
                + "upper(med.nombre) like ?6 OR "
                + "upper(e.nombre) like ?6)";
        Query query = this.em.createNativeQuery(StringQuery, DispDetalleDiagnostico.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, cliente);
        query.setParameter(5, estado);
        query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispDetalleDiagnostico findById(int id) {
        Query q = this.em.createNamedQuery("DispDetalleDiagnostico.findByIdDetalleDiagnostico");
        q.setParameter("idDetalleDiagnostico", id);
        try {
            DispDetalleDiagnostico dispDetalleDiagnostico = (DispDetalleDiagnostico) q.getSingleResult();
            return dispDetalleDiagnostico;
        } catch (Exception e) {
            return null;
        }
    }

    public DispDetalleDiagnostico findByIdDiagnostico(int id) {
        Query q = this.em.createNamedQuery("DispDetalleDiagnostico.findByIdDiagnostico");
        q.setParameter("idDiagnostico", id);
        try {
            DispDetalleDiagnostico dispDetalleDiagnostico = (DispDetalleDiagnostico) q.getSingleResult();
            return dispDetalleDiagnostico;
        } catch (Exception e) {
            return null;
        }
    }
}
