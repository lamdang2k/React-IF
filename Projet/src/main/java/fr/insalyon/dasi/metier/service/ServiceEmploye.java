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
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Intervention;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author tdang
 */
public class ServiceEmploye {

    protected ClientDao clientDao = new ClientDao();
    protected InterventionDao interDao = new InterventionDao();
    protected AgenceDao agenceDao = new AgenceDao();
    protected EmployeDao employeDao = new EmployeDao();
  

    public Employe seAuthentifierEmploye(String mail, String mdp) {
        //Service pour authentifier un employe existant
        Employe result = null;
        JpaUtil.creerContextePersistance();
        try {
            result = employeDao.seConnecter(mail, mdp);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherClientParId(id)", ex);
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    public String voirHistoriquesJournaliers(Employe e) {
        //Service pour afficher historiques des interventions journalieres
        List<Intervention> histo = null;
        String infos = "";
        JpaUtil.creerContextePersistance();
        try {
            histo = interDao.chercherInterventionsJournalieresEmploye(e);
            infos += "**** Nombre d'interventions réalisées aujourd'hui: " + histo.size() + " ****\r\n";
            
            infos += "**** Liste des interventions realisées aujourd'hui ****\r\n";
            if (histo!=null)
            {
                for (Intervention i : histo)
                {
                    infos +=  "Type d'intervention : "+ i.getTypeIntervention(); //Animal only
                    infos += " - Debut : "+ i.getDateDeb().toString();
                    infos += " - Fin :" + i.getDateFin().toString();
                    infos += " - Client : " + i.getDemandeur().getNom();
                    infos += " - Lieu : " + i.getDemandeur().getAddress();
                    infos += " - Statut actuel : "+ i.getStatut();
                    infos += "\r\n";
                    
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service voirHistoriquesJournaliers", ex);
            infos = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return infos;
    }
    public Intervention afficherInterventionEnCours(Employe e){
        //Service pour afficher interventions en cours -> Correspond au bouton En Cours
        Intervention resultat = null;
        JpaUtil.creerContextePersistance();
        try {
                JpaUtil.ouvrirTransaction();
                Intervention enCours = interDao.chercherInterventionEnAttenteParEmploye(e);
                if (enCours != null)resultat = enCours;
                
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service afficherInterventionEnCours", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
             JpaUtil.fermerContextePersistance();
        }
        return resultat;
        
    }
     public Intervention cloturerIntervention (Intervention enCours, String comment, String statut) {
         //Service pour terminer une intervention
        Intervention resultat = null;
        JpaUtil.creerContextePersistance();
        try {
                JpaUtil.ouvrirTransaction();
                enCours.setDateFin();
                enCours.setStatut(statut);
                enCours.setCommentaire(comment);
                Employe intervenant = enCours.getEmploye();
                intervenant.setDispo();
                employeDao.update(intervenant);
                interDao.update(enCours);
                JpaUtil.validerTransaction();
                resultat = enCours; 
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service cloturerIntervention(Employe)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
             JpaUtil.fermerContextePersistance();
        }
        return resultat;
        
    }
     

}

