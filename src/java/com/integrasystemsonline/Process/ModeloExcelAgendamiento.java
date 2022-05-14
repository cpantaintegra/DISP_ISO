package com.integrasystemsonline.Process;

import com.Entity.IsUsuarios;
import com.Session.IsParametrosFacade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ModeloExcelAgendamiento {

    @EJB
    IsParametrosFacade isParametrosFacade;

    String[] especialidad;

    String[] servicio;

    String[] diagnostico;

    String[] motivoConsulta;

    String[] nombres;

    String[] apellidoPaterno;

    String[] apellidoMaterno;

    Date[] fechaAgendamiento;

    int totalNoOfRows;

    int totalNoOfCols;

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public static final String SEPARATOR = ",";

    public static final String QUOTE = "\"";

    String[] datos;

    int numLinea = 0;

    boolean formatoCorrecto = false;

    public boolean validarImportacion(String ruta, ArrayList<String> lst) {
        boolean ok = false;
        try {
            FileInputStream file = new FileInputStream(new File(ruta));
            Workbook wb = WorkbookFactory.create(file);
            Sheet sh = wb.getSheetAt(0);
            int numCol = lst.size();
            for (int j = 0; j < numCol; j++) {
                String str = sh.getRow(0).getCell(j).getStringCellValue();
                if (str.equals(lst.get(j))) {
                    ok = true;
                } else {
                    j = numCol;
                    ok = false;
                }
            }
        } catch (Exception e) {
            ok = false;
        }
        return ok;
    }

    public boolean validarImportacionCSV(ArrayList<String> lst) {
        boolean ok = false;
        try {
            int numCol = lst.size();
            for (int j = 0; j < numCol; j++) {
                if (this.datos[j].toLowerCase().equals(((String) lst.get(j)).toLowerCase())) {
                    ok = true;
                } else {
                    j = numCol;
                    ok = false;
                }
            }
        } catch (Exception e) {
            ok = false;
        }
        return ok;
    }

    public void exportar(String nombre, String ruta, ArrayList<String> lst, String[][] str, int numCol, int numRow) {
        File archivo = new File(ruta);
        try {
            IsUsuarios usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            FileOutputStream file = new FileOutputStream(archivo);
            XSSFWorkbook worbook = new XSSFWorkbook();
            XSSFSheet sheet = worbook.createSheet(nombre);
            XSSFRow xSSFRow = sheet.createRow(0);
            XSSFCellStyle xSSFCellStyle1 = worbook.createCellStyle();
            xSSFCellStyle1.setAlignment((short) 0);
            xSSFCellStyle1.setVerticalAlignment((short) 0);
            xSSFCellStyle1.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            xSSFCellStyle1.setFillPattern((short) 1);
            xSSFCellStyle1.setBorderBottom((short) 1);
            xSSFCellStyle1.setBorderLeft((short) 1);
            xSSFCellStyle1.setBorderRight((short) 1);
            xSSFCellStyle1.setBorderTop((short) 1);
            XSSFFont xSSFFont1 = worbook.createFont();
            xSSFFont1.setFontName("Arial");
            xSSFFont1.setBold(true);
            xSSFFont1.setFontHeightInPoints((short) 11);
            xSSFCellStyle1.setFont((Font) xSSFFont1);
            for (int j = 0; j < numCol; j++) {
                Cell cell = xSSFRow.createCell(j);
                cell.setCellValue(lst.get(j));
                cell.setCellStyle((CellStyle) xSSFCellStyle1);
            }
            XSSFCellStyle xSSFCellStyle2 = worbook.createCellStyle();
            xSSFCellStyle2.setAlignment((short) 1);
            xSSFCellStyle2.setVerticalAlignment((short) 1);
            xSSFCellStyle2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            xSSFCellStyle2.setFillPattern((short) 1);
            xSSFCellStyle2.setBorderBottom((short) 1);
            xSSFCellStyle2.setBorderLeft((short) 1);
            xSSFCellStyle2.setBorderRight((short) 1);
            xSSFCellStyle2.setBorderTop((short) 1);
            XSSFFont xSSFFont2 = worbook.createFont();
            xSSFFont2.setFontName("Calibri");
            xSSFFont2.setBold(false);
            xSSFFont2.setItalic(false);
            xSSFFont2.setFontHeightInPoints((short) 11);
            xSSFCellStyle2.setFont((Font) xSSFFont2);
            for (int row = 1; row <= numRow; row++) {
                xSSFRow = sheet.createRow(row);
                for (int col = 0; col < numCol; col++) {
                    Cell cell = xSSFRow.createCell(col);
                    cell.setCellValue(str[row - 1][col]);
                    cell.setCellStyle((CellStyle) xSSFCellStyle2);
                }
            }
            for (int i = 0; i < numCol; i++) {
                sheet.autoSizeColumn(i);
            }
            worbook.write(file);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void importar(String ruta, ArrayList<String> lst) {
        try {
            int i = 0;
            FileInputStream file = new FileInputStream(new File(ruta));
            Workbook wb = WorkbookFactory.create(file);
            Sheet sh = wb.getSheetAt(0);
            this.totalNoOfRows = sh.getLastRowNum();
            this.totalNoOfCols = lst.size();
            this.especialidad = new String[this.totalNoOfRows];
            this.servicio = new String[this.totalNoOfRows];
            this.diagnostico = new String[this.totalNoOfRows];
            this.motivoConsulta = new String[this.totalNoOfRows];
            this.nombres = new String[this.totalNoOfRows];
            this.apellidoPaterno = new String[this.totalNoOfRows];
            this.apellidoMaterno = new String[this.totalNoOfRows];
            this.fechaAgendamiento = new Date[this.totalNoOfRows];
            DecimalFormat df = new DecimalFormat();
            Cell celda = null;
            for (int row = 0; row < this.totalNoOfRows + 1; row++) {
                if (row >= 1) {
                    for (int col = 0; col < this.totalNoOfCols; col++) {
                        try {
                            celda = sh.getRow(row).getCell(col);
                            switch (col) {
                                case 0:
                                    if (celda != null) {
                                        this.apellidoPaterno[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.apellidoPaterno[row - 1] = "";
                                    break;
                                case 1:
                                    if (celda != null) {
                                        this.apellidoMaterno[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.apellidoMaterno[row - 1] = "";
                                    break;
                                case 2:
                                    if (celda != null) {
                                        this.nombres[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.nombres[row - 1] = "";
                                    break;
                                case 3:
                                    if (celda != null) {
                                        this.especialidad[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.especialidad[row - 1] = "";
                                    break;
                                case 4:
                                    if (celda != null) {
                                        this.servicio[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.servicio[row - 1] = "";
                                    break;
                                case 5:
                                    if (celda != null) {
                                        this.diagnostico[row - 1] = eliminarNotacion(celda.getStringCellValue());
                                        break;
                                    }
                                    this.diagnostico[row - 1] = "";
                                    break;
                                case 6:
                                    if (celda != null) {
                                        this.motivoConsulta[row - 1] = eliminarNotacion(celda.getStringCellValue());
                                        break;
                                    }
                                    this.motivoConsulta[row - 1] = "";
                                    break;
                                case 7:
                                    if (celda != null) {
                                        this.fechaAgendamiento[row - 1] = celda.getDateCellValue();
                                        break;
                                    }
                                    this.fechaAgendamiento[row - 1] = null;
                                    break;
                            }
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void leerCSV(String ruta, ArrayList<String> lst) throws IOException {
        BufferedReader br = null;
        FileReader f = new FileReader(ruta);
        BufferedReader b = new BufferedReader(f);
        while (b.readLine() != null) {
            this.totalNoOfRows++;
        }
        b.close();
        this.totalNoOfCols = lst.size();
        this.especialidad = new String[this.totalNoOfRows - 1];
        this.servicio = new String[this.totalNoOfRows - 1];
        this.diagnostico = new String[this.totalNoOfRows - 1];
        this.motivoConsulta = new String[this.totalNoOfRows - 1];
        this.nombres = new String[this.totalNoOfRows - 1];
        this.apellidoPaterno = new String[this.totalNoOfRows - 1];
        this.apellidoMaterno = new String[this.totalNoOfRows - 1];
        this.fechaAgendamiento = new Date[this.totalNoOfRows - 1];
        try {
            br = new BufferedReader(new FileReader(ruta));
            String line = br.readLine();
            while (null != line) {
                this.datos = line.split(",");
                this.datos = removeTrailingQuotes(this.datos);
                if (this.numLinea == 0) {
                    this.formatoCorrecto = validarImportacionCSV(lst);
                    this.numLinea++;
                } else if (this.numLinea < 1 || this.formatoCorrecto != true) {
                    String str = "El archivo no tiene el formato correcto";
                }
                line = br.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != br) {
                br.close();
            }
        }
    }

    public void importarCSV() {
        try {
            for (int j = 0; j < this.datos.length; j++) {
                try {
                    switch (j) {
                        case 0:
                            this.apellidoPaterno[this.numLinea - 1] = this.datos[0];
                            break;
                        case 1:
                            this.apellidoMaterno[this.numLinea - 1] = this.datos[1];
                            break;
                        case 2:
                            this.nombres[this.numLinea - 1] = this.datos[2];
                            break;
                        case 3:
                            this.especialidad[this.numLinea - 1] = this.datos[3];
                            break;
                        case 4:
                            this.servicio[this.numLinea - 1] = this.datos[4];
                            break;
                        case 5:
                            this.diagnostico[this.numLinea - 1] = eliminarNotacion(this.datos[5]);
                            break;
                        case 6:
                            this.motivoConsulta[this.numLinea - 1] = eliminarNotacion(this.datos[6]);
                            break;
                        case 7:
                            try {
                                this.fechaAgendamiento[this.numLinea - 1] = new Date(this.datos[7]);
                            } catch (Exception e) {
                                this.fechaAgendamiento[this.numLinea - 1] = null;
                            }
                            break;
                    }
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            this.numLinea++;
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static String[] removeTrailingQuotes(String[] fields) {
        String[] result = new String[fields.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = fields[i].replaceAll("^\"", "").replaceAll("\"$", "");
        }
        return result;
    }

    public Date convertStringToDate(String str) {
        Date date = null;
        try {
            date = this.formato.parse(str);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return date;
    }

    public String eliminarNotacion(String str) {
        boolean notacion = false;
        boolean decimal = false;
        int num = 0;
        String temp = "";
        char[] c = str.toCharArray();
        int i;
        for (i = c.length - 1; i > 0; i--) {
            if (c[i] == 'E') {
                notacion = true;
                break;
            }
        }
        if (notacion == true) {
            BigDecimal bdSku = new BigDecimal(String.valueOf(str));
            temp = String.valueOf(bdSku);
        } else {
            temp = str;
            for (i = c.length - 1; i > 0; i--) {
                if (c[i] == '.') {
                    decimal = true;
                    break;
                }
            }
            if (decimal == true) {
                int n = Math.round(Float.parseFloat(str));
                temp = String.valueOf(n);
            }
        }
        return temp;
    }

    public int convertStringToInteger(String str) {
        int num = 0;
        if (str == null || str.equals("")) {
            num = 0;
        } else {
            num = Math.round(Float.parseFloat(str));
        }
        return num;
    }

    public double convertStringToDouble(String str) {
        double num = 0.0D;
        String temp = "";
        if (str != null && !str.equals("")) {
            char[] c = str.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == ',') {
                    c[i] = '.';
                }
                temp = temp + c[i];
            }
            num = Double.valueOf(temp);
        }
        return num;
    }

    public double convertPorcentToDouble(String str) {
        double num = 0.0D;
        String temp = "";
        if (str != null && !str.equals("")) {
            char[] c = str.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] != '%') {
                    temp = temp + c[i];
                }
            }
            num = Double.valueOf(temp);
        }
        return num;
    }

    public String[] getEspecialidad() {
        return this.especialidad;
    }

    public void setEspecialidad(String[] especialidad) {
        this.especialidad = especialidad;
    }

    public String[] getServicio() {
        return this.servicio;
    }

    public void setServicio(String[] servicio) {
        this.servicio = servicio;
    }

    public String[] getDiagnostico() {
        return this.diagnostico;
    }

    public void setDiagnostico(String[] diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String[] getMotivoConsulta() {
        return this.motivoConsulta;
    }

    public void setMotivoConsulta(String[] motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String[] getNombres() {
        return this.nombres;
    }

    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }

    public String[] getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public void setApellidoPaterno(String[] apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String[] getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public void setApellidoMaterno(String[] apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Date[] getFechaAgendamiento() {
        return this.fechaAgendamiento;
    }

    public void setFechaAgendamiento(Date[] fechaAgendamiento) {
        this.fechaAgendamiento = fechaAgendamiento;
    }
}
