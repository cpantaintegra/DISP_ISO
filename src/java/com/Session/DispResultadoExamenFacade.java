package com.Session;

import com.Entity.DispResultadoExamen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DispResultadoExamenFacade extends AbstractFacade<DispResultadoExamen> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispResultadoExamenFacade() {
        super(DispResultadoExamen.class);
    }
}
