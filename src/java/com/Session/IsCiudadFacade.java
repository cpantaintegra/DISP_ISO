package com.Session;

import com.Entity.IsCiudad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Stateless
public class IsCiudadFacade extends AbstractFacade<IsCiudad> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsCiudadFacade() {
        super(IsCiudad.class);
    }

    public int countIsCiudad(String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            Query query = this.em.createNativeQuery("SELECT count(*) FROM is_ciudad WHERE "
                    + "ESTADO = ?1  AND "
                    + "(upper(NOMBRE) LIKE ?2 OR upper(DETALLE) LIKE ?2)");
            query.setParameter(1, estado);
            query.setParameter(2, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<IsCiudad> listaIsCiudad(String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        Query query = this.em.createNativeQuery("SELECT * FROM is_ciudad WHERE "
                + "ESTADO = ?1  AND "
                + "(upper(NOMBRE) LIKE ?2 OR upper(DETALLE) LIKE ?2)", IsCiudad.class);
        query.setParameter(1, estado);
        query.setParameter(2, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public IsCiudad findById(int id) {
        Query q = this.em.createNamedQuery("IsCiudad.findByIdCiudad");
        q.setParameter("idCiudad", id);
        IsCiudad ciudad = (IsCiudad) q.getSingleResult();
        return ciudad;
    }

    @Transactional
    public List<IsCiudad> getCiudadList() {
        return this.em.createQuery("select a from is_ciudad a order by a.id_ciudad").getResultList();
    }

    public List<IsCiudad> findAllActivos() {
        Query q = this.em.createNamedQuery("IsCiudad.findAllActivos");
        return q.getResultList();
    }

    public List<IsCiudad> listaIsCiudadActivos(String estado) throws Exception {
        String strQuery = "SELECT * FROM is_ciudad WHERE estado='A' BY orden";
        Query query = this.em.createNativeQuery(strQuery, IsCiudad.class);
        query.setParameter(1, estado);
        return query.getResultList();
    }
}
