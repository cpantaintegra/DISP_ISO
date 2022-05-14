package com.Session;

import com.Entity.DispMuestra;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DispMuestraFacade extends AbstractFacade<DispMuestra> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispMuestraFacade() {
        super(DispMuestra.class);
    }
}
