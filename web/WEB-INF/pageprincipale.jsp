<%-- 
    Document   : pageprincipale
    Created on : 22 déc. 2014, 19:32:16
    Author     : Geoffrey
--%>

<%@page import="com.ocpappl.formulaires.Formulaire"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EDT</title>
</head>

<body>

<h1>Service de gestion des emplois du temps</h1>

  <%
        Formulaire prop = (Formulaire) request.getAttribute("auth");
        if (prop!=null){out.println(prop.getResultat());}
      %> 

<%@ include file="menu.jsp" %> 


<%--

 <%
    String name = (String) request.getAttribute("name");
    out.println(name);
    %>
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

