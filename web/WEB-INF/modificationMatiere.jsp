<%-- 
    Document   : modificationMatiere
    Created on : 11 janv. 2015, 18:33:21
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
        <title>Modification d'une matière</title>
    </head>
    <body>
        <%
            String objet = (String) request.getAttribute("objet");
            if ("supprimer".equals(objet)) {
                out.println("<h1>Etes-vous sur de vouloir supprimer la matière " + request.getAttribute("nom") + " ?<h1/><br/>");
                out.println("<form method=\"post\" action=\"ModificationSuppression?type=Matiere&objet=supprimer&id=" + request.getAttribute("id") + "\">"
                        + "<input type=\"submit\" value=\"oui\"  />");

            } else if ("modifier".equals(objet)) {
                BDonn edt = new BDonn();
                String id = (String) request.getAttribute("id");
                ArrayList liste = edt.selectionnerMatiere(id);
                out.println("<form method=\"post\" action=\"ModificationSuppression?type=Matiere&objet=modifier&id=" + request.getAttribute("id") + "\">"
                        + "<fieldset>"
                        + "<legend>Informations sur la matière</legend>");

                out.println("<label for=\"Matiere_Acronyme\">Acronyme <span class=\"requis\">*</span></label>"
                        + "<input type=\"text\" id=\"Matiere_Acronyme\" name=\"Matiere_Acronyme\" value=\"" + liste.get(0) + "\" size=\"20\" maxlength=\"100\" /><br />"
                        + "<label for=\"Matiere_Nom\">Nom <span class=\"requis\">*</span></label>"
                        + "<input type=\"text\" id=\"Matiere_Nom\" name=\"Matiere_Nom\" value=\"" + liste.get(1) + "\" size=\"20\" maxlength=\"100\" /><br />");

                out.println("<legend>Quelles options sont concernées par la matière ?*</legend>");

                ArrayList<LinkedList> optionSelectionnées = (ArrayList) liste.get(2);

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

                out.println("</fieldset>"
                        + "<input type=\"submit\" value=\"Valider\"  />"
                        + "<input type=\"reset\" value=\"Remettre à zéro\" />"
                        + "<a href=\"/Pappl/Creation?type=Option\">Creer une option</a>");

            } else {
                out.println("<h1> Erreur : objet incorrect, objet : " + objet + ".</h1>");

            }

        %>

        <a href="/Pappl/PagePrincipale">Annuler</a>
    </form>
</body>
</html>
