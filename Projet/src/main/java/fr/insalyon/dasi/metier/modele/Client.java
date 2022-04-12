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
import javax.persistence.Temporal;
import com.google.maps.model.LatLng;
import fr.insalyon.dasi.metier.service.util.GeoNetApi;


/**
 *
 * @author tdang
 */
enum Civilite{M,MME,MLLE
}
@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String mail; //question 5
    private String motDePasse;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthdate;
    private LatLng coordonnes;
    private String address;
    private String numTel;
    private Civilite civil;
    
    
    protected Client() {
    }

    public Client(String civilite,String nom, String prenom, String mail, String motDePasse, Date date, String address, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.birthdate = date;
        this.address = address;
        this.numTel = tel;
        this.coordonnes = GeoNetApi.getLatLng(address);
        if (null == civilite)this.civil = Civilite.M; //par defaut
        
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

    public Date getBirthdate() {
        return birthdate;
    }

    public LatLng getCoordonnes() {
        return coordonnes;

    }
    


    public String getAddress() {
        return address;
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

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNumTel() {
        return numTel;
    }
    

    
}
