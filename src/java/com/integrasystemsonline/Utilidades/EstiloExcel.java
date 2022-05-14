package com.integrasystemsonline.Utilidades;

import org.apache.poi.ss.usermodel.Font;

public class EstiloExcel {

    private short aligment;

    private short verticalAligment;

    private short fillForegroundColor;

    private short fillPattern;

    private short borderBottom;

    private short borderLeft;

    private short borderRight;

    private short borderTop;

    private Font font;

    public EstiloExcel(short aligment, short verticalAligment, short fillForegroundColor, short fillPattern, short borderBottom, short borderLeft, short borderRight, short borderTop, Font font) {
        this.aligment = aligment;
        this.verticalAligment = verticalAligment;
        this.fillForegroundColor = fillForegroundColor;
        this.fillPattern = fillPattern;
        this.borderBottom = borderBottom;
        this.borderLeft = borderLeft;
        this.borderRight = borderRight;
        this.borderTop = borderTop;
        this.font = font;
    }

    public EstiloExcel() {
    }

    public short getAligment() {
        return this.aligment;
    }

    public void setAligment(short aligment) {
        this.aligment = aligment;
    }

    public short getVerticalAligment() {
        return this.verticalAligment;
    }

    public void setVerticalAligment(short verticalAligment) {
        this.verticalAligment = verticalAligment;
    }

    public short getFillForegroundColor() {
        return this.fillForegroundColor;
    }

    public void setFillForegroundColor(short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
    }

    public short getFillPattern() {
        return this.fillPattern;
    }

    public void setFillPattern(short fillPattern) {
        this.fillPattern = fillPattern;
    }

    public short getBorderBottom() {
        return this.borderBottom;
    }

    public void setBorderBottom(short borderBottom) {
        this.borderBottom = borderBottom;
    }

    public short getBorderLeft() {
        return this.borderLeft;
    }

    public void setBorderLeft(short borderLeft) {
        this.borderLeft = borderLeft;
    }

    public short getBorderRight() {
        return this.borderRight;
    }

    public void setBorderRight(short borderRight) {
        this.borderRight = borderRight;
    }

    public short getBorderTop() {
        return this.borderTop;
    }

    public void setBorderTop(short borderTop) {
        this.borderTop = borderTop;
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
