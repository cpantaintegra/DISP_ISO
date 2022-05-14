package com.Session;

import com.Entity.IsSector;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Stateless
public class IsSectorFacade extends AbstractFacade<IsSector> {
  @PersistenceContext(unitName = "WebApplication2PU")
  private EntityManager em;
  
  @Override
  protected EntityManager getEntityManager() {
    return this.em;
  }
  
  public IsSectorFacade() {
    super(IsSector.class);
  }
  
  public int countIsSector(String estado, String filtro) throws Exception {
    int resp = 0;
    try {
      Query query = this.em.createNativeQuery("SELECT count(*) FROM is_sector WHERE "
              + "ESTADO = ?1  AND "
              + "(upper(NOMBRE) LIKE ?2 OR upper(DETALLE) LIKE ?2)");
      query.setParameter(1, estado);
      query.setParameter(2, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
      resp = Integer.valueOf(query.getSingleResult().toString());
    } catch (Exception exception) {}
    return resp;
  }
  
  public List<IsSector> listaIsSector(String estado, int startingAt, int maxPerPage, String filtro) throws Exception {
    Query query = this.em.createNativeQuery("SELECT * FROM is_sector WHERE "
            + "ESTADO = ?1  AND "
            + "(upper(NOMBRE) LIKE ?2 OR upper(DETALLE) LIKE ?2)", IsSector.class);
    query.setParameter(1, estado);
    query.setParameter(2, "%" + ((filtro == null) ? "" : filtro.toUpperCase()) + "%");
    query.setFirstResult(startingAt);
    query.setMaxResults(maxPerPage);
    return query.getResultList();
  }
  
  public IsSector findById(int id) {
    Query q = this.em.createNamedQuery("IsSector.findByIdSector");
    q.setParameter("idSector", id);
    IsSector sector = (IsSector)q.getSingleResult();
    return sector;
  }
  
  @Transactional
  public List<IsSector> getCiudaList() {
    return this.em.createQuery("select a from is_sector a order by a.id_sector").getResultList();
  }
  
  public List<IsSector> findAllActivos() {
    Query q = this.em.createNamedQuery("IsSector.findAllActivos");
    return q.getResultList();
  }
  
  public List<IsSector> listaIsSectorActivos(String estado) throws Exception {
    String strQuery = "SELECT * FROM is_sector WHERE estado='A' BY orden";
    Query query = this.em.createNativeQuery(strQuery, IsSector.class);
    query.setParameter(1, estado);
    return query.getResultList();
  }
}
