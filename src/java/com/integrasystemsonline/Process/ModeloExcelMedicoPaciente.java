package com.integrasystemsonline.Process;

import com.Entity.IsParametros;
import com.Entity.IsUsuarios;
import com.Session.IsParametrosFacade;
import com.integrasystemsonline.Utilidades.EstiloExcel;
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

public class ModeloExcelMedicoPaciente {

    @EJB
    IsParametrosFacade isParametrosFacade;

    String[] numDocumento;

    String[] tipoDocumento;

    String[] nombres;

    String[] apellidoPaterno;

    String[] apellidoMaterno;

    String[] movil;

    String[] convencional;

    String[] direccion;

    String[] correo;

    String[] ocupacion;

    String[] fechaNacimiento;

    String[] Sexo;

    String[] estadoCivil;

    String[] personaResponable;

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
            IsParametros lstParametros = this.isParametrosFacade.findByCodigo(nombre, usuario.getIdEmpresa().getIdEmpresa(), usuario.getIdCiudad().getIdCiudad(), usuario.getIdSector().getIdSector());
            FileOutputStream file = new FileOutputStream(archivo);
            XSSFWorkbook worbook = new XSSFWorkbook();
            XSSFSheet sheet = worbook.createSheet(nombre);
            XSSFRow xSSFRow = sheet.createRow(0);
            XSSFCellStyle xSSFCellStyle1 = worbook.createCellStyle();
            EstiloExcel objEstilo = new EstiloExcel();
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
            xSSFFont2.setItalic(true);
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
            this.numDocumento = new String[this.totalNoOfRows];
            this.tipoDocumento = new String[this.totalNoOfRows];
            this.nombres = new String[this.totalNoOfRows];
            this.apellidoPaterno = new String[this.totalNoOfRows];
            this.apellidoMaterno = new String[this.totalNoOfRows];
            this.movil = new String[this.totalNoOfRows];
            this.convencional = new String[this.totalNoOfRows];
            this.direccion = new String[this.totalNoOfRows];
            this.correo = new String[this.totalNoOfRows];
            this.ocupacion = new String[this.totalNoOfRows];
            this.fechaNacimiento = new String[this.totalNoOfRows];
            this.Sexo = new String[this.totalNoOfRows];
            this.estadoCivil = new String[this.totalNoOfRows];
            this.personaResponable = new String[this.totalNoOfRows];
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
                                        String strSku = String.valueOf(celda);
                                        this.numDocumento[row - 1] = celda.getStringCellValue();
                                    }
                                    break;
                                case 1:
                                    if (celda != null) {
                                        this.tipoDocumento[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.tipoDocumento[row - 1] = "";
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
                                        this.apellidoPaterno[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.apellidoPaterno[row - 1] = "";
                                    break;
                                case 4:
                                    if (celda != null) {
                                        this.apellidoMaterno[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.apellidoMaterno[row - 1] = "";
                                    break;
                                case 5:
                                    if (celda != null) {
                                        this.movil[row - 1] = eliminarNotacion(celda.getStringCellValue());
                                        break;
                                    }
                                    this.movil[row - 1] = "";
                                    break;
                                case 6:
                                    if (celda != null) {
                                        this.convencional[row - 1] = eliminarNotacion(celda.getStringCellValue());
                                        break;
                                    }
                                    this.convencional[row - 1] = "";
                                    break;
                                case 7:
                                    if (celda != null) {
                                        this.direccion[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.direccion[row - 1] = "";
                                    break;
                                case 8:
                                    if (celda != null) {
                                        this.correo[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.correo[row - 1] = "";
                                    break;
                                case 9:
                                    if (celda != null) {
                                        this.ocupacion[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.ocupacion[row - 1] = "";
                                    break;
                                case 10:
                                    if (celda != null) {
                                        this.fechaNacimiento[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.fechaNacimiento[row - 1] = "";
                                    break;
                                case 11:
                                    if (celda != null) {
                                        this.Sexo[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.Sexo[row - 1] = "";
                                    break;
                                case 12:
                                    if (celda != null) {
                                        this.estadoCivil[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.estadoCivil[row - 1] = "";
                                    break;
                                case 13:
                                    if (celda != null) {
                                        this.personaResponable[row - 1] = celda.getStringCellValue();
                                        break;
                                    }
                                    this.personaResponable[row - 1] = "";
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
        this.numDocumento = new String[this.totalNoOfRows - 1];
        this.tipoDocumento = new String[this.totalNoOfRows - 1];
        this.nombres = new String[this.totalNoOfRows - 1];
        this.apellidoPaterno = new String[this.totalNoOfRows - 1];
        this.apellidoMaterno = new String[this.totalNoOfRows - 1];
        this.movil = new String[this.totalNoOfRows - 1];
        this.convencional = new String[this.totalNoOfRows - 1];
        this.direccion = new String[this.totalNoOfRows - 1];
        this.correo = new String[this.totalNoOfRows - 1];
        this.ocupacion = new String[this.totalNoOfRows - 1];
        this.fechaNacimiento = new String[this.totalNoOfRows - 1];
        this.Sexo = new String[this.totalNoOfRows - 1];
        this.estadoCivil = new String[this.totalNoOfRows - 1];
        this.personaResponable = new String[this.totalNoOfRows - 1];
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
                            this.numDocumento[this.numLinea - 1] = this.datos[0];
                            break;
                        case 1:
                            this.tipoDocumento[this.numLinea - 1] = this.datos[1];
                            break;
                        case 2:
                            this.nombres[this.numLinea - 1] = this.datos[2];
                            break;
                        case 3:
                            this.apellidoPaterno[this.numLinea - 1] = this.datos[3];
                            break;
                        case 4:
                            this.apellidoMaterno[this.numLinea - 1] = this.datos[4];
                            break;
                        case 5:
                            this.movil[this.numLinea - 1] = eliminarNotacion(this.datos[5]);
                            break;
                        case 6:
                            this.convencional[this.numLinea - 1] = eliminarNotacion(this.datos[6]);
                            break;
                        case 7:
                            this.direccion[this.numLinea - 1] = this.datos[7];
                            break;
                        case 8:
                            this.correo[this.numLinea - 1] = this.datos[8];
                            break;
                        case 9:
                            this.ocupacion[this.numLinea - 1] = this.datos[9];
                            break;
                        case 10:
                            this.fechaNacimiento[this.numLinea - 1] = this.datos[10];
                            break;
                        case 11:
                            this.Sexo[this.numLinea - 1] = this.datos[11];
                            break;
                        case 12:
                            this.estadoCivil[this.numLinea - 1] = this.datos[12];
                            break;
                        case 13:
                            this.personaResponable[this.numLinea - 1] = this.datos[13];
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
        double num = 0;
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

    public String[] getNumDocumento() {
        return this.numDocumento;
    }

    public void setNumDocumento(String[] numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String[] getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(String[] tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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

    public String[] getMovil() {
        return this.movil;
    }

    public void setMovil(String[] movil) {
        this.movil = movil;
    }

    public String[] getConvencional() {
        return this.convencional;
    }

    public void setConvencional(String[] convencional) {
        this.convencional = convencional;
    }

    public String[] getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String[] direccion) {
        this.direccion = direccion;
    }

    public String[] getCorreo() {
        return this.correo;
    }

    public void setCorreo(String[] correo) {
        this.correo = correo;
    }

    public String[] getOcupacion() {
        return this.ocupacion;
    }

    public void setOcupacion(String[] ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String[] getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String[] fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String[] getSexo() {
        return this.Sexo;
    }

    public void setSexo(String[] Sexo) {
        this.Sexo = Sexo;
    }

    public String[] getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(String[] estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String[] getPersonaResponable() {
        return this.personaResponable;
    }

    public void setPersonaResponable(String[] personaResponable) {
        this.personaResponable = personaResponable;
    }
}
