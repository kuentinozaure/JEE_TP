<%-- 
    Document   : accueil
    Created on : 31 mars 2020, 10:08:09
    Author     : AdminEtu
--%>

<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Page display user content</title>
    </head>
    <body>
        <%
            String id = (String)request.getSession().getAttribute("id");
            if (id == null) {
                response.sendRedirect(getServletConfig().getServletContext().getContextPath() + "/login.html");
            } else {
            
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
            
            PreparedStatement prep1 = con.prepareStatement("SELECT * FROM utilisateurs where email=?"); 
            prep1.setString(1, (String) request.getSession().getAttribute("identifiant").toString());
            
            ResultSet rs = prep1.executeQuery();
            rs.next();
            
            request.getSession().setAttribute("userId", rs.getString("ID"));
            
            
        %>
        <a href='creation_service.jsp'>Creation de service</a>
        <a href='Reservation.jsp'>Reservation</a>
        <a href='recherche.jsp'>Recherche</a>
        
        <h1>Bienvenue a toi <%=rs.getString("prenom")%>,<%=rs.getString("nom")%></h1>
        <h1><%=rs.getString("email")%></h1>
        
        <%
            Statement prep2 = con.createStatement(); 
            ResultSet rsservice = prep2.executeQuery("SELECT * FROM service where userid="+Integer.parseInt(request.getSession().getAttribute("userId").toString()));
        %>
        
        <table>
            <thead>
                <tr>
                     Titre
                </tr>
                <tr>
                     Resume
                </tr>
                <tr>
                     Categorie
                </tr>
                <tr>
                     Unite de location
                </tr>
                <tr>
                    Action
                </tr>
            </thead>
            <tbody>
                <%
                  while (rsservice.next()){
                %>
                    <tr>
                        <td><%=rsservice.getString("titre")%></td>
                        <td><%=rsservice.getString("resume")%></td>
                        <td><%=rsservice.getString("categorie")%></td>
                        <td><%=rsservice.getString("unite_loc")%></td>
                        <td><%=rsservice.getString("coup_unite")%></td>
                        <td>
                            <form action="deleteService" method="POST">
                                <button  type="submit">x</button>
                                <input type="hidden" name="idtodelete" id="idtodelete" value="<%=rsservice.getString("ID")%>">
                            </form>
                        </td>
                    </tr>
                <%}%>
            </tbody>
        </table>
    </body>
</html>

<%
    con.close();
    }
%>
