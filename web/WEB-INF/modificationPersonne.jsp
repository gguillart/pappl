<%-- 
    Document   : modificationPersonne
    Created on : 11 janv. 2015, 18:33:34
    Author     : Yohann
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.ocpappl.bdonn.BDonn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modification d'une personne</title>
    </head>
    <body>
        <%
            String objet = (String) request.getAttribute("objet");
            if ("supprimer".equals(objet)) {
                out.println("<h1>Etes-vous sur de vouloir supprimer la personne " + request.getAttribute("prenom") + " " + request.getAttribute("nom") + " ?<h1/><br/>");
                out.println("<form method=\"post\" action=\"ModificationSuppression?type=Personne&objet=supprimer&id=" + request.getAttribute("id") + "\">"
                        + "<input type=\"submit\" value=\"oui\"  />");

            } else if ("modifier".equals(objet)) {
                BDonn edt = new BDonn();
                String id = (String) request.getAttribute("id");
                ArrayList liste = edt.selectionnerPersonne(id);
                out.println("<form method=\"post\" action=\"ModificationSuppression?type=Personne&objet=modifier&id=" + request.getAttribute("id") + "\">"
                        + "<fieldset>"
                        + "<legend>Informations sur la personne</legend>");

                out.println("<label for=\"Nom\">Nom <span class=\"requis\">*</span></label>"
                        + "<input type=\"text\" id=\"Nom\" name=\"Nom\" value=\"" + liste.get(0) + "\" size=\"20\" maxlength=\"20\" /><br />"
                        + "<label for=\"Prenom\">Prénom <span class=\"requis\">*</span></label>"
                        + "<input type=\"text\" id=\"Prenom\" name=\"Prenom\" value=\"" + liste.get(1) + "\" size=\"20\" maxlength=\"20\" /><br />");

                out.println("<legend>Est-ce un...</legend>");

                if ((int) liste.get(2) == 0) {
                    out.println("<input type=\"checkbox\" name=\"Personne0\" value=\"Enseignant\">Enseignant <br>");
                } else {
                    out.println("<input type=\"checkbox\" name=\"Personne0\" value=\"Enseignant\" checked=\"\">Enseignant <br>");
                }
                if ((int) liste.get(3) == 0) {
                    out.println("<input type=\"checkbox\" name=\"Personne1\" value=\"Responsable_Option\">Responsable d'option <br>");
                } else {
                    out.println("<input type=\"checkbox\" name=\"Personne1\" value=\"Responsable_Option\" checked=\"\">Responsable d'option <br>");
                }
                if ((int) liste.get(4) == 0) {
                    out.println("<input type=\"checkbox\" name=\"Personne2\" value=\"Administrateur\">Administrateur <br>");
                } else {
                    out.println("<input type=\"checkbox\" name=\"Personne2\" value=\"Administrateur\" checked=\"\">Administrateur <br>");
                }

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
