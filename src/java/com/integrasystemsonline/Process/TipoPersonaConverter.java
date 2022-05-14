package com.integrasystemsonline.Process;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("tipoPersonaConverter")
public class TipoPersonaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String tipoPersona = "";
        if (value != null) {
            tipoPersona = (String) value;
            switch (tipoPersona) {
                case "N":
                    tipoPersona = "Natural";
                    break;
                case "J":
                    tipoPersona = "Juridico";
                    break;
                case "E":
                    tipoPersona = "Extranjero";
                    break;
            }
        }
        return tipoPersona;
    }
}
