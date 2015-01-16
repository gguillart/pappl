/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocpappl.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Geoffrey
 */
public class DateJava { // timestamp '2001-09-28 01:00:00'     "hh'h'mm dd-MM-yy"
    
    private Date date;
    
     public String conversionSql() {
         DateFormat dateFormat = new SimpleDateFormat("'''yyyy'-'MM'-'dd' 'hh':'mm:'00'");
        return dateFormat.format(date);
     }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DateJava(Date date) {
        this.date = date;
    }
     
     
}
