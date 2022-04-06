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
public class Animal extends Intervention {
    private String name;

    public Animal() {
    }
    

    public Animal(Date dateDeb, Date dateFin, String commentaire, String statut, String description, Client c, String nomAnimal) {
        super(dateDeb, dateFin, commentaire, statut, description,c);
        this.name = nomAnimal;
    }
    
    
    
    public String getName() { return this.name; }
}
