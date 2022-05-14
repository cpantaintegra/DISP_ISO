package com.Session;

import com.Entity.DispMedicoPersonal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispMedicoPersonalFacade extends AbstractFacade<DispMedicoPersonal> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispMedicoPersonalFacade() {
        super(DispMedicoPersonal.class);
    }

    public int countDispMedicoPersonal(Integer empresa, Integer ciudad, Integer sector, String tipo_documento, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String StringQuery = "SELECT COUNT(*) FROM disp_medico_personal WHERE \n"
                    + "id_empresa = ?1 AND\n"
                    + "id_ciudad = ?2 AND\n"
                    + "id_sector = ?3 AND\n";
            if (tipo_documento != null) {
                StringQuery = StringQuery + "tipo_documento = ?4 AND ";
            }
            StringQuery = StringQuery + "ESTADO = ?5 AND\n"
                    + "(upper(num_documento) LIKE ?6 OR\n"
                    + "upper(apaterno) LIKE ?6 OR\n"
                    + "upper(amaterno) LIKE ?6 OR\n"
                    + "upper(nombre) LIKE ?6 OR\n"
                    + "upper(ocupacion) LIKE ?6)";
            Query query = this.em.createNativeQuery(StringQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            if (tipo_documento != null) {
                query.setParameter(4, tipo_documento);
            }
            query.setParameter(5, estado);
            query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<DispMedicoPersonal> listaDispMedicoPersonal(Integer empresa, Integer ciudad, Integer sector, String tipo_documento, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String StringQuery = "SELECT * FROM disp_medico_personal WHERE \n"
                + "id_empresa = ?1 AND\n"
                + "id_ciudad = ?2 AND\n"
                + "id_sector = ?3 AND ";
        if (tipo_documento != null) {
            StringQuery = StringQuery + "tipo_documento = ?4 AND ";
        }
        StringQuery = StringQuery + "ESTADO = ?5 AND "
                + "(upper(num_documento) LIKE ?6 OR\n"
                + "upper(apaterno) LIKE ?6 OR\n"
                + "upper(amaterno) LIKE ?6 OR\n"
                + "upper(nombre) LIKE ?6 OR\n"
                + "upper(ocupacion) LIKE ?6)";
        Query query = this.em.createNativeQuery(StringQuery, DispMedicoPersonal.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        if (tipo_documento != null) {
            query.setParameter(4, tipo_documento);
        }
        query.setParameter(5, estado);
        query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public DispMedicoPersonal findById(int id) {
        Query q = this.em.createNamedQuery("DispMedicoPersonal.findByIdMedicoPersonal");
        q.setParameter("idMedicoPersonal", id);
        try {
            DispMedicoPersonal medico = (DispMedicoPersonal) q.getSingleResult();
            return medico;
        } catch (Exception e) {
            return null;
        }
    }

    public DispMedicoPersonal findByIdUsuario(int idUsuario) {
        Query q = this.em.createNamedQuery("DispMedicoPersonal.findByIdUsuario");
        q.setParameter("idUsuario", idUsuario);
        try {
            DispMedicoPersonal medico = (DispMedicoPersonal) q.getSingleResult();
            return medico;
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispMedicoPersonal> listaDispMedicoPersonalByEspecialidad(Integer especialidad) {
        String StringQuery = "SELECT * FROM disp_medico_personal WHERE id_especialidad = ?1";
        Query query = this.em.createNativeQuery(StringQuery, DispMedicoPersonal.class);
        query.setParameter(1, especialidad);
        return query.getResultList();
    }

    public List<DispMedicoPersonal> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispMedicoPersonal.findAllActivos");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public DispMedicoPersonal findByNames(String aPaterno, String aMaterno, String nombre, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispMedicoPersonal.findByNames");
        q.setParameter("apaterno", aPaterno);
        q.setParameter("amaterno", aMaterno);
        q.setParameter("nombre", nombre);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            DispMedicoPersonal medico = (DispMedicoPersonal) q.getSingleResult();
            return medico;
        } catch (Exception e) {
            return null;
        }
    }
}
