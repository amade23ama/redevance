package sn.dscom.backend.common.dto;


import lombok.*;
import sn.dscom.backend.database.entite.ProfilEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import java.io.Serializable;
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
