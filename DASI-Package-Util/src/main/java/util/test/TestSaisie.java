package util.test;

import java.util.Arrays;
import util.Saisie;

/**
 *
 * @author DASI Team
 */
public class TestSaisie {

    public static void main(String[] args) {

        System.out.println("Bonjour !");

        String nom = Saisie.lireChaine("Entrez votre nom: ");
        System.out.println("Bonjour, " + nom + " !");

        Integer age = Saisie.lireInteger("Entrez votre âge: ");
        System.out.println("Vous avez " + age + " ans.");

        Integer annee = Saisie.lireInteger("Entrez votre année au Département IF (3,4,5): ", Arrays.asList(3, 4, 5));
        System.out.println("Vous êtes en " + annee + "IF.");

        Saisie.pause();

        System.out.println("Au revoir !");
    }
}
