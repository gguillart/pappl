<%-- 
    Document   : authentification
    Created on : 24 d�c. 2014, 17:07:29
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
        
         
    
      
        
        <form method="post" action="PagePrincipale">
            <label for="login">Login :  </label>
            <input type="text" name="login" id="login" /><br>
            <label for="mdp">Mot de passe : </label>
            <input type="password" name="mdp" id="mdp" /><br> <br> 
            
            <input type="submit" />
        </form>
        

        <p> <a href="/Pappl/PagePrincipale">Retour � la page principale</a> </p>
        <p> <a href="/Pappl/PagePrincipale">Mot de passe oubli�</a> </p>
    </body>
</html>
