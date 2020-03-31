<%-- 
    Document   : accueil
    Created on : 31 mars 2020, 10:08:09
    Author     : AdminEtu
--%>

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
        %>
        <h1>Bienvenue a toi <%=rs.getString("prenom")%>,<%=rs.getString("nom")%></h1>
        <h1><%=rs.getString("email")%></h1>
    </body>
</html>
