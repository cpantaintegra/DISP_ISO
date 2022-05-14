package com.webService;

import com.Entity.DispAgendamiento;
import com.Entity.DispCliente;
import com.Entity.DispEspecialidad;
import com.Entity.DispMedicoPersonal;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.DispClienteFacade;
import com.Session.DispEspecialidadFacade;
import com.Session.DispMedicoPersonalFacade;
import com.Session.DispServicioFacade;
import com.Session.IsUsuariosFacade;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.joda.time.DateTime;

@WebService(serviceName = "generarTurnoPaciente")
public class generarTurnoPaciente {

    @EJB
    private DispServicioFacade dispServicioFacade;

    @EJB
    private DispClienteFacade dispClienteFacade;

    @EJB
    private DispMedicoPersonalFacade dispMedicoPersonalFacade;

    @EJB
    private DispEspecialidadFacade dispEspecialidadFacade;

    @EJB
    private DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    IsUsuariosFacade isUsuariosFacade;

    private SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    @WebMethod(operationName = "listaEspecialidades")
    public List<DispEspecialidad> listaEspecialidades(@WebParam(name = "id_empresa") int id_empresa, @WebParam(name = "id_ciudad") int id_ciudad, @WebParam(name = "id_sector") int id_sector, @WebParam(name = "usuario") String usuario, @WebParam(name = "password") String password) {
        try {
            String pass = "";
            IsUsuarios usuarioObj = null;
            usuarioObj = this.isUsuariosFacade.findByUsuarioClave(usuario, password);
            if (usuarioObj != null) {
                List<DispEspecialidad> lstEspecialidades = this.dispEspecialidadFacade.findAllActivos(id_empresa, id_ciudad, id_sector);
                return lstEspecialidades;
            }
            System.out.println("Usuario o contraseincorrecta");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "registrarTurno")
    public DispAgendamiento registrarTurno(@WebParam(name = "usuario") String usuario, @WebParam(name = "password") String password, @WebParam(name = "id_especialidad") int id_especialidad) {
        DispAgendamiento valor = null;
        List<DispAgendamiento> listAgendamiento = new ArrayList<>();
        DispAgendamiento agendamientoObj = null;
        try {
            Date now = new Date();
            String ini = this.propParam.getProperty("minTime");
            String[] partesHoraIni = ini.split(":");
            int horaIni = Integer.parseInt(partesHoraIni[0]);
            int minutosIni = Integer.parseInt(partesHoraIni[1]);
            String fin = this.propParam.getProperty("maxTime");
            String[] partesHora = fin.split(":");
            int horaFin = Integer.parseInt(partesHora[0]);
            int minutosFin = Integer.parseInt(partesHora[1]);
            DateTime dataSelecionadaJoda = new DateTime(now.getTime());
            Date fechaIni = dataSelecionadaJoda.withHourOfDay(horaIni).withMinuteOfHour(minutosIni).withSecondOfMinute(0).toDate();
            Date fechaFin = dataSelecionadaJoda.withHourOfDay(horaFin).withMinuteOfHour(minutosFin).withSecondOfMinute(0).toDate();
            DateTime fechaCita = new DateTime(now.getTime());
            String timexCita = this.propParam.getProperty("timePlus");
            int duracion = Integer.parseInt(timexCita);
            int hora = fechaCita.getHourOfDay();
            int minuto = fechaCita.getMinuteOfHour();
            int residuo = minuto % duracion;
            int diff = duracion - residuo;
            if (minuto + diff == 60) {
                hora++;
                diff = 0;
                minuto = 0;
            }
            IsUsuarios usuarioObj = this.isUsuariosFacade.findByUsuarioClave(usuario, password);
            if (usuarioObj != null) {
                DispEspecialidad especialidadoObj = this.dispEspecialidadFacade.findById(id_especialidad);
                DispMedicoPersonal medicoPersonalObj = this.dispMedicoPersonalFacade.findById(1);
                DispCliente clienteObj = this.dispClienteFacade.findById(1);
                Date fechaNueva = fechaCita.withHourOfDay(hora).withMinuteOfHour(minuto + diff).withSecondOfMinute(0).toDate();
                listAgendamiento = this.dispAgendamientoFacade.findAllCreated(fechaIni, fechaFin, usuarioObj.getIdEmpresa().getIdEmpresa(), usuarioObj.getIdCiudad().getIdCiudad(), usuarioObj.getIdSector().getIdSector());
                for (int i = 0; i < listAgendamiento.size();) {
                    agendamientoObj = this.dispAgendamientoFacade.findByFecha(fechaNueva, usuarioObj.getIdEmpresa().getIdEmpresa(), usuarioObj.getIdCiudad().getIdCiudad(), usuarioObj.getIdSector().getIdSector());
                    if (agendamientoObj != null) {
                        DateTime fecha = new DateTime(fechaNueva.getTime());
                        fechaNueva = fecha.plusMinutes(duracion).toDate();
                        i++;
                    }
                }
                int turno = this.dispAgendamientoFacade.countTurno(fechaIni, fechaFin, especialidadoObj.getIdEspecialidad(), usuarioObj.getIdEmpresa().getIdEmpresa(), usuarioObj.getIdCiudad().getIdCiudad(), usuarioObj.getIdSector().getIdSector()) + 1;
                agendamientoObj = new DispAgendamiento();
                agendamientoObj.setFecha(this.objSDF.parse(this.objSDF.format(fechaNueva)));
                agendamientoObj.setTurno(turno);
                agendamientoObj.setCosto(new BigDecimal(0));
                agendamientoObj.setIdCliente(clienteObj);
                agendamientoObj.setIdEspecialidad(especialidadoObj);
                agendamientoObj.setIdMedicoPersonal(medicoPersonalObj);
                agendamientoObj.setEstado("C");
                agendamientoObj.setIdEmpresa(usuarioObj.getIdEmpresa());
                agendamientoObj.setIdCiudad(usuarioObj.getIdCiudad());
                agendamientoObj.setIdSector(usuarioObj.getIdSector());
                agendamientoObj.setUsuarioIngreso(usuarioObj.getUsuario());
                agendamientoObj.setFechaIngreso(this.objSDF.parse(this.objSDF.format(new Date())));
                this.dispAgendamientoFacade.createWithValidator(agendamientoObj);
                this.dispAgendamientoFacade.flush();
                valor = agendamientoObj;
            } else {
                System.out.println("Usuario o contraseincorrecta");
            }
        } catch (Exception e) {
            e.printStackTrace();
            valor = null;
        }
        return valor;
    }
}
