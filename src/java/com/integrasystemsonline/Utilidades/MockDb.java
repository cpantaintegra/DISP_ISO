package com.integrasystemsonline.Utilidades;

import java.util.ArrayList;
import java.util.List;
import org.primefaces.component.commandbutton.CommandButton;

public class MockDb {

    public static List<Header> getHeaderList(List<String> lstRow) {
        List<Header> headerList = new ArrayList<>();
        for (int i = 0; i < lstRow.size(); i++) {
            List<CommandButton> subColList = new ArrayList<>();
            CommandButton button = new CommandButton();
            button.setValue("Disponible");
            subColList.add(button);
            headerList.add(new Header("Header 1", subColList));
        }
        return headerList;
    }

    public static List<DataValue> getValueList(List<String> lstDatos) {
        List<DataValue> valueList = new ArrayList<>();
        for (int i = 0; i < lstDatos.size(); i++) {
            DataValue data = new DataValue(lstDatos.get(i));
            valueList.add(data);
        }
        return valueList;
    }
}
