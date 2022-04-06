/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;
import fr.insalyon.dasi.metier.modele.Client;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
/**
 *
 * @author tdang
 */
public class ClientDao {
     public void creer(Client client) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(client);
    }
     public Client chercherId(long id){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Client.class, id); 
     }
     public List<Client> listerClients(){
         EntityManager em = JpaUtil.obtenirContextePersistance();
         TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c ORDER BY c.nom ASC, c.prenom ASC", Client.class);
        return query.getResultList();
     }
    
     public Client seConnecter(String mail, String mdp){
        Client result;
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.mail=:mail AND c.motDePasse=:motPasse", Client.class);
        query.setParameter("mail",mail);
        query.setParameter("motPasse", mdp);
        List<Client> maListe= query.getResultList();
        if(maListe.size() > 0){
            result = maListe.get(0);
        }
        else result = null;
        return result;
     }

     public Client verifierMail(String mail){
        Client result;
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.mail=:mail", Client.class);
        query.setParameter("mail",mail);
        List<Client> maListe= query.getResultList();
        if(maListe.size() > 0){
            result = maListe.get(0);
        }
        else result = null;
        return result;
     }
}
