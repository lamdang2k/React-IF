/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Agence;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
/**
 *
 * @author tdang
 */
public class EmployeDao {
    public void update(Employe e) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.merge(e);
    }
     public Employe getEmployeDispo(Agence a, int heure){
         Employe result = null;
         EntityManager em = JpaUtil.obtenirContextePersistance();
         TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e WHERE e.agence =:a AND e.disponible = TRUE AND e.heureDeb <= :heure AND e.heureFin > :heure", Employe.class);
         query.setParameter("a",a);
         query.setParameter("heure",heure);
        List<Employe> maListe= query.getResultList();
        if(maListe.size() > 0){
            result = maListe.get(0);
        }
        else result = null;
        return result;
     }
     public Employe seConnecter(String mail, String mdp){
        Employe result;
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e WHERE e.mail=:mail AND e.motDePasse= :motPasse", Employe.class);
        query.setParameter("mail",mail);
        query.setParameter("motPasse", mdp);
        List<Employe> maListe= query.getResultList();
        if(maListe.size() > 0){
            result = maListe.get(0);
        }
        else result = null;
        return result;
     }
      
    
}
