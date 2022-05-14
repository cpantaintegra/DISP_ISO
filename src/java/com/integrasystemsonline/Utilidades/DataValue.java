package com.integrasystemsonline.Utilidades;

import org.primefaces.component.commandbutton.CommandButton;

public class DataValue {

    private String data1;

    private CommandButton button;

    public DataValue() {
    }

    public DataValue(String data1) {
        this.data1 = data1;
    }

    public String getData1() {
        return this.data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public CommandButton getButton() {
        return this.button;
    }

    public void setButton(CommandButton button) {
        this.button = button;
    }
}
