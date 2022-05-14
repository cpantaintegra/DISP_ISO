package com.Session;

import com.Entity.IsRoles;
import com.Entity.IsRolesPermisos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IsRolesPermisosFacade extends AbstractFacade<IsRolesPermisos> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsRolesPermisosFacade() {
        super(IsRolesPermisos.class);
    }

    public List<IsRolesPermisos> findByRol(IsRoles objRol) {
        Query q = this.em.createNamedQuery("IsRolesPermisos.findByRol");
        q.setParameter("objRol", objRol);
        return q.getResultList();
    }

    public List<IsRolesPermisos> findAllByRol(IsRoles isRoles) {
        Query q = this.em.createNamedQuery("IsRolesPermisos.findAllByRol");
        q.setParameter("objRoles", isRoles);
        return q.getResultList();
    }
}
