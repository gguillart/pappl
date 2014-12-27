<%-- 
    Document   : authentification
    Created on : 24 déc. 2014, 17:07:29
    Author     : Geoffrey
--%>


<%@page import="com.ocpappl.formulaires.Formulaire"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Authentification</title>
    </head>
    <body>
        <h1>Page d'authentification</h1>
        
       <%-- <c:if test="${!empty auth.resultat}"> <p> <c:out value="${auth.resultat}"/> </p> </c:if> --%>
       
       <%
    Formulaire auth = new Formulaire();
    auth.verification();
    
    if (auth.resultat.equals("Vous êtes désormais connectés")) {
        out.println("Vous êtes désormais connectés");
    } else {
        out.println("Mot de passe ou login incorrect");
    }
           %>
        
        <form method="post" action="Authentification">
            <label for="login">Login :  </label>
            <input type="text" name="login" id="login" /><br>
            <label for="mdp">Mot de passe : </label>
            <input type="password" name="mdp" id="mdp" /><br> <br> 
            
            <input type="submit" />
        </form>
        
        <p> <a href="/Pappl/PagePrincipale">Valider</a> </p>
        <p> <a href="/Pappl/PagePrincipale">Annuler</a> </p>
        <p> <a href="/Pappl/PagePrincipale">Mot de passe oublié</a> </p>
    </body>
</html>
