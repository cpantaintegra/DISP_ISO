/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Imprimir;

import com.integrasystemsonline.Process.GenerarPDF;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.util.ArrayList;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author User
 */
public class Ticket {
    static ArrayList<String> CabezaLineas=new ArrayList<String>();
    static ArrayList<String> subCabezaLineas=new ArrayList<String>();
    static ArrayList<String> items=new ArrayList<String>();
    static ArrayList<String> totales=new ArrayList<String>();
    static ArrayList<String> LineasPie=new ArrayList<String>();
    static ArrayList<String> lststr= new ArrayList<>();
    static Properties propParam=Utilidades.obtenerProperties("MessageResources.properties");
    
    public static void AddCabecera(String line){
        CabezaLineas.add(line);        
    }
    
    public static void AddSubCabecera(String line){
        subCabezaLineas.add(line);
    }
    
    public static void AddItem(String cantidad,String item,String price){
        OrderItem newItem = new OrderItem(' ');
        items.add(newItem.GeneraItem(cantidad,item, price));
    }
    
    public static void AddTotal(String name,String price){
        OrderTotal newTotal = new OrderTotal(' ');
        totales.add(newTotal.GeneraTotal(name, price));
    }
    
    public static void AddPieLinea(String line){
        LineasPie.add(line);
    }
    
    public static String DibujarLinea(int valor){
        String raya="";for(int x=0;x<valor;x++){raya+="=";}return raya;
    }
    
    public static String DarEspacio(){
        return "\n";
    }
    
    public static String DarTabulacion(){
        return "\t";
    }
    
    public static void ImprimirDocumento(){
        String cadena="";
        String nombreArchivo="1 Factura.pdf";
        String ruta=propParam.getProperty("rutaArchivo")+nombreArchivo;
        for(int cabecera=0;cabecera<CabezaLineas.size();cabecera++ ){
            cadena+=CabezaLineas.get(cabecera);
            lststr.add(CabezaLineas.get(cabecera));
        }
        
        for(int subcabecera=0;subcabecera<subCabezaLineas.size();subcabecera++){
            cadena+=subCabezaLineas.get(subcabecera);
            lststr.add(subCabezaLineas.get(subcabecera));
        }
        
        for(int ITEM=0;ITEM<items.size();ITEM++){
            cadena+=items.get(ITEM);
            lststr.add(items.get(ITEM));
        }
        
        for(int total=0;total<totales.size();total++){
            cadena+=totales.get(total);
            lststr.add(totales.get(total));
        }
        
        for(int pie=0;pie<LineasPie.size();pie++){
            cadena+=LineasPie.get(pie);
            lststr.add(LineasPie.get(pie));
        }

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob pj = service.createPrintJob();
        byte[]bytes =cadena.getBytes();
        Doc doc = new SimpleDoc(bytes, flavor,null);
        
        try{
            GenerarPDF pdf=new GenerarPDF();
            pdf.crearPdf(lststr,ruta);
            pj.print(doc,null);
        }catch(Exception e){ 
            System.out.println(e.toString());
        }
    }
}
