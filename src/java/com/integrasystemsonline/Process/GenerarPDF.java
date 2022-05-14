package com.integrasystemsonline.Process;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GenerarPDF {

    public void crearPdf(ArrayList<String> str, String ruta) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            int y = 0;
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            for (int i = 0; i < str.size(); i++) {
                contentStream.beginText();
                contentStream.setFont((PDFont) PDType1Font.TIMES_ROMAN, 11.0F);
                contentStream.newLineAtOffset(20.0F, page.getMediaBox().getHeight() - (10 * (i + 1)));
                if (((String) str.get(i)).contains("\n")) {
                    contentStream.showText(" ");
                } else {
                    contentStream.showText(str.get(i));
                }
                contentStream.endText();
            }
            contentStream.close();
            document.save(ruta);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
}
