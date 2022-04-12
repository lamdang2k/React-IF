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
import java.util.Date;
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
        // Service pour authentifier un employe existant
        Employe result = null;
        JpaUtil.creerContextePersistance();
        try {
            result = employeDao.seConnecter(mail, mdp);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service rechercherClientParId(id)", ex);
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    public List<Intervention> voirHistoriquesJournaliers(Employe e) {
        // Service pour afficher historiques des interventions journalieres
        List<Intervention> histo = null;
        JpaUtil.creerContextePersistance();
        try {
            histo = interDao.chercherInterventionsJournalieresEmploye(e);

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service voirHistoriquesJournaliers", ex);
            histo = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return histo;
    }

    public Intervention afficherInterventionEnCours(Employe e) {
        // Service pour afficher interventions en cours -> Correspond au bouton En Cours
        Intervention resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            Intervention enCours = interDao.chercherInterventionEnAttenteParEmploye(e);
            if (enCours != null)
                resultat = enCours;

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service afficherInterventionEnCours", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;

    }

    public Intervention cloturerIntervention(Intervention enCours, String comment, String statut) {
        // Service pour terminer une intervention
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
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service cloturerIntervention(Employe)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;

    }

    public double calculerTauxReussite(Employe e) {
        double resultat = 0;
        JpaUtil.creerContextePersistance();
        try {
            long nbInter = interDao.compterInterventionsParEmploye(e);
            long nbSucces = interDao.compterInterventionsReussiesParEmploye(e);
            if (nbInter != 0)
                resultat = nbSucces / nbInter;
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service calculerTauxReussite",
                    ex);
            resultat = 0;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;

    }

    public double calculerDureeMoyenne(Employe e) {
        double diff_in_time = 0;
        double avg_in_time = 0;
        double avg_in_min = 0;
        JpaUtil.creerContextePersistance();
        try {
            List<Date> datesDeb = interDao.chercherDatesDebutIntervention(e);
            List<Date> datesFin = interDao.chercherDatesFinIntervention(e);
            for (int i = 0; i < datesDeb.size(); i++) {
                {
                    diff_in_time += datesFin.get(i).getTime() - datesDeb.get(i).getTime();
                }
                avg_in_time = diff_in_time / datesDeb.size();
                avg_in_min = (avg_in_time / (1000 * 60)) % 60;
            }

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service calculerTauxReussite",
                    ex);
            avg_in_min = 0;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return avg_in_min;

    }

}
