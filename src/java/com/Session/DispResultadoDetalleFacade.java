package com.Session;

import com.Entity.DispResultadoDetalle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DispResultadoDetalleFacade extends AbstractFacade<DispResultadoDetalle> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispResultadoDetalleFacade() {
        super(DispResultadoDetalle.class);
    }
}
