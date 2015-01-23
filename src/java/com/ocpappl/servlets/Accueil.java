
package com.ocpappl.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocpappl.formulaires.Utilisateur;
import com.ocpappl.formulaires.Formulaire;

public class Accueil extends HttpServlet {

   
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
    }
}
