<%-- 
    Document   : dupliquerAnnee
    Created on : 21 janv. 2015, 20:13:37
    Author     : Yohann
--%>

<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            <form method="post" action="DupliquerAnnee">
                <fieldset>
                    <lengend>Duplication d'une année : </lengend><br><br>

                    <label for="JourCopier">L'année scolaire à copier commence le : </label>
                    <input type="number" name="JourCopier" id="JourCopier" min="1" max="31" value="1" >

                    <label for="MoisCopier"> </label>
                    <select name="MoisCopier" id="MoisCopier">
                        <option value="1">Janvier</option>
                        <option value="2">Février</option>
                        <option value="3">Mars</option>
                        <option value="4">Avril</option>
                        <option value="5">Mai</option>
                        <option value="6">Juin</option>
                        <option value="7">Juillet</option>
                        <option value="8">Août</option>
                        <option value="9" selected="">Septembre</option>
                        <option value="10">Octobre</option>
                        <option value="11">Novembre</option>
                        <option value="12">Décembre</option>
                    </select>

                    <label for="AnneeCopier"> </label>
                    <input type="number" name="AnneeCopier" id="AnneeCopier" min="2014" max="2100" <%
                        Date dateCourante = new java.util.Date();
                        GregorianCalendar calendar = new java.util.GregorianCalendar();
                        calendar.setTime(dateCourante);
                        int annee = calendar.get(GregorianCalendar.YEAR) - 1;
                        out.println("value=\"" + annee + "\">");
                           %><br />

                    <br>
                    <label for="JourColler">L'année scolaire est à coller à partir du : </label>
                    <input type="number" name="JourColler" id="JourColler" min="1" max="31" value="1" >

                    <label for="MoisColler"> </label>
                    <select name="MoisColler" id="MoisColler">
                        <option value="1">Janvier</option>
                        <option value="2">Février</option>
                        <option value="3">Mars</option>
                        <option value="4">Avril</option>
                        <option value="5">Mai</option>
                        <option value="6">Juin</option>
                        <option value="7">Juillet</option>
                        <option value="8">Août</option>
                        <option value="9" selected="">Septembre</option>
                        <option value="10">Octobre</option>
                        <option value="11">Novembre</option>
                        <option value="12">Décembre</option>
                    </select>

                    <label for="AnneeColler"> </label>
                    <input type="number" name="AnneeColler" id="AnneeColler" min="2014" max="2100" <%
                        annee = calendar.get(GregorianCalendar.YEAR);
                        out.println("value=\"" + annee + "\">");
                           %><br />

                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" />
                <a href="/Pappl/PagePrincipale">Annuler</a>
                <br>

                <h3>Par exemple, si vous selectionnez : "L'année scolaire à copier commence le : 1 septembre 2015" 
                    et "L'année scolaire est à coller à partir du : 6 septembre 2016".</h3>
                <h3>Tout les cours entre le 1er septembre 2015(inclu) et le 31 août 2016(inclu) seront selectionnés 
                    et copiés à partir du 6 septembre 2016.</h3>
                <h3>NB1 : les 2 dates choisis doivent correspondre au même jour de la semaine, 
                    dans l'exemple ci-dessus le 1 septembre 2015 et le 6 septembre 2016 sont des mardis.</h3>
                <h3>NB2 : Si des cours sont déjà placés dans l'année où les cours vont être dupliqués, 
                    la page suivante vous les indiquera et vous proposera de les supprimer.</h3>
            </form>
        </div>
    </body>
</html>
