package com.integrasystemsonline.Process;

import com.Session.IsMenuFacade;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;

@FacesConverter("menuPadreConverter")
public class MenuPadreConverter implements Converter {

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            Context ctx = new InitialContext();
            IsMenuFacade isMenuFacade = (IsMenuFacade) ctx.lookup("java:global/" + this.propParam.getProperty("nombreProyecto") + "/IsMenuFacade");
            if (value != null) {
                Integer idMenuPadre = (Integer) value;
                return isMenuFacade.findById(idMenuPadre).getNombre();
            }
        } catch (Exception ex) {
            Logger.getLogger(MenuPadreConverter.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return "";
    }
}
