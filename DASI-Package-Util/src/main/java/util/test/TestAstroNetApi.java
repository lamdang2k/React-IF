package util.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import util.AstroNetApi;

/**
 *
 * @author DASI Team
 */
public class TestAstroNetApi {

    /*
     * Méthode main() de test pour l'utilisation de l'API IfAstroNet
     */
    public static void main(String[] args) throws ParseException, IOException {

        if (AstroNetApi.hasInvalidApiKey()) {
            for (int i = 0; i < 100; i++) {
                System.err.println("[ERREUR] VOUS AVEZ OUBLIÉ DE CHANGER LA CLÉ DE L'API !!!!!");
            }
            return;
        }

        
        AstroNetApi astroApi = new AstroNetApi();

        // Pratique pour construire (et afficher) des objets Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        String prenom = "Ada";
        Date dateNaissance = simpleDateFormat.parse("10/12/1815");
        
        System.out.println("");
        System.out.println("~<[ Personne ]>~");
        System.out.println("Prénom: " + prenom);
        System.out.println("Date de Naissance: " + simpleDateFormat.format(dateNaissance));
        System.out.println("");

        List<String> profil = astroApi.getProfil(prenom, dateNaissance);

        String signeZodiaque = profil.get(0);
        String signeChinois = profil.get(1);
        String couleur = profil.get(2);
        String animal = profil.get(3);

        System.out.println("");
        System.out.println("~<[ Profil ]>~");
        System.out.println("[Profil] Signe du Zodiaque: " + signeZodiaque);
        System.out.println("[Profil] Signe Chinois: " + signeChinois);
        System.out.println("[Profil] Couleur porte-bonheur: " + couleur);
        System.out.println("[Profil] Animal-totem: " + animal);
        System.out.println("");

        // Niveaux entre 1 (mauvais) et 4 (excellent)
        int niveauAmour = 4;
        int niveauSante = 1;
        int niveauTravail = 2;

        List<String> predictions = astroApi.getPredictions(couleur, animal, niveauAmour, niveauSante, niveauTravail);

        System.out.println("");
        System.out.println("~<[ Prédictions ]>~");
        System.out.println("[ Amour ] " + predictions.get(0));
        System.out.println("[ Santé ] " + predictions.get(1));
        System.out.println("[Travail] " + predictions.get(2));
        System.out.println("");

        // Niveaux entre 1 (mauvais) et 4 (excellent)
        niveauAmour = 1;
        niveauSante = 3;
        niveauTravail = 4;

        predictions = astroApi.getPredictions(couleur, animal, niveauAmour, niveauSante, niveauTravail);

        System.out.println("");
        System.out.println("~<[ Prédictions ]>~");
        System.out.println("[ Amour ] " + predictions.get(0));
        System.out.println("[ Santé ] " + predictions.get(1));
        System.out.println("[Travail] " + predictions.get(2));
        System.out.println("");
    }
    
}
