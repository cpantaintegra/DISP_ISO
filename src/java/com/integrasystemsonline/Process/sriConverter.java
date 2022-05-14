package com.integrasystemsonline.Process;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("sriConverter")
public class sriConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String sri = "";
        if (value != null) {
            sri = (String) value;
            switch (sri) {
                case "S":
                    sri = "SI";
                    break;
                case "N":
                    sri = "NO";
                    break;
            }
        }
        return sri;
    }
}
