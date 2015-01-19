<%--
    Document   : pageprincipale
    Created on : 22 déc. 2014, 19:32:16
    Author     : Geoffrey
--%>



<%@page import="java.util.LinkedList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ocpappl.bdonn.BDonn"%>
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

        <table>
            <tr>
                <td> <div class="statut"> <span statut="dispo"> Test de coloration conditionnelle </span> </div> </td>   
                <td> hello </td>
            </tr>
        </table>

        <%
            Formulaire logs = (Formulaire) request.getAttribute("auth");
            if (logs != null) {  
                out.println("Bonjour " + request.getAttribute("login") + ", " + logs.getResultat());
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
                GregorianCalendar calendar2 = new java.util.GregorianCalendar();
                calendar2.setTime(dateCourante);
                int numSemActu = calendar.get(Calendar.WEEK_OF_YEAR);
                int AnneeActu = calendar.get(Calendar.YEAR);
                int numSem = calendar.get(Calendar.WEEK_OF_YEAR);
                int Annee = calendar.get(Calendar.YEAR);

                if (request.getAttribute("week") != null && request.getAttribute("year") != null) {
                    numSem = Integer.parseInt((String) request.getAttribute("week"));
                    Annee = Integer.parseInt((String) request.getAttribute("year"));
                    //out.println("Non null " + numSem + " " + Annee);
                }

                /* tentative de blocage du problème */

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
                calendar.set(Calendar.YEAR, Annee);
                calendar.set(Calendar.WEEK_OF_YEAR, numSem);
                /*
                 //Attention si il y a un changement d'année et de semaine en même temps c'est le chaos !!
                 if(Annee > AnneeActu  && numSem != numSemActu) {out.println("blabla1");//Attention il ne faut pas ajouter plus d'un an !!
                 //Attention si le changement d'année se fait dans la semaine courante il ne se passe rien !!!!TODO
                 //Attention si le changement d'année se fait avec numSem > à la semaine courante le changement d'année ne se fait pas
                 while (calendar.get(Calendar.WEEK_OF_YEAR) != numSem) {
                 calendar.add(Calendar.WEEK_OF_YEAR, +1);
                 //out.println(calendar.get(Calendar.WEEK_OF_YEAR)+" "+ numSem + "<br>");
                 }


                 } else if (Annee < AnneeActu && numSem != ) {out.println("blabla2");//Attention ne pas retirer plus d'un an !!
                 //Attention si le changement d'année se fait dans la semaine courante il ne se passe rien !!!!TODO
                 //Attention si le changement d'année se fait avec numSem < à la semaine courante le changement d'année ne se fait pas


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

                 */
                calendar.set(calendar.DAY_OF_WEEK, 1);
                numSem = calendar.get(Calendar.WEEK_OF_YEAR);
                Annee = calendar.get(Calendar.YEAR);


            %>
        </p>

        <p>
            <% out.println(
                        "numéro de semaine actuelle : " + numSemActu);%>
            <br>
            <% out.println(
                        "numéro de semaine de la visualisation : " + numSem); %>
            <br>
            <% out.println(
                        "Année de la semaine actuelle : " + AnneeActu);%>
            <br>
            <% out.println(
                        "Année de la semaine de la visualisation : " + Annee); %>

        </p>

        <p>
            <a href="/Pappl/PagePrincipale?week=<%
                calendar.add(Calendar.WEEK_OF_YEAR, +1);
                out.println(calendar.get(Calendar.WEEK_OF_YEAR));

               %>&year=<%                   out.println(calendar.get(Calendar.YEAR));
                   calendar.add(Calendar.WEEK_OF_YEAR, -1);
                   if (request.getParameter("option") != null) {
                       out.println("&option=" + request.getParameter("option"));
                   }
               %>">Semaine Suivante</a>
</p>

<p>

            <a href="/Pappl/PagePrincipale?week=<%                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                out.println(calendar.get(Calendar.WEEK_OF_YEAR));


               %>&year=<%                   out.println(calendar.get(Calendar.YEAR));
                   calendar.add(Calendar.WEEK_OF_YEAR, +1);

                   if (request.getParameter("option") != null) {
                       out.println("&option=" + request.getParameter("option"));
                   }
               %>">Semaine Précédente</a>
        </p>


        <%    int jourSemaine = calendar.get(Calendar.DAY_OF_WEEK);

            Date dateLundi = new java.util.Date();
            Date dateMardi = new java.util.Date();
            Date dateMercredi = new java.util.Date();
            Date dateJeudi = new java.util.Date();
            Date dateVendredi = new java.util.Date();

            //Lundi commence à 2
            calendar.add(calendar.DATE, 2 - jourSemaine);
            dateLundi = calendar.getTime();
            calendar.add(calendar.DATE, +1);
            dateMardi = calendar.getTime();
            calendar.add(calendar.DATE, +1);
            dateMercredi = calendar.getTime();
            calendar.add(calendar.DATE, +1);
            dateJeudi = calendar.getTime();
            calendar.add(calendar.DATE, +1);
            dateVendredi = calendar.getTime();
            calendar.add(calendar.DATE, -4);
        %>
        <table>

            <tr>
                <th>Semaine : <% 
            int semaineVisualisation = calendar.get(Calendar.WEEK_OF_YEAR) - 1;
            out.println(semaineVisualisation);
                if(request.getParameter("option")!=null){
                    out.println("Option : " + request.getParameter("option"));
                } %>  </th>
                <th>Lundi <% out.println(dateFormatJourMois.format(dateLundi)); %>  </th>
                <th>Mardi <% out.println(dateFormatJourMois.format(dateMardi)); %></th>
                <th>Mercredi <% out.println(dateFormatJourMois.format(dateMercredi)); %></th>
                <th>Jeudi <% out.println(dateFormatJourMois.format(dateJeudi)); %></th>
                <th>Vendredi <% out.println(dateFormatJourMois.format(dateVendredi));%></th>
            </tr>
         



            <tr><%
                
                BDonn edt = new BDonn();
                out.println("<form method=\"get\" action=\"PagePrincipale\">"
                        + "<label>Semaine : </label>"
                        + "<select name=\"week\" id=\"week\">"
                        + "<option value=\"" + calendar2.get(Calendar.WEEK_OF_YEAR) + "\">" + calendar2.get(Calendar.WEEK_OF_YEAR) + "</option>");

                for (int i = 1; i < 54; i++) {
                    out.println("<option value=\"" + i + "\">" + i + "</option>");
                }

                out.println("</select>");
                out.println("<label>Année :</label>"
                        + "<select name=\"year\" id=\"year\">"
                        + "<option value=\"" + calendar2.get(Calendar.YEAR) + "\">" + calendar2.get(Calendar.YEAR) + "</option>");

                for (int i = 2014; i < 2020; i++) {
                    out.println("<option value=\"" + i + "\">" + i + "</option>");
                }

                out.println("</select>");
            
                
                ArrayList<LinkedList> listeOption = edt.selectionner("Option");
                out.println("<label>Option :</label>"
                        + "<select name=\"option\" id=\"option\">");
                if (request.getParameter("option") != null) {
                    out.println("<option value=\"" + request.getParameter("option") + "\">" + edt.selectionnerOption(request.getParameter("option")).get(0) + "</option>");
                }
                for (int i = 0; i < listeOption.size(); i++) {
                    out.println("<option value=\"" + listeOption.get(i).get(0) + "\">" + listeOption.get(i).get(1) + "</option>");
                }
                out.println("</select>"
                        + "<input type=\"submit\" value=\"Valider\"  />"
                        + "</form>");

                out.println("<td>Matin</td>");
                LinkedList option = new LinkedList();
                if(request.getParameter("option")!=null){
                option.add(request.getParameter("option"));
                } else {
                    option.add(listeOption.get(0).get(0));
                }
                ArrayList<ArrayList> listeCours = new ArrayList();
                int moisCorrect = calendar.get(calendar.MONTH) + 1;
                
                String conditionJour = calendar.get(calendar.YEAR) + "-" + moisCorrect + "-" + calendar.get(calendar.DATE);

                String heureDebut = "13:45:00";
                String heureFin = "00:00:00";
                String conditionDebut = conditionJour + " " + heureDebut;
                String conditionFin = conditionJour + " " + heureFin;
                for (int i = 0; i < 10; i++) {
                    moisCorrect = calendar.get(calendar.MONTH) + 1;
                    conditionJour = calendar.get(calendar.YEAR) + "-" + moisCorrect + "-" + calendar.get(calendar.DATE);
                    conditionDebut = conditionJour + " " + heureDebut;
                    conditionFin = conditionJour + " " + heureFin;
                    ArrayList cours = edt.selectionnerCours(conditionJour, conditionFin, conditionDebut, option);
                    if (cours.size() == 0) {
                        cours.add(conditionJour + " " + conditionFin + " " + conditionDebut + " " +option.get(0));
                        cours.add(conditionJour + " " + conditionFin + " " + conditionDebut + " " +option.get(0));
                    }
                    listeCours.add(cours);
                    if ((i % 2 == 1)) {
                        calendar.add(calendar.DATE, +1);
                        heureDebut = "13:45:00";
                        heureFin = "00:00:00";
                    } else {
                        heureDebut = "23:59:59";
                        heureFin = "12:15:01";
                    }
                }
                for (int i = 0; i < 5; i++) {

                out.println("<td>" + listeCours.get(2 * i).get(1) + "</td>");
                 
                }
                out.println("</tr><tr>");
                out.println("<td>Après-midi</td>");
                for (int i = 0; i < 5; i++) {
                    out.println("<td>" + listeCours.get(2 * i + 1).get(1) + "</td>");
                }
                %>

            </tr>

            <br> <br>
        </table>


    </body>
</html>

