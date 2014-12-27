/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.servlets;

import com.ocpappl.formulaires.Formulaire;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String name = request.getParameter("name");
        //request.setAttribute("name", name);
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/pageprincipale.jsp").forward(request, response);
       
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Formulaire auth = new Formulaire();
        auth.verification(request);
        request.setAttribute("auth",auth);
       this.getServletContext().getRequestDispatcher("/WEB-INF/pageprincipale.jsp").forward(request, response);
    }


}
