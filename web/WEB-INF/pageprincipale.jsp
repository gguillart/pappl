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
    <style> <%@ include file="style.css"%>  </style>
    <title>EDT</title>
    
</head>

<body>

<h1>Service de gestion des emplois du temps</h1>

<p> Bonjour </p>

  <%
        Formulaire logs = (Formulaire) request.getAttribute("auth");
        if (logs!=null ){out.println("Bonjour "+session.getAttribute("login")+", "+logs.getResultat());}
      %> 

<%@ include file="menu.jsp" %> 



<table>

   <tr>
       <th> </th>
       <th>Lundi</th> <%-- ajouter du code java pour récupérer le numéro du jour du mois --%>
       <th>Mardi</th>
       <th>Mercredi</th>
       <th>Jeudi</th>
       <th>Vendredi</th>
   </tr>
   <tr>
       <td>Matin</td>
       <td>Cours</td>
       <td>Cours</td>
       <td>Cours</td>
       <td>Cours</td>
       <td>Cours</td>
   </tr>
   <tr>
       <td>Après-midi</td>
       <td>Cours</td>
       <td>Cours</td>
       <td>Cours</td>
       <td>Cours</td>
       <td>Cours</td>
   </tr>
</table>


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

