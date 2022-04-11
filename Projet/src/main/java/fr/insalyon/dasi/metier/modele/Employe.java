/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;


/**
 *
 * @author tdang
 */


@Entity
public class Employe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Civilite civil;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String mail; //question 5
    private String motDePasse;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthdate;
    //private double lat, lon; //ces attributs sont transférées dans la classe Agence
    private int heureDeb;
    private int heureFin;
    private boolean disponible;
    private double distance;
    private String numTel;
    @ManyToOne
    private Agence agence;
    
    protected Employe() {
    }

    public Employe(String civilite, String nom, String prenom, String mail, String motDePasse, Date birthdate, int heureDeb, int heureFin, boolean estDisponible, double distance, String numTel) {
       
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.birthdate = birthdate;
        //this.lat = lat;
        //this.lon = lon;
        this.heureDeb = heureDeb;
        this.heureFin = heureFin;
        this.disponible = estDisponible;
        this.distance = distance;
        this.numTel = numTel;
        if (null==civilite)this.civil = Civilite.M; //par defaut
        
        else switch (civilite) {
            case "MME":
            case "Mme":
                this.civil = Civilite.MME;
                break;
            case "MLLE":
            case "Mlle":
                this.civil = Civilite.MLLE;
                break;
            default:
                this.civil = Civilite.M; //par defaut
                break;
        }
       
    }

    public boolean estDisponible() {
        return disponible;
    }

   

    public Date getBirthdate() {
        return birthdate;
    }

    public String getNumTel() {
        return numTel;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }


    

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public double getDistance() {
        return distance;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public void setDistance (double distNouvParcours)
    {
        //Mettre a jour des infos
        this.disponible = false;
        this.distance += distNouvParcours; 
    }
    public void setDispo ()
    {
        this.disponible = true;
    }

    
}
