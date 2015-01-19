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
import java.util.Calendar;
import java.util.Date;
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
                String Salle = "";
                String Intervenant = "";
                String Commentaire = "";
                if (request.getParameter("Salle") != null) {
                    Salle = request.getParameter("Salle");
                }
                if (request.getParameter("Intervenant") != null) {
                    Intervenant = request.getParameter("Intervenant");
                }
                if (request.getParameter("Commentaire") != null) {
                    Commentaire = request.getParameter("Commentaire");
                }

                if (request.getParameter("Type_De_Cours") != null) {
                    String TypeDeCours = request.getParameter("Type_De_Cours");

                    if (request.getParameter("Enseignant") != null) {
                        int prof = parseInt(request.getParameter("Enseignant"));

                        if (request.getParameter("Matiere") != null) {
                            int Matiere = parseInt(request.getParameter("Matiere"));

                            LinkedList listeOption = new LinkedList();
                            int a = 0;
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
                                if (request.getParameter("Jour") != null) {
                                    int jour = parseInt(request.getParameter("Jour"));

                                    if (request.getParameter("Annee") != null) {
                                        int annee = parseInt(request.getParameter("Annee"));

                                        if (request.getParameter("Repeter") != null) {
                                            int repeter = parseInt(request.getParameter("Repeter"));
                                            LinkedList listeDateCours = new LinkedList();

                                            int mois = parseInt(request.getParameter("Mois"));
                                            String heureDebut = request.getParameter("Heure_Debut")+":"+request.getParameter("Minute_Debut")+":00";
                                            String heureFin = request.getParameter("Heure_Fin")+":"+request.getParameter("Minute_Fin")+":00";
                                            String valeurs = new String();
                                            Calendar calendrier = Calendar.getInstance();
                                            String bDonnJour = new String();
                                            String bDonnDebut = new String();
                                            String bDonnFin = new String();
                                            int count = 0;
                                            int p = 0;
                                            int moisCorrect=0;

                                            for (int i = 0; i < repeter; i++) {
                                                calendrier.set(annee, mois-1, jour + i * 7 + p * 7);
                                                moisCorrect = calendrier.get(calendrier.MONTH)+1;
                                                //pb date + repetition
                                                bDonnJour = calendrier.get(calendrier.YEAR) + "-" + moisCorrect + "-" + calendrier.get(calendrier.DAY_OF_MONTH);
                                                bDonnDebut = bDonnJour + " " + heureDebut;
                                                bDonnFin = bDonnJour + " " + heureFin;
                                                valeurs = "'" + TypeDeCours + "'," + Matiere + "," + prof
                                                        + ",'" + bDonnDebut + "', '" + bDonnFin
                                                        + "','" + Salle + "','" + Intervenant + "','" + Commentaire + "'";

                                                try {
                                                    if (edt.testCours(bDonnJour, bDonnDebut, bDonnFin, listeOption)) {

                                                        try {
                                                            edt.creerCours(valeurs, listeOption);
                                                            listeDateCours.add(bDonnJour);
                                                            count++;

                                                        } catch (SQLException ex) {
                                                            Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                                                        }

                                                    } else {
                                                        i--;
                                                        p++;
                                                        if (count > repeter) {
                                                            i = repeter;
                                                            request.setAttribute("erreur", "arret forcé, " + count
                                                                    + " cours ont été enregistré alors qu'il n'en fallait que " + repeter);
                                                            erreur(request, response);
                                                        }

                                                    }
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }

                                            if (count == repeter) {
                                                String dateCours = new String();
                                                for(int i=0;i<listeDateCours.size();i++){
                                                dateCours = dateCours + "<br>" + listeDateCours.get(i);
                                                        }
                                                request.setAttribute("confirmation", "Les cours ont bien été enregistré aux dates suivantes : " + dateCours);
                                                confirmation(request, response);

                                            } else {
                                                request.setAttribute("erreur", "Tous les cours n'ont pas été enregistré, " + count + " cour(s) enregistrés");
                                                erreur(request, response);
                                            }

                                        } else {
                                            request.setAttribute("erreur", "Nombre de répétition manquant");
                                            erreur(request, response);
                                        }

                                    } else {
                                        request.setAttribute("erreur", "Année manquante");
                                        erreur(request, response);
                                    }

                                } else {
                                    request.setAttribute("erreur", "Jour manquant");
                                    erreur(request, response);
                                }

                            } else {
                                request.setAttribute("erreur", "Option manquante");
                                erreur(request, response);
                            }

                        } else {
                            request.setAttribute("erreur", "Matière manquante");
                            erreur(request, response);
                        }

                    } else {
                        request.setAttribute("erreur", "Enseignant manquant");
                        erreur(request, response);
                    }

                } else {
                    request.setAttribute("erreur", "Type de cours manquant");
                    erreur(request, response);
                }

                break;

            case "Matiere":
                String Matiere_Nom = request.getParameter("Matiere_Nom");
                String Matiere_Acronyme = request.getParameter("Matiere_Acronyme");
                LinkedList listeOption = new LinkedList();
                int a = 0;
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
                            request.setAttribute("confirmation", "La matière a été enregistré");
                            confirmation(request, response);

                        } catch (SQLException ex) {
                            Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        request.setAttribute("erreur", "La matière n'a pas pu être enregistré");
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
                            edt.creerOption(valeurs);
                            request.setAttribute("confirmation", "L'option a bien été enregistré");
                            confirmation(request, response);
                        } catch (SQLException ex) {
                            Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        request.setAttribute("erreur", "L'option n'a pas pu être enregistré");
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
                String Nom = request.getParameter("Nom");
                String Prenom = request.getParameter("Prenom");
                LinkedList<String> liste = new LinkedList();
                for (int i = 0; i < 3; i++) {
                    if (request.getParameter("Personne" + i) != null) {
                        liste.add(request.getParameter("Personne" + i));
                    }
                }

                if ((Nom != "") & (Prenom != "")) {
                    String valeurs = "'" + Nom + "','" + Prenom + "'";
                    try {
                        edt.creerPersonne(valeurs, liste);
                        request.setAttribute("confirmation", "La personne a bien été enregistré");
                        confirmation(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.setAttribute("erreur", "La personne n'a pas pu être enregistré");
                    erreur(request, response);

                } else {
                    request.setAttribute("erreur", "Champ(s) manquant(s)");
                    erreur(request, response);
                }

                break;

            default:
                request.setAttribute("erreur", "Type incorrect");
                erreur(request, response);
                break;
        }
    }
}
