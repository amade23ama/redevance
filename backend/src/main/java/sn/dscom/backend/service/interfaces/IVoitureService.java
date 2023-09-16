package sn.dscom.backend.service.interfaces;

import sn.dscom.backend.common.dto.CritereRecherche;
import sn.dscom.backend.common.dto.VehiculeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface pournissant les operations sur les vehicule
 */
public interface IVoitureService {

    /**
     * Fournit l'operation d'enregistrement d'un véhicule
     *
     * @param vehiculeDTO le véhicule à enregistrer
     * @return le véhicule enregistrée
     */
    Optional<VehiculeDTO> enregistrerVehicule(VehiculeDTO vehiculeDTO);

    /**
     * Fournit l'operation de rechercher d'un véhicule
     *
     * @return la liste des véhicules trouvées
     */
    Optional<List<VehiculeDTO>> rechercherVehicules();

    /**
     * Fournit l'operation de modification d'un véhicule
     *
     * @param vehiculeDTO le véhicule modifier
     * @return le véhicule modifié
     */
    Optional<VehiculeDTO> modifierVehicule(VehiculeDTO vehiculeDTO);

    /**
     * Fournit l'operation de suppression d'un véhicule
     *
     * @param voiture de l'Entité à supprimer
     */
    void supprimerVehicule(VehiculeDTO voiture);

    VehiculeDTO chargerVehiculeDTOParId(Long id);

    /**
     * rechargement Par Critere
     * @param critereRecherche critereRecherche
     * @return liste
     */
     List<VehiculeDTO> rechargementParCritere(CritereRecherche<?> critereRecherche);
}
