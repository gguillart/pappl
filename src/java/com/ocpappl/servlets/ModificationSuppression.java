/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.servlets;

import com.ocpappl.bdonn.BDonn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("objet") == null) {
            response.setContentType("text/html;charset=UTF-8");
            BDonn edt = new BDonn();
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
                default:
                    break;
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
