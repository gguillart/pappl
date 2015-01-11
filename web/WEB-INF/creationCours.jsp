<%-- 
    Document   : creationCours
    Created on : 29 déc. 2014, 23:19:55
    Author     : Yohann
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ocpappl.bdonn.BDonn"%>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'un cours</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
            <form method="post" action="Creation?type=Cours">
                <fieldset>
                    <legend>Informations sur le cours</legend>

                    <label for="Salle">Salle </label>
                    <input type="text" id="Salle" name="Salle" value="" size="20" maxlength="20" />
                    <br />

                    <label for="Intervenant">Intervenant </label>
                    <input type="text" id="Intervenant" name="Intervenant" value="" size="20" maxlength="20" />
                    <br />

                    <label for="Commentaire">Commentaire </label>
                    <input type="text" id="Commentaire" name="Commentaire" value="" size="20" maxlength="20" />
                    <br />

                    <legend>Type de cours :</legend>
                    <input type="radio" name="Type_De_Cours" value="CM">CM<br>
                    <input type="radio" name="Type_De_Cours" value="TD">TD<br>
                    <input type="radio" name="Type_De_Cours" value="TP">TP<br>
                    <input type="radio" name="Type_De_Cours" value="Conf">Conf<br>

                    <br />
                    <legend>Qui est l'enseignant responsable de ce cours ?</legend>

                    <%
                        BDonn edt = new BDonn();
                        ArrayList<LinkedList> prof = new ArrayList();
                        prof = edt.selectionner("Enseignant");
                        for (int i = 0; i < prof.size(); i++) {
                            out.println("<input type=\"radio\" name=\"Enseignant\" value=\"" + prof.get(i).get(0) + "\">"
                                    + prof.get(i).get(2) + " " + prof.get(i).get(1) + "<br>");
                        }

                    %>

                    <br />
                    <legend>Quelle est la matière de ce cours ?</legend>

                    <%                        ArrayList<LinkedList> matiere = new ArrayList();
                        matiere = edt.selectionner("Matiere");
                        for (int i = 0; i < matiere.size(); i++) {
                            out.println("<input type=\"radio\" name=\"Matiere\" value=\"" + matiere.get(i).get(0) + "\">"
                                    + matiere.get(i).get(1) + "<br>");
                        }

                    %>

                    <br />
                    <legend>Quelles options sont concernées ?</legend>

                    <%                        ArrayList<LinkedList> option = new ArrayList();
                        option = edt.selectionner("Option");
                        for (int i = 0; i < option.size(); i++) {
                            out.println("<input type=\"checkbox\" name=\"Option" + i + "\" value=\"" + option.get(i).get(0) + "\">"
                                    + option.get(i).get(1) + "<br>");
                        }
                    %>

                    <br />
                    <legend>Horaire et date : </legend>


                    <label for="Debut">De :</label>
                    <select name="Debut" id="Debut">
                        <option value="08:00:00">8h00</option>
                        <option value="09:00:00">9h00</option>
                        <option value="10:15:00">10h15</option>
                        <option value="11:15:00">11h15</option>
                        <option value="13:45:00">13h45</option>
                        <option value="14:45:00">14h45</option>
                        <option value="16:00:00">16h00</option>
                        <option value="17:00:00">17h00</option>
                    </select>

                    <label for="Fin">à :</label>
                    <select name="Fin" id="Fin" >
                        <option value="09:00:00">9h00</option>
                        <option value="10:00:00">10h00</option>
                        <option value="11:15:00">11h15</option>
                        <option value="12:15:00">12h15</option>
                        <option value="14:45:00">14h45</option>
                        <option value="15:45:00">15h45</option>
                        <option value="17:00:00">17h00</option>
                        <option value="18:00:00">18h00</option>
                    </select><br />

                    <label for="Jour">Le </label>
                    <input type="number" name="Jour" id="Jour" min="1" max="31" value="1">

                    <label for="Mois"> </label>
                    <select name="Mois" id="Mois">
                        <option value="1">Janvier</option>
                        <option value="2">Février</option>
                        <option value="3">Mars</option>
                        <option value="4">Avril</option>
                        <option value="5">Mai</option>
                        <option value="6">Juin</option>
                        <option value="7">Juillet</option>
                        <option value="8">Août</option>
                        <option value="9">Septembre</option>
                        <option value="10">Octobre</option>
                        <option value="11">Novembre</option>
                        <option value="12">Décembre</option>
                    </select>

                    <label for="Annee"> </label>
                    <input type="number" name="Annee" id="Annee" min="2014" max="2100" value="2015" ><br />

                    <label for="Repeter">Repeter le cours sur combien de semaines ? </label>
                    <input type="number" name="Repeter" id="Repeter" min="1" max="20" value="1" >
                    <%--TODO
                    possibilités de valeurs par défaut pour les horaires du cours 
                    répétition sur n semaines etc... --%> 

                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" />
                <a href="/Pappl/PagePrincipale">Annuler</a>
            </form>
        </div>
    </body>
</html>