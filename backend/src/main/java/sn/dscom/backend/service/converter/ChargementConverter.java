package sn.dscom.backend.service.converter;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import org.springframework.util.CollectionUtils;
import sn.dscom.backend.common.dto.*;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.*;

import java.util.*;
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
    private Transformer<DepotDTO, DepotEntity> depotConverteur = new DepotConverter();
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
        // On récupère le dernier dépot
        //List<DepotEntity> listDepotEntity = chargementEntity.getDepots().stream().sorted(Comparator.comparing(DepotEntity::getDateHeureDepot)).toList();
        //DepotEntity depotEntity = Iterables.getFirst(MoreObjects.firstNonNull(chargementEntity.getDepots(), Collections.emptyList()), new DepotEntity());
        //List<ChargementEntity> listChargement = MoreObjects.firstNonNull(chargementEntity.getDepots(), Collections.emptyList());
        List<DepotDTO> listDepotDTO= chargementEntity.getDepots().stream()
                .map(this.depotConverteur::reverse).collect(Collectors.toList());

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
                .exploitation(exploitationConverteur.reverse(chargementEntity.getExploitationEntity()))
                .produit(this.produitConverter.reverse(chargementEntity.getProduitEntity()))
                //.idDepot(depotEntity.getId())
                .idDepot(1L)
                .depotDTOList(listDepotDTO)
                .build();
    }

    /**
     * getVoitureDTO
     * @param vehiculeDTOList vehiculeDTOList
     * @return list
     */
    private List<VehiculeDTO> getVoitureDTO(List<VehiculeEntity> vehiculeDTOList) {
        return vehiculeDTOList.stream()
                .map(this.vehiculeConverter::reverse)
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
        List<DepotEntity> listDepotEntity= chargementDTO.getDepotDTOList().stream()
                .map(this.depotConverteur::transform).collect(Collectors.toList());
        return ChargementEntity.builder()
                .id(chargementDTO.getId() == null ? null : chargementDTO.getId())
                .dateCreation(chargementDTO.getId() == null ? new Date() :chargementDTO.getDateCreation())
                .dateModification(chargementDTO.getId() == null ? null : new Date())
                .datePassage(chargementDTO.getDatePesage())
                .poids(chargementDTO.getPoids())
                .ecart(chargementDTO.getEcart())
                .poidsMax(chargementDTO.getPoidsMax())
                .poidsSubsitance(chargementDTO.getPoidsSubst())
                .destination(Strings.isNullOrEmpty(chargementDTO.getDestination()) ? chargementDTO.getDestination() : chargementDTO.getDestination().trim().toUpperCase())
                .volumeMoyen(chargementDTO.getVolumeMoyen())
                .volumeSubsitance(chargementDTO.getVolumeSubst())
                .vehiculeEntity(this.vehiculeConverter.transform(chargementDTO.getVehicule()))
                .siteEntity(this.siteConverteur.transform(chargementDTO.getSite()))
                .exploitationEntity(this.exploitationConverteur.transform(chargementDTO.getExploitation()))
                .produitEntity(this.produitConverter.transform(chargementDTO.getProduit()))
                .depots(listDepotEntity)
                .build();
    }
}
