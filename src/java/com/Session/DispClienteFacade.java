package com.Session;

import com.Entity.DispCliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispClienteFacade extends AbstractFacade<DispCliente> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispClienteFacade() {
        super(DispCliente.class);
    }

    public int countDispCliente(Integer empresa, Integer ciudad, Integer sector, String tipo_documento, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_cliente WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n";
            if (tipo_documento != null) {
                StringQuery = StringQuery + "tipo_documento = ?4 AND ";
            }
            StringQuery = StringQuery + "ESTADO = ?5 AND\n"
                    + "(num_documento LIKE ?6 OR\n"
                    + "upper(nombre) LIKE ?6 OR\n"
                    + "upper(apaterno) LIKE ?6)\n"
                    + "AND num_documento!='0000000000'";
            Query query = this.em.createNativeQuery(StringQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            if (tipo_documento != null) {
                query.setParameter(4, tipo_documento);
            }
            query.setParameter(5, estado);
            query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<DispCliente> listaDispCliente(Integer empresa, Integer ciudad, Integer sector, String tipo_documento, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_cliente WHERE "
                + "id_empresa = ?1 AND "
                + "id_ciudad = ?2 AND "
                + "id_sector = ?3 AND ";
        if (tipo_documento != null) {
            StringQuery = StringQuery + "tipo_documento = ?4 AND ";
        }
        StringQuery = StringQuery + "ESTADO = ?5 AND "
                + "(num_documento LIKE ?6 OR "
                + "upper(nombre) LIKE ?6 OR "
                + "upper(apaterno) LIKE ?6) AND "
                + "num_documento!='0000000000'";
        Query query = this.em.createNativeQuery(StringQuery, DispCliente.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        if (tipo_documento != null) {
            query.setParameter(4, tipo_documento);
        }
        query.setParameter(5, estado);
        query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public int countAllCliente(Integer empresa, Integer ciudad, Integer sector) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_cliente WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = 'A' AND num_documento!='0000000000'";
            Query query = this.em.createNativeQuery(StringQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<DispCliente> listaAllCliente(Integer empresa, Integer ciudad, Integer sector) throws Exception {
        String StringQuery = "SELECT * FROM disp_cliente WHERE "
                + "id_empresa = ?1 AND "
                + "id_ciudad = ?2 AND "
                + "id_sector = ?3 AND "
                + "ESTADO = 'A' AND "
                + "num_documento!='0000000000'";
        Query query = this.em.createNativeQuery(StringQuery, DispCliente.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        return query.getResultList();
    }

    public DispCliente findById(int id) {
        Query q = this.em.createNamedQuery("DispCliente.findByIdCliente");
        q.setParameter("idCliente", id);
        DispCliente Cliente = (DispCliente) q.getSingleResult();
        return Cliente;
    }

    public DispCliente findByNames(String nombres, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispCliente.findByNames");
        q.setParameter("nombres", nombres);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            DispCliente Cliente = (DispCliente) q.getSingleResult();
            return Cliente;
        } catch (Exception e) {
            return null;
        }
    }

    public DispCliente findByNumDocumento(String numDocumento, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispCliente.findByNumDocumentoByIDs");
        q.setParameter("numDocumento", numDocumento);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            DispCliente Cliente = (DispCliente) q.getSingleResult();
            return Cliente;
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispCliente> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispCliente.findAllActivos");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispCliente> findAllHistorialActivo(Integer empresa, Integer ciudad, Integer sector) {
        String StringQuery = "SELECT DISTINCT c.* FROM disp_cliente c, disp_resultado r, disp_agendamiento a\nWHERE  c.id_empresa = ?1 AND c.id_ciudad = ?2 AND c.id_sector = ?3 AND c.estado = 'A'\nAND c.id_cliente = a.id_cliente\nAND r.id_agendamiento = a.id_agendamiento";
        Query q = this.em.createNativeQuery(StringQuery, DispCliente.class);
        q.setParameter(1, empresa);
        q.setParameter(2, ciudad);
        q.setParameter(3, sector);
        return q.getResultList();
    }
}
