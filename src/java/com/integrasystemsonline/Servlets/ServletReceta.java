package com.integrasystemsonline.Servlets;

import com.Session.IsParametrosFacade;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.File;
import java.io.IOException;
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

public class ServletReceta extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public void generaReporte(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException, SQLException {
        JasperReport reports = null;
        Connection _conn = null;
        try {
            String recetaIDStr = request.getParameter("recetaID");
            int recetaID = Integer.parseInt(recetaIDStr);
            String ruta = request.getRealPath("/Reportes/recetaReport.jasper");
            File file = new File(ruta);
            DatabaseLogin login = this.isParametrosFacade.getSession().getLogin();
            _conn = (Connection) login.connectToDatasource(this.isParametrosFacade.getSession().getDatasourceLogin().buildAccessor(), this.isParametrosFacade.getSession());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("recetaID", recetaID);
            reports = (JasperReport) JRLoader.loadObject(file);
            byte[] bytes = null;
            bytes = JasperRunManager.runReportToPdf(ruta, parameters, _conn);
            ServletOutputStream sOI = response.getOutputStream();
            response.addHeader("Content-Disposition", "inline; filename=Receta.pdf");
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
