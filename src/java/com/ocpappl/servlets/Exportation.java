/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.servlets;

import com.ocpappl.bdonn.BDonn;
import com.ocpappl.beans.DateSql;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Geoffrey
 */
public class Exportation extends HttpServlet {

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

        this.getServletContext().getRequestDispatcher("/WEB-INF/exportation.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ((request.getParameter("NombreSemaine") != "") && (request.getParameter("Semaine") != "") && (request.getParameter("Annee") != "")) {
            Calendar calendrier = Calendar.getInstance();
            calendrier.setWeekDate(parseInt(request.getParameter("Annee")), parseInt(request.getParameter("Semaine")), 2);
            int moisCorrect = calendrier.get(Calendar.MONTH) + 1;
            String debut = calendrier.get(Calendar.YEAR) + "-" + moisCorrect + "-" + calendrier.get(Calendar.DATE) + " 00:00:01";
            calendrier.add(Calendar.WEEK_OF_YEAR, parseInt(request.getParameter("NombreSemaine")));
            moisCorrect = calendrier.get(Calendar.MONTH) + 1;
            String fin = calendrier.get(Calendar.YEAR) + "-" + moisCorrect + "-" + calendrier.get(Calendar.DATE) + " 00:00:01";

            ArrayList<LinkedList> listeCours = new ArrayList();
            int j = 0;

            try {
                listeCours = edt.selectionnerCoursEntre(debut, fin, request.getParameter("Option"));
                j++;
            } catch (SQLException ex) {
                Logger.getLogger(Exportation.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (j == 1) {
                if (listeCours.size() != 0) {
                    String path = System.getProperty("user.home");
                    String pathSuite = "";
                    if("".equals(request.getParameter("Path"))){
                        pathSuite = "Desktop\\";
                    } else {
                        pathSuite = request.getParameter("Path") + "\\";
                    }
                    String fileName = path + pathSuite + "EDT_de_l'option_" + listeCours.get(0).get(9) + "_semaine_"
                            + request.getParameter("Semaine") + "_annee_" + request.getParameter("Annee") + ".txt";
                    switch (request.getParameter("Format")) {
                        case "TXT":
                            BufferedWriter buffer = new BufferedWriter(new FileWriter(fileName));

                            if ("1".equals(request.getParameter("NombreSemaine"))) {
                                buffer.write("Emploi du temps de l'option " + listeCours.get(0).get(9)
                                        + " semaine " + request.getParameter("Semaine") + " année " + request.getParameter("Annee"));
                            } else {
                                buffer.write("Emploi du temps de l'option " + listeCours.get(0).get(9)
                                        + " de la semaine " + request.getParameter("Semaine") + " de l'année " + request.getParameter("Annee")
                                        + " à la semaine " + calendrier.get(Calendar.WEEK_OF_YEAR) + " de l'année " + calendrier.get(Calendar.YEAR));
                            }
                            buffer.newLine();
                            buffer.newLine();
                            DateSql dateDebut = new DateSql();
                            DateSql dateFin = new DateSql();
                            LinkedList<String> moment = new LinkedList();
                            String jour = "";
                            Calendar ancienCalendrier = Calendar.getInstance();
                            ancienCalendrier.setTimeInMillis(0);
                            for (int i = 0; i < listeCours.size(); i++) {
                                dateDebut.setSequence((String) listeCours.get(i).get(4));
                                moment = dateDebut.conversionAnneeMoisJourHoraire();
                                calendrier.set(parseInt(moment.get(0)), parseInt(moment.get(1)) - 1, parseInt(moment.get(2)));
                                if (calendrier.getTimeInMillis() == ancienCalendrier.getTimeInMillis()) {
                                    buffer.write("                      ");
                                } else {
                                    switch (calendrier.get(Calendar.DAY_OF_WEEK)) {
                                        case 7:
                                            jour = "Samedi";
                                            break;
                                        case 1:
                                            jour = "Dimanche";
                                            break;
                                        case 2:
                                            jour = "Lundi";
                                            break;
                                        case 3:
                                            jour = "Mardi";
                                            break;
                                        case 4:
                                            jour = "Mercredi";
                                            break;
                                        case 5:
                                            jour = "Jeudi";
                                            break;
                                        case 6:
                                            jour = "Vendredi";
                                            break;
                                        default:
                                            jour = "pb";
                                            break;
                                    }
                                    dateFin.setSequence((String) listeCours.get(i).get(5));
                                    buffer.newLine();
                                    buffer.write(jour + " " + moment.get(2) + "/" + moment.get(1) + "/" + moment.get(0)
                                            + " : ");
                                }

                                buffer.write(dateDebut.conversionJava() + " - " + dateFin.conversionJava() + " : "
                                        + listeCours.get(i).get(0) + " de " + listeCours.get(i).get(1) + " fait par "
                                        + listeCours.get(i).get(2) + " " + listeCours.get(i).get(3));

                                if (!("null".equals(listeCours.get(i).get(6)))) {
                                    buffer.write(" en salle " + listeCours.get(i).get(6));
                                }
                                if (!("null".equals(listeCours.get(i).get(7)))) {
                                    buffer.write(" avec " + listeCours.get(i).get(7));
                                }
                                if (!("null".equals(listeCours.get(i).get(8)))) {
                                    buffer.newLine();
                                    buffer.write("                     commentaire : " + listeCours.get(i).get(8));
                                }
                                buffer.newLine();
                                ancienCalendrier.setTimeInMillis(calendrier.getTimeInMillis());

                            }
                            buffer.close();
                            request.setAttribute("confirmation", "Les cours ont bien été enregistré dans : " + fileName);
                            confirmation(request, response);

                            break;
                        case "ICS":
                            request.setAttribute("erreur", "Format ICS non codé");
                            erreur(request, response);
                            break;
                        default:
                            request.setAttribute("erreur", "Format Incorrect");
                            erreur(request, response);
                            break;
                    }
                } else {
                    request.setAttribute("erreur", "Aucun cours à selectionner sur cette période");
                    erreur(request, response);
                }
            } else {
                request.setAttribute("erreur", "Echec de la selection des cours");
                erreur(request, response);
            }
        } else {
            request.setAttribute("erreur", "Champ(s) manquant(s)");
            erreur(request, response);
        }
    }
}
