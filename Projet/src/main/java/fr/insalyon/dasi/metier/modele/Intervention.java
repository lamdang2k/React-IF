/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author tdang
 */
@Entity
public class Intervention implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @ManyToOne
    protected Client demandeur;
    @ManyToOne
    protected Employe employe;
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date dateDeb;
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date dateFin;
    protected String commentaire, statut, description;

    /*
     * public Intervention() {
     * this.dateDeb = new Date();
     * this.dateFin = null;
     * this.commentaire = "";
     * this.description = "";
     * this.statut = "awaiting";
     * }
     */

    public Intervention() {
    }

    public Intervention(Date dateDeb, Date dateFin, String commentaire, String statut, String description, Client c) {
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.commentaire = commentaire;
        this.statut = statut;
        this.description = description;
        this.demandeur = c;
    }

    public Client getDemandeur() {
        return demandeur;
    }

    public Employe getEmploye() {
        return employe;
    }

    public String getNomDemandeur() {
        return demandeur.getNom() + " " + demandeur.getPrenom();
    }

    public String getNomIntervenant() {
        return employe.getNom() + " " + employe.getPrenom();
    }

    public String getNumTelIntervenant() {
        return employe.getNumTel();
    }

    public String getNumTelDemandeur() {
        return demandeur.getNumTel();
    }

    /**
     * Get the value of dateFin
     *
     * @return the value of dateFin
     */
    public Date getDateFin() {
        return dateFin;
    }

    public Long getId() {
        return id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getStatut() {
        return statut;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get the value of dateDeb
     *
     * @return the value of dateDeb
     */
    public Date getDateDeb() {
        return dateDeb;
    }

    public String getTypeIntervention() {
        String className = this.getClass().getName(); // fr.insalyon.dasi.metier.modele.animal
        String[] cName = className.split("\\.");
        return cName[cName.length - 1]; // Animal only
    }

    public void setDateFin() {
        this.dateFin = new Date();
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public void attribuerIntervenant(Employe e) {
        this.employe = e;
        this.statut = "En cours";
        this.dateDeb = new Date();

    }

    public void annuler() {
        this.statut = "Annulee";
        this.dateFin = new Date();

    }

}
