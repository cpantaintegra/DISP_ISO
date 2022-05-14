package com.integrasystemsonline.Process;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

class ImprimirFactura implements Printable {

    PrinterJob job;

    @Override
    public int print(Graphics g, PageFormat f, int pageIndex) throws PrinterException {
        if (pageIndex == 0) {
            g.drawString("Prueba", 100, 100);
            return 0;
        }
        return 1;
    }

    public void imprimir() {
        this.job = PrinterJob.getPrinterJob();
        this.job.setPrintable(new ImprimirFactura());
        try {
            PageFormat pageFormat = new PageFormat();
            pageFormat = this.job.pageDialog(pageFormat);
            if (this.job.printDialog()) {
                this.job.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    public void configImprimir() {
    }
}
