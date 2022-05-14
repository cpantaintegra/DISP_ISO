package com.integrasystemsonline.Utilidades;

import java.util.List;
import org.primefaces.component.commandbutton.CommandButton;

public class Header {

    private String name;

    private List<CommandButton> subColList;

    public Header() {
    }

    public Header(String name, List<CommandButton> subColList) {
        this.name = name;
        this.subColList = subColList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommandButton> getSubColList() {
        return this.subColList;
    }

    public void setSubColList(List<CommandButton> subColList) {
        this.subColList = subColList;
    }
}
