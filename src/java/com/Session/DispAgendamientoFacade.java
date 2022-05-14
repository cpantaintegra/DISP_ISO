package com.Session;

import com.Entity.AgendaDiagnostico;
import com.Entity.DispAgendamiento;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DispAgendamientoFacade extends AbstractFacade<DispAgendamiento> {

    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DispAgendamientoFacade() {
        super(DispAgendamiento.class);
    }

    public DispAgendamiento findById(int id) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findByIdAgendamiento");
        q.setParameter("idAgendamiento", id);
        DispAgendamiento agendamiento = (DispAgendamiento) q.getSingleResult();
        return agendamiento;
    }

    public List<DispAgendamiento> findByCliente(int idCliente) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findByIdCliente");
        q.setParameter("idCliente", idCliente);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispAgendamiento> findByMedico(int idMedicoPersonal) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findByIdMedicoPersonal");
        q.setParameter("idMedicoPersonal", idMedicoPersonal);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispAgendamiento> findByEspecialidad(int idEspecialidad) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findByIdEspecialidad");
        q.setParameter("idEspecialidad", idEspecialidad);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispAgendamiento> findByClienteEspecialidad(int idCliente, int idEspecialidad) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findByClienteEspecialidad");
        q.setParameter("idCliente", idCliente);
        q.setParameter("idEspecialidad", idEspecialidad);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public DispAgendamiento findByEspecialidadMedico(Date fecha, int idEspecialidad, int idMedico) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findByEspecialidadMedico");
        q.setParameter("idEspecialidad", idEspecialidad);
        q.setParameter("idMedicoPersonal", idMedico);
        q.setParameter("fecha", fecha);
        try {
            DispAgendamiento agendamiento = (DispAgendamiento) q.getSingleResult();
            return agendamiento;
        } catch (Exception e) {
            return null;
        }
    }

    public List<DispAgendamiento> findAllCalendario(Date fecha, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllCalendario");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispAgendamiento> findAllCreated(Date fechaIni, Date fechaFin, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllCreated");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispAgendamiento> findAllTriaje(Date fechaIni, Date fechaFin, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllTriaje");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispAgendamiento> findAllLlamados(Date fechaIni, Date fechaFin, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllLlamados");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispAgendamiento> findAllFinalizados(Date fechaIni, Date fechaFin, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllFinalizado");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispAgendamiento> findAllAtendidoMedicoOperador(Date fechaIni, Date fechaFin, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllAtendidoMedicoOperador");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispAgendamiento> findAllAtendidoTriaje(Date fechaIni, Date fechaFin, Integer medico, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllAtendidoTriaje");
        q.setParameter("medico", medico);
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public DispAgendamiento findByFecha(Date fecha, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findByFechaByIDs");
        q.setParameter("fecha", fecha);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            return (DispAgendamiento) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public int countTurno(Date fechaIni, Date fechaFin, Integer especialidad, Integer empresa, Integer ciudad, Integer sector) throws Exception {
        int resp = 0;
        try {
            Query q = this.em.createNamedQuery("DispAgendamiento.CountTurno");
            q.setParameter("especialidad", especialidad);
            q.setParameter("fechaIni", fechaIni);
            q.setParameter("fechaFin", fechaFin);
            q.setParameter("empresa", empresa);
            q.setParameter("ciudad", ciudad);
            q.setParameter("sector", sector);
            resp = Integer.valueOf(q.getSingleResult().toString());
        } catch (Exception exception) {
        }
        return resp;
    }

    public List<DispAgendamiento> findAllMedico(Date fechaIni, Date fechaFin, Integer medico, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllMedico");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("medico", medico);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispAgendamiento> findAllByEmpresa(Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllByEmpresa");
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        return q.getResultList();
    }

    public List<DispAgendamiento> findByAtendidoMedico(Date fechaIni, Date fechaFin, Integer medico, Integer empresa, Integer ciudad, Integer sector) {
        Query q = this.em.createNamedQuery("DispAgendamiento.findAllAtendidoMedico");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("medico", medico);
        q.setParameter("empresa", empresa);
        q.setParameter("ciudad", ciudad);
        q.setParameter("sector", sector);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<AgendaDiagnostico> listaAgendaDiagnostico(Date fechaini, Date fechafin, String estado, String pagado, Integer empresa, Integer ciudad, Integer sector) throws Exception {
        String strQuery = "SELECT a.id_agendamiento,UPPER(CONCAT_WS(' ',c.apaterno,c.amaterno, c.nombre)) nombPaciente, e.nombre especialidad,\n"
                + "r.motivo_consulta, d.enfermedad diagnostico, s.nombre servicio, a.estado,a.fecha,\n"
                + "a.fecha_atendido,a.fecha_atendido_medico,a.fecha_inicio_atencion,a.fecha_inicio_medico,a.fecha_llamada,a.fecha_llamar_medico\n"
                + "FROM disp_agendamiento a\n"
                + "INNER JOIN disp_cliente c ON c.id_cliente = a.id_cliente AND a.id_empresa = ?1 \n"
                + "AND a.id_ciudad = ?2\n"
                + "AND a.id_sector = ?3\n"
                + "AND a.fecha BETWEEN ?4 AND ?5\n"
                + "AND a.estado != 'CA' \n";
        if (estado != null) {
            strQuery = strQuery + " AND a.estado = ?6 ";
        }
        if (pagado.equals("0")) {
            strQuery = strQuery + " AND a.pagado is null ";
        } else if (!pagado.equals("All")) {
            strQuery = strQuery + " AND a.pagado = ?7 ";
        }
        strQuery = strQuery + "INNER JOIN disp_especialidad e ON e.id_especialidad = a.id_especialidad\n"
                + "INNER JOIN disp_servicio s ON s.id_servicio = a.id_servicio\n"
                + "INNER JOIN disp_medico_personal mp ON mp.id_medico_personal=a.id_medico_personal\n"
                + "LEFT JOIN disp_resultado r ON r.id_agendamiento = a.id_agendamiento\n"
                + "LEFT JOIN disp_detalle_diagnostico dg ON dg.id_resultado = r.id_resultado\n"
                + "LEFT JOIN disp_diagnostico d ON d.id_diagnostico = dg.id_diagnostico ";
        Query query = this.em.createNativeQuery(strQuery, AgendaDiagnostico.class);
        query.setParameter(1, empresa);
        query.setParameter(2, ciudad);
        query.setParameter(3, sector);
        query.setParameter(4, fechaini);
        query.setParameter(5, fechafin);
        if (estado != null) {
            query.setParameter(6, estado);
        }
        if (!pagado.equals("0") && !pagado.equals("All")) {
            query.setParameter(7, pagado);
        }
        return query.getResultList();
    }
}
