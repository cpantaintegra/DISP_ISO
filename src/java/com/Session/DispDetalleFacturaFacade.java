package com.Session;

import com.Entity.DispDetalleFactura;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DispDetalleFacturaFacade extends AbstractFacade<DispDetalleFactura> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispDetalleFacturaFacade() {
        super(DispDetalleFactura.class);
    }
}
