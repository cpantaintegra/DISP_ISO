package com.integrasystemsonline.Utilidades;

public class Columna {

    String id;

    boolean visible;

    public Columna() {
    }

    public Columna(String id, boolean visible) {
        this.id = id;
        this.visible = visible;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
