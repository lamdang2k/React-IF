package util.test;

import com.google.maps.model.LatLng;
import fr.insalyon.dasi.metier.service.util.GeoNetApi;

/**
 *
 * @author DASI Team
 */
public class TestGeoNetApi {

    /*
     * Méthode main() de test pour l'utilisation de l'API GeoNet utilisant l'API Google Maps
     */
    public static void main(String[] args) {

        if (GeoNetApi.hasInvalidApiKey()) {
            for (int i = 0; i < 100; i++) {
                System.err.println("[ERREUR] VOUS AVEZ OUBLIÉ DE CHANGER LA CLÉ DE L'API !!!!!");
            }
            return;
        }

        String adresse1 = "7 Avenue Jean Capelle Ouest, Villeurbanne";
        LatLng coords1 = GeoNetApi.getLatLng(adresse1);
        System.out.println("Lat/Lng de Adresse #1: " + coords1);

        String adresse2 = "37 Avenue Jean Capelle Est, Villeurbanne";
        LatLng coords2 = GeoNetApi.getLatLng(adresse2);
        System.out.println("Lat/Lng de Adresse #2: " + coords2);

        String adresse3 = "61 Avenue Roger Salengro, Villeurbanne";
        LatLng coords3 = GeoNetApi.getLatLng(adresse3);
        System.out.println("Lat/Lng de Adresse #3: " + coords3);

        // Coordonnées directes: Rond-Point du Totem, Cours Tolstoï, Villeurbanne
        LatLng coords4 = new LatLng(45.763781, 4.8735128);
        System.out.println("Lat/Lng de Coords #4: ( " + coords4.lat + "; " + coords4.lng + " )");

        Double duree = GeoNetApi.getTripDurationByBicycleInMinute(coords1, coords3);
        System.out.println("Durée de Trajet à Vélo de Adresse #1 à Adresse #3 (trajet direct): " + duree + " min");

        Double distance = GeoNetApi.getTripDistanceByCarInKm(coords1, coords3, coords2);
        System.out.println("Distance en Voiture de Adresse #1 à Adresse #3 en passant par Adresse #2 (distance par la route): " + distance + " km");

        Double distanceVolDOiseau = GeoNetApi.getFlightDistanceInKm(coords1, coords3);
        System.out.println("Distance à Vol d'Oiseau de Adresse #1 à Adresse #3 (distance géographique): " + distanceVolDOiseau + " km");

        Double autreDistanceVolDOiseau = GeoNetApi.getFlightDistanceInKm(coords1, coords4);
        System.out.println("Distance à Vol d'Oiseau de Adresse #1 à Coords #4 (distance géographique): " + autreDistanceVolDOiseau + " km");

        System.out.println("[FIN] Fin du Test");
        System.exit(0);
    }
}
