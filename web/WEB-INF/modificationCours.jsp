<%-- 
    Document   : modificationCours
    Created on : 11 janv. 2015, 18:33:06
    Author     : Yohann
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ocpappl.bdonn.BDonn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modification d'un cours</title>
    </head>
    <body>
        <%
            String objet = (String) request.getAttribute("objet");
            if ("supprimer".equals(objet)) {
                out.println("<h1>Etes-vous sur de vouloir supprimer ce cours de " + request.getAttribute("prenom") + " du " + request.getAttribute("nom") + " ?<h1/><br/>");
                out.println("<form method=\"post\" action=\"ModificationSuppression?type=Cours&objet=supprimer&id=" + request.getAttribute("id") + "\">"
                        + "<input type=\"submit\" value=\"oui\"  />");

            } else if ("modifier".equals(objet)) {
                BDonn edt = new BDonn();
                String id = (String) request.getAttribute("id");
                ArrayList liste = edt.selectionnerCours(id);
                out.println("<form method=\"post\" action=\"ModificationSuppression?type=Cours&objet=modifier&id=" + request.getAttribute("id") + "\">"
                        + "<fieldset>"
                        + "<legend>Informations sur le cours</legend>");

                out.println("<label for=\"Salle\">Salle </label>"
                        + "<input type=\"text\" id=\"Salle\" name=\"Salle\" value=\"" + liste.get(0) + "\" size=\"20\" maxlength=\"20\" /><br />"
                        + "<label for=\"Intervenant\">Intervenant </label>"
                        + "<input type=\"text\" id=\"Intervenant\" name=\"Intervenant\" value=\"" + liste.get(1) + "\" size=\"20\" maxlength=\"20\" /><br />"
                        + "<label for=\"Commentaire\">Commentaire </label>"
                        + "<input type=\"text\" id=\"Commentaire\" name=\"Commentaire\" value=\"" + liste.get(2) + "\" size=\"20\" maxlength=\"20\" /><br />");

                out.println("<legend>Type de cours :</legend>");

                switch ((String) liste.get(3)) {

                    case "CM":
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"CM\" checked=\"\">CM<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"TD\">TD<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"TP\">TP<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"Conf\">Conf<br>");
                        break;

                    case "TD":
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"CM\">CM<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"TD\" checked=\"\">TD<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"TP\">TP<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"Conf\">Conf<br>");
                        break;

                    case "TP":
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"CM\">CM<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"TD\">TD<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"TP\" checked=\"\">TP<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"Conf\">Conf<br>");

                        break;

                    case "Conf":
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"CM\">CM<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"TD\">TD<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"TP\">TP<br>");
                        out.println("<input type=\"radio\" name=\"Type_De_Cours\" value=\"Conf\" checked=\"\">Conf<br>");
                        break;
                }

                out.println("<br /><legend>Qui est l'enseignant responsable de ce cours ?</legend>");

                ArrayList<LinkedList> respo = new ArrayList();
                out.println("<input type=\"radio\" name=\"Enseignant\" value=\"" + liste.get(4) + "\" checked=\"\">"
                        + liste.get(5) + " " + liste.get(6) + "<br>");

                respo = edt.selectionner("Enseignant");
                for (int i = 0; i < respo.size(); i++) {
                    if (respo.get(i).get(0) != liste.get(4)) {
                        out.println("<input type=\"radio\" name=\"Enseignant\" value=\"" + respo.get(i).get(0) + "\">"
                                + respo.get(i).get(2) + " " + respo.get(i).get(1) + "<br>");
                    }
                }

                out.println("<br /><legend>Quelle est la matiere de ce cours ?</legend>");

                ArrayList<LinkedList> matiere = new ArrayList();
                out.println("<input type=\"radio\" name=\"Matiere\" value=\"" + liste.get(7) + "\" checked=\"\">"
                        + liste.get(8) + "<br>");

                matiere = edt.selectionner("Matiere");
                for (int i = 0; i < matiere.size(); i++) {
                    if (matiere.get(i).get(0) != liste.get(7)) {
                        out.println("<input type=\"radio\" name=\"Matiere\" value=\"" + matiere.get(i).get(0) + "\">"
                                + matiere.get(i).get(1) + "<br>");
                    }
                }

                out.println("<br /><legend>Quelles options sont concernées ?</legend>");
                ArrayList<LinkedList> optionSelectionnées = (ArrayList) liste.get(9);

                for (int i = 0; i < optionSelectionnées.size(); i++) {

                    out.println("<input type=\"checkbox\" name=\"Option" + i + "\" value=\"" + optionSelectionnées.get(i).get(0) + "\" checked=\"\">"
                            + optionSelectionnées.get(i).get(1) + "<br>");
                }

                int l = optionSelectionnées.size();
                int count = 0;
                ArrayList<LinkedList> option = new ArrayList();
                option = edt.selectionner("Option");
                for (int i = 0; i < option.size(); i++) {
                    for (int j = 0; j < optionSelectionnées.size(); j++) {
                        if (option.get(i).get(0) == optionSelectionnées.get(j).get(0)) {
                            count = 1;
                        }
                    }
                    if (count == 0) {
                        out.println("<input type=\"checkbox\" name=\"Option" + l + "\" value=\"" + option.get(i).get(0) + "\">"
                                + option.get(i).get(1) + "<br>");
                        l++;
                    } else {
                        count = 0;
                    }

                }

                out.println("<br /><legend>Horaire et date : </legend>");

                out.println("<label for=\"Debut\">De :</label>"
                        + "<select name=\"Debut\" id=\"Debut\">"
                        + "<option value=\"" + liste.get(13) + "\">" + liste.get(16) + "</option>"
                        + "<option value=\"08:00:00\">8h00</option>"
                        + "<option value=\"09:00:00\">9h00</option>"
                        + "<option value=\"10:15:00\">10h15</option>"
                        + "<option value=\"11:15:00\">11h15</option>"
                        + "<option value=\"13:45:00\">13h45</option>"
                        + "<option value=\"14:45:00\">14h45</option>"
                        + "<option value=\"16:00:00\">16h00</option>"
                        + "<option value=\"17:00:00\">17h00</option>"
                        + "</select>");

                out.println("<label for=\"Fin\">à :</label>"
                        + "<select name=\"Fin\" id=\"Fin\">"
                        + "<option value=\"" + liste.get(14) + "\">" + liste.get(17) + "</option>"
                        + "<option value=\"09:00:00\">9h00</option>"
                        + "<option value=\"10:00:00\">10h00</option>"
                        + "<option value=\"11:15:00\">11h15</option>"
                        + "<option value=\"12:15:00\">12h15</option>"
                        + "<option value=\"14:45:00\">14h45</option>"
                        + "<option value=\"15:45:00\">15h45</option>"
                        + "<option value=\"17:00:00\">17h00</option>"
                        + "<option value=\"18:00:00\">18h00</option>"
                        + "</select><br />");

                out.println("<label for=\"Jour\">Le </label>"
                        + "<input type=\"number\" name=\"Jour\" id=\"Jour\" min=\"1\" max=\"31\" value=\"" + liste.get(12) + "\">");

                out.println("<label for=\"Mois\"> </label>"
                        + "<select name=\"Mois\" id=\"Mois\">"
                        + "<option value=\"" + liste.get(11) + "\">" + liste.get(15) + "</option>"
                        + "<option value=\"1\">Janvier</option>"
                        + "<option value=\"2\">Février</option>"
                        + "<option value=\"3\">Mars</option>"
                        + "<option value=\"4\">Avril</option>"
                        + "<option value=\"5\">Mai</option>"
                        + "<option value=\"6\">Juin</option>"
                        + "<option value=\"7\">Juillet</option>"
                        + "<option value=\"8\">Août</option>"
                        + "<option value=\"9\">Septembre</option>"
                        + "<option value=\"10\">Octobre</option>"
                        + "<option value=\"11\">Novembre</option>"
                        + "<option value=\"12\">Décembre</option>"
                        + "</select>");

                out.println("<label for=\"Annee\"> </label>"
                        + "<input type=\"number\" name=\"Annee\" id=\"Annee\" min=\"2014\" max=\"2100\" value=\"" + liste.get(10) + "\" >");

                out.println("</fieldset>"
                        + "<input type=\"submit\" value=\"Valider\"  />"
                        + "<input type=\"reset\" value=\"Remettre à zéro\" />");

            } else {
                out.println("<h1> Erreur : objet incorrect, objet : " + objet + ".</h1>");

            }

        %>

        <a href="/Pappl/PagePrincipale">Annuler</a>
    </form>
</body>
</html>
