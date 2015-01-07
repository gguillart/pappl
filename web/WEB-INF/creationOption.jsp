<%-- 
    Document   : creationOption
    Created on : 29 déc. 2014, 23:22:33
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
        <title>Création d'une option</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
            <form method="post" action="Creation">
                <fieldset>
                    <legend>Informations sur l'option</legend>

                    <label for="Option_Acronyme">Acronyme <span class="requis">*</span></label>
                    <input type="text" id="Option_Acronyme" name="Option_Acronyme" value="" size="20" maxlength="20" />
                    <br />

                    <label for="Option_Nom">Nom <span class="requis">*</span></label>
                    <input type="text" id="Option_Nom" name="Option_Nom" value="" size="20" maxlength="20" />
                    <br />

                    <legend>Qui est le responsable de cette option ?</legend>

                    <%
                        BDonn edt = new BDonn();
                        ArrayList<LinkedList> respo = new ArrayList();
                        respo = edt.selectionner("Responsable_Option");
                        for (int i = 0; i < respo.size(); i++) {
                            out.println("<input type=\"radio\" name=\"Responsable_Option\" value=\"" + respo.get(i).get(0) + "\">"
                                    + respo.get(i).get(1) + " " + respo.get(i).get(2) + "<br>");
                        }
                    %>


                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" />
                <a href="/Pappl/PagePrincipale">Annuler</a>
                <a href="/Pappl/Creation?type=Personne">Creer un responsable d'option</a>
            </form>
        </div>
    </body>
</html>