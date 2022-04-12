package fr.insalyon.dasi.metier.modele;

import com.google.maps.model.LatLng;
import fr.insalyon.dasi.metier.modele.Employe;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-04-12T14:30:57")
@StaticMetamodel(Agence.class)
public class Agence_ { 

    public static volatile SingularAttribute<Agence, String> address;
    public static volatile ListAttribute<Agence, Employe> listeEmployes;
    public static volatile SingularAttribute<Agence, LatLng> coordonnes;
    public static volatile SingularAttribute<Agence, Long> id;
    public static volatile SingularAttribute<Agence, Integer> nbEmpDispo;

}