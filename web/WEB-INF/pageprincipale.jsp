<%-- 
    Document   : pageprincipale
    Created on : 22 déc. 2014, 19:32:16
    Author     : Geoffrey
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>

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


        <%
            Formulaire logs = (Formulaire) request.getAttribute("auth");
            if (logs != null) {
                out.println("Bonjour " + session.getAttribute("login") + ", " + logs.getResultat());
            }
        %> 

        <%@ include file="menu.jsp" %> 


        <p>   <%
            java.util.Date dateAccueil = new java.util.Date();
            Locale locale = Locale.getDefault();
            DateFormat dateFormatLong = DateFormat.getDateInstance(DateFormat.FULL, locale);
            DateFormat dateFormatSemaine = new SimpleDateFormat("w");
            DateFormat dateFormatJourSemaine = new SimpleDateFormat("E");
            DateFormat dateFormatJourMois = new SimpleDateFormat("dd'/'MM'/'yy");
            out.println("Nous sommes le " + dateFormatLong.format(dateAccueil));

            %>   
        </p>


        <p>
            <%  Date dateCourante = new java.util.Date();
                Date dateCurseur = new java.util.Date();
                    GregorianCalendar calendar = new java.util.GregorianCalendar();
                    calendar.setTime(dateCourante);
                    String numSemActuS = dateFormatSemaine.format(dateCourante);
                    String numSemS = request.getParameter("week");
                    int numSemActu = Integer.parseInt(numSemActuS);
                    int numSem = numSemActu;
                    if (request.getParameter("week") != null) {

                        numSem = Integer.parseInt(numSemS);

                        if (numSem > numSemActu) {
                            int k = 7 * (numSem - numSemActu);
                            calendar.add(calendar.DATE, k);
                        } else {
                            int k = 7 * (numSemActu - numSem);
                            calendar.add(calendar.DATE, -k);
                        }

                        dateCurseur = calendar.getTime();
                    } 
                    else {
                        dateCurseur = dateCourante;
                    }
                
                
                

                String jourSemaine = dateFormatJourSemaine.format(dateCourante);
                String jourMois = dateFormatJourMois.format(dateCourante);

                Date dateLundi = new java.util.Date();
                Date dateMardi = new java.util.Date();
                Date dateMercredi = new java.util.Date();
                Date dateJeudi = new java.util.Date();
                Date dateVendredi = new java.util.Date();

                switch (jourSemaine

                
                

                ) {
                    case "lun.":
                        dateLundi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMardi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMercredi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateJeudi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateVendredi = calendar.getTime();
                        break;
                    case "mar.":

                        calendar.add(calendar.DATE, -1);
                        dateLundi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMardi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMercredi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateJeudi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateVendredi = calendar.getTime();
                        break;
                    case "mer.":
                        calendar.add(calendar.DATE, -2);
                        dateLundi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMardi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMercredi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateJeudi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateVendredi = calendar.getTime();
                        break;
                    case "jeu.":
                        calendar.add(calendar.DATE, -3);
                        dateLundi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMardi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMercredi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateJeudi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateVendredi = calendar.getTime();
                        break;
                    case "ven.":
                        calendar.add(calendar.DATE, -4);
                        dateLundi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMardi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateMercredi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateJeudi = calendar.getTime();
                        calendar.add(calendar.DATE, +1);
                        dateVendredi = calendar.getTime();
                        break;
                }
            %>   
        </p> 


        <p>
            <% out.println (

                "numéro de semaine actuelle : "+numSemActu+"\n");%>
            <br>
            <% out.println (

                "numéro de semaine de la visualisation : "+numSem); %>

        </p>

        <p>
            <a href="/Pappl/PagePrincipale?week=<%if (numSem+1==53) {calendar.add(calendar.DATE, +7);
                    dateCurseur=calendar.getTime();
                    numSemS = dateFormatSemaine.format(dateCurseur);
                    numSem = Integer.parseInt(numSemS);
                    out.println(numSem);
                }

                
                    else {
                out.println (numSem + 1);
                        }        %>">Semaine Suivante</a>
        </p>
        <p>
            <a href="/Pappl/PagePrincipale?week=<%if (numSem-1==0) {calendar.add(calendar.DATE, -7);
                    dateCurseur=calendar.getTime();
                    numSemS = dateFormatSemaine.format(dateCurseur);
                    numSem = Integer.parseInt(numSemS);
                    out.println(numSem);
                }

                
                    else {
                out.println(numSem - 1);
                }
               %>">Semaine Précédente</a>
        </p>

        <table>

            <tr>
                <th> </th>
                <th>Lundi <% out.println (dateFormatJourMois.format

                    (dateLundi)); %>  </th> 
                <th>Mardi <% out.println (dateFormatJourMois.format

                    (dateMardi)); %></th>
                <th>Mercredi <% out.println (dateFormatJourMois.format

                    (dateMercredi)); %></th>
                <th>Jeudi <% out.println (dateFormatJourMois.format

                    (dateJeudi)); %></th>
                <th>Vendredi <% out.println (dateFormatJourMois.format
                (dateVendredi));%></th>
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

    </body>
</html>

