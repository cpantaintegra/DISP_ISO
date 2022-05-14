package com.Session;

import com.Entity.DispConsultorio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispConsultorioFacade extends AbstractFacade<DispConsultorio> {
  @PersistenceContext(unitName = "WebApplication2PU")
  private EntityManager em;
  
  @Override
  protected EntityManager getEntityManager() {
    return this.em;
  }
  
  public DispConsultorioFacade() {
    super(DispConsultorio.class);
  }
  
  public int countDispConsultorio(Integer especialidad, Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
    int resp = 0;
    try {
      String StringQuery = "SELECT COUNT(*) FROM disp_consultorio WHERE \n"
              + "id_empresa = ?1 AND\n"
              + "id_ciudad = ?2 AND\n"
              + "id_sector = ?3 AND\n";
      if (especialidad > 0)
        StringQuery = StringQuery + "id_especialidad = ?4 AND\n"; 
      StringQuery = StringQuery + "ESTADO = ?5 AND\n"
              + "(upper(nombre) LIKE ?6 OR\n"
              + "upper(detalle) LIKE ?6)\n";
      Query query = this.em.createNativeQuery(StringQuery);
      query.setParameter(1, empresa);
      query.setParameter(2, ciudad);
      query.setParameter(3, sector);
      if (especialidad > 0)
        query.setParameter(4, especialidad); 
      query.setParameter(5, estado);
      query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
      resp = Integer.valueOf(query.getSingleResult().toString());
    } catch (Exception exception) {}
    return resp;
  }
  
  public List<DispConsultorio> listaDispConsultorio(Integer especialidad, Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
    String StringQuery = "SELECT * FROM disp_consultorio WHERE \n"
            + "id_empresa = ?1 AND\n"
            + "id_ciudad = ?2 AND\n"
            + "id_sector = ?3 AND\n";
    if (especialidad > 0)
      StringQuery = StringQuery + "id_especialidad = ?4 AND\n"; 
    StringQuery = StringQuery + "ESTADO = ?5 AND\n"
            + "(upper(nombre) LIKE ?6 OR\n"
            + "upper(detalle) LIKE ?6)\n";
    Query query = this.em.createNativeQuery(StringQuery, DispConsultorio.class);
    query.setParameter(1, empresa);
    query.setParameter(2, ciudad);
    query.setParameter(3, sector);
    if (especialidad > 0)
      query.setParameter(4, especialidad); 
    query.setParameter(5, estado);
    query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
    query.setFirstResult(startingAt);
    query.setMaxResults(maxPerPage);
    return query.getResultList();
  }
  
  public DispConsultorio findById(int id) {
    Query q = this.em.createNamedQuery("DispConsultorio.findByIdConsultorio");
    q.setParameter("idConsultorio", id);
    DispConsultorio consultorio = (DispConsultorio)q.getSingleResult();
    return consultorio;
  }
  
  public List<DispConsultorio> findByConsultorioByEspecialidad(int idEspecialidad) {
    Query q = this.em.createNamedQuery("DispConsultorio.findByIdEspecialidad");
    q.setParameter("idEspecialidad", idEspecialidad);
    return q.getResultList();
  }
  
  public DispConsultorio findByNombre(String nombre, Integer empresa, Integer ciudad, Integer sector) {
    Query q = this.em.createNamedQuery("DispConsultorio.findByNombreByIDs");
    q.setParameter("nombre", nombre);
    q.setParameter("empresa", empresa);
    q.setParameter("ciudad", ciudad);
    q.setParameter("sector", sector);
    try {
      DispConsultorio consultorio = (DispConsultorio)q.getSingleResult();
      return consultorio;
    } catch (Exception e) {
      return null;
    } 
  }
  
  public List<DispConsultorio> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
    Query q = this.em.createNamedQuery("DispConsultorio.findAllActivos");
    q.setParameter("empresa", empresa);
    q.setParameter("ciudad", ciudad);
    q.setParameter("sector", sector);
    return q.getResultList();
  }
}