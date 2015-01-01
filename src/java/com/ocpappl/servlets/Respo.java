/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yohann
 */
@WebServlet(name = "Respo", urlPatterns = {"/Respo"})
public class Respo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            if(request.getAttribute("Responsable_Option") != null){
            ArrayList<LinkedList> respo = new ArrayList();
            respo = (ArrayList) request.getAttribute("Responsable_Option");
            for (int i = 0; i < respo.size(); i++) {
                out.println("<input type=\"radio\" name=\"Responsable_Option\" value=\"" + respo.get(i).get(0) + "\">"
                        + respo.get(i).get(1) + "<br>");
            }
            }
            out.println("</fieldset>\n"
                    + "                <input type=\"submit\" value=\"Valider\"  />\n"
                    + "                <input type=\"reset\" value=\"Remettre à zéro\" />\n"
                    + "                <a href=\"/Pappl/PagePrincipale\">Annuler</a>\n"
                    + "                <a href=\"/Pappl/Creation?type=Personne\">Creer un responsable d'option</a>\n"
                    + "            </form>\n"
                    + "        </div>\n"
                    + "    </body>\n"
                    + "</html>");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
