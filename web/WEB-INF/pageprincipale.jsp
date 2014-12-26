<%-- 
    Document   : pageprincipale
    Created on : 22 déc. 2014, 19:32:16
    Author     : Geoffrey
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EDT</title>
</head>

<body>

<h1>Service de gestion des emplois du temps</h1>

<%@ include file="menu.jsp" %> 



<%--
<%
    String heure = (String) request.getAttribute("heure");
    if (heure.equals("jour")) {
        out.println("Bonjour");
    } else {
        out.println("Bonsoir");
    }
%>

<p>
    <%
        for (int i = 0; i < 3; i++) {
            out.println("Une nouvelle ligne !<br />");
        }
    %>
</p>
--%>

</body>
</html>

