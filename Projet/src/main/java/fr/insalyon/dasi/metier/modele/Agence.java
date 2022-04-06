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
import java.util.List;
import javax.persistence.Temporal;
import com.google.maps.model.LatLng;
import fr.insalyon.dasi.metier.service.util.GeoNetApi;
import javax.persistence.OneToMany;


/**
 *
 * @author tdang
 */
@Entity
public class Agence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nbEmpDispo;
    private LatLng coordonnes;
    private String address;
    @OneToMany(mappedBy = "agence")
    List<Employe> listeEmployes;
    
    public Agence() {
    }

    public Agence(int nbEmpDispo, String address, List<Employe> listeEmp) {
        this.nbEmpDispo = nbEmpDispo;
        this.coordonnes = GeoNetApi.getLatLng(address);
        this.address = address;
        this.listeEmployes = listeEmp;
        for (Employe e : listeEmployes)
        {
            e.setAgence (this);

        }
    }

 

    public List<Employe> getListeEmployes() {
        return listeEmployes;
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

    public int getNbEmpDispo() {
        return nbEmpDispo;
    }

    public void majNbEmpDispoDebut() {
        //Quand un employé commence à réaliser une intervention
        this.nbEmpDispo = this.nbEmpDispo-1;
    }
    public void majNbEmpDispoFin() {
        //Quand un employé termine une intervention
        this.nbEmpDispo = this.nbEmpDispo+1;
    }
    

    

   
}
