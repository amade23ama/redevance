package sn.dscom.backend.service.converter;
import com.google.common.base.Strings;
import sn.dscom.backend.common.dto.TransporteurDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.TransporteurEntity;

import java.util.Date;

/**
 * Converteur de {@link TransporteurEntity} en {@link TransporteurDTO}
 */
public class TransporteurConverter implements Transformer<TransporteurDTO, TransporteurEntity> {

    /**
     * transformation de {@link TransporteurEntity} en {@link TransporteurDTO}
     *
     * @param transporteurEntity l'objet à transformer en {@link TransporteurDTO}
     * @return l'objet {@link TransporteurDTO}
     */
    @Override
    public TransporteurDTO reverse(TransporteurEntity transporteurEntity) {

        //on retourne null si l'objet d'entré est null
        if (transporteurEntity == null) {
            return null;
        }
        return TransporteurDTO.builder()
                .id(transporteurEntity.getId())
                .type(transporteurEntity.getType())
                .nom(transporteurEntity.getNom())
                .prenom(transporteurEntity.getPrenom())
                .email(transporteurEntity.getEmail())
                .telephone(transporteurEntity.getTelephone())
                .adresse(transporteurEntity.getAdresse())
                .dateCreation(transporteurEntity.getDateCreation())
                .dateModification(transporteurEntity.getDateModification())
                .build();
    }

    /**
     * transformation de {@link TransporteurDTO} en {@link TransporteurEntity}
     *
     * @param transporteurDTO l'objet à transformer en {@link TransporteurEntity}
     * @return l'objet {@link TransporteurEntity}
     */
    @Override
    public TransporteurEntity transform(TransporteurDTO transporteurDTO) {

        //on retourne null si l'objet d'entré est null
        if (transporteurDTO == null) {
            return null;
        }

        return TransporteurEntity.builder()
                .id(transporteurDTO.getId())
                .type(Strings.isNullOrEmpty(transporteurDTO.getType()) ? transporteurDTO.getType() : transporteurDTO.getType().trim().toUpperCase())
                .nom(transporteurDTO.getNom().trim().toUpperCase())
                .prenom(Strings.isNullOrEmpty(transporteurDTO.getPrenom()) ? transporteurDTO.getPrenom() : transporteurDTO.getPrenom().trim().toUpperCase())
                .email(transporteurDTO.getEmail())
                .telephone(Strings.isNullOrEmpty(transporteurDTO.getTelephone()) ? transporteurDTO.getTelephone() : transporteurDTO.getTelephone().trim())
                .adresse(Strings.isNullOrEmpty(transporteurDTO.getAdresse()) ? transporteurDTO.getAdresse() : transporteurDTO.getAdresse().trim().toUpperCase())
                .dateCreation(transporteurDTO.getId() == null ? new Date() : transporteurDTO.getDateCreation())
                .dateModification(transporteurDTO.getId() == null ? null : new Date())
                .build();
    }
}
