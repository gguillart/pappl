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


import com.ocpappl.beans.DateSql;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 *
 * @author Yohann
 */
public class BDonn {

    private String login = "postgres";
    private String mdp = "EDT";
    private String dbURL = "jdbc:postgresql://localhost:5432/postgres";

    /**
     * Constructeur
     */
    public BDonn() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException:");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Connection à la base de données
     * @return connection
     * @throws SQLException
     */
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

    /**
     * Déconnection de la base de données
     * @param con
     * @throws SQLException
     */
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

    /**
     * Crée une matiere liée à des options
     * @param valeurs
     * @param liste
     * @throws SQLException
     */
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

    /**
     * Crée une personne et l'associe aux tables enseignant, admin et respo d'option
     * @param valeurs
     * @param liste
     * @throws SQLException
     */
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

    /**
     * Crée un cours lié à des options
     * @param valeurs
     * @param liste
     * @throws SQLException
     */
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

    /**
     * Crée une option
     * @param valeurs
     * @throws SQLException
     */
    public void creerOption(String valeurs) throws SQLException {
        Connection con = connection();

        String query = "INSERT INTO Option(Option_Acronyme, Option_Nom, Responsable_id) VALUES(" + valeurs + ");";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();

        deconnection(con);

    }

    /**
     * crée le lien entre un cours et une option qui lui est associée
     * @param idCours
     * @param idOption
     * @throws SQLException
     */
    public void creerCoursOption(String idCours, String idOption) throws SQLException {
        Connection con = connection();

        Statement stmt = con.createStatement();

        String query = "INSERT INTO Cours_Option(Cours_id, Option_id)" + " VALUES(" + idCours + "," + idOption + ");";
        stmt.executeUpdate(query);

        stmt.close();

        deconnection(con);

    }

    /**
     * Crée un lien entre une matière et une option qui lui est associée
     * @param idMatiere
     * @param idOption
     * @throws SQLException
     */
    public void creerAppartient(String idMatiere, String idOption) throws SQLException {
        Connection con = connection();

        Statement stmt = con.createStatement();

        String query = "INSERT INTO Appartient(Matiere_id, Option_id)" + " VALUES(" + idMatiere + "," + idOption + ");";
        stmt.executeUpdate(query);

        stmt.close();

        deconnection(con);

    }

    /**
     * Crée un enseignant/admin/respo d'option à partir d'une personne
     * @param type
     * @param idPersonne
     * @throws SQLException
     */
    public void creer(String type, String idPersonne) throws SQLException {
        Connection con = connection();

        Statement stmt = con.createStatement();
        String query = "INSERT INTO " + type + "(Personne_id)" + " VALUES(" + idPersonne + ");";
        stmt.executeUpdate(query);

        stmt.close();
        deconnection(con);
    }

    /**
     * Modifie les liens avec les options et les parametres d'un cours reconnu par son identifiant 
     * @param id
     * @param attributsValeurs
     * @param liste
     * @throws SQLException
     */
    public void modifierCours(String id, String attributsValeurs, LinkedList liste) throws SQLException {
        Connection con = connection();

        ArrayList<LinkedList> listeCoursOption = selectionnerCoursOption(id);
        for (int i = 0; i < listeCoursOption.size(); i++) {
            for (int j = 0; j < liste.size(); j++) {
                if ((int) listeCoursOption.get(i).get(0) == parseInt((String) liste.get(j))) {
                    listeCoursOption.get(i).set(0, 0);
                    liste.set(j, "0");
                }
            }
        }

        for (int i = 0; i < listeCoursOption.size(); i++) {
            if ((int) listeCoursOption.get(i).get(0) != 0) {
                String condition = "Cours_Option_id = " + listeCoursOption.get(i).get(1);
                supprimer("Cours_Option", condition);

            }
        }
        int a;
        for (int i = 0; i < liste.size(); i++) {
            a = parseInt((String) liste.get(i));
            if (a != 0) {
                creerCoursOption(id, (String) liste.get(i));

            }
        }

        String query = "UPDATE Cours SET " + attributsValeurs + " WHERE Cours_id ="
                + id + ";";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();

        deconnection(con);

    }

    /**
     * Modifie les parametres d'une option reconnue par son identifiant
     * @param id
     * @param attributsValeurs
     * @throws SQLException
     */
    public void modifierOption(String id, String attributsValeurs) throws SQLException {
        Connection con = connection();

        String query = "UPDATE Option SET " + attributsValeurs + " WHERE Option_id ="
                + id + ";";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();

        deconnection(con);

    }

    /**
     * Modifie les liens avec les options et les parametres d'une matiere reconnue par son identifiant
     * @param id
     * @param attributsValeurs
     * @param liste
     * @throws SQLException
     */
    public void modifierMatiere(String id, String attributsValeurs, LinkedList liste) throws SQLException {
        Connection con = connection();

        ArrayList<LinkedList> listeAppartient = selectionnerAppartient(id);
        for (int i = 0; i < listeAppartient.size(); i++) {
            for (int j = 0; j < liste.size(); j++) {
                if ((int) listeAppartient.get(i).get(0) == parseInt((String) liste.get(j))) {
                    listeAppartient.get(i).set(0, 0);
                    liste.set(j, "0");
                }
            }
        }

        for (int i = 0; i < listeAppartient.size(); i++) {
            if ((int) listeAppartient.get(i).get(0) != 0) {
                String condition = "Appartient_id = " + listeAppartient.get(i).get(1);
                supprimer("Appartient", condition);

            }
        }
        int a;
        for (int i = 0; i < liste.size(); i++) {
            a = parseInt((String) liste.get(i));
            if (a != 0) {
                creerAppartient(id, (String) liste.get(i));

            }
        }

        String query = "UPDATE Matiere SET " + attributsValeurs + " WHERE Matiere_id ="
                + id + ";";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();

        deconnection(con);

    }

    /**
     * Modifie les liens avec les tables enseignant/admin/respo d'option et les parametres d'une personne reconnue par son identifiant
     * @param id
     * @param attributsValeurs
     * @param liste
     * @throws SQLException
     */
    public void modifierPersonne(String id, String attributsValeurs, LinkedList liste) throws SQLException {
        Connection con = connection();

        ArrayList ancienneListe = selectionnerPersonne(id);

        String query = "UPDATE Personne SET " + attributsValeurs + " WHERE Personne_id ="
                + id + ";";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        int enseignant = 0;
        int responsable = 0;
        int administrateur = 0;
        for (int i = 0; i < liste.size(); i++) {
            if ("Enseignant".equals(liste.get(i))) {
                enseignant = 1;

                if ((int) ancienneListe.get(2) == 0) {
                    creer("Enseignant", id);
                }
            }
            if ("Responsable_Option".equals(liste.get(i))) {
                responsable = 1;
                if ((int) ancienneListe.get(3) == 0) {
                    creer("Responsable_Option", id);
                }
            }
            if ("Administrateur".equals(liste.get(i))) {
                administrateur = 1;
                if ((int) ancienneListe.get(4) == 0) {
                    creer("Administrateur", id);
                }
            }
        }

        if (enseignant == 0) {
            if ((int) ancienneListe.get(2) != 0) {
                String conditionE = "Enseignant_id=" + ancienneListe.get(2);
                supprimer("Enseignant", conditionE);
            }
        }
        if (responsable == 0) {
            if ((int) ancienneListe.get(3) != 0) {
                String conditionR = "Responsable_id=" + ancienneListe.get(3);
                supprimer("Responsable_Option", conditionR);
            }
        }
        if (administrateur == 0) {
            if ((int) ancienneListe.get(4) != 0) {
                String conditionA = "Administrateur_id=" + ancienneListe.get(4);
                supprimer("Administrateur", conditionA);
            }
        }

        stmt.close();
        deconnection(con);
    }

    /**
     * Supprime les liens d'un d'objet et l'objet qui est reconnu par son type et son identifiant
     * @param type
     * @param id
     * @throws SQLException
     */
    public void supprimer(String type, String id) throws SQLException {
        Connection con = connection();

        Statement stmt = con.createStatement();
        ArrayList listeMatiere = new ArrayList();
        ArrayList listeCours = new ArrayList();

        if ("Option".equals(type)) {
            String query1 = "SELECT * FROM Matiere NATURAL JOIN Appartient NATURAL JOIN Option WHERE " + id + ";";
            ResultSet rs1 = stmt.executeQuery(query1);
            if (rs1.isBeforeFirst()) {
                rs1.next();
                while (rs1.getRow() != 0) {
                    listeMatiere.add(rs1.getInt("Matiere_id"));
                    rs1.next();
                }
            }

            String query2 = "SELECT * FROM Cours NATURAL JOIN Cours_Option NATURAL JOIN Option WHERE " + id + ";";
            ResultSet rs2 = stmt.executeQuery(query2);
            if (rs2.isBeforeFirst()) {
                rs2.next();
                while (rs2.getRow() != 0) {
                    listeCours.add(rs2.getInt("Cours_id"));
                    rs2.next();
                }
            }

        }

        String query = "DELETE FROM " + type + " CASCADE WHERE " + id + ";";

        stmt.executeUpdate(query);

        if (listeMatiere.size() != 0) {
            String Q1;
            for (int i = 0; i < listeMatiere.size(); i++) {
                Q1 = "SELECT * FROM Matiere NATURAL JOIN Appartient NATURAL JOIN Option WHERE Matiere_id=" + listeMatiere.get(i) + ";";
                ResultSet rs11 = stmt.executeQuery(Q1);
                if (!(rs11.isBeforeFirst())) {
                    String idMatiere = "Matiere_id=" + listeMatiere.get(i);
                    supprimer("Matiere", idMatiere);
                }
            }
        }

        if (listeCours.size() != 0) {
            String Q2;
            for (int i = 0; i < listeCours.size(); i++) {
                Q2 = "SELECT * FROM Cours NATURAL JOIN Cours_Option NATURAL JOIN Option WHERE Cours_id=" + listeCours.get(i) + ";";
                ResultSet rs22 = stmt.executeQuery(Q2);
                if (!(rs22.isBeforeFirst())) {
                    String idCours = "Cours_id=" + listeCours.get(i);
                    supprimer("Cours", idCours);
                }
            }
        }

        stmt.close();

        deconnection(con);

    }

    /**
     * Selectionne l'ensemble des objets d'un type et renvoie les informations les concernant
     * @param type
     * @return listeDesObjets(listeDesInformationsDUnObjet)
     * @throws SQLException
     */
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

    /**
     * Selectionne une option reconnue par son identifiant et renvoie les informations la concernant
     * @param identifiant
     * @return listeDesInformations
     * @throws SQLException
     */
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

    /**
     * Selectionne une matiere reconnue par son identifiant et renvoie les informations la concernant
     * @param identifiant
     * @return listeDesInformations
     * @throws SQLException
     */
    public ArrayList selectionnerMatiere(String identifiant) throws SQLException {
        Connection con = connection();
        int id = parseInt(identifiant);
        ArrayList liste = new ArrayList();
        String query = "SELECT * FROM Matiere NATURAL JOIN Appartient NATURAL JOIN Option WHERE Matiere_id = " + id + ";";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        liste.add(rs.getString("Matiere_Acronyme"));
        liste.add(rs.getString("Matiere_Nom"));

        ArrayList<LinkedList> optionSelectionnées = new ArrayList();
        while (rs.getRow() != 0) {
            LinkedList option = new LinkedList();
            option.add(rs.getInt("Option_id"));
            option.add(rs.getString("Option_Acronyme"));
            optionSelectionnées.add(option);
            rs.next();
        }

        liste.add(optionSelectionnées);

        stmt.close();
        deconnection(con);
        return liste;
    }

    /**
     * Selectionne une personne reconnue par son identifiant et renvoie les informations la concernant
     * @param identifiant
     * @return listeDesInformations
     * @throws SQLException
     */
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

    /**
     * Selectionne un cours reconnu par son identifiant et renvoie les informations le concernant
     * @param identifiant
     * @return listeDesInformations
     * @throws SQLException
     */
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

    /**
     * Selectionne les cours liés à l'une des options de la listeOption qui ont lieu le jour de conditionJour
     * commençant avant conditionFin et finissant apres conditionDebut et renvoie les informations les concernant
     * @param conditionJour
     * @param conditionDebut
     * @param conditionFin
     * @param listeOption
     * @return listeDesCours(listeDesInformationsDUnCours)
     * @throws SQLException
     */
    
    //TODO
    public ArrayList<LinkedList> selectionnerCours(String conditionJour, String conditionDebut, String conditionFin, LinkedList listeOption) throws SQLException {
        Connection con = connection();

        String option = " (Option_id = " + listeOption.get(0);
        if (listeOption.size() > 1) {
            for (int i = 1; i < listeOption.size(); i++) {
                option = option + " OR Option_id = " + listeOption.get(i);
            }
        }

        String plus = " NATURAL JOIN Cours_Option NATURAL JOIN Matiere WHERE (Cours_Date_Debut BETWEEN '" + conditionJour + " 00:00:00'"
                + " AND '" + conditionFin + "') AND (Cours_Date_Fin BETWEEN '" + conditionDebut
                + "' AND '" + conditionJour + " 23:59:59') AND" + option + ")";
        ArrayList liste = new ArrayList();

        String query = "SELECT * FROM " + "Cours" + plus + " ORDER BY cours_date_debut;";

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        while (rs.getRow() != 0) {
        LinkedList sousListe = new LinkedList();
            
         sousListe.add(rs.getInt("Cours_id"));

         DateSql debutSQL = new DateSql();
         debutSQL.setSequence(rs.getString("Cours_Date_Debut"));
         String debut = debutSQL.conversionJava();
            
         DateSql finSQL = new DateSql();
         finSQL.setSequence(rs.getString("Cours_Date_Fin"));
         String fin = finSQL.conversionJava();
            

            
         String matiere = rs.getString("Matiere_Acronyme");
         String typeDeCours = rs.getString("Type_De_Cours_Nom");
         String info = debut + "-" + fin + " : " + typeDeCours + " " + matiere;
         sousListe.add(info);
         liste.add(sousListe);
         rs.next();
         }

        stmt.close();
        deconnection(con);

        return liste;
    }

    /**
     * Selectionne un lien entre un cours et une option, le lien est reconnu par son identifiant et renvoie les informations le concernant
     * @param identifiant
     * @return listeDesInformations
     * @throws SQLException
     */
    public ArrayList<LinkedList> selectionnerCoursOption(String identifiant) throws SQLException {
        Connection con = connection();
        int id = parseInt(identifiant);
        ArrayList<LinkedList> liste = new ArrayList();
        String query = "SELECT * FROM Cours_Option WHERE Cours_id = " + id + ";";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        while (rs.getRow() != 0) {
            LinkedList sousListe = new LinkedList();
            sousListe.add(rs.getInt("Option_id"));
            sousListe.add(rs.getInt("Cours_Option_id"));
            liste.add(sousListe);
            rs.next();
        }

        stmt.close();
        deconnection(con);
        return liste;
    }

    /**
     * Selectionne un lien entre une option et une matiere, le lien est reconnu par son identifiant et renvoie les informations le concernant
     * @param identifiant
     * @return listeDesInformations
     * @throws SQLException
     */
    public ArrayList<LinkedList> selectionnerAppartient(String identifiant) throws SQLException {
        Connection con = connection();
        int id = parseInt(identifiant);
        ArrayList<LinkedList> liste = new ArrayList();
        String query = "SELECT * FROM Appartient WHERE Matiere_id = " + id + ";";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        while (rs.getRow() != 0) {
            LinkedList sousListe = new LinkedList();
            sousListe.add(rs.getInt("Matiere_id"));
            sousListe.add(rs.getInt("Appartient_id"));
            liste.add(sousListe);
            rs.next();
        }

        stmt.close();
        deconnection(con);
        return liste;
    }

    /**
     * Selectionne dans l'ordre chronologique les cours qui commencent entre le jour/mois/annee et le jour/mois/annee+1 
     * et renvoie les informations les concernant
     * @param jour
     * @param mois
     * @param annee
     * @return listeDesCours(listeDesInformationDUnCours)
     * @throws SQLException
     */
    public ArrayList<LinkedList> selectionnerCoursAnnee(String jour, String mois, String annee) throws SQLException {
        Connection con = connection();
        ArrayList<LinkedList> liste = new ArrayList();
        int anneeSuivante = parseInt(annee) + 1;
        String query = "SELECT * FROM Cours NATURAL JOIN Cours_Option NATURAL JOIN Option WHERE Cours_Date_Debut BETWEEN '" + annee + "-" + mois + "-" + jour
                + " 00:00:01' AND '" + anneeSuivante + "-" + mois + "-" + jour + " 00:00:01' ORDER BY Cours_Date_Debut;";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        while (rs.getRow() != 0) {

            LinkedList sousListe = new LinkedList();
            sousListe.add(rs.getString("Type_De_Cours_Nom"));//0
            sousListe.add(rs.getInt("Matiere_id"));//1
            sousListe.add(rs.getInt("Enseignant_id"));//2
            sousListe.add(rs.getString("Cours_Date_Debut"));//3
            sousListe.add(rs.getString("Cours_Date_Fin"));//4
            sousListe.add(rs.getString("Salle"));//5
            sousListe.add(rs.getString("Intervenant"));//6
            sousListe.add(rs.getString("Commentaire"));//7
            int coursId = rs.getInt("Cours_Id");
            int coursId2 = rs.getInt("Cours_Id");
            LinkedList listeOption = new LinkedList();
            LinkedList listeOption2 = new LinkedList();
            while (coursId == coursId2) {
                listeOption.add(rs.getInt("Option_id"));
                listeOption2.add(rs.getString("Option_Acronyme"));
                rs.next();
                if (rs.getRow() != 0) {
                    coursId2 = rs.getInt("Cours_Id");
                } else {
                    coursId2 = 0;
                }
            }
            sousListe.add(listeOption);//8
            sousListe.add(coursId);//9
            sousListe.add(listeOption2);//10
            liste.add(sousListe);
        }

        stmt.close();
        deconnection(con);
        return liste;

    }
    
    /**
     * Selectionne dans l'ordre chronologique les cours qui commencent entre le debut et la fin qui sont liés à l'option optionId
     * et renvoie les informations les concernant
     * @param debut
     * @param fin
     * @param optionId
     * @return listeDesCours(ListeDesInformationsDUnCours)
     * @throws SQLException
     */
    public ArrayList<LinkedList> selectionnerCoursEntre(String debut, String fin, String optionId) throws SQLException {
        Connection con = connection();
        ArrayList<LinkedList> liste = new ArrayList();
        String query = "SELECT * FROM Cours NATURAL JOIN Cours_Option NATURAL JOIN Option NATURAL JOIN Matiere NATURAL JOIN Enseignant NATURAL JOIN Personne "
                + "WHERE (Cours_Date_Debut BETWEEN '" + debut + "' AND '" + fin + "') AND (Option_id = " + optionId + ") ORDER BY Cours_Date_Debut;";
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        while (rs.getRow() != 0) {

            LinkedList sousListe = new LinkedList();
            sousListe.add(rs.getString("Type_De_Cours_Nom"));//0
            sousListe.add(rs.getString("Matiere_Nom"));//1
            sousListe.add(rs.getString("Prenom"));//2
            sousListe.add(rs.getString("Nom"));//3
            sousListe.add(rs.getString("Cours_Date_Debut"));//4
            sousListe.add(rs.getString("Cours_Date_Fin"));//5
            sousListe.add(rs.getString("Salle"));//6
            sousListe.add(rs.getString("Intervenant"));//7
            sousListe.add(rs.getString("Commentaire"));//8
            sousListe.add(rs.getString("Option_Nom"));//9
            liste.add(sousListe);
            rs.next();
        }

        stmt.close();
        deconnection(con);
        return liste;

    }

    /**
     * teste si un cours d'une des options de la listeOption commence le jour conditionJour avant conditionFin 
     * et fini le jour conditionJour après conditionDebut, renvoie vrai si cours aucun cours ne vérifie ces hypotheses
     * @param conditionJour
     * @param conditionDebut
     * @param conditionFin
     * @param listeOption
     * @return 
     * @throws SQLException
     */
    public boolean testCours(String conditionJour, String conditionDebut, String conditionFin, LinkedList listeOption) throws SQLException {
        ArrayList<LinkedList> liste = new ArrayList();
        liste = selectionnerCours(conditionJour, conditionDebut, conditionFin, listeOption);
        boolean bool = false;

        if (liste.size() == 0) {
            bool = true;
        }

        return bool;
    }

    /**
     * teste si un cours d'une des options de la listeOption commence le jour conditionJour avant conditionFin 
     * et fini le jour conditionJour après conditionDebut et n'est pas le cours reconnu par l'identifiant, 
     * renvoie vrai si aucun cours ne vérifie ces hypotheses
     * @param conditionJour
     * @param conditionDebut
     * @param conditionFin
     * @param listeOption
     * @param identifiant
     * @return
     * @throws SQLException
     */
    public boolean testCours(String conditionJour, String conditionDebut, String conditionFin, LinkedList listeOption, String identifiant) throws SQLException {
        ArrayList<LinkedList> liste = new ArrayList();
        int id = parseInt(identifiant);
        liste = selectionnerCours(conditionJour, conditionDebut, conditionFin, listeOption);
        boolean bool = true;
        int count = 0;

        if (liste.size() != 0) {
            for (int i = 0; i < liste.size(); i++) {
                if ((int) liste.get(i).get(0) != id) {
                    bool = false;
                }
            }
        }

        return bool;
    }

}
