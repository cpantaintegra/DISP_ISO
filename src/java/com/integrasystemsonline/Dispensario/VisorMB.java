package com.integrasystemsonline.Dispensario;

import com.Entity.DispAgendamiento;
import com.Entity.IsParametros;
import com.Entity.IsUsuarios;
import com.Session.DispAgendamientoFacade;
import com.Session.IsParametrosFacade;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.joda.time.DateTime;

@Named("visorMB")
@ViewScoped
public class VisorMB implements Serializable {

    @EJB
    DispAgendamientoFacade dispAgendamientoFacade;

    @EJB
    IsParametrosFacade isParametrosFacade;

    DispAgendamiento dispAgendamiento;

    List<DispAgendamiento> listDispAgendamiento = new ArrayList<>();

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    IsUsuarios usuario;

    private String timeZone = "";

    Date fechaActual = new Date();

    Date fechaFin = new Date();

    @PostConstruct
    public void ini() {
        try {
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("timeZone", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            this.timeZone = isParametros.getValor();
            isParametros = this.isParametrosFacade.findByCodigo("timePlus", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            int DuracionCita = Integer.parseInt(isParametros.getValor());
            isParametros = this.isParametrosFacade.findByCodigo("tiempoEspera", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            int tiempoEspera = Integer.parseInt(isParametros.getValor());
            isParametros = this.isParametrosFacade.findByCodigo("tiempoEspera", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            int tiempoCita = Integer.parseInt(isParametros.getValor());
            isParametros = this.isParametrosFacade.findByCodigo("maxTime", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            String maxTime = isParametros.getValor();
            String[] partesHora = maxTime.split(":");
            int horaFin = Integer.parseInt(partesHora[0]);
            int minutosFin = Integer.parseInt(partesHora[1]);
            this.fechaActual = Utilidades.obtenerFechaZonaHoraria(new Date(), "0", this.timeZone);
            DateTime dataSelecionadaJoda = new DateTime(this.fechaActual.getTime());
            this.fechaFin = dataSelecionadaJoda.withHourOfDay(horaFin).withMinuteOfHour(minutosFin).withSecondOfMinute(0).toDate();
            this.listDispAgendamiento = this.dispAgendamientoFacade.findAllLlamados(this.fechaActual, this.fechaFin, this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        } catch (Exception exception) {
        }
    }

    public boolean estadoAgendamineto(DispAgendamiento agendamiento) {
        boolean formato = false;
        try {
            Date fechaLlamado = null;
            fechaLlamado = agendamiento.getFechaLlamada();
            DateTime fecha = new DateTime(fechaLlamado.getTime());
            IsParametros isParametros = this.isParametrosFacade.findByCodigo("tiempoLlamado", this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
            int tiempoLlamado = Integer.parseInt(isParametros.getValor());
            Date fechaFin = fecha.plusSeconds(tiempoLlamado).toDate();
            int tiempoLlamadomiliseg = tiempoLlamado * 1000;
            int diff = (int) (this.fechaActual.getTime() - fechaFin.getTime());
            if (diff <= tiempoLlamadomiliseg) {
                formato = true;
            }
        } catch (Exception exception) {
        }
        return formato;
    }

    public String mostrarTurno(DispAgendamiento dispAgendamiento) {
        String turnoActual = "-";
        try {
            if (!this.listDispAgendamiento.isEmpty()) {
                if (dispAgendamiento.getTurno() == 0) {
                    turnoActual = dispAgendamiento.getIdCliente().getApaterno().toUpperCase().concat(" ").concat(dispAgendamiento.getIdCliente().getAmaterno().toUpperCase()).concat(" ").concat(dispAgendamiento.getIdCliente().getNombre().toUpperCase());
                } else {
                    turnoActual = dispAgendamiento.getIdEspecialidad().getCodigo().concat("-").concat(String.format("%03d", new Object[]{Integer.valueOf(dispAgendamiento.getTurno())}));
                }
            }
        } catch (Exception exception) {
        }
        return turnoActual;
    }

    public void refreshListAgendamiento() {
        ini();
    }

    public DispAgendamiento getDispAgendamiento() {
        return this.dispAgendamiento;
    }

    public void setDispAgendamiento(DispAgendamiento dispAgendamiento) {
        this.dispAgendamiento = dispAgendamiento;
    }

    public List<DispAgendamiento> getListDispAgendamiento() {
        return this.listDispAgendamiento;
    }

    public void setListDispAgendamiento(List<DispAgendamiento> listDispAgendamiento) {
        this.listDispAgendamiento = listDispAgendamiento;
    }
}
