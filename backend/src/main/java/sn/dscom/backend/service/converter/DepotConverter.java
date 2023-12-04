package sn.dscom.backend.service.converter;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import sn.dscom.backend.common.constants.Enum.StatutEnum;
import sn.dscom.backend.common.dto.ChargementDTO;
import sn.dscom.backend.common.dto.DepotDTO;
import sn.dscom.backend.common.dto.SiteDTO;
import sn.dscom.backend.common.dto.UtilisateurDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ChargementEntity;
import sn.dscom.backend.database.entite.DepotEntity;
import sn.dscom.backend.database.entite.SiteEntity;
import sn.dscom.backend.database.entite.UtilisateurEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exploitation Converter
 */
public class DepotConverter implements Transformer<DepotDTO, DepotEntity> {

    /** utilisateur Transformer */
    Transformer<UtilisateurDTO, UtilisateurEntity> utilisateurTransformer = new UtilisateurConverter();

    /**
     * chargement Transformer
     */
   // private final Transformer<ChargementDTO, ChargementEntity> chargementConverter = new ChargementConverter();

    /** Site Converteur */
    private final Transformer<SiteDTO, SiteEntity> siteConverteur = new SiteConverter();

    /**
     * transformation de {@link DepotEntity} en {@link DepotDTO}
     *
     * @param depotEntity l'objet à transformer en {@link DepotDTO}
     * @return l'objet {@link DepotDTO}
     */
    @Override
    public DepotDTO reverse(DepotEntity depotEntity) {

        //on retourne null si l'objet d'entré est null
        if (depotEntity == null) {
            return null;
        }

        // On recupère la liste des Chargements
        //List<ChargementEntity> listChargement = MoreObjects.firstNonNull(depotEntity.getChargementEntityList(), Collections.emptyList());

        return DepotDTO.builder()
                .id(depotEntity.getId())
                .statut(depotEntity.getStatut())
                .nom(depotEntity.getNom())
                .dateHeureDepot(depotEntity.getDateHeureDepot())
                .dateHeureFinDepot(depotEntity.getDateHeureFinDepot())
                .deposeur(this.utilisateurTransformer.reverse(depotEntity.getDeposeur()))
                .nbChargementDeposes(depotEntity.getNbChargementDeposes())
                .nbChargementErreur(depotEntity.getNbChargementErreur())
                .nomFichier(depotEntity.getNomFichier())
                .nbChargementReDeposes(depotEntity.getNbChargementReDeposes())
                .site(this.siteConverteur.reverse(depotEntity.getSiteEntity()))
                /*.chargementDTOList(listChargement.stream()
                        .map(this.chargementConverter::reverse)
                        .collect(Collectors.toList()))
                        */

                .build();
    }

    /**
     * transformation de {@link DepotDTO} en {@link DepotEntity}
     *
     * @param depotDTO l'objet à transformer en {@link DepotEntity}
     * @return l'objet {@link DepotEntity}
     */
    @Override
    public DepotEntity transform(DepotDTO depotDTO) {
        //on retourne null si l'objet d'entré est null
        if (depotDTO == null) {
            return null;
        }

        // On recupère la liste des Chargements
       // List<ChargementDTO> listChargement = MoreObjects.firstNonNull(depotDTO.getChargementDTOList(), Collections.emptyList());

        return DepotEntity.builder()
                .id(depotDTO.getId())
                .statut(depotDTO.getStatut())
                .nom(Strings.isNullOrEmpty(depotDTO.getNom()) ? depotDTO.getNom() : depotDTO.getNom().trim().toUpperCase())
                .dateHeureDepot(depotDTO.getId() == null ? new Date() :depotDTO.getDateHeureDepot())
                .dateHeureFinDepot(depotDTO.getDateHeureFinDepot() == null ? null :depotDTO.getDateHeureFinDepot())
                .deposeur(this.utilisateurTransformer.transform(depotDTO.getDeposeur()))
                .nbChargementDeposes(depotDTO.getNbChargementDeposes())
                .nbChargementErreur(depotDTO.getNbChargementErreur())
                .nomFichier(depotDTO.getNomFichier())
                .nbChargementReDeposes(depotDTO.getNbChargementReDeposes())
                .siteEntity(this.siteConverteur.transform(depotDTO.getSite()))
                /*.chargementEntityList(listChargement.stream()
                        .map(this.chargementConverter::transform)
                        .collect(Collectors.toList()))*/
                .build();
    }
}
