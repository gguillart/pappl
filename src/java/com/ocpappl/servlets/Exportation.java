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
                    if ("".equals(request.getParameter("Path"))) {
                        pathSuite = "Desktop\\";
                    } else {
                        pathSuite = request.getParameter("Path") + "\\";
                    }

                    switch (request.getParameter("Format")) {
                        case "TXT":
                            String fileName = path + pathSuite + "EDT_de_l'option_" + listeCours.get(0).get(9) + "_semaine_"
                                    + request.getParameter("Semaine") + "_annee_" + request.getParameter("Annee") + ".txt";
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

                                if (!("".equals(listeCours.get(i).get(6)))) {
                                    buffer.write(" en salle " + listeCours.get(i).get(6));
                                }
                                if (!("".equals(listeCours.get(i).get(7)))) {
                                    buffer.write(" avec " + listeCours.get(i).get(7));
                                }
                                if (!("".equals(listeCours.get(i).get(8)))) {
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
                            /*String fileNameIcs = path + pathSuite + "EDT_de_l'option_" + listeCours.get(0).get(9) + "_semaine_"
                                    + request.getParameter("Semaine") + "_annee_" + request.getParameter("Annee") + ".ics";
                            BufferedWriter bufferIcs = new BufferedWriter(new FileWriter(fileNameIcs));*/
                            /*bufferIcs.write("BEGIN:VCALENDAR");
                             bufferIcs.newLine();
                             bufferIcs.write("X-WR-CALNAME:" + "Emploi du temps de l'option " + listeCours.get(0).get(9)
                             + " de la semaine " + request.getParameter("Semaine") + " de l'année " + request.getParameter("Annee")
                             + " à la semaine " + calendrier.get(Calendar.WEEK_OF_YEAR) + " de l'année " + calendrier.get(Calendar.YEAR));
                             bufferIcs.newLine();
                             bufferIcs.write("X-WR-CALID:65718637aae7");
                             bufferIcs.write("PRODID:EDT");
                             bufferIcs.newLine();
                             bufferIcs.write("VERSION:2.0");
                             bufferIcs.newLine();
                             bufferIcs.write("METHODE:PUBLISH");
                             bufferIcs.newLine();
                             bufferIcs.write("CALSCALE:GREGORIAN");
                             bufferIcs.newLine();
                             bufferIcs.write("X-WR-TIMEZONE:Europe/Paris");
                             bufferIcs.newLine();*/

                            /*bufferIcs.write("BEGIN:VCALENDAR\n"
                                    + "CALSCALE:GREGORIAN\n"
                                    + "PRODID:-//PAPPL\n"
                                    + "VERSION:2.0\n"
                                    + "X-WR-CALNAME:EDT\n"
                                    + "BEGIN:VTIMEZONE\n"
                                    + "TZID:Europe/Paris\n"
                                    + "BEGIN:DAYLIGHT\n"
                                    + "DTSTART:20000404T020000\n"
                                    + "RRULE:FREQ=YEARLY;BYDAY=-1SU;BYMONTH=3\n"
                                    + "TZNAME:CET\n"
                                    + "TZOFFSETFROM:+0100\n"
                                    + "TZOFFSETTO:+0200\n"
                                    + "END:DAYLIGHT\n"
                                    + "BEGIN:STANDARD\n"
                                    + "DTSTART:20001026T020000\n"
                                    + "RRULE:FREQ=YEARLY;BYDAY=-1SU;BYMONTH=10\n"
                                    + "TZNAME:CET\n"
                                    + "TZOFFSETFROM:+0200\n"
                                    + "TZOFFSETTO:+0100\n"
                                    + "END:STANDARD\n"
                                    + "END:VTIMEZONE\n");*/

                            /*
                             bufferIcs.write("BEGIN:STANDARD");
                             bufferIcs.newLine();
                             bufferIcs.write("DTSTART:16010101T030000");
                             bufferIcs.newLine();
                             bufferIcs.write("TZOFFSETTO:+0200");
                             bufferIcs.newLine();
                             bufferIcs.write("TZOFFSETFROM:+0100");
                             bufferIcs.newLine();
                             bufferIcs.write("BEGIN:VTIMEZONE");
                             bufferIcs.newLine();
                             bufferIcs.write("TZID:Europe/Paris");
                             bufferIcs.newLine();
                             bufferIcs.write("BEGIN:STANDARD");
                             bufferIcs.newLine();
                             bufferIcs.write("DTSTART:16010101T030000");
                             bufferIcs.newLine();
                             bufferIcs.write("TZOFFSETTO:+0200");
                             bufferIcs.newLine();
                             bufferIcs.write("TZOFFSETFROM:+0100");
                             bufferIcs.newLine();
                             bufferIcs.write("RRULE:FREQ=YEARLY;WKST=MO;INTERVAL=1;BYMONTH=10;BYDAY=-1SU");
                             bufferIcs.newLine();
                             bufferIcs.write("TZNAME:CET");
                             bufferIcs.newLine();
                             bufferIcs.write("END:STANDARD");
                             bufferIcs.newLine();*/
                            /*String start = "";
                            String end = "";
                            DateSql date = new DateSql();
                            LinkedList<String> detail = new LinkedList();
                            for (int i = 0; i < listeCours.size(); i++) {
                                date.setSequence((String) listeCours.get(i).get(4));
                                detail = date.conversionAnneeMoisJourHoraire();
                                start = detail.get(0) + detail.get(1) + detail.get(2) + "T" + detail.get(4);
                                date.setSequence((String) listeCours.get(i).get(5));
                                detail = date.conversionAnneeMoisJourHoraire();
                                end = detail.get(0) + detail.get(1) + detail.get(2) + "T" + detail.get(4);
                                bufferIcs.write("BEGIN:VEVENT);
                                bufferIcs.newLine();
                                bufferIcs.newLine();
                                bufferIcs.write("DTSTART;TZID=\"Europe/Paris\":" + start);//début
                                bufferIcs.newLine();
                                bufferIcs.write("DTEND;TZID=\"Europe/Paris\":" + end);//fin
                                bufferIcs.newLine();
                                bufferIcs.write("SUMMARY:" + listeCours.get(i).get(1));//matiere
                                bufferIcs.newLine();*/
                                /*bufferIcs.write("CATEGORIES:" + listeCours.get(i).get(0));//type de cours
                                bufferIcs.newLine();
                                bufferIcs.write("DESCRIPTION:" + listeCours.get(i).get(2) + listeCours.get(i).get(3));//enseignant
                                bufferIcs.newLine();*/
                                /*if (!("".equals(listeCours.get(i).get(6)))) {
                                    bufferIcs.write("LOCATION:" + listeCours.get(i).get(6));//salle
                                    bufferIcs.newLine();
                                }
                                if (!("".equals(listeCours.get(i).get(7)))) {
                                    bufferIcs.write("DESCRIPTION:" + listeCours.get(i).get(7));//intervenant
                                    bufferIcs.newLine();
                                }
                                if (!("".equals(listeCours.get(i).get(8)))) {
                                    bufferIcs.write("DESCRIPTION:" + listeCours.get(i).get(8));//commentaire
                                    bufferIcs.newLine();
                                }*/
                                /*bufferIcs.write("END:VENVENT");
                                bufferIcs.newLine();
                            }
                            
                            bufferIcs.write("END:VCALENDAR");
                            bufferIcs.close();*/
                            request.setAttribute("erreur", "Format Ics non-implémenté");
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
