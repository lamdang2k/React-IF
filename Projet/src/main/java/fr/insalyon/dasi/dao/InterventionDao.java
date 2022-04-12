/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Intervention;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
/**
 *
 * @author tdang
 */
public class InterventionDao {
     public void creer(Intervention i) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(i);
    }
      public void update(Intervention i) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.merge(i);
    }
      /*public List<Intervention> listerInterventions(){
         EntityManager em = JpaUtil.obtenirContextePersistance();
         TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i", Intervention.class);
        return query.getResultList();
     }*/
      
      public List<Intervention> chercherInterventionsJournalieresEmploye(Employe e) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Date debutJournee = new Date();
        debutJournee.setHours(0);
        debutJournee.setMinutes(0);
        debutJournee.setSeconds(0);
        Date finJournee = new Date();
        finJournee.setHours(23);
        finJournee.setMinutes(59);
        finJournee.setSeconds(59); 
        TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i WHERE  i.employe =:e  AND i.dateDeb >= :debJ AND i.dateFin =:finJ ", Intervention.class);
        query.setParameter("debJ",debutJournee);
        query.setParameter("finJ",finJournee);
        query.setParameter("e", e);
        
        return query.getResultList();
    }
     public List <Intervention> chercherInterventionParClient(Client c){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i WHERE i.demandeur = :c", Intervention.class);
        query.setParameter("c", c);
        return query.getResultList();
     }
     
     public List <Intervention> chercherInterventionEnCoursParClient(Client c){
        EntityManager em = JpaUtil.obtenirContextePersistance();
         String statut = "En cours";
        TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i WHERE i.demandeur = :c AND i.statut =:statut", Intervention.class);
        query.setParameter("c", c);
        query.setParameter("statut", statut);
        return query.getResultList();
     }
     
     public Intervention chercherInterventionEnAttenteParEmploye(Employe e){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Intervention result;
        String enCours = "En cours";
        String annulee = "Annulée";
        TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i WHERE i.employe =:e AND i.statut =:enCours OR i.statut =:annulee ", Intervention.class);
        query.setParameter("e", e);
        query.setParameter("enCours", enCours);
         query.setParameter("annulee", annulee);
        List<Intervention> maListe= query.getResultList();
        if(maListe.size() > 0){
            result = maListe.get(0);
        }
        else result = null;
        return result;
     }
     
     
     public List<Date> chercherDatesDebutIntervention(Employe e){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        List<Date> result;
        String succes = "Succes";
        String echec = "Echec";
        TypedQuery<Date> query = em.createQuery("SELECT i.dateDeb FROM Intervention i WHERE i.employe =:e AND i.statut =:succes OR i.statut =:echec ", Date.class);
        query.setParameter("e", e);
        query.setParameter("succes", succes);
         query.setParameter("echec", echec);
        List<Date> maListe= query.getResultList();
        if(maListe.size() > 0){
            result = maListe;
        }
        else result = null;
        return result;
     }
     
     
     public List<Date> chercherDatesFinIntervention(Employe e){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        List<Date> result;
        String succes = "Succes";
        String echec = "Echec";
        TypedQuery<Date> query = em.createQuery("SELECT i.dateFin FROM Intervention i WHERE i.employe =:e AND i.statut =:succes OR i.statut =:echec ", Date.class);
        query.setParameter("e", e);
        query.setParameter("succes", succes);
         query.setParameter("echec", echec);
        List<Date> maListe= query.getResultList();
        if(maListe.size() > 0){
            result = maListe;
        }
        else result = null;
        return result;
     }
     
      public long compterInterventionsParEmploye(Employe e){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(i) FROM Intervention i WHERE i.employe =:e",Long.class);
        query.setParameter("e", e);
        return query.getSingleResult();
     }
       public long compterInterventionsReussiesParEmploye(Employe e){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(i) FROM Intervention i WHERE i.employe =:e AND i.statut =:statut",Long.class);
         query.setParameter("statut", "Succes");
        query.setParameter("e", e);
        return query.getSingleResult();
     }
}
  
   
