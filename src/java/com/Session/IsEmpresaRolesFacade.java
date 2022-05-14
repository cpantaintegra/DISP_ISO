package com.Session;

import com.Entity.IsEmpresaRoles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class IsEmpresaRolesFacade extends AbstractFacade<IsEmpresaRoles> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsEmpresaRolesFacade() {
        super(IsEmpresaRoles.class);
    }
}
