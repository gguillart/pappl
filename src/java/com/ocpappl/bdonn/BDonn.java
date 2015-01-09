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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BDonn {

    private String login = "postgres";
    private String mdp = "EDT";
    private String dbURL = "jdbc:postgresql://localhost:5432/postgres";

    public BDonn() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException:");
            System.err.println(e.getMessage());
        }
    }

    public Connection connection() throws SQLException {

        /*try {
         Class.forName("org.postgresql.Driver");

         } catch (java.lang.ClassNotFoundException e) {
         System.err.print("ClassNotFoundException:");
         System.err.println(e.getMessage());
         }*/
        Connection con = DriverManager.getConnection(dbURL, login, mdp);
        return con;

    }

    public void deconnection(Connection con) throws SQLException {
        con.close();
        //TODO
/*
         Driver theDriver;
         try {
         theDriver = DriverManager.getDriver(dbURL);
         DriverManager.deregisterDriver(theDriver);
         } catch (SQLException ex) {
         Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    public void creerMatiere(String valeurs, LinkedList liste) throws SQLException {
        Connection con = connection();
        String query = "INSERT INTO Matiere" + "(Matiere_Acronyme, Matiere_Nom)" + " VALUES(" + valeurs + ")"
                + "RETURNING Matiere_id";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id = rs.getInt("Matiere_id");

        String ids;
        for (int i = 0; i < liste.size(); i++) {
            ids = id + "," + liste.get(i);
            String query2 = "INSERT INTO " + "Appartient" + "(Matiere_id,Option_id)" + " VALUES(" + ids + ");";
            stmt.executeUpdate(query2);
        }

        stmt.close();

        deconnection(con);

    }

    public void creer(String type, String valeurs) throws SQLException {
        Connection con = connection();
        String attributs = new String();

        switch (type) {
            case "Cours":
                attributs = "(Type_De_Cours_Nom, Matiere_id, "
                        + "Enseignant_id, Cours_Date_Debut, Cours_Date_Fin, Salle, "
                        + "Intervenant, Commentaire)";
                break;

            case "Option":
                attributs = "(Option_Acronyme, Option_Nom, Responsable_id)";
                break;

            case "Personne":
                attributs = "(Nom, Prenom, Login, Password)";
                break;

            case "Enseignant":
                attributs = "(Personne_id)";
                break;

            case "ResponsableOption":
                attributs = "(Personne_id)";
                break;

            case "Administrateur":
                attributs = "(Personne_id)";
                break;

            default:
                break;
        }

        String query = "INSERT INTO " + type + attributs + " VALUES(" + valeurs + ");";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();

        deconnection(con);

    }

    public void modifier(String type, String attributsValeurs, String id) throws SQLException {
        Connection con = connection();

        String query = "UPDATE " + type + " SET " + attributsValeurs + " WHERE "
                + id + ";";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();

        deconnection(con);

    }

    public void supprimer(String type, String id) throws SQLException {
        Connection con = connection();

        String query = "DELETE FROM " + type + " WHERE " + id + ";";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();

        deconnection(con);

    }

    public ArrayList<LinkedList> selectionner(String type) throws SQLException {
        Connection con = connection();
        String colonne = new String();
        String colonne2 = new String();
        String colonneId = new String();
        colonneId = type;
        String plus = "";
        ArrayList<LinkedList> liste = new ArrayList();

        switch (type) {
            case "Cours": //TODO ?
                break;

            case "Matiere":
                colonne = "Matiere_Acronyme";
                break;

            case "Option":
                colonne = "Option_Acronyme";
                break;

            case "Personne":
                colonne = "Nom";
                colonne2 = "Prenom";
                break;

            case "Enseignant":
                colonne = "Nom";
                colonne2 = "Prenom";
                plus = " NATURAL JOIN Personne";
                break;

            case "Responsable_Option":
                colonne = "Nom";
                colonne2 = "Prenom";
                colonneId = "Responsable";
                plus = " NATURAL JOIN Personne";
                break;

            case "Administrateur":
                colonne = "Nom";
                colonne2 = "Prenom";
                plus = " NATURAL JOIN Personne";
                break;

            default:
                break;
        }

        String query = "SELECT * FROM " + type + plus + ";";

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        while (rs.getRow() != 0) {

            LinkedList sousListe = new LinkedList();
            sousListe.add(rs.getInt(colonneId + "_id"));
            sousListe.add(rs.getString(colonne));
            if (colonne2 == "Prenom") {
                sousListe.add(rs.getString(colonne2));
            }
            liste.add(sousListe);
            rs.next();
        }

        stmt.close();
        deconnection(con);

        return liste;

    }
}
