/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package front.verification;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * @author AdminEtu
 */
@WebServlet(name = "InitDatabase", urlPatterns = {"/InitDatabase"})
public class InitDatabase extends HttpServlet {

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
            throws ServletException, IOException, NamingException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        Connection con = null;
        try ( PrintWriter out = response.getWriter()) {
            
            Context initCtx=null;
            try {
                initCtx = new InitialContext();
            } catch (NamingException ex) {
                System.out.println("Erreur de chargement du service de nommage");
            } 
            
            // Connexion ? la base de donn?es enregistr?e dans le serveur de nom sous le nom "sample"
            Object refRecherchee = initCtx.lookup("jdbc/__default");
            DataSource ds = (DataSource)refRecherchee;
            con = ds.getConnection();
            
            // Cr?ation d'une requ?te sans param?tres
            Statement ps = con.createStatement();
            try
            {
                ps.executeUpdate("DROP TABLE utilisateurs");
                ps.executeUpdate("DROP TABLE service");
                ps.executeUpdate("DROP TABLE categories");
            }
            catch (Exception ex)
            {
                // Table d?j? existante
                System.out.println("La table n'existait pas");
            }
            ps.executeUpdate("CREATE TABLE utilisateurs (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, nom VARCHAR(255),prenom VARCHAR(255), email VARCHAR(255),privilege INT, mdp VARCHAR(255) )");
            ps.executeUpdate("CREATE TABLE service  (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, titre VARCHAR(255),resume VARCHAR(300), categorie VARCHAR(255),unite_loc VARCHAR(255), coup_unite FLOAT, userid INT )");
            ps.executeUpdate("CREATE TABLE categories  (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, nom VARCHAR(255),resume VARCHAR(100))");
            
            
            con = ds.getConnection();
            
            // Cr?ation d'une requ?te sans param?tres
            Statement ps1 = con.createStatement();
           
            ps1.executeUpdate("INSERT INTO utilisateurs(nom,prenom,email,privilege,mdp) VALUES ('LE ZERO','Toto','test',0,'toto')");
            ps1.executeUpdate("INSERT INTO utilisateurs(nom,prenom,email,privilege,mdp) VALUES ('AUBRY','Etienne','lebogoss77.com',1,'b')");
            ps1.executeUpdate("INSERT INTO utilisateurs(nom,prenom,email,privilege,mdp) VALUES ('FRANCE','julien','allezlom13@gmail.com',0,'c')");
            
            
            // ps.executeUpdate("INSERT INTO service(titre,resume,categorie,unite_loc,coup_unite) VALUES ('AUBRY','Etienne','lebogoss77.com',1,'b')");
            
            ps1.executeUpdate("INSERT INTO categories (nom,resume) VALUES ('COURS','Ceci est les cours')");
            ps1.executeUpdate("INSERT INTO categories (nom,resume) VALUES ('JARDINAGE','Ceci est le jardinage')");
            ps1.executeUpdate("INSERT INTO categories (nom,resume) VALUES ('BRICOLAGE','Ceci est les bricolage')");
            
            
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet InitDatabase</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet InitDatabase at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");

              request.getSession().setAttribute("id", "");
              response.sendRedirect("login.html");
        }
        finally {
            if (con != null) {
                con.close();
            }
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(InitDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InitDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(InitDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InitDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
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
