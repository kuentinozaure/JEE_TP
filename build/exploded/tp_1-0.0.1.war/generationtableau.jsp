<%-- 
    Document   : generationtableau
    Created on : 23 mars 2020, 17:57:45
    Author     : AdminEtu
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <table>
            <tr>
                <td>Euro</td>
                <td>Dollar</td>
            </tr>
            <%
                Integer premier = Integer.parseInt(request.getParameter("premier"));
                Integer dernier = Integer.parseInt(request.getParameter("dernier"));
                Integer pas = Integer.parseInt(request.getParameter("pas"));

                for(int i=premier; i<= dernier; i= i+pas) {
                           out.println("<tr>");
                            out.println("<td>");
                            out.println(i);
                            out.println("</td>");
                            out.println("<td>");
                            out.println(i*(1.08));
                            out.println("</td>");
                            out.println("</tr>");
                 }
            %>
        </table>

    </body>
</html>
