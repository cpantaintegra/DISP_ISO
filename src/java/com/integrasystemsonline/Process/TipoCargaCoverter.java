package com.integrasystemsonline.Process;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("tipoCargaConverter")
public class TipoCargaCoverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String tipoCarga = "";
        if (value != null) {
            tipoCarga = (String) value;
            switch (tipoCarga) {
                case "A":
                    tipoCarga = "Alumno";
                    break;
                case "D":
                    tipoCarga = "Docente";
                    break;
            }
        }
        return tipoCarga;
    }
}
