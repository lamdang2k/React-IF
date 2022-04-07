/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.service;

import fr.insalyon.dasi.dao.AgenceDao;
import fr.insalyon.dasi.dao.ClientDao;
import fr.insalyon.dasi.dao.InterventionDao;
import fr.insalyon.dasi.dao.EmployeDao;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Agence;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Intervention;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.maps.model.LatLng;
import fr.insalyon.dasi.metier.service.util.GeoNetApi;
import java.util.TreeMap;

/**
 *
 * @author tdang
 */
public class ServiceClient {

    protected ClientDao clientDao = new ClientDao();
    protected InterventionDao interDao = new InterventionDao();
    protected AgenceDao agenceDao = new AgenceDao();
    protected EmployeDao employeDao = new EmployeDao();
    


    public Long inscrireClient(Client client) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
                Client result = clientDao.verifierMail(client.getMail());
                if(result == null)
                {
                JpaUtil.ouvrirTransaction();
                clientDao.creer(client);
                JpaUtil.validerTransaction();
                resultat = client.getId();
                }
            
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service inscrireClient(client)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public Client rechercherClientId(long id) {
        Client result = null;
        JpaUtil.creerContextePersistance();
        try {
            result = clientDao.chercherId(id);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherClientParId(id)", ex);
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    public List<Client> listerClients() {
        List<Client> maListe = null;
        JpaUtil.creerContextePersistance();
        try {
            maListe = clientDao.listerClients();
        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherClientParId(id)", ex);
            maListe = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return maListe;
    }

    public Client checkAndConnectClient(String mail, String mdp) {
        Client result = null;
        JpaUtil.creerContextePersistance();
        try {
            result = clientDao.seConnecter(mail, mdp);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherClientParId(id)", ex);
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
     public Long demanderIntervention (Intervention i) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
                JpaUtil.ouvrirTransaction();
                LatLng coordiClient = i.getDemandeur().getCoordonnes();
                int heureDemande = i.getDateDeb().getHours();
                Employe intervenant  = chercherEmployeDispo(coordiClient,heureDemande); //a verifier
                //chercher agence le plus proche qui a des employes dispos
                if (intervenant != null)
                // on a trouve un intervenant
                {
                    i.attribuerIntervenant(intervenant); //a verifier
                    intervenant.addIntervToList(i);
                    interDao.creer(i);
                    JpaUtil.validerTransaction();
                    resultat = i.getId();
                }
                    
            
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service creerIntervention(intervention)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
     
     public Intervention annulerIntervention (Client c){
         Intervention resultat = null;
         //JpaUtil.creerContextePersistance();
        try {
                JpaUtil.ouvrirTransaction();
                Intervention i = interDao.chercherInterventionParClient(c);
                i.annuler();
                JpaUtil.validerTransaction();
                resultat = i;
            
                    
            
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service creerIntervention(intervention)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
           // JpaUtil.fermerContextePersistance();
        }
        return resultat;
     }
     private Employe chercherEmployeDispo (LatLng coordonnesClient, int heure){
          Agence agencePlusProche = null;
          Employe intervenant = null;
        try {
            List <Agence> maListeA = agenceDao.listerAgences();
            TreeMap <Double, Agence> mapAgence = new TreeMap();
    
            for (Agence a : maListeA)
            {
                LatLng coordonnesAgence = a.getCoordonnes();
                double tempsTrajet = GeoNetApi.getTripDurationByBicycleInMinute(coordonnesAgence, coordonnesClient); //a verifier
                if(a.getNbEmpDispo()>0)
                {
                    mapAgence.put(tempsTrajet,a);
                }
            }
            
           
            if (mapAgence.size()>0)
            {
                agencePlusProche = mapAgence.get(mapAgence.firstKey());
                Employe e = employeDao.getEmployeDispo(agencePlusProche,heure);
               if (e!=null) 
                   {
                       
                        intervenant = e; //We found an available employee
                        double disNouvParcours = 2*GeoNetApi.getTripDistanceByCarInKm(coordonnesClient, agencePlusProche.getCoordonnes()); //aller-retour
                        intervenant.setDistance(disNouvParcours);
                        agencePlusProche.majNbEmpDispoDebut(); 
                   }
            }  
            
            
            
        } catch (Exception ex) {
            //ex.printStackTrace();
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service chercherEmployeDispo", ex);
            intervenant = null;
        } finally {
           
        }
        return intervenant;

    }
}

