package com.ocpappl.servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.ocpappl.bdonn.BDonn;
import com.ocpappl.beans.DateSql;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yohann
 */
@WebServlet(urlPatterns = {"/DupliquerAnnee"})
public class DupliquerAnnee extends HttpServlet {

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

    protected void supprimerCours(HttpServletRequest request, HttpServletResponse response, ArrayList<LinkedList> listeCours)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Duplication d'une Année</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Les cours suivants doivent être supprimés pour pouvoir dupliquer l'année : </h1>");
            for (int i = 0; i < listeCours.size(); i++) {
                out.println("<br><h3> le cours du : " + listeCours.get(i).get(3) + " des options : ");
                LinkedList option = (LinkedList) listeCours.get(i).get(10);
                for (int j = 0; j < option.size(); j++) {
                    out.println(option.get(j) + " ");
                }
                out.println("</h3>");
            }
            out.println("<form method=\"post\" action=\"DupliquerAnnee?JourCopier=" + request.getParameter("JourCopier")
                    + "&MoisCopier=" + request.getParameter("MoisCopier")
                    + "&AnneeCopier=" + request.getParameter("AnneeCopier")
                    + "&JourColler=" + request.getParameter("JourColler")
                    + "&MoisColler=" + request.getParameter("MoisColler")
                    + "&AnneeColler=" + request.getParameter("AnneeColler")
                    + "&Valider=1" + "\">"
                    + "<input type=\"submit\" value=\"Valider\"  />"
                    + "</form>");
            out.println("<a href=\"/Pappl/PagePrincipale\">Annuler</a>");
            out.println("</body>");
            out.println("</html>");

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/dupliquerAnnee.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ((request.getParameter("JourCopier") != "") && (request.getParameter("AnneeCopier") != "") && (request.getParameter("JourColler") != "") && (request.getParameter("AnneeColler") != "")) {
            int jourCopier = parseInt(request.getParameter("JourCopier"));
            int moisCopier = parseInt(request.getParameter("MoisCopier")) - 1;
            int anneeCopier = parseInt(request.getParameter("AnneeCopier"));
            int jourColler = parseInt(request.getParameter("JourColler"));
            int moisColler = parseInt(request.getParameter("MoisColler")) - 1;
            int anneeColler = parseInt(request.getParameter("AnneeColler"));

            Calendar calendrierCopier = Calendar.getInstance();
            Calendar calendrierColler = Calendar.getInstance();
            calendrierCopier.set(anneeCopier, moisCopier, jourCopier);
            calendrierColler.set(anneeColler, moisColler, jourColler);
            if (calendrierCopier.get(Calendar.DAY_OF_WEEK) == calendrierColler.get(Calendar.DAY_OF_WEEK)) {
                ArrayList<LinkedList> coursCopier = new ArrayList();
                ArrayList<LinkedList> coursSupprimer = new ArrayList();
                int copier = 0;
                int supprimer = 0;
                long diff = (calendrierColler.getTimeInMillis() - calendrierCopier.getTimeInMillis()) / (3600 * 24 * 1000);
                try {
                    coursCopier = edt.selectionnerCoursAnnee(request.getParameter("JourCopier"), request.getParameter("MoisCopier"), request.getParameter("AnneeCopier"));
                    copier++;
                } catch (SQLException ex) {
                    Logger.getLogger(DupliquerAnnee.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    coursSupprimer = edt.selectionnerCoursAnnee(request.getParameter("JourColler"), request.getParameter("MoisColler"), request.getParameter("AnneeColler"));
                    supprimer++;
                } catch (SQLException ex) {
                    Logger.getLogger(DupliquerAnnee.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (copier == 0) {
                    request.setAttribute("erreur", "La selection des cours à dupliquer a échoué");
                    erreur(request, response);
                } else if (supprimer == 0) {
                    request.setAttribute("erreur", "La selection des cours à supprimer a échoué");
                    erreur(request, response);
                }

                String valeurs = new String();
                int valider = 0;
                int j = 0;
                if (request.getParameter("Valider") != null) {
                    valider = parseInt(request.getParameter("Valider"));
                }
                if ((coursSupprimer.size() == 0) || (valider == 1)) {
                    if (valider == 1) {
                        for (int i = 0; i < coursSupprimer.size(); i++) {
                            String id = "Cours_id = " + coursSupprimer.get(i).get(9);
                            try {
                                edt.supprimer("Cours", id);
                                j++;
                            } catch (SQLException ex) {
                                Logger.getLogger(DupliquerAnnee.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    if (j == coursSupprimer.size()) {

                        if (coursCopier.size() != 0) {
                            j = 0;
                            String debut = new String();
                            String fin = new String();
                            int moisCalendar = 0;
                            int moisCorrect = 0;
                            Calendar calendrier = Calendar.getInstance();
                            Calendar calendrier2 = Calendar.getInstance();
                            DateSql date = new DateSql();
                            for (int i = 0; i < coursCopier.size(); i++) {
                                date.setSequence((String) coursCopier.get(i).get(3));
                                LinkedList<String> timeDebut = date.conversionAnneeMoisJourHoraire();
                                moisCalendar = parseInt(timeDebut.get(1)) - 1;
                                calendrier.set(parseInt(timeDebut.get(0)), moisCalendar, parseInt(timeDebut.get(2)));
                                calendrier.add(Calendar.DATE, (int) diff);
                                moisCorrect = calendrier.get(Calendar.MONTH) + 1;
                                debut = calendrier.get(Calendar.YEAR) + "-" + moisCorrect + "-" + calendrier.get(Calendar.DATE) + " " + timeDebut.get(3);

                                date.setSequence((String) coursCopier.get(i).get(4));
                                LinkedList<String> timeFin = date.conversionAnneeMoisJourHoraire();
                                moisCalendar = parseInt(timeFin.get(1)) - 1;
                                calendrier2.set(parseInt(timeFin.get(0)), moisCalendar, parseInt(timeFin.get(2)));
                                calendrier2.add(Calendar.DATE, (int) diff);
                                moisCorrect = calendrier2.get(Calendar.MONTH) + 1;
                                fin = calendrier2.get(Calendar.YEAR) + "-" + moisCorrect + "-" + calendrier2.get(Calendar.DATE) + " " + timeFin.get(3);

                                valeurs = "'" + coursCopier.get(i).get(0) + "'," + coursCopier.get(i).get(1) + "," + coursCopier.get(i).get(2)
                                        + ",'" + debut + "', '" + fin
                                        + "','" + coursCopier.get(i).get(5) + "','" + coursCopier.get(i).get(6) + "','" + coursCopier.get(i).get(7) + "'";
                                try {
                                    edt.creerCours(valeurs, (LinkedList) coursCopier.get(i).get(8));
                                    j++;
                                } catch (SQLException ex) {
                                    Logger.getLogger(DupliquerAnnee.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if (j != coursCopier.size()) {
                                request.setAttribute("erreur", "Tout les cours n'ont pas pu être copié, seul " + j + " cours ont été copié");
                                erreur(request, response);
                            } else {
                                request.setAttribute("confirmation", "L'année a bien été dupliqué");
                                confirmation(request, response);
                            }
                        } else {
                            request.setAttribute("erreur", "Il n'y a aucun cours à copier sur la période");
                            erreur(request, response);
                        }
                    } else {
                        request.setAttribute("erreur", "tout les cours n'ont pas pu être supprimés, seul " + j + " cours ont été supprimé");
                        erreur(request, response);
                    }
                } else {
                    supprimerCours(request, response, coursSupprimer);
                }
            } else {
                request.setAttribute("erreur", "Les deux dates ne correspondent pas au même jour de la semaine");
                erreur(request, response);
            }
        } else {
            request.setAttribute("erreur", "Champ(s) manquant(s)");
            erreur(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
