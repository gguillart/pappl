<%-- 
    Document   : pageprincipale
    Created on : 22 d�c. 2014, 19:32:16
    Author     : Geoffrey
--%>



<%@page import="java.util.Calendar"%>
<%@page import="com.ocpappl.servlets.PagePrincipale"%>
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
            DateFormat dateFormatAnnee = new SimpleDateFormat("yy");
            out.println("Nous sommes le " + dateFormatLong.format(dateAccueil));

            %>   
        </p>


        <p>
            <%  Date dateCourante = new java.util.Date();
                GregorianCalendar calendar = new java.util.GregorianCalendar();
                calendar.setTime(dateCourante);
                int numSemActu = calendar.get(Calendar.WEEK_OF_YEAR);
                int AnneeActu = calendar.get(Calendar.YEAR);
                int numSem = calendar.get(Calendar.WEEK_OF_YEAR);
                int Annee = calendar.get(Calendar.YEAR);
               
                if (request.getAttribute("week") != null && request.getAttribute("year") != null) {
                numSem = Integer.parseInt((String) request.getAttribute("week")); 
                Annee = Integer.parseInt((String) request.getAttribute("year"));
                }
                
               /* tentative de blocage du probl�me */
                
                /*  if (numSem==numSemActu && Annee <AnneeActu) {
                    calendar.add(Calendar.WEEK_OF_YEAR,-51); 
                     while (calendar.get(Calendar.WEEK_OF_YEAR) != numSem && calendar.get(Calendar.YEAR)==Annee) {
                         calendar.add(Calendar.WEEK_OF_YEAR,-1);
                     }
                }
                
                 
                if (numSem==numSemActu && Annee >AnneeActu) {
                    calendar.add(Calendar.WEEK_OF_YEAR,+51); 
                     while (calendar.get(Calendar.WEEK_OF_YEAR) != numSem && calendar.get(Calendar.YEAR)==Annee) {
                         calendar.add(Calendar.WEEK_OF_YEAR,+1);
                     }
                } */
               
           
             
                    if(Annee > AnneeActu /* && numSem != numSemActu*/) {out.println("blabla1");
                            
                        while (calendar.get(Calendar.WEEK_OF_YEAR) != numSem) {
                            calendar.add(Calendar.WEEK_OF_YEAR, +1); 
                            
                        }
                        
                        
                    } else if (Annee < AnneeActu /* && numSem != */) {out.println("blabla2");
                            
                           
                        while (calendar.get(Calendar.WEEK_OF_YEAR) != numSem) {
                             
                            calendar.add(Calendar.WEEK_OF_YEAR, -1);
                             
                        }
                      
                        
                        
                    } else if (numSem > numSemActu) {out.println("blabla3");
                            
                        while (calendar.get(Calendar.WEEK_OF_YEAR) != numSem ) {
                            calendar.add(Calendar.WEEK_OF_YEAR, +1);
                        }
                       
                    } else if (numSem < numSemActu) {out.println("blabla4");

                        while (calendar.get(Calendar.WEEK_OF_YEAR) != numSem ) {
                            calendar.add(Calendar.WEEK_OF_YEAR, -1);
                                     
                        }
                        
                    }
                    
         
                                       
                    numSem = calendar.get(Calendar.WEEK_OF_YEAR); 
                    Annee = calendar.get(Calendar.YEAR); 
           
                       

            %>   
        </p> 

        <p>
            <% out.println(
                        "num�ro de semaine actuelle : " + numSemActu);%>
            <br>
            <% out.println(
                        "num�ro de semaine de la visualisation : " + numSem); %> 
            <br>
            <% out.println(
                        "Ann�e de la semaine actuelle : " + AnneeActu);%>
            <br>
            <% out.println(
                        "Ann�e de la semaine de la visualisation : " + Annee); %>

        </p>

        <p>
            <a href="/Pappl/PagePrincipale?week=<%
                calendar.add(Calendar.WEEK_OF_YEAR, +1);
                out.println(calendar.get(Calendar.WEEK_OF_YEAR));
                calendar.add(Calendar.WEEK_OF_YEAR, -1);

               %>&year=<%                   calendar.add(Calendar.WEEK_OF_YEAR, +1);
                   out.println(calendar.get(Calendar.YEAR)); 
                   calendar.add(Calendar.WEEK_OF_YEAR, -1);


               %>">Semaine Suivante</a>
        </p>

        <p>
            <a href="/Pappl/PagePrincipale?week=<%                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                out.println(calendar.get(Calendar.WEEK_OF_YEAR));
                calendar.add(Calendar.WEEK_OF_YEAR, +1);


               %>&year=<%                   calendar.add(Calendar.WEEK_OF_YEAR, -1);
                   out.println(calendar.get(Calendar.YEAR)); 
                   calendar.add(Calendar.WEEK_OF_YEAR, +1);
               %>">Semaine Pr�c�dente</a>
        </p>

        
            <%    int jourSemaine = calendar.get(Calendar.DAY_OF_WEEK); 
               

                Date dateLundi = new java.util.Date();
                Date dateMardi = new java.util.Date();
                Date dateMercredi = new java.util.Date();
                Date dateJeudi = new java.util.Date();
                Date dateVendredi = new java.util.Date();

                switch (jourSemaine) { 
                    case 2:                     //Lundi commence � 2 
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
                    case 3:

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
                    case 4:
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
                    case 5:
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
                    case 6:
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
        <table>

            <tr>
                <th> </th>
                <th>Lundi <% out.println(dateFormatJourMois.format(dateLundi)); %>  </th> 
                <th>Mardi <% out.println(dateFormatJourMois.format(dateMardi)); %></th>
                <th>Mercredi <% out.println(dateFormatJourMois.format(dateMercredi)); %></th>
                <th>Jeudi <% out.println(dateFormatJourMois.format(dateJeudi)); %></th>
                <th>Vendredi <% out.println(dateFormatJourMois.format(dateVendredi));%></th>
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
                <td>Apr�s-midi</td>
                <td>Cours</td>
                <td>Cours</td>
                <td>Cours</td>
                <td>Cours</td>
                <td>Cours</td>
            </tr>
        </table>

    </body>
</html>

