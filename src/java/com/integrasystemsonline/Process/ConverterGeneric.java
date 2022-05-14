package com.integrasystemsonline.Process;

import java.util.Collections;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("ConverterGeneric")
public class ConverterGeneric implements Converter {
  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    int index = Integer.parseInt(value);
    if (index == -1)
      return -1; 
    List<?> objects = getObjectsFromUISelectItemsComponent(component);
    return objects.get(index);
  }
  
  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    List<?> objects = getObjectsFromUISelectItemsComponent(component);
    String valor = "";
    try {
      valor = String.valueOf(objects.indexOf(value));
    } catch (Exception exception) {}
    return valor;
  }
  
  private List<?> getObjectsFromUISelectItemsComponent(UIComponent component) {
    List<?> objects = Collections.emptyList();
    for (UIComponent child : component.getChildren()) {
      if (child.getClass() == UISelectItems.class)
        objects = (List)((UISelectItems)child).getValue(); 
    } 
    return objects;
  }
}
