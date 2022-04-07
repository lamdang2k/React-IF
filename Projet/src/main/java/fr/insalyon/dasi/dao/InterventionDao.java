/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Intervention;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
      public List<Intervention> listerInterventions(){
         EntityManager em = JpaUtil.obtenirContextePersistance();
         TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i", Intervention.class);
        return query.getResultList();
     }
     public Intervention chercherInterventionParClient(Client c){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i WHERE i.demandeur = :c", Intervention.class);
        query.setParameter("c", c);
        return query.getSingleResult();
     }
     public Intervention chercherInterventionParEmploye(Employe e){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        
        String statut = "En cours";
        TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i WHERE i.statut =:statut AND i.employe =:e ", Intervention.class);
        query.setParameter("e", e);
        query.setParameter("statut", statut);
        return query.getSingleResult();
     }
      
     public static List<Intervention> chercherInterventionsJournalieresEmploye(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Date now = new Date();
        // Today at 00:00
        Date dayStart = Date.from(date.toInstant().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).toInstant());     
        TypedQuery<ListIntervention> query = em.createQuery("SELECT i FROM Intervention i WHERE i.dateDeb >= :today AND i.employe =:e ", Intervention.class);
        query.setParameter("today",dayStart,TemporalType.DATE);
        query.setParameter("e", e);
        List<Intervention>  = (List<DemandeIntervention>) query.getResultList();
        return demande;
    }

}
     /*public List<Intervention> listerInterventions(){
         EntityManager em = JpaUtil.obtenirContextePersistance();
         TypedQuery<Intervention> query = em.createQuery("SELECT i FROM Intervention i ORDER BY c.no, Client.class);
        return query.getResultList();
     }*/
    
   
