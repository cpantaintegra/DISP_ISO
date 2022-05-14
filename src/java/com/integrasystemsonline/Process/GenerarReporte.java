package com.integrasystemsonline.Process;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.component.panelgrid.PanelGrid;

public class GenerarReporte {

    private PanelGrid panel;

    public void listarColumnas(Object object) {
        try {
            String temp = "";
            List<String> nombreCampo = new ArrayList<>();
            Field[] lstCamposEntidad = object.getClass().getDeclaredFields();
            for (int i = 0; i < lstCamposEntidad.length; i++) {
                if (!lstCamposEntidad[i].getName().equals("serialVersionUID")) {
                    temp = lstCamposEntidad[i].getName();
                    if (temp.equals("usuarioIngreso")) {
                        i = lstCamposEntidad.length;
                    } else if (!temp.contains("id")) {
                        nombreCampo.add(lstCamposEntidad[i].getName());
                    }
                }
            }
            TablaDinamica obj = new TablaDinamica();
            obj.generarListCamposEntidad(nombreCampo);
            this.panel = obj.getPanel();
        } catch (Exception exception) {
        }
    }

    public static void visibilidadColumna() {
    }

    public PanelGrid getPanel() {
        return this.panel;
    }

    public void setPanel(PanelGrid panel) {
        this.panel = panel;
    }
}
