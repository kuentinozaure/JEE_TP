/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package front.verification;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "Auth", urlPatterns = {"/Auth"})
public class Verification extends HttpServlet {

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
            throws ServletException, IOException, SQLException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        
        Connection con = null;
        try ( PrintWriter out = response.getWriter()) {
            
            String email = request.getParameter("login");
            String mdp = request.getParameter("mdp");
            
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
            con = ds.getConnection();

            PreparedStatement prep1 = con.prepareStatement("SELECT COUNT(*) as TOTAL FROM utilisateurs where mdp=? and email=?"); 
            prep1.setString(1, mdp);
            prep1.setString(2, email);
            
            ResultSet rs = prep1.executeQuery();
            rs.next();
            
            RequestDispatcher dispatcher;
            if (rs.getInt("TOTAL") == 1) {
                request.getSession().setAttribute("identifiant", email);
                
                request.getSession().setAttribute("id", "ok");
                
                dispatcher = request.getRequestDispatcher("accueil.jsp");
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Login</title>");           
//                out.println("</head>");
//                out.println("<body>");
//                out.println();
//                out.println("<a href='front/accueil.jsp'>go to the homepage</a>");
//                out.println("<a href='front/login.html'>return to home</a>");
//                out.println();
//                out.println("<h1></h1>");
//                out.println("</body>");
//                out.println("</html>");
            } else {
                 request.getSession().setAttribute("id", "");
                 dispatcher = request.getRequestDispatcher("BadConnection.html");
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>mauvais login</title>");           
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<h1>Mauvais login</h1>");
//                out.println("<a href='front/login.html'>return to home</a>");
//                out.println();
//                out.println("<h1></h1>");
//                out.println("</body>");
//                out.println("</html>");
            }
            dispatcher.forward(request, response);
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
        } catch (SQLException ex) {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
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
