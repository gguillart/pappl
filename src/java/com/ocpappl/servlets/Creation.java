/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
                this.getServletContext().getRequestDispatcher("/WEB-INF/creation.jsp").forward(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/creation.jsp").forward(request, response);

    }
}
