/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.servlets;

import com.ocpappl.formulaires.Formulaire;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Geoffrey
 */
public class PagePrincipale extends HttpServlet {

    public PagePrincipale() {
        super();
    }

    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
     response.setContentType("text/html;charset=UTF-8");
     try (PrintWriter out = response.getWriter()) {
         
     out.println("<!DOCTYPE html>");
     out.println("<html>");
     out.println("<head>");
     out.println("<title>Servlet ServletTest</title>");            
     out.println("</head>");
     out.println("<body>");
     out.println("<h1>Servlet ServletTest at " + request.getContextPath() + "</h1>");
     out.println("</body>");
     out.println("</html>");
     }
     }

     */
    @Override
    @SuppressWarnings("empty-statement")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String week = request.getParameter("week");
        request.setAttribute("week", week);
        String year = request.getParameter("year");
        request.setAttribute("year", year);
        this.getServletContext().getRequestDispatcher("/WEB-INF/pageprincipale.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        HttpSession session = request.getSession();
        session.setAttribute("login", login);
        Formulaire logs = new Formulaire();
        logs.verification(request);
        request.setAttribute("auth", logs);
        this.getServletContext().getRequestDispatcher("/WEB-INF/pageprincipale.jsp").forward(request, response);
    }

    public static int getYearForWeek(GregorianCalendar cal) {
        int year = cal.get(Calendar.YEAR);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        if (week == 1 && dayOfMonth > 20) {
            return year + 1;
        }

        if (week >= 52 && dayOfMonth < 10) {
            return year - 1;
        }

        return year;
    }
}
