package com.integrasystemsonline.Process;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoCivilConverter")
public class EstadoCivilConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String estadoCivil = "";
        if (value != null) {
            estadoCivil = (String) value;
            switch (estadoCivil) {
                case "S":
                    estadoCivil = "Soltero";
                    break;
                case "C":
                    estadoCivil = "Casado";
                    break;
                case "D":
                    estadoCivil = "Divorciado";
                    break;
                case "V":
                    estadoCivil = "Viudo";
                    break;
                case "O":
                    estadoCivil = "Concubinato";
                    break;
                case "E":
                    estadoCivil = "Separacien proceso judicial";
                    break;
            }
        }
        return estadoCivil;
    }
}
