<%-- 
    Document   : modificationOption
    Created on : 11 janv. 2015, 18:32:28
    Author     : Yohann
--%>


<%@page import="java.util.LinkedList"%>
<%@page import="com.ocpappl.bdonn.BDonn"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modification d'une option</title>
    </head>
    <body>
        <%
            String objet = (String) request.getAttribute("objet");
            if ("supprimer".equals(objet)) {
                out.println("<h1>Etes-vous sur de vouloir supprimer l'option " + request.getAttribute("nom") + " ?<h1/><br/>");
                out.println("<form method=\"post\" action=\"ModificationSuppression?type=Option&objet=supprimer&id=" + request.getAttribute("id") + "\">"
                        + "<input type=\"submit\" value=\"oui\"  />");

            } else if ("modifier".equals(objet)) {
                BDonn edt = new BDonn();
                String id = (String) request.getAttribute("id");
                ArrayList liste = edt.selectionnerOption(id);
                out.println("<form method=\"post\" action=\"ModificationSuppression?type=Option&objet=modifier&id=" + request.getAttribute("id") + "\">"
                        + "<fieldset>"
                        + "<legend>Informations sur l'option</legend>");

                out.println("<label for=\"Option_Acronyme\">Acronyme <span class=\"requis\">*</span></label>"
                        + "<input type=\"text\" id=\"Option_Acronyme\" name=\"Option_Acronyme\" value=\"" + liste.get(0) + "\" size=\"20\" maxlength=\"20\" /><br />"
                        + "<label for=\"Option_Nom\">Nom <span class=\"requis\">*</span></label>"
                        + "<input type=\"text\" id=\"Option_Nom\" name=\"Option_Nom\" value=\"" + liste.get(1) + "\" size=\"20\" maxlength=\"20\" /><br />");

                out.println("<label for=\"Responsable_Option\">Qui est le responsable de cette option ?*</label><br>"
                        + "<select name=\"Responsable_Option\" id=\"Responsable_Option\">");
                ArrayList<LinkedList> respo = new ArrayList();
                out.println("<option value=\"" + liste.get(2) + "\">"
                        + liste.get(3) + " " + liste.get(4) + "</option>");

                respo = edt.selectionner("Responsable_Option");
                for (int i = 0; i < respo.size(); i++) {
                    if (respo.get(i).get(0) != liste.get(2)) {
                        out.println("<option value=\"" + respo.get(i).get(0) + "\">"
                                + respo.get(i).get(2) + " " + respo.get(i).get(1) + "</option>");
                    }
                }

                out.println("</select><br>");

                out.println("</fieldset>"
                        + "<input type=\"submit\" value=\"Valider\"  />"
                        + "<input type=\"reset\" value=\"Remettre à zéro\" />"
                        + "<a href=\"/Pappl/Creation?type=Personne\">Creer un responsable d'option</a>");

            } else {
                out.println("<h1> Erreur : objet incorrect, objet : " + objet + ".</h1>");

            }

        %>

        <a href="/Pappl/PagePrincipale">Annuler</a>
    </form>
</body>
</html>
