<%-- 
    Document   : creationMatiere
    Created on : 29 déc. 2014, 23:22:05
    Author     : Yohann
--%>

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
            <form method="post" action="Creation">
                <fieldset>
                    <legend>Informations sur la matière</legend>
    
                    <label for="Matiere_Acronyme">Acronyme <span class="requis">*</span></label>
                    <input type="text" id="Matiere_Acronyme" name="Matiere_Acronyme" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="Matiere_Nom">Nom <span class="requis">*</span></label>
                    <input type="text" id="Matiere_Nom" name="Matiere_Nom" value="" size="20" maxlength="20" />
                    <br />
    
                    <%--TODO option parmi celles enregistrés--%>
    
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" />
                <a href="/Pappl/PagePrincipale">Annuler</a>
            </form>
        </div>
    </body>
</html>