<%-- 
    Document   : exportation
    Created on : 24 déc. 2014, 17:07:43
    Author     : Geoffrey
--%>


<%@page import="com.ocpappl.bdonn.BDonn"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exportation</title>
    </head>
    <body>
        <div>
            <form method="post" action="Exportation">
                <fieldset>
                    <legend>Exporter un emploi du temps</legend>

                    <legend>Choix du format :*</legend>
                    <input type="radio" name="Format" value="TXT" checked="" >txt<br>
                    <input type="radio" name="Format" value="ICS">ics<br>


                    <label for="NombreSemaine">Exporter combien de semaine ?*</label>
                    <input type="number" name="NombreSemaine" id="Repeter" min="1" max="54" value="1" ><br>

                    <label for="Semaine">A partir de la semaine </label>
                    <input type="number" name="Semaine" id="Repeter" min="1" max="53" value="1" >

                    <label for="Annee"> de l'année </label>
                    <input type="number" name="Annee" id="Repeter" min="2014" max="2100" value="2015" ><br>


                    <label>Pour quelle option ?*</label><br>
                    <select name="Option" id="Option">
                        <%
                            BDonn edt = new BDonn();
                            ArrayList<LinkedList> listeOption = edt.selectionner("Option");

                            for (int i = 0; i < listeOption.size(); i++) {
                                out.println("<option value=\"" + listeOption.get(i).get(0) + "\">" + listeOption.get(i).get(1) + "</option>");
                            }
                        %>
                    </select><br>
                    
                    
                    <label for="Path"><%out.println(System.getProperty("user.home"));%> : </label>
                    <input type="Path" id="Path" name="Path" value="\Desktop" size="20" maxlength="200" />
                    <br />

                </fieldset>
            
            <input type="submit" value="Valider"  />
            <input type="reset" value="Remettre à zéro" />
            </form>
            <p> <a href="/Pappl/PagePrincipale">Annuler</a> </p>
        </div>
    </body>
</html>
