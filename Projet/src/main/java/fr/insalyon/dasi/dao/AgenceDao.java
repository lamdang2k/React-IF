/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;
import fr.insalyon.dasi.metier.modele.Agence;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
/**
 *
 * @author tdang
 */
public class AgenceDao {
     public List<Agence> listerAgences(){
         EntityManager em = JpaUtil.obtenirContextePersistance();
         TypedQuery<Agence> query = em.createQuery("SELECT a FROM Agence a WHERE a.nbEmpDispo >0 ORDER BY a.nbEmpDispo DESC", Agence.class);
        return query.getResultList();
     }
      
    
}
