package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Employe;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-04-10T21:28:27")
@StaticMetamodel(Intervention.class)
public class Intervention_ { 

    public static volatile SingularAttribute<Intervention, Employe> employe;
    public static volatile SingularAttribute<Intervention, Date> dateDeb;
    public static volatile SingularAttribute<Intervention, String> description;
    public static volatile SingularAttribute<Intervention, Long> id;
    public static volatile SingularAttribute<Intervention, Client> demandeur;
    public static volatile SingularAttribute<Intervention, Date> dateFin;
    public static volatile SingularAttribute<Intervention, String> commentaire;
    public static volatile SingularAttribute<Intervention, String> statut;

}