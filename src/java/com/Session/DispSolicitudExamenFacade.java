package com.Session;

import com.Entity.DispSolicitudExamen;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispSolicitudExamenFacade extends AbstractFacade<DispSolicitudExamen> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispSolicitudExamenFacade() {
        super(DispSolicitudExamen.class);
    }
    
    public List<DispSolicitudExamen> findByIdAgendamiento(int id) {
        Query q = this.em.createNamedQuery("DispSolicitudExamen.findByIdAgendamiento");
        q.setParameter("idAgendamiento", id);
        return q.getResultList();
    }
}
