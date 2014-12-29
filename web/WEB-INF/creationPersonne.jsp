<%-- 
    Document   : creationPersonne
    Created on : 29 déc. 2014, 23:22:20
    Author     : Yohann
--%>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une personne</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
            <form method="post" action="Creation">
                <fieldset>
                    <legend>Informations sur la personne</legend>
    
                    <label for="Nom">Nom <span class="requis">*</span></label>
                    <input type="text" id="Nom" name="Nom" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="Prenom">Prénom <span class="requis">*</span></label>
                    <input type="text" id="Prenom" name="Prenom" value="" size="20" maxlength="20" />
                    <br />
                    

                    <%--TODO login et password ? 
                    demander s'il s'agit d'un enseignant ou d'un responsable d'option--%> 
    
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" />
                <a href="/Pappl/PagePrincipale">Annuler</a>
            </form>
        </div>
    </body>
</html>