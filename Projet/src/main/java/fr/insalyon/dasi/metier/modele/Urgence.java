/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author tdang
 */
@Entity
public class Urgence extends Intervention {

    public Urgence() {
    }

    public Urgence(Date dateDeb, Date dateFin, String commentaire, String statut, String description, Client c) {
        super(dateDeb, dateFin, commentaire, statut, description,c);
    }
    
    
}
