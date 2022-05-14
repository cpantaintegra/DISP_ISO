package com.integrasystemsonline.Utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;

public class Utilidades {
  public static void main(String[] args) throws Throwable {
    Properties propParam = obtenerProperties("MessageResources.properties");
    String resp = "";
    try {
      String keyaes = propParam.getProperty("keyaes");
      System.out.println(keyaes);
    } catch (Exception exception) {}
  }
  
  public static Properties obtenerProperties(String propName) {
    Properties prop = new Properties();
    FileInputStream id = null;
    try {
      //id = new FileInputStream("/home/DispConfig/"+propName);//servidor a la nube 
        id = new FileInputStream("C:/DispConfig/"+propName);//servidor local
        prop.load(id);
    } catch (Exception exception) {
      try {
        id.close();
      } catch (IOException ex) {
        Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, (String)null, ex);
      } 
    } finally {
      try {
        id.close();
      } catch (IOException ex) {
        Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, (String)null, ex);
      } 
    } 
    return prop;
  }
  
  public static Date obtenerFechaZonaHoraria(Date fecha, String timeDif, String timeZone) {
    DateTime fechaActualPlus = new DateTime(fecha.getTime());
    fecha = fechaActualPlus.plusSeconds(Integer.parseInt(timeDif)).toDate();
    Instant instant = fecha.toInstant();
    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    Instant instantAfter = localDateTime.atZone(ZoneId.of(timeZone)).toInstant();
    fecha = Date.from(instantAfter);
    return fecha;
  }
  
  public static Properties obtenerPropertiesSinRuta(String propName) {
    Properties prop = new Properties();
    FileInputStream id = null;
    String url = "";
    int lg = 0;
    try {
      url = Utilidades.class.getProtectionDomain().getCodeSource().getLocation().toString();
      lg = url.length() - 16;
      url = url.substring(6, lg);
      id = new FileInputStream(url + propName);
      prop.load(id);
    } catch (Exception exception) {
      try {
        id.close();
      } catch (IOException ex) {
        Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, (String)null, ex);
      } 
    } finally {
      try {
        id.close();
      } catch (IOException ex) {
        Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, (String)null, ex);
      } 
    } 
    return prop;
  }
  
  public static Date getRestaDias(Calendar fecha, int dias) {
    Calendar cal = Calendar.getInstance();
    cal.get(1);
    cal.add(5, -dias);
    return cal.getTime();
  }
  
  public static String plantillaCambioClave(String usuario, String clave) {
    String cab = "<tr>\n"
            + "<th bgcolor='#DDE0FC'>Usuario</th> \n"
            + "<th>" + usuario + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC'>Clave</th> \n"
            + "<th>" + clave + "</th> \n"
            + "</tr>";
    return cab;
  }
  
  public static String plantillaAgendamientoCliente(String cliente, String fechaDesde, String fechaHasta, String servicio, String medico, String cedula) {
    String cab = "<tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Paciente</th> \n"
            + "<th style='text-align: right'>" + cliente + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Fecha Desde</th> \n"
            + "<th style='text-align: right'>" + fechaDesde + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Fecha Hasta</th> \n"
            + "<th style='text-align: right'>" + fechaHasta + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Servicio</th> \n"
            + "<th style='text-align: right'>" + servicio + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Medico</th> \n"
            + "<th style='text-align: right'>" + medico + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Cedula medico</th> \n"
            + "<th style='text-align: right'>" + cedula + "</th> \n"
            + "</tr>";
    return cab;
  }
  
  public static String plantillaAgendamientoMedico(String cliente, String fechaDesde, String fechaHasta, String servicio) {
    String cab = "<tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Paciente</th> \n"
            + "<th style='text-align: right'>" + cliente + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Fecha Desde</th> \n"
            + "<th style='text-align: right'>" + fechaDesde + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Fecha Hasta</th> \n"
            + "<th style='text-align: right'>" + fechaHasta + "</th> \n"
            + "</tr><tr>\n"
            + "<th bgcolor='#DDE0FC' style='text-align: left'>Servicio</th> \n"
            + "<th style='text-align: right'>" + servicio + "</th> \n"
            + "</tr>";
    return cab;
  }
  
  public static String plantillas(String usuario, String cab, String datos, String motivo) {
    String plantilla = "<table align='center' border='0' cellpadding='0' cellspacing='0'><tbody>\n"
            + "            <tr style='height:159.7pt'>\n"
            + "             <td height='213' style='width:500.7pt;border:solid gray 1.5pt;border-bottom:none;padding:0cm 5.4pt 0cm 5.4pt;height:159.7pt' valign='top' width='516'>\n"
            + "              <p><br />\n"
            + "               <font face='Arial' size='2'>Estimado(a):</font>\n"
            + "               <b><font face='Arial' size='2'> " + usuario + " </font></b>\n"
            + "              </p>\n"
            + "              <p>\n"
            + "               <font face='Arial' size='2'>Reciba un cordial saludo del\n"
            + "                <b><span style='font-weight:bold'> Sistema Medico DispISO </span></b>.\n"
            + "                Este e-mail es una notificaci&oacute;n autom&aacute;tica " + motivo + ".\n"
            + "               </font>\n"
            + "              </p>\n"
            + "               <TABLE BORDER align=center>\n"
            + cab + datos 
            + "               </TABLE>\n"
            + "              <p><font face='Arial' size='2'>Saludos  cordiales,</font></p>\n"
            + "              <font face='Arial' size='2'>\n"
            + "               <strong>Administrador del Sistema</strong>\n"
            + "               <br />Sistema Medico<br/>DispISO\n"
            + "              </font>\n"
            + "             </td>\n"
            + "            </tr>\n"
            + "            <tr>\n"
            + "             <td bgcolor='#FFFF99' style='width:386.7pt;border:solid gray 1.5pt;border-top:none;background:#FFFF99;padding:0cm 5.4pt 0cm 5.4pt' valign='top' width='516'>&nbsp;</td>\n"
            + "            </tr>\n"
            + "            <tr>\n"
            + "             <td bgcolor='red' style='width:386.7pt;border:solid gray 1.5pt;border-top:none;background:#C2191E;padding:0cm 5.4pt 0cm 5.4pt' valign='top' width='516'>\n"
            + "              <p align='center' style='text-align:center'>\n"
            + "               <span style='font-size:7.0pt;font-family:Arial;color:white;font-weight:bold'>LA INFORMACI&Oacute;N CONTENIDA EN EL PRESENTE CORREO ES CONFIDENCIAL Y PARA USO EXCLUSIVO DEL DESTINATARIO ORIGINAL RECUERDE USTED NO PUEDE RESPONDER ESTE CORREO</span>\n"
            + "              </p>\n"
            + "             </td>\n"
            + "            </tr>\n"
            + "            </tbody>"
            + "</table>";
    return plantilla;
  }
  
  public static String generarClave() {
    String claveRandom = "";
    char[] chr = { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', 
        '4', '5', '6', '7', '8', '9' };
    char[] aleatorio = new char[36];
    for (int i = 0; i < 6; i++) {
      aleatorio[i] = chr[(int)(Math.random() * 36)];
      claveRandom = claveRandom + aleatorio[i];
    } 
    return claveRandom;
  }
}