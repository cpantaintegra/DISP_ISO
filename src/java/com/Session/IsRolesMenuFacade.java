package com.Session;

import com.Entity.IsMenu;
import com.Entity.IsRolesMenu;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IsRolesMenuFacade extends AbstractFacade<IsRolesMenu> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsRolesMenuFacade() {
        super(IsRolesMenu.class);
    }

    public List<IsRolesMenu> findByEstadoOrdenRol(Integer isRoles) {
        Query q = this.em.createNamedQuery("IsRolesMenu.findByEstadoOrden");
        q.setParameter("idRoles", isRoles);
        return q.getResultList();
    }

    public List<IsRolesMenu> findAllByMenu(IsMenu isMenu) {
        Query q = this.em.createNamedQuery("IsRolesMenu.findAllByMenu");
        q.setParameter("objMenu", isMenu);
        return q.getResultList();
    }

    public List<IsRolesMenu> findByMenu(IsMenu objMenu) {
        Query q = this.em.createNamedQuery("IsRolesMenu.findByMenu");
        q.setParameter("objMenu", objMenu);
        return q.getResultList();
    }
}
