package com.Session;

import com.Entity.IsEmpresa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Stateless
public class IsEmpresaFacade extends AbstractFacade<IsEmpresa> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsEmpresaFacade() {
        super(IsEmpresa.class);
    }

    public int countIsEmpresa(String estado, String sri, String filtro) throws Exception {
        int resp = 0;
        try {
            String strQuery = "SELECT count(*) FROM is_empresa WHERE ESTADO = ?1 ";
            if (sri != null) {
                strQuery = strQuery + "AND fac_sri = ?2";
            }
            strQuery = strQuery + " AND (upper(NOMB_COMERCIAL) LIKE ?3 OR "
                    + "upper(NOMB_JURIDICO) LIKE ?3 OR "
                    + "upper(DETALLE) LIKE ?3)";
            Query query = this.em.createNativeQuery(strQuery);
            query.setParameter(1, estado);
            if (sri != null) {
                query.setParameter(2, sri);
            }
            query.setParameter(3, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<IsEmpresa> listaIsEmpresa(String estado, String sri, int startingAt, int maxPerPage, String filtro) throws Exception {
        String strQuery = "SELECT * FROM is_empresa WHERE ESTADO = ?1 ";
        if (sri != null) {
            strQuery = strQuery + "AND fac_sri = ?2";
        }
        strQuery = strQuery + " AND (upper(NOMB_COMERCIAL) LIKE ?3 OR "
                + "upper(NOMB_JURIDICO) LIKE ?3 OR "
                + "upper(DETALLE) LIKE ?3)";
        Query query = this.em.createNativeQuery(strQuery, IsEmpresa.class);
        query.setParameter(1, estado);
        if (sri != null) {
            query.setParameter(2, sri);
        }
        query.setParameter(3, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public IsEmpresa findById(int id) {
        Query q = this.em.createNamedQuery("IsEmpresa.findByIdEmpresa");
        q.setParameter("idEmpresa", id);
        IsEmpresa empresa = (IsEmpresa) q.getSingleResult();
        return empresa;
    }

    @Transactional
    public List<IsEmpresa> getEmpresaList() {
        return this.em.createQuery("select a from is_empresa a order by a.id_empresa").getResultList();
    }
}
