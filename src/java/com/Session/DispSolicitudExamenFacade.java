package com.Session;

import com.Entity.DispSolicitudExamen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
