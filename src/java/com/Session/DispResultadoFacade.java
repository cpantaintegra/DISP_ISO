package com.Session;

import com.Entity.DispResultado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispResultadoFacade extends AbstractFacade<DispResultado> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispResultadoFacade() {
        super(DispResultado.class);
    }

    public DispResultado findByIdAgendamiento(int id) {
        Query q = this.em.createNamedQuery("DispResultado.findByIdAgendamiento");
        q.setParameter("idAgendamiento", id);
        try {
            DispResultado resultado = (DispResultado) q.getSingleResult();
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }
}
