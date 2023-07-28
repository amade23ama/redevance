package sn.dscom.backend.service.converter;

import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Chargement Converter
 */
public class ChargementConverter implements Transformer<ChargementDTO, ChargementEntity> {

    /**
     * vehicule Converter
     */
    private final Transformer<VehiculeDTO, VehiculeEntity> vehiculeConverter = new VehiculeConverter();

    /** Site Converteur */
    private Transformer<SiteDTO, SiteEntity> siteConverteur = new SiteConverter();

    /**
     * explitation Converteur
     */
    private final Transformer<DepotDTO, DepotEntity> depotConverteur = new DepotConverter();

    /**
     * explitation Converteur
     */
    private final Transformer<ExploitationDTO, ExploitationEntity> exploitationConverteur = new ExploitationConverter();

    /**
     * produit Converter
     */
    private final Transformer<ProduitDTO, ProduitEntity> produitConverter = new ProduitConverter();

    /**
     * transformation de {@link ChargementEntity} en {@link ChargementDTO}
     *
     * @param chargementEntity l'objet à transformer en {@link ChargementDTO}
     * @return l'objet {@link ChargementDTO}
     */
    @Override
    public ChargementDTO reverse(ChargementEntity chargementEntity) {

        //on retourne null si l'objet d'entré est null
        if (chargementEntity == null) {
            return null;
        }

        return ChargementDTO.builder()
                .id(chargementEntity.getId())
                .dateCreation(chargementEntity.getDateCreation())
                .dateModif(chargementEntity.getDateModification())
                .datePesage(chargementEntity.getDatePassage())
                .poids(chargementEntity.getPoids())
                .ecart(chargementEntity.getEcart())
                .poidsMax(chargementEntity.getPoidsMax())
                .poidsSubst(chargementEntity.getPoidsSubsitance())
                .destination(chargementEntity.getDestination())
                .volumeMoyen(chargementEntity.getVolumeMoyen())
                .volumeSubst(chargementEntity.getVolumeSubsitance())
                .vehicule(this.vehiculeConverter.reverse(chargementEntity.getVehiculeEntity()))
                .site(this.siteConverteur.reverse(chargementEntity.getSiteEntity()))
               // .depot(this.depotConverteur.reverse(chargementEntity.getDepotEntity()))
                .exploitation(exploitationConverteur.reverse(chargementEntity.getExploitationEntity()))
                .produit(this.produitConverter.reverse(chargementEntity.getProduitEntity()))
                .build();
    }

    /**
     * getVoitureDTO
     * @param vehiculeDTOList vehiculeDTOList
     * @return list
     */
    private List<VehiculeDTO> getVoitureDTO(List<VehiculeEntity> vehiculeDTOList) {
        return vehiculeDTOList.stream()
                .map(voit -> this.vehiculeConverter.reverse(voit))
                .collect(Collectors.toList());
    }


    /**
     * transformation de {@link ChargementDTO} en {@link ChargementEntity}
     *
     * @param chargementDTO l'objet à transformer en {@link ChargementEntity}
     * @return l'objet {@link ChargementEntity}
     */
    @Override
    public ChargementEntity transform(ChargementDTO chargementDTO) {

        //on retourne null si l'objet d'entré est null
        if (chargementDTO == null) {
            return null;
        }

        return ChargementEntity.builder()
                .dateCreation(chargementDTO.getId() == null ? new Date() :chargementDTO.getDateCreation())
                .dateModification(chargementDTO.getId() == null ? null : new Date())
                .datePassage(chargementDTO.getDatePesage())
                .poids(chargementDTO.getPoids())
                .ecart(chargementDTO.getEcart())
                .poidsMax(chargementDTO.getPoidsMax())
                .poidsSubsitance(chargementDTO.getPoidsSubst())
                .destination(chargementDTO.getDestination())
                .volumeMoyen(chargementDTO.getVolumeMoyen())
                .volumeSubsitance(chargementDTO.getVolumeSubst())
                .vehiculeEntity(this.vehiculeConverter.transform(chargementDTO.getVehicule()))
                .siteEntity(this.siteConverteur.transform(chargementDTO.getSite()))
               // .depotEntity(this.depotConverteur.transform(chargementDTO.getDepot()))
                .exploitationEntity(this.exploitationConverteur.transform(chargementDTO.getExploitation()))
                .produitEntity(this.produitConverter.transform(chargementDTO.getProduit()))
                .build();
    }
}
