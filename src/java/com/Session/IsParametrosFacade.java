package com.Session;

import com.Entity.IsParametros;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.Session;

@Stateless
public class IsParametrosFacade extends AbstractFacade<IsParametros> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsParametrosFacade() {
        super(IsParametros.class);
    }

    public Session getSession() throws Exception {
        try {
            return ((JpaEntityManager) this.em.unwrap(JpaEntityManager.class)).getActiveSession();
        } catch (PersistenceException e) {
            throw new Exception(e.getCause());
        }
    }

    public int countIsParametros(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            Query query = this.em.createNativeQuery("SELECT count(*) FROM is_parametros WHERE "
                    + "id_empresa = ?1 AND "
                    + "id_ciudad=?2 AND "
                    + "id_sector=?3 AND "
                    + "ESTADO = ?4  AND "
                    + "(upper(codigo) LIKE ?5 OR upper(valor) LIKE ?5)");
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

    public List<IsParametros> listaIsParametros(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        Query query = this.em.createNativeQuery("SELECT * FROM is_parametros WHERE "
                + "id_empresa = ?1 AND "
                + "id_ciudad = ?2 AND "
                + "id_sector = ?3 AND "
                + "ESTADO = ?4  AND "
                + "(upper(codigo) LIKE ?5 OR upper(valor) LIKE ?5)", IsParametros.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public IsParametros findById(int id) {
        Query q = this.em.createNamedQuery("IsParametros.findByIdParametro");
        q.setParameter("idParametro", id);
        IsParametros parametro = (IsParametros) q.getSingleResult();
        return parametro;
    }

    public IsParametros findByCodigo(String codigo, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("IsParametros.findByCodigo");
        q.setParameter("codigo", codigo);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            IsParametros parametro = (IsParametros) q.getSingleResult();
            return parametro;
        } catch (Exception e) {
            return null;
        }
    }

    public int countIsParametrosByCodigo(Integer empresa, Integer ciudad, Integer sector, String codigo, String estado) throws Exception {
        int resp = 0;
        try {
            Query query = this.em.createNativeQuery("SELECT count(*) FROM is_parametros WHERE "
                    + "id_empresa = ?1 AND "
                    + "id_ciudad=?2 AND "
                    + "id_sector=?3 AND "
                    + "codigo=?4 AND "
                    + "ESTADO = ?5 ");
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            query.setParameter(4, codigo);
            query.setParameter(5, estado);
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<IsParametros> findAllActivos() {
        Query q = this.em.createNamedQuery("IsParametros.findAllActivos");
        return q.getResultList();
    }
}
