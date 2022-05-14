package com.Session;

import com.Entity.DispPrecio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispPrecioFacade extends AbstractFacade<DispPrecio> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispPrecioFacade() {
        super(DispPrecio.class);
    }

    public List<DispPrecio> findByServicio(int idServicio) {
        Query q = this.em.createNamedQuery("DispPrecio.findByIdServicio");
        q.setParameter("idServicio", idServicio);
        return q.getResultList();
    }

    public DispPrecio findByServicioOrigen(int idServicio, int idOrigen) {
        Query q = this.em.createNamedQuery("DispPrecio.findByServicioOrigen");
        q.setParameter("idServicio", idServicio);
        q.setParameter("idOrigen", idOrigen);
        try {
            DispPrecio precio = (DispPrecio) q.getSingleResult();
            return precio;
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispPrecio> findByOrigen(int idOrigen) {
        Query q = this.em.createNamedQuery("DispPrecio.findByIdOrigen");
        q.setParameter("idOrigen", idOrigen);
        return q.getResultList();
    }
}
