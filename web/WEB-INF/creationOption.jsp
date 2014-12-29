<%-- 
    Document   : creationOption
    Created on : 29 déc. 2014, 23:22:33
    Author     : Yohann
--%>

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
    
                    <%--TODO responsable d'option parmi les respos enregistrés--%> 
    
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" />
                <a href="/Pappl/PagePrincipale">Annuler</a>
            </form>
        </div>
    </body>
</html>