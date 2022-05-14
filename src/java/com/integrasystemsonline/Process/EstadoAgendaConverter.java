package com.integrasystemsonline.Process;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoAgendaConverter")
public class EstadoAgendaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String estado = "";
        if (value != null) {
            estado = (String) value;
            switch (estado) {
                case "C":
                    estado = "Creado";
                    return estado;
                case "D":
                    estado = "Disponible Triaje";
                    return estado;
                case "L":
                    estado = "Llamado Triaje";
                    return estado;
                case "IA":
                    estado = "Inicio Atencion Triaje";
                    return estado;
                case "AT":
                    estado = "Atentido Triaje";
                    return estado;
                case "DM":
                    estado = "Disponible Medico";
                    return estado;
                case "LM":
                    estado = "Llamado Medico";
                    return estado;
                case "IM":
                    estado = "Inicio Atencion Medico";
                    return estado;
                case "AM":
                    estado = "Atendido Medico";
                    return estado;
                case "FT":
                    estado = "Perdido Triaje";
                    return estado;
                case "FM":
                    estado = "Perdido Medico";
                    return estado;
                case "RT":
                    estado = "Turno Recuperado";
                    return estado;
            }
            estado = "Finalizado";
        }
        return estado;
    }
}
