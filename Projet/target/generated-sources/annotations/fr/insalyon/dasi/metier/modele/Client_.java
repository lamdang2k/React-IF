package fr.insalyon.dasi.metier.modele;

import com.google.maps.model.LatLng;
import fr.insalyon.dasi.metier.modele.Civilite;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-04-12T14:30:57")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, String> motDePasse;
    public static volatile SingularAttribute<Client, Date> birthdate;
    public static volatile SingularAttribute<Client, String> address;
    public static volatile SingularAttribute<Client, String> mail;
    public static volatile SingularAttribute<Client, Civilite> civil;
    public static volatile SingularAttribute<Client, LatLng> coordonnes;
    public static volatile SingularAttribute<Client, Long> id;
    public static volatile SingularAttribute<Client, String> nom;
    public static volatile SingularAttribute<Client, String> prenom;
    public static volatile SingularAttribute<Client, String> numTel;

}