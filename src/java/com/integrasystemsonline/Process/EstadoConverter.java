package com.integrasystemsonline.Process;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoConverter")
public class EstadoConverter implements Converter {

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
                case "A":
                    estado = "Activo";
                    break;
                case "I":
                    estado = "Inactivo";
                    break;
            }
        }
        return estado;
    }
}
