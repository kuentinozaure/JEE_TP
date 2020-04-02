<%-- 
    Document   : newjspcreation_service.jsp
    Created on : 2 avr. 2020, 14:10:01
    Author     : AdminEtu
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>

<%
     String id = (String)request.getSession().getAttribute("id");
            if (id == null) {
                response.sendRedirect(getServletConfig().getServletContext().getContextPath() + "/login.html");
            } else {
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
            
            PreparedStatement prep1 = con.prepareStatement("SELECT * FROM categories"); 
            
            ResultSet rs = prep1.executeQuery();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Service creation</title>
    </head>
    <body>
        <form action="NewService" method="post">
            <div>
                <label for="titre">Titre :</label>
                <input type="text" id="titre" name="titre">
            </div>
            <div>
                <label for="resume">Resume :</label>
                <input type="text" id="resume" name="resume">
            </div>
            <br/>
            <div>
                <label for="categorie">Categorie :</label>
                <select name="categorie" id="categorie">
                <%
                  while (rs.next()){
                %>
                
                    <option value="<%=rs.getString("nom")%>"><%=rs.getString("nom")%></option>
                <%}%>
                </select>
                
            </div>
            <br/>
            <div>
                <label for="uniteloc">Unite de location :</label>
                <input type="text" id="uniteloc" name="uniteloc">
            </div>
            <br/>
            <div>
                <label for="coupunit">Coup unite :</label>
                <input type="text" id="coupunit" name="coupunit">
            </div>
            <button type='submit'>Creer</button>
        </form>
    </body>
</html>
<%
    }
%>