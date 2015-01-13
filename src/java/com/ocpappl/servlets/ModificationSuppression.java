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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Geoffrey
 */
public class ModificationSuppression extends HttpServlet {

    BDonn edt = new BDonn();

    protected void erreur(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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
        if (request.getParameter("objet") == null) {
            response.setContentType("text/html;charset=UTF-8");
            ArrayList<LinkedList> liste = new ArrayList();
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Que Modifier ?</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Que voulez-vous modifier ?</h1>");

                switch (request.getParameter("type")) {
                    case "Option":

                        liste = edt.selectionner(request.getParameter("type"));
                        for (int i = 0; i < liste.size(); i++) {
                            out.println(liste.get(i).get(1) + " : ");
                            out.println("<a href=\"/Pappl/ModificationSuppression?type=Option&objet=modifier&id=" + liste.get(i).get(0) + "\"> Modifier </a>");
                            out.println("<a href=\"/Pappl/ModificationSuppression?type=Option&objet=supprimer&id=" + liste.get(i).get(0) + "&nom=" + liste.get(i).get(1) + "\"> Supprimer </a><br/><br/>");
                        }

                        break;
                    case "Matiere":
                        liste = edt.selectionner(request.getParameter("type"));
                        for (int i = 0; i < liste.size(); i++) {
                            out.println(liste.get(i).get(1) + " : ");
                            out.println("<a href=\"/Pappl/ModificationSuppression?type=Matiere&objet=modifier&id=" + liste.get(i).get(0) + "\"> Modifier </a>");
                            out.println("<a href=\"/Pappl/ModificationSuppression?type=Matiere&objet=supprimer&id=" + liste.get(i).get(0) + "&nom=" + liste.get(i).get(1) + "\"> Supprimer </a><br/><br/>");
                        }
                        break;
                    case "Personne":
                        liste = edt.selectionner(request.getParameter("type"));
                        for (int i = 0; i < liste.size(); i++) {
                            out.println(liste.get(i).get(2) + " " + liste.get(i).get(1) + " : ");
                            out.println("<a href=\"/Pappl/ModificationSuppression?type=Personne&objet=modifier&id=" + liste.get(i).get(0) + "\"> Modifier </a>");
                            out.println("<a href=\"/Pappl/ModificationSuppression?type=Personne&objet=supprimer&id=" + liste.get(i).get(0) + "&nom=" + liste.get(i).get(1) + "&prenom=" + liste.get(i).get(2) + "\"> Supprimer </a><br/><br/>");
                        }
                        break;
                    case "Cours":
                        out.println("<a href=\"/Pappl/ModificationSuppression?type=Cours&objet=modifier&id=5\"> Modifier </a>");
                        out.println("<a href=\"/Pappl/ModificationSuppression?type=Cours&objet=supprimer&id=9\"> Supprimer </a><br/><br/>");

                        break;

                    default:
                        break;

                }
                out.println("</body>");
                out.println("</html>");
            } catch (SQLException ex) {
                Logger.getLogger(ModificationSuppression.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("objet", request.getParameter("objet"));
            request.setAttribute("id", request.getParameter("id"));

            if (request.getParameter("nom") != null) {
                request.setAttribute("nom", request.getParameter("nom"));
            }

            if (request.getParameter("prenom") != null) {
                request.setAttribute("prenom", request.getParameter("prenom"));

            }

            switch (request.getParameter("type")) {
                case "Option":
                    this.getServletContext().getRequestDispatcher("/WEB-INF/modificationOption.jsp").forward(request, response);
                    break;
                case "Matiere":
                    this.getServletContext().getRequestDispatcher("/WEB-INF/modificationMatiere.jsp").forward(request, response);
                    break;
                case "Personne":
                    this.getServletContext().getRequestDispatcher("/WEB-INF/modificationPersonne.jsp").forward(request, response);
                    break;
                case "Cours":
                    this.getServletContext().getRequestDispatcher("/WEB-INF/modificationCours.jsp").forward(request, response);
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (request.getParameter("type")) {
            case "Cours":
                if ("modifier".equals(request.getParameter("objet"))) {
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

                                            int mois = parseInt(request.getParameter("Mois"));
                                            String heureDebut = request.getParameter("Debut");
                                            String heureFin = request.getParameter("Fin");
                                            String valeurs = new String();
                                            Date date = new Date();
                                            String bDonnJour = new String();
                                            String bDonnDebut = new String();
                                            String bDonnFin = new String();
                                            int count;
                                            int p = 0;
                                            int i = 0;

                                            while (i == 0) {
                                                count = 0;
                                                date.setYear(annee);
                                                date.setMonth(mois);
                                                date.setDate(jour + p * 7);
                                                i++;
                                                p++;
                                                bDonnJour = date.getYear() + "-" + date.getMonth() + "-" + date.getDate();
                                                bDonnDebut = bDonnJour + " " + heureDebut;
                                                bDonnFin = bDonnJour + " " + heureFin;
                                                valeurs = "Type_De_Cours_Nom='" + TypeDeCours + "', Matiere_id=" + Matiere + ", Enseignant_id=" + prof
                                                        + ", Cours_Date_Debut='" + bDonnDebut + "', Cours_Date_Fin='" + bDonnFin
                                                        + "', Salle='" + Salle + "', Intervenant='" + Intervenant + "', Commentaire='" + Commentaire + "'";

                                                try {
                                                    count = 1;

                                                    if (edt.testCours(bDonnJour, bDonnDebut, bDonnFin, listeOption, request.getParameter("id"))) {

                                                        try {
                                                            edt.modifierCours(request.getParameter("id"), valeurs, listeOption);
                                                            count = 2;

                                                            request.setAttribute("confirmation", "Les modifications du cours ont bien été enregistré");
                                                            confirmation(request, response);

                                                        } catch (SQLException ex) {
                                                            Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                                                        }

                                                    } else {
                                                        i--;
                                                        p++;
                                                        count = 2;

                                                    }
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                if (count == 0) {
                                                    request.setAttribute("erreur", "Les modifications n'ont pas été enregistré, le test de disponibilité du créneau horaire a échoué");
                                                    erreur(request, response);

                                                } else if (count == 1) {
                                                    request.setAttribute("erreur", "Les modifications n'ont pas été enregistré, la modification a échoué");
                                                    erreur(request, response);

                                                }
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

                } else if ("supprimer".equals(request.getParameter("objet"))) {
                    String condition = request.getParameter("type") + "_id=" + request.getParameter("id");

                    try {
                        edt.supprimer(request.getParameter("type"), condition);
                        request.setAttribute("confirmation", "La suppression du cours a bien été enregistré");
                        confirmation(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(ModificationSuppression.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.setAttribute("erreur", "La suppression du cours n'a pas été enregistré");
                    erreur(request, response);

                } else {
                    request.setAttribute("erreur", "Objet incorrect");
                    erreur(request, response);
                }

                break;

            case "Option":
                if ("modifier".equals(request.getParameter("objet"))) {
                    String Option_Nom = request.getParameter("Option_Nom");
                    String Option_Acronyme = request.getParameter("Option_Acronyme");
                    if (request.getParameter("Responsable_Option") != null) {
                        int respo = parseInt(request.getParameter("Responsable_Option"));

                        if ((Option_Nom != "") & (Option_Acronyme != "")) {
                            String valeurs = "Option_Acronyme='" + Option_Acronyme + "', Option_Nom='" + Option_Nom + "', Responsable_id=" + respo;
                            try {
                                edt.modifierOption(request.getParameter("id"), valeurs);
                                request.setAttribute("confirmation", "L'option a bien été modifié");
                                confirmation(request, response);
                            } catch (SQLException ex) {
                                Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            request.setAttribute("erreur", "L'option n'a pas pu être modifié");
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
                } else if ("supprimer".equals(request.getParameter("objet"))) {
                    String condition = request.getParameter("type") + "_id=" + request.getParameter("id");

                    try {
                        edt.supprimer(request.getParameter("type"), condition);
                        request.setAttribute("confirmation", "La suppression de l'option a bien été enregistré");
                        confirmation(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(ModificationSuppression.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.setAttribute("erreur", "La suppression de l'option n'a pas été enregistré");
                    erreur(request, response);

                } else {
                    request.setAttribute("erreur", "Objet incorrect");
                    erreur(request, response);
                }

            case "Matiere":
                if ("modifier".equals(request.getParameter("objet"))) {
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

                            String valeurs = "Matiere_Acronyme='" + Matiere_Acronyme + "', Matiere_Nom='" + Matiere_Nom + "'";
                            try {
                                edt.modifierMatiere(request.getParameter("id"), valeurs, listeOption);
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

                } else if ("supprimer".equals(request.getParameter("objet"))) {
                    String condition = request.getParameter("type") + "_id=" + request.getParameter("id");

                    try {
                        edt.supprimer(request.getParameter("type"), condition);
                        request.setAttribute("confirmation", "La suppression de la matière a bien été enregistré");
                        confirmation(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(ModificationSuppression.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.setAttribute("erreur", "La suppression de la matiere n'a pas été enregistré");
                    erreur(request, response);

                } else {
                    request.setAttribute("erreur", "Objet incorrect");
                    erreur(request, response);
                }
                break;

            case "Personne"://TODO
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
