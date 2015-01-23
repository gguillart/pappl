package com.ocpappl.formulaires;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



public final class Formulaire {

    private static final String CHAMP_LOGIN = "login";
    private static final String CHAMP_PASS = "motdepasse";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Utilisateur connecterUtilisateur(HttpServletRequest request) {
        /* Récupération des champs du formulaire */
        String login = getValeurChamp(request, CHAMP_LOGIN);
        String mdp = getValeurChamp(request, CHAMP_PASS);

        Utilisateur utilisateur = new Utilisateur();

        /* Validation du champ email. */
       try {
            validationLogin(login);
        } catch (Exception e) {
            setErreur(CHAMP_LOGIN, e.getMessage());
        } 
        utilisateur.setLogin(login);

        /* Validation du champ mot de passe. */
         try {
            validationMdp(mdp);
        } catch (Exception e) {
            setErreur(CHAMP_PASS, e.getMessage());
        } 
        utilisateur.setMdp(mdp);

        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            resultat = "Succès de la connexion.";
        } else {
            resultat = "Échec de la connexion.";
        }

        return utilisateur;
    }

    /**
     * Valide l'adresse email saisie.
     */
    private void validationLogin(String login) throws Exception {
        if (login != null && !login.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
            throw new Exception("Merci de saisir un login valide.");
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMdp(String mdp) throws Exception {
        if (mdp != null) {
            if (mdp.length() < 3) {
                throw new Exception("Le mot de passe doit contenir au moins 3 caractères.");
            }
        } else {
            throw new Exception("Merci de saisir votre mot de passe.");
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
        String valeur = request.getParameter(nomChamp);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur;
        }
    }
}
