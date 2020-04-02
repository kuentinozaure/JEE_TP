<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %><%--
  
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recherche</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

<h1>Rechercher</h1>

<a href="accueil.jsp">Accueil</a>

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

        String titre = request.getParameter("titre");
        String resume = request.getParameter("resume");
        String unite = request.getParameter("unite");
        String cout = request.getParameter("cout");


        con = dataSourceContext.getConnection();
        PreparedStatement psCat = con.prepareStatement("SELECT * FROM categories;");
        ResultSet rsCat = psCat.executeQuery();

        %>
<br/>
<form action="" >
    <div><label for="titre">Titre</label><input id="titre" name="titre" type="text" value=""></div>
    <div><label for="resume">Resume</label><input id="resume" name="resume" type="text" value=""></div>
    <div ><label for="unite">Unité</label><input id="unite" name="unite" type="text" value=""></div>
    <div ><label for="cout">Cout</label><input id="cout" name="cout" type="number" value=""></div>
    <button type="submit">Valider</button>
</form>
<br/>
        <%

        PreparedStatement ps = con
                .prepareStatement("SELECT s.ID AS ID, s.titre AS TITRE, s.resume AS RESUME, " +
                        "s.unite_loc AS UNITE, s.coup_unite AS COUT " +
                        "FROM service AS s" +
                        " WHERE s.titre LIKE ? " +
                        "AND s.resume LIKE ? " +
                        "AND s.unite_loc LIKE ? " +
                        "AND s.coup_unite LIKE ? ;");
        ps.setString(1, (titre == null ? "%": "%" + titre + "%"));
        ps.setString(2, (resume == null ? "%" : "%" + resume + "%"));
        ps.setString(3, (unite == null ? "%" : "%" + unite + "%"));
        ps.setString(4, (cout == null ? "%" : "%" + cout + "%"));
        ResultSet rs = ps.executeQuery();
%>
<br/>
<table>
    <tr>
        <th>TITRE</th>
        <th>RESUME</th>
        <th>UNITE</th>
        <th>COUT</th>
    </tr>
    <%
        while(rs.next()){
    %>
    <tr>
        <td><%= rs.getString("TITRE") %></td>
        <td><%= rs.getString("RESUME") %></td>
        <td><%= rs.getString("UNITE") %></td>
        <td><%= rs.getString("COUT") %></td>
        
        <!--<td><a href="serviceDelete?service_id=<%= rs.getString("ID")%>">Supprimer</a></td>-->
    </tr>
    <%
        }
    %>
</table>
<%
    
        //        ResultSet rs = ps.executeQuery();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
%>
</body>
</html>
