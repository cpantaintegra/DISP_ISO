package com.Session;

import com.Entity.IsUsuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IsUsuariosFacade extends AbstractFacade<IsUsuarios> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public IsUsuariosFacade() {
        super(IsUsuarios.class);
    }

    public IsUsuarios findByUsuarioClave(String usuario, String clave) {
        Query q = this.em.createNamedQuery("IsUsuarios.findByUserClave");
        q.setParameter("usuario", usuario.toUpperCase());
        q.setParameter("clave", clave);
        IsUsuarios usuarios = null;
        try {
            usuarios = (IsUsuarios) q.getSingleResult();
        } catch (Exception exception) {
        }
        return usuarios;
    }

    public IsUsuarios findByUsuarioCorreo(String usuario, String correo) {
        Query q = this.em.createNamedQuery("IsUsuarios.findByUserCorreo");
        q.setParameter("usuario", usuario.toUpperCase());
        q.setParameter("correo", correo);
        IsUsuarios usuarios = null;
        try {
            usuarios = (IsUsuarios) q.getSingleResult();
        } catch (Exception exception) {
        }
        return usuarios;
    }

    public int countIsUsuarios(Integer empresa, Integer ciudad, Integer sector, String tipo_persona, String estado, String filtro) throws Exception {
        int resp = 0;
        try {
            String strQuery = "SELECT count(*) FROM is_usuarios u, is_roles r  WHERE "
                    + "u.id_roles=r.id_roles and "
                    + "r.nombre not in ('DOCENTE','ALUMNOS') AND "
                    + "u.id_empresa=?1 AND "
                    + "u.id_ciudad=?2 AND "
                    + "u.id_sector=?3 AND ";
            if (tipo_persona != null) {
                strQuery = strQuery + "u.tipo_persona = ?4 AND ";
            }
            strQuery = strQuery + "u.estado=?5 AND "
                    + "(upper(u.nombres) LIKE ?6 OR "
                    + "upper(u.detalle) LIKE ?6 OR "
                    + "upper(u.apellidos) LIKE ?6) ORDER BY u.id_usuarios";
            Query query = this.em.createNativeQuery(strQuery);
            query.setParameter(1, empresa);
            query.setParameter(2, ciudad);
            query.setParameter(3, sector);
            if (tipo_persona != null) {
                query.setParameter(4, tipo_persona);
            }
            query.setParameter(5, estado);
            query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
            resp = Integer.valueOf(query.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<IsUsuarios> listaIsUsuarios(Integer empresa, Integer ciudad, Integer sector, String tipo_persona, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
        String strQuery = "SELECT * FROM is_usuarios u, is_roles r WHERE "
                + "u.id_roles=r.id_roles and "
                + "r.nombre not in ('DOCENTE','ALUMNOS') AND "
                + "u.id_empresa=?1 AND "
                + "u.id_ciudad=?2 AND "
                + "u.id_sector=?3 AND ";
        if (tipo_persona != null) {
            strQuery = strQuery + "u.tipo_persona = ?4 AND ";
        }
        strQuery = strQuery + "u.estado=?5 AND "
                + "(upper(u.nombres) LIKE ?6 OR "
                + "upper(u.detalle) LIKE ?6 OR "
                + "upper(u.apellidos) LIKE ?6) ORDER BY u.id_usuarios";
        Query query = this.em.createNativeQuery(strQuery, IsUsuarios.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        if (tipo_persona != null) {
            query.setParameter(4, tipo_persona);
        }
        query.setParameter(5, estado);
        query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
        return query.getResultList();
    }

    public IsUsuarios findById(int id) {
        Query q = this.em.createNamedQuery("IsUsuarios.findByIdUsuarios");
        q.setParameter("idUsuarios", id);
        try {
            IsUsuarios usuarios = (IsUsuarios) q.getSingleResult();
            return usuarios;
        } catch (Exception e) {
            return null;
        }
    }

    public IsUsuarios findByCedula(String cedula) {
        Query q = this.em.createNamedQuery("IsUsuarios.findByCedula");
        q.setParameter("cedula", cedula);
        IsUsuarios usuarios = null;
        try {
            usuarios = (IsUsuarios) q.getSingleResult();
        } catch (Exception exception) {
        }
        return usuarios;
    }

    public IsUsuarios findByUser(String usuario, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("IsUsuarios.findByUsuario");
        q.setParameter("usuario", usuario);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        IsUsuarios usuarios = null;
        try {
            usuarios = (IsUsuarios) q.getSingleResult();
        } catch (Exception exception) {
        }
        return usuarios;
    }

    public IsUsuarios findUser(String usuario) {
        Query q = this.em.createNamedQuery("IsUsuarios.findByUser");
        q.setParameter("usuario", usuario);
        IsUsuarios usuarios = null;
        try {
            usuarios = (IsUsuarios) q.getSingleResult();
        } catch (Exception exception) {
        }
        return usuarios;
    }

    public List<IsUsuarios> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("IsUsuarios.findAllActivos");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }
}
