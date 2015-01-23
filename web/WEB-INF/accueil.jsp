<%-- 
    Document   : accueil
    Created on : 23 janv. 2015, 01:23:46
    Author     : Geoffrey
--%>



<%@page import="com.ocpappl.formulaires.Formulaire"%>
<%@page import="com.ocpappl.servlets.Authentification"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Authentification</title>
    </head>
    <body>
        <h1>Page d'authentification</h1>


        <form method="post" action="Authentification">
            <fieldset>
                <legend>Connexion</legend>
                <br>
                <label for="login">Login <span class="requis">*</span></label>
                <input type="text" id="login" name="Login" value="<c:out value="${utilisateur.login}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['email']}</span>
                <br>
                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['motdepasse']}</span>
                <input type="submit" value="Connexion" class="sansLabel" />
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>


                <%-- Vérification de la présence d'un objet utilisateur en session --%>
                <c:if test="${!empty sessionScope.sessionUtilisateur}">
                    <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
                    <p class="succes">Vous êtes connecté(e) avec le login : ${sessionScope.sessionUtilisateur.login}</p>
                </c:if>
            </fieldset>
        </form>

        <%--
                <form method="post" action="PagePrincipale?log=j">
                    <label for="login">Login :  </label>
                    <input type="text" name="login" id="login" /><br>
                    <label for="mdp">Mot de passe : </label>
                    <input type="password" name="mdp" id="mdp" /><br> <br> 

            <input type="submit" />
        </form> --%>


       </body>
</html>
