<%-- 
    Document   : modificationMatiere
    Created on : 11 janv. 2015, 18:33:21
    Author     : Yohann
--%>

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
                        + "<input type=\"text\" id=\"Matiere_Acronyme\" name=\"Matiere_Acronyme\" value=\"" + liste.get(0) + "\" size=\"20\" maxlength=\"20\" /><br />"
                        + "<label for=\"Matiere_Nom\">Nom <span class=\"requis\">*</span></label>"
                        + "<input type=\"text\" id=\"Matiere_Nom\" name=\"Matiere_Nom\" value=\"" + liste.get(1) + "\" size=\"20\" maxlength=\"20\" /><br />");

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
