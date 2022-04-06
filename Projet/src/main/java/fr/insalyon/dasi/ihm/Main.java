/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm;


import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Agence;
import fr.insalyon.dasi.metier.modele.Intervention;
import fr.insalyon.dasi.metier.modele.Animal;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Urgence;
import fr.insalyon.dasi.metier.modele.Livraison;
import fr.insalyon.dasi.metier.service.ServiceClient;
import fr.insalyon.dasi.metier.service.ServiceEmploye;
import fr.insalyon.dasi.metier.service.util.Message;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author tdang
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JpaUtil.init();
       initialiser();            
       // testerInscriptionClient();                
        //testerAuthentificationClient();  
        //saisirInscriptionClient();       
        
        //saisirRechercheClient();
        testerCreationIntervention(); 
        

        JpaUtil.destroy();
    }
    public static void afficherClient(Client client) {
        System.out.println("-> " + client);
    }
    
    
    public static void initialiser() {
        
        System.out.println();
        System.out.println("**** initialiser() ****");
        System.out.println();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fr.insalyon.dasi.test_Project_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        //INITIALISATION DES CLIENTS
        Date d1 = new Date(76,7,10);
        Date d2 = new Date(83,11,9);
        Date d4 = new Date (82,10,9);
        Date d5 = new Date (75,0,7);
        Date d6 = new Date (84,7,16);
        Date dateDeb = new Date();
        
        
       Client c1 = new Client("BORROTI MATIAS DANTAS", "Raphaël", "rborrotimatiasdantas4171@free.fr", "mdp1234",d1 , "8 Rue Arago, Villeurbanne","0328178508");
       Client c2 = new Client("OLMEADA MARAIS", "Nor", "nolmeadamarais1551@gmail.com", "mdp1234",d2 , "5 Rue Léon Fabre, Villeurbanne","0418932546");
       
       
       // INITIALISATION DES EMPLOYES
       Employe e1 = new Employe ("SING", "Ainhoa", "asing8183@free.fr", "mdp1234",d4 ,20, 24, true, 0, "0705224200");
       
       Employe e2 = new Employe ("ABDIULLINA", "David Alexander", "david-alexander.abdiullina@laposte.net", "mdp1234",d5 ,0, 5, true, 0,"0590232772");
       Employe e3 = new Employe ("WOAGNER", "Moez", "moez.woagner@laposte.net", "mdp1234",d6 ,20, 24, true, 0,"0832205629");
       
        //INITIALISATION DES AGENCES
       List <Employe> listeEmpA1 = new LinkedList <Employe>();
       listeEmpA1.add(e1);
       List <Employe> listeEmpA2 = new LinkedList <Employe>();
       listeEmpA2.add(e2);
       listeEmpA2.add(e3);
       
       Agence a1 = new Agence (1, "20 avenue Albert Einstein, Villeurbanne",listeEmpA1);
       Agence a2 = new Agence (2, "20 cours Emile Zola, Villeurbanne",listeEmpA2);
       
       //Urgence i1 = new Urgence (dateDeb, dateDeb, " ", " ", "il faut preparer repas",c1);
       
       //PERSISTANCE
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(e1);
            em.persist(e2);
            em.persist(e3);
            em.persist(a1);
            em.persist(a2);
            //em.persist(i1);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service", ex);
            try {
                em.getTransaction().rollback();
            }
            catch (IllegalStateException ex2) {
                // Ignorer cette exception...
            }
        } finally {
            em.close();
        }

    }
    public static void testerInscriptionClient(){
        System.out.println();
        System.out.println("**** testerInscriptionClient() ****");
        System.out.println();
        
        ServiceClient service = new ServiceClient();
        Date d3 = new Date(92,7,28);
        Client c3 = new Client("RAYES GEMEZ", "Olena", "orayesgemez5313@outlook.com", "mdp1234", d3, "12 Rue de la Prevoyance, Villeurbanne","0532731620" );
        Long idC3 = service.inscrireClient(c3);
        String objet, corps;
        if(idC3 == null)
        {
            objet = "Echec de votre inscription";
            corps = "Nous sommes désolés, veuillez réessayer plus tard!";
        } else
        {
            objet = "Succès de l'inscription";
            corps = "Bienvenue chez REACT'IF";
        }
        
        Message.envoyerMail("dsi@reactif.com", c3.getMail(), objet, corps);
    }
    
    
      public static void testerCreationIntervention(){
        System.out.println();
        System.out.println("**** testerCreationIntervention() ****");
        System.out.println();
        
        ServiceClient service = new ServiceClient();
        Date dateDeb = new Date();
        Date dateFin = new Date();
        Client login = service.checkAndConnectClient("rborrotimatiasdantas4171@free.fr","mdp1234");
        String objet, corps, sms;
        if (login == null){
            System.out.println("> Identifiant incorrect");
            
        }
        else 
        {
            Animal chien = new Animal(dateDeb, dateFin, " ", " ", "il faut promener mon chien 30 mins",login,"chien (Médor)");
            String mailClient = (chien.getDemandeur()).getMail();
            Long idInter = service.demanderIntervention(chien);
            if (idInter != null) {
                System.out.println("> Intervention créée");
            } else {
                System.out.println("> Intervention refusée");
                
            }
            System.out.println();  
         
            if(idInter == null)
            {
                objet = "Refus de demande";
                corps = "Votre demande a été refusée. Désolé !";
                
            } else
            {
                
                objet = "Confirmation de demande";
                corps = "Votre demande a été enregistrée. L'intervenant est : " + chien.getNomIntervenant();
                corps += ". Merci d'avoir utilisé REACT'IF!";
                sms = "Bonjour " + chien.getNomIntervenant();
                String className = chien.getClass().getName(); //fr.insalyon.dasi.metier.modele.animal
                String []cName = className.split("\\.");
              
                sms += ". Merci de prendre en charge l'intervention "+ cName[cName.length-1]; //Animal only
                sms += " demandée à "+chien.getDateDeb().toString() +" par "+chien.getDemandeur().getNom()+ " " + chien.getDemandeur().getAddress();
                Message.envoyerNotification(chien.getNumTelIntervenant(), sms);
            }
            Message.envoyerMail("dsi@reactif.com", mailClient, objet, corps);
            
            
            
        }
        //testerClotureIntervention();
        
    }
    
      public static void testerClotureIntervention(){
        System.out.println();
        System.out.println("**** testerClotureIntervention() ****");
        System.out.println();
        String sms;
        ServiceEmploye serviceEmp = new ServiceEmploye();
        Employe login = serviceEmp.seAuthentifierEmploye("david-alexander.abdiullina@laposte.net","mdp1234");
        
        if (login == null){
            System.out.println("> Identifiant incorrect");
            
        }
        else 
        {
            Intervention i = serviceEmp.cloturerIntervention(login);
            if (i==null){
                System.out.print("hello null is here");
            }
            String statut = i.getStatut();
            if (!"En cours".equals(statut)) {
                System.out.println("> Intervention cloturée");
                sms =  "Bonjour " + i.getNomDemandeur();
                sms += ", votre demande d'intervention du " +i.getDateDeb().toString()+ " a été cloturée à " 
                        + i.getDateDeb().toString() +". "+ i.getCommentaire() + ". Cordialement, "+ i.getNomIntervenant();
                Message.envoyerNotification(i.getNumTelDemandeur(), sms);
            } 
           
                
            else
            {
                System.out.println("> Intervention reste en cours");
            }
            
            
        }
      }
    
       public static void testerAnnulationIntervention(){
        System.out.println();
        System.out.println("**** testerAnnulationIntervention() ****");
        System.out.println();
        
        ServiceClient service = new ServiceClient();
        Client login = service.checkAndConnectClient("rborrotimatiasdantas4171@free.fr","mdp1234");
        if (login == null){
            System.out.println("> Identifiant incorrect");
            
        }
        else 
        {
            
            Intervention i = service.annulerIntervention(login);
            if (i!=null) {
                System.out.println("> Intervention annulée");
            } else {
                System.out.println("> L'annulation d'intervention non valide");
            }
            System.out.println();  
            String objet, corps, sms;
            if(i == null)
            {
                sms = "";
                objet = "Echec de l'annulation de votre demande";
                corps = "Desole! ";
            } else
            {
                
                objet = "Confirmation de l'annulation";
                corps = "Votre annulation a été enregistrée. ";
                sms = "Bonjour " + i.getNomIntervenant();
                String className = i.getClass().getName(); //fr.insalyon.dasi.metier.modele.animal
                String []cName = className.split("\\.");
              
                sms += ". L'intervention "+ cName[cName.length-1]; //Animal only
                sms += " a été annulée par client. Merci de revenir en agence.";
            }
            String mailClient = (i.getDemandeur()).getMail();

            Message.envoyerMail("dsi@reactif.com", mailClient, objet, corps);
            Message.envoyerNotification(i.getNumTelIntervenant(), sms);
        }
    }  
      
      
     public static void testerRechercheClient(){
        System.out.println();
        System.out.println("**** testerRechercheClient() ****");
        System.out.println();
        
        ServiceClient service = new ServiceClient();
        long id;
        Client c;
        
        id=1;
        c = service.rechercherClientId(id);
        if (c != null) {
            System.out.println("> Client id "+ id+ " trouvé ");
        } else {
            System.out.println("> Client" +id+ " non trouvé");
        }
        
        id=42;
        c = service.rechercherClientId(id);
        if (c != null) {
            System.out.println("> Client id "+ id+ "trouvé ");
        } else {
            System.out.println("> Client id " +id+ " non trouvé");
        }
        
    }
     
    public static void testerListeClients(){
        System.out.println();
        System.out.println("**** testerListeClient() ****");
        System.out.println();
        
        ServiceClient service = new ServiceClient();
        List<Client> maListe = service.listerClients();
        System.out.println("*** Liste des Clients");
        if (maListe != null) {
            for (Client c : maListe) {
                System.out.println("(id, Nom, Prenom) : " +c.getId()+" "+ c.getNom()+" "+c.getPrenom());
            }
        }
        else {
            System.out.println("=> ERREUR...");
        }
        System.out.println();
        
    }
    
    
    public static void  testerAuthentificationClient(){
        System.out.println();
        System.out.println("****  testerAuthentificationClient() ****");
        System.out.println();
        
        ServiceClient service = new ServiceClient();
        String mail, mdp;
        Client c;
        
        mail = "alan.turing@insa-lyon.fr";
        mdp = "Turing5678";
        c = service.checkAndConnectClient(mail,mdp);
        if (c != null) {
            System.out.println("Le client ayant l'identifiant (id, Nom, Prenom) : " +c.getId()+" "+ c.getNom()+" "+c.getPrenom()+" est connecté!");
        }
        else {
            System.out.println("=> Mail ou mot de passe est incorrect");
        }
        System.out.println();
        
        mdp = "Turing56";
        c = service.checkAndConnectClient(mail,mdp);
        if (c != null) {
            System.out.println("Le client ayant l'identifiant (id, Nom, Prenom) : " +c.getId()+" "+ c.getNom()+" "+c.getPrenom()+" est connecté!");
        }
        else {
            System.out.println("=> Mail ou mot de passe est incorrect");
        }
        System.out.println();
    }
    
    /*public static void saisirInscriptionClient() {
        ServiceClient service = new ServiceClient();
        System.out.println("**************************");
        System.out.println("** NOUVELLE INSCRIPTION **");
        System.out.println("**************************");
        System.out.println("Veuillez saisir les informations suivantes:");

        String nom = Saisie.lireChaine("Nom de famille : ");
        String prenom = Saisie.lireChaine("Prénom : ");
        String mail = Saisie.lireChaine("Mail : ");
        String motDePasse = Saisie.lireChaine("Mot de passe : ");
        String tempDate = Saisie.lireChaine("Mot de passe : ");
        double lat = Saisie.lireChaine("Mot de passe : ");
        String motDePasse = Saisie.lireChaine("Mot de passe : ");
        String motDePasse = Saisie.lireChaine("Mot de passe : ");

        Client client = new Client(nom, prenom, mail, motDePasse);
        Long idClient = service.inscrireClient(client);

        if (idClient != null) {
            System.out.println("> Succès inscription. L'id du nouveau client est : " + client.getId());
        } else {
            System.out.println("> Échec inscription");
        }
    }*/

    public static void saisirRechercheClient() {
        ServiceClient service = new ServiceClient();
        System.out.println();
        System.out.println("**************************");
        System.out.println("** RECHERCHE de CLIENTS **");
        System.out.println("**************************");
        System.out.println();
        System.out.println();
        System.out.println("** Recherche par Identifiant:");
        System.out.println();

        Integer idClient = Saisie.lireInteger("Identifiant ? [0 pour quitter] ");
        while (idClient != 0) {
            Client client = service.rechercherClientId(idClient.longValue());
            if (client != null) {
                System.out.println("=> Client #" + idClient + " trouvé");
                System.out.println("Son nom est : " + client.getPrenom()+" "+client.getNom());     
            } else {
                System.out.println("=> Client #" + idClient + " non-trouvé");
            }
            System.out.println();
            idClient = Saisie.lireInteger("Identifiant ? [0 pour quitter] ");
        }

        System.out.println();
        System.out.println("********************************");
        System.out.println("** AUTHENTIFICATION de CLIENT **");
        System.out.println("********************************");
        System.out.println();
        System.out.println();
        System.out.println("** Authentifier Client:");
        System.out.println();

        String clientMail = Saisie.lireChaine("Mail ? [0 pour quitter] ");

        while (!clientMail.equals("0")) {
            String clientMotDePasse = Saisie.lireChaine("Mot de passe ? ");
            Client client = service.checkAndConnectClient(clientMail, clientMotDePasse);
            if (client != null) {
                System.out.println("Le client ayant l'identifiant (id, Nom, Prenom) : " +client.getId()+" "+ client.getNom()+" "+client.getPrenom()+" est connecté!");
                
            } else {
                System.out.println("=> Client non-authentifié");
            }
            clientMail = Saisie.lireChaine("Mail ? [0 pour quitter] ");
        }

        System.out.println();
        System.out.println("*****************");
        System.out.println("** AU REVOIR ! **");
        System.out.println("*****************");
        System.out.println();

    }
   
}
