/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.servlets;

import com.ocpappl.bdonn.BDonn;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Geoffrey
 */
public class Creation extends HttpServlet {

    BDonn edt = new BDonn();

    protected void erreur(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Erreur</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Erreur : " + request.getAttribute("erreur") + "</h1>");
            out.println("<a href=\"/Pappl/PagePrincipale\">Retour à la page principale</a>");
            out.println("</body>");
            out.println("</html>");

        }
    }

    protected void confirmation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Confirmation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Confirmation : " + request.getAttribute("confirmation") + "</h1>");
            out.println("<a href=\"/Pappl/PagePrincipale\">Retour à la page principale</a>");
            out.println("</body>");
            out.println("</html>");

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (request.getParameter("type")) {
            case "Cours":
                this.getServletContext().getRequestDispatcher("/WEB-INF/creationCours.jsp").forward(request, response);
                break;

            case "Matiere":
                this.getServletContext().getRequestDispatcher("/WEB-INF/creationMatiere.jsp").forward(request, response);
                break;

            case "Option":
                this.getServletContext().getRequestDispatcher("/WEB-INF/creationOption.jsp").forward(request, response);
                break;

            case "Personne":
                this.getServletContext().getRequestDispatcher("/WEB-INF/creationPersonne.jsp").forward(request, response);
                break;

            default:
                request.setAttribute("erreur", "Type incorrect");
                erreur(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (request.getParameter("type")) {
            case "Cours":
                break;

            case "Matiere":
                String Matiere_Nom = request.getParameter("Matiere_Nom");
                String Matiere_Acronyme = request.getParameter("Matiere_Acronyme");
                LinkedList listeOption = new LinkedList();
                int a = -1;
                try {
                    a = edt.selectionner("Option").size();
                } catch (SQLException ex) {
                    Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int i = 0; i < a; i++) {
                    if (request.getParameter("Option" + i) != null) {
                        listeOption.add(request.getParameter("Option" + i));
                    }
                }

                if (listeOption.size() != 0) {

                    if ((Matiere_Nom != "") & (Matiere_Acronyme != "")) {

                        String valeurs = "'" + Matiere_Acronyme + "','" + Matiere_Nom + "'";
                        try {
                            edt.creerMatiere(valeurs, listeOption);
                            request.setAttribute("confirmation", "La matière a été enregistrée");
                            confirmation(request, response);

                        } catch (SQLException ex) {
                            Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        request.setAttribute("erreur", "La matière n'a pas pu être enregistrée");
                        erreur(request, response);

                    } else {
                        request.setAttribute("erreur", "Champ(s) manquant(s)");
                        erreur(request, response);
                    }

                } else {
                    request.setAttribute("erreur", "Option manquante");
                    erreur(request, response);
                }
                break;

            case "Option":
                String Option_Nom = request.getParameter("Option_Nom");
                String Option_Acronyme = request.getParameter("Option_Acronyme");
                if (request.getParameter("Responsable_Option") != null) {
                    int respo = parseInt(request.getParameter("Responsable_Option"));

                    if ((Option_Nom != "") & (Option_Acronyme != "")) {
                        String valeurs = "'" + Option_Acronyme + "','" + Option_Nom + "'," + respo;
                        try {
                            edt.creer(request.getParameter("type"), valeurs);
                            request.setAttribute("confirmation", "L'option a bien été enregistrée");
                            confirmation(request, response);
                        } catch (SQLException ex) {
                            Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        request.setAttribute("erreur", "L'option n'a pas pu être enregistrée");
                        erreur(request, response);

                    } else {
                        request.setAttribute("erreur", "Champ(s) manquant(s)");
                        erreur(request, response);
                    }

                } else {
                    request.setAttribute("erreur", "Responsable d'option manquant");
                    erreur(request, response);
                }
                break;

            case "Personne":
                break;

            default:
                request.setAttribute("erreur", "Type incorrect");
                erreur(request, response);
                break;
        }
    }
}
