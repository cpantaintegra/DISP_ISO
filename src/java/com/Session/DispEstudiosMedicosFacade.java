/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Session;

import com.Entity.DispEstudiosMedicos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
@Stateless
public class DispEstudiosMedicosFacade extends AbstractFacade<DispEstudiosMedicos> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispEstudiosMedicosFacade() {
        super(DispEstudiosMedicos.class);
    }
    
    public DispEstudiosMedicos findByNombre(String nombre, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispEstudiosMedicos.findByNombreByIDs");
        q.setParameter("nombre", nombre);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            DispEstudiosMedicos estudio = (DispEstudiosMedicos) q.getSingleResult();
            return estudio;
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<DispEstudiosMedicos> findByIdEspecialidad(int idEspecialidad) {
        Query q = this.em.createNamedQuery("DispEstudiosMedicos.findByIdEspecialidad");
        q.setParameter("idEspecialidad", idEspecialidad);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public int countDispEstudiosMedicos(Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_estudios_medicos WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n"
                    + "ESTADO = ?4 AND\n(upper(nombre) LIKE ?5 OR\n"
                    + "upper(detalle) LIKE ?5)\n";
            Query query = this.em.createNativeQuery(StringQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            query.setParameter(4, estado);
            query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<DispEstudiosMedicos> listaDispEstudiosMedicos(Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_estudios_medicos WHERE \n"
                + "id_empresa = ?1 AND\n"
                + "id_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND\n"
                + "ESTADO = ?4 AND\n"
                + "(upper(nombre) LIKE ?5 OR\n"
                + "upper(detalle) LIKE ?5)\n";
        Query query = this.em.createNativeQuery(StringQuery, DispEstudiosMedicos.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, estado);
        query.setParameter(5, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispEstudiosMedicos findById(int id) {
        Query q = this.em.createNamedQuery("DispEstudiosMedicos.findByIdEstudiosMedicos");
        q.setParameter("idEstudiosMedicos", id);
        DispEstudiosMedicos estudios = (DispEstudiosMedicos) q.getSingleResult();
        return estudios;
    }
}
