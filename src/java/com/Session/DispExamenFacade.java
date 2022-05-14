package com.Session;

import com.Entity.DispExamen;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispExamenFacade extends AbstractFacade<DispExamen> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispExamenFacade() {
        super(DispExamen.class);
    }
    
    public List<DispExamen> findByIdEstudiosMedicos(int idEstudiosMedicos) {
        Query q = this.em.createNamedQuery("DispExamen.findByIdEstudiosMedicos");
        q.setParameter("idEstudiosMedicos", idEstudiosMedicos);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
