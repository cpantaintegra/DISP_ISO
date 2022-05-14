package com.integrasystemsonline.Process;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("tipoDocumentoConverter")
public class TipoDocumentoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String tipoDocumento = "";
        if (value != null) {
            tipoDocumento = (String) value;
            switch (tipoDocumento) {
                case "C":
                    tipoDocumento = "Cédula";
          break;
                case "R":
                    tipoDocumento = "RUC";
                    break;
                case "P":
                    tipoDocumento = "Pasaporte";
                    break;
            }
        }
        return tipoDocumento;
    }
}
