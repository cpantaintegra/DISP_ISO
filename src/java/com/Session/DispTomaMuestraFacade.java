package com.Session;

import com.Entity.DispTomaMuestra;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DispTomaMuestraFacade extends AbstractFacade<DispTomaMuestra> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispTomaMuestraFacade() {
        super(DispTomaMuestra.class);
    }
}
