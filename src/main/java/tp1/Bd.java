/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author JCS
 */
@WebServlet(name = "ServletTestCreation", urlPatterns = {"/ServletTestBDCreation"})
public class Bd extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            
            // Chargement du service de nommage
            Context initCtx=null;
            try {
                initCtx = new InitialContext();
            } catch (NamingException ex) {
                System.out.println("Erreur de chargement du service de nommage");
            } 
            
            // Connexion ? la base de donn?es enregistr?e dans le serveur de nom sous le nom "sample"
            Object refRecherchee = initCtx.lookup("jdbc/__default");
            DataSource ds = (DataSource)refRecherchee;
            Connection con = ds.getConnection();
            
            // Cr?ation d'une requ?te sans param?tres
            Statement ps = con.createStatement();
            try
            {
                ps.executeUpdate("DROP TABLE CLIENT");
            }
            catch (Exception ex)
            {
                // Table d?j? existante
                System.out.println("La table n'existait pas");
            }
            ps.executeUpdate("CREATE TABLE CLIENT (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, NOM VARCHAR(30) NOT NULL UNIQUE)"); // On fait un select dans la table CUSTOMER

            ps.executeUpdate("INSERT INTO CLIENT(NOM) VALUES ('DUPOND')");
            ps.executeUpdate("INSERT INTO CLIENT(NOM) VALUES ('DURANT')");

            Statement ps2 = con.createStatement();
            ResultSet rs = ps2.executeQuery("select * from CLIENT"); // On fait un select dans la table CLIENT

            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletTestBD</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletTestBD at " + request.getContextPath() + "</h1>");
            
            // On passe toutes les lignes de la table et on affiche le champ NAME
            while (rs.next())
                out.println("<p>"+rs.getString("NOM")+"</p>");
            
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            //Logger.getLogger(ServletTestBDCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            //Logger.getLogger(ServletTestBDCreation.class.getName()).log(Level.SEVERE, null, ex);
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

}