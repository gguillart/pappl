<%-- 
    Document   : authentification
    Created on : 24 déc. 2014, 17:07:29
    Author     : Geoffrey
--%>



<%@page import="com.ocpappl.formulaires.Utilisateur"%>
<%@page import="com.ocpappl.formulaires.Formulaire"%>
<%@page import="com.ocpappl.servlets.Authentification"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Authentification</title>
    </head>
    <body>
        <h1>Félicitations, vous êtes désormais authentifié ! </h1>

     <%--   <p> <% HttpSession session2 = request.getSession(); 
        Utilisateur uti = (Utilisateur) session2.getAttribute("sessionUtilisateur");
        out.println(uti.getLogin()); %> </p> --%>
        
        
        
             <%--    
                <c:if test="${!empty sessionScope.sessionUtilisateur}">
                   Si l'utilisateur existe en session, alors on affiche son adresse email. 
                    <p class="succes">Vous êtes connecté(e) avec le login : ${sessionScope.sessionUtilisateur.login}</p>
                </c:if> --%>
          

        <%--
                <form method="post" action="PagePrincipale?log=j">
                    <label for="login">Login :  </label>
                    <input type="text" name="login" id="login" /><br>
                    <label for="mdp">Mot de passe : </label>
                    <input type="password" name="mdp" id="mdp" /><br> <br> 

            <input type="submit" />
        </form> --%>

 <%-- <p> Félicitation, vous êtes désormais connecté ! <% HttpSession sessionUser = request.getSession();
        Utilisateur User = (Utilisateur) sessionUser.getAttribute("sessionUtilisateur");
            out.println(User.getLogin()); %> --%> </p>
 
       <p> <a href="/Pappl/PagePrincipale">Aller vers la page principale de visualisation</a> </p> 

    </body>
</html>
