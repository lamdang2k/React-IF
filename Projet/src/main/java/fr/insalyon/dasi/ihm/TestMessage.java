package fr.insalyon.dasi.ihm;

import java.io.PrintWriter;
import java.io.StringWriter;
import fr.insalyon.dasi.metier.service.util.Message;

/**
 *
 * @author DASI Team
 */
public class TestMessage {
    
    public static void main(String[] args) {
        
        StringWriter corps = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(corps);
        
        mailWriter.println("Bonjour,");
        mailWriter.println();
        mailWriter.println("  Ceci est un mail destiné à tester l'envoi simulé par affichage sur la console.");
        mailWriter.println();
        mailWriter.println("  Cordialement,");
        mailWriter.println();
        mailWriter.println("    Yann Gripay");

        Message.envoyerMail(
                "yann.gripay@insa-lyon.fr",
                "etudiants.3IF@insa-lyon.fr",
                "[DASI] Test d'envoi de e-mail",
                corps.toString()
            );
        
        
        StringWriter message = new StringWriter();
        PrintWriter notificationWriter = new PrintWriter(message);
        
        notificationWriter.println("Ceci est une notification pour prévenir de 2 choses:");
        notificationWriter.println("1) NE PAS oublier le poly");
        notificationWriter.println("2) TESTER au fur et à mesure du développement");

        Message.envoyerNotification(
                "0988776655",
                message.toString()
            );
    }
}
