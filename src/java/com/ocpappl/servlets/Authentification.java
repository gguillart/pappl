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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Geoffrey
 */
public class Authentification extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/authentification.jsp").forward(request, response);
       
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/authentification.jsp").forward(request, response);
    }

}