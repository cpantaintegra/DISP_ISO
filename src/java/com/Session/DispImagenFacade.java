package com.Session;

import com.Entity.DispImagen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DispImagenFacade extends AbstractFacade<DispImagen> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispImagenFacade() {
        super(DispImagen.class);
    }
}
