/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yohann
 */

package com.ocpappl.bdonn;

import com.ocpappl.servlets.Creation;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BDonn {
    
    private String login="postgres";
    private String mdp="EDT";
    private String dbURL = "jdbc:postgresql://localhost:5432/postgres";

    
    public Connection connection() throws SQLException{
        
        try{
                Class.forName("org.postgresql.Driver");
                
            }
            catch(java.lang.ClassNotFoundException e){
                System.err.print("ClassNotFoundException:");
                System.err.println(e.getMessage());
            }
      
        Connection con = DriverManager.getConnection(dbURL,login,mdp);
        return con;
        
    }
    
    public void deconnection(Connection con) throws SQLException{
        con.close();
        
        Driver theDriver;
       try {
           theDriver = DriverManager.getDriver(dbURL);
           DriverManager.deregisterDriver(theDriver);
       } catch (SQLException ex) {
           Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
    
    public void creer(String type, String valeurs) throws SQLException{
        Connection con = connection();
        String attributs = new String();
        
        switch (type)
        {
            case "Cours" : attributs = "(Type_De_Cours_Nom, Matiere_id, "
                    + "Enseignant_id, Cours_Date_Debut, Cours_Date_Fin, Salle, "
                    + "Intervenant, Commentaire)";
                break;
            
            case "Matiere" : attributs = "(Matiere_Acronyme, Matiere_Nom)";
                break;
                
            case "Option" : attributs = "(Option_Acronyme, Option_Nom, Responsable_id)";
                break;
                
            case "Personne" : attributs = "(Nom, Prenom, Login, Password)";
                break;
                
            case "Enseignant" : attributs = "(Personne_id)";
                break;
                
            case "ResponsableOption" :  attributs = "(Personne_id)";
                break;
                
            case "Administrateur" :  attributs = "(Personne_id)";
                break;
                
            default :
                break;  
        }
           

        String query = "INSERT INTO " + type + attributs + " VALUES(" + valeurs +")";
          
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        
        deconnection(con);
        
    }
    
    public void modifier(String type, String attributsValeurs, String id) throws SQLException{
        Connection con = connection();
     

        String query = "UPDATE " + type + " SET " + attributsValeurs + " WHERE " 
                + id;
          
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        
        deconnection(con);
        
        
    }
    
    public void supprimer(String type, String id) throws SQLException{
        Connection con = connection();
     
        String query = "DELETE FROM " + type + " WHERE " + id;
          
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        
        deconnection(con);
        
    }
}
