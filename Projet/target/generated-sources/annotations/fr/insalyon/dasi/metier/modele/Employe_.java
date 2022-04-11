package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.Agence;
import fr.insalyon.dasi.metier.modele.Civilite;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-04-10T21:28:27")
@StaticMetamodel(Employe.class)
public class Employe_ { 

    public static volatile SingularAttribute<Employe, Date> birthdate;
    public static volatile SingularAttribute<Employe, String> mail;
    public static volatile SingularAttribute<Employe, Double> distance;
    public static volatile SingularAttribute<Employe, Integer> heureFin;
    public static volatile SingularAttribute<Employe, String> nom;
    public static volatile SingularAttribute<Employe, String> numTel;
    public static volatile SingularAttribute<Employe, Agence> agence;
    public static volatile SingularAttribute<Employe, String> motDePasse;
    public static volatile SingularAttribute<Employe, Civilite> civil;
    public static volatile SingularAttribute<Employe, Integer> heureDeb;
    public static volatile SingularAttribute<Employe, Long> id;
    public static volatile SingularAttribute<Employe, String> prenom;
    public static volatile SingularAttribute<Employe, Boolean> disponible;

}