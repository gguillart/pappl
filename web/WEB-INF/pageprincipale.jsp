<%-- 
    Document   : pageprincipale
    Created on : 22 déc. 2014, 19:32:16
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
                //Date dateCurseur = new java.util.Date();
                    GregorianCalendar calendar = new java.util.GregorianCalendar();
                    calendar.setTime(dateCourante);
                    int numSemActu = calendar.get(Calendar.WEEK_OF_YEAR);
                            //Integer.parseInt(dateFormatSemaine.format(dateCourante));
                    int numSem = calendar.get(Calendar.WEEK_OF_YEAR);
                            //numSemActu;
                    int AnneeActu = PagePrincipale.getYearForWeek(calendar);
                    int Annee = PagePrincipale.getYearForWeek(calendar);
                    
                  if (request.getAttribute("week") != null) {

                        numSem = Integer.parseInt((String) request.getAttribute("week"));
                        Annee = Integer.parseInt((String) request.getAttribute("year"));

                       if (numSem > numSemActu && Annee>AnneeActu) {
                            int k = Math.abs(7 * (numSem - numSemActu)+52*7*(Annee-AnneeActu));
                            calendar.add(calendar.DATE, +k);
                      } 
                       
                       else if (numSem < numSemActu && Annee>AnneeActu){
                         int k = Math.abs(7 * (numSem - numSemActu)+52*7*(Annee-AnneeActu));
                           calendar.add(calendar.DATE, +k);}
                          
                           else if (numSem > numSemActu && Annee<AnneeActu){
                         int k = Math.abs(7 * (numSem - numSemActu)-52*7*(AnneeActu-Annee));
                           calendar.add(calendar.DATE, - k); 
                       }
                       else if (numSem < numSemActu && Annee<AnneeActu){
                         int k = Math.abs(7 * (numSem - numSemActu)-52*7*(numSemActu-numSem));
                           calendar.add(calendar.DATE, - k);
                       }
                       else if (numSem > numSemActu && Annee==AnneeActu){
                         int k = Math.abs(7 * (numSem - numSemActu));
                           calendar.add(calendar.DATE, +k);
                       }
                       else if (numSem < numSemActu && Annee==AnneeActu){
                         int k = Math.abs(7 * (numSem - numSemActu));
                           calendar.add(calendar.DATE, - k);
                       }
                       numSem = calendar.get(Calendar.WEEK_OF_YEAR); 
                       Annee = PagePrincipale.getYearForWeek(calendar); 
                       // dateCurseur = calendar.getTime();
                    } 
                        
                             

                int jourSemaine = calendar.get(Calendar.DAY_OF_WEEK);  
                        //dateFormatJourSemaine.format(dateCourante);
              
                Date dateLundi = new java.util.Date();
                Date dateMardi = new java.util.Date();
                Date dateMercredi = new java.util.Date();
                Date dateJeudi = new java.util.Date();
                Date dateVendredi = new java.util.Date();

                switch (jourSemaine) {
                    case 2:                     //Lundi commence à 2
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
        </p> 

        <p>
            <% out.println (

                "numéro de semaine actuelle : "+numSemActu+"\n");%>
            <br>
            <% out.println (

                "numéro de semaine de la visualisation : "+numSem); %>
                 <br>
                 <% out.println (

                "Année de la semaine actuelle : "+AnneeActu+"\n");%>
            <br>
            <% out.println (

                "Année de la semaine de la visualisation : "+Annee); %>

        </p>

        <p>
            <a href="/Pappl/PagePrincipale?week=<%
                    calendar.add(calendar.DATE, +7);
                         //dateCurseur=calendar.getTime();
                    int numSemSuiv = calendar.get(Calendar.WEEK_OF_YEAR); 
                            //Integer.parseInt(dateFormatSemaine.format(dateCurseur));
                    out.println(numSemSuiv);
                    calendar.add(calendar.DATE, -7);                

                   %>&year=<% Annee = PagePrincipale.getYearForWeek(calendar);
                   if (numSemSuiv==1){ 
                   out.println(Annee+1);
                   }
                   else
                   {out.println(Annee);}
                           
                   %>">Semaine Suivante</a>
        </p>
        
                <p>
            <a href="/Pappl/PagePrincipale?week=<%
                    calendar.add(calendar.DATE, -7);
                         //dateCurseur=calendar.getTime();
                    int numSemPrec = calendar.get(Calendar.WEEK_OF_YEAR); 
                            //Integer.parseInt(dateFormatSemaine.format(dateCurseur));
                    out.println(numSemPrec);
                    calendar.add(calendar.DATE, +7);
                                    

                   %>&year=<% Annee = PagePrincipale.getYearForWeek(calendar);
                   if (numSemPrec==52){ 
                   out.println(Annee-1);
                   }
                   else
                   {out.println(Annee);}
                   
                           
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

