package com.Session;

import com.Entity.DispAntecedentes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispAntecedentesFacade extends AbstractFacade<DispAntecedentes> {
  @PersistenceContext(unitName = "WebApplication2PU")
  private EntityManager em;
  
  @Override
  protected EntityManager getEntityManager() {
    return this.em;
  }
  
  public DispAntecedentesFacade() {
    super(DispAntecedentes.class);
  }
  
  public int countDispAntecedentes(Integer cliente, Integer empresa, Integer ciudad, Integer sector, String estado, String filtro) throws Exception {
    int resp = 0;
    try {
      String StringQuery = "SELECT COUNT(*) FROM disp_antecedentes WHERE \n"
              + "id_empresa = ?1 AND\n"
              + "id_ciudad = ?2 AND\n"
              + "id_sector = ?3 AND\n"
              + "id_cliente = ?4 AND\n";
      StringQuery = StringQuery + "ESTADO = ?5 AND\n"
              + "(upper(ante_personales) LIKE ?6 OR\n"
              + "upper(ante_familiares) LIKE ?6 OR\n"
              + "upper(ante_oftalmologicos) LIKE ?6 OR\n"
              + "upper(alergias) LIKE ?6 OR\n"
              + "upper(medicamentos) LIKE ?6 OR\n"
              + "upper(inter_quirurgicas) LIKE ?6 OR\n"
              + "upper(anestesicos) LIKE ?6) order by fecha_ingreso desc";
      Query query = this.em.createNativeQuery(StringQuery);
      query.setParameter(1, empresa);
      query.setParameter(2, ciudad);
      query.setParameter(3, sector);
      query.setParameter(4, cliente);
      query.setParameter(5, estado);
      query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
      resp = Integer.valueOf(query.getSingleResult().toString());
    } catch (Exception exception) {}
    return resp;
  }
  
  public List<DispAntecedentes> listaDispAntecedentes(Integer cliente, Integer empresa, Integer ciudad, Integer sector, String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
    String StringQuery = "SELECT * FROM disp_antecedentes WHERE "
            + "id_empresa = ?1 AND "
            + "id_ciudad = ?2 AND "
            + "id_sector = ?3 AND\n"
            + "id_cliente = ?4 AND\n";
    StringQuery = StringQuery + "ESTADO = ?5 AND\n"
            + "(upper(ante_personales) LIKE ?6 OR\n"
            + "upper(ante_familiares) LIKE ?6 OR\n"
            + "upper(ante_oftalmologicos) LIKE ?6 OR\n"
            + "upper(alergias) LIKE ?6 OR\n"
            + "upper(medicamentos) LIKE ?6 OR\n"
            + "upper(inter_quirurgicas) LIKE ?6 OR\n"
            + "upper(anestesicos) LIKE ?6) order by fecha_ingreso desc";
    Query query = this.em.createNativeQuery(StringQuery, DispAntecedentes.class);
    query.setParameter(1, empresa);
    query.setParameter(2, ciudad);
    query.setParameter(3, sector);
    query.setParameter(4, cliente);
    query.setParameter(5, estado);
    query.setParameter(6, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
    query.setFirstResult(startingAt);
    query.setMaxResults(maxPerPage);
    return query.getResultList();
  }
  
  public DispAntecedentes findById(int id) {
    Query q = this.em.createNamedQuery("DispAntecedentes.findByIdAntecedentes");
    q.setParameter("idAntecedentes", id);
    DispAntecedentes dispAntecedentes = (DispAntecedentes)q.getSingleResult();
    return dispAntecedentes;
  }
  
  public List<DispAntecedentes> findByIdCliente(int id) {
    Query q = this.em.createNamedQuery("DispAntecedentes.findByIdCliente");
    q.setParameter("idCliente", id);
    return q.getResultList();
  }
  
  public List<DispAntecedentes> findAllActivos(Integer empresa, Integer ciudad, Integer sector) {
    Query q = this.em.createNamedQuery("DispAntecedentes.findAllActivos");
    q.setParameter("empresa", empresa);
    q.setParameter("ciudad", ciudad);
    q.setParameter("sector", sector);
    return q.getResultList();
  }
}
