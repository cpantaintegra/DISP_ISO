package com.Session;

import com.Entity.DispFactura;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DispFacturaFacade extends AbstractFacade<DispFactura> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispFacturaFacade() {
        super(DispFactura.class);
    }
}
