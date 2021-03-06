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

@WebServlet(name = "InitDb", urlPatterns = {"/InitDB"})
public class InitDb extends HttpServlet {

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
                ps.executeUpdate("DROP TABLE utilisateurs");
            }
            catch (Exception ex)
            {
                // Table d?j? existante
                System.out.println("La table n'existait pas");
            }
            ps.executeUpdate("CREATE TABLE utilisateurs (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, nom VARCHAR(255),prenom VARCHAR(255), email VARCHAR(255),privilege INT, mdp VARCHAR(255) )");
            ps.executeUpdate("CREATE TABLE service  (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, titre VARCHAR(255),resume VARCHAR(300), categorie VARCHAR(255),unite_loc VARCHAR(255), coup_unite FLOAT )");
            ps.executeUpdate("CREATE TABLE categories  (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, nom VARCHAR(255),resume VARCHAR(100))");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>InitOK</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>DATA BASE INITIALIZED</h1>");
            out.println("<a href='backoffice.html'>return to home</a>");
            
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
