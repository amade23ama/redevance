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
        this.libelle = new StringBuilder()
                .append("[PROFIL] ")
                .append(profil.getLibelle())
                .toString();
        this.typeClass = ProfilEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }
    public AutocompleteRecherche(UtilisateurEntity utilisateur) {
        this.id = utilisateur.getId().toString();
        this.libelle = new StringBuilder().
                append("[UTILISATEUR] ")
                .append(utilisateur.getPrenom())
                .append(" - ")
                .append(utilisateur.getNom())
                .toString();
        this.typeClass = UtilisateurEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param produit produit
     */
    public AutocompleteRecherche(ProduitEntity produit) {
        this.id = produit.getId().toString();
        this.libelle = new StringBuilder().
                append("[PRODUIT] ")
                .append(produit.getNomSRC())
                .append(" - ")
                .append(produit.getNomNORM())
                .toString();
        this.typeClass = ProduitEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param site site
     */
    public AutocompleteRecherche(SiteEntity site) {
        this.id = site.getId().toString();
        this.libelle = new StringBuilder().
                append("[SITE] ")
                .append(site.getNom())
                .append(" - ")
                .append(site.getLocalite())
                .toString();
        this.typeClass = SiteEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param siteExploitation siteExploitation
     */
    public AutocompleteRecherche(ExploitationEntity siteExploitation) {
        this.id = siteExploitation.getId().toString();
        this.libelle = new StringBuilder().
                append("[SITE_EXPLOITATION] ")
                .append(siteExploitation.getNom())
                .append(" - ")
                .append(siteExploitation.getRegion())
                .toString();
        this.typeClass = ExploitationEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param vehicule vehicule
     */
    public AutocompleteRecherche(VehiculeEntity vehicule) {
        this.id = vehicule.getId().toString();
        this.libelle = new StringBuilder().
                append("[VEHICULE] ")
                .append(vehicule.getImmatriculation())
                .append(" - ")
                .append(vehicule.getCategorieEntity().getType())
                .toString();
        this.typeClass = VehiculeEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }

    /**
     * AutocompleteRecherche
     * @param chargement chargement
     */
    public AutocompleteRecherche(ChargementEntity chargement) {
        this.id = chargement.getId().toString();
        this.libelle = new StringBuilder().
                append("[CHARGEMENT] ")
                .append(chargement.getDestination())
                .toString();
        this.typeClass = VehiculeEntity.class;
        this.origine = this.typeClass.getSimpleName();
    }
    public AutocompleteRecherche(CategorieEntity categorie) {
        this.id = categorie.getId().toString();
        this.libelle = new StringBuilder().
                append("[Categorie] ")
                .append(categorie.getType())
                .toString();
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
        this.libelle = new StringBuilder().
                append("[NOM] ")
                .append(depotEntity.getNom())
                .toString();
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
