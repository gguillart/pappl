/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.beans;

import static java.lang.Integer.parseInt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Geoffrey
 */
public class DateSql {
    
    private String sequence;
    
    public Date conversionJava() throws ParseException { // timestamp '2001-09-28 01:00:00'
       
        StringTokenizer chaine = new StringTokenizer(sequence);
        
        String annee = chaine.nextToken("-");
        String mois = chaine.nextToken("-");
        StringTokenizer suite = new StringTokenizer(chaine.nextToken("-"));
        String jour = suite.nextToken();
        
        
        

        if (parseInt(mois) < 10) {
            mois = "" + parseInt(mois);
        }

        if (parseInt(jour) < 10) {
            jour = "" + parseInt(jour);
        }
 
        String heure = suite.nextToken(": ");
        String minute = suite.nextToken(": ");
        String seconde = suite.nextToken(": ");
        

        String sequenceJava = heure+"h"+minute+" "+jour+"-"+mois+"-"+annee; 
        SimpleDateFormat sdf = new SimpleDateFormat("hh'h'mm dd-MM-yy");
        Date date = sdf.parse(sequenceJava);
      

        return date;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
    
}
