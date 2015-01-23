<%-- 
    Document   : creationMatiere
    Created on : 29 déc. 2014, 23:22:05
    Author     : Yohann
--%>

<%@page import="com.ocpappl.bdonn.BDonn"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.ArrayList"%>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une matière</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
            <form method="post" action="Creation?type=Matiere">
                <fieldset>
                    <legend>Informations sur la matière</legend>

                    <label for="Matiere_Acronyme">Acronyme <span class="requis">*</span></label>
                    <input type="text" id="Matiere_Acronyme" name="Matiere_Acronyme" value="" size="20" maxlength="100" />
                    <br />

                    <label for="Matiere_Nom">Nom <span class="requis">*</span></label>
                    <input type="text" id="Matiere_Nom" name="Matiere_Nom" value="" size="20" maxlength="100" />
                    <br />


                    <legend>Quelles options sont concernées par la matière ?*</legend>

                    <%
                        BDonn edt = new BDonn();
                        ArrayList<LinkedList> option = new ArrayList();
                        option = edt.selectionner("Option");
                        for (int i = 0; i < option.size(); i++) {
                            out.println("<input type=\"checkbox\" name=\"Option" + i + "\" value=\"" + option.get(i).get(0) + "\">"
                                    + option.get(i).get(1) + "<br>");
                        }
                    %>

                    
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" />
                <a href="/Pappl/PagePrincipale">Annuler</a>
                <a href="/Pappl/Creation?type=Option">Creer une option</a>
            </form>
        </div>
    </body>
</html>