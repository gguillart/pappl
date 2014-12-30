<%-- 
    Document   : creationCours
    Created on : 29 déc. 2014, 23:19:55
    Author     : Yohann
--%>

<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'un cours</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
            <form method="post" action="Creation">
                <fieldset>
                    <legend>Informations sur le cours</legend>

                    <label for="Salle">Salle </label>
                    <input type="text" id="Salle" name="Salle" value="" size="20" maxlength="20" />
                    <br />

                    <label for="Intervenant">Intervenant </label>
                    <input type="text" id="Intervenant" name="Intervenant" value="" size="20" maxlength="20" />
                    <br />

                    <label for="Commentaire">Commentaire </label>
                    <input type="text" id="Commentaire" name="Commentaire" value="" size="20" maxlength="20" />
                    <br />

                    <legend>Type de cours</legend>
                    <input type="radio" name="Type_De_Cours" value="CM">CM<br>
                    <input type="radio" name="Type_De_Cours" value="TD">TD<br>
                    <input type="radio" name="Type_De_Cours" value="TP">TP<br>
                    <input type="radio" name="Type_De_Cours" value="Conf">Conf<br>

                    <%--TODO enseignants, matieres et options parmi ceux proposés.
                    possibilités de valeurs par défaut pour les horaires du cours--%> 

                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" />
                <a href="/Pappl/PagePrincipale">Annuler</a>
            </form>
        </div>
    </body>
</html>