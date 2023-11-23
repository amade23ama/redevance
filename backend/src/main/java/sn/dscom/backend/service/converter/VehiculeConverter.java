package sn.dscom.backend.service.converter;
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.CategorieEntity;
import sn.dscom.backend.database.entite.TransporteurEntity;
import sn.dscom.backend.database.entite.VehiculeEntity;

import javax.validation.Valid;
import java.util.Date;

/**
 * Converteur de {@link VehiculeEntity} en {@link VehiculeDTO}
 */
public class VehiculeConverter implements Transformer<VehiculeDTO, VehiculeEntity> {

    /** transporteur Converter*/
    private Transformer<TransporteurDTO, TransporteurEntity> transporteurConverter = new TransporteurConverter();

    /** categorie Converter */
    Transformer<CategorieDTO, CategorieEntity> categorieConverter = new CategorieConverter();

    /**
     * transformation de {@link VehiculeEntity} en {@link VehiculeDTO}
     *
     * @param vehiculeEntity l'objet à transformer en {@link VehiculeDTO}
     * @return l'objet {@link VehiculeDTO}
     */
    @Override
    public VehiculeDTO reverse(VehiculeEntity vehiculeEntity) {

        //on retourne null si l'objet d'entré est null
        if (vehiculeEntity == null) {
            return null;
        }
        return VehiculeDTO.builder()
                .id(vehiculeEntity.getId())
                .immatriculation(vehiculeEntity.getImmatriculation())
                .categorie(this.categorieConverter.reverse(vehiculeEntity.getCategorieEntity()))
                .transporteur(this.transporteurConverter.reverse(vehiculeEntity.getTransporteurEntity()))
                .dateCreation(vehiculeEntity.getDateCreation())
                .dateModification(vehiculeEntity.getDateModification())
                .poidsVide(vehiculeEntity.getPoidsVide())
                .build();
    }

    /**
     * transformation de {@link VehiculeDTO} en {@link VehiculeEntity}
     *
     * @param vehiculeDTO l'objet à transformer en {@link VehiculeEntity}
     * @return l'objet {@link VehiculeEntity}
     */
    @Override
    public VehiculeEntity transform(VehiculeDTO vehiculeDTO) {

        //on retourne null si l'objet d'entré est null
        if (vehiculeDTO == null) {
            return null;
        }

        return VehiculeEntity.builder()
                .id(vehiculeDTO.getId())
                .immatriculation(vehiculeDTO.getImmatriculation().trim().toUpperCase())
                .categorieEntity(this.categorieConverter.transform(vehiculeDTO.getCategorie()))
                .transporteurEntity(this.transporteurConverter.transform(vehiculeDTO.getTransporteur()))
                .dateCreation(vehiculeDTO.getId() == null ? new Date() : vehiculeDTO.getDateCreation())
                .dateModification(vehiculeDTO.getId() == null ? null : new Date())
                .poidsVide(vehiculeDTO.getPoidsVide())
                .build();
    }
}
