package sn.dscom.backend.common.dto;


import lombok.*;
import sn.dscom.backend.database.entite.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutocompleteRecherche <T> implements Serializable {


    private String id;
    private String libelle;
    private String origine;
    private Class typeClass;

    public AutocompleteRecherche(ProfilEntity profil) {
        this.id = profil.getCode();
        this.libelle = "[PROFIL] " +
                profil.getLibelle();
        this.typeClass = ProfilEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }
    public AutocompleteRecherche(UtilisateurEntity utilisateur) {
        this.id = utilisateur.getId().toString();
        this.libelle = "[UTILISATEUR] " +
                utilisateur.getPrenom() +
                " - " +
                utilisateur.getNom();
        this.typeClass = UtilisateurEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param produit produit
     */
    public AutocompleteRecherche(ProduitEntity produit) {
        this.id = produit.getId().toString();
        this.libelle = "[PRODUIT] " +
                produit.getNomSRC() +
                " - " +
                produit.getNomNORM();
        this.typeClass = ProduitEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param site site
     */
    public AutocompleteRecherche(SiteEntity site) {
        this.id = site.getId().toString();
        this.libelle = "[SITE] " +
                site.getNom() +
                " - " +
                site.getLocalite();
        this.typeClass = SiteEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param siteExploitation siteExploitation
     */
    public AutocompleteRecherche(ExploitationEntity siteExploitation) {
        this.id = siteExploitation.getId().toString();
        this.libelle = "[SITE_EXPLOITATION] " +
                siteExploitation.getNom() +
                " - " +
                siteExploitation.getRegion();
        this.typeClass = ExploitationEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param vehicule vehicule
     */
    public AutocompleteRecherche(VehiculeEntity vehicule) {
        this.id = vehicule.getId().toString();
        this.libelle = "[VEHICULE] " +
                vehicule.getImmatriculation() +
                " - " +
                vehicule.getCategorieEntity().getType();
        this.typeClass = VehiculeEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param chargement chargement
     */
    public AutocompleteRecherche(ChargementEntity chargement) {
        this.id = chargement.getId().toString();
        this.libelle = "[CHARGEMENT] " +
                chargement.getDestination();
        this.typeClass = VehiculeEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }
    public AutocompleteRecherche(CategorieEntity categorie) {
        this.id = categorie.getId().toString();
        this.libelle = "[Categorie] " +
                categorie.getType();
        this.typeClass = CategorieEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    public AutocompleteRecherche(String id, String type, String libelle, String origine) {
        this.id = id;
        this.libelle = new StringBuilder()
                .append("[")
                .append(type.toUpperCase())
                .append("] ")
                .append(libelle)
                .toString();
        this.typeClass = String.class;
        this.origine = Arrays.asList(origine.split(" ")).stream().map(element -> {
            return element.substring(0, 1).toUpperCase() + element.substring(1).toLowerCase();
        }).collect(Collectors.joining(""));
    }
    /**
     * AutocompleteRecherche
     * @param depotEntity chargement
     */
    public AutocompleteRecherche(DepotEntity depotEntity) {

        this.id = depotEntity.getId().toString();
        this.libelle = "[NOM] " +
                depotEntity.getNom();
        this.typeClass = DepotEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    public void setId(String id) {
        this.id = id;
    }



    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getOrigine() {
        return origine;
    }
    public Class<T> getTypeClass() {
        return typeClass;
    }
}
