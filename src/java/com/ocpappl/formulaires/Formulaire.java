/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.formulaires;

import javax.servlet.http.HttpServletRequest;



public class Formulaire {
    
    private String resultat;

    public void verification (HttpServletRequest request) {
        
        String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");
        
        if (mdp.equals(login)) {
            resultat="Vous êtes désormais connectés";
        }
        else
        {
            resultat="Mot de passe ou login incorrect";
        }
    }
    
    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    
}
