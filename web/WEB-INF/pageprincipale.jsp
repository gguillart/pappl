<%--
    Document   : pageprincipale
    Created on : 22 d�c. 2014, 19:32:16
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
                    //out.println("Non null " + numSem + " " + Annee);
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
                calendar.set(Calendar.YEAR, Annee);
                calendar.set(Calendar.WEEK_OF_YEAR, numSem);
                /*
                 //Attention si il y a un changement d'ann�e et de semaine en m�me temps c'est le chaos !!
                 if(Annee > AnneeActu  && numSem != numSemActu) {out.println("blabla1");//Attention il ne faut pas ajouter plus d'un an !!
                 //Attention si le changement d'ann�e se fait dans la semaine courante il ne se passe rien !!!!TODO
                 //Attention si le changement d'ann�e se fait avec numSem > � la semaine courante le changement d'ann�e ne se fait pas
                 while (calendar.get(Calendar.WEEK_OF_YEAR) != numSem) {
                 calendar.add(Calendar.WEEK_OF_YEAR, +1);
                 //out.println(calendar.get(Calendar.WEEK_OF_YEAR)+" "+ numSem + "<br>");
                 }


                 } else if (Annee < AnneeActu && numSem != ) {out.println("blabla2");//Attention ne pas retirer plus d'un an !!
                 //Attention si le changement d'ann�e se fait dans la semaine courante il ne se passe rien !!!!TODO
                 //Attention si le changement d'ann�e se fait avec numSem < � la semaine courante le changement d'ann�e ne se fait pas


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

               %>&year=<%                   
                   out.println(calendar.get(Calendar.YEAR));
                   calendar.add(Calendar.WEEK_OF_YEAR, -1);


               %>">Semaine Suivante</a>
        </p>

        <p>
            <a href="/Pappl/PagePrincipale?week=<%                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                out.println(calendar.get(Calendar.WEEK_OF_YEAR));
                


               %>&year=<%
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

            //Lundi commence � 2
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
            <%--<%
                BDonn edt = new BDonn();
                ArrayList<LinkedList> option = new ArrayList();
                option = edt.selectionnerOption(edt.selectionner("Option").get(0).get(0));
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd'/'MM'/'yy");
                String jour=sdf1.format(dateLundi);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd'/'MM'/'yy'/'hh'/'mm");
                Date debut =  sdf2.parse(jour+"'/'07'/'59");
                Date fin =  sdf2.parse(jour+"'/'13'/'46");
                edt.selectionnerCoursHoraire(debut,fin,option);


                %> --%>
            <tr><%
                out.println("<td>Matin</td>");
                BDonn edt = new BDonn();
                LinkedList option = new LinkedList();
                option.add("1");
                ArrayList<ArrayList> listeCours = new ArrayList();
                int moisCorrect = calendar.get(calendar.MONTH) + 1;
                calendar.add(calendar.DATE, -4);
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
                        cours.add("vide");
                        cours.add("vide");
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
                out.println("<td>Apr�s-midi</td>");
                for (int i = 0; i < 5; i++) {
                    out.println("<td>" + listeCours.get(2 * i + 1).get(1) + "</td>");
                }
                %>
            </tr>
        </table>

    </body>
</html>

