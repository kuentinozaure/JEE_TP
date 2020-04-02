<%-- 
    Document   : Reservation
    Created on : 2 avr. 2020, 17:56:43
    Author     : AdminEtu
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        
        <a href='accueil.jsp'>Accueil</a>
        
            <%
    String id = (String)request.getSession().getAttribute("id");
    if (id == null) {
       response.sendRedirect(getServletConfig().getServletContext().getContextPath() + "/login.html");
    }
    else {
        ServletContext servletContext = getServletConfig().getServletContext();

        String dataSourceName = "DATA_SOURCE";
        if (servletContext.getAttribute(dataSourceName) == null) {
            Context initCtx=null;
            Object refRecherchee = null;
            try {
                initCtx = new InitialContext();
                refRecherchee = initCtx.lookup("jdbc/__default");
            } catch (NamingException ex) {
                System.out.println("Erreur de chargement du service de nommage");
            }

            DataSource dataSource = (DataSource) refRecherchee;

            servletContext.setAttribute(dataSourceName, dataSource);
        }

        DataSource dataSourceContext = (DataSource) servletContext.getAttribute(dataSourceName);
        System.out.println(dataSourceContext);
        Connection con = null;

        try {
                con = dataSourceContext.getConnection();

                con = dataSourceContext.getConnection();
                PreparedStatement psSer = con.prepareStatement("SELECT * FROM service;");
                ResultSet rsSer = psSer.executeQuery();
                
                String temp = request.getParameter("temp");
                
                String res = request.getParameter("reservation");
                
%>
        <form action="">
            <div>
                <label for="temp">temp reservation</label>
                <input id="temp" name="temp" type="number" value="">
            </div>
            <br/>
            <label for="reservation">Reservation :</label>
                <select name="reservation" id="reservation">
                <%
                  while (rsSer.next()){
                %>
                
                    <option value="<%=rsSer.getString("coup_unite")%>"><%=rsSer.getString("titre")%></option>
                <%}%>
                </select>
                <br/>
            <button type="submit">Checker votre reservation</button>
        </form>
                <%

                    if (res != null || temp != null) {
                        Float reservation = Float.parseFloat(res);
                        Float temppasse = Float.parseFloat(temp);
                        Float total = reservation * temppasse;
                        
                %>
                    <h1>
                        Vous en aurez pour <%=total%>
                    </h1>
                <%
                    }
                %>
    </body>
    <%
    }
    catch(Exception e) {
    }
    }
%>
</html>
