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
import static java.lang.Integer.parseInt;
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
import java.util.StringTokenizer;

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
        String query = "INSERT INTO Matiere(Matiere_Acronyme, Matiere_Nom) VALUES(" + valeurs + ")"
                + "RETURNING Matiere_id";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id = rs.getInt("Matiere_id");

        String ids;
        for (int i = 0; i < liste.size(); i++) {
            ids = id + "," + liste.get(i);
            String query2 = "INSERT INTO Appartient(Matiere_id,Option_id) VALUES(" + ids + ");";
            stmt.executeUpdate(query2);
        }

        stmt.close();

        deconnection(con);

    }

    public void creerPersonne(String valeurs, LinkedList<String> liste) throws SQLException {
        Connection con = connection();
        String query = "INSERT INTO Personne(Nom, Prenom) VALUES(" + valeurs + ")"
                + "RETURNING Personne_id";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id = rs.getInt("Personne_id");

        String query2;
        for (int i = 0; i < liste.size(); i++) {

            query2 = "INSERT INTO " + liste.get(i) + "(Personne_id)" + " VALUES(" + id + ");";
            stmt.executeUpdate(query2);
        }

        stmt.close();

        deconnection(con);

    }

    public void creerCours(String valeurs, LinkedList liste) throws SQLException {
        Connection con = connection();

        String query = "INSERT INTO Cours(Type_De_Cours_Nom, Matiere_id, "
                + "Enseignant_id, Cours_Date_Debut, Cours_Date_Fin, Salle, "
                + "Intervenant, Commentaire) VALUES(" + valeurs + ")"
                + "RETURNING Cours_id";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id = rs.getInt("Cours_id");

        String query2;
        for (int i = 0; i < liste.size(); i++) {

            query2 = "INSERT INTO Cours_Option(Cours_id, Option_id)" + " VALUES(" + id + "," + liste.get(i) + ");";
            stmt.executeUpdate(query2);
        }

        stmt.close();

        deconnection(con);

    }

    public void creerOption(String valeurs) throws SQLException {
        Connection con = connection();

        String query = "INSERT INTO Option(Option_Acronyme, Option_Nom, Responsable_id) VALUES(" + valeurs + ");";

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

    public ArrayList selectionnerOption(String identifiant) throws SQLException {
        Connection con = connection();
        int id = parseInt(identifiant);
        ArrayList liste = new ArrayList();
        String query = "SELECT * FROM Option NATURAL JOIN Responsable_Option NATURAL JOIN Personne WHERE Option_id = " + id + ";";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        liste.add(rs.getString("Option_Acronyme"));
        liste.add(rs.getString("Option_Nom"));
        liste.add(rs.getInt("Responsable_id"));
        liste.add(rs.getString("Prenom"));
        liste.add(rs.getString("Nom"));

        stmt.close();
        deconnection(con);
        return liste;
    }

    public ArrayList selectionnerMatiere(String identifiant) throws SQLException {
        Connection con = connection();
        int id = parseInt(identifiant);
        ArrayList liste = new ArrayList();
        String query = "SELECT * FROM Matiere WHERE Matiere_id = " + id + ";";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        liste.add(rs.getString("Matiere_Acronyme"));
        liste.add(rs.getString("Matiere_Nom"));

        stmt.close();
        deconnection(con);
        return liste;
    }

    public ArrayList selectionnerPersonne(String identifiant) throws SQLException {
        Connection con = connection();
        int id = parseInt(identifiant);
        ArrayList liste = new ArrayList();
        String query = "SELECT * FROM Personne NATURAL LEFT OUTER JOIN Administrateur NATURAL LEFT OUTER JOIN Responsable_Option NATURAL LEFT OUTER JOIN Enseignant WHERE Personne_id = " + id + ";";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        liste.add(rs.getString("Nom"));
        liste.add(rs.getString("Prenom"));
        liste.add(rs.getInt("Enseignant_id"));
        liste.add(rs.getInt("Responsable_id"));
        liste.add(rs.getInt("Administrateur_id"));

        stmt.close();
        deconnection(con);
        return liste;
    }

    public ArrayList selectionnerCours(String identifiant) throws SQLException {
        Connection con = connection();
        int id = parseInt(identifiant);
        ArrayList liste = new ArrayList();
        String query = "SELECT * FROM Cours NATURAL JOIN Enseignant NATURAL JOIN Personne NATURAL JOIN Cours_Option"
                + " NATURAL JOIN Option NATURAL JOIN Matiere WHERE Cours_id = " + id + ";";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        liste.add(rs.getString("Salle"));
        liste.add(rs.getString("Intervenant"));
        liste.add(rs.getString("Commentaire"));
        liste.add(rs.getString("Type_De_Cours_Nom"));
        liste.add(rs.getInt("Enseignant_id"));
        liste.add(rs.getString("Prenom"));
        liste.add(rs.getString("Nom"));
        liste.add(rs.getInt("Matiere_id"));
        liste.add(rs.getString("Matiere_Acronyme"));

        StringTokenizer debut = new StringTokenizer(rs.getString("Cours_Date_Debut"));
        StringTokenizer fin = new StringTokenizer(rs.getString("Cours_Date_Fin"));
        String année = debut.nextToken("-");
        String mois = debut.nextToken("-");
        StringTokenizer suite = new StringTokenizer(debut.nextToken("-"));
        String jour = suite.nextToken();
        String heureDebut = suite.nextToken();
        fin.nextToken();
        String heureFin = fin.nextToken();

        if (parseInt(mois) < 10) {
            mois = "" + parseInt(mois);
        }

        if (parseInt(jour) < 10) {
            jour = "" + parseInt(jour);
        }

        String moisNom = new String();

        switch (mois) {
            case "1":
                moisNom = "Janvier";
                break;
            case "2":
                moisNom = "Février";
                break;
            case "3":
                moisNom = "Mars";
                break;
            case "4":
                moisNom = "Avril";
                break;
            case "5":
                moisNom = "Mai";
                break;
            case "6":
                moisNom = "Juin";
                break;
            case "7":
                moisNom = "Juillet";
                break;
            case "8":
                moisNom = "Août";
                break;
            case "9":
                moisNom = "Septembre";
                break;
            case "10":
                moisNom = "Octobre";
                break;
            case "11":
                moisNom = "Novembre";
                break;
            case "12":
                moisNom = "Décembre";
                break;
            default:
                moisNom = mois;
                break;
        }

        StringTokenizer debut2 = new StringTokenizer(rs.getString("Cours_Date_Debut"));
        StringTokenizer fin2 = new StringTokenizer(rs.getString("Cours_Date_Fin"));

        debut2.nextToken();
        fin2.nextToken();

        StringTokenizer horaireD = new StringTokenizer(debut2.nextToken());
        StringTokenizer horaireF = new StringTokenizer(fin2.nextToken());

        String heureD = horaireD.nextToken(":");
        String minuteD = horaireD.nextToken(":");
        String heureDebut2 = heureD + "h" + minuteD;

        String heureF = horaireF.nextToken(":");
        String minuteF = horaireF.nextToken(":");
        String heureFin2 = heureF + "h" + minuteF;

        ArrayList<LinkedList> optionSelectionnées = new ArrayList();
        while (rs.getRow() != 0) {
            LinkedList option = new LinkedList();
            option.add(rs.getInt("Option_id"));
            option.add(rs.getString("Option_Acronyme"));
            optionSelectionnées.add(option);
            rs.next();
        }

        liste.add(optionSelectionnées);//9
        liste.add(année);//10
        liste.add(mois);//11
        liste.add(jour);//12
        liste.add(heureDebut);//13
        liste.add(heureFin);//14
        liste.add(moisNom);//15
        liste.add(heureDebut2);//16
        liste.add(heureFin2);//17

        stmt.close();
        deconnection(con);

        return liste;
    }

    public ArrayList<LinkedList> selectionnerCours(String conditionJour, String conditionDebut, String conditionFin, LinkedList listeOption) throws SQLException {
        Connection con = connection();

        String option = " (Option_id = " + listeOption.get(0);
        if (listeOption.size() > 1) {
            for (int i = 1; i < listeOption.size(); i++) {
                option = option + " OR Option_id = " + listeOption.get(i);
            }
        }

        String plus = " NATURAL JOIN Cours_Option WHERE (Cours_Date_Debut BETWEEN '" + conditionJour + " 00:00:00'"
                + " AND '" + conditionFin + "') AND (Cours_Date_Fin BETWEEN '" + conditionDebut
                + "' AND '" + conditionJour + " 23:59:59') AND" + option + ")";
        ArrayList<LinkedList> liste = new ArrayList();

        String query = "SELECT * FROM " + "Cours" + plus + ";";

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        while (rs.getRow() != 0) {

            LinkedList sousListe = new LinkedList();
            sousListe.add(rs.getString("Cours_Date_Debut"));
            sousListe.add(rs.getString("Cours_Date_Fin"));
            liste.add(sousListe);
            rs.next();
        }

        stmt.close();
        deconnection(con);

        return liste;
    }

    public boolean testCours(String conditionJour, String conditionDebut, String conditionFin, LinkedList listeOption) throws SQLException {
        ArrayList<LinkedList> liste = new ArrayList();
        liste = selectionnerCours(conditionJour, conditionDebut, conditionFin, listeOption);
        boolean bool = false;

        if (liste.size() == 0) {
            bool = true;
        }

        return bool;
    }
}
