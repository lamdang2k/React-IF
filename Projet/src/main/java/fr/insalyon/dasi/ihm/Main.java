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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static void main(String[] args) throws ParseException {
        // TODO code application logic here
        JpaUtil.init();
        initialiser();        
        Menu();
        JpaUtil.destroy();
    }
    
    public static void Menu() throws ParseException{
        Integer input;
        System.out.println("**************************");
        System.out.println("** BIENVENUE CHEZ REACT'IF **");
        System.out.println("**************************");
        do {
            System.out.println("==================== MENU ====================");
            System.out.println("Vous êtes : [1] Client [2] Employe");
             input = Saisie.lireInteger("Choisissez une fonction ou tapez 0 pour quitter l'application");
     
            switch (input){
                case 0:
                    System.out.println("Au revoir...");
                    break;
                case 1:
                    ModeClient();
                    System.out.println("Tapez 0 pour quitter l'application");
                    break;
                case 2: 
                    saisirAuthentificationEmploye();
                    System.out.println("Tapez 0 pour quitter l'application");
                    break;
                default:
                     System.out.println("Choix invalide. Choisir l'une des valeurs entre 0 et 2");
            }
        }while (input!=0);
                
               
                
    }
    private static void ModeClient() throws ParseException{
        System.out.println("** Vous êtes Client **");
        
        Integer input;
        do {
            System.out.println("Liste des services : ");
            System.out.println("1. S'inscrire");
            System.out.println("2. Se connecter");
             input = Saisie.lireInteger("Choisissez un service ou tapez 0 pour quitter le mode Client");
     
            switch (input){
                case 0:
                    System.out.println("Déconnexion...");
                    break;
                case 1:
                    saisirInscriptionClient();
                    break;
                case 2: 
                    saisirAuthentificationClient();
                    break;
                default:
                     System.out.println("Choix invalide. Choisir l'une des valeurs entre 0 et 2");
            }
        }while (input!=0);
        
    }
    public static void initialiser() {
        //Methode qui fait l'initialisation des clients, agences et employes
        
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
        Date d7 = new Date (96,1,17);
        Date d8 = new Date (82,1,16);
        Date d9 = new Date (88,7,13);
        Date d10 = new Date (89,4,16);
        Date dateDeb = new Date();
        
        
       Client c1 = new Client("M","BORROTI MATIAS DANTAS", "Raphaël", "rborrotimatiasdantas4171@free.fr", "mdp1234",d1 , "8 Rue Arago, Villeurbanne","0328178508");
       Client c2 = new Client("Mme","OLMEADA MARAIS", "Nor", "nolmeadamarais1551@gmail.com", "mdp1234",d2 , "5 Rue Léon Fabre, Villeurbanne","0418932546");
       Client c3 = new Client("M","RINERD", "Julien", "jrinerd5241@yahoo.com", "mdp1234",d10 , "4 Rue de la Jeunesse, Villeurbanne","0727252485");
       
       // INITIALISATION DES EMPLOYES
       Employe e1 = new Employe ("Mme","SING", "Ainhoa", "asing8183@free.fr", "mdp1234",d4 ,0, 24, true, 0, "0705224200");
       Employe e2 = new Employe ("M","ABDIULLINA", "David Alexander", "david-alexander.abdiullina@laposte.net", "mdp1234",d5 ,0, 5, true, 10,"0590232772");
       Employe e3 = new Employe ("M","WOAGNER", "Moez", "moez.woagner@laposte.net", "mdp1234",d6 ,0, 24, true, 5,"0832205629");
       Employe e4 = new Employe ("M","HONRY","Matteo", "matteo.honry@yahoo.com", "mdp1234", d7, 0,6,true,6,"0482381862");
       Employe e5 = new Employe ("M","CECCANI","Kevin","kevin.ceccani@hotmail.com", "mdp1234",d8,0,6,true,2, "0664426037");
       Employe e6= new Employe ("Mme","VOYRET","Alice","alice.voyret@hotmail.com", "mdp1234",d9,0,24,true,8, "0486856520");
        //INITIALISATION DES AGENCES
       List <Employe> listeEmpA1 = new LinkedList <Employe>();
       listeEmpA1.add(e1);
       listeEmpA1.add(e4);
       listeEmpA1.add(e5);
       List <Employe> listeEmpA2 = new LinkedList <Employe>();
       listeEmpA2.add(e2);
       listeEmpA2.add(e3);
       listeEmpA2.add(e6);
       
       Agence a1 = new Agence ( "20 avenue Albert Einstein, Villeurbanne",listeEmpA1);
       Agence a2 = new Agence ( "27 Rue Gabriel Péri, Villeurbanne",listeEmpA2);
       
       
       
       //PERSISTANCE
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist (c3);
            em.persist(e1);
            em.persist(e2);
            em.persist(e3);
            em.persist(e4);
            em.persist(e5);
            em.persist(e6);
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
    
    private static void clientConnecte (Client c){
        //Methode correspond au portail Client - la fenetre apparait quand le Client est connecte
        System.out.println("** Bonjour " + c.getNom() + " " + c.getPrenom() + "! **");
        
        Integer input;
        do {
            System.out.println("=========== Liste des services ================= ");
            System.out.println("1. Demander une intervention");
            System.out.println("2. Annuler une intervention en cours");
            System.out.println("3. Voir historiques");
             input = Saisie.lireInteger("Choisissez un service ou tapez 0 pour se déconnecter");
     
            switch (input){
                case 0:
                    System.out.println("Déconnexion...");
                    break;
                case 1:
                    remplirDemandeIntervention(c);
                    break;
                case 2: 
                    annulerIntervention(c);
                    break;
                case 3: 
                    voirHistoriquesClient(c);
                    break;
                default:
                     System.out.println("Choix invalide. Choisir l'une des valeurs entre 0 et 2");
            }
        }while (input!=0);    
    }
    
     private static void employeConnecte (Employe e){
         //Methode correspond au portail Employe - la fenetre apparait quand l'Employe est connecte
        System.out.println("** Bonjour " + e.getNom() + " " + e.getPrenom() + "! **");
        ServiceEmploye service = new ServiceEmploye();
        Intervention i = null;
        Integer input;
        do {
            i = service.afficherInterventionEnCours(e);
            if (i!=null)
            {
                System.out.print("\r\n");
                System.out.print("Intervention en attente: ");
                System.out.print("Type : " + i.getTypeIntervention());
                System.out.print(" - Description : " + i.getDescription());
                System.out.print(" - Lieu : " + i.getDemandeur().getAddress());
                System.out.print(" - Debut : "+ i.getDateDeb().toString());
                System.out.print( " - Client : " + i.getDemandeur().getNom());
                System.out.print("\r\n");
            
            
            }
            else{
                System.out.println("Aucune intervention en attente. ");
                
            }
            
            System.out.println("=========== Liste des services ================= ");
            System.out.println("1. Cloturer une intervention");
            System.out.println("2. Consulter historiques de la journée");
            System.out.println("3. Statistiques");
             input = Saisie.lireInteger("Choisissez un service ou tapez 0 pour se déconnecter");
     
            switch (input){
                case 0:
                    System.out.println("Déconnexion...");
                    break;
                case 1:
                    if (i!= null) 
                    {
                        String commentaire = Saisie.lireChaine("Commentaire pour cette cloture d'intervention: ");
                        String statut = Saisie.lireChaine("Statut de cloture : ");
                        Integer valide = Saisie.lireInteger("Tapez [1] pour valider votre cloture");
                       if (valide == 1)
                       {
                          confirmerClotureIntervention(i,commentaire,statut);
                       }
                        
                    }
                    break;
                case 2: 
                    voirHistoriquesEmploye(e);
                    break;
                case 3: 
                    consulterStatistiques(e);
                    break;
                default:
                     System.out.println("Choix invalide. Choisir l'une des valeurs entre 0 et 3");
            }
        }while (input!=0);    
    }
    
    
      private static void remplirDemandeIntervention(Client login)
      {
        //Methode correspond au formulaire de demande d'intervention
        Date dateDeb = new Date();
        Date dateFin = new Date();
        
        String descripiton;
        Integer valide,type;
        
        do{ 
            Intervention i = null;
            System.out.println();
            System.out.println("**** NOUVELLE DEMANDE D'INTERVENTION ****");
            System.out.println();
            System.out.println("**************************");
            System.out.println("Veuillez saisir les informations suivantes:");
            type = Saisie.lireInteger("Type d'intervention [1] Animal || [2] Livraison || [3] Urgence || [0] Retourner au menu de service: ");
        
            switch (type){
                    case 0:
                        break;
                    
                   case 1:
                       System.out.println("Intervention de type ANIMAL ");
                       String nomAnimal = Saisie.lireChaine("L'espèce de votre animal (et son nom s'il en y a un). Par exemple: chien (Médor)  : ");
                       descripiton = Saisie.lireChaine("Description détaillée de l'intervention  : ");
                       valide = Saisie.lireInteger("Tapez [1] pour valider votre demande");
                       if (valide == 1)
                       {
                           i = new Animal(dateDeb,dateFin," ", " ",descripiton,login,nomAnimal);
                           validerIntervention(i,login);
                       }
                       break;
                       
                   case 2:
                       System.out.println("Intervention de type LIVRAISON");
                       String nomObjet = Saisie.lireChaine("L'objet de livraison. Par exemple: canapé  : ");
                       descripiton = Saisie.lireChaine("Description détaillée de l'intervention  : ");
                       valide = Saisie.lireInteger("Tapez [1] pour valider votre demande");
                       if (valide == 1)
                       {
                           i = new Livraison(dateDeb,dateFin," ", " ",descripiton,login,nomObjet);
                           validerIntervention(i,login);
                           
                       }
                        break;
                       
                   case 3: 
                       System.out.println("Intervention de type URGENCE");
                       descripiton = Saisie.lireChaine("Description détaillée de l'intervention  : ");
                       valide = Saisie.lireInteger("Tapez [1] pour valider votre demande");
                       if (valide == 1)
                       {
                           i = new Urgence(dateDeb,dateFin," ", " ", descripiton,login);
                           validerIntervention(i,login);
                           
                       }
                       break;
                   default:
                        System.out.println("Choix invalide. Choisir l'une des valeurs entre 0 et 3");
               }
           }while (type!=0);    
      }
      
      private static void validerIntervention(Intervention i, Client login )
      {
          //Une fois que le formulaire est rempli, cette methode validera la nouvelle intervention
          ServiceClient service = new ServiceClient();
            String mailClient = login.getMail();
            String objet,corps,sms;
            if (i !=null)
            {
                Long idInter = service.demanderIntervention(i);
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
                    corps = "Votre demande a été enregistrée. L'intervenant est : " + i.getNomIntervenant();
                    corps += ". Merci d'avoir utilisé REACT'IF!";
                    sms = "Bonjour " + i.getNomIntervenant();
                    sms += ". Merci de prendre en charge l'intervention "+ i.getTypeIntervention(); 
                    sms += " demandée à "+i.getDateDeb().toString() +" par "+ login.getNom()+ " " + login.getAddress();
                    Message.envoyerNotification(i.getNumTelIntervenant(), sms);
                }
                Message.envoyerMail("contact@react.if", mailClient, objet, corps);
            }
    }
      
    private static void voirHistoriquesEmploye(Employe login){
        //Methode correspond au bouton Effectues (Historiques) de l'Employe
        System.out.println();
        System.out.println("**** HISTORIQUES DES INTERVENTIONS DE LA JOURNEE ****");
        System.out.println();
        
        ServiceEmploye serviceEmp = new ServiceEmploye();
        String infos = serviceEmp.voirHistoriquesJournaliers(login);
        infos += "\r\n**** Distance parcourue aujourd'hui: " + (int)login.getDistance() + " km. \r\n****";
        System.out.print(infos);
        System.out.print("\r\n");
        
    }
       
    private static void voirHistoriquesClient(Client login){
         //Methode correspond au bouton Historiques du Client
        System.out.println();
        System.out.println("**** HISTORIQUES DES COMMANDES ****");
        System.out.println();
        
        ServiceClient serviceCli = new ServiceClient();
       
        String infos = serviceCli.voirHistoriques(login);
        System.out.println(infos);
        System.out.println();   
     }
            
    
    
    private static void confirmerClotureIntervention(Intervention i, String commentaire, String statut){
        //Methode correspond au bouton Terminer de l'Intervention (portail Employe) 
        System.out.println();
        System.out.println("**** CLOTURE DE L'INTERVENTION ****");
        System.out.println();
        String sms;
        ServiceEmploye serviceEmp = new ServiceEmploye();     
        Intervention cloturee = serviceEmp.cloturerIntervention(i, commentaire, statut);
        if (cloturee != null)
        {
            System.out.println("> Intervention cloturée");
            sms =  "Bonjour " + i.getNomDemandeur();
            sms += ", votre demande d'intervention du " +i.getDateDeb().toString()+ " a été cloturée à " 
                            + i.getDateDeb().toString() +". "+ i.getCommentaire() + ". Cordialement, "+ i.getNomIntervenant();
            Message.envoyerNotification(i.getNumTelDemandeur(), sms);
        } else
            {
                System.out.println("> Intervention reste en cours");
            }
    }  
    
       private static void annulerIntervention(Client login){
        //Methode correspond au bouton Annuler de l'Intervention (portail Client)
        System.out.println();
        System.out.println("**** ANNULER INTERVENTION ****");
        System.out.println();
        
        ServiceClient service = new ServiceClient();
        String objet,corps,sms;
        List <Intervention> enCours = service.listerInterventionEnCours(login);
           
        int size = enCours.size();
        System.out.println("> Nb d'intervention en cours: "+ size);
        if(size == 0)
        {
            System.out.println("Aucune intervention en cours ");
            return;
        } else {
                for (int i = 0; i<size; i++)
                {
                    Intervention temp = enCours.get(i);
                    System.out.println("Numero: " + i + "> Type d'intervention: "+ temp.getTypeIntervention()+ ". Nom d'intervenant : "+temp.getNomIntervenant() );
                }
                Integer idInter = Saisie.lireInteger("> Voulez - vous annuler laquelle? Tapez le numéro correspondant. ");
                while (idInter < 0||size <=idInter) {
                    String warning = "> Numéro saisi invalide. Il faut choisir entre 0 et " + (size-1);
                    idInter = Saisie.lireInteger(warning);
                }
                    Intervention annulee = enCours.get(idInter);
                    Employe e = service.annulerIntervention(enCours.get(idInter));
                    if (e==null){
                         sms = "";
                        objet = "Echec de l'annulation de votre demande";
                        corps = "Desole! ";
                    }
                    else{
                        objet = "Confirmation de l'annulation";
                        corps = "Bonjour " + login.getPrenom() +" .Votre demande d'annulation a été enregistrée avec succès. ";
                        sms = "Bonjour " + e.getPrenom();
                        sms += ". L'intervention "+ annulee.getTypeIntervention();
                        sms += " a été annulée par client. Merci de revenir en agence et cloturer cette intervention.";
                        
                    }
                String mailClient = (login.getMail());
                Message.envoyerMail("dsi@reactif.com", mailClient, objet, corps);
                Message.envoyerNotification(e.getNumTel(), sms);
                        
            }     
    }     
      
    private static void consulterStatistiques (Employe e)
    {
        //taux de reussite
        //duree moyenne pour traiter une intervention
        // Type d'intervention recue le plus
    }
   
    
    
    
    private static void saisirInscriptionClient() throws ParseException {
        //Methode correspond au bouton Creer un compte (Portail Client)
        ServiceClient service = new ServiceClient();
        String objet,corps;
        System.out.println("**************************");
        System.out.println("** NOUVELLE INSCRIPTION **");
        System.out.println("**************************");
        System.out.println("Veuillez saisir les informations suivantes:");
        String civilite = Saisie.lireChaine("Civilité (M, Mme ou Mlle): ");
        String nom = Saisie.lireChaine("Nom de famille : ");
        String prenom = Saisie.lireChaine("Prénom : ");
        String mail = Saisie.lireChaine("Mail : ");
        String motDePasse = Saisie.lireChaine("Mot de passe : ");
        String birthday = Saisie.lireChaine("Date de naissance (YYYY-MM-DD) : ");
        String adresse = Saisie.lireChaine("Adresse : ");
        String numTel = Saisie.lireChaine ("Numero de telephone : ");
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);  
        Client client = new Client(civilite, nom, prenom, mail, motDePasse, d,adresse,numTel);
        Long idClient = service.inscrireClient(client);

        if (idClient != null) {
            System.out.println("> Succès inscription. L'id du nouveau client est : " + client.getId());
            objet = "Bienvenue chez REACT'IF";
            corps = "Bonjour " + prenom;
            corps += ", nous vous confirmons votre inscription au service REACT'IF. Un cas d'urgence? Rendez-vous vite sur notre site, vous pouvez compter sur nous pour résoudre votre problème avec rapidité et efficacité";
            
        } else {
            System.out.println("> Échec inscription");
            objet = "Echec de l'inscription chez REACT'IF";
            corps = "Bonjour " + prenom;
            corps += ", votre inscription au service REACT'IF a malencontreusement échoué... Merci de recommencer ultérieurement.";
            
        }
        Message.envoyerMail("contact@react.if", mail, objet, corps);
        System.out.println("*****************");
        System.out.println("** Inscription terminée ! **");
        System.out.println("*****************");
    }

    private static void saisirAuthentificationClient() {
        //Methode correspond au formulaire de connexion + bouton Se Connecter
        ServiceClient service = new ServiceClient();
        System.out.println();
        System.out.println("********************************");
        System.out.println("** AUTHENTIFICATION de CLIENT **");
        System.out.println("********************************");
        System.out.println();
        System.out.println();
        System.out.println("** Authentifier Client:");
        System.out.println();
        String clientMail;
        Client login;
        do {
            clientMail = Saisie.lireChaine("Mail ? [0 pour quitter] ");
            String clientMotDePasse = Saisie.lireChaine("Mot de passe ? ");
            login = service.seAuthentifierClient(clientMail, clientMotDePasse);
            if (login == null)
            {
                 System.out.println("Authentification invalide. Veuillez resaisir votre identifiant ou taper 0 pour terminer. ");
                
            }
        }while (!"0".equals(clientMail)&&login ==null);
        if (login != null)
        {
            System.out.println("Le client ayant l'identifiant (id, Nom, Prenom) : " +login.getId()+" "+ login.getNom()+" "+login.getPrenom()+" est connecté!");
            System.out.println();
            System.out.println("*****************");
            System.out.println("** Authentification terminée avec succes ! **");
            System.out.println("*****************");
            System.out.println();
            clientConnecte(login);
        }   
       
    }
    private static void saisirAuthentificationEmploye() {
        //Methode correspond au formulaire de connexion + bouton Se Connecter
        ServiceEmploye service = new ServiceEmploye();
        System.out.println("** Vous etes Employe **");
        System.out.println("********************************");
        System.out.println("** AUTHENTIFICATION d' EMPLOYE **");
        System.out.println("********************************");
        System.out.println();
        System.out.println();
        System.out.println("** Authentifier Employe:");
        System.out.println();
        String empMail;
        Employe login;
        do {
            empMail = Saisie.lireChaine("Mail ? [0 pour quitter] ");
            String empMotDePasse = Saisie.lireChaine("Mot de passe ? ");
            login = service.seAuthentifierEmploye(empMail, empMotDePasse);
            if (login == null)
            {
                 System.out.println("Authentification invalide. Veuillez resaisir votre identifiant ou taper 0 pour terminer. ");
                
            }
        }while (login==null&&!"0".equals(empMail));
        if (login != null)
        {
            System.out.println("L'employe ayant l'identifiant (id, Nom, Prenom) : " +login.getId()+" "+ login.getNom()+" "+login.getPrenom()+" est connecté!");
            System.out.println();
            System.out.println("*****************");
            System.out.println("** Authentification terminée avec succes ! **");
            System.out.println("*****************");
            System.out.println();
            employeConnecte(login);
        }   
       
    }   
}

