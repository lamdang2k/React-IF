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
        // Service pour creer un nouveau client
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            Client result = clientDao.verifierMail(client.getMail());
            if (result == null) {
                JpaUtil.ouvrirTransaction();
                clientDao.creer(client);
                JpaUtil.validerTransaction();
                resultat = client.getId();
            }

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service inscrireClient(client)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public Client seAuthentifierClient(String mail, String mdp) {
        // Service pour authentifier un client existant
        Client result = null;
        JpaUtil.creerContextePersistance();
        try {
            result = clientDao.seConnecter(mail, mdp);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service rechercherClientParId(id)", ex);
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    public Long demanderIntervention(Intervention i) {
        // Service pour demander une nouvelle intervention
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            LatLng coordiClient = i.getDemandeur().getCoordonnes();
            int heureDemande = i.getDateDeb().getHours();
            Employe intervenant = chercherEmployeDispo(coordiClient, heureDemande);
            // chercher agence le plus proche qui a des employes dispos
            if (intervenant != null)
            // on a trouve un intervenant
            {
                i.attribuerIntervenant(intervenant); // attributer l'intervennant pour cette intervention
                interDao.creer(i);
                JpaUtil.validerTransaction();
                resultat = i.getId();
            }

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service creerIntervention(intervention)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public List<Intervention> listerInterventionEnCours(Client c)
    // Service pour lister des interventions en cours -> Pour annuler
    {
        JpaUtil.creerContextePersistance();
        List<Intervention> resultat = null;
        try {

            List<Intervention> enCours = interDao.chercherInterventionEnCoursParClient(c);
            resultat = enCours;
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service listerInterventionEnCours", ex);
            // JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;

    }

    public Employe annulerIntervention(Intervention i) {
        // Service pour annuler une intervention en cours
        Employe resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            i.setStatut("Annulée");
            interDao.update(i);
            JpaUtil.validerTransaction();
            resultat = i.getEmploye();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,
                    "Exception lors de l'appel au Service creerIntervention(intervention)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;

    }

    public List<Intervention> voirHistoriques(Client c) {
        // Service pour afficher historiques des demandes
        List<Intervention> histo = null;
        // String infos ="";
        JpaUtil.creerContextePersistance();
        try {
            histo = interDao.chercherInterventionParClient(c);
            /*
             * infos += "**** Nombre de demandes d'intervention : " + histo.size() +
             * " ****\r\n";
             * 
             * infos += "**** Liste des demandes d'intervention ****\r\n";
             * if (histo!=null)
             * {
             * for (Intervention i : histo)
             * {
             * 
             * String className = i.getClass().getName();
             * //fr.insalyon.dasi.metier.modele.animal
             * String []cName = className.split("\\.");
             * infos += "Type d'intervention : "+ cName[cName.length-1]; //Animal only
             * infos += " - Debut : "+ i.getDateDeb().toString();
             * infos += " - Fin :" + i.getDateFin().toString();
             * infos += " - Intervenant : " + i.getNomIntervenant();
             * infos += " - Statut actuel : "+ i.getStatut();
             * infos += "\r\n";
             * }
             * }
             */

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service voirHistoriques", ex);
            histo = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return histo;
    }

    private Employe chercherEmployeDispo(LatLng coordonnesClient, int heure) {
        // Service prive pour chercher un employe disponible pour realiser intervention
        // demandee
        Agence agencePlusProche = null;
        Employe intervenant = null;
        try {
            List<Agence> maListeA = agenceDao.listerAgences();
            TreeMap<Double, Agence> mapAgence = new TreeMap();

            for (Agence a : maListeA) {
                LatLng coordonnesAgence = a.getCoordonnes();
                double tempsTrajet = GeoNetApi.getTripDurationByBicycleInMinute(coordonnesAgence, coordonnesClient); // a
                                                                                                                     // verifier
                if (a.getNbEmpDispo() > 0) {
                    mapAgence.put(tempsTrajet, a);
                }
            }

            if (mapAgence.size() > 0) {
                agencePlusProche = mapAgence.get(mapAgence.firstKey());
                Employe e = employeDao.getEmployeDispo(agencePlusProche, heure);
                if (e != null) {

                    intervenant = e; // We found an available employee
                    double disNouvParcours = 2
                            * GeoNetApi.getTripDistanceByCarInKm(coordonnesClient, agencePlusProche.getCoordonnes()); // aller-retour
                    intervenant.setDistance(disNouvParcours);
                    agencePlusProche.majNbEmpDispoDebut();
                }
            }

        } catch (Exception ex) {
            // ex.printStackTrace();
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service chercherEmployeDispo",
                    ex);
            intervenant = null;
        } finally {

        }
        return intervenant;

    }
}
