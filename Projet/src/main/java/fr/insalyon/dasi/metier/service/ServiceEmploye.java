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
import fr.insalyon.dasi.metier.modele.Client;
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
     public Intervention cloturerIntervention (Employe e) {
        Intervention resultat = null;
        JpaUtil.creerContextePersistance();
        try {
                JpaUtil.ouvrirTransaction();
                Intervention enCours = interDao.chercherInterventionParEmploye(e);
                //List<Client> check = clientDao.listerClients();
                //if (enCours.get(0) == null) System.out.println ("null inter");
                //if (check == null) System.out.print("null clients");
                if (enCours != null)
                {
         
                    enCours.setFinSucces();
                    enCours.setCommentaire("Médor a été très sage");
                    interDao.update(enCours);
                    JpaUtil.validerTransaction();
                    resultat = enCours;
                }
                
               
                
            
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

