/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.Servlets;

import com.Session.IsParametrosFacade;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.eclipse.persistence.sessions.DatabaseLogin;

/**
 *
 * @author USER
 */
public class ServletOrden extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Inject
    private IsParametrosFacade isParametrosFacade;

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            generaReporte(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletAgendamiento.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void generaReporte(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException, SQLException {
        JasperReport reports = null;
        Connection _conn = null;
        try {
            String agendamientoIDStr = request.getParameter("agendamientoID");
            int agendamientoID = Integer.parseInt(agendamientoIDStr);
            String ruta = request.getRealPath("/Reportes/ordenExamenReport.jasper");
            File file = new File(ruta);
            DatabaseLogin login = this.isParametrosFacade.getSession().getLogin();
            _conn = (Connection) login.connectToDatasource(this.isParametrosFacade.getSession().getDatasourceLogin().buildAccessor(), this.isParametrosFacade.getSession());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("agendamientoID", agendamientoID);
            reports = (JasperReport) JRLoader.loadObject(file);
            byte[] bytes = null;
            bytes = JasperRunManager.runReportToPdf(ruta, parameters, _conn);
            ServletOutputStream sOI = response.getOutputStream();
            response.addHeader("Content-Disposition", "inline; filename=Orden_Examen.pdf");
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            sOI.write(bytes, 0, bytes.length);
            sOI.flush();
            sOI.close();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.toString());
        }
    }
}
